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

public class MoveDinosaurResponseFactory extends ResponseFactory {

	static final MoveDinosaurResponseFactory uniqueInstance = new MoveDinosaurResponseFactory();

	public static MoveDinosaurResponseFactory getInstance() {
		return uniqueInstance;
	}
	
	private MoveDinosaurResponseFactory() {
		super();
	}
	
	public Response createGeneralResponse(String theStringToBeParsed) throws IllegalArgumentException {
		return createResponse(theStringToBeParsed);
	}
	
	public MoveDinosaurResponse createResponse(String theStringToBeParsed) throws IllegalArgumentException {

		ArrayList<String> tokens = CommandHelper.tokenizeArguments(theStringToBeParsed);
		validate(tokens);
		ArrayList<String> args = CommandHelper.getCommandArguments(tokens);
		
		boolean invalidTokenFlag = false;
		boolean invalidDinosaurIDFlag = false;
		boolean invalidDestinationFlag = false;
		boolean maxMovesLimitFlag = false;
		boolean starvationDeathFlag = false;
		boolean turnNotOwnedByPlayerFlag = false;
		boolean playerNotInGameFlag = false;
		boolean fightEncounteredFlag = false;
		boolean fightWonFlag = false;
		
		if (getResponsePositivity()) {
			if (args.size() > 0) {
				if (args.get(0).equals("@combattimento")) {
					fightEncounteredFlag = true;
					if (args.get(1).equals("v")) {
						fightWonFlag = true;
					} 
				}
		}
			
		
		}
		else {
		
			if (args.size() > 0) {
				if (args.get(0).equals("@tokenNonValido")) {
					 invalidTokenFlag = true;
				} else if (args.get(0).equals("@idNonValido")) {
					invalidDinosaurIDFlag = true;
				} else if (args.get(0).equals("@destinazioneNonValida")) {
					invalidDestinationFlag = true;
				} else if (args.get(0).equals("@raggiuntoLimiteMosseDinosauro")) {
					maxMovesLimitFlag = true;
				} else if (args.get(0).equals("@mortePerInedia")) {
					starvationDeathFlag = true;
				} else if (args.get(0).equals("@nonIltuoTurno")) {
					turnNotOwnedByPlayerFlag = true;
				} else if (args.get(0).equals("@nonInPartita")) {
					playerNotInGameFlag = true;	
				}
			}
		}
		return new MoveDinosaurResponse(getResponsePositivity(), 
				invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
				starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag); 
	}
		

	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.responseFactory.moveDinosaurResponseFactory");
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
	protected void populateRegexpPositiveArguments() {
		CommandHelper.getInstance();
		regexpPatternArguments.add(new String("(@combattimento)?"));
		regexpPatternArguments.add(new String("(v|p)?"));
		
	}
	/* {@inheritDoc}
	 * @see communication.CommandFactory#populateRegexpArguments()
	 * 
	 */
	@Override
	protected void populateRegexpNegativeArguments() {
		regexpPatternNegativeArguments.add(new String("(@tokenNonValido|@idNonValido|@destinazioneNonValida|@raggiuntoLimiteMosseDinosauro|@mortePerInedia|@nonIltuoTurno|@nonInPartita)?"));
	}

}
