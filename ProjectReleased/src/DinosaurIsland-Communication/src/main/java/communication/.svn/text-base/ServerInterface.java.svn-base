package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

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
import exceptions.InvalidDestinationException;
import exceptions.InvalidDinosaurIDException;
import exceptions.InvalidTokenException;
import exceptions.MaxInGamePlayersExceededException;
import exceptions.MaxMovesLimitException;
import exceptions.MaxSizeReachedException;
import exceptions.NameAlreadyTakenException;
import exceptions.NegativeResponseException;
import exceptions.NotFoundException;
import exceptions.PasswordMismatchException;
import exceptions.PlayerAlreadyInGameException;
import exceptions.PlayerNotInGameException;
import exceptions.SpeciesAlreadyCreatedException;
import exceptions.SpeciesIsFullException;
import exceptions.SpeciesNotCreatedException;
import exceptions.StarvationDeathException;
import exceptions.TurnNotOwnedByPlayerException;


/**
 * <b>Overview</b><br>
 * <p>
 * This is the interface of the server: it shows the kind of actions that a
 * server can handle.
 * </p>
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * Since the game logic is in the server, the client can make request that affect
 * the game logic using this methods specified here.
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * This interface is implemenented in {@link ClientHandler} in the Server part
 * of the project and its methods are called by the class {@link ServerHandler}
 * in the Client in order to let the client interact with the game logic of the server.
 * </p>
 * 
 * @author RAA
 * @version 1.0
 * 
 */
public interface ServerInterface extends Remote {
	
	/**
	 * Is the time that the server leaves the clients to finalize their operations (in milliseconds)
	 */
	static final int TURN_FINALIZATION_DELAY = 500;
	/**
	 * Is the time that the server leaves the clients to confirm their turn (in milliseconds)
	 */
	static final int TURN_CONFIRMATION_DELAY = 30000 + TURN_FINALIZATION_DELAY; 
	/**
	 * Is the time that the server leaves the clients to execute actions during their turn (in milliseconds)
	 */
	static final int TURN_EXECUTION_DELAY = 120000 + TURN_FINALIZATION_DELAY;

	public void createUser(String theUsername, String thePassword) 
			throws RemoteException, IllegalArgumentException, 
			NameAlreadyTakenException;
	
	public String loginPlayer(String theUsername, String thePassword) 
			throws RemoteException, AuthenticationException, 
			NullPointerException, PasswordMismatchException, 
			IllegalArgumentException, NotFoundException;
	
	public void createSpecies(String theToken, String theSpeciesName, String theSpeciesType) 
			throws RemoteException, IllegalArgumentException,
			NameAlreadyTakenException, SpeciesAlreadyCreatedException,
			InvalidTokenException;
	
	public void logout(String theToken) 
			throws RemoteException, InvalidTokenException;
	
	public void canEnterGame(String theToken) 
	throws RemoteException, InvalidTokenException, 
	IllegalArgumentException, PlayerAlreadyInGameException, 
	SpeciesNotCreatedException, MaxInGamePlayersExceededException;
	
	public void enterGame(String theToken) 
			throws RemoteException, InvalidTokenException, 
			IllegalArgumentException, PlayerAlreadyInGameException, 
			SpeciesNotCreatedException, MaxInGamePlayersExceededException;
	
	public void leaveGame(String theToken) 
			throws RemoteException, InvalidTokenException, 
			IllegalArgumentException, PlayerNotInGameException;
	
	public PlayersList getInGamePlayersList(String theToken) 
			throws RemoteException, InvalidTokenException;
	
	public Highscore getHighscore(String theToken) 
			throws RemoteException, InvalidTokenException;
	
	public void moveDinosaur(String theToken, String theDinosaurID, Position theDestination)
			throws RemoteException, InvalidDinosaurIDException, 
			InvalidDestinationException, MaxMovesLimitException, 
			StarvationDeathException, TurnNotOwnedByPlayerException, 
			PlayerNotInGameException, FightWonException,
			FightLostException, InvalidTokenException;
	
	public void growDinosaur(String theToken, String theDinosaurID)
		throws RemoteException, InvalidDinosaurIDException, 
		MaxMovesLimitException, MaxSizeReachedException, 
		StarvationDeathException, TurnNotOwnedByPlayerException, 
		PlayerNotInGameException, InvalidTokenException;
	
	public NewBornID layEgg(String theToken, String theDinosaurID)
		throws RemoteException, InvalidDinosaurIDException, 
		MaxMovesLimitException, SpeciesIsFullException, 
		StarvationDeathException, TurnNotOwnedByPlayerException, 
		PlayerNotInGameException, InvalidTokenException;
	
	public LocalView getLocalView(String theToken, String theDinosaurID) 
		throws RemoteException, InvalidTokenException,
		InvalidDinosaurIDException, PlayerNotInGameException,  
		NegativeResponseException;
	
	public MainMap getMainMap(String theToken)
			throws RemoteException, InvalidTokenException,
			PlayerNotInGameException;
	
	public DinosaurStatus getDinosaurStatus(String theToken, String theDinosaurID) 
		throws RemoteException, InvalidTokenException,
		InvalidDinosaurIDException, PlayerNotInGameException,  
		NegativeResponseException;
	
	public DinosaursList getInGameDinosaursList(String theToken)
			throws RemoteException, InvalidTokenException,
			PlayerNotInGameException;
	
	public void confirmTurn(String theToken)
			throws RemoteException, InvalidTokenException,
			PlayerNotInGameException, TurnNotOwnedByPlayerException,
			NegativeResponseException;
	
	public void canPassTurn(String theToken) 
			throws RemoteException, InvalidTokenException, 
			PlayerNotInGameException, TurnNotOwnedByPlayerException, 
			NegativeResponseException;
	
	public void passTurn(String theToken)
			throws RemoteException, InvalidTokenException,
			PlayerNotInGameException, TurnNotOwnedByPlayerException,
			NegativeResponseException;
	

}