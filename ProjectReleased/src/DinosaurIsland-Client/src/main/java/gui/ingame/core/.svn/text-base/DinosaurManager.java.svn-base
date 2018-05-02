/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gui.ingame.core;

import gui.ingame.dinosaurislandgame.GameManager;
import gui.ingame.dinosaurislandgame.ResourceManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import communication.Client;

import util.Position;

import beans.DinosaursList;
import beans.LocalView;

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
 * @since	Jun 10, 2011@10:00:02 AM
 *
 */
public class DinosaurManager {
	
	private static DinosaurManager uniqueInstance = new DinosaurManager();
	private String playerSpeciesName = null;
	ArrayList<String> playerDinosaurIDListAtTurnBegin;
	private Hashtable<String, Dinosaur> dinosaurIdToDinosaurMap;
	
	
	
	/**
	 * @return the playerSpeciesName
	 */
	public String getPlayerSpeciesName() {
		return playerSpeciesName;
	}

	/**
	 * @param playerSpeciesName the playerSpeciesName to set
	 */
	public void setPlayerSpeciesName(String playerSpeciesName) {
		this.playerSpeciesName = playerSpeciesName;
	}


	
	private DinosaurManager() {
		setDinosaurIdToDinosaurMap(new Hashtable<String, Dinosaur>());
		playerDinosaurIDListAtTurnBegin = new ArrayList<String>();
	}
	
	public void freeResources() {
		playerDinosaurIDListAtTurnBegin = new ArrayList<String>();
    	DinosaurManager.getInstance().deleteAllDinosaur();
    	setPlayerSpeciesName(null);
	}
	
	public void deleteAllDinosaur() {
		getDinosaurIdToDinosaurMap().clear();
	}
	
	public Dinosaur getDinosaur(String theDinosaurID) {
		
		if (theDinosaurID == null)
			return null;

		if (getDinosaurIdToDinosaurMap().containsKey(theDinosaurID))
			return getDinosaurIdToDinosaurMap().get(theDinosaurID);
		else
			return null;
	}
	
	public ArrayList<Dinosaur> getPlayerDinosaurList() {
		
		ArrayList<Dinosaur> dinosaurList = new ArrayList<Dinosaur>();
		
		for (Dinosaur dino : dinosaurIdToDinosaurMap.values()) {
			if (dino.getOwnerUsername().equals(Client.getUsername()))
				dinosaurList.add(dino);
		}
		
		return dinosaurList;
		
	}

	
	public boolean isPositionInsidePlayerView(Position thePosition) {
		
		int dimX,dimY;
		int dinoFOVRadius;
		int bottomLeftX,bottomLeftY,topRightX,topRightY;
		int dinoX, dinoY;
		
		boolean inView = false;
		
		dimX = (int) GameManager.getInstance().getMap().getWidth();
		dimY = (int) GameManager.getInstance().getMap().getHeight();
		
		for (Dinosaur dino : dinosaurIdToDinosaurMap.values()) {
		
			dinoX = dino.getPosition().getIntX();
			dinoY = dino.getPosition().getIntY();
			
			dinoFOVRadius = (int) (2 + Math.floor(dino.getSize()/2));
	
			bottomLeftX = dinoX - dinoFOVRadius;
			if (bottomLeftX < 0 )
				bottomLeftX = 0;
	
			bottomLeftY = dinoY - dinoFOVRadius;
			if (bottomLeftY < 0 )
				bottomLeftY = 0;
	
			topRightY = dinoY + dinoFOVRadius;
			if (topRightY > dimY-1)
				topRightY = dimY-1;
	
			topRightX = dinoX + dinoFOVRadius;
			if (topRightX > dimX-1)
				topRightX = dimX-1;
	
			if ( 	thePosition.getIntX() >= bottomLeftX &&
					thePosition.getIntX() <= topRightX &&
					thePosition.getIntY() >= bottomLeftY &&
					thePosition.getIntY() <= topRightY) {
				
				inView = true;
				break;
				
			}
			
		}

		return inView;
		
	}
	
	public void addDinosaur(Dinosaur theDinosaur) {
		getDinosaurIdToDinosaurMap().put(theDinosaur.getDinosaurID(), theDinosaur);
	}
	
	public Collection<Dinosaur> getDinosaurList() {
		return getDinosaurIdToDinosaurMap().values();
	}
	
	public void createDinosaurs(ArrayList<LocalView> theLocalViewsList) {
		
		getDinosaurIdToDinosaurMap().clear();
		
		for (LocalView localView : theLocalViewsList) {
			
			int dimX = (int) localView.getDimension().getX();
			int dimY = (int) localView.getDimension().getY();
			
			String[][] loadedAttributes = localView.getAttributes();
			Character[][] loadedLocalView = localView.getLocalView();
			
			int originX = (int) localView.getOrigin().getX(); //already reconverted to our coordinate system
			int originY = (int) localView.getOrigin().getY(); //already reconverted to our coordinate system
			
			int posY;
			int posX;
			
			for (int y = 0; y < dimY; y++) {
				for (int x = 0; x < dimX; x++) {
					
					posY = originY + y;
					posX = originX + x;

					if (loadedLocalView[x][y].equals('d')) {
									
						ResourceManager.getInstance().getMap().updateCellDinosaurID(loadedAttributes[x][y], new Position(posX,posY));
						getDinosaurIdToDinosaurMap().put(loadedAttributes[x][y], new Dinosaur(loadedAttributes[x][y]));
					}
				}
			}
		}
	}

	/** 
	 * The method getInstance bla bla bla...
	 * Bla bla bla... as the indicated {@link NameClass1} bla bla bla...
	 * It is useful also insert some {@code arg1} references to the args
	 * while explaining.
	 *
	 * @return          
	 *
	 * @see		NameClass1 (remove the line if there is no need of it)
	 * @see		NameClass2 (remove the line if there is no need of it)
	 *  
	 * @since	Jun 12, 2011@6:49:23 PM
	 * 
	 */
	public static DinosaurManager getInstance() {

		return uniqueInstance;
	}

	/**
	 * @param dinosaurIdToDinosaurMap the dinosaurIdToDinosaurMap to set
	 */
	public void setDinosaurIdToDinosaurMap(Hashtable<String, Dinosaur> dinosaurIdToDinosaurMap) {
		this.dinosaurIdToDinosaurMap = dinosaurIdToDinosaurMap;
	}

	/**
	 * @return the dinosaurIdToDinosaurMap
	 */
	public Hashtable<String, Dinosaur> getDinosaurIdToDinosaurMap() {
		return dinosaurIdToDinosaurMap;
	}
	
	public ArrayList<String> getPlayerDinosaurIDListAtTurnBegin() {
		
		return playerDinosaurIDListAtTurnBegin;
				
	}
	
	public void updatePlayerDinosaurIDListAtTurnBegin(DinosaursList theDinosaursList) {
		
		playerDinosaurIDListAtTurnBegin.clear();
		
		for (String dinoID : theDinosaursList.getStrings()) {
			playerDinosaurIDListAtTurnBegin.add(dinoID);
		}
				
	}
	
	public boolean wasDinosaurPresentAtTurnBegin(String theDinosaurID) {
		return playerDinosaurIDListAtTurnBegin.contains(theDinosaurID);
	}
	
	

}
