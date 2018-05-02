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
public class EnterGameResponse extends Response {
	
	private boolean tooManyPlayersFlag;
	private boolean speciesNotCreatedFlag;
	private boolean invalidTokenFlag;
	
	
	public EnterGameResponse(boolean theOutcome, boolean theTooManyPlayersFlag, boolean theSpeciesNotCreatedFlag, boolean theInvalidTokenFlag) {
		setOutcomePositive(theOutcome);
		setTooManyPlayersFlag(theTooManyPlayersFlag);
		setSpeciesNotCreatedFlag(theSpeciesNotCreatedFlag);
		setInvalidTokenFlag(theInvalidTokenFlag);
	}
	
	/**
	 * @return the tooManyPlayersFlag
	 */
	public boolean isTooManyPlayers() {
		return tooManyPlayersFlag;
	}

	/**
	 * @param theTooManyPlayersFlag the tooManyPlayersFlag to set
	 */
	public void setTooManyPlayersFlag(boolean theTooManyPlayersFlag) {
		tooManyPlayersFlag = theTooManyPlayersFlag;
	}

	/**
	 * @return the speciesNotCreatedFlag
	 */
	public boolean isSpeciesNotCreated() {
		return speciesNotCreatedFlag;
	}

	/**
	 * @param theSpeciesNotCreatedFlag the speciesNotCreatedFlag to set
	 */
	public void setSpeciesNotCreatedFlag(boolean theSpeciesNotCreatedFlag) {
		speciesNotCreatedFlag = theSpeciesNotCreatedFlag;
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
			if (isTooManyPlayers() == true ) {
				return new String("@no,@troppiGiocatori");
			} else if (isSpeciesNotCreated() == true ) {
				return new String("@no,@razzaNonCreata");
			} else if (isInvalidToken() == true) {
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
