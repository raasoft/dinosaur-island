/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands;

import java.util.ArrayList;
import java.util.logging.Logger;
import logging.Loggable;
import util.StackTraceUtils;

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
 * @since	Jun 1, 2011@6:15:25 PM
 *
 */

public abstract class CommandFactory implements Loggable {

	protected Logger logger;
	protected ArrayList<String> validCommandTags = new ArrayList<String>();
	protected ArrayList<String> regexpPatternArguments = new ArrayList<String>();

	abstract protected void populateValidCommandTags();
	
	protected void validateCommandTag(ArrayList<String> theTokens) throws IllegalArgumentException {
		
		String theTag = theTokens.get(0);
		
		for (String aValidCommandTag : validCommandTags) {
			if (aValidCommandTag.equals(theTag))
				return;
		}
		IllegalArgumentException ex = new IllegalArgumentException("The command tag "+theTag+" is not valid for this factory");
		getLogger().warning(StackTraceUtils.getThrowMessage(ex));
		throw ex;
	}
	
	protected CommandFactory() {
		setupLogger();
		populateValidCommandTags();
	}
	
	protected void validate(ArrayList<String> theTokens) throws IllegalArgumentException	{
		
		validateCommandTag(theTokens);
		
		ArrayList<String> theArguments = CommandHelper.getCommandArguments(theTokens);
		
		CommandHelper.validate(theArguments, regexpPatternArguments);
	}
}
