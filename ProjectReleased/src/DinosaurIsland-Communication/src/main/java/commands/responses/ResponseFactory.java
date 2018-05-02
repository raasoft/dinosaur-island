/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands.responses;

import java.util.ArrayList;
import commands.CommandFactory;
import commands.CommandHelper;

public abstract class ResponseFactory extends CommandFactory {

	protected ArrayList<String> regexpPatternNegativeArguments = new ArrayList<String>();
	
	/**
	 * 
	 * 
	 * @since Jun 1, 2011@7:28:56 PM
	 */
	ResponseFactory() {
		super();
		populateRegexpPositiveArguments();
		populateRegexpNegativeArguments();
	}
	
	abstract protected void populateRegexpPositiveArguments();
	abstract protected void populateRegexpNegativeArguments();

	boolean responsePositivity;
	
	/**
	 * @return the responseIsPositive
	 */
	protected boolean getResponsePositivity() {
		return responsePositivity;
	}

	/**
	 * @param responseIsPositive the responseIsPositive to set
	 */
	protected void setResponsePositivity(boolean theResponsePositivity) {
		responsePositivity = theResponsePositivity;
	}

	protected void computeResponsePositivity(String theResponseTag) {
		
		if (theResponseTag.equals("@no"))
			setResponsePositivity(false);
		else
			setResponsePositivity(true);
	}
	
	public abstract Response createGeneralResponse(String theStringToBeParsed);

	/* {@inheritDoc}
	 * @see communication.CommandFactory#validate(java.util.ArrayList)
	 * 
	 */
	@Override
	protected void validate(ArrayList<String> theTokens)
			throws IllegalArgumentException {

		validateCommandTag(theTokens);
		computeResponsePositivity(CommandHelper.getCommandTag(theTokens));
		
		ArrayList<String> theArguments = CommandHelper.getCommandArguments(theTokens);
		if (getResponsePositivity() == true) {
			CommandHelper.validate(theArguments, regexpPatternArguments);	
		} else {
			CommandHelper.validate(theArguments, regexpPatternNegativeArguments);
		}
	}
}

