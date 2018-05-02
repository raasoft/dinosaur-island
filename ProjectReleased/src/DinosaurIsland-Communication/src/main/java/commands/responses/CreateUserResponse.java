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
 * @author	RAA
 * @version 1.0
 * @since	Jun 2, 2011@11:37:34 AM
 *
 */
public class CreateUserResponse extends Response {

	private boolean usernameAlreadyTakenFlag;

	public CreateUserResponse(boolean theOutcome, boolean theUsernameAlreadyTakenFlag) {
		setOutcomePositive(theOutcome);
		setUsernameAlreadyTakenFlag(theUsernameAlreadyTakenFlag);
	}
	/**
	 * @param usernameAlreadyTakenFlag the usernameAlreadyTakenFlag to set
	 */
	public void setUsernameAlreadyTakenFlag(boolean theUsernameAlreadyTakenFlag) {
		usernameAlreadyTakenFlag = theUsernameAlreadyTakenFlag;
	}
	/**
	 * @return the usernameAlreadyTakenFlag
	 */
	public boolean isUsernameAlreadyTaken() {
		return usernameAlreadyTakenFlag;
	}
	
	public String generateVerbose() {
		if (isOutcomePositive() == true) {
			return new String("@ok");
		}
		else {
			if (isUsernameAlreadyTaken() == true ) {
				return new String("@no,@usernameOccupato");
			} else {
				return new String("@no");
			}
		}
	}
	
	public String toString() {
		return generateVerbose();
	}	
}
