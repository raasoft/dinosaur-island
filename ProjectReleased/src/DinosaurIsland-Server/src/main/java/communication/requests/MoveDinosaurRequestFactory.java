/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.util.ArrayList;
import java.util.logging.Logger;

import util.Position;

import commands.CommandHelper;
import communication.SocketClientHandler;

public class MoveDinosaurRequestFactory extends RequestFactory {

static final MoveDinosaurRequestFactory uniqueInstance = new MoveDinosaurRequestFactory();
	
	private MoveDinosaurRequestFactory() {
		super();
	}
	
	public MoveDinosaurRequest createRequest(ArrayList<String> theTokens, SocketClientHandler socketClientHandler) throws IllegalArgumentException {
		
		validate(theTokens);
		
		ArrayList<String> theArgs =  CommandHelper.getCommandArguments(theTokens);
		
		int equalsPosition;
		equalsPosition = theArgs.get(0).indexOf('=');
		String token =  theArgs.get(0).substring(equalsPosition+1);		
		
		equalsPosition = theArgs.get(1).indexOf('=');
		String dinosaurID =  theArgs.get(1).substring(equalsPosition+1);
		
		equalsPosition = theArgs.get(2).indexOf('=');
		Position destination = new Position(CommandHelper.parseVector(theArgs.get(2).substring(equalsPosition+1), CommandHelper.getNewCommaSeparator()));
		
		MoveDinosaurRequest command = new MoveDinosaurRequest(token, dinosaurID, destination, socketClientHandler);
		return command;
	}
	
	public static MoveDinosaurRequestFactory getInstance() {
		return uniqueInstance;
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
		logger = Logger.getLogger("server.commandFactory.requestfactory.moveDinosaurRequestFactory");
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
		validCommandTags.add("@muoviDinosauro");		
	}

	/* {@inheritDoc}
	 * @see commands.CommandFactory#populateRegexpArguments()
	 * 
	 */
	@Override
	protected void populateRegexpArguments() {
		regexpPatternArguments.add("token=\\w+");
		regexpPatternArguments.add("idDino=\\w+");
		regexpPatternArguments.add("dest=" + CommandHelper.getVector2DRegexp(CommandHelper.getNewCommaSeparator()));
	}	

}
