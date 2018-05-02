/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;

import util.Position;
import util.StackTraceUtils;
import beans.DinosaurStatus;
import beans.DinosaursList;
import beans.Highscore;
import beans.LocalView;
import beans.MainMap;
import beans.NewBornID;
import beans.PlayersList;

import commands.responses.ChangeTurnResponse;
import commands.responses.ChangeTurnResponseFactory;
import commands.responses.CreateSpeciesResponse;
import commands.responses.CreateSpeciesResponseFactory;
import commands.responses.CreateUserResponse;
import commands.responses.CreateUserResponseFactory;
import commands.responses.DinosaurStatusResponse;
import commands.responses.DinosaurStatusResponseFactory;
import commands.responses.DinosaursListResponse;
import commands.responses.DinosaursListResponseFactory;
import commands.responses.EnterGameResponse;
import commands.responses.EnterGameResponseFactory;
import commands.responses.HighscoreResponse;
import commands.responses.HighscoreResponseFactory;
import commands.responses.GrowDinosaurResponse;
import commands.responses.GrowDinosaurResponseFactory;
import commands.responses.LayEggResponse;
import commands.responses.LayEggResponseFactory;
import commands.responses.LeaveGameResponse;
import commands.responses.LeaveGameResponseFactory;
import commands.responses.LocalViewResponse;
import commands.responses.LocalViewResponseFactory;
import commands.responses.LoginResponse;
import commands.responses.LoginResponseFactory;
import commands.responses.LogoutResponse;
import commands.responses.LogoutResponseFactory;
import commands.responses.MainMapResponse;
import commands.responses.MainMapResponseFactory;
import commands.responses.MoveDinosaurResponse;
import commands.responses.MoveDinosaurResponseFactory;
import commands.responses.PlayersListResponse;
import commands.responses.PlayersListResponseFactory;
import commands.responses.Response;
import commands.responses.ResponseFactory;
import commands.responses.TurnResponse;
import commands.responses.TurnResponseFactory;

import exceptions.FightLostException;
import exceptions.FightWonException;
import exceptions.GameAccessDeniedException;
import exceptions.GameLeavingDeniedException;
import exceptions.IdentifierNotPresentException;
import exceptions.IllegalResponseException;
import exceptions.InvalidDestinationException;
import exceptions.InvalidDinosaurIDException;
import exceptions.InvalidTokenException;
import exceptions.MaxInGamePlayersExceededException;
import exceptions.MaxMovesLimitException;
import exceptions.MaxSizeReachedException;
import exceptions.NameAlreadyTakenException;
import exceptions.NegativeResponseException;
import exceptions.PasswordMismatchException;
import exceptions.PlayerNotInGameException;
import exceptions.SpeciesAlreadyCreatedException;
import exceptions.SpeciesIsFullException;
import exceptions.SpeciesNotCreatedException;
import exceptions.StarvationDeathException;
import exceptions.TurnNotOwnedByPlayerException;

