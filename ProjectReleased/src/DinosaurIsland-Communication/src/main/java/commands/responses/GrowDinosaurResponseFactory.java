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

public class GrowDinosaurResponseFactory extends ResponseFactory {

	static final GrowDinosaurResponseFactory uniqueInstance = new GrowDinosaurResponseFactory();

	public static GrowDinosaurResponseFactory getInstance() {
		return uniqueInstance;
	}
	
	private GrowDinosaurResponseFactory() {
		super();
	}
	
	public GrowDinosaurResponse createResponse(String theStringToBeParsed) throws IllegalArgumentException {

		ArrayList<String> tokens = CommandHelper.tokenizeArguments(theStringToBeParsed);
		validate(tokens);
		ArrayList<String> args = CommandHelper.getCommandArguments(tokens);
				
		boolean invalidTokenFlag = false;
		boolean invalidDinosaurIDFlag = false;
		boolean maxMovesLimitFlag = false;
		boolean starvationDeathFlag = false;
		boolean turnNotOwnedByPlayerFlag = false;
		boolean playerNotInGameFlag = false;
		boolean maxSizeReachedFlag = false;
		
		if (args.size() > 0 && args.get(0).isEmpty() == false) {
			if (args.get(0).equals("@tokenNonValido")) {
				invalidTokenFlag = true;
			} else if (args.get(0).equals("@idNonValido")) {
				invalidDinosaurIDFlag = true;
			} else if (args.get(0).equals("@raggiuntoLimiteMosseDinosauro")) {
				maxMovesLimitFlag = true;
			} else if (args.get(0).equals("@mortePerInedia")) {
				starvationDeathFlag = true;
			} else if (args.get(0).equals("@nonIlTuoTurno")) {
				starvationDeathFlag = true;
			} else if (args.get(0).equals("@nonInPartita")) {
				starvationDeathFlag = true;
			} else if (args.get(0).equals("@raggiuntaDimensioneMax")) {
				maxSizeReachedFlag = true;
			} 
		}
		
		return new GrowDinosaurResponse(getResponsePositivity(), invalidTokenFlag, invalidDinosaurIDFlag,
				maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag,
				playerNotInGameFlag, maxSizeReachedFlag);
	}
	

	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.responseFactory.GrowDisonaurResponseFactory");
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

	public Response createGeneralResponse(String theStringToBeParsed) throws IllegalArgumentException {
		return createResponse(theStringToBeParsed);
	}
	
	/* {@inheritDoc}
	 * @see commands.responses.ResponseFactory#populateRegexpNegativeArguments()
	 * 
	 */
	@Override
	protected void populateRegexpNegativeArguments() {
		regexpPatternNegativeArguments.add(new String("(@tokenNonValido|@idNonValido|@raggiuntaDimensioneMax|@raggiuntoLimiteMosseDinosauro|@mortePerInedia|@nonIlTuoTurno|@nonInPartita)?"));
	}

}
