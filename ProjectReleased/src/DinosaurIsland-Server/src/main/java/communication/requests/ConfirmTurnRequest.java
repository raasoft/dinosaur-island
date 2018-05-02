/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;

import commands.responses.TurnResponse;
import communication.SocketClientHandler;

import exceptions.InvalidTokenException;
import exceptions.PlayerNotInGameException;
import exceptions.TurnNotOwnedByPlayerException;

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
 * @since	Jun 4, 2011@10:34:57 PM
 *
 */
public class ConfirmTurnRequest extends Request {
	protected String token;

	ConfirmTurnRequest(String theToken, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
		super(theSocketClientHandler);
		
		token = theToken;			
	}

	/* {@inheritDoc}
	 * @see communication.Request#execute()
	 * 
	 */
	@Override
	public
	void execute() throws IOException {
		
		boolean outcome = false;
		boolean invalidTokenFlag = false;
		boolean turnNotOwnedByPlayerFlag = false;
		boolean playerNotInGameFlag = false;
		
		try {
			getSocketClientHandler().confirmTurn(token);
			outcome = true;
			getLogger().info("Sending the user that he/she successfully confirmed the turn ");
			getSocketClientHandler().write(new TurnResponse(outcome, invalidTokenFlag, playerNotInGameFlag, turnNotOwnedByPlayerFlag).generateVerbose());
			return;
		}
		catch (InvalidTokenException e) {
			invalidTokenFlag = true;
			getLogger().info("Sending the user a message that inform he/she that the identifier given for the log out is not valid");
			getSocketClientHandler().write(new TurnResponse(outcome, invalidTokenFlag, playerNotInGameFlag, turnNotOwnedByPlayerFlag).generateVerbose());
			return;
		}
		catch (PlayerNotInGameException e) {
			playerNotInGameFlag = true;
			getLogger().info("Sending the user a message that inform he/she is not in game");
			getSocketClientHandler().write(new TurnResponse(outcome, invalidTokenFlag, playerNotInGameFlag, turnNotOwnedByPlayerFlag).generateVerbose());
			return;
		}
		catch (TurnNotOwnedByPlayerException e) {
			turnNotOwnedByPlayerFlag = true;
			getLogger().info("Sending the user a message that inform he/she that the turn is now owned by him/her");
			getSocketClientHandler().write(new TurnResponse(outcome, invalidTokenFlag, playerNotInGameFlag, turnNotOwnedByPlayerFlag).generateVerbose());
			return;
		}
		catch (IllegalArgumentException e) {
			getLogger().info("Sending the user a message that inform he/she that the player cannot perform the turn modification yet");
			getSocketClientHandler().write(new TurnResponse(outcome, invalidTokenFlag, playerNotInGameFlag, turnNotOwnedByPlayerFlag).generateVerbose());
			return;
		}
	
	}	
	
	public String toString() {
		return "@confermaTurno,token=" + token;
	}

}
