/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import commands.CommandHelper;



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
 * @since	Jun 2, 2011@11:38:10 AM
 *
 */
public class CreateUserResponseFactory extends ResponseFactory {
	
	static final CreateUserResponseFactory uniqueInstance = new CreateUserResponseFactory();

	public static CreateUserResponseFactory getInstance() {
		return uniqueInstance;
	}
	
	public Response createGeneralResponse(String theStringToBeParsed) throws IllegalArgumentException {
		return createResponse(theStringToBeParsed);
	}
	
	private CreateUserResponseFactory() {
		super();
	}
	
	public CreateUserResponse createResponse(String theStringToBeParsed) throws IllegalArgumentException {

		ArrayList<String> tokens = CommandHelper.tokenizeArguments(theStringToBeParsed);
		validate(tokens);
		ArrayList<String> args = CommandHelper.getCommandArguments(tokens);
				
		boolean usernameAlreadyTaken = false;
		
		if (args.size() > 0 && args.get(0).isEmpty() == false)
			usernameAlreadyTaken = true;
		
		return new CreateUserResponse(getResponsePositivity(), usernameAlreadyTaken);
	}
	

	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.responseFactory.createUserResponseFactory");
		logger.setParent(Logger.getLogger("client.main"));
		logger.setLevel(Level.ALL);
		logger.setUseParentHandlers(true);
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
	 * @see communication.CommandFactory#populateValidCommandTags()
	 * 
	 */
	@Override
	protected void populateValidCommandTags() {
		validCommandTags.add(new String("@ok"));
		validCommandTags.add(new String("@no"));
	}

	/* {@inheritDoc}
	 * @see communication.CommandFactory#populateRegexpArguments()
	 * 
	 */
	@Override
	protected void populateRegexpPositiveArguments() {}

	/* {@inheritDoc}
	 * @see commands.responses.ResponseFactory#populateRegexpNegativeArguments()
	 * 
	 */
	@Override
	protected void populateRegexpNegativeArguments() {
		regexpPatternNegativeArguments.add(new String("(@usernameOccupato)?"));		
	}
}
