/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;

import beans.MainMap;

import commands.responses.MainMapResponse;
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
 * @author	RAA
 * @version 1.0
 * @since	Jun 4, 2011@12:32:45 PM
 *
 */
final class MainMapRequest extends Request {

	protected String token;

	MainMapRequest(String theToken, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
		super(theSocketClientHandler);
		
		token = theToken;		
		
	}

	/* {@inheritDoc}
	 * @see communication.Request#execute()
	 * 
	 */
	@Override
	public void execute() throws IOException {
		
		boolean invalidTokenFlag = false;
		boolean playerNotInGame = false;
		boolean outcome = false;
		MainMap mainMap = null;

		
		try {
		mainMap = getSocketClientHandler().getMainMap(token);
		} catch (InvalidTokenException e) {
			invalidTokenFlag = true;
			getLogger().info("Sending the user a message that inform he/she that the identifier given for the local view request is not valid");
			getSocketClientHandler().write(new MainMapResponse(outcome, mainMap, invalidTokenFlag, playerNotInGame).generateVerbose());
			return;
		} catch (PlayerNotInGameException e) {
			playerNotInGame = true;
			getLogger().info("Sending the user a message that inform he/she is not in game");
			getSocketClientHandler().write(new MainMapResponse(outcome, mainMap, invalidTokenFlag, playerNotInGame).generateVerbose());
			return;			
		} catch (NullPointerException e) {
			getLogger().info("Sending the user a message that inform he/she cannot obtain the main map");
			getSocketClientHandler().write(new MainMapResponse(outcome, mainMap, invalidTokenFlag, playerNotInGame).generateVerbose());
			return;			
		}
		
		outcome = true;
		getLogger().info("Sending the user the main map");
		getSocketClientHandler().write(new MainMapResponse(outcome, mainMap, invalidTokenFlag, playerNotInGame).generateVerbose());
		return;
	}	
	
public String toString() {
	return "@mappaGenerale,token=" + token;
}

}
