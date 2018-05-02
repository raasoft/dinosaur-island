package communication.identifiers;

import java.util.Calendar;

import communication.identifiers.IdentifierManager;
import gameplay.Player;



/**
 * <b>Overview</b><br>
 * <p>
 * TokenManager is a singleton class that manages a <i>finite</i> pool of Token, created ensuring that each token is unique in the pool. <br>
 * </p>
 *
 * <b>Responsibilities</b><br>
 * <p>
 * It gives the possibility to:
 * <ul>
 * <li> Assign a token to a player
 * <li> Remove a token from an player
 * <li> Get a reference to a player from its token 
 * <li> Get a token from a reference to a player
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
public class TokenManager extends IdentifierManager<Player> {

	static int tokenInPool = 20; /* This is subject to change */
	private static final TokenManager uniqueInstance = new TokenManager(tokenInPool); 
	
	/**
	 * @param theUniquesKeys is the number of Token contained in the Token Pool that is being created 
	 * 
	 * @since May 21, 2011@4:00:41 PM
	 */
	private TokenManager(int theUniquesIdentifiers)
	{
		super(theUniquesIdentifiers);
	}
	
	/** 
	 * The method getInstance let to access the TokenManager singleton.
	 *
	 * @return a reference to the TokenManager
	 *
	 * @since	May 21, 2011@5:16:41 PM
	 * 
	 */
	public static TokenManager getInstance() 
	{
		return uniqueInstance;
	}
	
	/* The method fillIdentifierPool fills the pool with a certain number of unique Token.
	 * 
	 * @see communication.IdentifierManager#fillIdentifierPool(int)
	 * 
	 */
	protected void fillIdentifierPool(int theUniquesIdentifiers)
	{
		
		/* Create the uniques token to be assigned once a player logs in */
		for (Integer i = 1; i <= theUniquesIdentifiers; i++)
		{
			String date =  Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "_" + Calendar.getInstance().get(Calendar.MONTH) + "_" + Calendar.getInstance().get(Calendar.YEAR);
			addIdentifierToPool(new String(i.toString() + date));
		}
	}
	
}
