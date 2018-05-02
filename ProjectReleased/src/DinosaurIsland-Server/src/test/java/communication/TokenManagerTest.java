/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication;

import gameplay.Player;
import gameplay.SpeciesCarnivorousFactory;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import communication.identifiers.TokenManager;

/**
 * <b>Overview<b><br>
 * <p>
 * Description of the type.
 * This state information includes:
 * <ul>
 * <li>Element 1
 * <li>Element 2
 * <li>The current element implementation 
 *     (see <a href="#setXORMode">setXORMode</a>)
 * </ul>
 * </p>
 * <b>Responsibilities</b><br>
 * <p>
 * Other description in a separate paragraph.
 * </p>
 * <b>Collaborators</b><br>
 * <p>
 * Some important points to consider are that this is only a stub.
 * </p>
 * <b>Implementor</b> - RAA<br>
 * <b>Testor</b> - RAA<br>
 *
 * "Don't be too much inspired." - P. San Pietro
 *  
 * @author	RAA
 *
 * @version 1.0
 * @since	May 19, 2011@6:23:04 PM
 */
public class TokenManagerTest 
{
	
	TokenManager tokenManager;
	ArrayList<Player> playerList;
	final int playerNumber = 15;
	final int playerToBeRemovedNumber = 5;

	/** 
	 * The method setUp of the class TokeManagerTest 
	 * draws bla bla bla...
	 * <p>
	 * Bla bla bla... as the indicated {@link ImageObserver} bla bla bla...
	 * It is useful also insert some {@code arg1} references to the args
	 * while explaining.
	 *
	 * @throws java.lang.Exception          
	 *
	 * @param	arg4	the image observer to be notified as more
	 *					of the image is converted.  May be 
	 *          		<code>null</code>
	 *  
	 * @return  <code>true</code>	if the image is completely 
	 * 				loaded and was painted successfully; 
	 *              <code>false</code> otherwise.
	 *
	 * @see		Image
	 * @see		ImageObserver
	 *  
	 * @author	RAA
	 *
	 * @version	1.0
	 * @since	May 19, 2011@6:23:04 PM
	 * 
	 */
	
	@Before
	public void setUp() throws Exception 
	{
		playerList = new ArrayList<Player>();
		tokenManager = TokenManager.getInstance();
		
		for (int i = 0; i < playerNumber; i++)
		{
			playerList.add( new Player("RAA"+i,"password") );
			playerList.get(i).createCurrentSpecies(SpeciesCarnivorousFactory.getInstance(), "Dinosauri"+i);
		}
		
	}

	@Test
	public void testAddIdentifiersToPlayers() throws Exception
	{
		Player aPlayer;
		String token;
		
		/* We try to assign a token to all players, but we expect to fail since the
		 * number of the tokens is smaller than the number of players.
		 */
		for (Player player: playerList)
		{
			try
			{
				token = (String) tokenManager.assignIdentifierToObject(player);
				System.out.println("The player token is: " + token);
			}
			catch (Exception e)
			{
				System.out.println("There are no more tokens available to the player");
			}
		}
		
		/* Now that we assigned all the tokens available, let's some players and then freeing some tokens */
		for (int i = 1; i <= playerToBeRemovedNumber; i++)
		{
			aPlayer = playerList.get(0);
			playerList.remove(0);
			tokenManager.removeIdentifierFromObject(aPlayer);
		}

		token = (String) tokenManager.assignIdentifierToObject(new Player("LOL","ASD"));
		System.out.println("The player token is: " + token);
	
	}

	

}
