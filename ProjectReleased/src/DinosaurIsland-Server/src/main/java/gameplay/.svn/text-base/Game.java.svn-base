/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import java.util.logging.Level;
import java.util.logging.Logger;

import beans.DinosaurStatus;
import beans.NewBornID;

import communication.ClientHandler;
import communication.ServerInterface;

import util.Position;
import util.StackTraceUtils;

import logging.Loggable;

import exceptions.AlreadyOccupiedCellException;
import exceptions.FightLostException;
import exceptions.FightWonException;
import exceptions.IdentifierNotPresentException;
import exceptions.InvalidDestinationException;
import exceptions.InvalidDinosaurIDException;
import exceptions.InvalidPositionException;
import exceptions.MaxInGamePlayersExceededException;
import exceptions.MaxMovesLimitException;
import exceptions.MaxSizeReachedException;
import exceptions.MaxSpeciesAgeExceededException;
import exceptions.NotFoundException;
import exceptions.PlayerAlreadyInGameException;
import exceptions.PlayerNotInGameException;
import exceptions.SpeciesIsFullException;
import exceptions.SpeciesNotCreatedException;
import exceptions.StarvationDeathException;
import exceptions.TurnNotOwnedByPlayerException;
import gameplay.highscore.HighscoreManager;
import gameplay.map.Cell;
import gameplay.map.Food;
import gameplay.map.Map;
import gui.ServerMainWindow;
/**
 * <b>Overview</b><br>
 * <p>
 * Game is the main class of the {@link gameplay} package.<br>
 * It manages all the actions made by dinosaurs and the game loop. The game
 * proceed by turns owned by the players in game. During a turn only the turn
 * owner can make actions while the other players keep on waiting for their
 * turn.<br>
 * This class also handles the creation and removal of dinosaurs and players in
 * game. It also returns all the information regardind dinosaurs and It stores
 * this informations:
 * <ul>
 * <li>The curent player index
 * <li>The current turn number
 * <li>The list of in game players
 * <li>The state of the game
 * <li>Its unique istance
 * <li>A map containing the turn listeners
 * <li>The timer to start the game
 * <li>The turn confirmation timer
 * <li>The turn execution timer
 * </ul>
 * </p>
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * The Game class is responsible for the addition of players in the current
 * game.<br>
 * It handles the turns.<br>
 * It manages all the dinosaurs' actions
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * {@see Map}, {@see Species}, {@see Player}, {@see DinosaurIDManager}, {@see
 * GameState}
 * </p>
 * 
 * @author AXXO
 * @version 1.0
 */
final public class Game implements Loggable {

	private static Logger logger;
	final private static Game uniqueInstance = new Game();

	private Hashtable<Player, ClientHandler> changeTurnListeners;

	private static int currentTurn;
	private static GameState state;

	TurnConfirmationTimerTask turnConfirmationTimerTask;
	boolean turnConfirmationTimerIsRunning;
	Timer turnConfirmationTimer;

	boolean timerForStartingGameAlreadyFired = false;

	TurnExecutionTimerTask turnExecutionTimerTask;
	boolean turnExecutionTimerIsRunning;
	Timer turnExecutionTimer;

	private static ArrayList<Player> inGamePlayers;
	private static int currentPlayerIndex;
	private static int playersMax = 8;

	/**
	 * It sets up the logger<br>
	 * It gets the unique instance of the map<br>
	 * It sets the current player index to -1<br>
	 * It initializes the turn listeners map
	 */
	private Game() {

		setupLogger();

		Map.getInstance();

		inGamePlayers = new ArrayList<Player>();
		setCurrentPlayerIndex(-1);

		changeTurnListeners = new Hashtable<Player, ClientHandler>();

		state = GameState.STOP;
		setupTimers();
	}

	/**
	 * @return the unique instance of the game
	 * 
	 */
	public static Game getInstance() {
		return uniqueInstance;
	}

