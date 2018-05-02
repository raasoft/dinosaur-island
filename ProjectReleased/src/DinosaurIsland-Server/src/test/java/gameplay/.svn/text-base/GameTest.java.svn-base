/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay;
import gameplay.map.Map;

import java.util.Timer;
import java.util.TimerTask;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.Position;

import communication.Server;
import communication.ServerInterface;

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
public class GameTest extends Assert {

	Game game;
	Server server;
	@Before
	public void setUp() throws Exception {
		server = Server.getInstance();
		game = Game.getInstance();
		Map.getInstance().createRandomMap();
	}
	
	@Test
	public void testTurnsHandling() throws Exception
	{
		
		/* This class test wants to test the functionality of the events handling:
		 * first two players are created and added to the game.
		 * The game then starts. Are artificially generated the first turn confirmation for 
		 * each player, then the players stop to confirm the turns. After a period of idle time,
		 * the test ends.
		 */
		
		final Player thePlayer1 = new Player("raa", "raa");
		thePlayer1.createCurrentSpecies(SpeciesHerbivoreFactory.getInstance(), "HerbivoreSpeciesNo1");
		final Player thePlayer2 = new Player("axxo", "axxo");
		thePlayer2.createCurrentSpecies(SpeciesHerbivoreFactory.getInstance(), "HerbivoreSpeciesNo2");
		
		game.addPlayer(thePlayer1); //This action starts the game
		game.addPlayer(thePlayer2);
		
		Timer timer1 = new Timer();
		timer1.schedule(new ConfirmPlayer(thePlayer1, timer1), ServerInterface.TURN_CONFIRMATION_DELAY/2);
		
		Timer timer2 = new Timer();
		timer2.schedule(new ConfirmPlayer(thePlayer2, timer2), ServerInterface.TURN_CONFIRMATION_DELAY + ServerInterface.TURN_EXECUTION_DELAY);
		
		Timer timer3 = new Timer();
		timer3.schedule(new EndTest(), 5 * ServerInterface.TURN_EXECUTION_DELAY);
		
		
		while (true) {}
	}
	
	@Test
	public void testFOV() throws Exception {
		final Player thePlayer1 = new Player("ale", "al");
		thePlayer1.createCurrentSpecies(SpeciesHerbivoreFactory.getInstance(), "HerbivoreSpeciesNo1");
		Dinosaur dino = thePlayer1.getCurrentSpecies().addDinosaur();
		Position position = new Position(1, 2);
		dino.setPosition(position);
		dino.getSpecies().updateFOV(dino);
		boolean[][] dinoFOV = dino.getSpecies().getFOV();
		
		 
		for (int j = 0; j < dinoFOV[0].length; j++) {
			for (int i = 0; i < dinoFOV.length; i++) {
				System.out.print("[" + dinoFOV[i][j] + "]");
				}
			System.out.print("\n");
		}
		Game.getInstance().moveDinosaur(dino, new Position(1,4));
		System.out.println("MOVEMENT");
		for (int j = 0; j < dinoFOV[0].length; j++) {
			for (int i = 0; i < dinoFOV.length; i++) {
				System.out.print("[" + dinoFOV[i][j] + "]");
				}
			System.out.print("\n");
		}
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
}

class EndTest extends TimerTask {
	
	@Override
	public void run() {
		System.out.println("SVEGLIATI");
		notifyAll();
	}
}



class ConfirmPlayer extends TimerTask {
	
	Player player;
	Timer timer;
	
	ConfirmPlayer(Player thePlayer, Timer timer1) {
		player = thePlayer;
		timer = timer1;
	}
	
	@Override
	public void run() {
		Game.getInstance().confirmTurn(player);
		timer.cancel();
	}
}