/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;

import commands.responses.EnterGameResponse;
import communication.SocketClientHandler;

import exceptions.InvalidTokenException;
import exceptions.MaxInGamePlayersExceededException;
import exceptions.PlayerAlreadyInGameException;
import exceptions.SpeciesNotCreatedException;

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
public class EnterGameRequest extends Request {


		protected String token;

		EnterGameRequest(String theToken, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
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
			boolean tooManyPlayersFlag = false;
			boolean speciesNotCreatedFlag = false;
			boolean invalidTokenFlag = false;
			
			try {
				getSocketClientHandler().canEnterGame(token);
				
				outcome = true;
				getLogger().info("Sending the user that he/she successfully entered the game");
				getSocketClientHandler().write(new EnterGameResponse(outcome, tooManyPlayersFlag, speciesNotCreatedFlag, invalidTokenFlag).generateVerbose());
				
				getSocketClientHandler().enterGame(token);
				return;
			}
			catch (InvalidTokenException e) {
				invalidTokenFlag = true;
				getSocketClientHandler().write(new EnterGameResponse(outcome, tooManyPlayersFlag, speciesNotCreatedFlag, invalidTokenFlag).generateVerbose());
				return;
			}
			catch (IllegalArgumentException e) {
				getSocketClientHandler().write(new EnterGameResponse(outcome, tooManyPlayersFlag, speciesNotCreatedFlag, invalidTokenFlag).generateVerbose());
				return;
			}
			catch (PlayerAlreadyInGameException e) {
				getSocketClientHandler().write(new EnterGameResponse(outcome, tooManyPlayersFlag, speciesNotCreatedFlag, invalidTokenFlag).generateVerbose());
				return;
			}
			catch (SpeciesNotCreatedException e) {
				speciesNotCreatedFlag = true;
				getSocketClientHandler().write(new EnterGameResponse(outcome, tooManyPlayersFlag, speciesNotCreatedFlag, invalidTokenFlag).generateVerbose());
				return;
			}
			catch (MaxInGamePlayersExceededException e) {
				tooManyPlayersFlag = true;
				getSocketClientHandler().write(new EnterGameResponse(outcome, tooManyPlayersFlag, speciesNotCreatedFlag, invalidTokenFlag).generateVerbose());
				return;
			}
		}	
		
	public String toString() {
		return "@accessoPartita,token=" + token;
	}

}
