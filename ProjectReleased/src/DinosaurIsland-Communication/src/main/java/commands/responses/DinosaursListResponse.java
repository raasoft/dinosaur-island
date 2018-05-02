/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;

import beans.DinosaursList;

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
 * @author	AXXO
 * @version 1.0
 * @since	Jun 4, 2011@3:22:24 PM
 *
 */
final public class DinosaursListResponse extends Response {
	
	private DinosaursList dinosaursInGameList; 
	private boolean invalidTokenFlag;
	private boolean playerNotInGameFlag;
	
	/**
	 * @return the playerNotInGameFlag
	 */
	public boolean isPlayerNotInGameFlag() {
		return playerNotInGameFlag;
	}

	/**
	 * @param playerNotInGameFlag the playerNotInGameFlag to set
	 */
	public void setPlayerNotInGameFlag(boolean thePlayerNotInGameFlag) {
		playerNotInGameFlag = thePlayerNotInGameFlag;
	}

	/**
	 * @return the dinosaursInGameList
	 */
	public DinosaursList getDinosaursInGameList() {
		return dinosaursInGameList;
	}

	/**
	 * @param dinosaursInGameList the dinosaursInGameList to set
	 */
	public void setDinosaursInGameList(DinosaursList theDinosaursInGameList) {
		dinosaursInGameList = theDinosaursInGameList;
	}
	
	public DinosaursListResponse(boolean theOutcome, DinosaursList theDinosaursInGameList, boolean thePlayerNotInGameFlag, boolean theInvalidTokenFlag) {
		setOutcomePositive(theOutcome);
		setPlayerNotInGameFlag(thePlayerNotInGameFlag);
		setInvalidTokenFlag(theInvalidTokenFlag);
		setDinosaursInGameList(theDinosaursInGameList);
	}
	
	/**
	 * @return the invalidTokenFlag
	 */
	public boolean isInvalidToken() {
		return invalidTokenFlag;
	}

	/**
	 * @param theInvalidTokenFlag the invalidTokenFlag to set
	 */
	public void setInvalidTokenFlag(boolean theInvalidTokenFlag) {
		invalidTokenFlag = theInvalidTokenFlag;
	}

	public String generateVerbose() {
		if (isOutcomePositive() == true) {
			String comma = "";
			if (dinosaursInGameList.getStringsNumber() > 0) {
				comma = ",";
			}
			return new String("@listaDinosauri"+comma+dinosaursInGameList.toString());
		}
		else {
			if (isInvalidToken() == true ) {
				return new String("@no,@tokenNonValido");
			} else if (isPlayerNotInGameFlag()) {
				return new String("@no,@nonInPartita");
			} else {
				return new String("@no");
			}
		}
	}
	
	public String toString() {
		return generateVerbose();
	}

}
