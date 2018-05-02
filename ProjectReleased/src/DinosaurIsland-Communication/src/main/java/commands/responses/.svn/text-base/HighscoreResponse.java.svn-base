/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;

import beans.Highscore;

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
final public class HighscoreResponse extends Response {
	
	private Highscore highscore; 
	private boolean invalidTokenFlag;
	
	/**
	 * @return the highscore
	 */
	public Highscore getHighscore() {
		return highscore;
	}

	/**
	 * @param highscore the highscore to set
	 */
	public void setHighscore(Highscore theHighscore) {
		highscore = theHighscore;
	}
	
	public HighscoreResponse(boolean theOutcome, Highscore theHighscore, boolean theInvalidTokenFlag) {
		setOutcomePositive(theOutcome);
		setInvalidTokenFlag(theInvalidTokenFlag);
		setHighscore(theHighscore);
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
			if (highscore.size() > 0) {
				comma = ",";
			}
			return new String("@classifica"+comma+highscore.toString());
		}
		else {
			if (isInvalidToken() == true ) {
				return new String("@no,@tokenNonValido");
			} else {
				return new String("@no");
			}
		}
	}
	
	public String toString() {
		return generateVerbose();
	}

}
