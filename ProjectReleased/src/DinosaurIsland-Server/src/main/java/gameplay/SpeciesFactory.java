package gameplay;

/**
 * The class SpeciesFactory is a factory that let you create species of
 * dinosaurs.<br>
 * In order to create a species, it must be given the factory the player that
 * owns the species and the name of the species. </p> <b>Responsibilities</b><br>
 * <p>
 * It creates a new dinosaur species.
 * </p>
 * <b>Collaborators</b><br>
 * <p>
 * A SpeciesFactory creates {@link Species} objects.
 * </p>
 * 
 * @author RAA
 * @version 1.6
 * 
 * @see Species
 * 
 */
public abstract class SpeciesFactory {

	/**
	 * The method createSpecies creates a new species of dinosaur.
	 * 
	 * @param thePlayer
	 *            is the owner of the species.
	 * @param theName
	 *            is the name of the species created
	 * @return the new species created
	 * 
	 */
	public abstract Species createSpecies(Player thePlayer, String theName);

}
