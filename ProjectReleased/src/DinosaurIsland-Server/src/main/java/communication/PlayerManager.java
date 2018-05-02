package communication;

import exceptions.InvalidTokenException;
import exceptions.NameAlreadyTakenException;
import exceptions.NotFoundException;
import exceptions.PasswordMismatchException;
import gameplay.Game;
import gameplay.Player;
import gui.ServerMainWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;

import communication.identifiers.TokenManager;

import logging.Loggable;
import util.StackTraceUtils;

/**
 * <b>Overview</b><br>
 * <p>
 * DinosaurIDManager is a singleton class that manages a <i>finite</i> pool of DinosaurID, created ensuring that each DinosaurID is unique in the pool. <br>
 * </p>
 *
 * <b>Responsibilities</b><br>
 * <p>
 * It gives the possibility to:
 * <ul>
 * <li> Assign a dinosaurID to a dinosaur
 * <li> Remove a dinosaurID from an dinosaur
 * <li> Get a reference to a dinosaur from its dinosaurID 
 * <li> Get a dinosaurID from a reference to a dinosaur
 * </ul>
 * </p>
 * <b>Collaborators</b><br>
 * <p>
 * The class manages a pool of {@link Token}.
 * </p>
 * 
 * @author	RAA
 * @version 1.6
 * @since	May 21, 2011@3:18:28 PM
 * 
 * @see IdentifierManager
 *
 */



/**
 * <b>Overview</b><br>
 * <p>
 * PlayerManager is a singleton class that manages the player registered to the server.<br>
 * </p>
 *
 * <b>Responsibilities</b><br>
 * <p>
 * It gives the possibility to:
 * <ul>
 * <li> Add a new player to the server
 * <li> Let the login of a player
 * </ul>
 * </p>
 *
 * <b>Collaborators</b><br>
 * <p>
 * The PlayerManager class manages object of the {@link Player} class.
 * </p>
 * 
 * @author	RAA
 * @version 1.6
 * @since	May 21, 2011@10:34:51 PM
 * 
 * @see Player
 *
 */
public class PlayerManager implements Loggable {

	private static Hashtable<String, Player> players = new Hashtable<String, Player>();
	
	private static final PlayerManager uniqueInstance = new PlayerManager();

	/** 
	 * The method addPlayer creates a new player in the server.<br>
	 *
	 * @param theUsername is the username of the player
	 * @param thePassword is the password linked to the username of the player
	 * @throws IllegalArgumentException is thrown by the Player constructor. See {@link  gameplay.Player#Player(String, String)}.
	 * @throws NameAlreadyTakenException 
	 *
	 * @see		Player
	 * @see		gameplay.Player#Player(String, String)
	 *  
	 * @since	May 21, 2011@10:57:02 PM
	 * 
	 */
	public static void addPlayer(String theUsername, String thePassword) throws IllegalArgumentException, NameAlreadyTakenException
	{
		Player newPlayer = new Player(theUsername, thePassword);
		
		if (players.containsKey(theUsername) == false ) {
			players.put(theUsername, newPlayer);
			ServerMainWindow.getInstance().updateRegisteredPlayersValue();
		}
		else {
			throw new NameAlreadyTakenException("The username: " + theUsername + " of the new player is already taken");
		}
			
	}
	
	public int getRegisteredPlayersNumber() {
		return players.size();
	}
	
	private static void clearPlayerList() {
		players.clear();
		ServerMainWindow.getInstance().updateRegisteredPlayersValue();
	}

	/** 
 	 * The method getInstance returns the unique instance of the singleton class
 	 *
 	 * @return the unique instance of the singleton class 
 	 *
 	 * @since	May 21, 2011@10:06:05 PM
 	 * 
 	 */
	static public PlayerManager getInstance() 
	{
		return uniqueInstance;
	}

	public String loginPlayer(String theUsername, String thePassword) throws NullPointerException, PasswordMismatchException, IllegalArgumentException, AuthenticationException, NotFoundException {
		
		if (theUsername.isEmpty()) {
			IllegalArgumentException e = new IllegalArgumentException("The username cannot be null or empty");
			getLogger().warning(StackTraceUtils.getThrowMessage(e));
			throw e;
		}
		if (thePassword.isEmpty()) {
			IllegalArgumentException e = new IllegalArgumentException("The password cannot be null or empty");
			getLogger().warning(StackTraceUtils.getThrowMessage(e));
			throw e;
		}
		
		if (players.containsKey(theUsername)) {
			
			Player player = players.get(theUsername);
			
			
			
			if (TokenManager.getInstance().containsIdentifier(player) == true) {
				
				AuthenticationException e = new AuthenticationException("The user is already logged in");
				getLogger().warning(StackTraceUtils.getThrowMessage(e));
				throw e;
			}
			else {
				
				if (player.getPassword().equals(thePassword) == false) {
					
					PasswordMismatchException e = new PasswordMismatchException("The given password does not match the stored password for the user: " + theUsername);
					getLogger().warning(StackTraceUtils.getThrowMessage(e));
					throw e;
				}
				
				String token = TokenManager.getInstance().assignIdentifierToObject(player);
				return token;
			}
		}
		else {
			
			NotFoundException e = new NotFoundException("The username does not exist");
			getLogger().warning(StackTraceUtils.getThrowMessage(e));
			throw e;
		}
		
	}
	
	public void logout(String theToken) throws InvalidTokenException, IllegalArgumentException {
		Player player;
		
		 try {
			player = TokenManager.getInstance().getObject(theToken);
		}
		catch (NotFoundException e) {
			InvalidTokenException ex = new InvalidTokenException("Invalid Token");
			getLogger().severe(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}
		
		
		if (Game.getInstance().getInGamePlayers().contains(player) == true) {
			IllegalArgumentException ex =  new IllegalArgumentException("The user cannot log out because he/she is in game");
			getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
			
		} else {
			TokenManager.getInstance().removeIdentifierFromObject(player);
		} 
		
		//TokenManager.getInstance().removeIdentifierFromObject(player);
	}
	
	static void loadData(PlayersData thePlayersData) {
		
		clearPlayerList();
		
		for (Player player : thePlayersData.getPlayerList()) {
			players.put(player.getUsername(),player);
		}
		ServerMainWindow.getInstance().updateRegisteredPlayersValue();
	}
	
	static PlayersData saveData() {
		return new PlayersData(players);
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
	
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#getLogger()
	 * 
	 */
	@Override
	public Logger getLogger() {
		return Server.getInstance().getLogger();
	}
}

class PlayersData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Player> thePlayerList;
	
PlayersData() {
		
		thePlayerList = new ArrayList<Player>();
		
	}
	
	PlayersData(Hashtable<String, Player> thePlayersHashTable) {
		
		thePlayerList = new ArrayList<Player>();
		thePlayerList.addAll(thePlayersHashTable.values());
		
	}
	
	ArrayList<Player> getPlayerList() {
		return new ArrayList<Player>(thePlayerList);
	}
	
	
}
