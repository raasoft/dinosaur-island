package gameplay;

import java.io.Serializable;
import java.util.*;

import util.Position;
import exceptions.*;
import gameplay.map.Food;
import gameplay.map.Map;

/**
 * <b>Overview</b><br>
 * <p>
 * In the game, the class Dinosaur stores information about each dinosaur of a
 * certain player. A Dinosaur can be a Carnivorous or a Herbivore, so Dinosaur
 * is the parent class for the {@link Carnivorous} and {@link Herbivore}
 * classes.
 * </p>
 * <b>Responsibilities</b><br>
 * <p>
 * The class Dinosaur is responsible to store information about the dinosaurs
 * owned by the player. A dinosaur stores information about:
 * <ul>
 * <li>Its species
 * <li>Its age
 * <li>Its maximum age
 * <li>Its energy
 * <li>Its size
 * <li>Its maximum size
 * <li>Its position in the map
 * <li>Its ability to move
 * <li>Its ability to grow or lay an egg
 * </ul>
 * A dinosaur can be created only inside a {@link Species}, so its constructor
 * is protected. To create a dinosaur you have to call the
 * {@code addDinosaur(Species theSpecies)} method of the {@link Species} class.
 * </p>
 * <b>Collaborators</b><br>
 * <p>
 * A dinosaur is a class that only stores information: it cannot decide where to
 * move or decide to move at all. All the decision are made by the {@link Map}
 * class.
 * </p>
 * 
 * @author RAA
 * @version 1.6
 * 
 */
