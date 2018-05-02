/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;
import beans.Highscore;
import commands.responses.HighscoreResponse;
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
public class HighscoreRequest extends Request {


		protected String token;

		HighscoreRequest(String theToken, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
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
			Highscore highscore = null;
			
			try {
				getLogger().info("Requesting players in game list");
				highscore = getSocketClientHandler().getHighscore(token);
				getLogger().info("Sending the player the highscore");
				outcome = true;
				getSocketClientHandler().write(new HighscoreResponse(outcome, highscore, invalidTokenFlag).generateVerbose());
				getLogger().info("Players in game list sent");
			}
			catch (InvalidTokenException e) {
				invalidTokenFlag = true;
				getLogger().info("Sending the player that his request for the highscore cannot be accomplished due to the invalid token given");
				getSocketClientHandler().write(new HighscoreResponse(outcome, highscore, invalidTokenFlag).generateVerbose());
				return;
			}
			catch (IllegalArgumentException e) {
				getLogger().info("Sending the player that his request for the highscore cannot be accomplished due a general error");
				getSocketClientHandler().write(new HighscoreResponse(outcome, highscore, invalidTokenFlag).generateVerbose());
				return;
			}
		}	
		
	public String toString() {
		return "@classifica,token=" + token;
	}

}
