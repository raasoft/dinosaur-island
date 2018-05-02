/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;

import commands.responses.LeaveGameResponse;
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
public class LeaveGameRequest extends Request {


		protected String token;

		LeaveGameRequest(String theToken, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
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
			
			try {
				getSocketClientHandler().leaveGame(token);
				outcome = true;
				getSocketClientHandler().write(new LeaveGameResponse(outcome, invalidTokenFlag).generateVerbose());
			}
			catch (InvalidTokenException e) {
				invalidTokenFlag = true;
				getSocketClientHandler().write(new LeaveGameResponse(outcome, invalidTokenFlag).generateVerbose());
				return;
			} catch (PlayerNotInGameException e) {
				getSocketClientHandler().write(new LeaveGameResponse(outcome, invalidTokenFlag).generateVerbose());
				return;
			}
		}	
		
	public String toString() {
		return "@uscitaPartita,token=" + token;
	}

}
