package es.ubu.lsi.server;

import es.ubu.lsi.client.ChatClient;
import es.ubu.lsi.common.ChatMessage;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Representa la implementacion de la interfaz ChatServer
 * @author Andreeo
 * @version 1.0
 * @since 1.0
 */
public class ChatServerImpl implements ChatServer {

    //list of connected clients
    HashMap<Integer, ChatClient> clientMap;
    // list of banned users
    private final HashSet<String> blacklist;
    // id to control clientMap
    static private int nextId = 1;

    /**
     * Constructor
     */
    public ChatServerImpl() {
        super();
        clientMap = new HashMap<>();
        blacklist = new HashSet<>();
    }

    @Override
    public int checkIn(ChatClient client) {
        // increment the nextId
        int clientId = nextId++;
        // save each connected user
        clientMap.put(clientId, client);
        return clientId;
    }

    @Override
    public void logout(ChatClient client) throws RemoteException {
        // get id of user
        int clientId = client.getId();
        // and remove user
        clientMap.remove(clientId);
    }

    //broadcast message
    @Override
    public void publish(ChatMessage msg) throws RemoteException {
        // loop list of connected clients and broadcast the messages
        for (ChatClient client : clientMap.values()) {
            if(msg.getId() != client.getId()) {
                client.receive(msg);
            }

        }
    }

    @Override
    public HashSet<String> getBlacklist() throws RemoteException {
        return blacklist;
    }

    @Override
    public void banUser(String word) throws RemoteException {
        blacklist.add(word);
    }

    @Override
    public void unbanUser(String word) throws RemoteException {
        blacklist.remove(word);
    }


}
