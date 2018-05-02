package communication;

import gui.ServerMainWindow;

import java.net.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.logging.Logger;
import java.io.*;

import javax.swing.JTextPane;

import util.StackTraceUtils;

import logging.HTMLFormatter;
import logging.Loggable;
import logging.TextPaneHandler;

public class SocketManager extends ConnectionManager implements Loggable {
	
	private static Logger logger;
	static protected final SocketManager uniqueInstance = new SocketManager();
	static ServerSocket serverSocket = null; 
	static Socket clientSocket = null;
	
	public static SocketManager getInstance() {
		return uniqueInstance;
	}
	
	private SocketManager() {
		clientHandlerList = new ArrayList<ClientHandler>();
		setupLogger();
	}
	
	public void run() {
		
		while(true) {
			System.out.flush();
			while (canAcceptConnections) {
				try { 
					newClientHandler(serverSocket.accept());					
				}
				catch (IOException e) {  
					if (canAcceptConnections) {
						logger.warning("Accept failed");
						logger.warning("CATCH: "+StackTraceUtils.getStackTrace(e));						
					}
					else {
						logger.info("Accept interrupted by the server shutdown procedure");
					}
				}
			}
		}	
	}

	public void startUp() throws IOException {
		try {
			setPort(ServerMainWindow.getInstance().getSocketPort());
			logger.info("Opening the server socket");
			serverSocket = new ServerSocket(getPort());
			canAcceptConnections = true;
			logger.info("Opened the server socket properly on port:" + getPort());
		}
		catch (IOException e) { 
			logger.severe("Can't bind the Server Socket to port: " + getPort());
			logger.severe("CATCH: "+StackTraceUtils.getStackTrace(e));
		}
	}

	public void closeDown() {
		
		getLogger().info("Stopping the socket manager");
		
		canAcceptConnections = false;
		
		getLogger().info("Starting to close the socket client handlers");
		for (ClientHandler clientHandler : clientHandlerList)
		{
			getLogger().info("Forcing to closing created client handler");
			clientHandler.closeDown();
			
			try {
				getLogger().info("Now send an interrupt to the client handler");
			clientHandler.getThread().interrupt();
			
				try {
					getLogger().info("Waiting for the client handler thread to close");
					clientHandler.getThread().join();
					getLogger().info("Client handler thread closed");
				}
				catch (InterruptedException e) {
					getLogger().info("Interrupted manually by the server");
				}
				
			} catch (ConcurrentModificationException e) {
				getLogger().severe(StackTraceUtils.getStackTrace(e));
			}
			
		}
		
		getLogger().info("Cleaning the client handler thread list");
		clientHandlerList.clear();
		
		/* HOW TO CLEAN THE INTERRUPTED THREADS? */ 
		
		try {
			getLogger().info("Closing the server socket");
			serverSocket.close();	
			getLogger().info("Closed the server socket properly");
		}
		catch(IOException e) {
			getLogger().warning("Didn't close correctly the socket server");
			getLogger().warning("CATCH: "+StackTraceUtils.getStackTrace(e));
		}
		
		getLogger().info("Server shutdown ended succesfully");
	}

	public int getClientHandlerNumber()	{
		return clientHandlerList.size();
	}

	// It returns a new Socket Client Handler object
	protected SocketClientHandler newClientHandler(Socket theSocket) {
		SocketClientHandler clientHandler;
		try {
			clientHandler = new SocketClientHandler(theSocket);
		}
		catch(IOException e) {
			logger.severe("Error when creating a new client handler.");
			logger.severe("CATCH: "+StackTraceUtils.getStackTrace(e));
			return null;
		}
		
		addClientHandler(clientHandler);
		Thread serverThread = new Thread(clientHandler);
		clientHandler.setThread(serverThread);
		clientHandler.getThread().start();
		return clientHandler;
	}
	
	public static void setupJdkLoggerHandler(JTextPane theLogJTextPane) {
			
		TextPaneHandler tph = new TextPaneHandler();
		tph.setTextPane(theLogJTextPane);
		tph.setFormatter(new HTMLFormatter());
		logger.addHandler(tph);
		
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
		logger = Logger.getLogger("server.manager");
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
	

}
