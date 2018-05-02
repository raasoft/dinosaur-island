/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;

import beans.DinosaursList;
import commands.responses.DinosaursListResponse;
import communication.SocketClientHandler;
import exceptions.InvalidTokenException;
import exceptions.PlayerNotInGameException;

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
 * @since	Jun 4, 2011@12:36:14 PM
 *
 */
public class DinosaursListRequest extends Request {


		protected String token;

		DinosaursListRequest(String theToken, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
			super(theSocketClientHandler);
			
			token = theToken;			
		}

		/* {@inheritDoc}
		 * @see communication.Request#execute()
		 * 
		 */
		@Override
		public void execute() throws IOException {
			
			boolean outcome = false;
			boolean invalidTokenFlag = false;
			boolean playerNotInGameFlag = false;
			DinosaursList dinosaursList = null;
			
			try {
				getLogger().info("Requesting dinosaur in game list");
				dinosaursList = getSocketClientHandler().getInGameDinosaursList(token);
				getLogger().info("Sending the dinosaur the  players in game list");
				outcome = true;
				getSocketClientHandler().write(new DinosaursListResponse(outcome, dinosaursList, playerNotInGameFlag, invalidTokenFlag).generateVerbose());
				getLogger().info("Dinosaur in game list sent");
			}
			catch (InvalidTokenException e) {
				invalidTokenFlag = true;
				getLogger().info("Sending the player that his request for the dinosaur in game list cannot be accomplished due to the invalid token given");
				getSocketClientHandler().write(new DinosaursListResponse(outcome, dinosaursList, playerNotInGameFlag, invalidTokenFlag).generateVerbose());
				return;
			}
			catch (PlayerNotInGameException e) {
				playerNotInGameFlag = true;
				getLogger().info("Sending the player that his request for the dinosaur in game list cannot be accomplished due to the fact that the player is not in game");
				getSocketClientHandler().write(new DinosaursListResponse(outcome, dinosaursList, playerNotInGameFlag, invalidTokenFlag).generateVerbose());
				return;
			}
			catch (IllegalArgumentException e) {
				getLogger().info("Sending the player that his request for the dinosaur in game list cannot be accomplished due a general error");
				getSocketClientHandler().write(new DinosaursListResponse(outcome, dinosaursList, playerNotInGameFlag, invalidTokenFlag).generateVerbose());
				return;
			}
		}	
		
	public String toString() {
		return "@listaDinosauri,token=" + token;
	}

}
