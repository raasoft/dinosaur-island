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
public class ChangeTurnResponseFactory extends ResponseFactory {
	
	static final ChangeTurnResponseFactory uniqueInstance = new ChangeTurnResponseFactory();

	public static ChangeTurnResponseFactory getInstance() {
		return uniqueInstance;
	}
	
	private ChangeTurnResponseFactory() {
		super();
	}
	
	public ChangeTurnResponse createResponse(String theStringToBeParsed) throws IllegalArgumentException {

		ArrayList<String> tokens = CommandHelper.tokenizeArguments(theStringToBeParsed);
		validate(tokens);
		ArrayList<String> args = CommandHelper.getCommandArguments(tokens);
						
		String theUsername = args.get(0);
		
		return new ChangeTurnResponse(theUsername);
	}
	

	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.responseFactory.changeTurnResponseFactory");
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
		validCommandTags.add(new String("@cambioTurno"));
	}

	/* {@inheritDoc}
	 * @see communication.CommandFactory#populateRegexpArguments()
	 * 
	 */
	@Override
	protected void populateRegexpPositiveArguments() {
		regexpPatternArguments.add(new String("\\w+"));
	}

	/* {@inheritDoc}
	 * @see commands.responses.ResponseFactory#populateRegexpNegativeArguments()
	 * 
	 */
	@Override
	protected void populateRegexpNegativeArguments() {}

	public Response createGeneralResponse(String theStringToBeParsed) throws IllegalArgumentException {
		return createResponse(theStringToBeParsed);
	}
}
