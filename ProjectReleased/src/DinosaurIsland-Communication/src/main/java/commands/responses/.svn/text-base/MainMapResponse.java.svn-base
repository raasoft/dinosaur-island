/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;

import beans.MainMap;


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
 * @since	Jun 2, 2011@11:37:34 AM
 *
 */
public class MainMapResponse extends Response {

	private boolean invalidTokenFlag;
	private boolean playerNotInGameFlag;
	private MainMap mainMap;

	
	/**
	 * @return the mainMap
	 */
	public MainMap getMainMap() {
		return mainMap;
	}
	/**
	 * @param mainMap the mainMap to set
	 */
	public void setMainMap(MainMap theMainMap) {
		mainMap = theMainMap;
	}
	public MainMapResponse(boolean theOutcome, MainMap theMainMap, boolean theInvalidTokenFlag, boolean thePlayerNotInGameFlag) {
		setOutcomePositive(theOutcome);
		setMainMap(theMainMap);
		setPlayerNotInGameFlag(thePlayerNotInGameFlag);
		setInvalidTokenFlag(theInvalidTokenFlag);
	}
	/**
	 * @param usernameAlreadyTakenFlag the usernameAlreadyTakenFlag to set
	 */
	public void setPlayerNotInGameFlag(boolean thePlayerNotInGameFlag) {
		playerNotInGameFlag = thePlayerNotInGameFlag;
	}
	/**
	 * @return the usernameAlreadyTakenFlag
	 */
	public boolean isPlayerNotInGame() {
		return playerNotInGameFlag;
	}

	public void setInvalidTokenFlag(boolean theInvalidTokenFlag) {
		invalidTokenFlag = theInvalidTokenFlag;
	}
	
	public boolean isInvalidToken() {
		return invalidTokenFlag;
	}
	
	public String generateVerbose() {
		if (isOutcomePositive() == true) {
			return new String("@mappaGenerale,"+mainMap.toString());
		}
		else {
			if (isPlayerNotInGame() == true ) {
				return new String("@no,@nonInPartita");
			} else if (isInvalidToken() == true){
				return new String("@no,@tokenNonValido");
			}
			else {
				return new String("@no");
			}
		}
	}
	
	public String toString() {
		return generateVerbose();
	}	
}
