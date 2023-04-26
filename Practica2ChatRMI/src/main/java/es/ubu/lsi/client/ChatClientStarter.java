package es.ubu.lsi.client;

import es.ubu.lsi.common.ChatMessage;
import es.ubu.lsi.server.ChatServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Representa la clase para ejecutar e iniciar el cliente
 * @author Andreeo
 * @version 1.0
 * @since 1.0
 */
public class ChatClientStarter {

    /**
     * Ejecuta el hilo principal (main) del programa cliente.
     * @param args  parámetros
     */
    public static void main(String[] args) {
        // set args
        if (args.length < 1 || args.length > 2) {
            System.out.println("Usage: java ChatClientStarter <nickname> [<host>]");
            System.exit(1);
        }

        // get nickname
        String  nickname = args[0];
        // get localhost or the specified host
        String host = args.length  > 1 ?
                args[1]
                : "localhost";

        try {
            // get registry
            Registry reg = LocateRegistry.getRegistry(host);
            // search the remote object
            ChatServer server = (ChatServer) reg.lookup("ChatServerRegistry");
            // instance ChatClientImpl
            ChatClientImpl client = new ChatClientImpl();
            // set nickname
            client.setNickName(nickname);
            //  get user id
            int userId = server.checkIn(client);
            // set id to user
            client.setId(userId);
            System.out.println("Welcome " + client.getNickName());

            // run interaction between client and server
            runClient(client, server);

        } catch (Exception e) {
            System.err.println("ClientStarter exception: " + e.getMessage());
        }
    }

    /**
     * Ejecuta la interaccion entre el cliente y el servidor
     * @param client
     * @param server
     * @throws RemoteException
     */
    private static void runClient(ChatClientImpl client, ChatServer server) throws RemoteException {
        // instance input
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            // get input
            input = scanner.nextLine();
            // process input
            if ("logout".equals(input)) {
                server.logout(client);
                break;
            } else if(input.startsWith("ban")) {
                // split ban and username
                String[] words = input.split(" ");
                // ban user
                server.banUser(words[1]);
                // create message
                ChatMessage msg = new ChatMessage(
                        client.getId(),
                        client.getNickName(),
                        "ha baneado a "+
                                words[1]);
                // publish message
                server.publish(msg);
            } else if(input.startsWith("unban")) {
                String[] words = input.split(" ");
                //unban user
                server.unbanUser(words[1]);
                ChatMessage msg = new ChatMessage(
                        client.getId(),
                        client.getNickName(),
                        "ha desbaneado a "+
                                words[1]);

                server.publish(msg);
            }
            else {
                // if user is not in blacklist then he can send message
                if (!server.getBlacklist().contains(client.getNickName())) {
                    ChatMessage msg = new ChatMessage(client.getId(), client.getNickName(), input);
                    server.publish(msg);
                } else { // if not, he is ban!
                    System.out.println("Estas baneado");
                }
            }
        }
        // is recommend close the scanner
        scanner.close();
        System.exit(0);
    }

}
