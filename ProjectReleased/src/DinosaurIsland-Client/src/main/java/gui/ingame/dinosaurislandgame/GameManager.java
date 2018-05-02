package gui.ingame.dinosaurislandgame;

import exceptions.FightLostException;
import exceptions.FightWonException;
import exceptions.IdentifierNotPresentException;
import exceptions.InvalidDestinationException;
import exceptions.InvalidDinosaurIDException;
import exceptions.InvalidTokenException;
import exceptions.MaxMovesLimitException;
import exceptions.MaxSizeReachedException;
import exceptions.NegativeResponseException;
import exceptions.PlayerNotInGameException;
import exceptions.SpeciesIsFullException;
import exceptions.StarvationDeathException;
import exceptions.TurnNotOwnedByPlayerException;

import gui.ingame.core.Dinosaur;
import gui.ingame.core.DinosaurManager;
import gui.ingame.core.GameCore;
import gui.ingame.core.GameState;
import gui.ingame.dinosaurislandgame.ResourceManager.InGameInformationIcon;
import gui.ingame.input.GameAction;
import gui.ingame.input.InputManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logging.Loggable;
import util.Position;
import util.StackTraceUtils;
import beans.DinosaursList;
import beans.Highscore;
import beans.LocalView;
import beans.MainMap;
import beans.PlayersList;
import beans.Score;
import communication.Client;
import communication.ServerInterface;
import util.AlarmTimer;



/**
    GameManager manages all parts of the game.
*/
public class GameManager extends GameCore implements Loggable {

	Logger logger;  
	
	static private GameManager uniqueInstance;
	
	private final String NONE_PLAYER = "#NONE#";
	private String currentPlayerPlaying = NONE_PLAYER;

    private TileMap map;
    private ResourceManager resourceManager;
    private InputManager inputManager;

    private GameAction returnToWaitingRoom;
    private GameAction confirmTurn;
    private GameAction confirmExit;
    private GameAction denyExit;
    private GameAction passTurn;
    private GameAction skipTurn;
    private GameAction abortMoveAction;
    private GameAction mouseDXClick;
    private GameAction mouseMapClick;
    private GameAction mouseButtonClick;
    private GameAction exit;
    
    private PlayersList ingamePlayersList;
    
    protected GameState gameState;
    protected boolean changingTurn;
    protected boolean userWantsToLeaveGame;
    protected TurnTimer turnTimer;
    protected KeepAliveTimer keepAliveTimer;
    
    static private boolean gameIsLoaded;
    static private Object gameIsLoadedLock = new Object();
    protected boolean isServerDisconnected;

	/**
	 * @return the isServerDisconnected
	 */
	public boolean isServerDisconnected() {
		return isServerDisconnected;
	}

	/**
	 * @param isServerDisconnected the isServerDisconnected to set
	 */
	void setServerDisconnected(boolean isServerDisconnected) {
		this.isServerDisconnected = isServerDisconnected;
	}

	Position cellSourceSelected;
	Position cellDestinationSelected;
    
	int timeElapsed;
	
	boolean movingActionInProgress;
	boolean canPerformLayEgg;
	boolean canPerformGrow;
	boolean canPerformMove;
    
	boolean isRunning = true;
	
	boolean firstMainMapLoading = false;
	boolean readyToPlayTurn = false;
	boolean playerIsDied = false;
	
	private int score;

	private GameManager() {
		
		setupLogger();
		
		gameIsLoaded = false;
		
        turnTimer = new TurnTimer(0);
        turnTimer.start();
        
        keepAliveTimer = new KeepAliveTimer();
        keepAliveTimer.start();
	}
	
	static public GameManager getInstance() {
    	if (uniqueInstance == null) {
    		uniqueInstance = new GameManager();
    	} 
    	
    	return uniqueInstance;
	}
	
	

	public void init() {
        super.init();
                
        getLogger().info("Starting to loading the game room");
        
        // set up input manager
        initInput();

        // start resource manager
        resourceManager = ResourceManager.getInstance(
        screen.getFullScreenWindow().getGraphicsConfiguration());

        // load a blank map
        map = resourceManager.getMap();
        map.setRenderer(new GameRenderer(screen.getWidth(), screen.getHeight()));
        map.setBlankMap();
        
        
        //gameState = GameState.PLAYING_TURN;
        gameState = GameState.WAITING_TURN;
        
        gameIsLoaded = true;
        synchronized (gameIsLoadedLock) {
		
        	getLogger().info("The game room is now loaded. Notify all!");
        	gameIsLoadedLock.notifyAll();
        	
		}
        
        if (checkIfPlayerIsStillInGame() == false)
        	return;
        
        setInGameInformationPlayersInGame();
                
    }


    /**
        Closes any resources used by the GameManager.
    */
    public void stop() {
    	freeResources();
        super.stop();
    }
    
    private void freeResources() {
    	uniqueInstance = null;
    	turnTimer.stopForever();
    	keepAliveTimer.stopForever();
    	
    	DinosaurManager.getInstance().freeResources();
    	map.freeResources();
    }

    private void handleServerDisconnection() {
    	
    	getLogger().severe("SERVER DISCONNECTED. HANDLING THIS SITUATION");
    	changeGameState(GameState.SERVER_DISCONNECTED);
    	
    }


