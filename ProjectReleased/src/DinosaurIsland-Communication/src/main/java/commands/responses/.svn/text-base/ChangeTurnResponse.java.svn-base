/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;


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
 * @since	Jun 2, 2011@11:37:34 AM
 *
 */
public class ChangeTurnResponse extends Response {

	private String username;
	

	public ChangeTurnResponse(String theUsername) {
		setUsername(theUsername);
		setOutcomePositive(true);
	}

	public void setUsername(String theUsername) {
		username = theUsername;
	}

	public String getUsername() {
		return username;
	}
	
	public String generateVerbose() {
		return "@cambioTurno,"+getUsername();
	}
	
	public String toString() {
		return generateVerbose();
	}	
}
