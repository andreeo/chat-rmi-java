package es.ubu.lsi.client;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import es.ubu.lsi.common.ChatMessage;

/**
 * Representa la implementacion de la interfaz ChatClient
 * @author Andreeo
 * @version 1.0
 * @since 1.0
 */
public class ChatClientImpl extends UnicastRemoteObject implements ChatClient, Serializable {
    private static final long serialVersionUID = 1L;

    private int id = 0;
    private String nickname;

    /**
     * Constructor
     * @throws RemoteException si la comunicacion remota tiene problemas
     */
    public ChatClientImpl() throws RemoteException {
        super();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void receive(ChatMessage msg) throws RemoteException {
        System.out.println(msg.getNickname() + ": " + msg.getMessage());
    }

    @Override
    public String getNickName() throws RemoteException {
        return this.nickname;
    }

    @Override
    public void setNickName(String nickname) throws RemoteException {
        this.nickname = nickname;
    }

}
