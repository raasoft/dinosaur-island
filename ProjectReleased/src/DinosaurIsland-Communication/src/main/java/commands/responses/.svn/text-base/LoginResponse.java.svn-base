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
public class LoginResponse extends Response {

	private boolean authenticationFailedFlag;
	String token;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String theToken) {
		token = theToken;
	}
	public LoginResponse(boolean theOutcome, boolean authenticationFailedFlag, String theToken) {
		setOutcomePositive(theOutcome);
		setAuthenticationFailedFlag(authenticationFailedFlag);
		setToken(theToken);
	}
	/**
	 * @param usernameAlreadyTakenFlag the usernameAlreadyTakenFlag to set
	 */
	public void setAuthenticationFailedFlag(boolean theAuthenticationFailedFlag) {
		authenticationFailedFlag = theAuthenticationFailedFlag;
	}
	/**
	 * @return the usernameAlreadyTakenFlag
	 */
	public boolean isAuthenticationFailed() {
		return authenticationFailedFlag;
	}
	
	public String generateVerbose() {
		if (isOutcomePositive() == true) {
			return new String("@ok,"+getToken());
		}
		else {
			if (isAuthenticationFailed() == true ) {
				return new String("@no,@autenticazioneFallita");
			} else {
				return new String("@no");
			}
		}
	}
	
	public String toString() {
		return generateVerbose();
	}	
}
