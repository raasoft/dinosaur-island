package Dinosaur Island.Gameplay;

import java.io.*;
import java.util.*;

public abstract class Food {

	protected int energy;
	protected int energyMax;

	public int setEnergy() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public abstract void updateTurnEnergy();

	public int getEnergy() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setEnergyMax(int theEnergyMax) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void getEnergyMax() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
