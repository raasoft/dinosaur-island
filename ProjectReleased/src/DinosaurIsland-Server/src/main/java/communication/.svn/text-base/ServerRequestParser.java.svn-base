/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Logger;

import logging.Loggable;
import commands.CommandHelper;
import communication.requests.ConfirmTurnRequestFactory;
import communication.requests.CreateSpeciesRequestFactory;
import communication.requests.CreateUserRequestFactory;
import communication.requests.DinosaurStatusRequestFactory;
import communication.requests.DinosaursListRequestFactory;
import communication.requests.EnterGameRequestFactory;
import communication.requests.HighscoreRequestFactory;
import communication.requests.GrowDinosaurRequestFactory;
import communication.requests.LayEggRequestFactory;
import communication.requests.LeaveGameRequestFactory;
import communication.requests.LocalViewRequestFactory;
import communication.requests.LoginRequestFactory;
import communication.requests.LogoutRequestFactory;
import communication.requests.MainMapRequestFactory;
import communication.requests.MoveDinosaurRequestFactory;
import communication.requests.PassTurnRequestFactory;
import communication.requests.PlayersListRequestFactory;
import communication.requests.Request;
import communication.requests.RequestFactory;

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
 * @since	May 25, 2011@10:23:02 AM
 *
 */
public class ServerRequestParser implements Loggable  {

	static final Hashtable<String, RequestFactory> stringCommandMap = new Hashtable<String, RequestFactory>();
	static final ServerRequestParser uniqueInstance = new ServerRequestParser();
	static final String newCommaSeparator = new String("/");
	static Logger logger;
	
	public static ServerRequestParser getInstance()
	{
		return uniqueInstance;
	}
	
	/**
	 * 
	 * 
	 * @since May 24, 2011@10:25:28 AM
	 */
	private ServerRequestParser() {
		RequestFactory factory;
		
		factory = LoginRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
				
		factory = CreateUserRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = CreateSpeciesRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = EnterGameRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = LeaveGameRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = LogoutRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = PlayersListRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = DinosaursListRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = GrowDinosaurRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = MoveDinosaurRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = LayEggRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = DinosaurStatusRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = MainMapRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = LocalViewRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = ConfirmTurnRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = PassTurnRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		factory = HighscoreRequestFactory.getInstance();
		stringCommandMap.put(factory.toString(), factory);
		
		setupLogger();
	}
	
	static String getNewCommaSeparator() {
		return newCommaSeparator;
	}
	
	static Request parse(String theStringToBeParsed, SocketClientHandler theSocketClientHandler) {

		ArrayList<String> tokens = CommandHelper.tokenizeArguments(theStringToBeParsed);
		RequestFactory commandFactory = stringCommandMap.get(CommandHelper.getCommandTag(tokens));
		Request command = null;
		
		logger.fine("CommandTag: "+CommandHelper.getCommandTag(tokens));
		logger.fine("Arguments: "+CommandHelper.getCommandArguments(tokens));
		
		try {
			command = commandFactory.createRequest(tokens, theSocketClientHandler);	
		}
		catch (IllegalArgumentException e) {
			logger.warning("Command's arguments not formatted correctly: "+e.getMessage());
			return null;
		}
		catch (NullPointerException e) {
			logger.warning("Command not supported");
			return null;
		}
		
		return command;
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
		
		logger = Server.getInstance().getLogger();
		
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#getLogger()
	 * 
	 */
	@Override
	public Logger getLogger() { return logger;}
}
