/**
 * 
 */
package gameplay;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import exceptions.*;

/** Overview of the type
 *
 * @author RAA
 * @version 1.0
 * @since May 17, 2011
 */

public class CreateNewCustomPlayerTest extends Assert {

	/**
	 * @throws java.lang.Exception
	 */
	Player[] players;
	final int playersMax = 2;

	@Before
	public void setUp() throws Exception {

		players = new Player[playersMax];
		int i = 0;
		players[i++] = new Player("RAA","Password");
		//players[i++] = new Player("AXXO","Password");
		
	}
	
	@Test(expected=SpeciesIsFullException.class)
	public void testCreatingAndPopulatingASpecies() throws Exception
	{
		int i = 0;
		assertEquals("Check whether the username has been set correctly","RAA",players[i].getUsername());
		
		try
		{
			players[i].createCurrentSpecies(SpeciesCarnivorousFactory.getInstance(), null);	
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("We can't give a null name to a species");
		}
		
		try
		{
			players[i].createCurrentSpecies(SpeciesCarnivorousFactory.getInstance(), "");	
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("We can't give an empty name to a species");
		}
		
		players[i].createCurrentSpecies(SpeciesCarnivorousFactory.getInstance(), "I cattivi trex");	
		assertEquals("Check whether the species' name has been set correctly","I cattivi trex",players[i].getCurrentSpecies().getName());
		assertEquals("When a new dinosaur species is created, is the size of the species equals to 1?", 1, players[i].getCurrentSpecies().getSize());
		
		/* Now we try to add more than 5 dinosaur to the species: we therefore expect an exception */
		for (int j = 1; j < 6; j++)
		{
			players[i].getCurrentSpecies().addDinosaur();
			assertEquals("When adding a new dinosaur, the size of the species equals to "+(j+1)+"?", j+1, players[i].getCurrentSpecies().getSize());		
		}
		
		/* We try to compute the score of the species */
		assertEquals("After 5 dinosaurs of size 1, the score of the species is 5*(1+1) = 10",10,players[i].getCurrentSpecies().computeScore());
	
		
		/*
		 * i++;
		assertEquals("Check whether the username has been set correctly","AXXO",players[i].getUsername());
		
		players[i].createCurrentSpecies(SpeciesHerbivoreFactory.getInstance(), "I buoni trex");
		assertEquals("Check whether the species has been set correctly","I buoni trex",players[i].getCurrentSpecies().getName());
		assertEquals("When a new dinosaur species is created, is the size of the species equals to 1?", 1, players[i].getCurrentSpecies().getSize());
		
		players[i].getCurrentSpecies().addDinosaur();
		assertEquals("When adding a new dinosaur, the size of the species equals to 2?", 2, players[i].getCurrentSpecies().getSize());
		*/
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
}
