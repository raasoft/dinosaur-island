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
public class CreateSpeciesResponse extends Response {

	private boolean nameAlreadyTakenFlag;
	private boolean speciesAlreadyCreatedFlag;
	private boolean invalidTokenFlag;

	public CreateSpeciesResponse(boolean theOutcome, boolean theNameAlreadyTakenFlag, boolean theSpeciesAlreadyCreatedFlag, boolean theInvalidTokenFlag) {
		setOutcomePositive(theOutcome);
		setNameAlreadyTakenFlag(theNameAlreadyTakenFlag);
		setSpeciesAlreadyCreatedFlag(theSpeciesAlreadyCreatedFlag);
		setInvalidTokenFlag(theInvalidTokenFlag);
	}
	/**
	 * @param usernameAlreadyTakenFlag the usernameAlreadyTakenFlag to set
	 */
	public void setNameAlreadyTakenFlag(boolean theNameAlreadyTakenFlag) {
		nameAlreadyTakenFlag = theNameAlreadyTakenFlag;
	}
	/**
	 * @return the usernameAlreadyTakenFlag
	 */
	public boolean isNameAlreadyTaken() {
		return nameAlreadyTakenFlag;
	}
	
	public void setSpeciesAlreadyCreatedFlag(boolean theSpeciesAlreadyCreatedFlag) {
		speciesAlreadyCreatedFlag = theSpeciesAlreadyCreatedFlag;
	}
	
	public boolean isSpeciesAlreadyCreated() {
		return speciesAlreadyCreatedFlag;
	}
	
	public void setInvalidTokenFlag(boolean theInvalidTokenFlag) {
		invalidTokenFlag = theInvalidTokenFlag;
	}
	
	public boolean isInvalidToken() {
		return invalidTokenFlag;
	}
	
	public String generateVerbose() {
		if (isOutcomePositive() == true) {
			return new String("@ok");
		}
		else {
			if (isNameAlreadyTaken() == true ) {
				return new String("@no,@nomeRazzaOccupato");
			} else if (isSpeciesAlreadyCreated() == true){
				return new String("@no,@razzaGiaCreata");
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
