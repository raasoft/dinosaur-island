/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gui.ingame.core;

import gui.ingame.dinosaurislandgame.ResourceManager;

import java.awt.Image;

import util.Position;

import beans.DinosaurStatus;

import communication.Client;

/**
 * <b>Overview</b><br>
 * <p>
 * Description of the type.
 * This state information includes:
 * <ul>
 * <li>Element 1
 * <li>Element 2
 * <li>The current element implementation 
 *     (see <a href="#setXORMode">setXORMode</a>)
 * </ul>
 * </p>
 *
 * <b>Responsibilities</b><br>
 * <p>
 * Other description in a separate paragraph.
 * </p>
 *
 * <b>Collaborators</b><br>
 * <p>
 * Here write about classes 
 * </p>
 * 
 * @author	RAA
 * @version 1.0
 * @since	Jun 10, 2011@10:01:16 AM
 *
 */
public class Dinosaur {
	
	
	private String dinosaurID;
	private String ownerUsername;
	private String speciesName;
	private Character speciesType;
	private int size;
	private Position position;
	
	int energy;
	int turnsAlive;
	
	private Image image;



	public Dinosaur(String theDinosaurID) {
		dinosaurID = theDinosaurID;
	}
	
	
	public void characterize(DinosaurStatus theDinosaurStatus) {
		
		setOwnerUsername(theDinosaurStatus.getOwnerUsername());		
		setSpeciesName(theDinosaurStatus.getSpeciesName());
		
		if (getOwnerUsername().equals(Client.getUsername()) && DinosaurManager.getInstance().getPlayerSpeciesName() == null) {
			DinosaurManager.getInstance().setPlayerSpeciesName(getSpeciesName());
		}
		
		setSpeciesType(theDinosaurStatus.getSpeciesType());
		setSize(theDinosaurStatus.getSize());
		setPosition(theDinosaurStatus.getPosition());
		setEnergy(theDinosaurStatus.getEnergy());
		setTurnsAlive(theDinosaurStatus.getTurnsAlive());
		
		setImage(ResourceManager.getInstance().getDinosaurImage(getSpeciesType().toString()));
		
	}
	
	

	boolean isOwnedByThePlayer() {
		
		if (Client.getUsername().equals(getOwnerUsername())) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * @return the dinosaurID
	 */
	public String getDinosaurID() {
		return dinosaurID;
	}
	/**
	 * @param dinosaurID the dinosaurID to set
	 */
	public void setDinosaurID(String theDinosaurID) {
		dinosaurID = theDinosaurID;
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
	public void setOwnerUsername(String theOwnerUsername) {
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
	public void setSpeciesName(String theSpeciesName) {
		speciesName = theSpeciesName;
	}	
	
	/**
	 * @return the speciesType
	 */
	public Character getSpeciesType() {
		return speciesType;
	}
	
	public int getStepSize() {
		if (getSpeciesType().equals(new Character('c'))) {
			return 3;
		} else {
			return 2;
		}
	}
	
	/**
	 * @param speciesType the speciesType to set
	 */
	public void setSpeciesType(Character theSpeciesType) {
		speciesType = theSpeciesType;
	}
	
	/**
	 * @param image the image to set
	 */
	public void setImage(Image theImage) {
		image = theImage;
	}
	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
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
	public void setSize(int theSize) {
		size = theSize;
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
	public void setPosition(Position thePosition) {
		position = thePosition;
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
	public void setEnergy(int theEnergy) {
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
	
}
