/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;
import beans.PlayersList;
import commands.responses.PlayersListResponse;
import communication.SocketClientHandler;
import exceptions.InvalidTokenException;

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
public class PlayersListRequest extends Request {


		protected String token;

		PlayersListRequest(String theToken, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
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
			PlayersList playerList = null;
			
			try {
				getLogger().info("Requesting players in game list");
				playerList = getSocketClientHandler().getInGamePlayersList(token);
				getLogger().info("Sending the player the  players in game list");
				outcome = true;
				getSocketClientHandler().write(new PlayersListResponse(outcome, playerList, invalidTokenFlag).generateVerbose());
				getLogger().info("Players in game list sent");
			}
			catch (InvalidTokenException e) {
				invalidTokenFlag = true;
				getLogger().info("Sending the player that his request for the players in game list cannot be accomplished due to the invalid token given");
				getSocketClientHandler().write(new PlayersListResponse(outcome, playerList, invalidTokenFlag).generateVerbose());
				return;
			}
			catch (IllegalArgumentException e) {
				getLogger().info("Sending the player that his request for the players in game list cannot be accomplished due a general error");
				getSocketClientHandler().write(new PlayersListResponse(outcome, playerList, invalidTokenFlag).generateVerbose());
				return;
			}
		}	
		
	public String toString() {
		return "@listaGiocatori,token=" + token;
	}

}
