/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;

import beans.DinosaurStatus;


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
public class DinosaurStatusResponse extends Response {

	private boolean invalidTokenFlag;
	private boolean invalidDinosaurIDFlag;
	private boolean playerNotInGameFlag;
	private DinosaurStatus dinosaurStatus;

	
	/**
	 * @return the dinosaurStatus
	 */
	public DinosaurStatus getDinosaurStatus() {
		return dinosaurStatus;
	}
	/**
	 * @param dinosaurStatus the dinosaurStatus to set
	 */
	public void setDinosaurStatus(DinosaurStatus theDinosaurStatus) {
		dinosaurStatus = theDinosaurStatus;
	}
	public DinosaurStatusResponse(boolean theOutcome, DinosaurStatus theDinosaurStatus, boolean theInvalidTokenFlag, boolean theInvalidDinosaurIDFlag, boolean thePlayerNotInGameFlag) {
		setOutcomePositive(theOutcome);
		setDinosaurStatus(theDinosaurStatus);
		setPlayerNotInGameFlag(thePlayerNotInGameFlag);
		setInvalidTokenFlag(theInvalidTokenFlag);
		setInvalidDinosaurIDFlag(theInvalidDinosaurIDFlag);
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
	
	public void setInvalidDinosaurIDFlag(boolean theInvalidDinosaurIDFlag) {
		invalidDinosaurIDFlag = theInvalidDinosaurIDFlag;
	}
	
	public boolean isInvalidDinosaurID() {
		return invalidDinosaurIDFlag;
	}
	
	public String generateVerbose() {
		if (isOutcomePositive() == true) {
			return new String("@statoDinosauro,"+dinosaurStatus.toString());
		}
		else {
			if (isPlayerNotInGame() == true ) {
				return new String("@no,@nonInPartita");
			} else if (isInvalidToken() == true) {
				return new String("@no,@tokenNonValido");
			} else if (isInvalidDinosaurID() == true) {
				return new String("@no,@idNonValido");
			} else {
				return new String("@no");
			}
		}
	}
	
	public String toString() {
		return generateVerbose();
	}	
}
