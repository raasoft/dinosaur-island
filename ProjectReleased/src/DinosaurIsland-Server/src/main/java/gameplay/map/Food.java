package gameplay.map;

import java.io.Serializable;
import java.util.Random;

/**
 * <b>Overview</b><br>
 * <p>
 * The class Food stores information about the units of food present in the game
 * <br>
 * There can be two sorts of Food: Vegetation and Carrion; <br>
 * Food is the parent class of the {@link Vegetation} and {@link Carrion}
 * classes. <br>
 * Food stores information about:
 * <ul>
 * <li>Its amount of energy
 * <li>Its maximum amount of energy
 * <li>If it is edible by herbivores
 * <li>If it is edible by carnivorous
 * </ul>
 * </p>
 * <b>Responsibilities</b><br>
 * <p>
 * The class Food is responsible for storing information about the Food present
 * in a Cell of the Map.
 * </p>
 * <b>Collaborators</b><br>
 * <p>
 * Food is a class that only stores information: it cannot decide where to be
 * placed. All the decision are made by the {@link Cell} class.
 * </p>
 * 
 * @author AXXO
 * @version 1.1
 */

public abstract class Food implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int energy;
	protected int energyMax;
	protected boolean edibleByHerbivores;
	protected boolean edibleByCarnivorous;

	/**
	 * @return energy The energy of the food
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * @return energyMax The maximum energy of the food
	 */
	public int getEnergyMax() {
		return energyMax;
	}

	/**
	 * @param theValue
	 *            The new value of energy of the food
	 * @throws IllegalArgumentException
	 *             if the new energy value is negative
	 */
	public void setEnergy(int theValue) throws IllegalArgumentException {

		if (theValue < 0) {
			throw new IllegalArgumentException("Negative energy is not allowed");
		}

		else {
			energy = theValue;
		}

		/*
		 * Energy must not exceed the maximum amount of energy of the food
		 */
		if (energy > energyMax) {
			energy = energyMax;
		}

	}

	/**
	 * @param theEnergyMax
	 *            the desired value for the maximum amount of energy
	 * @throws IllegalArgumentException
	 *             if the value given as parameter is negative
	 */
	public void setEnergyMax(int theEnergyMax) throws IllegalArgumentException {

		if (theEnergyMax >= 0) {
			energyMax = theEnergyMax;
		}

		else {
			throw new IllegalArgumentException(
					"A negative energyMax value is not allowed");
		}

	}

	/**
	 * @return true if the food is edible by herbivores
	 */
	public boolean isEdibleByHerbivores() {
		return edibleByHerbivores;
	}

	/**
	 * The method setEdibleByHerbivores sets that the food is edible by
	 * herbivores
	 * 
	 */
	void setEdibleByHerbivores() {
		edibleByHerbivores = true;
	}

	/**
	 * @return true if the food is edible by carnivorous
	 */
	public boolean isEdibleByCarnivorous() {
		return edibleByCarnivorous;
	}

	/**
	 * The method setEdibleByCarnivorous sets that the food is edible by
	 * carnivorous
	 * 
	 */
	void setEdibleByCarnivorous() {
		edibleByCarnivorous = true;
	}

	/**
	 * This is the abstract method updateTurnEnergy. It determines the energy
	 * variation of food at the end of every turn </br>It will be implemented by
	 * {@linkVegetation} and {@linkCarrion} classes.
	 * 
	 */
	public abstract void updateTurnEnergy();

}

/**
 * Vegetation class is a subclass of {@linkFood} and represents the food edible
 * by herbivores.
 * 
 * @author AXXO
 * @version 1.1
 * 
 */
class Vegetation extends Food {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor sets the maximum energy to a random value between 150 and 350 <br>
	 * and that it is edible by herbivores
	 * 
	 */
	public Vegetation() {

		/*
		 * We declare a random object
		 */
		Random random = new Random();

		/*
		 * We flip a coin to decide whether the maximum energy value will be
		 * greater or less than 250.
		 */
		if (random.nextBoolean()) {
			/*
			 * We use the method random.nexInt(101) to pick a random number from
			 * 0 to 100
			 */
			setEnergyMax(250 + random.nextInt(101));
		}

		else {
			setEnergyMax(250 - random.nextInt(101));
		}

		setEnergy(getEnergyMax());

		setEdibleByHerbivores();

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Food#updateTurnEnergy() The energy of the vegetation is
	 * increased by energyMax/20 every turn
	 */
	public void updateTurnEnergy() {

		energy += energyMax / 20;

		/*
		 * Energy must not exceed energyMax
		 */
		if (energy > energyMax) {
			energy = energyMax;
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Vegetation clone() {

		Vegetation vegetation = new Vegetation();
		vegetation.setEnergy(getEnergy());
		vegetation.setEnergyMax(getEnergyMax());

		return vegetation;
	}

}

/**
 * Carrion class is a subclass of {@linkFood} and represents the food edible by
 * carnivorous.
 * 
 * @author AXXO
 * @version 1.1
 * 
 */
class Carrion extends Food {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor sets the maximum energy to a random value between 350 and 650 <br>
	 * and that it is edible by carnivorous
	 * 
	 */
	public Carrion() {

		/*
		 * We declare a random object
		 */
		Random random = new Random();

		/*
		 * We flip a coin to decide whether the maximum energy value will be
		 * greater or less than 250.
		 */
		if (random.nextBoolean()) {
			/*
			 * We use the method random.nexInt(151) to pick a random number from
			 * 0 to 100
			 */
			setEnergyMax(500 + random.nextInt(151));
		}

		else {
			setEnergyMax(500 - random.nextInt(151));
		}

		setEnergy(getEnergyMax());

		setEdibleByCarnivorous();
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.Food#updateTurnEnergy() The energy of the carrion is
	 * decreased by energyMax/20 every turn
	 */
	public void updateTurnEnergy() {

		energy -= energyMax / 20;
	}

	public Carrion clone() {

		Carrion carrion = new Carrion();
		carrion.setEnergy(getEnergy());
		carrion.setEnergyMax(getEnergyMax());

		return carrion;
	}

}
