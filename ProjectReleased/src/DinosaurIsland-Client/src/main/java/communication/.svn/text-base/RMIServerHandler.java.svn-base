/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;

import util.Position;
import beans.DinosaurStatus;
import beans.DinosaursList;
import beans.Highscore;
import beans.LocalView;
import beans.MainMap;
import beans.NewBornID;
import beans.PlayersList;
import exceptions.FightLostException;
import exceptions.FightWonException;
import exceptions.GameAccessDeniedException;
import exceptions.GameLeavingDeniedException;
import exceptions.IdentifierNotPresentException;
import exceptions.InvalidDestinationException;
import exceptions.InvalidDinosaurIDException;
import exceptions.InvalidTokenException;
import exceptions.MaxInGamePlayersExceededException;
import exceptions.MaxMovesLimitException;
import exceptions.MaxSizeReachedException;
import exceptions.NameAlreadyTakenException;
import exceptions.NegativeResponseException;
import exceptions.PlayerNotInGameException;
import exceptions.SpeciesAlreadyCreatedException;
import exceptions.SpeciesIsFullException;
import exceptions.SpeciesNotCreatedException;
import exceptions.StarvationDeathException;
import exceptions.TurnNotOwnedByPlayerException;

/**
 * <b>Overview</b><br>
 * <p>
 * The RMIServerHandler class is an concrete class that handle the communication
 * with the server using the Remote Method Invocation. 
 * Most of its methods implements the method of the parent class.
 * </p>
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * This class must give the client the possibility to make requests to the
 * server and obtain responses.<br>
 * For this reason, it must define the legal requests that the client can
 * perform and must store some information about the server status or
 * notifications and implements them.
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * This class is called very often by the client GUI, so its collaborators are
 * {@link ClientMainWindow}, {@link CreationSpeciesDialog},
 * {@link RegistrationDialog}, {@link WaitingRoomWindow}, and
 * {@link GameManager} and in general by all the classes that needs to
 * communicate with the server.
 * </p>
 * 
 * @author RAA
 * @version 1.0
 */
public class RMIServerHandler extends ServerHandler {

	private Logger logger;
	
	private static RMIServerHandler uniqueInstance = new RMIServerHandler();
	
	private static ServerInterface server;
	private static ClientInterface stub;

	// remote class bind addresses
	private static String url;

