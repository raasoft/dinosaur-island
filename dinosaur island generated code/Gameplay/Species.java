package Dinosaur Island.Gameplay;

import java.io.*;
import java.util.*;

public abstract class Species {

	protected String name;
	protected final int familySizeMax = 5;
	protected ArrayList<Dinosaur> family;
	protected int turnMax = 120;
	protected int currentTurn = 0;
	protected Player player;

	public Species(Player thePlayer) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getCurrentTurn() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void incrementTurn() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setTurnMax(int theValue) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Player getPlayer() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setPlayer() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getTurnMax() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public String getName() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setName(String theName) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getSize() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public abstract void addDinosaur();

	public void removeDinosaur(Dinosaur theDinosaur) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int computeScore() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
