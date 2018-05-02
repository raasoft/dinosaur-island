package Dinosaur Island.Gameplay;

import java.io.*;
import java.util.*;

public class Map {

	private Cell[] grid;
	private Vector2D dimension;
	private Map uniqueInstance = new Map();
	private Hashtable<Position, Dinosaur> positionMap;
	private Hashtable<Dinosaur, Position> dinosaurMap;

	public Map() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public static Map getInstance() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Cell getCell(Position thePosition) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean placeDinosaur(Dinosaur theDinosaur, Position thePosition) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean removeDinosaur(Dinosaur theDinosaur) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Vector2D getDimension() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setDimension(Vector2D theVector) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean validate(Dinosaur theDinosaur, Position thePosition) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Dinosaur getDinosaur(Position thePosition) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Position getPosition(Dinosaur theDinosaur) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void moveDinosaur(Dinosaur theDinosaur, Position theFinalPosition) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
