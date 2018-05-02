/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication;

import org.junit.Before;
import org.junit.Test;

import communication.requests.Request;


/**
 * <b>Overview</b><br>
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
 *
 * <b>Responsibilities</b><br>
 * <p>
 * Other description in a separate paragraph.
 * </p>
 *
 * <b>Collaborators</b><br>
 * <p>
 * Here write about classes 
 * </p>
 * 
 * @author	RAA
 * @version 1.0
 * @since	May 24, 2011@10:42:12 AM
 *
 */

//May plot the results in the "test" logger?!?!?!?
public class ServerRequestParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSplitString()
	{
		Request request = null;
	
		request = ServerRequestParser.parse("@creaUtente,user=raa,pass=ciao", null);
		System.out.println("Request : "+request);
		
		request = ServerRequestParser.parse("@creaRazza,token=1356,nome=DINO,tipo=c", null);
		System.out.println("Request : "+request);
		
		request = ServerRequestParser.parse("@creaRazza,token=1356,nome=DINO,tipo=d", null);
		System.out.println("Request : "+request);
		
		request = ServerRequestParser.parse("@accessoPartita,token=token89", null);
		System.out.println("Request : "+request);
		
		request = ServerRequestParser.parse("@mappaGenerale,token=token89", null);
		System.out.println("Request : "+request);
		
		request = ServerRequestParser.parse("@cresciDinosauro,token=token89,idDino=30", null);
		System.out.println("Request : "+request);
		
		request = ServerRequestParser.parse("@muoviDinosauro,token=token89,idDino=30,dest={1,1}", null);
		System.out.println("Request : "+request);
		
		request = ServerRequestParser.parse("@deponiUovo,token=token89,idDino=30", null);
		System.out.println("Request : "+request);
		
	}
}
