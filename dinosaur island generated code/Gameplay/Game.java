package Dinosaur Island.Gameplay;

import java.io.*;
import java.util.*;

public class Game {

	private Game uniqueInstance = new Game();
	private int currentTurn = 0;
	private Player[] currentPlayers = Null;
	private int playerMax = 8;

	private Game() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public static Game getInstance() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setPlayerMax(int thePlayerNumber) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getPlayerMax() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getCurrentTurn() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Player[] getCurrentPlayers() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void confirmTurn(Player thePlayer) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void passTurn(Player thePlayer) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public bool nextTurn() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public bool endGame() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void moveDinosaur(Player thePlayer, Dinosaur theDinosaur, Cell theCell) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void growDinosaur(Player thePlayer, Dinosaur theDinosaur) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void layDinosaurEgg(Player thePlayer, Dinosaur theDinosaur) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public bool resumeGame() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public bool saveGame() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
