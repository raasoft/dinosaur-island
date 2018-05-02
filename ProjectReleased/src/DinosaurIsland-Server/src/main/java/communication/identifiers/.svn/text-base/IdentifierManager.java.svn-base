package communication.identifiers;

import java.util.*;
import exceptions.*;

/**
 * <b>Overview</b><br>
 * <p>
 * IdentifierManager is a class that manages a <i>finite</i> pool of String, created ensuring that each identifier is unique in the pool. <br>
 * </p>
 *
 * <b>Responsibilities</b><br>
 * <p>
 * It gives the possibility to:
 * <ul>
 * <li> Assign an identifier to an object
 * <li> Remove an identifier from an object
 * <li> Get a reference to an object from its identifier 
 * <li> Get an identifier from a reference to an object
 * </ul>
 * </p>
 * <b>Collaborators</b><br>
 * <p>
 * The class manages a pool of {@link String}.
 * </p>
 * 
 * @author	RAA
 * @version 1.6
 * @since	May 21, 2011@3:18:28 PM
 * 
 * @see String
 *
 */
public abstract class IdentifierManager<O> {

	private Hashtable<O, String> keysMap;
	private Stack<String> keysPool;
	private Hashtable<String, O> objectsMap;

	
	/**
	 * @param theUniquesKeys is the number of identifiers contained in the String Pool that is being created 
	 * 
	 * @since May 21, 2011@4:00:41 PM
	 */
	public IdentifierManager(int theUniquesKeys) 
	{
			keysPool = new Stack<String>();
			keysMap = new Hashtable<O, String>();
			objectsMap = new Hashtable<String, O>();
			
			fillIdentifierPool(theUniquesKeys);
	}
	
	
	/** 
	 * The method addIdentifierToPool add an existing identifier to the identifier pool.
	 * 
	 * @param theIdentifier is the identifier that is being to be added to the pool
	 * @throws IllegalArgumentException if the identifier provided is null
	 *  
	 * @since	May 21, 2011@4:04:23 PM
	 * 
	 */
	protected void addIdentifierToPool(String theIdentifier) throws IllegalArgumentException
	{
		if (theIdentifier == null)
		{
			throw new IllegalArgumentException("The identifier to be added to the pool can't be null");
		}
		
		keysPool.add(theIdentifier);
	}
	
	/** 
	 * The method assignIdentifierToObject assign an identifier to an object.
	 *
	 * @param theObject is the object which to assign the identifier
	 * @return is the identifier assigned to the object
	 * @throws EmptyIdentifierPoolException if there are no more available identifiers in the pool
	 * @throws IllegalArgumentException if the object to whom assign the identifier is null
	 *  
	 * @since	May 21, 2011@4:07:33 PM
	 * 
	 */
	public String assignIdentifierToObject(O theObject) throws EmptyIdentifierPoolException, IllegalArgumentException
	{
		String key;
		try	{
			key = getUnusedIdentifier();
		}
		catch (EmptyStackException e) {
			throw new EmptyIdentifierPoolException("Can't get an identifier from the pool: there are no more identifiers available");
		}

		if (key == null) {
			throw new IllegalArgumentException("The object to whom assign the identifier can't be null");
		}
		
		objectsMap.put(key, theObject);
		keysMap.put(theObject, key);
		
		return key;
	}
	
	/** 
	 * The method fillIdentifierPool fills the pool with a certain number of unique identifiers.
	 * 
	 * @param theUniquesIdentifiers is the number of identifiers that must fill the pool          
	 *  
	 * @since	May 21, 2011@4:09:53 PM
	 * 
	 */
	protected abstract void fillIdentifierPool(int theUniquesIdentifiers);

	/** 
	 * The method getIdentifier returns an identifiers from the object assigned to it.
	 * 
	 * @param theObject is the object assigned to the searched identifier
	 * @return the identifier assigned to the object passed to the function
	 * @throws IdentifierNotPresentException if the object is not assigned to any identifier
	 *  
	 * @since	May 21, 2011@4:10:31 PM
	 * 
	 */
	public String getIdentifier(O theObject) throws IdentifierNotPresentException
	{
		String key = keysMap.get(theObject);
		
		if (key == null)
		{
			throw new IdentifierNotPresentException("The object doesn't have an identifier attached");
		}
		
		return key;
	}

	/** 
	 * The method getUnusedIdentifier returns the first available identifier in the identifier pool.
	 *
	 * @return the first unusued identifier in the pool
	 * @throws EmptyIdentifierPoolException if there are no available identifiers in the pool          
	 *
	 *  
	 * @since	May 21, 2011@4:12:06 PM
	 * 
	 */
	protected String getUnusedIdentifier() throws EmptyIdentifierPoolException 
	{
		String key = null;
		try
		{
			key = keysPool.pop();
		}
		catch (EmptyStackException e)
		{
			throw new EmptyIdentifierPoolException("Can't get a new identifier from the identifier pool");
		}

		return key;
	}

	/** 
	 * The method getObject returns the object assigned to a certain identifier.
	 *
	 * @param theIdentifier is the identifier assigned to the searched object
	 * @return a reference to the object which is assigned to the specified identifier
	 * @throws NotFoundException if there are no object assigned to the particular identifier specified as argument
	 *  
	 * @since	May 21, 2011@4:12:41 PM
	 * 
	 */
	public O getObject(String theIdentifier) throws NotFoundException
	{
		O object = objectsMap.get(theIdentifier);
		
		if (object == null)
		{
			throw new NotFoundException("Can't retrieve any object that are assigned to the identifier " + theIdentifier.toString());
		}

		return object;
	}
	
	

	/** 
	 * The method removeIdentifierFromObject removes the identifier from an object if the object had 
	 * an identifier assigned to it.
	 *
	 * @param theObject is the object from which remove assigned the identifier
	 * @throws IdentifierNotPresentException if the object didn't have an identifier assigned.
	 *  
	 * @since	May 21, 2011@4:17:52 PM
	 * 
	 */
	public void removeIdentifierFromObject(O theObject) throws IdentifierNotPresentException 
	{
		String key = getIdentifier(theObject);
				
		objectsMap.remove(key);
		keysMap.remove(theObject);
		
		addIdentifierToPool(key);
	}
	
	public boolean containsIdentifier(O theObject) {
		try {
			getIdentifier(theObject);
			return true;
		}
		catch (IdentifierNotPresentException e) {
			return false;
		}
	}
	
	public int getUsedIdentifiersNumber() {
		
		return objectsMap.values().size();
		
	}

}
