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
 * @author	AXXO
 * @version 1.0
 * @since	Jun 4, 2011@3:20:44 PM
 *
 */
public class EnterGameResponseFactory extends ResponseFactory {
	
	static final EnterGameResponseFactory uniqueInstance = new EnterGameResponseFactory();

	public static EnterGameResponseFactory getInstance() {
		return uniqueInstance;
	}
	
	public Response createGeneralResponse(String theStringToBeParsed) throws IllegalArgumentException {
		return createResponse(theStringToBeParsed);
	}
	
	private EnterGameResponseFactory() {
		super();
	}
	
	public EnterGameResponse createResponse(String theStringToBeParsed) throws IllegalArgumentException {

		ArrayList<String> tokens = CommandHelper.tokenizeArguments(theStringToBeParsed);
		validate(tokens);
		ArrayList<String> args = CommandHelper.getCommandArguments(tokens);
		
		boolean tooManyPlayersFlag = false;
		boolean speciesNotCreatedFlag = false;
		boolean invalidTokenFlag = false;
		
		if (args.size() > 0) {
			if (args.get(0).equals("@troppiGiocatori")) {
				tooManyPlayersFlag = true;
			}
			else if (args.get(0).equals("@razzaNonCreata")) {
				speciesNotCreatedFlag = true;
			}
			else if (args.get(0).equals("@tokenNonValido")) {
				invalidTokenFlag = true;
			}
		}
				
		return new EnterGameResponse(getResponsePositivity(), tooManyPlayersFlag, speciesNotCreatedFlag, invalidTokenFlag);
	}
	

	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.responseFactory.enterGameResponseFactory");
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
	 * @see communication.CommandFactory#populateRegexpArguments()
	 * 
	 */
	@Override
	protected void populateRegexpNegativeArguments() {
		regexpPatternNegativeArguments.add(new String("(@troppiGiocatori|@razzaNonCreata|@tokenNonValido)?"));
	}
}
