package beans;

import java.io.Serializable;

import exceptions.InvalidMapException;
import util.Position;

final public class DinosaurStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ownerUsername;
	private String speciesName;
	private Character speciesType;
	private Position position;
	private int size;
	
	int energy;
	int turnsAlive;

	
	public DinosaurStatus(String theOwnerUsername, String theSpeciesName, Character theSpeciesType, Position thePosition, int theSize) throws NullPointerException, InvalidMapException {
		this(theOwnerUsername,theSpeciesName,theSpeciesType,thePosition,theSize,-1,-1);
	}

	public DinosaurStatus(String theOwnerUsername, String theSpeciesName, Character theSpeciesType, Position thePosition, int theSize, int theEnergy, int theTurnsAlive) throws NullPointerException, InvalidMapException {
		
		setTurnsAlive(theTurnsAlive);
		setOwnerUsername(theOwnerUsername);
		setSpeciesName(theSpeciesName);
		setSpeciesType(theSpeciesType);
		setPosition(thePosition);
		setSize(theSize);
		
		setTurnsAlive(theTurnsAlive);
		setEnergy(theEnergy);
		
		if (theEnergy == -1 || theTurnsAlive == -1) {
			setPlayerOwningIt(false);
		} else {
			setPlayerOwningIt(true);
		}
		

	}
	
	boolean isPlayerOwningIt;

	/**
	 * @return the isPlayerOwningIt
	 */
	public boolean isPlayerOwningIt() {
		return isPlayerOwningIt;
	}



	/**
	 * @param isPlayerOwningIt the isPlayerOwningIt to set
	 */
	public void setPlayerOwningIt(boolean theValue) {
		isPlayerOwningIt = theValue;
	}

	/**
	 * @return the ownerUsername
	 */
	public String getOwnerUsername() {
		return ownerUsername;
	}

	/**
	 * @param ownerUsername the ownerUsername to set
	 */
	private void setOwnerUsername(String theOwnerUsername) {
		ownerUsername = theOwnerUsername;
	}

	/**
	 * @return the speciesName
	 */
	public String getSpeciesName() {
		return speciesName;
	}

	/**
	 * @param speciesName the speciesName to set
	 */
	private void setSpeciesName(String theSpeciesName) {
		speciesName = theSpeciesName;
	}

	/**
	 * @return the speciesType
	 */
	public Character getSpeciesType() {
		return speciesType;
	}

	/**
	 * @param speciesType the speciesType to set
	 */
	private void setSpeciesType(Character theSpeciesType) {
		speciesType = theSpeciesType;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	private void setPosition(Position thePosition) {
		position = thePosition;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	private void setSize(int theSize) {
		size = theSize;
	}

	/**
	 * @return the energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * @param energy the energy to set
	 */
	private void setEnergy(int theEnergy) {
		energy = theEnergy;
	}

	/**
	 * @return the turnsAlive
	 */
	public int getTurnsAlive() {
		return turnsAlive;
	}

	/**
	 * @param turnsAlive the turnsAlive to set
	 */
	public void setTurnsAlive(int theTurnsAlive) {
		turnsAlive = theTurnsAlive;
	}

	public String toString() {

		String str = "";

		str += getOwnerUsername() + ",";
		str += getSpeciesName() + ",";
		str += getSpeciesType() + ",";
		str += "{"+getPosition().getIntX() + "," + getPosition().getIntY()+"},";
		str += getSize();
		
		if (isPlayerOwningIt()) {
			str+=","+getEnergy()+","+getTurnsAlive();
		}
		
		return str;
	}

}
