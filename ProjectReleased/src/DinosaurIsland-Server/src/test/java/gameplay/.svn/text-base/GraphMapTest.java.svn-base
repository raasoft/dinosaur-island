/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay;

import exceptions.NameAlreadyTakenException;
import exceptions.SpeciesAlreadyCreatedException;
import gameplay.map.GraphMap;
import gameplay.map.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.Position;

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
 * @author	Ale
 * @version 1.0
 * @since	Jun 9, 2011@12:30:39 AM
 *
 */
public class GraphMapTest extends Assert {
	
	@Before
	public void setUp() throws Exception {
		Map.getInstance();
		Map.getInstance().createRandomMap();
	}
	
	@Test
	public static void testGraphMap() throws SpeciesAlreadyCreatedException, IllegalArgumentException, NameAlreadyTakenException {
		
		/*
		 * This class wants to test the initialization of the graph composed by the cells a dinosaur can reach
		 * from its current position
		 */
		
		System.out.println(Map.getInstance().toString());
		Player thePlayer2 = new Player("axxo", "axxo");
		thePlayer2.createCurrentSpecies(SpeciesHerbivoreFactory.getInstance(), "HerbivoreSpeciesNo2");
		
		Dinosaur dino = thePlayer2.getCurrentSpecies().addDinosaur();
		Position position = new Position(0, 0);
		dino.setPosition(position);
		
		GraphMap graphMap = new GraphMap(dino);
		System.out.println(graphMap.getGraph().toString());
			
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
