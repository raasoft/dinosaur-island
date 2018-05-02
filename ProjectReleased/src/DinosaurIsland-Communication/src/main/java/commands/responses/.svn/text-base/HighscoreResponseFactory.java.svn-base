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

import beans.Highscore;
import beans.Score;

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
public class HighscoreResponseFactory extends ResponseFactory {
	
	static final HighscoreResponseFactory uniqueInstance = new HighscoreResponseFactory();

	public static HighscoreResponseFactory getInstance() {
		return uniqueInstance;
	}
	
	private HighscoreResponseFactory() {
		super();
	}
	
	public HighscoreResponse createResponse(String theStringToBeParsed) throws IllegalArgumentException {

		ArrayList<String> tokens = CommandHelper.tokenizeArguments(theStringToBeParsed);
		ArrayList<String> args = CommandHelper.getCommandArguments(tokens);
		validateCommandTag(tokens);
		computeResponsePositivity(CommandHelper.getCommandTag(tokens));
		
		Highscore highscore = null;
		boolean invalidTokenFlag = false;
		
		if (getResponsePositivity()) {
			
			ArrayList<Score> scoreList = new ArrayList<Score>();
			String comma = CommandHelper.getNewCommaSeparator();
			
			regexpPatternArguments.clear();
			for (int i = 0; i < args.size(); i++) {
				regexpPatternArguments.add(new String("(\\{\\w+"+comma+"\\w+"+comma+"[0-9]+"+comma+"(s|n)\\})*"));
			}
			
			System.out.println("The size of the args list is: "+args.size());
			System.out.println("The first argument of the args list is: "+args.get(0));
			
			if (args.size() > 0 &&  args.get(0).length() != 0 ) {
				CommandHelper.validate(args, regexpPatternArguments);
						
				for (String singleRow : args) {
					scoreList.add(new Score(singleRow));
				}
				
				highscore = new Highscore(scoreList);
			} else {
				highscore = new Highscore();
			}
			
		} else {
			if (args.size() > 0) {
				 if (args.get(0).equals("@tokenNonValido")) {
					 invalidTokenFlag = true;
				}
			}
		}
		
		return new HighscoreResponse(getResponsePositivity(), highscore, invalidTokenFlag);
	}
	
	public Response createGeneralResponse(String theStringToBeParsed) throws IllegalArgumentException {
		return createResponse(theStringToBeParsed);
	}

	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.responseFactory.playersListResponseFactory");
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
		validCommandTags.add(new String("@classifica"));
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
		regexpPatternNegativeArguments.add(new String("(@tokenNonValido)?"));
	}
}
