/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.naming.AuthenticationException;

import util.StackTraceUtils;

import commands.responses.LoginResponse;
import communication.SocketClientHandler;

import exceptions.EmptyIdentifierPoolException;
import exceptions.NotFoundException;
import exceptions.PasswordMismatchException;

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
 * @since	Jun 4, 2011@12:32:24 PM
 *
 */
final class LoginRequest extends UserPassRequest {

	LoginRequest(String theUsername, String thePassword, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException	{
		super(theUsername, thePassword, theSocketClientHandler);
	}
	
	
	static String generateVerbose(String theUsername, String thePassword) {
		return new String("@login,user="+theUsername+",pass="+thePassword);
	}
	
	public String toString() {
		return generateVerbose(username, password);
	}
	
	public void execute() throws IOException {
		
		String token = "";
		boolean positiveOutcome = false;
		boolean authenticationFailedFlag = false;
		
		try {
			token = getSocketClientHandler().loginPlayer(username, password);
		}
		catch (IllegalArgumentException e) {
			getLogger().warning("CATCH: "+StackTraceUtils.getStackTrace(e));
			getSocketClientHandler().write(new LoginResponse(positiveOutcome, authenticationFailedFlag, token).generateVerbose());
			return;
		}
		catch (NotFoundException e) {
			authenticationFailedFlag = true;
			getLogger().warning("CATCH: "+StackTraceUtils.getStackTrace(e));
			getSocketClientHandler().write(new LoginResponse(positiveOutcome, authenticationFailedFlag, token).generateVerbose());
			return;
		}
		catch (EmptyIdentifierPoolException e) {
			getLogger().warning("CATCH: "+StackTraceUtils.getStackTrace(e));
			getSocketClientHandler().write(new LoginResponse(positiveOutcome, authenticationFailedFlag, token).generateVerbose());
			return;
		} catch (PasswordMismatchException e) {
			authenticationFailedFlag = true;
			getLogger().warning("CATCH: "+StackTraceUtils.getStackTrace(e));
			getSocketClientHandler().write(new LoginResponse(positiveOutcome, authenticationFailedFlag, token).generateVerbose());
			return;
		} catch (NullPointerException e) {
			getLogger().warning("Username or password is null");
			getLogger().warning("CATCH: "+StackTraceUtils.getStackTrace(e));
			getSocketClientHandler().write(new LoginResponse(positiveOutcome, authenticationFailedFlag, token).generateVerbose());
			return;
		} catch (AuthenticationException e) {
			getLogger().warning("CATCH: "+StackTraceUtils.getStackTrace(e));
			getSocketClientHandler().write(new LoginResponse(positiveOutcome, authenticationFailedFlag, token).generateVerbose());
			return;
		} catch (RemoteException e) {
			getLogger().warning("CATCH: "+StackTraceUtils.getStackTrace(e));
			getSocketClientHandler().write(new LoginResponse(positiveOutcome, authenticationFailedFlag, token).generateVerbose());
			return;
		}
		
		 positiveOutcome = true;
		 authenticationFailedFlag = false;
		 
		 getSocketClientHandler().write(new LoginResponse(positiveOutcome, authenticationFailedFlag, token).generateVerbose());
		 getLogger().info("User: "+username+" has logged in successfully");

	}
}
