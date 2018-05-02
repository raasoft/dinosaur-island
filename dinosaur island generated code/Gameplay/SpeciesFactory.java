package Dinosaur Island.Gameplay;

import java.io.*;
import java.util.*;

public abstract class SpeciesFactory {

	private SpeciesFactory uniqueInstance = Null;

	public abstract Species createSpecies();

	public static SpeciesFactory getInstance() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