/**
 * <b>Overview</b><br>
 * <p>
 * The SocketServerHandler class is an concrete class that handle the
 * communication with the server using sockets. Most of its methods implements
 * the method of the parent class.
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
public class SocketServerHandler extends ServerHandler {

	private Logger logger;

	static Socket clientSocket = null;
	private InputStreamReader inputStream;
	private OutputStreamWriter outputStream;
	private BufferedReader in;
	private BufferedWriter out;

	boolean receiveMessage = false;
	boolean isRunning = true;

	private ArrayList<String> incomingMessages;
	Object requestLock = new Object();

	public static final SocketServerHandler uniqueInstance = new SocketServerHandler();

	public static SocketServerHandler getInstance() {
		return uniqueInstance;
	}

	/**
	 * 
	 * 
	 * @since May 29, 2011@11:46:38 PM
	 */
	public SocketServerHandler() {
		super();
		setupLogger();

		incomingMessages = new ArrayList<String>();
	}

	/**
	 * @return the isRunning
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * @param isRunning
	 *            the isRunning to set
	 */
	private void setRunning(boolean theValue) {
		isRunning = theValue;
	}

	/**
	 * @return the incomingMessage
	 */
	public String getIncomingMessage() {
		if (incomingMessages.size() > 0)
			return incomingMessages.get(0);
		else
			return null;
	}

	/**
	 * @param incomingMessage
	 *            the incomingMessage to set
	 */
	public void addIncomingMessage(String theString) {
		incomingMessages.add(theString);
	}

	/* {@inheritDoc}
	 * @see communication.ServerHandler#connect()
	 * 
	 */
	protected final void connect() throws IOException {

		String theHost = Client.getIpHost();
		int thePort = Client.getPort();

		try {
			clientSocket = new Socket(theHost, thePort);
			logger.info("Socket connection initiated correctly with host: "
					+ theHost + " on port: " + thePort);
		} catch (IOException e) {
			IOException ex = new IOException("Can't initiate a connection on "
					+ theHost + ":" + thePort);
			logger.warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		try {
			inputStream = new InputStreamReader(clientSocket.getInputStream());
		} catch (IOException e) {
			IOException ex = new IOException(
					"Can't get the input stream from the socket");
			logger.warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		in = new BufferedReader(inputStream);

		try {
			outputStream = new OutputStreamWriter(
					clientSocket.getOutputStream());
		} catch (IOException e) {
			IOException ex = new IOException(
					"Can't get the output stream from the socket");
			logger.warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		out = new BufferedWriter(outputStream);

		setServerDown(false);
	}

	/* {@inheritDoc}
	 * @see communication.ServerHandler#disconnect()
	 * 
	 */
	public void disconnect() {

		logger.info("Finalizing the socket server handler");
		Client.setToken(null);

		try {
			clientSocket.close();
			logger.info("The socket is closed");
		} catch (IOException e) {
			logger.severe("Can't close the socket");
			e.printStackTrace();
		}

		setServerDown(true);
		logger.info("Finalized the socket server handler");
	}

	/* {@inheritDoc}
	 * @see communication.ServerHandler#login(java.lang.String, java.lang.String)
	 * 
	 */
	public void login(String theUsername, String thePassword)
			throws ConnectException, TimeoutException, AuthenticationException,
			IllegalResponseException, PasswordMismatchException,
			RemoteException {

		try {
			connect();
		} catch (IOException e) {
			throw new ConnectException("Can't connect to the server");
		}

		getLogger().info("Started the socket client handler LISTENER");
		setReceiveMessage(true);
		setRunning(true);

		if (getInstance().getState() == Thread.State.NEW) {
			getInstance().start();
		}

		LoginResponse response;

		try {

			response = (LoginResponse) sendRequestAndWaitResponse(
					"@login,user=" + theUsername + ",pass=" + thePassword,
					LoginResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the login request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}
		if (response.isOutcomePositive() == true) {
			Client.setToken(response.getToken());
			return;
		} else {
			if (response.isAuthenticationFailed() == true) {
				setRunning(false);
				setReceiveMessage(false);
				getInstance().interrupt();
				PasswordMismatchException ex = new PasswordMismatchException(
						"The given password doesn't match the user password or the user: \""
								+ theUsername + "\" does not exist.");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				disconnect();
				throw ex;
			} else {
				setRunning(false);
				setReceiveMessage(false);
				getInstance().interrupt();
				AuthenticationException ex = new AuthenticationException(
						"The server cannot login the user");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				disconnect();
				throw ex;
			}
		}
	}

	/** 
	 * The method isReceiveMessage returns if the client can receive message
	 * or not.
	 *
	 * @return true if the client can receive message from the server, false if it can't         
	 *
	 * @see GameManager
	 *  
	 */
	public boolean isReceiveMessage() {
		return receiveMessage;
	}

	/** 
	 * The method setReceiveMessage set a boolean value that inform about
	 * the client possibility to receive messages from the server.
	 *
	 * @param theValue if it is setted to true, the client can receive message, else it can't
	 *
	 * @see GameManager
	 * 
	 */
	public void setReceiveMessage(boolean theValue) {
		receiveMessage = theValue;
	}

	/** 
	 * The method handleConnectionError tries to shut down the connection between
	 * the client and the server without blocking the client. If something goes
	 * wrong, an exception is occurred.
	 *
	 * @return an exception that can be thrown elsewhere
	 * @throws RemoteException if something goes wrong when trying to shut down safely the connection       
	 *
	 * @see GameManager
	 * 
	 */
	private RemoteException handleConnectionError() throws RemoteException {

		logger.severe("Error in the communication with the server. Now disconnecting.");
		disconnect();
		return new RemoteException();

	}

	/* {@inheritDoc}
	 * @see java.lang.Thread#run()
	 * 
	 */
	public void run() {

		incomingMessages.clear();

		String readedLine = "";

		while (true) {

			logger.finest("");

			while (isRunning) {

				logger.info("Server listener is running");

				while (receiveMessage) {

					try {

						logger.info("Server listener is trying to listen a message");

						logger.info("Input is ready. Preparing to readLine()");
						readedLine = "";
						readedLine = in.readLine();

						if (readedLine == null) {

							setRunning(false);
							setReceiveMessage(false);
							handleServerDisconnection(new IOException());
						}

						logger.info("Input is ready. Incoming message: "
								+ readedLine);

						ChangeTurnResponse response = null;
						try {
							response = ChangeTurnResponseFactory.getInstance()
									.createResponse(readedLine);
						} catch (IllegalArgumentException e) {
						}

						if (response != null) {

							logger.warning("Received CHANGE TURN message.");
							logger.warning("the next player to play is: "
									+ response.getUsername());

							handleChangeTurn(response.getUsername());

						} else {
							getLogger()
									.info("A not change turn method arrived! Notify all!");

							synchronized (requestLock) {
								addIncomingMessage(readedLine);
								requestLock.notifyAll();
							}
							getLogger().info(
									"Notified all of the incoming request");

						}
					}

					catch (IOException e) {
						logger.warning("Connection error with the server. Disconnecting.");
						handleServerDisconnection(e);
					}
				}

				incomingMessages.clear();
			}
		}
	}

	/* {@inheritDoc}
	 * @see communication.ServerHandler#register(java.lang.String, java.lang.String)
	 * 
	 */
	public void register(String theUsername, String thePassword)
			throws ConnectException, TimeoutException,
			IllegalResponseException, NameAlreadyTakenException,
			RemoteException {

		try {
			connect();
		} catch (IOException e) {
			throw new ConnectException("Can't connect to the server");
		}

		String lineRead = "";
		int timeout = 1000 * 10;
		int timeDelayed = 0;

		try {

			String commandToBeSent = "@creaUtente,user=" + theUsername
					+ ",pass=" + thePassword;
			logger.info("Sending: " + commandToBeSent);
			write(commandToBeSent);
			logger.info("Command sent.");

			logger.info("Waiting for a registration reply");

			while (!inputStream.ready()) {

				try {
					logger.finest("Waited: " + timeDelayed + "ms");
					Thread.sleep(500);
					timeDelayed += 500;

					if (timeDelayed > timeout)
						throw new TimeoutException();
				} catch (TimeoutException e) {

					handleTimeout();

				} catch (InterruptedException e) {
					disconnect();
					logger.warning("Waiting interrupted manually");
				}
			}

			logger.info("Input is ready. Reading the input.");
			lineRead = in.readLine();

		} catch (IOException e) {
			throw handleConnectionError();
		}

		logger.info("Received a server response: " + lineRead
				+ ". Parsing the server response");

		disconnect();

		CreateUserResponse response = null;
		try {
			response = CreateUserResponseFactory.getInstance().createResponse(
					lineRead);
		} catch (IllegalArgumentException e) {
			IllegalResponseException ex = new IllegalResponseException(
					"The server responded with a nonsense message");
			getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		if (response.isOutcomePositive() == true) {
			return;
		} else {
			if (response.isUsernameAlreadyTaken() == true) {
				NameAlreadyTakenException ex = new NameAlreadyTakenException(
						"The username the client wanted to register is already taken");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}

			IllegalResponseException ex = new IllegalResponseException(
					"The server cannot register the client");
			getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

	}

	/** 
	 * The method handleTimeout handle a connection timeout between the client and the
	 * server trying to shutdown the connection safely.
	 *
	 * @throws TimeoutException is always throwed in order to alert the client that a timeout occurred
	 *
	 * 
	 */
	public void handleTimeout() throws TimeoutException {

		logger.warning("Waiting interrupted by timeout. Disconnecting the socket server handler.");
		disconnect();
		TimeoutException ex = new TimeoutException();
		logger.warning("THROWN: Waiting interrupted by timeout");
		logger.warning(StackTraceUtils.getStackTrace(ex));
		throw ex;

	}

	/* {@inheritDoc}
	 * @see communication.ServerHandler#enterGame()
	 * 
	 */
	public void enterGame() throws TimeoutException, IllegalResponseException,
			MaxInGamePlayersExceededException, SpeciesNotCreatedException,
			InvalidTokenException, GameAccessDeniedException, RemoteException {

		EnterGameResponse response;
		try {

			response = (EnterGameResponse) sendRequestAndWaitResponse(
					"@accessoPartita,token=" + Client.getToken(),
					EnterGameResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the enter game request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isTooManyPlayers() == true) {
				MaxInGamePlayersExceededException ex = new MaxInGamePlayersExceededException(
						"The maximum amount of players in game has been reached");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isSpeciesNotCreated() == true) {
				SpeciesNotCreatedException ex = new SpeciesNotCreatedException(
						"The player has not created a species yet");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isInvalidToken() == true) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;

			} else {
				GameAccessDeniedException ex = new GameAccessDeniedException(
						"The server cannot let the user enter the game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		}
	}

	/** 
	 * The method sendRequestAndWaitResponse makes a request to the server setting
	 * a default timeout for the response of 10 seconds. For more information, see
	 * {@link SocketServerHandler#sendRequestAndWaitResponse(String, ResponseFactory, int)}
	 *
	 * @param theRequest see
	 * {@link SocketServerHandler#sendRequestAndWaitResponse(String, ResponseFactory, int)}
	 * @param theResponseFactory see
	 * {@link SocketServerHandler#sendRequestAndWaitResponse(String, ResponseFactory, int)}
	 * @return see
	 * {@link SocketServerHandler#sendRequestAndWaitResponse(String, ResponseFactory, int)}
	 * @throws TimeoutException see
	 * {@link SocketServerHandler#sendRequestAndWaitResponse(String, ResponseFactory, int)}
	 * @throws IOException see
	 * {@link SocketServerHandler#sendRequestAndWaitResponse(String, ResponseFactory, int)}         
	 * 
	 */
	private Response sendRequestAndWaitResponse(String theRequest,
			ResponseFactory theResponseFactory) throws TimeoutException,
			IOException {
		return sendRequestAndWaitResponse(theRequest, theResponseFactory,
				1000 * 10);
	}

	/** 
	 * The method sendRequestAndWaitResponse makes a request to the server setting
	 * a default timeout for the response of 10 seconds. After having sent a request to
	 * the server, the method set the thread that invoked it in {@link Thread#wait()}. When the server
	 * will send a message response, the {@link SocketServerHandler#run()} method will catch the message
	 * and will notify all the thread that was in waiting state like this that a new message is arrived.
	 * Then the method awakes and try to understand if the server message is a valid response
	 * for the request made or not. If it is not valid, sets itself again in a waiting state
	 * calling the function {@link Thread#wait()} waiting for another message; instead, if it's valid,
	 * it will generate a {@link Response} object that contains information about the 
	 * server response that finally is returned to the caller. 
	 *
	 * @param theRequest is a string that represents a request to the server
	 * @param theResponseFactory is an object that can check if a server response received by
	 * the client can be a valid response for the request made or not
	 * @param theTimeout is a time value in milliseconds that the client must wait before give up trying to receive a response from the server
	 * @return an object that contains information about the server response (if any) or null if there was no response.
	 * @throws TimeoutException if the server can't manage to sent a valid response in {@code theTimeout} milliseconds 
	 * @throws IOException if there is a connection error while trying to send the request         
	 * 
	 */
	private Response sendRequestAndWaitResponse(String theRequest,
			ResponseFactory theResponseFactory, int theTimeout)
			throws TimeoutException, IOException {

		Response response = null;

		synchronized (requestLock) {

			getLogger().info("Write the request: " + theRequest);
			write(theRequest);

			getLogger().info("Entered in a synchronized lock");

			while (response == null) {

				try {
					getLogger().info(
							"Starting to wait() for a response to the request: "
									+ theRequest);
					requestLock.wait();
				} catch (InterruptedException e) {
				}

				if (isRunning == false) {
					logger.warning("Waiting interrupted by served disconnection. Disconnecting the socket server handler.");
					RemoteException ex = new RemoteException();
					logger.warning("THROWN: Waiting interrupted by RemoteException");
					logger.warning(StackTraceUtils.getStackTrace(ex));
					throw ex;
				}

				getLogger().info(
						"WAKEN UP by NOTIFY ALL! Try to catch a response to the request: "
								+ theRequest);

				try {
					response = theResponseFactory
							.createGeneralResponse(getIncomingMessage());
				} catch (IllegalArgumentException e) {

					getLogger().warning(StackTraceUtils.getCatchMessage(e));

				}

			}
			getLogger().severe(
					"Incoming messages in queue before del: "
							+ incomingMessages.size());
			incomingMessages.remove(0);
			getLogger().severe(
					"Incoming messages in queue after del: "
							+ incomingMessages.size());
		}

		return response;
	}

	/* {@inheritDoc}
	 * @see communication.ServerHandler#logout()
	 * 
	 */
	public void logout() throws TimeoutException, IllegalResponseException,
			InvalidTokenException, IllegalResponseException, RemoteException {

		LogoutResponse response;

		try {

			response = (LogoutResponse) sendRequestAndWaitResponse(
					"@logout,token=" + Client.getToken(),
					LogoutResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the logout request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken() == true) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				IllegalResponseException ex = new IllegalResponseException(
						"The server cannot let the user log out");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#createSpecies(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void createSpecies(String theSpeciesName, String theSpeciesType)
			throws TimeoutException, IllegalResponseException,
			NameAlreadyTakenException, SpeciesAlreadyCreatedException,
			InvalidTokenException, RemoteException {

		CreateSpeciesResponse response;
		try {

			response = (CreateSpeciesResponse) sendRequestAndWaitResponse(
					"@creaRazza,token=" + Client.getToken() + ",nome="
							+ theSpeciesName + ",tipo=" + theSpeciesType,
					CreateSpeciesResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the create species request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isNameAlreadyTaken()) {
				NameAlreadyTakenException ex = new NameAlreadyTakenException(
						"The name of the species is already taken");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isSpeciesAlreadyCreated()) {
				SpeciesAlreadyCreatedException ex = new SpeciesAlreadyCreatedException(
						"The player has already created a species");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				IllegalResponseException ex = new IllegalResponseException(
						"The server cannot let the player create a new species");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#playersInGameList()
	 */
	@Override
	public PlayersList getInGamePlayersList() throws RemoteException,
			TimeoutException, InvalidTokenException, IllegalResponseException,
			NegativeResponseException {

		PlayersListResponse response;

		try {

			response = (PlayersListResponse) sendRequestAndWaitResponse(
					"@listaGiocatori,token=" + Client.getToken(),
					PlayersListResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the player list request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				NegativeResponseException ex = new NegativeResponseException(
						"The server cannot give the user the in game players list");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		} else {
			return response.getPlayerInGameList();
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#getHighscore()
	 */
	@Override
	public Highscore getHighscore() throws RemoteException, TimeoutException,
			InvalidTokenException, NegativeResponseException {

		HighscoreResponse response;

		try {

			response = (HighscoreResponse) sendRequestAndWaitResponse(
					"@classifica,token=" + Client.getToken(),
					HighscoreResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the player list request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				NegativeResponseException ex = new NegativeResponseException(
						"The server cannot give the user the highscore");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		} else {
			return response.getHighscore();
		}

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#getMainMap()
	 */
	@Override
	public MainMap getMainMap() throws RemoteException, TimeoutException,
			IllegalResponseException, InvalidTokenException,
			PlayerNotInGameException, NegativeResponseException {

		MainMapResponse response;
		try {

			response = (MainMapResponse) sendRequestAndWaitResponse(
					"@mappaGenerale,token=" + Client.getToken(),
					MainMapResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the main map request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isPlayerNotInGame()) {
				PlayerNotInGameException ex = new PlayerNotInGameException(
						"The player is not in game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				NegativeResponseException ex = new NegativeResponseException(
						"The server cannot give the user the main map");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		} else {
			return response.getMainMap();
		}

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#getLocalView()
	 */
	@Override
	public LocalView getLocalView(String theDinosaurID) throws RemoteException,
			TimeoutException, InvalidDinosaurIDException,
			InvalidTokenException, PlayerNotInGameException,
			NegativeResponseException {

		LocalViewResponse response;
		try {

			response = (LocalViewResponse) sendRequestAndWaitResponse(
					"@vistaLocale,token=" + Client.getToken() + ",idDino="
							+ theDinosaurID,
					LocalViewResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the local view request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isPlayerNotInGame()) {
				PlayerNotInGameException ex = new PlayerNotInGameException(
						"The player is not in game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isInvalidDinosaurID()) {
				InvalidDinosaurIDException ex = new InvalidDinosaurIDException(
						"The dinosaur id is invalid");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				NegativeResponseException ex = new NegativeResponseException(
						"The server cannot give the user the local view");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		} else {
			return response.getLocalView();
		}

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#getDinosaurStatus(java.lang.String)
	 */
	@Override
	public DinosaurStatus getDinosaurStatus(String theDinosaurID)
			throws RemoteException, TimeoutException,
			InvalidDinosaurIDException, InvalidTokenException,
			PlayerNotInGameException, NegativeResponseException {

		DinosaurStatusResponse response;
		try {

			response = (DinosaurStatusResponse) sendRequestAndWaitResponse(
					"@statoDinosauro,token=" + Client.getToken() + ",idDino="
							+ theDinosaurID,
					DinosaurStatusResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the dinosaur status request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isPlayerNotInGame()) {
				PlayerNotInGameException ex = new PlayerNotInGameException(
						"The player is not in game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isInvalidDinosaurID()) {
				InvalidDinosaurIDException ex = new InvalidDinosaurIDException(
						"The dinosaur id is invalid");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				NegativeResponseException ex = new NegativeResponseException(
						"The server cannot give the user the dinosaur status");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		} else {
			return response.getDinosaurStatus();
		}
	}

	/* {@inheritDoc}
	 * @see communication.ServerHandler#handleServerDisconnection(java.io.IOException)
	 * 
	 */
	protected void handleServerDisconnection(IOException theException) {

		getLogger().log(Level.WARNING, "The server disconnected", theException);
		getLogger().warning(StackTraceUtils.getStackTrace(theException));
		disconnect();

	}

	/** 
	 * The method write writes on the socket a string.
	 *
	 * @param theString is the string to be written
	 * @throws IOException if an error occurs when writing
	 * 
	 */
	void write(String theString) throws IOException {
		try {
			out.write(theString + "\n");
			out.flush();
		} catch (SocketException e) {
			logger.severe("The server disconnected");
			disconnect();
			logger.info("Closing the socket thread");
			return;
		}

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see logging.Loggable#setupLogger()
	 */
	@Override
	public void setupLogger() {

		logger = Logger.getLogger("client.serverhandler.socketserverhandler");
		logger.setParent(Logger.getLogger("client.main"));
		logger.setLevel(Level.ALL);
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
	 * @see communication.ServerHandler#leaveGame()
	 */
	@Override
	public void leaveGame() throws RemoteException, TimeoutException,
			IllegalResponseException, PlayerNotInGameException,
			InvalidTokenException, GameLeavingDeniedException {

		LeaveGameResponse response;

		try {

			response = (LeaveGameResponse) sendRequestAndWaitResponse(
					"@uscitaPartita,token=" + Client.getToken(),
					LeaveGameResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the leave game request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == true) {
			return;
		} else {
			if (response.isInvalidToken() == true) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;

			} else {
				GameLeavingDeniedException ex = new GameLeavingDeniedException(
						"The server cannot let the user leave the game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		}

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#dinosaursInGameList()
	 */
	@Override
	public DinosaursList getDinosaursInGameList() throws RemoteException,
			TimeoutException, IllegalResponseException, InvalidTokenException,
			NegativeResponseException, PlayerNotInGameException {

		DinosaursListResponse response;
		try {

			response = (DinosaursListResponse) sendRequestAndWaitResponse(
					"@listaDinosauri,token=" + Client.getToken(),
					DinosaursListResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the dinosaur list request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isPlayerNotInGameFlag()) {
				PlayerNotInGameException ex = new PlayerNotInGameException(
						"The player is not in game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				NegativeResponseException ex = new NegativeResponseException(
						"The server cannot give the user the in game dinosaurs list");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		} else {
			return response.getDinosaursInGameList();
		}

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#confirmTurn()
	 */
	@Override
	public void confirmTurn() throws RemoteException, TimeoutException,
			IllegalResponseException, InvalidTokenException,
			NegativeResponseException, PlayerNotInGameException {

		TurnResponse response;
		try {

			response = (TurnResponse) sendRequestAndWaitResponse(
					"@confermaTurno,token=" + Client.getToken(),
					TurnResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the confirm turn request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isPlayerNotInGameFlag()) {
				PlayerNotInGameException ex = new PlayerNotInGameException(
						"The player is not in game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isTurnNotOwnedByThePlayerFlag()) {
				TurnNotOwnedByPlayerException ex = new TurnNotOwnedByPlayerException(
						"This is not the player's turn");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				NegativeResponseException ex = new NegativeResponseException(
						"The server cannot give the user the possibility to confirm turn");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		} else {
			return;
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#confirmTurn()
	 */
	@Override
	public void passTurn() throws RemoteException, TimeoutException,
			IllegalResponseException, InvalidTokenException,
			NegativeResponseException, PlayerNotInGameException {

		TurnResponse response;
		try {

			response = (TurnResponse) sendRequestAndWaitResponse(
					"@passaTurno,token=" + Client.getToken(),
					TurnResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the pass turn request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isPlayerNotInGameFlag()) {
				PlayerNotInGameException ex = new PlayerNotInGameException(
						"The player is not in game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isTurnNotOwnedByThePlayerFlag()) {
				TurnNotOwnedByPlayerException ex = new TurnNotOwnedByPlayerException(
						"This is not the player's turn");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				NegativeResponseException ex = new NegativeResponseException(
						"The server cannot give the user the possibility to pass turn");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		} else {
			return;
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#growDinosaur(java.lang.String)
	 */
	@Override
	public void growDinosaur(String theDinosaurID) throws RemoteException,
			TimeoutException, InvalidDinosaurIDException,
			MaxSizeReachedException, PlayerNotInGameException,
			TurnNotOwnedByPlayerException, MaxMovesLimitException,
			InvalidTokenException, StarvationDeathException {

		GrowDinosaurResponse response;
		try {

			response = (GrowDinosaurResponse) sendRequestAndWaitResponse(
					"@cresciDinosauro,token=" + Client.getToken() + ",idDino="
							+ theDinosaurID,
					GrowDinosaurResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the dinosaur growth request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isInvalidDinosaurID()) {
				InvalidDinosaurIDException ex = new InvalidDinosaurIDException(
						"Invalid dinosaur ID");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isMaxSizeReached()) {
				MaxSizeReachedException ex = new MaxSizeReachedException(
						"The dinosaur has already reached its maximum size");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isMaxMovesLimit()) {
				MaxMovesLimitException ex = new MaxMovesLimitException(
						"The dinosaur can't grow anymore in this turn");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isStarvationDeath()) {
				StarvationDeathException ex = new StarvationDeathException(
						"The dinosaur died because he hadn't got enough energy to grow up");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isTurnNotOwnedByPlayer()) {
				TurnNotOwnedByPlayerException ex = new TurnNotOwnedByPlayerException(
						"The current turn does not belong to the Player");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isPlayerNotInGame()) {
				PlayerNotInGameException ex = new PlayerNotInGameException(
						"The player is not in game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				IllegalResponseException ex = new IllegalResponseException(
						"The server cannot let the dinosaur grow up");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#moveDinosaur(java.lang.String,
	 * util.Position)
	 */
	@Override
	public void moveDinosaur(String theDinosaurID, Position theDestination)
			throws RemoteException, TimeoutException,
			IdentifierNotPresentException, StarvationDeathException,
			InvalidDestinationException, PlayerNotInGameException,
			TurnNotOwnedByPlayerException, MaxMovesLimitException,
			FightLostException, FightWonException {

		MoveDinosaurResponse response;

		try {

			response = (MoveDinosaurResponse) sendRequestAndWaitResponse(
					"@muoviDinosauro,token=" + Client.getToken() + ",idDino="
							+ theDinosaurID + ",dest={"
							+ theDestination.getIntX() + ","
							+ theDestination.getIntY() + "}",
					MoveDinosaurResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the dinosaur movement request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isInvalidDinosaurID()) {
				InvalidDinosaurIDException ex = new InvalidDinosaurIDException(
						"Invalid dinosaur ID");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isInvalidDestination()) {
				InvalidDestinationException ex = new InvalidDestinationException(
						"The destination is not valid");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isMaxMovesLimit()) {
				MaxMovesLimitException ex = new MaxMovesLimitException(
						"The dinosaur can't move anymore in this turn");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isStarvationDeath()) {
				StarvationDeathException ex = new StarvationDeathException(
						"The dinosaur died because he hadn't got enough energy to move around");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isTurnNotOwnedByPlayer()) {
				TurnNotOwnedByPlayerException ex = new TurnNotOwnedByPlayerException(
						"The current turn does not belong to the Player");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isPlayerNotInGame()) {
				PlayerNotInGameException ex = new PlayerNotInGameException(
						"The player is not in game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				IllegalResponseException ex = new IllegalResponseException(
						"The server cannot let the dinosaur move");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		} else {
			if (response.isFightEncountered()) {
				if (response.isFightWon()) {
					FightWonException ex = new FightWonException(
							"Your dinosaur won the fight");
					getLogger().warning(StackTraceUtils.getThrowMessage(ex));
					throw ex;
				} else {
					FightLostException ex = new FightLostException(
							"Your dinosaur lost the fight");
					getLogger().warning(StackTraceUtils.getThrowMessage(ex));
					throw ex;
				}
			}
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ServerHandler#layEgg(java.lang.String)
	 */
	@Override
	public NewBornID layEgg(String theDinosaurID) throws RemoteException,
			TimeoutException, InvalidDinosaurIDException,
			SpeciesIsFullException, PlayerNotInGameException,
			TurnNotOwnedByPlayerException, MaxMovesLimitException,
			InvalidTokenException {

		LayEggResponse response;
		try {

			response = (LayEggResponse) sendRequestAndWaitResponse(
					"@deponiUovo,token=" + Client.getToken() + ",idDino="
							+ theDinosaurID,
					LayEggResponseFactory.getInstance());

			getLogger()
					.info("Waiting a message response for the egg deposition request arrived!");

		} catch (IOException e) {
			throw handleConnectionError();
		}

		if (response.isOutcomePositive() == false) {
			if (response.isInvalidToken()) {
				InvalidTokenException ex = new InvalidTokenException(
						"Invalid Token");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isInvalidDinosaurID()) {
				InvalidDinosaurIDException ex = new InvalidDinosaurIDException(
						"Invalid dinosaur ID");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isSpeciesFull()) {
				SpeciesIsFullException ex = new SpeciesIsFullException(
						"The dinosaur's species is full");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isMaxMovesLimit()) {
				MaxMovesLimitException ex = new MaxMovesLimitException(
						"The dinosaur has already reached its maximum number of available moves in this turn");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isStarvationDeath()) {
				StarvationDeathException ex = new StarvationDeathException(
						"The dinosaur died because he hadn't got enough energy to lay an egg");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isTurnNotOwnedByPlayer()) {
				TurnNotOwnedByPlayerException ex = new TurnNotOwnedByPlayerException(
						"The current turn does not belong to the Player");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else if (response.isPlayerNotInGame()) {
				PlayerNotInGameException ex = new PlayerNotInGameException(
						"The player is not in game");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} else {
				IllegalResponseException ex = new IllegalResponseException(
						"The server cannot let the dinosaur lay an egg");
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
		} else {
			return response.getNewBornID();
		}
	}

	/* {@inheritDoc}
	 * @see communication.ClientInterface#ping()
	 * 
	 */
	@Override
	public void ping() throws RemoteException {

		//This functions is never called by the server using socket connectivity.
		
	}
}