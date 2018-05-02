/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.util.ArrayList;
import java.util.logging.Logger;

import commands.CommandHelper;
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
 * @since	Jun 4, 2011@12:28:40 PM
 *
 */
final public class CreateUserRequestFactory extends RequestFactory {

	static final CreateUserRequestFactory uniqueInstance = new CreateUserRequestFactory();
	
	private CreateUserRequestFactory() {
		super();
	}
	
	public CreateUserRequest createRequest(ArrayList<String> theTokens, SocketClientHandler socketClientHandler) throws IllegalArgumentException {
		
		validate(theTokens);
		
		ArrayList<String> theArgs =  CommandHelper.getCommandArguments(theTokens);
		
		int equalsPosition;
		equalsPosition = theArgs.get(0).indexOf('=');
		String username =  theArgs.get(0).substring(equalsPosition+1);		
		equalsPosition = theArgs.get(1).indexOf('=');
		String password =  theArgs.get(1).substring(equalsPosition+1);
		
		return new CreateUserRequest(username, password, socketClientHandler);
	}
	
	public static CreateUserRequestFactory getInstance() {
		return uniqueInstance;
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
		logger = Logger.getLogger("server.commandfactory.requestfactory.createuserrequestfactory");
		logger.setParent(Logger.getLogger("server.main"));		
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#getLogger()
	 * 
	 */
	@Override
	public Logger getLogger() {
		return logger;
	}

	/* {@inheritDoc}
	 * @see commands.CommandFactory#populateValidCommandTags()
	 * 
	 */
	@Override
	protected void populateValidCommandTags() {
		validCommandTags.add("@creaUtente");		
	}

	/* {@inheritDoc}
	 * @see commands.CommandFactory#populateRegexpArguments()
	 * 
	 */
	@Override
	protected void populateRegexpArguments() {
		regexpPatternArguments.add("user=\\w+");
		regexpPatternArguments.add("pass=\\w+");
	}

}
