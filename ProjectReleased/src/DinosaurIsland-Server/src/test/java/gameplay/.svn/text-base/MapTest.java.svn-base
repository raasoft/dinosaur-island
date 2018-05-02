/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay;

import gameplay.map.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
 * @since	Jun 6, 2011@7:05:25 PM
 *
 */
public class MapTest extends Assert {

	
	@Before
	public void setUp() throws Exception {

	}
	
	@Test
	public void testGenerateRandomMap() throws Exception
	{
		Map.getInstance().createRandomMap();
		System.out.println("RANDOM MAP");
		System.out.println(Map.getInstance().toString());
		
		
		System.out.println("MAP ALREADY DISCOVERED");
		System.out.println(Map.getInstance().getMainMap(null));

	}
	
	@Test
	public void testLoadFromFile() throws Exception
	{
		/*
		Map.getInstance().loadMapFromFile("map.txt");
		System.out.println("MAP map.txt");
		System.out.println(Map.getInstance().toString());
		*/
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
}

