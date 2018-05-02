/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <b>Overview</b><br>
 * <p>
 * This interface 
 * This state information includes:
 * <ul>
 * <li>Element 1
 * <li>Element 2
 * <li>The current element implementation 
 *     (see <a href="#setXORMode">setXORMode</a>)
 * </ul>
 * </p>
 *
 * <b>Responsibilities</b><br>
 * <p>
 * Other description in a separate paragraph.
 * </p>
 *
 * <b>Collaborators</b><br>
 * <p>
 * Here write about classes 
 * </p>
 * 
 * @author	728570
 * @version 1.0
 * @since	22/giu/2011@11.51.55
 *
 */















/**
 * <b>Overview</b><br>
 * <p>
 * This is the interface of the connection establisher of the server: it shows the kind of actions that a
 * server can handle in order to establish a connection with it.
 * </p>
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * Since the client has to connect to a sever before can try to enter the game, this interface
 * specifies the methods that a client can invoke to gain a connection with the server
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * This interface is implemenented in {@link ClientHandler} in the Server part
 * of the project and its methods are called by the class {@link ServerHandler}
 * in the Client in order to establish a client-server connection.
 * </p>
 * 
 * @see	RMIManager
 * @see RMIClientHandler
 * 
 */
public interface ServerAccept extends Remote {

	/** 
	 * The method connect is called by the client in order to notify the 
	 * server that it wants to establish a connection.  
	 * If the notification can be performed, the
	 * function ends normally the method will return a server interface, otherwise, an exception
	 * is thrown.
	 *
	 * @param theClient is the interface of the client that wants to connect to the server
	 * @return a server interface that can be used to interact with the server
	 * @throws RemoteException if the connections is not successful     
	 *
	 * @see		RMIManager
	 * @see		RMIClientHandler
	 *  
	 * @since	29/giu/2011@12.21.38
	 * 
	 */
	public ServerInterface connect(ClientInterface theClient) throws RemoteException;
	
}
