/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay;

/**
 * <b>Overview</b><br>
 * <p>
 * The class SpeciesCarnivorousFactory is a singleton factory that let you
 * create herbivorous species of dinosaurs.<br>
 * In order to create a species, it must be given the factory the player that
 * owns the species and the name of the species.
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * It creates a new herbivorous dinosaur species.
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * A SpeciesFactory creates {@link SpeciesHerbivore} objects.
 * </p>
 * 
 * @author RAA
 * @version 1.6
 * 
 * @see SpeciesFactory
 * @see SpeciesHerbivore
 * 
 */
public class SpeciesHerbivoreFactory extends SpeciesFactory {

	protected static final SpeciesHerbivoreFactory uniqueInstance = new SpeciesHerbivoreFactory();

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.SpeciesFactory#createSpecies(gameplay.Player,
	 * java.lang.String)
	 */
	public Species createSpecies(Player thePlayer, String theName) {
		Species newSpecies = new SpeciesHerbivore(thePlayer, theName);
		return newSpecies;
	}

	/**
	 * The method getInstance returns the unique instance of the singleton class
	 * 
	 * @return the unique instance of the singleton class
	 * 
	 */
	public static SpeciesHerbivoreFactory getInstance() {
		return uniqueInstance;
	}

}
