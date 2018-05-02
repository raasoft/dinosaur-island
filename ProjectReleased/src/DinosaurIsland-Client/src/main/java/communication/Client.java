/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication;

import gui.ClientMainWindow;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import logging.HTMLFormatter;
import logging.Loggable;

/**
 * <b>Overview</b><br>
 * <p>
 * The class client manages all the information that are needed to let the 
 * DinosaurIsland user to connect successfully with the server.
 *
 * <b>Responsibilities</b><br>
 * <p>
 * The class Client is a Singleton class (can be accessed by the {@link getInstance()} method) 
 * and is responsible to store information like:
 * <ul>
 * <li>The username of the player that wants to play the game
 * <li>IP address of the server
 * <li>The connection type (RMI or Socket)
 * <li>The token that the server assigns the user at the login.
 * </ul>
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * This class only stores information about the client connection.
 * Its information are accessed by a variety of classes, including {@link ServerHandler} and
 * its subclasses, some GUI classes like {@link ClientMainWindow}, {@link WaitingRoomWindow}, 
 * {@link RegistrationDialog} and the {@link GameManager} class.
 * </p>
 * 
 * @author	AXXO
 * @version 1.0
 * @since	May 24, 2011@10:24:49 AM
 *
 */
/**
 * <b>Overview</b><br>
 * <p>
 * Description of the type.
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
 * @author	RAA
 * @version 1.0
 * @since	27/giu/2011@17.55.43
 *
 */
/**
 * <b>Overview</b><br>
 * <p>
 * Description of the type.
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
 * @author	RAA
 * @version 1.0
 * @since	27/giu/2011@17.55.45
 *
 */
/**
 * <b>Overview</b><br>
 * <p>
 * Description of the type. This state information includes:
 * <ul>
 * <li>Element 1
 * <li>Element 2
 * <li>The current element implementation (see <a
 * href="#setXORMode">setXORMode</a>)
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
 * @author RAA
 * @version 1.0
 * @since 27/giu/2011@17.55.51
 * 
 */
public class Client implements Loggable {

	private static Client uniqueInstance;
	private static ServerHandler serverHandler;

	static private String ipHost;
	static private int port;
	static private String token;
	static private String username;

	private static Logger logger;
	private static FileHandler loggerFileHandler;

	/** 
	 * The method getInstance let to access the Client singleton.
	 *
	 * @return a reference to the Client
	 *
	 * @since	May 29, 2011@5:16:41 PM
	 * 
	 */
	public static Client getInstance() {

		if (uniqueInstance == null) {
			uniqueInstance = new Client();
		}
		return uniqueInstance;
	}

	/**
	 * The method getIpHost returns the host IP address to whom the client wants
	 * to connect.
	 * 
	 * 
	 * @return a string containing the IP address of the server (it could be in
	 *         the IPv4 or IPv6 format, it has to be checked manually) or
	 *         {@code null} if there is no such address.
	 * 
	 * 
	 * @see ServerHandler
	 * @see ClientMainWindow
	 *  
	 */
	public static String getIpHost() {
		return ipHost;
	}

	/**
	 * The method getPort returns the host port that the client wants to use to connect
	 * to the server.
	 * 
	 * 
	 * @return a integer representing the port number used by the client server communication. 
	 * 
	 * @see ServerHandler
	 * @see ClientMainWindow
	 * 
	 * 
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * The method getServerHandler returns the current server handler used by
	 * the client to connect to the server (by default the
	 * {@link SocketServerHandler}).
	 * 
	 * @return the reference to the current server handler the client to connect
	 *         to the server (by default the {@link SocketServerHandler})
	 * 
	 * @see RMIServerHandler
	 * @see SocketServerHandler
	 * 
	 * 
	 */
	public static ServerHandler getServerHandler() {
		return serverHandler;
	}

	/**
	 * The method getToken return the current token assigned to the client by
	 * the server at the login used for all of the client server communication.
	 * 
	 * @return a string representing the token given by the server at the login.
	 * 
	 * @see RMIServerHandler
	 * @see SocketServerHandler
	 * 
	 * 
	 */
	public static String getToken() {
		return token;
	}


	/**
	 * The method getUsername returns the username choosen by the client to register
	 * and login into the server.
	 * 
	 * @return a string representing the username of the client
	 * 
	 * @see ServerHandler
	 * 
	 */
	public static String getUsername() {
		return username;
	}

	/**
	 * The method setIpHost set the host IP address to whom the client wants
	 * to connect.
	 * 
	 * 
	 * @param theIpHost string containing the IP address of the server (it could be in
	 *         the IPv4 or IPv6 format, it has to be checked manually) or
	 *         {@code null} if there is no such address.
	 * 
	 * 
	 * @see ServerHandler
	 * @see ClientMainWindow
	 *  
	 */
	public static void setIpHost(String theIpHost) {
		ipHost = theIpHost;
	}

	/**
	 * The method setIpHost set the host port that the client wants to use to connect
	 * to the server.
	 * 
	 * @param theIpHost a integer representing the port number used by the client server communication. 
	 * 
	 * @see ServerHandler
	 * @see ClientMainWindow
	 *  
	 */
	public static void setPort(int thePort) {
		port = thePort;
	}

	/**
	 * The method setServerHandler set the current server handler used by the
	 * client to connect to the server.
	 * 
	 * @param theServerHandler
	 *            the serverHandler to be set as current
	 * 
	 * @return the reference to the current server handler the client to connect
	 *         to the server (by default the {@link SocketServerHandler})
	 * 
	 * @see RMIServerHandler
	 * @see SocketServerHandler
	 * 
	 */
	public static void setServerHandler(ServerHandler theServerHandler) {
		serverHandler = theServerHandler;
	}

	/**
	 * The method setToken is used to set the current token assigned to the
	 * client by the server at the login used for all of the client server
	 * communication.
	 * 
	 * @param theToken
	 *            the token to be set as current
	 * 
	 * @see ServerHandler
	 * 
	 */
	public static void setToken(String theToken) {
		token = theToken;
	}

	/**
	 * The method setUsername is used to set the current username chosen by the
	 * client to register a new user and to login in the server. The username is
	 * chosen using a GUI class in the {@link ClientMainWindow} class.
	 * 
	 * @param theUsername
	 *            the username of the client
	 * 
	 * @see ClientMainWindow
	 * 
	 */
	public static void setUsername(String theUsername) {
		username = theUsername;
	}

	/**
	 * The constructor is private since the class is a singleton.<br>
	 * In the constructor are setted up the class logger and is indicated as the
	 * preferred server handler the socket server handler.<br>
	 * 
	 */
	private Client() {
		setupLogger();
		setServerHandler(SocketServerHandler.getInstance());
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see logging.Loggable#getLogger()
	 */
	@Override
	public Logger getLogger() {
		return logger;
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see logging.Loggable#setupLogger()
	 */
	@Override
	public void setupLogger() {
		
		logger = Logger.getLogger("client.main");
		
		try {
			loggerFileHandler = new FileHandler("TestLog.html", 2048, 1,true);
			loggerFileHandler.setFormatter(new HTMLFormatter());
			logger.addHandler(loggerFileHandler);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}