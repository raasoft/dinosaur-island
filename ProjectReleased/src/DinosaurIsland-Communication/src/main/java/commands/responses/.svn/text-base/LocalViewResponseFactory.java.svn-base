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

import util.StackTraceUtils;
import beans.LocalView;

import commands.CommandHelper;
import exceptions.InvalidMapException;

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
public class LocalViewResponseFactory extends ResponseFactory {
	
	static final LocalViewResponseFactory uniqueInstance = new LocalViewResponseFactory();

	public static LocalViewResponseFactory getInstance() {
		return uniqueInstance;
	}
	
	private LocalViewResponseFactory() {
		super();
	}
	
	public Response createGeneralResponse(String theStringToBeParsed) throws IllegalArgumentException {
		return createResponse(theStringToBeParsed);
	}
	
	public LocalViewResponse createResponse(String theStringToBeParsed) throws IllegalArgumentException {

		ArrayList<String> tokens = CommandHelper.tokenizeArguments(theStringToBeParsed);
		ArrayList<String> args = CommandHelper.getCommandArguments(tokens);
		validateCommandTag(tokens);
		computeResponsePositivity(CommandHelper.getCommandTag(tokens));
		
		LocalView localView = null;
		boolean invalidTokenFlag = false;
		boolean invalidDinosaurIDFlag = false;
		boolean playerNotInGameFlag = false;
		
		if (getResponsePositivity() && args.size() == 3) {
		
			String theLocalViewToBeParsed = args.get(0) + "," + args.get(1) + "," + args.get(2);
			
			try {
				localView = new LocalView(theLocalViewToBeParsed);
				setResponsePositivity(true);
			} catch (NullPointerException e) {
				IllegalArgumentException ex = new IllegalArgumentException(e.getMessage());
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			} catch (InvalidMapException e) {
				IllegalArgumentException ex = new IllegalArgumentException(e.getMessage());
				getLogger().warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
			}
			
			
		} else {
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
		
		return new LocalViewResponse(getResponsePositivity(), localView, invalidTokenFlag, invalidDinosaurIDFlag, playerNotInGameFlag);
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
		validCommandTags.add(new String("@vistaLocale"));
		validCommandTags.add(new String("@no"));
	}

	/* {@inheritDoc}
	 * @see communication.CommandFactory#populateRegexpArguments()
	 * 
	 */
	@Override
	protected void populateRegexpPositiveArguments() {
		CommandHelper.getInstance();
		regexpPatternArguments.add(new String(CommandHelper.getVector2DRegexp(CommandHelper.getNewCommaSeparator())));
		regexpPatternArguments.add(new String(CommandHelper.getVector2DRegexp(CommandHelper.getNewCommaSeparator())));
		regexpPatternArguments.add(new String("((\\[(a|t|v,([0-9]+)|c,([0-9]+)|d,(\\w+))\\])+;)+"));
		
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
