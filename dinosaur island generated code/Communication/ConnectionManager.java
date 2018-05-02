package Dinosaur Island.Communication;

import java.io.*;
import java.util.*;

public abstract class ConnectionManager {

	protected Server uniqueInstance = Null;
	protected int port;
	protected int connections;
	protected ArrayList<Communication> activeCommunications;

	public Server getInstance() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public abstract void start();

	public abstract void stop();

	protected abstract ClientHandler newClientHandler();

	public int getConnections() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getConnectionsMax() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getPort() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public abstract void RAAXXOgetActiveCommunications();

	public void setPort(int thePort) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	protected void setConnections() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