	static public RMIServerHandler getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new RMIServerHandler();
		}

		return uniqueInstance;
	}

	public RMIServerHandler() {
		super();
		setupLogger();
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#confirmTurn()
	 */
	@Override
	final public void confirmTurn() throws RemoteException, TimeoutException,
			InvalidTokenException, NegativeResponseException,
			PlayerNotInGameException {

		logger.info("Confirm turn method invokated");
		server.confirmTurn(Client.getToken());

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#connect()
	 */
	final public void connect() throws RemoteException {

		String host = Client.getIpHost();
		int port = Client.getPort();
		url = "rmi://" + host + ":" + port + "/server";

		File javaPolicyFile = new File("java.policy");
		String path = javaPolicyFile.getAbsolutePath();
		System.setProperty("java.security.policy", path);

		try {
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());

			Registry registry = LocateRegistry.getRegistry(host, port);
			logger.info("Registry located corectly on host: " + host + ":"
					+ port);
			try {
				stub = (ClientInterface) UnicastRemoteObject.exportObject(
						uniqueInstance, 0);
				logger.info("Client stub exported correctly");
			} catch (ExportException e) {
				logger.warning(e.getMessage());
			}
			try {
				ServerAccept serverStub = (ServerAccept) registry.lookup(url);
				logger.info("Server stub looked up correctly on " + url);
				server = serverStub.connect(stub);
				logger.info("RMI connection established");
			} catch (NotBoundException e) {
				getLogger().severe("Client exception: " + e.getMessage());
				e.printStackTrace();
				throw e;
			}
		} catch (RemoteException e) {
			getLogger().severe("Client exception: " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			getLogger().severe("Client exception: " + e.getMessage());
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}

		setServerDown(false);
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#createSpecies(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	final public void createSpecies(String theSpeciesName, String theSpeciesType)
			throws RemoteException, TimeoutException,
			NameAlreadyTakenException, SpeciesAlreadyCreatedException,
			InvalidTokenException {

		logger.info("Create species method invokated");
		server.createSpecies(Client.getToken(), theSpeciesName, theSpeciesType);

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#disconnect()
	 */
	@Override
	final public void disconnect() {
		try {
			UnicastRemoteObject.unexportObject(stub, true);
		} catch (NoSuchObjectException e) {
			logger.warning(e.getMessage());
		}

		setServerDown(true);

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#enterGame()
	 */
	@Override
	final public void enterGame() throws RemoteException, TimeoutException,
			MaxInGamePlayersExceededException, SpeciesNotCreatedException,
			InvalidTokenException, GameAccessDeniedException {

		logger.info("Enter game method invokated");
		server.enterGame(Client.getToken());

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#getDinosaursInGameList()
	 */
	@Override
	final public DinosaursList getDinosaursInGameList() throws RemoteException,
			TimeoutException, InvalidTokenException, NegativeResponseException,
			PlayerNotInGameException {

		logger.info("Get dinosaur list method invokated");
		return server.getInGameDinosaursList(Client.getToken());

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#getDinosaurStatus(java.lang.String)
	 */
	@Override
	final public DinosaurStatus getDinosaurStatus(String theDinosaurID)
			throws RemoteException, TimeoutException,
			InvalidDinosaurIDException, InvalidTokenException,
			PlayerNotInGameException, NegativeResponseException {

		logger.info("Get dinosaur status method invokated");
		return server.getDinosaurStatus(Client.getToken(), theDinosaurID);

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#getHighscore()
	 */
	@Override
	final public Highscore getHighscore() throws RemoteException,
			TimeoutException, InvalidTokenException, NegativeResponseException {

		logger.info("Get highscore method invokated");
		return server.getHighscore(Client.getToken());

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#getInGamePlayersList()
	 */
	@Override
	final public PlayersList getInGamePlayersList() throws RemoteException,
			TimeoutException, InvalidTokenException, NegativeResponseException {

		logger.info("Get player list method invokated");
		return server.getInGamePlayersList(Client.getToken());

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#getLocalView(java.lang.String)
	 */
	@Override
	final public LocalView getLocalView(String theDinosaurID)
			throws RemoteException, TimeoutException,
			InvalidDinosaurIDException, InvalidTokenException,
			PlayerNotInGameException, NegativeResponseException {

		logger.info("Get local view method invokated");
		return server.getLocalView(Client.getToken(), theDinosaurID);

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
	 * @see communication.ServerHandler#getMainMap()
	 */
	@Override
	final public MainMap getMainMap() throws RemoteException, TimeoutException,
			InvalidTokenException, PlayerNotInGameException,
			NegativeResponseException {

		logger.info("Get main map method invokated");
		return server.getMainMap(Client.getToken());

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#growDinosaur(java.lang.String)
	 */
	@Override
	final public void growDinosaur(String theDinosaurID)
			throws RemoteException, TimeoutException,
			InvalidDinosaurIDException, MaxSizeReachedException,
			PlayerNotInGameException, TurnNotOwnedByPlayerException,
			MaxMovesLimitException, InvalidTokenException,
			StarvationDeathException {

		logger.info("Grow dinosaur method invokated");
		server.growDinosaur(Client.getToken(), theDinosaurID);

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see
	 * communication.ServerHandler#handleServerDisconnection(java.io.IOException
	 * )
	 */
	@Override
	final protected void handleServerDisconnection(IOException theException) {
		disconnect();
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#layEgg(java.lang.String)
	 */
	@Override
	final public NewBornID layEgg(String theDinosaurID) throws RemoteException,
			TimeoutException, InvalidDinosaurIDException,
			SpeciesIsFullException, PlayerNotInGameException,
			TurnNotOwnedByPlayerException, MaxMovesLimitException,
			InvalidTokenException {

		logger.info("Lay egg method invokated");
		return server.layEgg(Client.getToken(), theDinosaurID);

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#leaveGame()
	 */
	@Override
	final public void leaveGame() throws RemoteException, TimeoutException,
			PlayerNotInGameException, InvalidTokenException,
			GameLeavingDeniedException {

		logger.info("Leave game method invokated");
		server.leaveGame(Client.getToken());

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#login(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	final public void login(String theUsername, String thePassword)
			throws RemoteException, ConnectException, TimeoutException,
			AuthenticationException, IllegalArgumentException {

		connect();
		logger.info("Login method invokated");
		Client.setToken(server.loginPlayer(theUsername, thePassword));

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#logout()
	 */
	@Override
	final public void logout() throws RemoteException, TimeoutException,
			InvalidTokenException {

		logger.info("Logout method invokated");
		server.logout(Client.getToken());

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#moveDinosaur(java.lang.String,
	 * util.Position)
	 */
	@Override
	final public void moveDinosaur(String theDinosaurID, Position theDestination)
			throws RemoteException, TimeoutException,
			IdentifierNotPresentException, StarvationDeathException,
			InvalidDestinationException, PlayerNotInGameException,
			TurnNotOwnedByPlayerException, MaxMovesLimitException,
			FightLostException, FightWonException {

		logger.info("Move dinosaur method invokated");
		server.moveDinosaur(Client.getToken(), theDinosaurID, theDestination);
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#passTurn()
	 */
	@Override
	final public void passTurn() throws RemoteException, TimeoutException,
			InvalidTokenException, NegativeResponseException,
			PlayerNotInGameException {

		logger.info("Pass turn method invokated");
		server.passTurn(Client.getToken());
		logger.info("Pass turn method ended");

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#register(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	final public void register(String theUsername, String thePassword)
			throws RemoteException, ConnectException, TimeoutException,
			NameAlreadyTakenException {

		connect();
		logger.info("Register method invokated");
		server.createUser(theUsername, thePassword);
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see logging.Loggable#setupLogger()
	 */
	@Override
	public void setupLogger() {

		logger = Logger.getLogger("client.serverhandler.rmiserverhandler");
		logger.setParent(Logger.getLogger("client.main"));
		logger.setLevel(Level.ALL);

	}

	/* {@inheritDoc}
	 * @see communication.ClientInterface#ping()
	 * 
	 */
	@Override
	public void ping() throws RemoteException {
		
		//This functions is called only to check connectivity. It is meant to do nothing else.
		
	}

}
