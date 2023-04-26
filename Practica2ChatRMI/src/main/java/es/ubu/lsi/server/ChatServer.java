package es.ubu.lsi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;

import es.ubu.lsi.client.ChatClient;
import es.ubu.lsi.common.ChatMessage;

/**
 * Chat server.
 * 
 * @author Raúl Marticorena
 * @author Joaquin P. Seco
 *
 */
public interface ChatServer extends Remote {
	
	/**
	 * Registers a new client.
	 * 
	 * @param client client
	 * @return client id
	 * @throws RemoteException remote error
	 */
	public abstract int checkIn(ChatClient client) throws RemoteException;
	
	
	/**
	 * Unregisters a new client.
	 * 
	 * @param client current client
	 * @throws RemoteException remote error
	 */
	public abstract void logout(ChatClient client) throws RemoteException;
	
	
	/**
	 * Publish a received message.
	 * 
	 * @param msg message
	 * @throws RemoteException remote error
	 */
	public abstract void publish(ChatMessage msg) throws RemoteException;

	/**
	 * Get a blacklist of users (banned)
	 * @throws RemoteException remote error
	 */
	HashSet<String> getBlacklist() throws RemoteException;

	/**
	 * Ban user
	 * @param word username
	 * @throws RemoteException remote error
	 */
	void banUser(String word) throws RemoteException;

	/**
	 * Unban user
	 * @param word username
	 * @throws RemoteException remote error
	 */
	void unbanUser(String word) throws  RemoteException;

}