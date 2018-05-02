/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;

import javax.naming.AuthenticationException;

import logging.Loggable;
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
 * The ServerHandler class is an abstract class that handle the communication
 * with the server. Most of its methods are to be overridden in its subclasses,
 * since them define the communication type (i.e. the subclass
 * {@link SocketServerHandler} handles the socket communication with the server,
 * while the {@link RMIServerHandler} handles the RMI communication with the
 * server).
 * </p>
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * This class must give the client the possibility to make requests to the
 * server and obtain responses regardless the effective connection method
 * (socket or RMI).<br>
 * For this reason, it must define the legal requests that the client can
 * perform and must store some information about the server status or
 * notifications.
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * This class is called very often by the client GUI, so its collaborators are
 * {@link ServerMainWindow}, {@link CreationSpeciesDialog},
 * {@link RegistrationDialog}, {@link WaitingRoomWindow}, and
 * {@link GameManager} and in general by all the classes that needs to
 * communicate with the server regardless the type of communication used.
 * </p>
 * 
 * @author RAA
 * @version 1.0
 */
public abstract class ServerHandler extends Thread implements ClientInterface,
		Loggable {

	/**
	 * This variable is true when a change turn notification arrives from the
	 * server.
	 */
	protected boolean changingTurn = false;

	/**
	 * This variable contains the player that is owning the turn.
	 */
	protected String thePlayerThatIsOwningTheTurn;

	/**
	 * This variable is true when the client detects connection problems with
	 * the server.
	 */
	protected boolean isServerDown = false;

	/**
	 * The method confirmTurn request the server to let the player confirm
	 * his/her turn. If the request can be accepted and processed, the function
	 * ends normally without any return value, otherwise, an exception is
	 * thrown.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws NegativeResponseException
	 *             if the request simply cannot be accepted
	 * @throws PlayerNotInGameException
	 *             if the player is not in game
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void confirmTurn() throws RemoteException,
			TimeoutException, InvalidTokenException, NegativeResponseException,
			PlayerNotInGameException;

	/**
	 * The method connect tries to establish a connection with the server. If
	 * the connection can be accepted and processed, the function ends normally
	 * without any return value, otherwise, an exception is thrown.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws IOExceptio
	 *             if there a connection error
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	protected abstract void connect() throws RemoteException, IOException;

	/**
	 * The method createSpecies request the server to let the player create a
	 * new species. If the request can be accepted and processed, the function
	 * ends normally without any return value, otherwise, an exception is
	 * thrown.
	 * 
	 * @param theSpeciesName
	 *            is the name of the species the user wants to create. It must
	 *            be an alphanumeric character
	 * @param theSpeciesType
	 *            is the type of the species the user wants to create. It must
	 *            be "e" for a herbivore species or "c" for a carnivorous
	 *            species.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws NameAlreadyTakenException
	 *             if there is already one species created by the user with the
	 *             same name
	 * @throws SpeciesAlreadyCreatedException
	 *             if the player already has one species (only one species could
	 *             be owned at time)
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void createSpecies(String theSpeciesName,
			String theSpeciesType) throws RemoteException, TimeoutException,
			NameAlreadyTakenException, SpeciesAlreadyCreatedException,
			InvalidTokenException;

	/**
	 * The method connect tries to safely break a connection with the server. In
	 * every case, the method doesn't throw exception, neither if there are some
	 * connection problems (because if there were the client could not do
	 * anything to repair them).
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void disconnect();

	/**
	 * The method enterGame request the server to let the player enter a game.
	 * If the request can be accepted and processed, the function ends normally
	 * without any return value, otherwise, an exception is thrown.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws MaxInGamePlayersExceededException
	 *             if the game has already reached its maximum number of players
	 * @throws SpeciesNotCreatedException
	 *             if the user wants to enter the game without owning any
	 *             species
	 * @throws GameAccessDeniedException
	 *             if the server cannot let the user enter for an unspecified
	 *             reason
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void enterGame() throws RemoteException, TimeoutException,
			MaxInGamePlayersExceededException, SpeciesNotCreatedException,
			InvalidTokenException, GameAccessDeniedException;

	/**
	 * The method getDinosaursInGameList request the server for a list of
	 * dinosaurs owned by the client player. If the request can be accepted and
	 * processed, the function ends normally without any return value,
	 * otherwise, an exception is thrown.
	 * 
	 * @return a {@link DinosaurList} bean that contains the dinosaurs owned by
	 *         the client player
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws NegativeResponseException
	 *             if the request cannot be accomplished due to a non specified
	 *             reason
	 * @throws PlayerNotInGameException
	 *             if the player that is requesting the dinosaur list is not
	 *             gui.ingame
	 * 
	 * @see DinosaurList
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract DinosaursList getDinosaursInGameList()
			throws RemoteException, TimeoutException, InvalidTokenException,
			NegativeResponseException, PlayerNotInGameException;

	/**
	 * The method getDinosaurStatus request the server for a detailed
	 * information about a dinosaur. If the request can be accepted and
	 * processed, the function ends normally without any return value,
	 * otherwise, an exception is thrown.
	 * 
	 * @param theDinosaurID
	 *            is the ID of the dinosaur
	 * @return a {@link DinosaurStatus} bean that contains the dinosaur detailed
	 *         information requested
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws InvalidDinosaurIDException
	 *             if the ID of the dinosaur is not valid
	 * @throws PlayerNotInGameException
	 *             if the player is not in game
	 * @throws NegativeResponseException
	 *             if the request cannot be accomplished due to a non specified
	 *             reason
	 * 
	 * @see DinosaurStatus
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract DinosaurStatus getDinosaurStatus(String theDinosaurID)
			throws RemoteException, TimeoutException,
			InvalidDinosaurIDException, InvalidTokenException,
			PlayerNotInGameException, NegativeResponseException;

	/**
	 * The method getHighscore request the server for the game highscore. If the
	 * request can be accepted and processed, the function ends normally without
	 * any return value, otherwise, an exception is thrown.
	 * 
	 * @return a {@link Highscore} bean that contains the highscore of the game
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws NegativeResponseException
	 *             if the request cannot be accomplished due to a non specified
	 *             reason
	 * 
	 * @see Highscore
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract Highscore getHighscore() throws RemoteException,
			TimeoutException, InvalidTokenException, NegativeResponseException;

	/**
	 * The method getInGamePlayersList request the server for a list of players
	 * that are currently gui.ingame. If the request can be accepted and
	 * processed, the function ends normally without any return value,
	 * otherwise, an exception is thrown.
	 * 
	 * @return a {@link PlayersList} bean that contains a list of players that
	 *         are gui.ingame
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws NegativeResponseException
	 *             if the request cannot be accomplished due to a non specified
	 *             reason
	 * 
	 * @see PlayersList
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract PlayersList getInGamePlayersList() throws RemoteException,
			TimeoutException, InvalidTokenException, NegativeResponseException;

	/**
	 * The method getLocalView request the server for a list of players that are
	 * currently gui.ingame. If the request can be accepted and processed, the
	 * function ends normally without any return value, otherwise, an exception
	 * is thrown.
	 * 
	 * 
	 * @return a {@link LocalView} bean that contains the information about the
	 *         dinosaur local view
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws NegativeResponseException
	 *             if the request cannot be accomplished due to a non specified
	 *             reason
	 * @throws InvalidDinosaurIDException
	 *             if the ID of the dinosaur is not valid
	 * @throws PlayerNotInGameException
	 *             if the player is not gui.ingame
	 * 
	 * @see LocalView
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract LocalView getLocalView(String theDinosaurID)
			throws RemoteException, TimeoutException,
			InvalidDinosaurIDException, InvalidTokenException,
			PlayerNotInGameException, NegativeResponseException;

	/**
	 * The method getMainMap request the server for information about the cells
	 * already discovered by a dinosaur species. If the request can be accepted
	 * and processed, the function ends normally without any return value,
	 * otherwise, an exception is thrown.
	 * 
	 * @return a {@link MainMap} bean that contains the requested information
	 *         about the map
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws NegativeResponseException
	 *             if the request cannot be accomplished due to a non specified
	 *             reason
	 * @throws PlayerNotInGameException
	 *             if the player is not gui.ingame
	 * 
	 * @see MainMap
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract MainMap getMainMap() throws RemoteException,
			TimeoutException, InvalidTokenException, PlayerNotInGameException,
			NegativeResponseException;

	/**
	 * The method getPlayerThatIsOwningTheTurn returns the player that is owning
	 * the turn.
	 * 
	 * @return the username of the player requested
	 * 
	 * @see GameManager
	 * 
	 */
	public String getPlayerThatIsOwningTheTurn() {
		return thePlayerThatIsOwningTheTurn;
	}

	/**
	 * The method growDinosaur requests the server to let a dinosaur grow in
	 * size. If the request can be accepted and processed, the function ends
	 * normally without any return value, otherwise, an exception is thrown.
	 * 
	 * @param theDinosaurID
	 *            is the ID of the dinosaur to let grow
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws NegativeResponseException
	 *             if the request cannot be accomplished due to a non specified
	 *             reason
	 * @throws PlayerNotInGameException
	 *             if the player is not gui.ingame
	 * @throws InvalidDinosaurIDException
	 *             if the dinosaur ID given is not valid
	 * @throws MaxSizeReachedException
	 *             if the dinosaur has already reached its maximum size
	 * @throws TurnNotOwnedByPlayerException
	 *             if the player is not owning the current turn
	 * @throws MaxMovesLimitException
	 *             if the dinosaur has already reached its maximum number of
	 *             moves per turn
	 * @throws StarvationDeathException
	 *             if the dinosaur dies while trying to perform this action
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void growDinosaur(String theDinosaurID)
			throws RemoteException, TimeoutException,
			InvalidDinosaurIDException, MaxSizeReachedException,
			PlayerNotInGameException, TurnNotOwnedByPlayerException,
			MaxMovesLimitException, InvalidTokenException,
			StarvationDeathException;

	/*
	 * {@inheritDoc}
	 * 
	 * @see communication.ClientInterface#handleChangeTurn(java.lang.String)
	 */
	public void handleChangeTurn(String theCurrentPlayerUsername)
			throws RemoteException {
		setPlayerThatIsOwningTheTurn(theCurrentPlayerUsername);
		setChangingTurn(true);
	}

	/**
	 * The method handleServerDisconnection handles the unexpected server
	 * disconnections. It reset all the settings in order to let the
	 * ServerHandler reconnect later with the server.
	 * 
	 * @param theException
	 *            is the exception that has been throwned by a failed operations
	 *            with the server
	 * 
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	protected abstract void handleServerDisconnection(IOException theException);

	/**
	 * The method isChangingTurn returns whether the server has recently
	 * notified a change turn or not.
	 * 
	 * @return true if a change turn was notified recently, false if not
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public boolean isChangingTurn() {
		return changingTurn;
	}

	/**
	 * The method isChangingTurn returns whether the server has disconnected
	 * unexpectedly or not.
	 * 
	 * @return true if the server disconnected unexpectedly or false otherwise.
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public boolean isServerDown() {
		return isServerDown;
	}

	/**
	 * The method layEgg requests the server to let a dinosaur lay an egg. If
	 * the request can be accepted and processed, the function ends normally
	 * without any return value, otherwise, an exception is thrown.
	 * 
	 * @param theDinosaurID
	 *            is the ID of the dinosaur that has to lay an egg.
	 * @return a {@link NewBornID} bean that contains the ID of the newborn
	 *         dinosaur created.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws NegativeResponseException
	 *             if the request cannot be accomplished due to a non specified
	 *             reason
	 * @throws PlayerNotInGameException
	 *             if the player is not gui.ingame
	 * @throws InvalidDinosaurIDException
	 *             if the dinosaur ID given is not valid
	 * @throws SpeciesIsFullException
	 *             if the dinosaur species has already the maximum number of
	 *             dinosaur
	 * @throws TurnNotOwnedByPlayerException
	 *             if the player is not owning the current turn
	 * @throws MaxMovesLimitException
	 *             if the dinosaur has already reached its maximum number of
	 *             moves per turn
	 * @throws StarvationDeathException
	 *             if the dinosaur dies while trying to perform this action
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract NewBornID layEgg(String theDinosaurID)
			throws RemoteException, TimeoutException,
			InvalidDinosaurIDException, SpeciesIsFullException,
			PlayerNotInGameException, TurnNotOwnedByPlayerException,
			MaxMovesLimitException, InvalidTokenException;

	/**
	 * The method leaveGame requests the server to let a client leave the game.
	 * If the request can be accepted and processed, the function ends normally
	 * without any return value, otherwise, an exception is thrown.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws PlayerNotInGameException
	 *             if the player is not gui.ingame
	 * @throws GameLeavingDeniedException
	 *             if the player cannot leave the game due a non specified
	 *             reason
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void leaveGame() throws RemoteException, TimeoutException,
			PlayerNotInGameException, InvalidTokenException,
			GameLeavingDeniedException;

	/**
	 * The method login requests the server to let a client login in the server.
	 * If the request can be accepted and processed, the function ends normally
	 * without any return value, otherwise, an exception is thrown.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws ConnectException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws IllegalResponseException
	 *             if the server send a non valid response
	 * @throws AuthenticationException
	 *             if the client can't login due to a non specified error
	 * @throws PasswordMismatchException
	 *             if the client can't login due to a mismatch between password
	 *             and username
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void login(String theUsername, String thePassword)
			throws RemoteException, ConnectException, TimeoutException,
			AuthenticationException, IllegalResponseException,
			PasswordMismatchException;

	/**
	 * The method logout requests the server to let a client logout out of the
	 * server. If the request can be accepted and processed, the function ends
	 * normally without any return value, otherwise, an exception is thrown.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client can't login due to a non specified error
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void logout() throws RemoteException, TimeoutException,
			InvalidTokenException;

	/**
	 * The method moveDinosaur requests the server to let a dinosaur move around
	 * in the map. If the request can be accepted and processed, the function
	 * ends normally without any return value, otherwise, an exception is
	 * thrown.
	 * 
	 * @param theDinosaurID
	 *            is the ID of the dinosaur that the player wants to move
	 * @param theDestination
	 *            is the final position that the dinosaur has to occupy at the
	 *            end of the action
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws IdentifierNotPresentException
	 *             if the dinosaur ID of the dinosaur that has to be moved is
	 *             not valid
	 * @throws StarvationDeathException
	 *             if the dinosaur dies while trying to make the move
	 * @throws InvalidDestinationException
	 *             if the dinosaur is to be placed on a not valid position
	 * @throws PlayerNotInGameException
	 *             if the player is not gui.ingame
	 * @throws TurnNotOwnedByPlayerException
	 *             if the player is not owning the current turn
	 * @throws MaxMovesLimitException
	 *             if the dinosaur has reached its maximum moves per turn
	 * @throws FightLostException
	 *             if the dinosaur had to fight during the move and has lost the
	 *             fight
	 * @throws FightWonException
	 *             if the dinosaur had to fight during the move and has won the
	 *             fight
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void moveDinosaur(String theDinosaurID,
			Position theDestination) throws RemoteException, TimeoutException,
			IdentifierNotPresentException, StarvationDeathException,
			InvalidDestinationException, PlayerNotInGameException,
			TurnNotOwnedByPlayerException, MaxMovesLimitException,
			FightLostException, FightWonException;

	/**
	 * The method passTurn request the server to let the player pass his/her
	 * turn. If the request can be accepted and processed, the function ends
	 * normally without any return value, otherwise, an exception is thrown.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws NegativeResponseException
	 *             if the request simply cannot be accepted
	 * @throws PlayerNotInGameException
	 *             if the player is not in game
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void passTurn() throws RemoteException, TimeoutException,
			InvalidTokenException, NegativeResponseException,
			PlayerNotInGameException;

	/**
	 * The method register request the server to let the player register a new
	 * game user. If the request can be accepted and processed, the function
	 * ends normally without any return value, otherwise, an exception is
	 * thrown.
	 * 
	 * @throws RemoteException
	 *             if there is a connection error
	 * @throws TimeoutException
	 *             if there is no connection error but the request takes too
	 *             long to be executed
	 * @throws InvalidTokenException
	 *             if the client has an invalid token no more accepted by the
	 *             server
	 * @throws NameAlreadyTakenException
	 *             if the username to be registered is already taken
	 * 
	 * @see RegistrationDialog
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public abstract void register(String theUsername, String thePassword)
			throws RemoteException, ConnectException, TimeoutException,
			NameAlreadyTakenException;

	/**
	 * The method setChangingTurn set whether the server has recently notified a
	 * change turn or not.
	 * 
	 * @param theValue
	 *            is true if a change turn was notified recently, false if not
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public void setChangingTurn(boolean theValue) {
		changingTurn = theValue;
	}

	/**
	 * The method setServerDown set whether the server has disconnected
	 * unexpectedly or not.
	 * 
	 * @param theValue
	 *            is true if a change turn was notified recently, false if not
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public void setServerDown(boolean theValue) {
		isServerDown = theValue;
	}

	/**
	 * The method setPlayerThatIsOwningTheTurn set the username of the player
	 * that is currently owning the turn.
	 * 
	 * @see GameManager
	 * @see SocketServerHandler
	 * @see RMIServerHandler
	 * 
	 */
	public void setPlayerThatIsOwningTheTurn(
			String theNewPlayerThatIsOwningTheTurn) {
		thePlayerThatIsOwningTheTurn = theNewPlayerThatIsOwningTheTurn;
	}

}