/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication.requests;

import java.io.IOException;

import util.Position;

import commands.responses.MoveDinosaurResponse;
import communication.SocketClientHandler;

import exceptions.FightLostException;
import exceptions.FightWonException;
import exceptions.IdentifierNotPresentException;
import exceptions.InvalidDestinationException;
import exceptions.InvalidTokenException;
import exceptions.MaxMovesLimitException;
import exceptions.PlayerNotInGameException;
import exceptions.StarvationDeathException;
import exceptions.TurnNotOwnedByPlayerException;

public class MoveDinosaurRequest extends Request {

	
		protected String token;
		protected String dinosaurID;
		protected Position destination;

		MoveDinosaurRequest(String theToken, String theDinosaurID, Position theDestination, SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
			super(theSocketClientHandler);
			
			token = theToken;		
			dinosaurID = theDinosaurID;
			destination = theDestination;
			
		}

		/* {@inheritDoc}
		 * @see communication.Request#execute()
		 * 
		 */
		@Override
		public void execute() throws IOException {
			
			boolean invalidTokenFlag = false;
			boolean invalidDinosaurIDFlag = false;
			boolean invalidDestinationFlag = false;
			boolean maxMovesLimitFlag = false;
			boolean starvationDeathFlag = false;
			boolean turnNotOwnedByPlayerFlag = false;
			boolean playerNotInGameFlag = false;
			boolean fightEncounteredFlag = false;
			boolean fightWonFlag = false;
			boolean outcome = false;
			

			
			try {
			getSocketClientHandler().moveDinosaur(token, dinosaurID, destination);
			
			} catch (InvalidTokenException e) {
				invalidTokenFlag = true;
				getLogger().info("Sending the user a message informing him/her that the token given for the dinosaur movement request is not valid");
				getSocketClientHandler().write(new MoveDinosaurResponse(outcome,  invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
						starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag).generateVerbose());
				return;
			} catch (IdentifierNotPresentException e) {
				invalidDinosaurIDFlag = true;
				getLogger().info("Sending the user a message informing him/her that the identifier given for the dinosaur movement request is not valid");
				getSocketClientHandler().write(new MoveDinosaurResponse(outcome,  invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
						starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag).generateVerbose());
				return;
			} catch (InvalidDestinationException e) {
				invalidDestinationFlag = true;
				getLogger().info("Sending the user a message informing him/her that the destination given for the dinosaur movement request is not valid");
				getSocketClientHandler().write(new MoveDinosaurResponse(outcome,  invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
						starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag).generateVerbose());
				return;
			} catch (MaxMovesLimitException e) {
				maxMovesLimitFlag = true;
				getLogger().info("Sending the user a message informing him/her that the dinosaur given for the dinosaur movement request has reached its maximum number of available moves in this turn");
				getSocketClientHandler().write(new MoveDinosaurResponse(outcome,  invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
						starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag).generateVerbose());
				return;
			} catch (StarvationDeathException e) {
				starvationDeathFlag = true;
				getLogger().info("Sending the user a message informing him/her that the identifier given for the dinosaur movement request is not valid");
				getSocketClientHandler().write(new MoveDinosaurResponse(outcome,  invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
						starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag).generateVerbose());
				return;
			} catch (TurnNotOwnedByPlayerException e) {
				turnNotOwnedByPlayerFlag = true;
				getLogger().info("Sending the user a message informing him/her that the current turn belongs to another player");
				getSocketClientHandler().write(new MoveDinosaurResponse(outcome,  invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
						starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag).generateVerbose());
				return;
			} catch (PlayerNotInGameException e) {
				playerNotInGameFlag = true;
				getLogger().info("Sending the user a message informing him/her that he/she is not in game");
				getSocketClientHandler().write(new MoveDinosaurResponse(outcome,  invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
						starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag).generateVerbose());
				return;
			} catch (FightWonException e) {
				fightEncounteredFlag = true;
				fightWonFlag = true;
				outcome = true;
				getLogger().info("Sending the user a message informing him/her that the dinosaur given for the dinosaur movement has won a fight");
				getSocketClientHandler().write(new MoveDinosaurResponse(outcome,  invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
						starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag).generateVerbose());
				return;
			}catch (FightLostException e) {
				fightEncounteredFlag = true;
				outcome = true;
				getLogger().info("Sending the user a message informing him/her that the dinosaur given for the dinosaur movement has lost a fight");
				getSocketClientHandler().write(new MoveDinosaurResponse(outcome,  invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
						starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag).generateVerbose());
				return;
			}
			
			outcome = true;
			getLogger().info("The dinosaur moved successfully ");
			getSocketClientHandler().write(new MoveDinosaurResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, invalidDestinationFlag, maxMovesLimitFlag,
					starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, fightEncounteredFlag, fightWonFlag).generateVerbose());
			return;
		}
		
		public String toString() {
			return "@muoviDinosauro,token=" + token + ",idDino=" + dinosaurID + ",dest={" + destination.getIntX() + "," + destination.getIntY() + "}";
		}

}
