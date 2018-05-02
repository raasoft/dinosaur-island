/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;

import beans.PlayersList;

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
final public class PlayersListResponse extends Response {
	
	private PlayersList playerInGameList; 
	private boolean invalidTokenFlag;
	
	/**
	 * @return the playerInGameList
	 */
	public PlayersList getPlayerInGameList() {
		return playerInGameList;
	}

	/**
	 * @param playerInGameList the playerInGameList to set
	 */
	public void setPlayerInGameList(PlayersList thePlayerInGameList) {
		playerInGameList = thePlayerInGameList;
	}


	
	
	public PlayersListResponse(boolean theOutcome, PlayersList thePlayerInGameList, boolean theInvalidTokenFlag) {
		setOutcomePositive(theOutcome);
		setInvalidTokenFlag(theInvalidTokenFlag);
		setPlayerInGameList(thePlayerInGameList);
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
			if (playerInGameList.getStringsNumber() > 0) {
				comma = ",";
			}
			return new String("@listaGiocatori"+comma+playerInGameList.toString());
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
