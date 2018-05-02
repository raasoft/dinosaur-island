package Dinosaur Island.Gameplay;

import java.io.*;
import java.util.*;

public abstract class Cell {

	protected bool walkable = true;
	protected Food food = Null;
	private Position position;

	public Cell(Position thePosition) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setWalkable(bool theValue) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public bool isWalkable(bool theValue) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setDinosaur(Dinosaur theDinosaur) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Dinosaur getDinosaur() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Food getFood() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setFood(Food theFood) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
