package communication;


import exceptions.InvalidTokenException;
import gameplay.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import logging.Loggable;
import util.StackTraceUtils;

import commands.responses.ChangeTurnResponse;
import communication.identifiers.TokenManager;
import communication.requests.Request;

public class SocketClientHandler extends ClientHandler implements Loggable {


	/**
	 * 
	 */
	final static int PING_INTERVAL = 5000;
	private int elapsedTime;

	private Logger logger;
	private Socket socket = null;
	private InputStreamReader inputStream;
	private OutputStreamWriter outputStream;
	private BufferedReader in;
	private BufferedWriter out;

	Object writeLock = new Object();
	
	/**
	 * 
	 */
	public SocketClientHandler(Socket theSocket) throws IOException, IllegalArgumentException {

		super();

		setupLogger();

		elapsedTime = 0;

		initSocket(theSocket);

	}
	
	private void setSocket(Socket theSocket) {
		socket = theSocket;
	}

	private Socket getSocket()
	{
		return socket; 
	}

	private void initSocket(Socket theSocket) {
		if (theSocket == null) {
			logger.severe("Can't handle the client with a null socket");
			throw new IllegalArgumentException();
		}

		logger.fine("Created SOCKET client handler");
		setSocket(theSocket);

		try {
			inputStream = new InputStreamReader (getSocket().getInputStream());
		}
		catch (IOException e) {
			logger.info("Can't get the input stream from the socket");
		}

		in = new BufferedReader(inputStream);

		try {
			outputStream = new OutputStreamWriter(getSocket().getOutputStream());
		}
		catch (IOException e) {
			logger.info("Can't get the output stream from the socket");
		}

		out = new BufferedWriter(outputStream);
	}

	synchronized public void run() {

		String lineRead = "";

		try	{

			while (isRunning) {

				lineRead = "";
				while (!inputStream.ready())
				{	
					
					if (elapsedTime > PING_INTERVAL) {
						
						//If the protocol expects a Ping command place it here
						//i.e. sendPing();
						
						getLogger().info("Trying to understand if the client is still connected.");

						int byt = inputStream.read();

						if ( byt == -1)
						{
							throw new IOException();
						}
						else
						{
							char a = (char) byt;
							lineRead = lineRead  + a;
						}
						
						getLogger().info("The client is still connected.");
						
						elapsedTime = 0;
					}

					try {
						Thread.sleep(500);
						elapsedTime+=500;
					}
					catch (InterruptedException e) {
						logger.info("Interrupted manually from the SocketManager");
						closeDown();
						return;
					}
				}

				logger.finest("Trying to catch the input from the client");
				lineRead += in.readLine();

				logger.finest("Received from client: "+ lineRead);
				Request requestParsed = ServerRequestParser.parse(lineRead,this);

				if (requestParsed != null)
					requestParsed.execute();
				else {
					write("@comandoNonValido");
				}
			}
		}
		catch (IOException e) {
			handleClientDisconnection(e);
			return;
		}
	}

	public void write(String theString) {
		
		synchronized (writeLock) {

			if (getPlayerToken() != null ) {
	
				Player thePlayer = TokenManager.getInstance().getObject(getPlayerToken());
				getLogger().info("To be written: "+theString + " to the client: "+thePlayer.getUsername());
	
			} else {
				getLogger().info("To be written: "+theString + " to an unknown client");
			}
	
	
			try {
				out.write(theString+"\n");
				out.flush();
			} catch (IOException e) {
				handleClientDisconnection(e);
			}
			getLogger().info("Write done succesfully");
		
		}
	}

	protected void handleClientDisconnection(IOException theException) {
		
		logger.warning("The client "+getPlayerToken()+ " disconnected");
		logger.warning(StackTraceUtils.getCatchMessage(theException));

		logger.warning("Trying to logout the client disconnected");
		/* Log out the player (it will be removed from the game also)*/
		try {
			logout(getPlayerToken());
		} catch (RemoteException e) {
			getLogger().severe(StackTraceUtils.getThrowMessage(e));
		} catch (InvalidTokenException e) {
			getLogger().severe(StackTraceUtils.getThrowMessage(e));
		}

		closeDown();
		SocketManager.getInstance().requestClientHandlerClosure(this);
		
	}

	public void closeDown()
	{
		logger.info("Finalizing the socket thread");

		try {
			getSocket().close();
			logger.info("The socket is closed");
		}
		catch (IOException e) {
			logger.severe("Can't close the socket");
			e.printStackTrace();
		}
		
		logger.info("Socket is closed? " + getSocket().isClosed());
		logger.info("Socket is bound? " + getSocket().isBound());
		logger.info("Socket is connected? " + getSocket().isConnected());

		logger.info("Finalized the socket thread");
		isRunning = false;

	}
	
	//Can be used but it is not standard protocol compliant
	public void sendPing() {
		
		System.out.println("Ping sending.");

		try {

			System.out.println("PING WRITE.");
			outputStream.write("ping");
			outputStream.flush();

			
		} catch (IOException e) {
			handleClientDisconnection(e);
		}

		System.out.println("Ping sent.");
		
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#getLogger()
	 * 
	 */
	@Override
	public Logger getLogger() {
		return logger;
	}

	public void setupLogger() {
		logger = Logger.getLogger("server.clienthandler."+this);
		logger.setParent(Logger.getLogger("server.main"));
	}

	/* {@inheritDoc}
	 * @see communication.ClientHandler#changeTurn(java.lang.String)
	 * 
	 */
	@Override
	public void changeTurn(String theUsername) {

		getLogger().info("Sending via socket the turn change. Next player is "+theUsername);
		write(new ChangeTurnResponse(theUsername).generateVerbose());

		return;

	}
}

