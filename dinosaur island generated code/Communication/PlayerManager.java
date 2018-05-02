package Dinosaur Island.Communication;

import java.io.*;
import java.util.*;

public abstract class PlayerManager {

	private ArrayList<Player> players;
	private static final PlayerManager uniqueInstance = new PlayerManager();

	public void addPlayer(String theName, String thePassword) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public PlayerManager getInstance() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private Player getPlayer() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void loginPlayer(String theUsername, String thePassword) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