public abstract class Dinosaur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int age;
	private int ageMax;
	private int energy;
	private Position position;
	private boolean ableToMove;
	private boolean ableToAct;
	private int size;
	private int sizeMax;
	private Species species;

	/**
	 * @param theSpecies
	 *            is the species of the dinosaur. The constructor is protected
	 *            because a dinosaur can only be created inside a species, so it
	 *            can be created only by a Species class.
	 */
	protected Dinosaur(Species theSpecies) {
		setSpecies(theSpecies);

		/*
		 * When a dinosaur is created, its age (expressed in turn's number
		 * played) is zero
		 */
		setAge(0);

		/*
		 * The maximum age that a dinosaur can have is computed randomly at its
		 * creation.
		 */
		setAgeMax();

		/* It sets the maximum size of the dinosaur */
		setSizeMax();

		/* When a dinosaur is created, it has a unitary size */
		setSize(1);

		/* When a dinosaur is created, it has a starting energy of 750 points */
		int startEnergy = 750;
		setEnergy(startEnergy);

		/*
		 * When a dinosaur is created, it is not placed in the map, so we'll set
		 * is position to null.
		 */
		setPosition(null);

		/* When a dinosaur is created, it is able to grow or lay an egg */
		setAbleToAct(true);

		/* When a dinosaur is created it is able to move */
		setAbleToMove(true);

	}

	/**
	 * @return true if the dinosaur is aggressive
	 * @return false if the dinosaur is not aggressive
	 */
	protected abstract boolean isAggressive();

	/**
	 * @return true if the dinosaur is able to move
	 */
	public boolean isAbleToMove() {
		return ableToMove;
	}

	/**
	 * @param theValue
	 *            sets the ability of the dinosaur to move
	 */
	public void setAbleToMove(boolean theValue) {
		ableToMove = theValue;
	}

	/**
	 * @return true if the dinosaur is able to grow or an egg
	 */
	public boolean isAbleToAct() {
		return ableToAct;
	}

	/**
	 * @param theValue
	 *            sets the ability of the dinosaur to grow or lay an egg
	 */
	public void setAbleToAct(boolean theValue) {
		ableToAct = theValue;
	}

	/**
	 * @param theFood
	 *            the type of food to check
	 * @return true if the dinosaur can eat the food given as parameter
	 * 
	 * @see Food
	 */
	public abstract boolean canEat(Food theFood);

	/**
	 * The method getAge returns the age of the dinosaur, that is the number of
	 * turns played. A dinosaur can play from 0 to {@link #ageMax} turns.
	 * 
	 * @return the age of the dinosaur, that is the number of turns played.
	 * 
	 */
	public int getAge() {
		return age;
	}

	/**
	 * The method getAgeMax returns the maximum age a dinosaur can live,
	 * expressed in number of turns that it can play.<br>
	 * After reaching that age, the dinosaur will die.
	 * 
	 * @return the maximum age of the dinosaur.
	 * 
	 */
	public int getAgeMax() {
		return ageMax;
	}

	/**
	 * The method getEnergy returns the current energy of the dinosaur, that can
	 * be in the range from 0 to {@link #ageMax}. <br>
	 * If the energy will be minor or equal to 0, the dinosaur will die.
	 * 
	 * @return the current energy of the dinosaur.
	 * 
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * The method getEnergyMax returns the maximum energy that a dinosaur can
	 * store.<br>
	 * So if a dinosaur will eat something that should increment its energy, if
	 * it has already reached its maximum energy the energy level will remain
	 * the same.
	 * 
	 * @return the maximum energy value that a dinosaur can store.
	 * 
	 */
	public int getEnergyMax() {
		/*
		 * The maximum energy storable by a dinosaur is obtained by the
		 * expression "size * multiplier"
		 */
		int multiplier = 1000;
		return size * multiplier;
	}

	/**
	 * The method getGrowthEnergy returns the energy needed for the dinosaur to
	 * grow up and enlarge its size by one unity.
	 * 
	 * @return the energy needed for the dinosaur to grow up.
	 * 
	 */
	public int getGrowthEnergy() {
		/*
		 * The growth energy of the dinosaur is obtained by the expression
		 * "energy max / 2
		 */
		return getEnergyMax() / 2;
	}

	/**
	 * The method getEggDepositionEnergy returns the amount of energy necessary
	 * to lay an egg
	 * 
	 * @return 1500
	 * 
	 */
	public int getEggDepositionEnergy() {
		return 1500;
	}

	/**
	 * The method getId returns the DinosaurID associated to the dinosaur. A
	 * DinosaurID is associated when a dinosaur starts to play in a game. When a
	 * dinosaur leaves a game, its DinosaurID will reassigned to another
	 * dinosaur.
	 * 
	 * @return the DinosaurID associated to the dinosaur
	 * @throws IdentifierNotPresentException
	 *             - if the dinosaur has no DinosaurID assigned
	 * 
	 * @see DinosaurIDManager
	 * 
	 */
	public String getId() throws IdentifierNotPresentException {

		return (String) DinosaurIDManager.getInstance().getIdentifier(this);

	}

	/**
	 * The method getMaxStep returns the the maximum distance a dinosaur can
	 * travel.
	 * 
	 * @return the maximum distance a dinosaur can travel
	 * 
	 */
	public abstract int getMaxStep();

	/**
	 * The method getPosition returns the current position of the dinosaur in
	 * the map as a {@link Position} object.<br>
	 * When a dinosaur is created its position is null, that means that is not
	 * placed on the map.
	 * 
	 * @return the current position of a dinosaur in the map
	 * 
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * The method getSize returns the size of the dinosaur, a number in the
	 * range from 1 to 5.
	 * 
	 * @return the size of the dinosaur
	 * 
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the maximum size of the dinosaur
	 * 
	 */
	public int getSizeMax() {
		return sizeMax;
	}

	/**
	 * The method getSpecies returns the parent species of the dinosaur.<br>
	 * A dinosaur MUST always be assigned to a species. Otherwise a dinosaur
	 * would not exist.
	 * 
	 * @return the species the dinosaur belogs to
	 * 
	 * @see Species
	 * 
	 */
	public Species getSpecies() {
		return species;
	}

	/**
	 * The method getStrength returns the strength that a dinosaur can exhibit
	 * during a fight against another dinosaur.
	 * 
	 * @return the strength that a dinosaur can exhibit during a fight
	 * 
	 */
	public abstract int getStrength();

	/**
	 * The method setAge sets the age of the dinosaur which is the number of
	 * turns played by the dinosaur.<br>
	 * The age must be a non-negative value.
	 * 
	 * @param theAge
	 *            the new age of the dinosaur
	 * @throws IllegalArgumentException
	 *             - if {@code theAge} is a negative number.
	 * 
	 */
	public void setAge(int theAge) throws IllegalArgumentException {
		if (theAge < 0) {
			throw new IllegalArgumentException(
					"The age of the dinosaur cannot be negative");
		}

		age = theAge;
	}

	/**
	 * The method setAgeMax sets the maximum age a dinosaur can live, that is
	 * the maximum number of turns that a dinosaur can play.<br>
	 * There are no arguments since the maximum age is computed randomly when a
	 * dinosaur is created.
	 * 
	 */
	protected void setAgeMax() {
		Random rnd = new Random();
		int minAge = 24;
		int maxAge = 36;
		ageMax = rnd.nextInt(maxAge - minAge + 1) + minAge;
	}

	/**
	 * The method setEnergy sets the energy of the dinosaur.<br>
	 * The energy must be a non-negative value.
	 * 
	 * @param theEnergy
	 *            the new energy of the dinosaur
	 * @throws IllegalArgumentException
	 *             if {@code theEnergy} is a negative number.
	 * 
	 */
	public void setEnergy(int theEnergy) throws IllegalArgumentException {
		if (theEnergy < 0) {
			throw new IllegalArgumentException(
					"The energy of the dinosaur cannot be negative");
		}

		energy = theEnergy;

		System.out.println("Setted the energy!" + energy + "," + theEnergy);

		/*
		 * The dinosaur's energy can't exceed its maximum amount of energy
		 */
		if (energy > getEnergyMax()) {
			energy = getEnergyMax();
		}
	}

	/**
	 * The method setId returns a string generated by
	 * {@code DinosaurIDManager#assignIdentifierToObject(Dinosaur)} which is the
	 * alphanumeric identifier of the dinosaur
	 * 
	 * @return the alphanumeric string representing the dinosaur ID
	 * @throws Exception
	 *             if the dinosaur already has an ID assigned
	 * 
	 * @see DinosaurIDManager
	 * 
	 */
	public String setId() throws Exception {

		if (getId() == null) {
			String id = (String) DinosaurIDManager.getInstance()
					.assignIdentifierToObject(this);
			return id;
		} else {
			throw new Exception("The dinosaur has already an ID");
		}

	}

	/**
	 * The method setPosition sets the position given as parameter to the
	 * dinosaur
	 * 
	 * @param thePosition
	 *            the new positiom of the dinosaur
	 * 
	 * @see Position
	 */
	public void setPosition(Position thePosition) {
		position = thePosition;
	}

	/**
	 * The method setSize sets the size of the dinosaur. The size must be an
	 * integer between 1 and 5 (included).
	 * 
	 * @param theSize
	 *            is the new size of the dinosaur
	 * @throws IllegalArgumentException
	 *             if {@code theSize} is out of range (theSize < 1 || theSize >
	 *             5)
	 * 
	 */
	public void setSize(int theSize) throws IllegalArgumentException {

		if (theSize < 1) {
			throw new IllegalArgumentException(
					"The size of the dinosaur cannot be smaller than 1");
		}
		if (theSize > sizeMax) {
			throw new IllegalArgumentException(
					"The size of the dinosaur cannot be greater than the maximum size");
		}

		size = theSize;
	}

	/**
	 * It sets the maximum size of the dinosaur to 5
	 */
	private void setSizeMax() {
		sizeMax = 5;
	}

	/**
	 * The method setSpecies sets the species of the dinosaur. You MUST assign a
	 * dinosaur to a species, since without it the dinosaur would not exist.
	 * 
	 * @param theSpecies
	 *            the species which will be assigned to the dinosaur
	 * @throws IllegalArgumentException
	 *             if the species provided is null
	 * 
	 */
	private void setSpecies(Species theSpecies) throws IllegalArgumentException {

		if (theSpecies == null) {
			throw new IllegalArgumentException(
					"A dinosaur must belong to a species");
		}

		species = theSpecies;
	}

	abstract public String getSymbol();

}

