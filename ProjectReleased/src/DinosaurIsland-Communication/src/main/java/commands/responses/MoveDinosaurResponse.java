/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;

public class MoveDinosaurResponse extends Response {
	
	boolean invalidTokenFlag = false;
	boolean invalidDinosaurIDFlag = false;
	boolean invalidDestinationFlag = false;
	boolean maxMovesLimitFlag = false;
	boolean starvationDeathFlag = false;
	boolean turnNotOwnedByPlayerFlag = false;
	boolean playerNotInGameFlag = false;
	boolean fightEncounteredFlag = false;
	boolean fightWonFlag = false;
	
	public MoveDinosaurResponse(boolean theOutcome,
			boolean theInvalidTokenFlag, boolean theInvalidDinosaurIDFlag, boolean theInvalidDestinationFlag,
			boolean theMaxMovesLimitFlag, boolean theStarvationDeathFlag, boolean theTurnNotOwnedByPlayerFlag, 
			boolean thePlayerNotInGameFlag, boolean theFightEncounteredFlag, boolean theFightWonFlag) {
		setOutcomePositive(theOutcome);
		setInvalidTokenFlag(theInvalidTokenFlag);
		setInvalidDinosaurIDFlag(theInvalidDinosaurIDFlag);
		setInvalidDestinationFlag(theInvalidDestinationFlag);
		setMaxMovesLimitFlag(theMaxMovesLimitFlag);
		setStarvationDeathFlag(theStarvationDeathFlag);
		setTurnNotOwnedByPlayerFlag(theTurnNotOwnedByPlayerFlag);
		setPlayerNotInGameFlag(thePlayerNotInGameFlag);
		setFightEncounteredFlag(theFightEncounteredFlag);
		setFightWonFlag(theFightWonFlag);
		
	}

	
	/**
	 * @return the invalidTokenFlag
	 */
	public boolean isInvalidToken() {
		return invalidTokenFlag;
	}


	/**
	 * @param invalidTokenFlag the invalidTokenFlag to set
	 */
	public void setInvalidTokenFlag(boolean theInvalidTokenFlag) {
		invalidTokenFlag = theInvalidTokenFlag;
	}


	/**
	 * @return the invalidDinosaurIDFlag
	 */
	public boolean isInvalidDinosaurID() {
		return invalidDinosaurIDFlag;
	}


	/**
	 * @param invalidDinosaurIDFlag the invalidDinosaurIDFlag to set
	 */
	public void setInvalidDinosaurIDFlag(boolean theInvalidDinosaurIDFlag) {
		invalidDinosaurIDFlag = theInvalidDinosaurIDFlag;
	}


	/**
	 * @return the invalidDestinationFlag
	 */
	public boolean isInvalidDestination() {
		return invalidDestinationFlag;
	}


	/**
	 * @param invalidDestinationFlag the invalidDestinationFlag to set
	 */
	public void setInvalidDestinationFlag(boolean theInvalidDestinationFlag) {
		invalidDestinationFlag = theInvalidDestinationFlag;
	}


	/**
	 * @return the maxMovesLimitFlag
	 */
	public boolean isMaxMovesLimit() {
		return maxMovesLimitFlag;
	}


	/**
	 * @param maxMovesLimitFlag the maxMovesLimitFlag to set
	 */
	public void setMaxMovesLimitFlag(boolean theMaxMovesLimitFlag) {
		maxMovesLimitFlag = theMaxMovesLimitFlag;
	}


	/**
	 * @return the starvationDeathFlag
	 */
	public boolean isStarvationDeath() {
		return starvationDeathFlag;
	}


	/**
	 * @param starvationDeathFlag the starvationDeathFlag to set
	 */
	public void setStarvationDeathFlag(boolean theStarvationDeathFlag) {
		starvationDeathFlag = theStarvationDeathFlag;
	}


	/**
	 * @return the turnNotOwnedByPlayerFlag
	 */
	public boolean isTurnNotOwnedByPlayer() {
		return turnNotOwnedByPlayerFlag;
	}


	/**
	 * @param turnNotOwnedByPlayerFlag the turnNotOwnedByPlayerFlag to set
	 */
	public void setTurnNotOwnedByPlayerFlag(boolean theTurnNotOwnedByPlayerFlag) {
		turnNotOwnedByPlayerFlag = theTurnNotOwnedByPlayerFlag;
	}


	/**
	 * @return the playerNotInGameFlag
	 */
	public boolean isPlayerNotInGame() {
		return playerNotInGameFlag;
	}


	/**
	 * @param playerNotInGameFlag the playerNotInGameFlag to set
	 */
	public void setPlayerNotInGameFlag(boolean thePlayerNotInGameFlag) {
		playerNotInGameFlag = thePlayerNotInGameFlag;
	}


	/**
	 * @return the fightEncounteredFlag
	 */
	public boolean isFightEncountered() {
		return fightEncounteredFlag;
	}


	/**
	 * @param fightEncounteredFlag the fightEncounteredFlag to set
	 */
	public void setFightEncounteredFlag(boolean theFightEncounteredFlag) {
		fightEncounteredFlag = theFightEncounteredFlag;
	}


	/**
	 * @return the fightWonFlag
	 */
	public boolean isFightWon() {
		return fightWonFlag;
	}


	/**
	 * @param fightWonFlag the fightWonFlag to set
	 */
	public void setFightWonFlag(boolean theFightWonFlag) {
		fightWonFlag = theFightWonFlag;
	}


	public String generateVerbose() {
		if (isOutcomePositive() == true) {
			if (isFightEncountered() == false) {
				return new String("@ok");
			} else {
				if (isFightWon() == true) {
					return new String("@ok,@combattimento,v");
				} else {
					return new String("@ok,@combattimento,p");
				}
			}
		}
		else {
			if (isPlayerNotInGame() == true ) {
				return new String("@no,@nonInPartita");
			} else if (isInvalidToken() == true) {
				return new String("@no,@tokenNonValido");
			} else if (isInvalidDinosaurID() == true) {
				return new String("@no,@idNonValido");
			} else if (isInvalidDestination() == true) {
				return new String("@no,@destinazioneNonValida");
			} else if (isMaxMovesLimit() == true) {
				return new String("@no,@raggiuntoLimiteMosseDinosauro");
			} else if (isStarvationDeath() == true) {
				return new String("@no,@mortePerInedia");
			} else if (isTurnNotOwnedByPlayer() == true) {
				return new String("@no,@nonIlTuoTurno");
			} else {
				return new String("@no");
			}
		}
	}
	
	public String toString() {
		return generateVerbose();
	}

}
