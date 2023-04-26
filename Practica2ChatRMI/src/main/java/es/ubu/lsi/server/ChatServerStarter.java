package es.ubu.lsi.server;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Representa la clase para ejecutar e iniciar el servidor
 * @author Andreeo
 * @version 1.0
 * @since 1.0
 */
public class ChatServerStarter {

    /**
     * Constructor
     */
    public ChatServerStarter() {
    }

    /**
     * Ejecuta el hilo principal (main) del programa servidor.
     * @param args  parámetros
     */
    public static void main(String[] args) {

        try {
            //create ChatServerImpl instance
            ChatServer server = new ChatServerImpl();
            // create a stub for a remote object
            ChatServer stub = (ChatServer) UnicastRemoteObject.exportObject(server, 8888);
            // get registry (set by command rmiregistry ...)
            Registry registry = LocateRegistry.getRegistry();
            // bind the name to the object stub
            registry.rebind("ChatServerRegistry", stub);
            // show that server is running and hope a key to end
            System.out.println("Server is running, click enter to end");
            int input = System.in.read();
            System.out.println((char) input);

            // is recommend unbind the ChatServerRegistry object
            registry.unbind("ChatServerRegistry");
            // is recommend unexport the server object
            UnicastRemoteObject.unexportObject(server, true);
        } catch (Exception e) { //throw error
            System.out.println("Server error:" + e.toString());
        }
    }
}
