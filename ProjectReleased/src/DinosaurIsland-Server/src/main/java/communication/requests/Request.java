/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;
import java.util.logging.Logger;
import communication.SocketClientHandler;
import logging.Loggable;

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
 * @since	May 25, 2011@10:24:23 AM
 *
 */
public abstract class Request implements Loggable {
	
	protected SocketClientHandler socketClientHandler = null;
	
	Request(SocketClientHandler theSocketClientHandler) {
		socketClientHandler = theSocketClientHandler;
	}
	
	void setSocketClientHandler(SocketClientHandler theSocketClientHandler) {
		socketClientHandler = theSocketClientHandler;
	}
	
	SocketClientHandler getSocketClientHandler() {
		return socketClientHandler;
	}
	
	public void setupLogger() {
		/* The logger for a Request command is already setup in the SocketClientHandler class */
	}
	
	public Logger getLogger() {
		return getSocketClientHandler().getLogger();
	}
	
	public abstract void execute() throws IOException;
	
}
