/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay;

import gameplay.highscore.HighscoreManager;
import junit.framework.Assert;

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
 * @author	Giulia
 * @version 1.0
 * @since	15/giu/2011@06.31.16
 *
 */
public class HighscoreTest extends Assert {
	
	@Before
	public void setUp() throws Exception {

	}
	
	@Test
	public void simpleHighscoreTest() {
		
		HighscoreManager.getInstance().setScore("raa", "stego", 10);
		HighscoreManager.getInstance().setScore("raa", "stego", 100);
		HighscoreManager.getInstance().setScore("raa", "stego", 10000);
		HighscoreManager.getInstance().setScoreAsFinal("raa", "stego");
		HighscoreManager.getInstance().setScore("axxo", "raptor", 50);
		
		System.out.println(HighscoreManager.getInstance().getSortedHighscore().toString());
		
	}

}
