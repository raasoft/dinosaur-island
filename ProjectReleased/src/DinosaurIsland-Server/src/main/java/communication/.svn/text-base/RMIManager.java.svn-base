package communication;

import gui.ServerMainWindow;

import java.io.File;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.logging.Logger;

import logging.Loggable;
import util.StackTraceUtils;


public class RMIManager extends ConnectionManager implements Loggable, ServerAccept {
	
	static protected RMIManager uniqueInstance;
	static protected ServerAccept stub;
	
	private static final long serialVersionUID = 1L;

	private static String url;
	private static String host;
	
	private Registry registry;
	private int registryPort;
	private Object lock = null;
	
	private RMIManager() {
		clientHandlerList = new ArrayList<ClientHandler>();
		lock = new Object();
		setupLogger();
	}
	
	public static RMIManager getInstance() {
		
		if (uniqueInstance == null) {
			uniqueInstance = new RMIManager();
		}
	
		
		return uniqueInstance;
	}
	
	public void run() {
		
		while(true) {
			
			while (canAcceptConnections) {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void startUp()
	{
		try {
			logger.info("Locating the RMI registry");
			setPort(ServerMainWindow.getInstance().getRMIPort());
			
			File javaPolicyFile = new File("java.policy");
			String path = javaPolicyFile.getAbsolutePath();
			System.setProperty("java.security.policy", path);
			
			if (registryPort != port) {
				registry = LocateRegistry.createRegistry(port);
				registryPort = port;
			}
			try {
				stub = (ServerAccept) UnicastRemoteObject.exportObject(uniqueInstance, port);
			} catch (ExportException e) {
				logger.warning(e.getMessage());
			} catch (RemoteException e) {
				logger.severe(StackTraceUtils.getThrowMessage(e));
			}  
			host = ServerMainWindow.getInstance().getIPAddress();
			url = "rmi://" + host + ":" + port + "/server";
			
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());

			registry.rebind(url, stub);

			canAcceptConnections = true;
			logger.info("Registry located properly on host:" + host + ":" + port);
		} catch (RemoteException e) {
			logger.severe("Can't locate the Server Registy on host: " + host + ":" + port);
			logger.severe("CATCH: "+StackTraceUtils.getStackTrace(e)); 
		}
	}

	public void closeDown() 
	{
		getLogger().info("Stopping the RMI manager");
		
		try {
			registry.unbind(url);
			UnicastRemoteObject.unexportObject(stub, true);
		} catch (AccessException e) {
			logger.warning(e.getMessage());
		} catch (RemoteException e) {
			logger.warning(e.getMessage());
		} catch (NotBoundException e) {
			logger.warning(e.getMessage());
		} 
			
		canAcceptConnections = false;
		
		getLogger().info("Starting to close the RMI client handlers");
		for (ClientHandler clientHandler : clientHandlerList)
		{
			getLogger().info("Closing created client handler");
			
			try {

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
				getLogger().severe("CAUGHT: "+StackTraceUtils.getStackTrace(e));
			}
			
		}
		
		getLogger().info("Cleaning the client handler thread list");
		clientHandlerList.clear();
		
		getLogger().info("Server shutdown ended succesfully");
	}

	public int getClientHandlerNumber() {
		return clientHandlerList.size();
	}

	/**
	 * It returns a new Server Interface for the Client
	 */
	public ServerInterface connect(ClientInterface theClient) throws RemoteException {
		RMIClientHandler clientHandler;
		
		clientHandler = new RMIClientHandler(theClient);
			
		synchronized (lock) {
			addClientHandler(clientHandler);
		}
		Thread serverThread = new Thread(clientHandler);
		ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(clientHandler, port);
		clientHandler.setThread(serverThread);
		clientHandler.getThread().start();
		return stub;
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