/**
 * <b>Overview</b><br>
 * <p>
 * Herbivore is a subclass of the Dinosaur class.<br>
 * It represents a herbivore dinosaur.<br>
 * It implements the abstract methods of the parent class in order to represent
 * a usable object in the game and nothing more.
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * See {@link Dinosaur}.
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * See {@link Dinosaur}.
 * </p>
 * 
 * @see Dinosaur
 * 
 * @author RAA
 * @version 1.6
 */
class Herbivore extends Dinosaur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param theSpecies
	 *            assigned to the dinosaur
	 */
	public Herbivore(Species theSpecies) {

		super(theSpecies);

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Dinosaur#getMaxStep()
	 */
	public int getMaxStep() {

		/* The max distance that a herbivorous dinosaur can go is 3 cells */
		return 2;
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Dinosaur#getStrength()
	 */
	public int getStrength() {

		/*
		 * The strength of the herbivorous dinosaur is obtained by the
		 * expression "energy * size"
		 */
		return getEnergy() * getSize();

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Dinosaur#isAggressive()
	 */
	@Override
	protected boolean isAggressive() {

		/*
		 * An herbivorous dinosaur is not aggressive
		 */
		return false;
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Dinosaur#canEat(gameplay.map.Food)
	 */
	@Override
	public boolean canEat(Food theFood) {
		if (theFood.isEdibleByHerbivores()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Dinosaur#getSymbol()
	 */
	@Override
	public String getSymbol() {
		return "e";
	}

}

/**
 * <b>Overview</b><br>
 * <p>
 * Carnivorous is a subclass of the Dinosaur class.<br>
 * It represents a carnivorous dinosaur.<br>
 * It implements the abstract methods of the parent class in order to represent
 * a usable object in the game and nothing more.
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * See {@link Dinosaur}.
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * See {@link Dinosaur}.
 * </p>
 * 
 * @see Dinosaur
 * 
 * @author RAA
 * @version 1.6
 */
class Carnivorous extends Dinosaur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param theSpecies
	 *            assigned to the dinosaur
	 */
	public Carnivorous(Species theSpecies) {

		super(theSpecies);

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Dinosaur#getMaxStep()
	 */
	public int getMaxStep() {

		/* The max distance that a carnivorous dinosaur can go is 3 cells */
		return 3;
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Dinosaur#getStrength()
	 */
	public int getStrength() {

		/*
		 * The strength of the carnivorous dinosaur is obtained by the
		 * expression "2 * energy * size"
		 */
		return 2 * getEnergy() * getSize();

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Dinosaur#isAggressive()
	 */
	@Override
	protected boolean isAggressive() {

		/*
		 * A carnivorous dinosaur is aggressive, if it encounters another
		 * dinosaur they fight
		 */
		return true;
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Dinosaur#canEat(gameplay.map.Food)
	 */
	@Override
	public boolean canEat(Food theFood) {
		if (theFood.isEdibleByCarnivorous()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Dinosaur#getSymbol()
	 */
	@Override
	public String getSymbol() {
		return "c";
	}

}