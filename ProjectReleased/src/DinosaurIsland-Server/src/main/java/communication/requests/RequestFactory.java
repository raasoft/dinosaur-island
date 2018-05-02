/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.util.ArrayList;
import commands.CommandFactory;
import communication.SocketClientHandler;


public abstract class RequestFactory extends CommandFactory {

	abstract protected void populateRegexpArguments();
	abstract public Request createRequest(ArrayList<String> theTokens, SocketClientHandler socketClientHandler) throws IllegalArgumentException;
	
	
	RequestFactory() {
		super();
		populateRegexpArguments();
	}
	
	public String toString() {
		return validCommandTags.get(0);
	}
}