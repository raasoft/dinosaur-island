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

import beans.NewBornID;

import commands.CommandHelper;

public class LayEggResponseFactory extends ResponseFactory {

	static final LayEggResponseFactory uniqueInstance = new LayEggResponseFactory();

	public static LayEggResponseFactory getInstance() {
		return uniqueInstance;
	}
	
	private LayEggResponseFactory() {
		super();
	}
	
	public Response createGeneralResponse(String theStringToBeParsed) throws IllegalArgumentException {
		return createResponse(theStringToBeParsed);
	}
	
	public LayEggResponse createResponse(String theStringToBeParsed) throws IllegalArgumentException {

		ArrayList<String> tokens = CommandHelper.tokenizeArguments(theStringToBeParsed);
		validate(tokens);
		ArrayList<String> args = CommandHelper.getCommandArguments(tokens);
		
		boolean invalidTokenFlag = false;
		boolean invalidDinosaurIDFlag = false;
		boolean maxMovesLimitFlag = false;
		boolean starvationDeathFlag = false;
		boolean turnNotOwnedByPlayerFlag = false;
		boolean playerNotInGameFlag = false;
		boolean speciesFullFlag = false;
		NewBornID newBornID = null;
		
		if (getResponsePositivity()) {
			
			newBornID = new NewBornID(args.get(0));
		
		} else {
		
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
					turnNotOwnedByPlayerFlag = true;
				} else if (args.get(0).equals("@nonInPartita")) {
					playerNotInGameFlag = true;
				} else if (args.get(0).equals("@raggiuntoNumeroMaxDinosauri")) {
					speciesFullFlag = true;
				} 
			}
		}
		
		return new LayEggResponse(getResponsePositivity(), invalidTokenFlag, invalidDinosaurIDFlag,
				maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag,
				playerNotInGameFlag, speciesFullFlag, newBornID);
	}
	

	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.responseFactory.layEggResponseFactory");
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
		regexpPatternArguments.add(new String("(\\w+)"));
	}
	/* {@inheritDoc}
	 * @see communication.CommandFactory#populateRegexpArguments()
	 * 
	 */
	@Override
	protected void populateRegexpNegativeArguments() {
		regexpPatternNegativeArguments.add(new String("(@tokenNonValido|@idNonValido|@raggiuntoNumeroMaxDinosauri|@raggiuntoLimiteMosseDinosauro|@mortePerInedia|@nonIlTuoTurno|@nonInPartita)?"));
	}

}
