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

import util.Position;
import beans.DinosaurStatus;

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
public class DinosaurStatusResponseFactory extends ResponseFactory {
	
	static final DinosaurStatusResponseFactory uniqueInstance = new DinosaurStatusResponseFactory();

	public static DinosaurStatusResponseFactory getInstance() {
		return uniqueInstance;
	}
	
	private DinosaurStatusResponseFactory() {
		super();
	}
	
	public Response createGeneralResponse(String theStringToBeParsed) throws IllegalArgumentException {
		return createResponse(theStringToBeParsed);
	}
	
	public DinosaurStatusResponse createResponse(String theStringToBeParsed) throws IllegalArgumentException {

		ArrayList<String> tokens = CommandHelper.tokenizeArguments(theStringToBeParsed);
		validate(tokens);
		ArrayList<String> args = CommandHelper.getCommandArguments(tokens);
		
		
		DinosaurStatus dinosaurStatusBean = null;
		boolean invalidTokenFlag = false;
		boolean invalidDinosaurIDFlag = false;
		boolean playerNotInGameFlag = false;
		
		if (getResponsePositivity()) {
			
			String theUsername = args.get(0);
			String theSpeciesName = args.get(1);
			Character theSpeciesType = args.get(2).charAt(0);
			Position thePos = new Position(CommandHelper.parseVector(args.get(3), CommandHelper.getNewCommaSeparator()));
			int theSize = (int) (Float.parseFloat(args.get(4)));
			
			if (args.size() == 7) {
	
				int theEnergy = (int) (Float.parseFloat(args.get(5)));
				int theTurnsAlive = (int) (Float.parseFloat(args.get(6)));
				
				dinosaurStatusBean = new DinosaurStatus(theUsername, theSpeciesName, theSpeciesType, thePos, theSize, theEnergy, theTurnsAlive);
			} else {
				dinosaurStatusBean = new DinosaurStatus(theUsername, theSpeciesName, theSpeciesType, thePos, theSize);
			}
		
		}
		else {
		
			if (args.size() > 0) {
				if (args.get(0).equals("@tokenNonValido")) {
					 invalidTokenFlag = true;
				} else if (args.get(0).equals("@nonInPartita")) {
					playerNotInGameFlag = true;
				} else if (args.get(0).equals("@idNonValido")) {
					invalidDinosaurIDFlag = true;
				}
			}
		}
		
		return new DinosaurStatusResponse(getResponsePositivity(), dinosaurStatusBean, invalidTokenFlag, invalidDinosaurIDFlag, playerNotInGameFlag);
	}
	

	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.responseFactory.dinosaurStatusResponseFactory");
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
		validCommandTags.add(new String("@statoDinosauro"));
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
		regexpPatternArguments.add(new String("(\\w+)"));
		regexpPatternArguments.add(new String("(c|e)"));
		regexpPatternArguments.add(new String(CommandHelper.getVector2DRegexp(CommandHelper.getNewCommaSeparator())));
		regexpPatternArguments.add(new String("([1-5])"));
		regexpPatternArguments.add(new String("([0-9]+)?"));
		regexpPatternArguments.add(new String("([0-9]+)?"));
		
		
	}
	/* {@inheritDoc}
	 * @see communication.CommandFactory#populateRegexpArguments()
	 * 
	 */
	@Override
	protected void populateRegexpNegativeArguments() {
		regexpPatternNegativeArguments.add(new String("(@tokenNonValido|@nonInPartita|@idNonValido)?"));
	}
}
