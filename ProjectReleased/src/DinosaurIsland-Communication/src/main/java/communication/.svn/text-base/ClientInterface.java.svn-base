package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <b>Overview</b><br>
 * <p>
 * This is the interface of the client: it shows the kind of actions that a
 * client can handle.
 * </p>
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * Since the game logic is in the server, the server has to notify the client
 * about change turns, so this interface specify what is the action that the
 * server can call on the client in order to notify it about a change turn.
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * This interface is implemenented in {@link ServerHandler} in the Client part
 * of the project and its methods are called by the class {@link ClientHandler}
 * in the Server in order to notify the client about change turns.
 * </p>
 * 
 * @author RAA
 * @version 1.0
 * 
 */
public interface ClientInterface extends Remote {

	/**
	 * The method handleChangeTurn is called by the server in order to notify
	 * clients about a change turn. If the notification can be performed, the
	 * function ends normally without any return value, otherwise, an exception
	 * is thrown.
	 * 
	 * @param theCurrentPlayerUsername
	 *            is the name of the player that from now on will own the turn
	 * @throws RemoteException
	 *             if there is a connection error
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public void handleChangeTurn(String theCurrentPlayerUsername)
			throws RemoteException;

	/**
	 * The method ping is called by the server in order to check connectivity
	 * with the client. If the check can be performed, the function ends
	 * normally without any return value, otherwise, an exception is thrown.
	 * The action performed depends strongly on the implementations: some implementation
	 * may not be compatible with this method. In that case, the function simply does
	 * nothing.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * 
	 * @see RMIServerHandler
	 * 
	 */
	public void ping() throws RemoteException;
}