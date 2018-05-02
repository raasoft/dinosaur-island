package gameplay;

import communication.identifiers.IdentifierManager;


/**
 * <b>Overview</b><br>
 * <p>
 * DinosaurIDManager is a singleton class that manages a <i>finite</i> pool of String, created ensuring that each String is unique in the pool. <br>
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
public class DinosaurIDManager extends IdentifierManager<Dinosaur> {
	
	final static int dinosaurIDInPool = 40; 
	private static final DinosaurIDManager uniqueInstance = new DinosaurIDManager(dinosaurIDInPool);  /* This is subject to change */
	
	/**
	 * @param theUniquesKeys is the number of String contained in the String Pool that is being created 
	 * 
	 * @since May 21, 2011@4:00:41 PM
	 */
	private DinosaurIDManager(int theUniquesIdentifiers) {
		super(theUniquesIdentifiers);
	}
	
	/** 
	 * The method getInstance let to access the DinosaurIDManager singleton.
	 *
	 * @return a reference to the DinosaurIDManager
	 *
	 * @since	May 21, 2011@5:16:41 PM
	 * 
	 */
	public static DinosaurIDManager getInstance() 
	{
		return uniqueInstance;
	}
	
	
	/* The method fillIdentifierPool fills the pool with a certain number of unique String.
	 * 
	 * @see communication.IdentifierManager#fillIdentifierPool(int)
	 * 
	 */
	protected void fillIdentifierPool(int theUniquesIdentifiers)
	{
		/* Create the uniques token to be assigned once a dinosaur enter the game */
		for (Integer i = 1; i < theUniquesIdentifiers; i++)
		{
			addIdentifierToPool(new String(i.toString()));
		}
	}
}