	/**
	 * @return the current player index
	 */
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	/**
	 * The method checkPlayerInGameConsistency checks whether the player given
	 * as parameter is in game or not
	 * 
	 * @param thePlayer
	 *            the player to be checked
	 * @throws PlayerNotInGameException
	 *             if the player given as parameter os not in game
	 * 
	 */
	public void checkPlayerInGameConsistency(Player thePlayer)
			throws PlayerNotInGameException {

		boolean found = false;

		found = inGamePlayers.contains(thePlayer);

		if (!found) {
			PlayerNotInGameException ex = new PlayerNotInGameException(
					"The player is not ingame");
			getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

	}

	/**
	 * @param currentPlayerIndex
	 *            the currentPlayerIndex to set
	 */
	public void setCurrentPlayerIndex(int theCurrentPlayerIndex) {
		currentPlayerIndex = theCurrentPlayerIndex;
	}

	/**
	 * @param theValue
	 *            the max players in game value to set
	 * @throws IllegalArgumentException
	 *             if the value given as parameter is negative
	 * 
	 */
	public void setPlayersMax(int theValue) throws IllegalArgumentException {

		if (theValue <= 0) {
			IllegalArgumentException ex = new IllegalArgumentException(
					"The maximum number of players can't be non positive");
			getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		playersMax = theValue;
	}

	/**
	 * @return the maximum numbers of players allowed to play
	 * 
	 */
	public int getPlayersMax() {
		return playersMax;
	}

	/**
	 * 
	 * @return the current turn number
	 * 
	 */
	public int getCurrentTurn() {
		return currentTurn;
	}

	/**
	 * @return the list of players in game
	 */
	public ArrayList<Player> getInGamePlayers() {
		return new ArrayList<Player>(inGamePlayers);
	}

	/**
	 * @return the number of players in game
	 * 
	 */
	public int getInGamePlayersNumber() {
		return inGamePlayers.size();
	}

	/**
	 * @return true if the game is running
	 */
	public boolean isRunning() {
		if (getState() != GameState.STOP)
			return true;
		else
			return false;
	}

	/**
	 * @return the player owning the current turn
	 */
	public Player getCurrentPlayer() {
		if (getCurrentPlayerIndex() == -1) {
			return null;
		} else {
			return inGamePlayers.get(getCurrentPlayerIndex());
		}
	}

	/**
	 * The method startGame calls {@code Map#createRandomMap()} which creates a
	 * random map then changes the game state to
	 * {@code GameState#WAITING_TURN_CONFIRMATION}
	 * 
	 * @see GameState
	 * 
	 * 
	 */
	private void startGame() {
		getLogger().info("Starting the game");

		if (Map.isMapGenerated() == false) {
			Map.getInstance().createRandomMap();
		}
		changeState(GameState.WAITING_TURN_CONFIRMATION);
	}

	/**
	 * The method stopGame changes the game state to {@code GameState#STOP}
	 * 
	 * 
	 * @see GameState
	 */
	public void stopGame() {

		changeState(GameState.STOP);
		timerForStartingGameAlreadyFired = false;

	}

	/**
	 * The method addDinosaur adds a dinosaur to the player given as parameter.
	 * It calls {@code #registerDinosaurInGame(Dinosaur)} to register the new
	 * dinosaur and {@code Map#placeDinosaurRandomly(Dinosaur)} to assigns a
	 * random position to it
	 * 
	 * @param thePlayer
	 *            the player to which the new dinosaur will be added
	 * @return the newly created dinosaur
	 * @throws IllegalArgumentException
	 *             if the player given as parameter is null or if its species
	 *             its null
	 * @throws SpeciesIsFullException
	 *             if the species owned by the player given as parameter is full
	 * 
	 * @see Species
	 * @see Map
	 * 
	 * 
	 */
	public Dinosaur addDinosaur(Player thePlayer)
			throws IllegalArgumentException, SpeciesIsFullException {

		if (thePlayer == null) {
			throw new IllegalArgumentException("The player cannot be null");
		}

		if (thePlayer.getCurrentSpecies() == null) {
			throw new IllegalArgumentException(
					"The player MUST have one species assigned");
		}

		/* This method can throw the SpeciesIsFullException */
		Dinosaur newBorn = thePlayer.getCurrentSpecies().addDinosaur();

		registerDinosaurInGame(newBorn);

		/* Now that the dinosaur has been created, register it */
		Map.getInstance().placeDinosaurRandomly(newBorn);

		return newBorn;

	}

	/**
	 * The method registerDinosaurInGame is intended to register a new fresh
	 * dinosaur or the existing dinosaur of a player when the server is resumed
	 * without using the addDinosaur method (that creates a fresh new dinosaur
	 * instead);
	 * 
	 * @param theDinosaur
	 *            to be registered
	 * 
	 * @see DinosaurIDManager
	 * 
	 * 
	 */
	private void registerDinosaurInGame(Dinosaur theDinosaur) {

		if (DinosaurIDManager.getInstance().containsIdentifier(theDinosaur) == false) {
			DinosaurIDManager.getInstance().assignIdentifierToObject(
					theDinosaur);
		}
	}

	/**
	 * The method growDinosaur check if the dinosaur given as parameter is able
	 * to grow, in that case it performs all the required instructions:<br>
	 * It increases its size, updates its energy and sets that it is not able to
	 * grow anymore in this turn
	 * 
	 * @param theDinosaur
	 *            the dinosaur wanting to grow up
	 * @throws InvalidDinosaurIDException
	 *             if the dinosaur ID of the dinosaur given as parameter is not
	 *             valid
	 * @throws MaxSizeReachedException
	 *             if the dinosaur already reached its maximum size
	 * @throws PlayerNotInGameException
	 *             if the player owning the dinosaur is not in game
	 * @throws TurnNotOwnedByPlayerException
	 *             if the player does not own the current turn
	 * @throws MaxMovesLimitException
	 *             if the dinosaur has alreeady grown up in this turn
	 * @throws StarvationDeathException
	 *             if the dinosaur has not enough energy to perform the grow
	 * 
	 * @see DinosaurIDManager
	 * @see Dinosaur
	 * 
	 * 
	 */
	public void growDinosaur(Dinosaur theDinosaur)
			throws InvalidDinosaurIDException, MaxSizeReachedException,
			PlayerNotInGameException, TurnNotOwnedByPlayerException,
			MaxMovesLimitException, StarvationDeathException {

		if (theDinosaur == null) {
			InvalidDinosaurIDException e = new InvalidDinosaurIDException(
					"The dinosaur ID is not valid");
			logger.warning(e.getMessage());
			throw e;
		}

		if (DinosaurIDManager.getInstance().getIdentifier(theDinosaur) == null) {
			InvalidDinosaurIDException e = new InvalidDinosaurIDException(
					"The dinosaur ID is not valid");
			logger.warning(e.getMessage());
			throw e;
		}

		Player dinosaurOwner = theDinosaur.getSpecies().getPlayer();
		if (inGamePlayers.contains(dinosaurOwner) == false) {
			PlayerNotInGameException e = new PlayerNotInGameException(
					"The player is not in game");
			logger.warning(e.getMessage());
			throw e;
		}

		if (getCurrentPlayerIndex() != inGamePlayers.indexOf(dinosaurOwner)) {
			TurnNotOwnedByPlayerException e = new TurnNotOwnedByPlayerException(
					"The player is not allowed to move in this turn");
			logger.warning(e.getMessage());
			throw e;
		}

		if (theDinosaur.isAbleToAct() == false) {
			MaxMovesLimitException e = new MaxMovesLimitException(
					"The dinosaur can't grow anymore in this turn");
			logger.warning(e.getMessage());
			throw e;
		}

		if (theDinosaur.getSize() == theDinosaur.getSizeMax()) {
			MaxSizeReachedException e = new MaxSizeReachedException(
					"The dinosaur has already reached its maximum size");
			logger.warning(e.getMessage());
			throw e;
		}

		if (theDinosaur.getEnergy() <= theDinosaur.getGrowthEnergy()) {
			removeDinosaur(theDinosaur);
			StarvationDeathException e = new StarvationDeathException(
					"The dinosaur died because it hadn't got enough energy to grow");
			logger.info(e.getMessage());
			throw e;
		}

		try {
			int initialEnergy = theDinosaur.getEnergy();
			theDinosaur
					.setEnergy(initialEnergy - theDinosaur.getGrowthEnergy());
			int initialSize = theDinosaur.getSize();
			theDinosaur.setSize(initialSize + 1);
			theDinosaur.getSpecies().updateFOV(theDinosaur);
			logger.info("Dinosaur " + theDinosaur.getId()
					+ " has grown to size " + theDinosaur.getSize());
			/* The dinosaur can't grow anymore in this turn */
			theDinosaur.setAbleToAct(false);
		} catch (IllegalArgumentException e) {
			logger.warning(e.getMessage());
		}

	}

	/**
	 * The method layEgg check if the dinosaur given as parameter is able to lay
	 * an egg, in that case it creates a new born dinosaur and call
	 * {@code #registerDinosaurInGame(Dinosaur))} to register it and
	 * {@code Map#placeDinosaurRandomly(Dinosaur, Dinosaur)} to place it near
	 * the parent dinosaur
	 * 
	 * @param theDinosaur
	 *            the parent dinosaur
	 * @return the dinosaur ID of the new born dinosaur
	 * @throws SpeciesIsFullException
	 *             if the species of the player owning the dinosaur given as
	 *             parameter is full
	 * @throws MaxMovesLimitException
	 *             if the dinosaur already laid an egg or grew up in this turn
	 * @throws IdentifierNotPresentException
	 *             if the dinosaur is null or if its identifier is not valid
	 * @throws TurnNotOwnedByPlayerException
	 *             if the player owning the dinosaur does not own the current
	 *             turn
	 * @throws PlayerNotInGameException
	 *             if the player owning the dinosaur is not in game
	 * @throws StarvationDeathException
	 *             if the dinosaur has not enough energy to perform the action
	 * 
	 * @see DinosaurIDManager
	 * @see Map
	 * @see Species
	 * 
	 * 
	 */
	public NewBornID layEgg(Dinosaur theDinosaur)
			throws SpeciesIsFullException, MaxMovesLimitException,
			IdentifierNotPresentException, TurnNotOwnedByPlayerException,
			PlayerNotInGameException, StarvationDeathException {

		if (theDinosaur == null) {
			InvalidDinosaurIDException e = new InvalidDinosaurIDException(
					"The dinosaur cannot be null");
			logger.warning(e.getMessage());
			throw e;

		}

		if (DinosaurIDManager.getInstance().getIdentifier(theDinosaur) == null) {
			InvalidDinosaurIDException e = new InvalidDinosaurIDException(
					"The dinosaur to move is not in game");
			logger.warning(e.getMessage());
			throw e;
		}

		if (theDinosaur.isAbleToAct() == false) {
			MaxMovesLimitException e = new MaxMovesLimitException(
					"The dinosaur has already made its maximum number of moves in this turn");
			logger.warning(e.getMessage());
			throw e;
		}

		Player dinosaurOwner = theDinosaur.getSpecies().getPlayer();
		if (inGamePlayers.contains(dinosaurOwner) == false) {
			PlayerNotInGameException e = new PlayerNotInGameException(
					"The player is not in game");
			logger.warning(e.getMessage());
			throw e;
		}

		if (getCurrentPlayerIndex() != inGamePlayers.indexOf(dinosaurOwner)) {
			TurnNotOwnedByPlayerException e = new TurnNotOwnedByPlayerException(
					"The player is not allowed to move in this turn");
			logger.warning(e.getMessage());
			throw e;
		}

		if (theDinosaur.getEnergy() <= theDinosaur.getEggDepositionEnergy()) {
			removeDinosaur(theDinosaur);
			StarvationDeathException e = new StarvationDeathException(
					"The dinosaur died because he hadn't got enough energy to lay an egg");
			logger.warning(e.getMessage());
			throw e;
		}

		try {
			logger.info("Dinosaur " + theDinosaur.getId()
					+ " is trying to lay an egg");
			Dinosaur newBorn = theDinosaur.getSpecies().addDinosaur();
			logger.info("Dinosaur " + theDinosaur.getId()
					+ " succesfully laid an egg");
			theDinosaur.setAbleToMove(false);
			/*
			 * The new born dinosaur is not able to grow,move or lay an egg in
			 * this turn
			 */
			newBorn.setAbleToMove(false);
			newBorn.setAbleToAct(false);
			registerDinosaurInGame(newBorn);
			logger.info("The new born dinosaur received ID " + newBorn.getId());
			Map.getInstance().placeDinosaurRandomly(newBorn, theDinosaur);
			int initialEnergy = theDinosaur.getEnergy();
			theDinosaur.setEnergy(initialEnergy
					- theDinosaur.getEggDepositionEnergy());
			return new NewBornID(newBorn.getId());
		} catch (SpeciesIsFullException e) {
			logger.warning(e.getMessage());
			throw new SpeciesIsFullException(e.getMessage());
		}

	}

	/**
	 * The method moveDinosaur check if the dinosaur is able to move in this
	 * turn then calls {@code Map#moveDinosaur(Dinosaur, Position)} which
	 * delegates to the Map class the task of checking the movement path <br>
	 * If the dinosaur encounters a fight it calls
	 * {@code #manageFight(Dinosaur, Dinosaur)} to get the result of the fight
	 * 
	 * @param theDinosaur
	 *            the dinosaur to be moved
	 * @param theDestination
	 *            the position the dinosaur is going to be placed
	 * @throws IdentifierNotPresentException
	 *             if the dinosaur given as parameter is null or if its
	 *             identifier is null
	 * @throws StarvationDeathException
	 *             if the dinosaur has not enough energy to move
	 * @throws InvalidDestinationException
	 *             if the destination is not valid
	 * @throws PlayerNotInGameException
	 *             if the player owning the dinosaur is not in game
	 * @throws TurnNotOwnedByPlayerException
	 *             if the player owning the dinosaur does not own the current
	 *             turn
	 * @throws MaxMovesLimitException
	 *             if the dinosaur already moved in this turn
	 * @throws FightLostException
	 *             if the dinosaur lost the fight
	 * @throws FightWonException
	 *             if the dinosaur won the fight
	 * 
	 * @see Map
	 * @see DinosaurIDManager
	 * 
	 * 
	 */
	public void moveDinosaur(Dinosaur theDinosaur, Position theDestination)
			throws IdentifierNotPresentException, StarvationDeathException,
			InvalidDestinationException, PlayerNotInGameException,
			TurnNotOwnedByPlayerException, MaxMovesLimitException,
			FightLostException, FightWonException {

		if (theDinosaur == null) {
			InvalidDinosaurIDException e = new InvalidDinosaurIDException(
					"The dinosaur ID is not valid");
			logger.warning(e.getMessage());
			throw e;
		}

		if (theDestination == null) {
			InvalidDestinationException e = new InvalidDestinationException(
					"The destination is not valid");
			logger.warning(e.getMessage());
			throw e;
		}

		if (DinosaurIDManager.getInstance().getIdentifier(theDinosaur) == null) {
			IdentifierNotPresentException e = new IdentifierNotPresentException(
					"The dinosaur to move is not in game");
			logger.warning(e.getMessage());
			throw e;
		}

		Player dinosaurOwner = theDinosaur.getSpecies().getPlayer();
		if (inGamePlayers.contains(dinosaurOwner) == false) {
			PlayerNotInGameException e = new PlayerNotInGameException(
					"The player is not in game");
			logger.warning(e.getMessage());
			throw e;
		}

		if (getCurrentPlayerIndex() != inGamePlayers.indexOf(dinosaurOwner)) {
			TurnNotOwnedByPlayerException e = new TurnNotOwnedByPlayerException(
					"The player is not allowed to move in this turn");
			logger.warning(e.getMessage());
			throw e;
		}

		if (theDinosaur.isAbleToMove() == false) {
			MaxMovesLimitException e = new MaxMovesLimitException(
					"The dinosaur has already made its maximum number of moves in this turn");
			logger.warning(e.getMessage());
			throw e;
		}

		if (getState() == GameState.WAITING_TURN_CONFIRMATION) {
			changeState(GameState.TURN_EXECUTION);
		}

		try {
			Map.getInstance().moveDinosaur(theDinosaur, theDestination);
			/*
			 * If the dinosaur has moved it won't be able to move again in this
			 * turn
			 */
			theDinosaur.setAbleToMove(false);
		} catch (InvalidDestinationException e) {
			logger.warning(StackTraceUtils.getCatchMessage(e));
			throw new InvalidDestinationException(e.getMessage());
		} catch (InvalidPositionException e) {
			logger.warning(StackTraceUtils.getCatchMessage(e));
			throw new InvalidDestinationException(e.getMessage());
		} catch (StarvationDeathException e) {
			removeDinosaur(theDinosaur);
			logger.warning(StackTraceUtils.getCatchMessage(e));
			throw new StarvationDeathException(e.getMessage());
		} catch (AlreadyOccupiedCellException e) {
			logger.info(e.getMessage());
			Dinosaur theOtherDinosaur = Map.getInstance().getDinosaur(
					theDestination);

			if ((!theDinosaur.isAggressive() && !theOtherDinosaur
					.isAggressive()) == false
					&& !theDinosaur.getSpecies().equals(
							theOtherDinosaur.getSpecies())) {
				if (manageFight(theDinosaur, theOtherDinosaur) == theDinosaur) {
					Map.getInstance().moveDinosaur(theDinosaur, theDestination);
					/*
					 * If the dinosaur has moved it won't be able to move again
					 * or lay an egg in this turn
					 */
					theDinosaur.setAbleToMove(false);
					FightWonException ex = new FightWonException(
							"You won the fight");
					logger.info(ex.getMessage());
					throw ex;
				} else {
					FightLostException ex = new FightLostException(
							"You lost the fight");
					logger.info(ex.getMessage());
					throw ex;
				}
			} else if (theDinosaur.getSpecies().equals(
					theOtherDinosaur.getSpecies())) {
				InvalidDestinationException ex = new InvalidDestinationException(
						"The final destination contains a dinosaur of the same species");
				logger.warning(ex.getMessage());
				throw ex;
			} else if ((!theDinosaur.isAggressive() && !theOtherDinosaur
					.isAggressive()) == true) {
				InvalidDestinationException ex = new InvalidDestinationException(
						"The final destination contains another herbivore");
				logger.warning(ex.getMessage());
				throw ex;
			}
		}
	}

	/**
	 * The method manageFight is responsible to check and calculate the result
	 * of a fight between dinosaurs of different species
	 * 
	 * @param theAttacker
	 *            the attacking dinosaur
	 * @param theDefender
	 *            the attacked dinosaur
	 * @return
	 * 
	 * @see Dinosaur
	 * 
	 * 
	 */
	private Dinosaur manageFight(Dinosaur theAttacker, Dinosaur theDefender) {

		int attackerStrength = theAttacker.getStrength();
		int defenderStrength = theDefender.getStrength();

		if (attackerStrength >= defenderStrength) {
			if (theAttacker.isAggressive()) {
				theAttacker.setEnergy(theAttacker.getEnergy() + (int) 0.75
						* theDefender.getEnergy());
			}
			logger.info("The attacked dinosaur with ID " + theDefender.getId()
					+ " has died after the fight");
			removeDinosaur(theDefender);
			return theAttacker;
		} else {
			if (theDefender.isAggressive()) {
				theDefender.setEnergy(theDefender.getEnergy() + (int) 0.75
						* theAttacker.getEnergy());
			}
			logger.info("The attacker dinosaur with ID " + theAttacker.getId()
					+ " has died after the fight");
			removeDinosaur(theAttacker);
			return theDefender;
		}
	}

	/**
	 * @param thePlayer
	 *            the player owning the dinosaur given as parameter
	 * @param theDinosaur
	 *            the dinosaur of which will be retrieved the state
	 * @return an object containing the status of the dinosaur
	 * 
	 * @see Dinosaur
	 * 
	 * 
	 */
	public DinosaurStatus getDinosaurStatus(Player thePlayer,
			Dinosaur theDinosaur) {

		if (theDinosaur == null) {

			NullPointerException e = new NullPointerException(
					"The dinosaur can't be null!");
			getLogger().warning(StackTraceUtils.getThrowMessage(e));
			throw e;
		}

		if (thePlayer == null) {

			NullPointerException e = new NullPointerException(
					"The player can't be null!");
			getLogger().warning(StackTraceUtils.getThrowMessage(e));
			throw e;
		}

		boolean isInLocalView = false;

		for (Dinosaur dino : thePlayer.getCurrentSpecies().getDinosaurList()) {
			if (Map.getInstance().isDinosaurInLocalView(dino, theDinosaur)) {
				isInLocalView = true;
				break;
			}
		}

		String thePlayerOwner = theDinosaur.getSpecies().getPlayer()
				.getUsername();

		if (isInLocalView && thePlayerOwner.equals(thePlayer.getUsername())) {
			return new DinosaurStatus(thePlayerOwner, theDinosaur.getSpecies()
					.getName(), theDinosaur.getSymbol().charAt(0),
					theDinosaur.getPosition(), theDinosaur.getSize(),
					theDinosaur.getEnergy(), theDinosaur.getAge());
		} else {
			return new DinosaurStatus(thePlayerOwner, theDinosaur.getSpecies()
					.getName(), theDinosaur.getSymbol().charAt(0),
					theDinosaur.getPosition(), theDinosaur.getSize());
		}

	}

	/**
	 * The method addChangeTurnListener adds a player to the list of turn
	 * listeners
	 * 
	 * @param thePlayer
	 *            the player to be added to the list of turn listeners
	 * @param theClientHandler
	 *            the client handler communicating with the client
	 * 
	 */
	public void addChangeTurnListener(Player thePlayer,
			ClientHandler theClientHandler) {

		if (thePlayer == null) {
			IllegalArgumentException ex = new IllegalArgumentException(
					"The player cannot be null");
			getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		if (theClientHandler == null) {
			IllegalArgumentException e = new IllegalArgumentException(
					"The client handler cannot be null");
			getLogger().warning(StackTraceUtils.getThrowMessage(e));
			throw e;
		}

		System.out.println("The player " + thePlayer.getUsername()
				+ "has been added to the turn listener");
		changeTurnListeners.put(thePlayer, theClientHandler);

	}

	/**
	 * The method removeChangeTurnListener removes a player from the list of
	 * turn listeners
	 * 
	 * @param thePlayer
	 *            to be removed from the list of turn listeners
	 * 
	 * 
	 */
	public void removeChangeTurnListener(Player thePlayer) {

		if (thePlayer == null) {
			IllegalArgumentException ex = new IllegalArgumentException(
					"The player cannot be null");
			getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		System.out.println("The player " + thePlayer.getUsername()
				+ "wants to be removed from turn listener");

		if (changeTurnListeners.containsKey(thePlayer) == false) {
			IllegalArgumentException ex = new IllegalArgumentException(
					"The player is not registered in the client handler listener list");
			getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		changeTurnListeners.remove(thePlayer);

	}

	/**
	 * The method addPlayer adds a player to the game. It first call
	 * {@code #canAddPlayer(Player)} to check if that is possible<br>
	 * If it is his/her first turn ever with the current species a new dinosaur
	 * is added else it registers its dinosaurs to the game
	 * 
	 * @param thePlayer
	 *            to be added
	 * @throws IllegalArgumentException
	 *             if the player given as parameter is null
	 * @throws PlayerAlreadyInGameException
	 *             if the player is already in game
	 * @throws SpeciesNotCreatedException
	 *             if the player has not created a species yet
	 * @throws MaxInGamePlayersExceededException
	 *             if the game is full
	 * 
	 * @see Map
	 * 
	 * 
	 */
	public void addPlayer(Player thePlayer) throws IllegalArgumentException,
			PlayerAlreadyInGameException, SpeciesNotCreatedException,
			MaxInGamePlayersExceededException {

		canAddPlayer(thePlayer);

		getLogger().info(
				"Adding the player \"" + thePlayer.getUsername()
						+ "\" to the game");
		inGamePlayers.add(thePlayer);

		if (thePlayer.getCurrentSpecies().getSize() == 0) {
			getLogger()
					.info("Since is the first turn of a new player, create a new dinosaur and place it randomly in the map");
			addDinosaur(thePlayer);
			getLogger().info("New dinosaur added. Now setting its age to 1");
			thePlayer.getCurrentSpecies().incrementTurn();
			thePlayer.getCurrentSpecies().getDinosaurList().get(0).setAge(1);
		}

		if (thePlayer.getCurrentSpecies().getCurrentTurn() > 1) {
			getLogger()
					.info("Since the player \""
							+ thePlayer.getUsername()
							+ "\" has already entered the game, register and place its dinosaurs");
			for (Dinosaur dino : thePlayer.getCurrentSpecies()
					.getDinosaurList()) {
				registerDinosaurInGame(dino);
				Map.getInstance().placeDinosaur(dino, dino.getPosition());
			}
		}

		if (isRunning() == false) {
			getLogger().info(
					"Since a new player was added, let's start the game");
			startGame();
		}

	}

	/**
	 * The method canAddPlayer is a utility method which checks if the player
	 * given as parameter can be added to the game
	 * 
	 * @param thePlayer
	 *            who can be added
	 * @throws IllegalArgumentException
	 *             if the player given as parameter is null
	 * @throws PlayerAlreadyInGameException
	 *             if the player is already in game
	 * @throws SpeciesNotCreatedException
	 *             if the player has not created a species yet
	 * @throws MaxInGamePlayersExceededException
	 *             if the game is full
	 * 
	 * 
	 */
	public void canAddPlayer(Player thePlayer) throws IllegalArgumentException,
			PlayerAlreadyInGameException, SpeciesNotCreatedException,
			MaxInGamePlayersExceededException {

		if (thePlayer == null) {
			throw new IllegalArgumentException("The player cannot be null");
		}

		if (inGamePlayers.contains(thePlayer) == true) {
			throw new PlayerAlreadyInGameException(
					"The player is already in game");
		}

		if (thePlayer.getCurrentSpecies() == null) {
			throw new SpeciesNotCreatedException(
					"The player has not created a species yet");
		}

		if (inGamePlayers.size() == playersMax) {
			throw new MaxInGamePlayersExceededException(
					"The maximum amount of players in game has been reached");
		}

	}

	/**
	 * The method removeDinosaur removes a dinosaur from the game, it calls
	 * {@code Map#removeDinosaur(Dinosaur)} to remove it from the map and
	 * {@code DinosaurIDManager#removeIdentifierFromObject(Dinosaur)} to remove
	 * its identifier
	 * 
	 * @param theDinosaur
	 *            to be removed from the game
	 * @throws NullPointerException
	 *             if the dinosaur given as parameter is null
	 * 
	 * @see Map
	 * @see DinosaurIDManager
	 * 
	 * 
	 */
	private void removeDinosaur(Dinosaur theDinosaur)
			throws NullPointerException {
		if (theDinosaur == null) {
			throw new NullPointerException("The dinosaur cannot be null");
		}
		try {
			Map.getInstance().removeDinosaur(theDinosaur);
		} catch (NotFoundException e) {
			logger.warning(StackTraceUtils.getThrowMessage(e));
		}
		theDinosaur.getSpecies().removeDinosaur(theDinosaur);
		try {
			DinosaurIDManager.getInstance().removeIdentifierFromObject(
					theDinosaur);
		} catch (IdentifierNotPresentException e) {
			logger.warning(StackTraceUtils.getThrowMessage(e));
		}

		if (theDinosaur.getSpecies().getDinosaurList().size() == 0) {
			getLogger()
					.info("The player has no more dinosaurs and this isn't the first turn of his/her species. Remove now the player");
			setPlayerGameEnd(theDinosaur.getSpecies().getPlayer());
		}

		theDinosaur = null;
	}

	/**
	 * The method removePlayer removes a player from the game. It removes
	 * his/dinosaurs from the map and their identifiers
	 * 
	 * @param thePlayer
	 *            to be removed from the game
	 * @throws IllegalArgumentException
	 *             if the player given as parameter is null
	 * @throws PlayerNotInGameException
	 *             if the player is not in game
	 * 
	 * @see Player
	 * @see Map
	 * @see DinosaurIDManager
	 * 
	 */
	public void removePlayer(Player thePlayer) throws IllegalArgumentException,
			PlayerNotInGameException {

		if (thePlayer == null) {
			throw new IllegalArgumentException("The player cannot be null");
		}

		if (inGamePlayers.contains(thePlayer) == false) {
			throw new PlayerNotInGameException("The player is not in game");
		}

		getLogger().info(
				"Removing the player \"" + thePlayer.getUsername()
						+ "\" from the game");

		if (thePlayer.getCurrentSpecies().getCurrentTurn() > 1
				&& thePlayer.getCurrentSpecies().getDinosaurList().size() == 0) {
			HighscoreManager.getInstance().setScoreAsFinal(
					thePlayer.getUsername(),
					thePlayer.getCurrentSpecies().getName());
		}

		boolean changeTurn = false;

		if (getCurrentPlayer() == thePlayer) {
			getLogger()
					.info("The current player is to be removed. Must left shift the player index");
			setCurrentPlayerIndex(getCurrentPlayerIndex() - 1);
			changeTurn = true;
		}

		if (getCurrentPlayerIndex() > inGamePlayers.indexOf(thePlayer)) {
			getLogger()
					.info("The current player is in the array list after the player to be removed. Must left shift the player index");
			setCurrentPlayerIndex(getCurrentPlayerIndex() - 1);
		}

		for (Dinosaur dino : thePlayer.getCurrentSpecies().getDinosaurList()) {
			Map.getInstance().getCell(dino.getPosition()).setDinosaur(null);
		}

		for (Dinosaur dino : thePlayer.getCurrentSpecies().getDinosaurList()) {
			DinosaurIDManager.getInstance().removeIdentifierFromObject(dino);
		}

		changeTurnListeners.remove(thePlayer);
		inGamePlayers.remove(thePlayer);

		if (isRunning() == true && getInGamePlayersNumber() == 0) {
			getLogger().info(
					"Since the last player was deleted, let's stop the game");
			stopGame();
		} else {
			if (changeTurn) {
				getLogger()
						.info("Since the current player was removed, let's swap the current player");
				changeState(GameState.WAITING_TURN_CONFIRMATION);
			}
		}

	}

	/**
	 * The method canPassTurn check if a player can pass the turn
	 * 
	 * @param thePlayer
	 *            to be checked
	 * @throws IllegalArgumentException
	 *             if the player is null
	 * @throws PlayerNotInGameException
	 *             if the player is not in game
	 * @throws TurnNotOwnedByPlayerException
	 *             if the player does not own the current turn
	 * 
	 * 
	 */
	public void canPassTurn(Player thePlayer) throws IllegalArgumentException,
			PlayerNotInGameException, TurnNotOwnedByPlayerException {

		if (thePlayer == null) {
			throw new IllegalArgumentException("The player cannot be null");
		}

		if (getCurrentPlayer() != thePlayer) {
			throw new TurnNotOwnedByPlayerException(
					"The player cannot confirm a turn that is not owned by him/her");
		}

		if (inGamePlayers.contains(thePlayer) == false) {
			throw new PlayerNotInGameException("The player is not in game");
		}

		getLogger().info(
				"Player " + thePlayer.getUsername() + " has passed the turn");

	}

	/**
	 * The method passTurn changes the state of the game to
	 * {@code GameState#WAITING_TURN_CONFIRMATION} because the player given as
	 * parameter has passed his/her turn
	 * 
	 * @param thePlayer
	 *            who passed the turn
	 * @throws IllegalArgumentException
	 *             if the player is null
	 * @throws PlayerNotInGameException
	 *             if the player is not in game
	 * @throws TurnNotOwnedByPlayerException
	 *             if the player does not own the current turn
	 * 
	 * 
	 */
	public void passTurn(Player thePlayer) throws IllegalArgumentException,
			PlayerNotInGameException, TurnNotOwnedByPlayerException {

		canPassTurn(thePlayer);

		changeState(GameState.WAITING_TURN_CONFIRMATION);
	}

	/**
	 * The method confirmTurn changes the game state to
	 * {@code GameState#TURN_EXECUTION}
	 * 
	 * @param thePlayer
	 *            who has confirmed his/her turn
	 * @throws IllegalArgumentException
	 *             if the player is null
	 * @throws PlayerNotInGameException
	 *             if the player is not in game
	 * @throws TurnNotOwnedByPlayerException
	 *             if the player does not own the current turn
	 * 
	 * @see GameState
	 * 
	 * 
	 */
	public void confirmTurn(Player thePlayer) throws IllegalArgumentException,
			PlayerNotInGameException, TurnNotOwnedByPlayerException {

		if (thePlayer == null) {
			throw new IllegalArgumentException("The player cannot be null");
		}

		if (getCurrentPlayer() != thePlayer) {
			throw new TurnNotOwnedByPlayerException(
					"The player cannot confirm a turn that is not owned by him/her");
		}

		if (inGamePlayers.contains(thePlayer) == false) {
			throw new PlayerNotInGameException("The player is not in game");
		}

		getLogger()
				.info("Player " + thePlayer.getUsername()
						+ " has confirmed the turn");
		changeState(GameState.TURN_EXECUTION);

	}

	/**
	 * The method performTurnCalculations manages the actions to make at the end
	 * if the turn of a player. <br>
	 * It checks if dinosaurs have died during the turn, updates the food
	 * present in the map and calculate the score
	 * 
	 * @param thePlayer
	 *            who finished the turn
	 * @throws MaxSpeciesAgeExceededException
	 *             if the species reached 120 turns
	 * 
	 * 
	 */
	private void performTurnCalculations(Player thePlayer)
			throws MaxSpeciesAgeExceededException {

		for (Dinosaur dino : thePlayer.getCurrentSpecies().getDinosaurList()) {
			dino.setAge(dino.getAge() + 1);

			if (dino.getAge() > dino.getAgeMax()) {
				removeDinosaur(dino);
			}
		}

		if (thePlayer.getCurrentSpecies() != null) {
			thePlayer.getCurrentSpecies().incrementTurn();
			getLogger().info(
					"Incremented the species turn: now it's "
							+ thePlayer.getCurrentSpecies().getCurrentTurn());
		}

		updateAllFood();

		updateCurrentPlayerScore();

	}

	/**
	 * The method swapCurrentPlayer gives the turn to the next player and
	 * performs turn calculations on the previous player by calling
	 * {@code #performTurnCalculations(Player)}
	 * 
	 * 
	 */
	private void swapCurrentPlayer() {

		getLogger().info("Swapping current player");
		if (getInGamePlayersNumber() == 0) {
			getLogger()
					.info("The number of player is zero. Setting to -1 the player index");
			setCurrentPlayerIndex(-1);
			return;
		}

		if (getInGamePlayersNumber() == 1) {
			getLogger()
					.info("The number of player is one. Setting to 0 the player index");
			setCurrentPlayerIndex(0);
		}

		Player theOldPlayer = getCurrentPlayer();

		getLogger().info("Performing turn calculations on the player species.");
		try {
			performTurnCalculations(theOldPlayer);
		} catch (MaxSpeciesAgeExceededException e) {
			// END OF THE PLAYER
			getLogger()
					.info("The species of the player has reached the maximum age. Removing the player");
			setPlayerGameEnd(theOldPlayer);
		}

		// If the performTurnCalculations removes the last player from the
		// game...
		if (getState() == GameState.STOP && getInGamePlayersNumber() == 0) {
			getLogger().info("The game stopped: no more need to swap player");
			return;
		}

		setCurrentPlayerIndex((getCurrentPlayerIndex() + 1)
				% getInGamePlayersNumber());

		Player theNewPlayer = getCurrentPlayer();
		getLogger().info(
				"The new current player is " + theNewPlayer.getUsername());

		getLogger().info(
				"Its current species turn is: "
						+ theNewPlayer.getCurrentSpecies().getCurrentTurn());
		getLogger().info(
				"Its current species size is: "
						+ theNewPlayer.getCurrentSpecies().getSize());

		for (Dinosaur dino : theNewPlayer.getCurrentSpecies().getDinosaurList()) {
			dino.setAbleToMove(true);
			dino.setAbleToAct(true);
		}

	}

	/**
	 * The method setPlayerGameEnd is called if the species owned by a player is
	 * extinct. It removes the player from the game and delete its current
	 * species
	 * 
	 * @param thePlayer
	 *            owning the extinct species
	 * 
	 * 
	 */
	private void setPlayerGameEnd(Player thePlayer) {
		removePlayer(thePlayer);
		thePlayer.deleteCurrentSpecies();
	}

	/**
	 * The method updateAllFood updates the energy of all the food present in
	 * the map and place the new carrions
	 * 
	 * 
	 * @see Map
	 * 
	 * 
	 */
	private void updateAllFood() {

		Food theFood;

		for (Cell[] cellRow : Map.getInstance().getGrid()) {
			for (Cell cell : cellRow) {
				theFood = cell.getFood();

				if (theFood != null) {

					theFood.updateTurnEnergy();

					if (theFood.isEdibleByCarnivorous()
							&& theFood.getEnergy() <= 0) {
						getLogger()
								.info("Carrion exausted. Removing it and placing a fresh one in the map");
						cell.removeFood();
						Map.getInstance().placeCarrionRandomly();
					}
				}
			}
		}
	}

	/**
	 * @return the current game state
	 * 
	 * @see GameState
	 * 
	 */
	private GameState getState() {
		return state;
	}

	/**
	 * The method changeState changes the current game state to the one gicen as
	 * parameter
	 * 
	 * @param theState
	 *            the new game state
	 * @throws IllegalStateException
	 *             if the state given as parameter is not valuid
	 * 
	 * @see GameState
	 * 
	 * 
	 */
	public void changeState(GameState theState) throws IllegalStateException {

		getLogger().fine("Changing state");
		GameState currentState = getState();

		switch (currentState) {
			case STOP : {

				if (theState == GameState.TURN_EXECUTION) {
					IllegalStateException e = new IllegalStateException(
							"The game status can go only from STOP to WAITING_TURN_CONFIRMATION");
					getLogger().warning(StackTraceUtils.getThrowMessage(e));
					throw e;
				} else if (theState == GameState.STOP) {
					getLogger().info("The game is already stopped");
				} else {
					getLogger().info(
							"Changing from STOP to WAITING_TURN_CONFIRMATION");

					setGameStateWaitingForPlayerConfirmation();
				}

				break;

			}
			case WAITING_TURN_CONFIRMATION : {

				switch (theState) {
					case WAITING_TURN_CONFIRMATION : {
						getLogger()
								.info("Changing from WAITING_TURN_CONFIRMATION to WAITING_TURN_CONFIRMATION");
						setGameStateWaitingForPlayerConfirmation();
						break;
					}

					case TURN_EXECUTION : {
						getLogger()
								.info("Changing from WAITING_TURN_CONFIRMATION to TURN_EXECUTION");
						setGameStateTurnExecution();
						break;
					}
					case STOP : {
						getLogger()
								.info("Changing from WAITING_TURN_CONFIRMATION to STOP");
						setGameStateStopped();
						break;
					}
				}

				break;
			}

			case TURN_EXECUTION : {

				switch (theState) {

					case TURN_EXECUTION : {

						break;
					}

					case WAITING_TURN_CONFIRMATION : {
						getLogger()
								.info("Changing from TURN_EXECUTION to WAITING_TURN_CONFIRMATION");
						setGameStateWaitingForPlayerConfirmation();
						break;
					}
					case STOP : {
						getLogger()
								.info("Changing from TURN_EXECUTION to STOP");
						setGameStateStopped();
						break;
					}
					default : {
						IllegalStateException e = new IllegalStateException(
								"The game status can go only from TURN_EXECUTION to WAITING_TURN_CONFIRMATION or STOP");
						getLogger().warning(StackTraceUtils.getThrowMessage(e));
						throw e;
					}
				}

				break;
			}
		}
	}

	/**
	 * The method changeOperations stops all the game timers
	 * 
	 * 
	 * 
	 * 
	 */
	private void changeOperations() {
		stopAllTimers();
	}

	/**
	 * The method setGameStateTurnExecution sets the game state to
	 * {@code GameState#TURN_EXECUTION}
	 * 
	 * @see GameState
	 * 
	 * 
	 */
	private void setGameStateTurnExecution() {
		changeOperations();
		getLogger()
				.info("Now the game is waiting for a player to perform his/hers actions");
		state = GameState.TURN_EXECUTION;
		startTurnExecutionTimer();
	}

	/**
	 * The method setGameStateStopped sets the game state to
	 * {@code GameState#STOP}
	 * 
	 * 
	 * 
	 */
	private void setGameStateStopped() {
		state = GameState.STOP;

		changeOperations();
		getLogger().info("Now the game is stopped");

		updateCurrentPlayerScore();

		setCurrentPlayerIndex(-1);

		while (getInGamePlayersNumber() > 0) {
			inGamePlayers.remove(0);
		}

	}

	/**
	 * The method updateCurrentPlayerScore updates the current player score
	 * 
	 * 
	 * 
	 */
	private void updateCurrentPlayerScore() {

		if (getCurrentPlayer() != null) {
			updatePlayerScore(getCurrentPlayer());
		}

	}

	/**
	 * The method updatePlayerScore updates the score of a player
	 * 
	 * @param thePlayer
	 *            whose score is to be calculated
	 * 
	 * 
	 */
	private void updatePlayerScore(Player thePlayer) {

		if (thePlayer == null) {
			throw new NullPointerException();
		}

		HighscoreManager.getInstance().setScore(thePlayer.getUsername(),
				thePlayer.getCurrentSpecies().getName(),
				thePlayer.getCurrentSpecies().computeScore());
	}

	/**
	 * The method setGameStateWaitingForPlayerConfirmation sets the game state
	 * to {@code GameState#WAITING_TURN_CONFIRMATION}
	 * 
	 * 
	 * @see GameState
	 * 
	 * 
	 */
	private void setGameStateWaitingForPlayerConfirmation() {
		changeOperations();
		swapCurrentPlayer();
		notifyClients();
		if (getInGamePlayersNumber() != 0)
			getLogger().info(
					"Now the game is waiting for the player "
							+ getCurrentPlayer().getUsername()
							+ " to confirm his/her turn");

		state = GameState.WAITING_TURN_CONFIRMATION;
		startTurnConfirmationTimer();
	}

	/**
	 * The method setupTimers sets up the game timers
	 * 
	 * 
	 * 
	 */
	private void setupTimers() {

		turnConfirmationTimerTask = new TurnConfirmationTimerTask();
		turnConfirmationTimer = new Timer();
		turnConfirmationTimerIsRunning = false;

		turnExecutionTimerTask = new TurnExecutionTimerTask();
		turnExecutionTimer = new Timer();
		turnExecutionTimerIsRunning = false;

	}

	/**
	 * The method stopAllTimers stops all the game timers
	 * 
	 * 
	 * 
	 */
	private void stopAllTimers() {
		stopTurnExecutionTimer();
		stopTurnConfirmationTimer();
	}

	/**
	 * The method startTurnExecutionTimer starts the turn execution timer
	 * 
	 * 
	 * 
	 */
	private void startTurnExecutionTimer() {

		if (turnExecutionTimerIsRunning == false) {
			turnExecutionTimer.schedule(new TurnExecutionTimerTask(),
					ServerInterface.TURN_EXECUTION_DELAY);
			turnExecutionTimerIsRunning = true;
		} else {
			Error e = new Error(
					"You have to stop the existing Turn Confirmation Timer before start another timer");
			getLogger().warning(StackTraceUtils.getStackTrace(e));
			throw e;
		}
	}

	/**
	 * The method startTurnConfirmationTimer starts the turn execution timer
	 * 
	 * @throws Error
	 *             if the previous timer has not been stopped
	 * 
	 * 
	 */
	private void startTurnConfirmationTimer() throws Error {
		if (turnConfirmationTimerIsRunning == false) {

			/*
			 * if (timerForStartingGameAlreadyFired == false) {
			 * timerForStartingGameAlreadyFired = true;
			 * turnConfirmationTimer.schedule(new TurnConfirmationTimerTask(),
			 * 2000); } else {
			 */
			turnConfirmationTimer.schedule(new TurnConfirmationTimerTask(),
					ServerInterface.TURN_CONFIRMATION_DELAY);
			// }
			turnConfirmationTimerIsRunning = true;
		} else {
			Error e = new Error(
					"You have to stop the existing Turn Confirmation Timer before start another timer");
			getLogger().warning(StackTraceUtils.getStackTrace(e));
			throw e;
		}
	}

	/**
	 * The method stopTurnExecutionTimer stops the turn execution timer
	 * 
	 * 
	 * 
	 */
	private void stopTurnExecutionTimer() {
		turnExecutionTimerIsRunning = false;
		turnExecutionTimer.purge();
		turnExecutionTimer.cancel();
		turnExecutionTimer = new Timer();
	}

	/**
	 * The method stopTurnConfirmationTimer stops the turn confirmation timer
	 * 
	 * 
	 * 
	 */
	private void stopTurnConfirmationTimer() {
		turnConfirmationTimerIsRunning = false;
		turnConfirmationTimer.purge();
		turnConfirmationTimer.cancel();
		turnConfirmationTimer = new Timer();
	}

	/**
	 * The method runTaskTurnConfirmationTimer contains the task of the turn
	 * conformation timer
	 * 
	 */
	void runTaskTurnConfirmationTimer() {

		if (turnConfirmationTimerIsRunning == false)
			return;

		getLogger().warning("END OF THE PLAYER TURN CONFIRMATION PERIOD.");
		changeState(GameState.WAITING_TURN_CONFIRMATION);

	}

	/**
	 * The method notifyClients notifies all the in game players that the turn
	 * is changing
	 * 
	 * 
	 * 
	 */
	private void notifyClients() {

		for (ClientHandler clientHandler : changeTurnListeners.values()) {
			clientHandler.changeTurn(getCurrentPlayer().getUsername());
		}/*
		 * 
		 * Thread queryThread = new Thread() {
		 * 
		 * public void run() {
		 * 
		 * for (ClientHandler clientHandler : changeTurnListeners.values()) {
		 * //getLogger().severe("Writing to the client: "+clientHandler.)
		 * clientHandler.changeTurn(getCurrentPlayer().getUsername()); } }
		 * 
		 * }; queryThread.start();
		 */
	}

	/**
	 * The method runTaskTurnExecutionTimer contains the task of the turn
	 * execution timer
	 * 
	 * 
	 * 
	 */
	void runTaskTurnExecutionTimer() {

		if (turnExecutionTimerIsRunning == false)
			return;

		getLogger().warning("END OF THE PLAYER TURN.");
		changeState(GameState.WAITING_TURN_CONFIRMATION);

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see logging.Loggable#setupLogger()
	 */
	@Override
	public void setupLogger() {
		logger = Logger.getLogger("server.gameplay");
		logger.setParent(Logger.getLogger("server.main"));
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

	public void setupJdkLoggerHandler() {
		ServerMainWindow.addNewLoggerTextPane(getLogger(), "Gameplay");
	}

}

/**
 * GameState is an enumerator which contains the possible game states
 */
enum GameState {
	STOP, WAITING_TURN_CONFIRMATION, TURN_EXECUTION;
}

final class TurnExecutionTimerTask extends TimerTask {
	public void run() {
		Game.getInstance().runTaskTurnExecutionTimer();
	}
}

final class TurnConfirmationTimerTask extends TimerTask {
	public void run() {
		Game.getInstance().runTaskTurnConfirmationTimer();
	}
}
