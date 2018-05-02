/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.util.ArrayList;
import java.util.logging.Logger;

import commands.CommandHelper;
import communication.SocketClientHandler;

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
 * @since	Jun 4, 2011@10:43:59 PM
 *
 */
public class DinosaurStatusRequestFactory extends RequestFactory {
	
static final DinosaurStatusRequestFactory uniqueInstance = new DinosaurStatusRequestFactory();
	
	private DinosaurStatusRequestFactory() {
		super();
	}
	
	public DinosaurStatusRequest createRequest(ArrayList<String> theTokens, SocketClientHandler socketClientHandler) throws IllegalArgumentException {
		
		validate(theTokens);
		
		ArrayList<String> theArgs =  CommandHelper.getCommandArguments(theTokens);
		
		int equalsPosition;
		equalsPosition = theArgs.get(0).indexOf('=');
		String token =  theArgs.get(0).substring(equalsPosition+1);		
		
		equalsPosition = theArgs.get(1).indexOf('=');
		String dinosaurID =  theArgs.get(1).substring(equalsPosition+1);		
		
		DinosaurStatusRequest command = new DinosaurStatusRequest(token, dinosaurID, socketClientHandler);
		return command;
	}
	
	public static DinosaurStatusRequestFactory getInstance() {
		return uniqueInstance;
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
		logger = Logger.getLogger("server.commandFactory.requestfactory.dinosaurStatusRequestFactory");
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
		validCommandTags.add("@statoDinosauro");		
	}

	/* {@inheritDoc}
	 * @see commands.CommandFactory#populateRegexpArguments()
	 * 
	 */
	@Override
	protected void populateRegexpArguments() {
		regexpPatternArguments.add("token=\\w+");
		regexpPatternArguments.add("idDino=\\w+");
	}	

}
