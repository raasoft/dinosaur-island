/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;

import commands.responses.CreateUserResponse;
import communication.SocketClientHandler;

import exceptions.NameAlreadyTakenException;

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
 * @since	Jun 4, 2011@12:33:04 PM
 *
 */
final class CreateUserRequest extends UserPassRequest {

	CreateUserRequest(String theUsername, String thePassword, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException	{
		super(theUsername, thePassword, theSocketClientHandler);
	}
	
	
	static String generateVerbose(String theUsername, String thePassword) {
		return new String("@creaUtente,user="+theUsername+",pass="+thePassword);
	}
	
	public String toString() {
		return generateVerbose(username, password);
	}
	
	public void execute() throws IOException {
		
		try {
			getSocketClientHandler().createUser(username, password);
		}
		catch (IllegalArgumentException e) {
			
			boolean isPositive = false;
			boolean isUsernameAlreadyTaken = false;
			
			getSocketClientHandler().write(new CreateUserResponse(isPositive, isUsernameAlreadyTaken).generateVerbose());
			getLogger().warning("Username is not valid");
			return;
		} 
		catch (NameAlreadyTakenException e) {
			boolean isPositive = false;
			boolean isUsernameAlreadyTaken = true;
			
			getSocketClientHandler().write(new CreateUserResponse(isPositive, isUsernameAlreadyTaken).generateVerbose());
			getLogger().warning("Username is already taken");
			return;
		}
		
		boolean isPositive = true;
		boolean isUsernameAlreadyTaken = false;
		
		getSocketClientHandler().write(new CreateUserResponse(isPositive, isUsernameAlreadyTaken).generateVerbose());
		getLogger().info("User: "+username+" has been created");
	}

}
