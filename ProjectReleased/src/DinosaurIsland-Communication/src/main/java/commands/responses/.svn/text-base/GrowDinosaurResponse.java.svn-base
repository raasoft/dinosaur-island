/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;

public class GrowDinosaurResponse extends Response {
	
	private boolean invalidTokenFlag;
	private boolean invalidDinosaurIDFlag;
	private boolean maxMovesLimitFlag;
	private boolean starvationDeathFlag;
	private boolean turnNotOwnedByPlayerFlag;
	private boolean playerNotInGameFlag;
	private boolean maxSizeReachedFlag;
	

	public GrowDinosaurResponse(boolean theOutcome, boolean theInvalidTokenFlag, boolean theInvalidDinosaurIDFlag,
			boolean theMaxMovesLimitFlag, boolean theStarvationDeathFlag, boolean theTurnNotOwnedByPlayerFlag,
			boolean thePlayerNotInGameFlag, boolean theMaxSizeReachedFlag) {
		setOutcomePositive(theOutcome);
		setInvalidTokenFlag(theInvalidTokenFlag);
		setInvalidDinosaurIDFlag(theInvalidDinosaurIDFlag);
		setMaxMovesLimitFlag(theMaxMovesLimitFlag);
		setStarvationDeathFlag(theStarvationDeathFlag);
		setTurnNotOwnedByPlayerFlag(theTurnNotOwnedByPlayerFlag);
		setPlayerNotInGameFlag(thePlayerNotInGameFlag);
		setMaxSizeReachedFlag(theMaxSizeReachedFlag);
		
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
	 * @return the maxSizeReachedFlag
	 */
	public boolean isMaxSizeReached() {
		return maxSizeReachedFlag;
	}

	/**
	 * @param maxSizeReachedFlag the maxSizeReachedFlag to set
	 */
	public void setMaxSizeReachedFlag(boolean theMaxSizeReachedFlag) {
		maxSizeReachedFlag = theMaxSizeReachedFlag;
	}

	public String generateVerbose() {
		if (isOutcomePositive() == true) {
			return new String("@ok");
		}
		else {
			if (isInvalidToken() == true ) {
				return new String("@no,@tokenNonValido");
			} else if (isInvalidDinosaurID() == true){
				return new String("@no,@idNonValido");
			} else if (isMaxMovesLimit() == true){
				return new String("@no,@raggiuntoLimiteMosseDinosauro");
			} else if (isStarvationDeath() == true){
				return new String("@no,@mortePerInedia");
			} else if (isTurnNotOwnedByPlayer() == true){
				return new String("@no,@nonIlTuoTurno");
			} else if (isPlayerNotInGame() == true){
				return new String("@no,@nonInPartita");
			} else if (isMaxSizeReached() == true){
				return new String("@no,@raggiuntaDimensioneMax");
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
