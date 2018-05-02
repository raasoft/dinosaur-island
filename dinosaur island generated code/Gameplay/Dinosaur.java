package Dinosaur Island.Gameplay;

import java.io.*;
import java.util.*;

public abstract class Dinosaur {

	protected int age;
	protected Species species;
	protected int ageMax;
	protected int energy;
	private DinosaurID id;
	protected int size;
	private Position position;

	public Dinosaur(Species theSpecies) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public abstract int getStrength();

	public int getEnergy() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Species getSpecies() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setSpecies() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getAge() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getSize() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setEnergy() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setAge() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setSize() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getEnergyMax() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public abstract int getMovementEnergy();

	public abstract int getGrowthEnergy();

	public void getMaxStep() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setPosition(Position thePosition) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Position getPosition() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
