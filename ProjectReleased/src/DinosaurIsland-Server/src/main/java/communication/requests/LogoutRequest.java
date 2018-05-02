/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;

import commands.responses.LogoutResponse;
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
 * @since	Jun 4, 2011@10:34:57 PM
 *
 */
public class LogoutRequest extends Request {
	protected String token;

	LogoutRequest(String theToken, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
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
			getSocketClientHandler().logout(token);
			outcome = true;
			getLogger().info("Sending the user a log out confirmation message");
			getSocketClientHandler().write(new LogoutResponse(outcome, invalidTokenFlag).generateVerbose());
			getLogger().info("User correctly logged out");
			getSocketClientHandler().closeDown();
			return;
		}
		catch (InvalidTokenException e) {
			invalidTokenFlag = true;
			getLogger().info("Sending the user a message that inform he/she that the identifier given for the log out is not valid");
			getSocketClientHandler().write(new LogoutResponse(outcome, invalidTokenFlag).generateVerbose());
			return;
		}
		catch (IllegalArgumentException e) {
			getLogger().info("Sending the user a message that inform he/she that the player cannot log yet");
			getSocketClientHandler().write(new LogoutResponse(outcome, invalidTokenFlag).generateVerbose());
			return;
		}
	
	}	
	
	public String toString() {
		return "@logout,token=" + token;
	}

}
