/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;

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
 * @since	Jun 4, 2011@10:54:35 PM
 *
 */
public class TurnResponse extends Response {
	
	private boolean invalidTokenFlag;
	private boolean turnNotOwnedByThePlayerFlag;
	private boolean playerNotInGameFlag;
	
	
	/**
	 * @return the turnNotOwnedByThePlayerFlag
	 */
	public boolean isTurnNotOwnedByThePlayerFlag() {
		return turnNotOwnedByThePlayerFlag;
	}

	/**
	 * @param turnNotOwnedByThePlayerFlag the turnNotOwnedByThePlayerFlag to set
	 */
	public void setTurnNotOwnedByThePlayerFlag(boolean theValue) {
		turnNotOwnedByThePlayerFlag = theValue;
	}

	/**
	 * @return the playerNotInGameFlag
	 */
	public boolean isPlayerNotInGameFlag() {
		return playerNotInGameFlag;
	}

	/**
	 * @param playerNotInGameFlag the playerNotInGameFlag to set
	 */
	public void setPlayerNotInGameFlag(boolean theValue) {
		playerNotInGameFlag = theValue;
	}
	
	public TurnResponse(boolean theOutcome, boolean theInvalidTokenFlag,  boolean thePlayerNotInGameFlag, boolean theTurnNotOwnedByThePlayerFlag) {
		setOutcomePositive(theOutcome);
		setInvalidTokenFlag(theInvalidTokenFlag);
		setPlayerNotInGameFlag(thePlayerNotInGameFlag);
		setTurnNotOwnedByThePlayerFlag(theTurnNotOwnedByThePlayerFlag);
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
			return new String("@ok");
		}
		else if (isInvalidToken() == true) {
				return new String("@no,@tokenNonValido");
		} else if (isPlayerNotInGameFlag() == true) {
				return new String("@no,@nonInPartita");
		} else if (isTurnNotOwnedByThePlayerFlag() == true) {
				return new String("@no,@nonIlTuoTurno");
		} else {
			return new String("@no");
		}
	}
	
	public String toString() {
		return generateVerbose();
	}


}
