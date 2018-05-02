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
 * @since	Jun 4, 2011@3:22:24 PM
 *
 */
public class LeaveGameResponse extends Response {
	
	private boolean invalidTokenFlag;
	
	
	public LeaveGameResponse(boolean theOutcome, boolean theInvalidTokenFlag) {
		setOutcomePositive(theOutcome);
		setInvalidTokenFlag(theInvalidTokenFlag);
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
		else {
			if (isInvalidToken() == true) {
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
