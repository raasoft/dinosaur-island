package communication;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import util.StackTraceUtils;
import exceptions.InvalidTokenException;

public class RMIClientHandler extends ClientHandler {
	
	private static final long serialVersionUID = 1L;
	private ClientInterface client;
	private Logger logger;
	
	public RMIClientHandler(ClientInterface theClient) throws RemoteException {
    	
		super();
		
		client = theClient;
		
		setupLogger();
		
    }
	

	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
		logger = Logger.getLogger("server.clienthandler."+this);
		logger.setParent(Logger.getLogger("server.main"));		
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#getLogger()
	 * 
	 */
	@Override
	public Logger getLogger() {
		return logger;
	}

	/* {@inheritDoc}
	 * @see communication.ClientHandler#run()
	 * 
	 */
	@Override
	public void run() {
		
		
			int i = 0;
			while(isRunning){
				try {	
					Thread.sleep(2000);
					i++;
					try {
						client.ping();
					} catch (RemoteException e) {
						handleClientDisconnection(e);
					}

				} catch (InterruptedException e) {

					logger.info("Interrupt Received");
					closeDown();
					return;
				}
		}
		
	}

	/* {@inheritDoc}
	 * @see communication.ClientHandler#closeDown()
	 * 
	 */
	@Override
	public void closeDown() {
		logger.info("Finalizing the RMI thread");
		isRunning = false;
		
		try {
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {
			logger.warning(e.getMessage());
		}
		
		logger.info("RMI thread finalized");
	}

	/* {@inheritDoc}
	 * @see communication.ClientHandler#handleClientDisconnection(java.io.IOException)
	 * 
	 */
	@Override
	protected void handleClientDisconnection(IOException theException) {
		
		logger.warning("The client "+getPlayerToken()+ " disconnected");
		logger.warning(StackTraceUtils.getCatchMessage(theException));

		logger.warning("Trying to logout the client disconnected");
		/* Log out the player (it will be removed from the game also)*/
		try {
			logout(getPlayerToken());
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (InvalidTokenException e) {

			e.printStackTrace();
		}

		closeDown();
		RMIManager.getInstance().requestClientHandlerClosure(this);
		
	}

	/* {@inheritDoc}
	 * @see communication.ClientHandler#changeTurn(java.lang.String)
	 * 
	 */
	@Override
	public void changeTurn(String theUsername) {
		getLogger().info("Sending via RMI the turn change. Next player is "+theUsername);
		try {
			client.handleChangeTurn(theUsername);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return;
	}

}
