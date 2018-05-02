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

public class GrowDinosaurRequestFactory extends RequestFactory {

	static final GrowDinosaurRequestFactory uniqueInstance = new GrowDinosaurRequestFactory();
		
		private GrowDinosaurRequestFactory() {
			super();
		}
		
		public GrowDinosaurRequest createRequest(ArrayList<String> theTokens, SocketClientHandler socketClientHandler) throws IllegalArgumentException {
			
			validate(theTokens);
			
			ArrayList<String> theArgs =  CommandHelper.getCommandArguments(theTokens);
			
			int equalsPosition;
			equalsPosition = theArgs.get(0).indexOf('=');
			String token =  theArgs.get(0).substring(equalsPosition+1);
			
			equalsPosition = theArgs.get(1).indexOf('=');
			String dinosaurID =  theArgs.get(1).substring(equalsPosition+1);
			
			GrowDinosaurRequest command = new GrowDinosaurRequest(token, dinosaurID, socketClientHandler);
			return command;
		}
		
		public static GrowDinosaurRequestFactory getInstance() {
			return uniqueInstance;
		}
	
		/* {@inheritDoc}
		 * @see logging.Loggable#setupLogger()
		 * 
		 */
		@Override
		public void setupLogger() {
			logger = Logger.getLogger("server.commandFactory.requestfactory.growDinosaurRequestFactory");
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
			validCommandTags.add("@cresciDinosauro");		
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
