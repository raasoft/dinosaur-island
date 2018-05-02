package communication;


import exceptions.ServerStartupErrorException;
import gameplay.Game;
import gameplay.highscore.HighscoreManager;
import gameplay.map.Map;
import gameplay.map.MapData;
import gui.ServerMainWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;

import beans.Highscore;
import logging.HTMLFormatter;
import logging.Loggable;
import logging.TextPaneHandler;
import util.StackTraceUtils;


public class Server implements Loggable {

	private static Server uniqueInstance = new Server();
	private int connectionsMax = 10; /* This value is to be changed */
	private ArrayList<ConnectionManager> connectionManagerList;
	
	private static Logger logger;
	
	
	public void setConnectionsMax(int theValue) {
		connectionsMax = theValue;
	}
	
	private Server() {
		
		setupLogger();
		
		Game.getInstance();

		connectionManagerList = new ArrayList<ConnectionManager>(2);
		
		SocketManager socketManager = SocketManager.getInstance();
		socketManager.setDaemon(true);
		socketManager.start();
		connectionManagerList.add(socketManager);

		RMIManager rmiManager = RMIManager.getInstance();
		rmiManager.setDaemon(true);
		rmiManager.start();
		connectionManagerList.add(rmiManager);
		
	}

	// Server is a Singleton
	public static Server getInstance()	{
		return uniqueInstance;
		
	}

	public void start() throws ServerStartupErrorException, IOException 	{
		
		loadState();
		
		setConnectionsMax(ServerMainWindow.getInstance().getMaxConnections());
		
		for (ConnectionManager manager: connectionManagerList)
		{
			try {
				manager.startUp();
			}
			catch (IOException e)
			{
				getLogger().info("Can't start the " + manager + " connection manager");
				throw e;
			}
		}
		ServerMainWindow.getInstance().getStopServerButton().setEnabled(true);
	}

	public void stop() {
		getLogger().info("Starting the server stop procedure");
		
		getLogger().info("Closing the connection managers");
		for (ConnectionManager manager : connectionManagerList) {
			manager.closeDown();
		}

		getLogger().info("Stopping the game");
		Game.getInstance().stopGame();
		
		getLogger().info("Saving the server state");
		saveState();

	}

	// It returns the maximum number of active connections
	public int getConnectionsMax() {
		return connectionsMax;
	}
	
	private void saveState() {

		writeObject(PlayerManager.saveData(), "players.dat");
		writeObject(Map.getInstance().saveData(), "map.dat");
		writeObject(HighscoreManager.getInstance().saveData(), "scores.dat");		
		
	}
	
	private void loadState() throws ServerStartupErrorException {
		
		String fileName;
		ObjectInputStream input;
		
		fileName = "players.dat";
		input = null;
		boolean playersDatabaseLoaded = true;
		
		try {
			input = new ObjectInputStream( new FileInputStream( fileName ) );
			
			try {
				PlayersData playersData = (PlayersData) input.readObject();
				PlayerManager.loadData(playersData);
			}
			catch (IOException e) {
				getLogger().severe("Error reading the file. Can't read the players state.");
				throw e;
			}
			catch (ClassNotFoundException e) {
				getLogger().severe("ClassNotFoundException: cannot locate the class where load the readed data");
				playersDatabaseLoaded = false;
			}
			
		}
		catch (IOException e) {
			
			if ((new File(fileName)).exists()) {
				getLogger().severe("Error opening file. Can't load the players state.");
			}
			else {
				getLogger().info("There is no players data file. Starting server with a empty player list.");
			}
			
			playersDatabaseLoaded = false;
			
			PlayersData playersData = new PlayersData();
			PlayerManager.loadData(playersData);
		}
		
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				getLogger().warning("Error closing the input file.");
				getLogger().warning(StackTraceUtils.getCatchMessage(e));
			}
		}
		
		
		
		fileName = "map.dat";
		input = null;
		
		try {
			input = new ObjectInputStream( new FileInputStream( fileName ) );
			
			try {
				MapData mapData = (MapData) input.readObject();
				Map.getInstance().loadData(mapData);
			}
			catch (IOException e) {
				getLogger().severe("Error reading the file. Can't read the map state.");
				throw e;
			}
			catch (ClassNotFoundException e) {
				getLogger().severe("ClassNotFoundException: cannot locate the class where load the readed data");
				System.exit(1);
			}
			
		}
		catch (IOException e) {
			
			if ((new File(fileName)).exists()) {
				getLogger().severe("Error opening file. Can't load the map state.");
			}
			else {
				getLogger().warning("There is no map data file.");
			}
			
			if (playersDatabaseLoaded == true) {
				ServerStartupErrorException exception = new ServerStartupErrorException("Can't start the game if there is a player database to resume but not the map.");
				getLogger().severe(StackTraceUtils.getThrowMessage(exception));
				System.exit(1);
			} else {
				Map.getInstance().createRandomMap();
			}
		}
		
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				getLogger().warning("Error closing the input file.");
				getLogger().warning(StackTraceUtils.getCatchMessage(e));
			}
		}
		
		
		fileName = "scores.dat";
		input = null;
		
		try {
			input = new ObjectInputStream( new FileInputStream( fileName ) );
			
			try {
				Highscore highscoreData = (Highscore) input.readObject();
				HighscoreManager.getInstance().loadData(highscoreData);
			}
			catch (IOException e) {
				getLogger().severe("Error reading the file. Can't read the highscore state.");
				throw e;
			}
			catch (ClassNotFoundException e) {
				getLogger().severe("ClassNotFoundException: cannot locate the class where load the readed data");
				System.exit(1);
			}
			
		}
		catch (IOException e) {
			
			if ((new File(fileName)).exists()) {
				getLogger().severe("Error opening file. Can't load the highscore state.");
			}
			else {
				getLogger().warning("There is no map data file.");
			}
			HighscoreManager.getInstance().loadData(new Highscore());
			
		}
		
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				getLogger().warning("Error closing the input file.");
				getLogger().warning(StackTraceUtils.getCatchMessage(e));
			}
		}
		
		
	}

	public int getActiveCommunications() {
		return SocketManager.getInstance().getClientHandlerNumber() + RMIManager.getInstance().getClientHandlerNumber(); 
}
	
	public static void setupJdkLoggerHandler(JTextPane theLogJTextPane) {
			
		TextPaneHandler tph = new TextPaneHandler();
		tph.setTextPane(theLogJTextPane);
		tph.setFormatter(new HTMLFormatter());
		logger.addHandler(tph);
		
	}
	
	private void writeObject(Object theObject, String fileName) {

		ObjectOutputStream output;
		output = null;
		
		try {
			output = new ObjectOutputStream( new FileOutputStream( fileName ) );
			
			try {
				output.writeObject(theObject);
			}
			catch (IOException e) {
				getLogger().severe("Error writing to the file. Can't save the "+fileName);
				getLogger().warning(StackTraceUtils.getCatchMessage(e));
			}
			
		}
		catch (IOException e) {
			getLogger().severe("Error opening file. Can't save the "+fileName);
			getLogger().warning("CATCH: "+StackTraceUtils.getStackTrace(e));
		}
		
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				getLogger().warning("Error closing the output file.");
				getLogger().warning("CATCH: "+StackTraceUtils.getStackTrace(e));
			}
		}
		
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
		
		logger = Logger.getLogger("server.main");
		logger.setLevel(Level.ALL);
		
		FileHandler fh;
		try {
			fh = new FileHandler("TestLog.html", 2048, 1,true);
			fh.setFormatter(new HTMLFormatter());
		    logger.addHandler(fh);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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


