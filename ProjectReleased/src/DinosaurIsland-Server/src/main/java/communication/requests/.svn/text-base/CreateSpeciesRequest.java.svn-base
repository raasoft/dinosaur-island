/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import exceptions.InvalidTokenException;
import exceptions.NameAlreadyTakenException;
import exceptions.SpeciesAlreadyCreatedException;
import java.io.IOException;

import commands.responses.CreateSpeciesResponse;
import communication.SocketClientHandler;

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
final class CreateSpeciesRequest extends Request {

	protected String token;
	protected String name;
	protected String speciesType;

	CreateSpeciesRequest(String theToken, String theName, String theSpeciesType, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
		super(theSocketClientHandler);
		
		token = theToken;
		name = theName;
		speciesType = theSpeciesType;
		
		
	}

	/* {@inheritDoc}
	 * @see communication.Request#execute()
	 * 
	 */
	@Override
	public void execute() throws IOException {
		
		boolean outcome = false;
		boolean nameAlreadyTakenFlag = false;
		boolean speciesAlreadyCreatedFlag = false;
		boolean invalidTokenFlag = false;
		
		try {
			getSocketClientHandler().createSpecies(token, name, speciesType);
			getLogger().info("Creation of a new species succeeded");
			outcome = true;
			getLogger().info("Sending the user a \"creation species succesfully\" confirmation message");
			getSocketClientHandler().write(new CreateSpeciesResponse(outcome, nameAlreadyTakenFlag, speciesAlreadyCreatedFlag, invalidTokenFlag).generateVerbose());
			return;
		} catch (IllegalArgumentException e) {
			getLogger().info("Sending the user a \"creation species NOT SUCCEEDED\" message");
			getSocketClientHandler().write(new CreateSpeciesResponse(outcome, nameAlreadyTakenFlag, speciesAlreadyCreatedFlag, invalidTokenFlag).generateVerbose());
			return;
		} catch (SpeciesAlreadyCreatedException e) {
			speciesAlreadyCreatedFlag = true;
			getLogger().info("Sending the user a message that inform he/she that he/she has already created a species");
			getSocketClientHandler().write(new CreateSpeciesResponse(outcome, nameAlreadyTakenFlag, speciesAlreadyCreatedFlag, invalidTokenFlag).generateVerbose());
			return;
		} catch (InvalidTokenException e) {
			invalidTokenFlag = true;
			getLogger().info("Sending the user a message that inform he/she that the identifier given for the log out is not valid");
			getSocketClientHandler().write(new CreateSpeciesResponse(outcome, nameAlreadyTakenFlag, speciesAlreadyCreatedFlag, invalidTokenFlag).generateVerbose());
			return;
		} catch (NameAlreadyTakenException e) {
			nameAlreadyTakenFlag = true;
			getLogger().info("Sending the user a message that inform he/she that the name of the species he/she wants to create has been already taken");
			getSocketClientHandler().write(new CreateSpeciesResponse(outcome, nameAlreadyTakenFlag, speciesAlreadyCreatedFlag, invalidTokenFlag).generateVerbose());
			return;
		}
	}	
	
public String toString() {
	return "@creaRazza,token=" + token + ",nome=" + name + ",tipo=" + speciesType;
}

}
