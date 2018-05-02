package gameplay;

import java.io.Serializable;
import java.util.*;

import util.Position;
import exceptions.*;
import gameplay.map.Map;

/**
 * <b>Overview</b><br>
 * <p>
 * The Species class contains informations about a species of dinosaurs owned by
 * a player Species is the parent class of {@link SpeciesCarnivorous} and
 * {@link SpeciesHerbivore} This includes:
 * <ul>
 * <li>Its current turn number
 * <li>The list of dinosaurs
 * <li>Its maximum size
 * <li>The list of cells discovered
 * <li>Its name
 * <li>The player who owns it
 * <li>Its score
 * <li>Its maximum number of turns
 * </ul>
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * {@see Species}, {@see Dinosaur}
 * </p>
 * 
 * @author AXXO
 * @version 1.0
 * 
 */
public abstract class Species implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected final int familySizeMax = 5;
	protected ArrayList<Dinosaur> family;
	protected int turnMax = 120;
	protected int currentTurn = 0;
	protected Player player;
	protected int score;

	protected boolean[][] mapAlreadyDiscovered;

	/**
	 * @param thePlayer
	 *            owning the species
	 * @param theName
	 *            of the species
	 */
	public Species(Player thePlayer, String theName) {
		family = new ArrayList<Dinosaur>();

		setPlayer(thePlayer);
		setName(theName);

		/* The maximum turn a species can live: default value 120 turns */
		setTurnMax(120);

		/* It initializes the mapAlreadyDiscovered matrix */
		mapAlreadyDiscovered = null;
	}

	/**
	 * @return the current turn of the species
	 * 
	 */
	public int getCurrentTurn() {
		return currentTurn;
	}

	/**
	 * The method incrementTurn increments the species current turn
	 * 
	 * @return the current turn
	 * @throws MaxSpeciesAgeExceededException
	 *             if the species reaches its maximum age
	 * 
	 */
	public int incrementTurn() throws MaxSpeciesAgeExceededException {
		/*
		 * Try to increment the current turn: if the species has reached its
		 * maximum turn, throw an exception.
		 */
		if (currentTurn == getTurnMax()) {
			throw new MaxSpeciesAgeExceededException(
					"The species has reached its maximum number of turns playable.");
		}

		currentTurn++;
		return currentTurn;
	}

	/**
	 * @param theValue
	 *            the new maximum number of turns value to set
	 * @throws IllegalArgumentException
	 *             if the valuegiven as parameter is null
	 * 
	 */
	private void setTurnMax(int theValue) throws IllegalArgumentException {
		if (theValue <= 0) {
			throw new IllegalArgumentException(
					"The maximum number of turns that a species can play can't be negative or zero");
		}
		turnMax = theValue;

	}

	/**
	 * @return the player owning the species
	 * 
	 * 
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param thePlayer
	 *            the player who will own the species
	 * @throws IllegalArgumentException
	 *             if the player is null
	 * 
	 * 
	 */
	private void setPlayer(Player thePlayer) throws IllegalArgumentException {
		if (thePlayer == null) {
			throw new IllegalArgumentException(
					"A species must be owned by a player");
		}
		player = thePlayer;
	}

	/**
	 * @return the maximum number of turns the species can live
	 * 
	 */
	public int getTurnMax() {
		return turnMax;
	}

	/**
	 * @return the name of the species
	 * 
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param theName
	 *            the name to be set to the species
	 * @throws IllegalArgumentException
	 * 
	 */
	public void setName(String theName) throws IllegalArgumentException {
		if (theName == null) {
			throw new IllegalArgumentException(
					"The name of the species player owner mustn't be null");
		}

		if (theName.length() == 0) {
			throw new IllegalArgumentException(
					"The name of the species player owner mustn't be empty");
		}

		name = theName;
	}

	/**
	 * @return the number of dinosaur of the species
	 * 
	 * 
	 */
	public int getSize() {
		return family.size();
	}

	public abstract Dinosaur addDinosaur() throws SpeciesIsFullException;

	/**
	 * 
	 * The method removeDinosaur removes the dinosaur given as parameter from
	 * its species
	 * 
	 * @param theDinosaur
	 *            the dinosaur to remove
	 * 
	 * @since Jun 9, 2011@11:08:59 PM
	 * 
	 */
	void removeDinosaur(Dinosaur theDinosaur) {
		family.remove(theDinosaur);
	}

	/**
	 * The method updateFOV updates the list of cells visible by a dinosaur and
	 * adds them to the list
	 * 
	 * @param theDinosaur
	 * 
	 * 
	 */
	public void updateFOV(Dinosaur theDinosaur) {

		/**
		 * This first block initializes the bidimensional array
		 * mapAlreadyDiscovered
		 */
		if (mapAlreadyDiscovered == null) {

			int maxX = (int) Map.getInstance().getDimension().getX();
			int maxY = (int) Map.getInstance().getDimension().getY();

			mapAlreadyDiscovered = new boolean[maxX][maxY];
			for (int i = 0; i < maxX; i++) {
				for (int j = 0; j < maxY; j++) {
					mapAlreadyDiscovered[i][j] = false;
				}
			}
		}

		int dinoX = theDinosaur.getPosition().getIntX();
		int dinoY = theDinosaur.getPosition().getIntY();

		int distance = 2 + (int) Math.floor(theDinosaur.getSize() / 2);

		for (int i = -distance; i <= distance; i++) {
			for (int j = -distance; j <= distance; j++) {
				if (Map.getInstance().validatePosition(
						new Position(dinoX + i, dinoY + j))) {
					mapAlreadyDiscovered[dinoX + i][dinoY + j] = true;
				}
			}
		}
	}

	/**
	 * @return the bidimensional array of cells visible by the dinosaurs
	 * @throws NullPointerException
	 * 
	 * 
	 */
	public boolean[][] getFOV() throws NullPointerException {

		if (mapAlreadyDiscovered == null) {

			int maxX = (int) Map.getInstance().getDimension().getX();
			int maxY = (int) Map.getInstance().getDimension().getY();

			mapAlreadyDiscovered = new boolean[maxX][maxY];
		}

		return mapAlreadyDiscovered;
	}

	/**
	 * The method computeScore calculates the score of the species based on the
	 * size of each dinosaur
	 * 
	 * @return the calculated score
	 * 
	 * 
	 */
	public int computeScore() {

		int theScore = getScore();

		for (Dinosaur dinosaur : family) {
			theScore += dinosaur.getSize() + 1;
		}

		setScore(theScore);

		return theScore;
	}

	/**
	 * @return the list of dinosaurs present in the species
	 * 
	 */
	public ArrayList<Dinosaur> getDinosaurList() {

		return new ArrayList<Dinosaur>(this.family);

	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	protected void setScore(int theValue) {
		score = theValue;
	}
}

class SpeciesCarnivorous extends Species {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see #Species(Player, String)
	 */
	public SpeciesCarnivorous(Player thePlayer, String theName) {
		super(thePlayer, theName);
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Species#addDinosaur()
	 */
	public Dinosaur addDinosaur() throws SpeciesIsFullException {
		Dinosaur newborn = new Carnivorous(this);

		if (getSize() == familySizeMax) {
			throw new SpeciesIsFullException(
					"Maximum species size reached. Cannot create a new dinosaur.");
		}

		family.add(newborn);

		return newborn;
	}

}

class SpeciesHerbivore extends Species {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see #Species(Player, String)
	 */
	public SpeciesHerbivore(Player thePlayer, String theName) {
		super(thePlayer, theName);
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Species#addDinosaur()
	 */
	public Dinosaur addDinosaur() throws SpeciesIsFullException {
		Dinosaur newborn = new Herbivore(this);

		if (getSize() == familySizeMax) {
			throw new SpeciesIsFullException(
					"Maximum species size reached. Cannot create a new dinosaur.");
		}

		family.add(newborn);

		return newborn;
	}
}