    private void initInput() {

        exit = new GameAction("exit",
            GameAction.DETECT_INITAL_PRESS_ONLY);
        mouseDXClick = new GameAction("mouseDXClick", 
        		GameAction.NORMAL);
        mouseMapClick = new GameAction("mouseMapClick", 
        		GameAction.DETECT_INITAL_PRESS_ONLY);
        
        mouseButtonClick = new GameAction("mouseButtonClick", 
        		GameAction.DETECT_INITAL_PRESS_ONLY);

        confirmTurn = new GameAction("confirmTurn", 
        		GameAction.DETECT_INITAL_PRESS_ONLY);
        
        passTurn = new GameAction("denyExit", 
        		GameAction.DETECT_INITAL_PRESS_ONLY);
        
        confirmExit = new GameAction("confirmExit", 
        		GameAction.DETECT_INITAL_PRESS_ONLY);
        
        denyExit = new GameAction("denyExit", 
        		GameAction.DETECT_INITAL_PRESS_ONLY);
        
        skipTurn = new GameAction("skipTurn", 
        		GameAction.DETECT_INITAL_PRESS_ONLY);
        
        abortMoveAction = new GameAction("abortMoveAction", 
        		GameAction.DETECT_INITAL_PRESS_ONLY);
        
        returnToWaitingRoom = new GameAction("returnToWaitingRoom", 
        		GameAction.DETECT_INITAL_PRESS_ONLY);
        
        inputManager = new InputManager(
            screen.getFullScreenWindow());
        
        inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
        
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(confirmTurn, KeyEvent.VK_Y);
        inputManager.mapToKey(passTurn, KeyEvent.VK_N);
        inputManager.mapToKey(denyExit, KeyEvent.VK_N);
        inputManager.mapToKey(confirmExit, KeyEvent.VK_Y);
        inputManager.mapToKey(returnToWaitingRoom, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(skipTurn, KeyEvent.VK_S);
        inputManager.mapToKey(abortMoveAction, KeyEvent.VK_X);
        
        inputManager.mapToMouse(mouseDXClick, InputManager.MOUSE_BUTTON_3);
        inputManager.mapToMouse(mouseMapClick, InputManager.MOUSE_BUTTON_1);
        inputManager.mapToMouse(mouseButtonClick, InputManager.MOUSE_BUTTON_1);
        
    }
    
    private void checkInputNotPlaying(long elapsedTime) {
    	
    	if (returnToWaitingRoom.isPressed()) {
			changeGameState(GameState.STOPPED);
		}
    }


    private void checkInput(long elapsedTime) {

    	boolean mapWasClicked = mouseMapClick.isPressed(); 
    	
    	map.getRenderer().setMousePosition(inputManager.getMouseX(), inputManager.getMouseY());
    	
    	if (getGameState() != GameState.NOT_PLAYING && getGameState() != GameState.STOPPED) {

    		if (isUserWantsToLeaveGame() == false) {
    			if (exit.isPressed() && exit.getAmount() == 0) {
    				setUserWantsToLeaveGame(true);
    				confirmExit.reset();
    				returnToWaitingRoom.reset();
    				exit.reset();
    			}
    		} else {
    			exit.reset();
    			returnToWaitingRoom.reset();
    			if (confirmExit.isPressed()) {
    				changeGameState(GameState.NOT_PLAYING);
    				confirmExit.reset();
    				confirmTurn.reset();
    			}

    			if (denyExit.isPressed()) {
    				setUserWantsToLeaveGame(false);
    				denyExit.reset();
    				passTurn.reset();
    			}	
    		}

    		if (isUserWantsToLeaveGame() == false) {

    			if (getGameState() != GameState.STOPPED) {     

    				map.getRenderer().updateScrolling(mouseDXClick.isPressed());
    				
    				if (isMovingActionInProgress() == false)
    					map.updateCellSourceSelected(mapWasClicked);
    				else
    					map.updateCellDestinationSelected(mapWasClicked);

    				if (mapWasClicked) {
    					refreshAllActions();
    					mouseMapClick.press(); //reuse this mouseclick later
    					
    				}

    			}
    		}
    	}
    }

	protected GameState getGameState() {
		return gameState;
	}
	
	int getTurnTimerInterval() {
		return turnTimer.getInterval() / 1000;
	}
	
	public void changeGameState(GameState theState) throws IllegalStateException {
		
		getLogger().fine("Changing state");
		GameState currentState = getGameState();
		
		switch (currentState)
		{
			case NOT_PLAYING: {
				
				switch (theState)
				{
					case STOPPED: {
						setGameStateStop();
						break;
					}
					
				}
				
				break;
			}
			case WAITING_TURN: {
				
				switch (theState)
				{
					case NOT_PLAYING: {
						setGameStateNotPlaying();
						
						//disconnetti partita e chiudi questo thread
						//setGameStateWaitingForPlayerConfirmation();
						break;
					}
					case PLAYING_TURN: {
						//riottengo info e posso fare delle azioni
						setGameStateTurnPlaying();
						break;
					}
					case WAITING_TURN: {
						setGameStateTurnWaiting();						
						//Riottengo info e aspetto ancora...
			
						break;
					}
					
					case SERVER_DISCONNECTED: {
						setServerDisconnected(true);
						setGameStateNotPlaying();
						break;
					}
				}
				
				break;
			}
			
			case PLAYING_TURN: {
				
				switch (theState)
				{
					case WAITING_TURN: {
						setGameStateTurnWaiting();
						break;
					}
					case NOT_PLAYING: {
						setGameStateNotPlaying();
						break;
					}
					case SERVER_DISCONNECTED: {
						setServerDisconnected(true);
						setGameStateNotPlaying();
						break;
					}
					default: {
						IllegalStateException e = new IllegalStateException("The game status can go only from TURN_PLAYING to WAITING_TURN or NOT_PLAYING");
						getLogger().warning(StackTraceUtils.getThrowMessage(e));
						throw e;
					}
				}
				
				break;
			}
		}
	}
	
	private void setGameStateTurnWaiting() {

		gameState = GameState.WAITING_TURN;
		setReadyToPlayTurn(false);
		runChangeStateOperations();
		
		if (map.getDinosaurIDSource() != null) {
			
			Dinosaur theDinosaur = DinosaurManager.getInstance().getDinosaur(map.getDinosaurIDSource());
			//If a dinosaur died in the change turn...
			if (theDinosaur == null) {
				map.setCellSourceSelected(null);
				setInGameInformationDinosaurDeath();
				
			}
		}
		
		
	}
	

	private void setGameStateTurnPlaying() {

		gameState = GameState.PLAYING_TURN;
		turnTimer.setNewInterval(ServerInterface.TURN_EXECUTION_DELAY - ServerInterface.TURN_FINALIZATION_DELAY);
		
		getLogger().info("KeepAlive resetted");
		keepAliveTimer.reset();
		runChangeStateOperations();
		
	}
	
	private void setGameStateNotPlaying() {

		if (isServerDisconnected() == false) {

			if (isPlayerIsDied() == false) {

				try {
					Client.getServerHandler().leaveGame();
				} 
				catch (IOException e2) {
					handleFailException(e2);
					return;
				} 
				catch (PlayerNotInGameException e2) {
					checkIfPlayerIsStillInGame();
					return;
				} 
				catch (InvalidTokenException e2) {
					handleFailException(e2);
					return;
				} 
				catch (TimeoutException e2) {
					handleFailException(e2);
					return;
				}
			}
			getLogger().info("Now get the highscore from the server");

			Highscore highscore = null;
			try {
				highscore = Client.getServerHandler().getHighscore();
				getLogger().info("Searching for user: "+Client.getUsername() + " and species: " + DinosaurManager.getInstance().getPlayerSpeciesName());
				for (Score aScore : highscore.getHighscore()) {

					if (aScore.getUsername().equals(Client.getUsername())
							&& aScore.getSpecies().equals(DinosaurManager.getInstance().getPlayerSpeciesName())) {

						score = aScore.getScore();
						getLogger().info("Highscore entry found. Setting the score to: "+score);
						break;
					}

				}
			} catch (IOException e2) {
				handleFailException(e2);
				return;
			}  
			catch (PlayerNotInGameException e2) {
				checkIfPlayerIsStillInGame();
				return;
			} 
			catch (InvalidTokenException e2) {
				handleFailException(e2);
				return;
			} 
			catch (TimeoutException e2) {
				handleFailException(e2);
				return;
			}

		}

		gameState = GameState.NOT_PLAYING;
	}

	/** 
	 * The method setNotPlayingGameState bla bla bla...
	 * Bla bla bla... as the indicated {@link NameClass1} bla bla bla...
	 * It is useful also insert some {@code arg1} references to the args
	 * while explaining.
	 *          
	 *
	 * @see		NameClass1 (remove the line if there is no need of it)
	 * @see		NameClass2 (remove the line if there is no need of it)
	 *  
	 * @since	Jun 10, 2011@12:46:41 AM
	 * 
	 */
	private void setGameStateStop() {		
		gameState = GameState.STOPPED;
		
		if (isServerDisconnected())
			exitWithError = true;
		else
			exitWithError = false;
		
		stop();
	}
    
    private void checkInputPlaying(long elapsedTime) {

    	if (isUserWantsToLeaveGame() == false) {

    		if (getCurrentPlayerPlaying().equals(Client.getUsername()) && GameManager.getInstance().getTurnTimerInterval() > 0) {


    			if (skipTurn.isPressed()) {
    				skipTurn.reset();

    				try {
    					Client.getServerHandler().passTurn();
    					turnTimerTimeout();
    				} catch (IOException e2) {
    					handleFailException(e2);
    					return;
    				} catch (InvalidTokenException e2) {
    					handleFailException(e2);
    					return;
    				} 
    				catch (TurnNotOwnedByPlayerException e2) {
    					handleTurnNotOwnedByPlayerException(e2);
    					return;
    				} 
    				catch (NegativeResponseException e) {
    					handleTurnNotOwnedByPlayerException(new TurnNotOwnedByPlayerException("Created adhoc by NegativeResponseException"));
    					return;
    				} 
    				catch (PlayerNotInGameException e2) {
    					checkIfPlayerIsStillInGame();
    					return;
    				} 
    				catch (TimeoutException e2) {
    					handleFailException(e2);
    					return;
    				}

    				refreshAllActions();

				   getLogger().info("KeepAlive restarted");
				   turnTimer.reset();
				   keepAliveTimerTimeout();
				   setInGameInformationPlayersInGame();
				   
    			}

    		}



    		if (getGameState() != GameState.STOPPED && getGameState() != GameState.NOT_PLAYING && isReadyToPlayTurn()) {        

    			if (mouseButtonClick.isPressed()) {

    				if (map.getDinosaurIDSource() != null) {

    					if (map.getRenderer().mouseIsWithinEggButton() && isCanPerformLayEgg()) {

    						setMovingActionInProgress(false);
    						performActionLayEgg(map.getDinosaurIDSource());
    					}

    					if (map.getRenderer().mouseIsWithinMoveButton() && isCanPerformMove()) {
    						setMovingActionInProgress(true);
    					}

    					if (map.getRenderer().mouseIsWithinGrowButton() && isCanPerformGrow()) {
    						setMovingActionInProgress(false);
    						performActionGrow(map.getDinosaurIDSource());
    					}
    				}
    			}


    			if(isMovingActionInProgress()) {

    				if (abortMoveAction.isPressed()) {
    					abortMoveAction.reset();
    					setMovingActionInProgress(false);
    				}

    				if (map.getCellDestinationSelected() != null && inputManager.getMouseX() > GameRenderer.VIEW_LEFT_BORDER) {
    					performActionMove(map.getDinosaurIDSource());
    				}
    			}
    		}
    	}
    }

    private void checkInputWaiting(long elapsedTime) {

    	if (getCurrentPlayerPlaying() == null) {
    		return;
    	}

    	if (getCurrentPlayerPlaying().equals(Client.getUsername()) && GameManager.getInstance().getTurnTimerInterval() > 0) {

    		if (confirmTurn.isPressed()) {
    			confirmTurn.reset();

    			try {
    				Client.getServerHandler().confirmTurn();
    			} 
    			catch (IOException e2) {
    				handleFailException(e2);
    				return;
    			} catch (InvalidTokenException e2) {
    				handleFailException(e2);
    				return;
    			} 
    			catch (TurnNotOwnedByPlayerException e2) {
    				handleTurnNotOwnedByPlayerException(e2);
    				return;
    			} 
    			catch (NegativeResponseException e) {
    				handleTurnNotOwnedByPlayerException(new TurnNotOwnedByPlayerException("Created adhoc by NegativeResponseException"));
    				return;
    			} 
    			catch (PlayerNotInGameException e2) {
    				checkIfPlayerIsStillInGame();
    				return;
    			} 
    			catch (TimeoutException e2) {
    				handleFailException(e2);
    				return;
    			}


    			changeGameState(GameState.PLAYING_TURN);   

    			refreshAllActions();

    		} else if (passTurn.isPressed()) {
    			passTurn.reset();

    			try {
    				Client.getServerHandler().passTurn();
    				turnTimerTimeout();
    			}
				 catch (IOException e2) {
    				handleFailException(e2);
    				return;
    			} catch (InvalidTokenException e2) {
    				handleFailException(e2);
    				return;
    			} 
    			catch (TurnNotOwnedByPlayerException e2) {
    				handleTurnNotOwnedByPlayerException(e2);
    				return;
    			} 
    			catch (NegativeResponseException e) {
    				handleTurnNotOwnedByPlayerException(new TurnNotOwnedByPlayerException("Created adhoc by NegativeResponseException"));
    				return;
    			} 
    			catch (PlayerNotInGameException e2) {
    				checkIfPlayerIsStillInGame();
    				return;
    			} 
    			catch (TimeoutException e2) {
    				handleFailException(e2);
    				return;
    			}
    			
				   getLogger().info("KeepAlive restarted");
				   turnTimer.reset();
				   keepAliveTimerTimeout();
				   setInGameInformationPlayersInGame();
    		}

    	}

    }
	
	private void setAllActionsOff() {
		setCanPerformGrow(false);
		setCanPerformLayEgg(false);
		setCanPerformMove(false);
	}
	
	private void setAllActionsOn() {
		setCanPerformGrow(true);
		setCanPerformLayEgg(true);
		setCanPerformMove(true);
	}
	
	
	
	private void refreshAllActions() {
		if (map == null) {
			return;
		}
		if (map.getDinosaurIDSource() == null) {
			setMovingActionInProgress(false);
			setAllActionsOff();
		} else if (DinosaurManager.getInstance().wasDinosaurPresentAtTurnBegin(map.getDinosaurIDSource())) {
			if (getGameState() == GameState.PLAYING_TURN && isReadyToPlayTurn()) {
				
				Dinosaur theDino = DinosaurManager.getInstance().getDinosaur(map.getDinosaurIDSource());
				
				if (theDino.getTurnsAlive() > 0)
					setAllActionsOn();
				else
					setAllActionsOff();
			} else {
				setAllActionsOff();
				setMovingActionInProgress(false);
			}
		} else {
			setMovingActionInProgress(false);
			setAllActionsOff();
		}
		
		
	}
	
	private void performActionLayEgg(String theDinosaurID) {
		setAllActionsOff();
		
		boolean updateIngameInformation = true;
		
		try {
			Client.getServerHandler().layEgg(theDinosaurID);
			setInGameInformationActionSuccesfully("Lay an egg");
		} 
		catch (IOException e2) {
			handleFailException(e2);
			return;
		} 
		catch (InvalidDinosaurIDException e2) {
			handleInvalidDinosaurIDException(e2);
			return;
		} 
		catch (SpeciesIsFullException e2) {
			setInGameInformationActionSpeciesIsFull();
			updateIngameInformation = false;
		} 
		catch (PlayerNotInGameException e2) {
			updateIngameInformation = false;
			checkIfPlayerIsStillInGame();
			return;
		} 
		catch (TurnNotOwnedByPlayerException e2) {
			handleTurnNotOwnedByPlayerException(e2);
			return;
		} 
		catch (MaxMovesLimitException e2) {
			setInGameInformationActionMaxMovesReached("Lay an egg");
			updateIngameInformation = false;
		} 
		catch (InvalidTokenException e2) {
			handleFailException(e2);
			return;
		}  
		catch (StarvationDeathException e2) {
			setInGameInformationActionLeadToDeath("Lay an egg");
			map.setCellSourceSelected(null);
		} 
		catch (TimeoutException e2) {
			handleFailException(e2);
			return;
		}

		if (checkIfPlayerIsStillInGame() == false)
			return;

		if (updateIngameInformation) {
			getChangeTurnInformation();
		}
		setAllActionsOn();

		getLogger().info("Lay egg action performed successfully");

	}

	private void performActionMove(String theDinosaurID) {
		
		boolean movingSuccessfully = false;
		boolean updateIngameInformation = true;

			try {
				Client.getServerHandler().moveDinosaur(theDinosaurID, map.getCellDestinationSelected());
				movingSuccessfully = true;
				setInGameInformationActionSuccesfully("Move");
			}
			catch (IOException e1) {
				handleFailException(e1);
				return;
			} 
			catch (InvalidDinosaurIDException e1) {
				handleInvalidDinosaurIDException(e1);
				return;
			} 
			catch (IdentifierNotPresentException e1) {
				getLogger().warning(StackTraceUtils.getCatchMessage(e1));
				handleInvalidDinosaurIDException(new InvalidDinosaurIDException("Created adhoc by IdentifierNotPresentException") );
				return;
			}
			catch (StarvationDeathException e1) {
				setInGameInformationActionLeadToDeath("Move");
				map.setCellSourceSelected(null);
			} 
			catch (InvalidDestinationException e1) {
				setInGameInformationActionMoveUnreachableDestination();
				updateIngameInformation = false;
			} 
			catch (PlayerNotInGameException e1) {
				updateIngameInformation = false;
				checkIfPlayerIsStillInGame();
				return;
			} 
			catch (TurnNotOwnedByPlayerException e1) {
				handleTurnNotOwnedByPlayerException(e1);
				return;
			} 
			catch (MaxMovesLimitException e1) {
				setInGameInformationActionMaxMovesReached("Move");
				updateIngameInformation = false;
			} 
			catch (FightLostException e1) {
				setInGameInformationActionMoveFightLost();
				map.setCellSourceSelected(null);
				updateIngameInformation = true;
			} 
			catch (FightWonException e1) {
				setInGameInformationActionMoveFightWon();
				movingSuccessfully = true;
				updateIngameInformation = true;
			} 
			catch (TimeoutException e2) {
				handleFailException(e2);
				return;
			}
			
			if (movingSuccessfully) {
				map.updateCellDinosaurID(null, map.getCellSourceSelected());
				map.setCellSourceSelected(map.getCellDestinationSelected());
			}
			
		if (checkIfPlayerIsStillInGame() == false)
			return;
			
		if (updateIngameInformation)  {
			getChangeTurnInformation();
			setMovingActionInProgress(false);
		}
		
	setAllActionsOn();
	map.setCellDestinationSelected(null);
	
	
	getLogger().info("Move action performed successfully");
		
	}
	
	private void handleTurnNotOwnedByPlayerException(TurnNotOwnedByPlayerException e) {
		
		getLogger().severe(StackTraceUtils.getThrowMessage(e));
		changeGameState(GameState.WAITING_TURN);
		
	}
	
	private void handleInvalidDinosaurIDException(InvalidDinosaurIDException e) {
		getLogger().severe(StackTraceUtils.getThrowMessage(e));
		changeGameState(GameState.WAITING_TURN);
	}
	
	private void handleFailException(TimeoutException e) {
		getLogger().severe(StackTraceUtils.getThrowMessage(e));
		handleGenericFailException();
	}
	
	private void handleFailException(IOException e) {
		getLogger().severe(StackTraceUtils.getThrowMessage(e));
		handleGenericFailException();
	}
	
	private void handleFailException(InvalidTokenException e) {
		getLogger().severe(StackTraceUtils.getThrowMessage(e));
		handleGenericFailException();
	}
	
	private void handleGenericFailException() {
		handleServerDisconnection();
	}
    
	private void performActionGrow(String theDinosaurID) {
		
		setAllActionsOff();
		
		boolean updateIngameInformation = true;
		
			try {
				Client.getServerHandler().growDinosaur(theDinosaurID);
				setInGameInformationActionSuccesfully("Grow");
			} 
			catch (IOException e2) {
				handleFailException(e2);
				return;
			} 
			catch (InvalidDinosaurIDException e2) {
				handleInvalidDinosaurIDException(e2);
				return;
			} 
			catch (MaxSizeReachedException e2) {
				setInGameInformationActionMaxSizeReached();
				updateIngameInformation = false;
			} 
			catch (PlayerNotInGameException e2) {
				updateIngameInformation = false;
				checkIfPlayerIsStillInGame();
				return;
			} 
			catch (TurnNotOwnedByPlayerException e2) {
				handleTurnNotOwnedByPlayerException(e2);
				return;
			} 
			catch (MaxMovesLimitException e2) {
				setInGameInformationActionMaxMovesReached("Grow");
				updateIngameInformation = false;
			} 
			catch (InvalidTokenException e2) {
				handleFailException(e2);
				return;
			} 
			catch (StarvationDeathException e2) {
				setInGameInformationActionLeadToDeath("Grow");
				map.setCellSourceSelected(null);
			} 
			catch (TimeoutException e2) {
				handleFailException(e2);
				return;
			}
			
			if (checkIfPlayerIsStillInGame() == false)
				return;
	
			if (updateIngameInformation) {
				getChangeTurnInformation();
			}
			setAllActionsOn();

			getLogger().info("Grow action performed successfully");
	}


	private void runChangeStateOperations() {
		getChangeTurnInformation();
		refreshAllActions();
	}

	public void updatePlayersList(PlayersList thePlayersList) {
		
		if (thePlayersList == null) {
			return;
		}
		
		ingamePlayersList = thePlayersList;
		
	}
    
	public PlayersList getPlayersList() {
		
		return ingamePlayersList;
		
	}
	
    boolean checkIfPlayerIsStillInGame() {
    	
    	PlayersList pl;
    	try {
    		pl = Client.getServerHandler().getInGamePlayersList();
		}  
    	catch (IOException e2) {
			handleFailException(e2);
			return true;
		}  
		catch (InvalidTokenException e2) {
			handleFailException(e2);
			return true;
		}  
		catch (NegativeResponseException e) {
			handleTurnNotOwnedByPlayerException(new TurnNotOwnedByPlayerException("Created adhoc by NegativeResponseException"));
			return true;
		} 
		catch (TimeoutException e2) {
			handleFailException(e2);
			return true;
		}
    	
    	boolean playerIsStillInGame = false;
    	
    	updatePlayersList(pl);
    	
    	for (String otherPlayer : ingamePlayersList.getStrings()) {
    		if (otherPlayer.equals(Client.getUsername())) {
    			playerIsStillInGame = true;
    			break;
    		}
    	}
    	
    	if (playerIsStillInGame == false) {
    		getLogger().info("The player is dead");
    		setPlayerIsDied(true);
    		changeGameState(GameState.NOT_PLAYING);
    		return false;
    	}
    	
    	return true;
    }
    
    void getChangeTurnInformation() {
    	
    	try {

    		if (firstMainMapLoading == false) {

    			MainMap mainMap;

    			mainMap = Client.getServerHandler().getMainMap();
    			getMap().updateMainMap(mainMap);

    			firstMainMapLoading = true;
    		}
    		
    		getMap().resetCarrionTiles();

    		DinosaursList  dinosaursList;
    		dinosaursList = Client.getServerHandler().getDinosaursInGameList();

    		DinosaurManager.getInstance().updatePlayerDinosaurIDListAtTurnBegin(dinosaursList);

    		ArrayList<LocalView>  localViewLists = new ArrayList<LocalView>();

    		for (String idDino : DinosaurManager.getInstance().getPlayerDinosaurIDListAtTurnBegin()) {
    			localViewLists.add(Client.getServerHandler().getLocalView(idDino));
    		}

    		getMap().updateLocalViews(localViewLists);

    		for (Dinosaur aDinosaur : DinosaurManager.getInstance().getDinosaurList()) {
    			aDinosaur.characterize(Client.getServerHandler().getDinosaurStatus(aDinosaur.getDinosaurID()));
    		}    	

    		setReadyToPlayTurn(true);


    	} 	
    	catch (IOException e2) {
			handleFailException(e2);
			return;
		}  
		catch (InvalidTokenException e2) {
			handleFailException(e2);
			return;
		}  
		catch (NegativeResponseException e) {
			handleTurnNotOwnedByPlayerException(new TurnNotOwnedByPlayerException("Created adhoc by NegativeResponseException"));
			return;
		} 
		catch (TimeoutException e2) {
			handleFailException(e2);
			return;
		}
		catch (PlayerNotInGameException e2) {
			checkIfPlayerIsStillInGame();
			return;
		} 
    	
    	
    
    }
    
    
    public void drawInGame(Graphics2D g) {
    	
    	final int textLeftBorder = GameRenderer.VIEW_LEFT_BORDER+10;
    	final int textBottomBorder = screen.getHeight() - 10;
    	String toBeDisplayed;
    	FontMetrics fm = g.getFontMetrics();
    	int stringWidth;
    	
    	map.getRenderer().draw(g);
    	
    	if (isUserWantsToLeaveGame() == true) {
    		g.drawImage(ResourceManager.getInstance().getExitConfirmation(), screen.getWidth()/2 - 272 , screen.getHeight()/2 - 48, null);
    	}    	 
    	
    	Font inGameFont = new Font("Verdana", Font.BOLD, 15);
    	g.setFont(inGameFont);

    	if ( GameManager.getInstance().getTurnTimerInterval() > 0 || !(getCurrentPlayerPlaying().equals(Client.getUsername()))) {
    	
	    	if (isMovingActionInProgress())
	    		g.drawImage(ResourceManager.getInstance().getBottomPanel(), GameRenderer.VIEW_LEFT_BORDER + 5, screen.getHeight() - 72 + 16, null);
	    	else
	    		g.drawImage(ResourceManager.getInstance().getBottomPanel(), GameRenderer.VIEW_LEFT_BORDER + 5, screen.getHeight() - 54 + 16 , null);

    		switch (getGameState())
    		{
    		case WAITING_TURN: {

    			g.setColor(Color.YELLOW);

    			//if (getCurrentPlayerPlaying() != null) {

    			if (!getCurrentPlayerPlaying().equals(NONE_PLAYER)) {

    				if (getCurrentPlayerPlaying().equals(Client.getUsername())) {
    					toBeDisplayed = Client.getUsername() + " it's your turn. Press Y to use it, or press N to skip it.";
    				} else {
    					toBeDisplayed = "This turn is owned by " + getCurrentPlayerPlaying()+ ". Please wait your turn.";
    				}

    			} else {
    				toBeDisplayed = "Waiting for an unknown player to accept his/her turn.";
    			}

    	//		} else {
    		//		toBeDisplayed = "Waiting for an unknown player to accept his/her turn.";;
    			//}

    			fm = g.getFontMetrics();
    			stringWidth = fm.stringWidth(toBeDisplayed);
    			g.drawString(toBeDisplayed, textLeftBorder + (screen.getWidth()-textLeftBorder)/2-stringWidth/2, textBottomBorder);

    			break;
    		}

    		case PLAYING_TURN: {

    			fm = g.getFontMetrics();
    			g.setColor(Color.YELLOW);
    			toBeDisplayed = "Press S when you finish your actions to pass the turn";
    			stringWidth = fm.stringWidth(toBeDisplayed);
    			g.drawString(toBeDisplayed, textLeftBorder + (screen.getWidth()-textLeftBorder)/2-stringWidth/2, textBottomBorder);

    			if (isMovingActionInProgress()) {

    				g.setColor(Color.RED);

    				toBeDisplayed = "Select dinosaur destination cell. Press X to abort the move action";

    				stringWidth = fm.stringWidth(toBeDisplayed);
    				g.drawString(toBeDisplayed, textLeftBorder + (screen.getWidth()-textLeftBorder)/2-stringWidth/2, textBottomBorder - 20);

    			}

    			break;
    		}

    		}

    	}

    	inGameFont = new Font("Verdana", Font.BOLD, 24);
    	g.setFont(inGameFont);

    	int timerSecs = GameManager.getInstance().getTurnTimerInterval();
    	
    	g.setColor(Color.GREEN);
    	
    	if (timerSecs > 5 && timerSecs <= 30) {
    		g.setColor(Color.YELLOW);
    	}
    	
    	if (timerSecs <= 5) {
    		g.setColor(Color.RED);
    	}
    	
    	if (isReadyToPlayTurn() && GameManager.getInstance().getTurnTimerInterval() > 0) {
    		g.drawImage(ResourceManager.getInstance().getTurnTimer(), screen.getWidth()-80, 16, null);
    		
    		if (timerSecs >= 100) 	
    			g.drawString(""+GameManager.getInstance().getTurnTimerInterval(), screen.getWidth()-74, 57);
    		else if (timerSecs >= 10)
    			g.drawString(""+GameManager.getInstance().getTurnTimerInterval(), screen.getWidth()-64, 57);
    		else
    			g.drawString(""+GameManager.getInstance().getTurnTimerInterval(), screen.getWidth()-55, 57);
    		
    	} else {
    		g.drawImage(ResourceManager.getInstance().getWaitingForTurnChange(), 550/2 + GameRenderer.VIEW_LEFT_BORDER - 175, 20, null);    	}
    	
    }
    
    private void drawGameOver(Graphics2D g) {
    	
    	Font totalScoreFont = new Font("Arial Black", Font.BOLD, 50);
    	g.setFont(totalScoreFont.deriveFont(50));
		    	
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
    	
    	if (isServerDisconnected() == false) {
    	
	    
    		if (isPlayerIsDied()) {
    			g.drawImage(ResourceManager.getInstance().getGameOverBackground(),0,0,null);
    	    	g.setColor(Color.YELLOW);
    	    	g.drawString(""+score, screen.getWidth()/2 - 30 , screen.getHeight()/2 + 40);
    		} else {
    			g.drawImage(ResourceManager.getInstance().getLeaveGameBackground(),0,0,null);
    	    	g.setColor(Color.YELLOW);
    	    	g.drawString(""+score, screen.getWidth()/2 - 30 , screen.getHeight()/2 + 40);	
    		}
    	    	
    	} else {
    		
    		g.drawImage(ResourceManager.getInstance().getLostConnectionBackground(),0,0,null);

    	}
    }

    public void draw(Graphics2D g) {
    	
    	g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

    	if (getGameState() != GameState.NOT_PLAYING && getGameState() != GameState.STOPPED) {
    		drawInGame(g);
    		drawMousePointer(g);
    	} else {
    		drawGameOver(g);	
    	}    	
    }
    
    private void drawMousePointer(Graphics2D g) {
    	g.drawImage(ResourceManager.getInstance().getMousePointer(), inputManager.getMouseX(), inputManager.getMouseY(), null);
    }


    /**
        Gets the current map.
    */
    public TileMap getMap() {
        return map;
    }


    private void resetAllKeyActions() {
    	
    	 exit.reset();
    	 confirmTurn.reset();
    	 passTurn.reset();
    	 confirmExit.reset();
    	 denyExit.reset();
    	 skipTurn.reset();
    	 abortMoveAction.reset();
    	 returnToWaitingRoom.reset();
    	 
    }

   public void handleChangeTurn(String thePlayer) {
	   
	   if (thePlayer == null) {
		   NullPointerException e = new NullPointerException("The current player cannot be null");
		   getLogger().severe(StackTraceUtils.getThrowMessage(e));
		   throw e;
	   }
	   
	   System.out.println("Entering the handle change turn function");
	   getLogger().info("Entering the handle change turn function");
	   
	   synchronized (gameIsLoadedLock) {

		   if (gameIsLoaded == false) {

			   try {
				   getLogger().info("Waiting for the game room to be loaded");
				   gameIsLoadedLock.wait();
			   } catch (InterruptedException e) {

				   e.printStackTrace();
			   }
			   getLogger().info("Notified by the init function!");
			   getLogger().info("Waiting for the game room to be loaded ENDED.");
		   }
	   }

	   getLogger().info("Now setting the change turn");
	   
	   setCurrentPlayerPlaying(thePlayer);
	   getLogger().info("KeepAlive resetted");
   
	   keepAliveTimer.reset();
	   
	   if (getCurrentPlayerPlaying().equals(Client.getUsername())) {
		   turnTimer.setNewInterval(ServerInterface.TURN_CONFIRMATION_DELAY - ServerInterface.TURN_FINALIZATION_DELAY);
	   } else {
		   getLogger().info("KeepAlive restarted");
		   turnTimer.reset();
		   keepAliveTimerTimeout();
		   setInGameInformationPlayersInGame();
		   
	   }

    }
    
    /**
	 * @return the currentPlayerPlaying
	 */
	public String getCurrentPlayerPlaying() {
		return currentPlayerPlaying;
	}

	/**
	 * @param currentPlayerPlaying the currentPlayerPlaying to set
	 */
	public void setCurrentPlayerPlaying(String theUsername) {
		currentPlayerPlaying = theUsername;
	}

	/**
        Updates Animation, position, and velocity of all Sprites
        in the current map.
    */
    public void update(long elapsedTime) {

    	
    	if (Client.getServerHandler().isChangingTurn() == true) {
    		
    		handleChangeTurn(Client.getServerHandler().getPlayerThatIsOwningTheTurn());
    		
    		getLogger().info("Changing the turn");
    		
    		Client.getServerHandler().setChangingTurn(false);
    		changeGameState(GameState.WAITING_TURN);
    		
    		getLogger().info("Changing turn done.");
    	}
    	
        	checkInput(elapsedTime);
    	
    	switch (getGameState()) {
		
			case NOT_PLAYING: {
				
				checkInputNotPlaying(elapsedTime);
				
				break;
			}
			
			case PLAYING_TURN: {
				
		    	if (Client.getServerHandler().isServerDown() == true) {
		    		getLogger().severe("The server is disconnected. Connection lost");
		    		handleServerDisconnection();
		    		return;	
		    	}
				
				checkInputPlaying(elapsedTime);
				
				break;
			}
			
			case WAITING_TURN: {
				
		    	if (Client.getServerHandler().isServerDown() == true) {
		    		getLogger().severe("The server is disconnected. Connection lost");
		    		handleServerDisconnection();
		    		return;	
		    	}
				
				checkInputWaiting(elapsedTime);
				
				break;
			}
    	}

    	resetAllKeyActions();
        // get keyboard/mouse input

    }
    
    void turnTimerTimeout() {
    	
    	turnTimer.reset();
    	setReadyToPlayTurn(false);
    	refreshAllActions();
    	
    }
    
    void keepAliveTimerTimeout() {
    	
    	getLogger().info("TRY TO KEEP ALIVE PLAYER");
    	
    	if (checkIfPlayerIsStillInGame() == false)
    		return;
    	
    	setInGameInformationPlayersInGame();

    	getLogger().info("KeepAlive restarted");
    	keepAliveTimer.restart();
    	
    }
    
	/**
	 * @return the readyToPlayTurn
	 */
	public boolean isReadyToPlayTurn() {
		return readyToPlayTurn;
	}

	/**
	 * @param readyToPlayTurn the readyToPlayTurn to set
	 */
	public void setReadyToPlayTurn(boolean readyToPlayTurn) {
		this.readyToPlayTurn = readyToPlayTurn;
	}

	
	/**
	 * @return the playerIsDied
	 */
	public boolean isPlayerIsDied() {
		return playerIsDied;
	}

	/**
	 * @param playerIsDied the playerIsDied to set
	 */
	public void setPlayerIsDied(boolean playerIsDied) {
		this.playerIsDied = playerIsDied;
	}

    
    /**
	 * @return the userWantsToLeaveGame
	 */
	public boolean isUserWantsToLeaveGame() {
		return userWantsToLeaveGame;
	}

	/**
	 * @param userWantsToLeaveGame the userWantsToLeaveGame to set
	 */
	protected void setUserWantsToLeaveGame(boolean userWantsToLeaveGame) {
		this.userWantsToLeaveGame = userWantsToLeaveGame;
	}
    
	/**
	 * @return the canPerformLayEgg
	 */
	public boolean isCanPerformLayEgg() {
		return canPerformLayEgg;
	}

	/**
	 * @param canPerformLayEgg the canPerformLayEgg to set
	 */
	public void setCanPerformLayEgg(boolean canPerformLayEgg) {
		this.canPerformLayEgg = canPerformLayEgg;
	}

	/**
	 * @return the canPerformGrow
	 */
	public boolean isCanPerformGrow() {
		return canPerformGrow;
	}

	/**
	 * @param canPerformGrow the canPerformGrow to set
	 */
	public void setCanPerformGrow(boolean canPerformGrow) {
		this.canPerformGrow = canPerformGrow;
	}

	/**
	 * @return the canPerformMove
	 */
	public boolean isCanPerformMove() {
		return canPerformMove;
	}

	/**
	 * @param canPerformMove the canPerformMove to set
	 */
	public void setCanPerformMove(boolean canPerformMove) {
		this.canPerformMove = canPerformMove;
	}	
	
    /**
	 * @return the movingActionInProgress
	 */
	protected boolean isMovingActionInProgress() {
		return movingActionInProgress;
	}

	/**
	 * @param movingActionInProgress the movingActionInProgress to set
	 */
	protected void setMovingActionInProgress(boolean movingActionInProgress) {
		this.movingActionInProgress = movingActionInProgress;
	}
	
	public void setInGameInformationActionMoveUnreachableDestination() {
    	
    	map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_MOVE_UNSUCCESSFULLY));
    	map.getRenderer().setInGameInformationTitle("Move action failed");
    	map.getRenderer().setInGameInformationText("The destination you want to place your dinosaur is unreachable.");
    }
	
	public void setInGameInformationActionMoveFightWon() {
		
    	map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_PERFORMED_SUCCESSFULLY));
    	map.getRenderer().setInGameInformationTitle("Move action successful");
    	map.getRenderer().setInGameInformationText("The destination you wanted to place your dinosaur was already occupied, but you won the fight with the other dinosaur.");
    }
	
	public void setInGameInformationActionMoveFightLost() {
    	
    	map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_LEAD_TO_DEATH));
    	map.getRenderer().setInGameInformationTitle("Move action lead to dinosaur's death");
    	map.getRenderer().setInGameInformationText("The destination cell was already occupied: you tried to fight the other dinosaur, but were too weak to win. Now your dinosaur rests in peace the dinosaurs' paradise.");
    }
	
	
	public void setInGameInformationActionLeadToDeath(String theActionString) {
    	
    	map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_LEAD_TO_DEATH));
    	map.getRenderer().setInGameInformationTitle(theActionString+" action failed");
    	map.getRenderer().setInGameInformationText("The action you requested your dinosaur to perform was too hard for it. Now it rests in peace the dinosaurs' paradise.");
    }
	
	public void setInGameInformationOldAgeDeath() {
    	
    	map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_LEAD_TO_DEATH));
    	map.getRenderer().setInGameInformationTitle("A dinosaur is dead");
    	map.getRenderer().setInGameInformationText("An old dinosaur reached the end of its life. Now it rests in peace the dinosaurs' paradise.");
    }
	
	public void setInGameInformationDinosaurDeath() {
		
    	map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_LEAD_TO_DEATH));
    	map.getRenderer().setInGameInformationTitle("A dinosaur is dead");
    	map.getRenderer().setInGameInformationText("A dinosaur of yours has died. Now it rests in peace in the dinosaurs' paradise.");
		
	}
	
	public void setInGameInformationPlayersInGame() {
		
		getLogger().info("Trying to update the players ingame list");
		
		if (getPlayersList() != null) {

			map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_PLAYERS_INFO));
			map.getRenderer().setInGameInformationTitle("Players List");

			String toBeDisplayed = "";

			for (String player : getPlayersList().getStrings() ) {
				toBeDisplayed += player+" ";
			}

			map.getRenderer().setInGameInformationText(toBeDisplayed);
			
			getLogger().info("Players ingame list updated");
		}
	}

    
    public void setInGameInformationActionSuccesfully(String theActionString) {
    	
    	if (theActionString.equals("Move"))
    		map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_MOVE_SUCCESSFULLY));
    	else if (theActionString.equals("Lay an egg"))
    		map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_LAY_EGG_SUCCESSFULLY));
    	else if (theActionString.equals("Grow"))
    		map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_GROW_SUCCESSFULLY));
        
    	map.getRenderer().setInGameInformationTitle(theActionString+" action successful");
    	map.getRenderer().setInGameInformationText("The action was performed successfully");
    }
    
    public void setInGameInformationActionMaxMovesReached(String theActionString) {

    	if (theActionString.equals("Move"))
    		map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_MOVE_UNSUCCESSFULLY));
    	else if (theActionString.equals("Lay an egg"))
    		map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_LAY_EGG_UNSUCCESSFULLY));
    	else if (theActionString.equals("Grow"))
    		map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_GROW_UNSUCCESSFULLY));
      	map.getRenderer().setInGameInformationTitle(theActionString+" action failed");
    	map.getRenderer().setInGameInformationText("The dinosaur can't perform the action because it has already done this action in the current turn.");
    }
    
    
    public void setInGameInformationActionMaxSizeReached() {
    	map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_GROW_UNSUCCESSFULLY));
      	map.getRenderer().setInGameInformationTitle("Grow action failed");
    	map.getRenderer().setInGameInformationText("The dinosaur can't grow anymore because it has reached its maximum size.");
    }
    
    public void setInGameInformationActionSpeciesIsFull() {
    	map.getRenderer().setInGameInformationIcon(ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_LAY_EGG_UNSUCCESSFULLY));
      	map.getRenderer().setInGameInformationTitle("Lay an egg action failed");
    	map.getRenderer().setInGameInformationText("The dinosaur can't lay an egg because its species has already reached its maximum size of 5 dinosaurs.");
    }


    
    
	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.gamemanager");
		logger.setParent(Logger.getLogger("client.main"));
		logger.setLevel(Level.ALL);
		
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#getLogger()
	 * 
	 */
	@Override
	public Logger getLogger() {
		return logger;
	}
    
    
    
    
}





class KeepAliveTimer extends AlarmTimer implements Loggable {
	
	Logger logger;
	
	static private int POLLING_INTERVAL = 10000;
	
	KeepAliveTimer() {
		super(POLLING_INTERVAL);
		setupLogger();
	}
		
	public void actionToBePerformed() {
		
		GameManager.getInstance().keepAliveTimerTimeout();
	}
	
	void restart() {
		reset();
		setNewInterval(POLLING_INTERVAL);
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.gamemanager.keepalivetimer");
		logger.setParent(Logger.getLogger("client.main"));
		logger.setLevel(Level.ALL);
		
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#getLogger()
	 * 
	 */
	@Override
	public Logger getLogger() {
		return logger;
		
	}

}







class TurnTimer extends AlarmTimer {
	
	TurnTimer(int theInterval) {
		super(theInterval);
	}
		
	public void actionToBePerformed() {
		GameManager.getInstance().turnTimerTimeout();
	}
	
}