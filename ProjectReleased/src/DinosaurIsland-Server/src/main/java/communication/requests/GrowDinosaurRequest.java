/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;

import commands.responses.GrowDinosaurResponse;
import communication.SocketClientHandler;

import exceptions.InvalidDinosaurIDException;
import exceptions.InvalidTokenException;
import exceptions.MaxMovesLimitException;
import exceptions.MaxSizeReachedException;
import exceptions.PlayerNotInGameException;
import exceptions.StarvationDeathException;
import exceptions.TurnNotOwnedByPlayerException;

public class GrowDinosaurRequest extends Request {

	protected String token;
	protected String dinosaurID;

	GrowDinosaurRequest(String theToken, String theDinosaurID , SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
		super(theSocketClientHandler);
		
		token = theToken;
		dinosaurID = theDinosaurID;
	}

	/* {@inheritDoc}
	 * @see communication.Request#execute()
	 * 
	 */
	@Override
	public
	void execute() throws IOException {
		
		boolean outcome = false;
		boolean invalidTokenFlag = false;
		boolean invalidDinosaurIDFlag = false;
		boolean maxMovesLimitFlag = false;
		boolean starvationDeathFlag = false;
		boolean turnNotOwnedByPlayerFlag = false;
		boolean playerNotInGameFlag = false;
		boolean maxSizeReachedFlag = false;
		
		try {
			getSocketClientHandler().growDinosaur(token,dinosaurID);
			outcome = true;
			getLogger().info("Sending the user that his/her dinosaur successfully grew up");
			getSocketClientHandler().write(new GrowDinosaurResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, maxSizeReachedFlag).generateVerbose());
			return;
		} catch (InvalidTokenException e) {
			invalidTokenFlag = true;
			getLogger().info("Sending the user a message informing him/her that the token given to grow the dinosaur is not valid");
			getSocketClientHandler().write(new GrowDinosaurResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, maxSizeReachedFlag).generateVerbose());
			return;
		} catch (InvalidDinosaurIDException e) {
			invalidDinosaurIDFlag = true;
			getLogger().info("Sending the user a message informing him/her that the identifier given to grow the dinosaur is not valid");
			getSocketClientHandler().write(new GrowDinosaurResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, maxSizeReachedFlag).generateVerbose());
			return;
		} catch (MaxMovesLimitException e) {
			maxMovesLimitFlag = true;
			getLogger().info("Sending the user a message informing him/her that the dinosaur already reached its maximum number of available moves in this turn");
			getSocketClientHandler().write(new GrowDinosaurResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, maxSizeReachedFlag).generateVerbose());
			return;
		} catch (StarvationDeathException e) {
			starvationDeathFlag = true;
			getLogger().info("Sending the user a message informing him/her that the dinosaur hadn't enough energy to grow up");
			getSocketClientHandler().write(new GrowDinosaurResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, maxSizeReachedFlag).generateVerbose());
			return;
		} catch (TurnNotOwnedByPlayerException e) {
			turnNotOwnedByPlayerFlag = true;
			getLogger().info("Sending the user a message informing him/her that the current turn doesn't belong to him/her");
			getSocketClientHandler().write(new GrowDinosaurResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, maxSizeReachedFlag).generateVerbose());
			return;
		} catch (PlayerNotInGameException e) {
			playerNotInGameFlag = true;
			getLogger().info("Sending the user a message informing him/her that he/she is not in game");
			getSocketClientHandler().write(new GrowDinosaurResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, maxSizeReachedFlag).generateVerbose());
			return;
		} catch (MaxSizeReachedException e) {
			maxSizeReachedFlag = true;
			getLogger().info("Sending the user a message informing him/her that the dinosaur already reached its maximum size");
			getSocketClientHandler().write(new GrowDinosaurResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, maxSizeReachedFlag).generateVerbose());
			return;	
		}
	}
	
	public String toString() {
		return "@cresciDinosauro,token=" + token + ",idDino=" + dinosaurID;
	}

}
