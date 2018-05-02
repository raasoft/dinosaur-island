package gameplay;

import java.io.Serializable;
import java.util.ArrayList;

import commands.CommandHelper;
import communication.PlayerManager;
import exceptions.NameAlreadyTakenException;
import exceptions.SpeciesAlreadyCreatedException;

//TODO Test the Player class

/**
 * <b>Overview</b><br>
 * <p>
 * In the game, the class Player stores information about each player registered
 * on the server.
 * </p>
 * <b>Responsibilities</b><br>
 * <p>
 * The class Player is responsible to store information about the player. A
 * Player class stores information about:
 * <ul>
 * <li>The player's username;
 * <li>The player's password;
 * <li>The player's current dinosaur species;
 * <li>The player's current status in the server;
 * </ul>
 * A player can be created only by the {@link PlayerManager} class, so its
 * constructor is never used programmatically. To create a dinosaur you have to
 * call the {@code PlayerManager#addPlayer(String, String) } method of the
 * {@link PlayerManager} class.
 * </p>
 * <b>Collaborators</b><br>
 * <p>
 * A player is managed by the {@link PlayerManager} class.
 * </p>
 * 
 * @author RAA
 * @version 1.6
 * 
 * @see PlayerManager
 */
public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Species currentSpecies;
	private PlayerStatus status;

	/**
	 * @param theUsername
	 *            is the username that identifies the player
	 * @param thePassword
	 *            is the password that corresponds to the player username
	 * 
	 */
	public Player(String theUsername, String thePassword)
			throws IllegalArgumentException {
		/*
		 * Since this is a public constructor, we should check if the name of
		 * the species is composed only by alphanumeric characters
		 */
		ArrayList<String> theArguments = new ArrayList<String>();
		theArguments.add(theUsername);
		theArguments.add(thePassword);

		CommandHelper.checkStringsAlphanumeric(theArguments);

		setUsername(theUsername);
		setPassword(thePassword);
	}

	/**
	 * The method setUsername sets the player username.<br>
	 * The user name is a non-null and not-empty string used to identify the
	 * player and log the player in.
	 * 
	 * @param theUsername
	 *            is the new username of the player
	 * @throws IllegalArgumentException
	 *             is thrown if theUsername is empty or null
	 * 
	 */
	private void setUsername(String theUsername)
			throws IllegalArgumentException {

		if (theUsername == null) {
			throw new IllegalArgumentException(
					"The player's username mustn't be null");
		}

		if (theUsername.length() == 0) {
			throw new IllegalArgumentException(
					"The player's username mustn't be empty");
		}
		username = theUsername;

	}

	/**
	 * The method getStatus get the status of a player, that is an enumerator of
	 * type {@link PlayerStatus}
	 * 
	 * @return the status of a player
	 * 
	 * @see PlayerStatus
	 * 
	 */
	public PlayerStatus getStatus() {

		return status;

	}

	/**
	 * The method setStatus sets the new status of a player.
	 * 
	 * @param theStatus
	 *            the new status of a player
	 * @throws IllegalArgumentException
	 *             is thrown if theStatus is null
	 * 
	 * @see PlayerStatus
	 * 
	 */
	public void setStatus(PlayerStatus theStatus)
			throws IllegalArgumentException {
		/*
		 * TODO Player setStatus: find a better way of integration between the
		 * status change and the Game class dynamics
		 */

		if (theStatus == null) {
			throw new IllegalArgumentException(
					"The player's status must be non-null");
		}

		status = theStatus;
	}

	/**
	 * The method setPassword sets the new password associated to the username
	 * of the player.
	 * 
	 * @param thePassword
	 *            is the new password
	 * @throws IllegalArgumentException
	 *             is thrown if the password is null or is empty
	 * 
	 */
	private void setPassword(String thePassword)
			throws IllegalArgumentException {
		if (thePassword == null || thePassword.length() == 0) {
			throw new IllegalArgumentException(
					"The player's password mustn't be null");
		}

		if (thePassword.length() == 0) {
			throw new IllegalArgumentException(
					"The player's password mustn't be empty");
		}

		password = thePassword;
	}

	/**
	 * The method getUsername returns the current username of a player.
	 * 
	 * @return a string that correspond to the username of the player.
	 * 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * The method getUsername returns the current password of a player.
	 * 
	 * @return a string that correspond to the password of the player.
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * The method deleteCurrentSpecies deletes the current dinosaur species
	 * associated to the player.
	 * 
	 */
	public void deleteCurrentSpecies() {
		currentSpecies = null;
	}

	/**
	 * The method createCurrentSpecies creates a new dinosaur species for the
	 * current player if the player didn't have one.<br>
	 * In order to create the species, it must be given one SpeciesFactory and
	 * the name of the species.
	 * 
	 * @param theSpeciesFactory
	 *            is the factory that will create the species.
	 * @param theName
	 *            is the name of the new species
	 * @return the new species created
	 * @throws Exception
	 *             is thrown if the player already has one species
	 * @throws IllegalArgumentException
	 *             is thrown if theName is a null or empty string
	 * 
	 */
	public Species createCurrentSpecies(SpeciesFactory theSpeciesFactory,
			String theName) throws SpeciesAlreadyCreatedException,
			IllegalArgumentException, NameAlreadyTakenException {

		if (currentSpecies != null) {
			throw new SpeciesAlreadyCreatedException(
					"Can't create another species for the player: he already have a species");
		}

		if (theSpeciesFactory == null) {
			throw new IllegalArgumentException(
					"Can't create another species for the player: species type doesn't specified");
		}

		currentSpecies = theSpeciesFactory.createSpecies(this, theName);
		return currentSpecies;

	}

	/**
	 * The method getCurrentSpecies returns the current dinosaur species
	 * associated to the player.
	 * 
	 * @return a reference to the current player species
	 * 
	 */
	public Species getCurrentSpecies() {
		return currentSpecies;
	}

}

/**
 * <b>Overview</b><br>
 * <p>
 * The PlayerStatus is an enumerator that represents the actual status of a
 * player in the server.<br>
 * The status of a player can be:
 * <ul>
 * <li>Offline, that means that the player is registered to the server but is
 * not currently connected to the server
 * <li>Logged, that means that the player is registered to the server and is
 * connected to it, but is not playing
 * <li>Playing, that means that the player is currently playing.
 * </p>
 * <b>Responsibilities</b><br>
 * <p>
 * Represent the current status of a player in the server.
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * Is used within the {@link Player} class.
 * </p>
 * 
 * @author RAA
 * @version 1.6
 * 
 * @see Player
 * 
 */
enum PlayerStatus {

	OFFLINE, LOGGED, PLAYING
}
