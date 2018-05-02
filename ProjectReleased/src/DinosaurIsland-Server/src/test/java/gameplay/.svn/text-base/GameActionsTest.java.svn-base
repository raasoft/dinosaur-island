/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay;

import exceptions.FightWonException;
import exceptions.InvalidDestinationException;
import exceptions.MaxMovesLimitException;
import exceptions.StarvationDeathException;
import gameplay.map.Map;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Position;

import communication.Server;

/**
 * This class is intended to test the actions made by dinosaurs in game  
  */


public class GameActionsTest extends Assert {

		Game game;
		Server server;
		@Before
		
		public void setUp() throws Exception {
			server = Server.getInstance();
			game = Game.getInstance();
			Map.getInstance().createRandomMap();	
		}
		
		@Test
		public void testFOV() throws Exception {
			final Player thePlayer1 = new Player("ale", "al");
			thePlayer1.createCurrentSpecies(SpeciesHerbivoreFactory.getInstance(), "HerbivoreSpeciesNo1");
			Game.getInstance().addPlayer(thePlayer1);
			Dinosaur dino = thePlayer1.getCurrentSpecies().addDinosaur();
			DinosaurIDManager.getInstance().assignIdentifierToObject(dino);
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
			dino.setEnergy(2000);
			System.out.println("dino health: " + dino.getEnergy());
			Game.getInstance().moveDinosaur(dino, new Position(1,4));
			System.out.println("MOVEMENT");
			for (int j = 0; j < dinoFOV[0].length; j++) {
				for (int i = 0; i < dinoFOV.length; i++) {
					System.out.print("[" + dinoFOV[i][j] + "]");
					}
				System.out.print("\n");
			}
		}
		
		@Test (expected=StarvationDeathException.class)
		public void growTest() throws Exception  {
			final Player thePlayer1 = new Player("ale", "al");
			thePlayer1.createCurrentSpecies(SpeciesHerbivoreFactory.getInstance(), "HerbivoreSpeciesNo1");
			Game.getInstance().addPlayer(thePlayer1);
			Dinosaur dino = thePlayer1.getCurrentSpecies().addDinosaur();
			Position position = new Position(1, 2);
			dino.setPosition(position);
			DinosaurIDManager.getInstance().assignIdentifierToObject(dino);
			dino.setEnergy(dino.getEnergyMax());
			//dino.setSize(5);
			
			//Game.getInstance().growDinosaur(dino);
			dino.setEnergy(501);
			Game.getInstance().growDinosaur(dino);
			
			System.out.println("Dino size: " + dino.getSize());
		}
		
		@Test (expected=MaxMovesLimitException.class)
		public void layEggTest() throws Exception {
			final Player thePlayer1 = new Player("ale", "al");
			thePlayer1.createCurrentSpecies(SpeciesHerbivoreFactory.getInstance(), "HerbivoreSpeciesNo1");
			Dinosaur dino1 = thePlayer1.getCurrentSpecies().addDinosaur();
			dino1.setPosition(new Position(1,2));
			
			Game.getInstance().addDinosaur(thePlayer1);
			Game.getInstance().addDinosaur(thePlayer1);
			Game.getInstance().addDinosaur(thePlayer1);
			//Game.getInstance().addDinosaur(thePlayer1);
			Game.getInstance().addPlayer(thePlayer1);
			DinosaurIDManager.getInstance().assignIdentifierToObject(dino1);
			dino1.setSize(3);
			dino1.setEnergy(9999);
			System.out.println("Parent energy: " + dino1.getEnergy());
			System.out.println("Parent ID: " + dino1.getId());
			Game.getInstance().moveDinosaur(dino1, new Position(1,1));
			//Map.getInstance().placeDinosaurRandomly(dino);
			System.out.println("Parent energy: " + dino1.getEnergy());
			System.out.println("Parent ID: " + dino1.getId());
			
			System.out.println("Id of the new born dino: " + Game.getInstance().layEgg(dino1));
		}
		
		@Test (expected=InvalidDestinationException.class)
		public void moveDinosaurTest() throws Exception {
			final Player thePlayer1 = new Player("ale", "al");
			thePlayer1.createCurrentSpecies(SpeciesHerbivoreFactory.getInstance(), "HerbivoreSpeciesNo1");
			Dinosaur dino1 = thePlayer1.getCurrentSpecies().addDinosaur();
			dino1.setPosition(new Position(1,2));
			
			
			Dinosaur dino2 = thePlayer1.getCurrentSpecies().addDinosaur();
			dino2.setPosition(new Position(1,1));
			
			//Game.getInstance().addDinosaur(thePlayer1);
			Game.getInstance().addPlayer(thePlayer1);
			DinosaurIDManager.getInstance().assignIdentifierToObject(dino1);
			DinosaurIDManager.getInstance().assignIdentifierToObject(dino2);
			//dino1.setSize(3);
			dino1.setEnergy(9999);
			System.out.println("Dino1 energy: " + dino1.getEnergy());
			System.out.println("Dino1 ID: " + dino1.getId());
			Game.getInstance().moveDinosaur(dino1, new Position(1,1));	
		}
		
		@Test (expected=MaxMovesLimitException.class)
		public void fightTest() throws Exception {
			final Player thePlayer1 = new Player("ale", "ale");
			thePlayer1.createCurrentSpecies(SpeciesHerbivoreFactory.getInstance(), "HerbivoreSpeciesNo1");
			Dinosaur dino1 = thePlayer1.getCurrentSpecies().addDinosaur();
			DinosaurIDManager.getInstance().assignIdentifierToObject(dino1);
			Map.getInstance().placeDinosaur(dino1, new Position(1,1));
			dino1.setEnergy(9999);
			dino1.setSize(3);
			
			final Player thePlayer2 = new Player("raa", "raa");
			thePlayer2.createCurrentSpecies(SpeciesCarnivorousFactory.getInstance(), "CarnivorousSpeciesNo1");
			Dinosaur dino2 = thePlayer2.getCurrentSpecies().addDinosaur();
			DinosaurIDManager.getInstance().assignIdentifierToObject(dino2);
			Map.getInstance().placeDinosaur(dino2, new Position(1,2));
			dino2.setEnergy(9999);
			
			Game.getInstance().addPlayer(thePlayer1);
			Game.getInstance().addPlayer(thePlayer2);
			
			System.out.println("Dino1 energy: " + dino1.getEnergy());
			System.out.println("Dino1 ID: " + dino1.getId());
			System.out.println("Dino1 Pos: " + dino1.getPosition());
			System.out.println("Dino2 energy: " + dino2.getEnergy());
			System.out.println("Dino2 ID: " + dino2.getId());
			System.out.println("Dino2 Pos: " + dino2.getPosition());
			
			System.out.println("Dino1 Species: " + dino1.getSpecies());
			System.out.println("Dino2 Species: " + dino2.getSpecies());
			try {
			Game.getInstance().moveDinosaur(dino1, new Position(1,2));
			} catch (FightWonException e) { }
			System.out.println("Dino1 Pos: " + dino1.getPosition());
			Game.getInstance().moveDinosaur(dino1, new Position(1,1));
			Game.getInstance().passTurn(thePlayer1);
		}
		
		@After
		public void tearDown() throws Exception {
		}

		
	}

	



	
