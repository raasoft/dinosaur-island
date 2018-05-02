package communication;

import gui.ServerMainWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import logging.Loggable;


public abstract class ConnectionManager extends Thread implements Loggable {

	// uniqueInstance set to null because the ConnectionManager class is abstract
	protected Logger logger;
	static boolean canAcceptConnections = false;
	static protected int port;
	protected ArrayList<ClientHandler> clientHandlerList;

	// abstract method to start the Connection Manager
	abstract void startUp() throws IOException;

	// abstract method to stop the Connection Manager
	public abstract void closeDown();

	// It returns the port number the Connection Manager is listening on
	public static int getPort() {
		return port;
	}
	
	// It sets the port number the Connection Manager is listening on
	// throws IllegalArgumentException if the specified port number is smaller than 1024
	public static void setPort(int thePort) throws IllegalArgumentException {
		if (thePort <= 1024) {
			throw new IllegalArgumentException("Port number must not be a value below 1024");
		}
		port = thePort;
	}

	// abstract method to return the active connections
	public abstract int getClientHandlerNumber();
	
	public void addClientHandler(ClientHandler theClientHandler) {
		clientHandlerList.add(theClientHandler);
		
	}
	
	public void requestClientHandlerClosure(ClientHandler theClientHandler) {
		
		getLogger().info("Requested closure from client: " + theClientHandler);
		
		if (clientHandlerList.contains(theClientHandler) == true) {
			
			clientHandlerList.remove(theClientHandler);
			theClientHandler.getThread().interrupt();
			
		}
		
		ServerMainWindow.getInstance().updateLoggedPlayersValue();
		ServerMainWindow.getInstance().updateInGamePlayersValue();
		
		getLogger().info("Closure of client: " + theClientHandler + " executed succesfully");
		
	}

}
