package communication.requests;

import java.io.IOException;

import beans.NewBornID;

import commands.responses.LayEggResponse;
import communication.SocketClientHandler;

import exceptions.InvalidDinosaurIDException;
import exceptions.InvalidTokenException;
import exceptions.MaxMovesLimitException;
import exceptions.PlayerNotInGameException;
import exceptions.SpeciesIsFullException;
import exceptions.StarvationDeathException;
import exceptions.TurnNotOwnedByPlayerException;

public class LayEggRequest extends Request {
	
	protected String token;
	protected String dinosaurID;

	LayEggRequest(String theToken, String theDinosaurID , SocketClientHandler theSocketClientHandler) throws IllegalArgumentException {
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
		boolean starvationDeathFlag = false;
		boolean maxMovesLimitFlag = false;
		boolean turnNotOwnedByPlayerFlag = false;
		boolean speciesFullFlag = false;
		boolean playerNotInGameFlag = false;
		NewBornID newBornID = null;
		
		try {
			newBornID = getSocketClientHandler().layEgg(token,dinosaurID);
			outcome = true;
			getLogger().info("Sending the user that his/her dinosaur successfully laid an egg");
			getSocketClientHandler().write(new LayEggResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, speciesFullFlag, newBornID).generateVerbose());
			return;
		} catch (InvalidTokenException e) {
			invalidTokenFlag = true;
			getLogger().info("Sending the user a message informing him/her that the token given to lay an egg is not valid");
			getSocketClientHandler().write(new LayEggResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, speciesFullFlag, newBornID).generateVerbose());
			return;
		} catch (InvalidDinosaurIDException e) {
			invalidDinosaurIDFlag = true;
			getLogger().info("Sending the user a message informing him/her that the identifier given to lay an egg is not valid");
			getSocketClientHandler().write(new LayEggResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, speciesFullFlag, newBornID).generateVerbose());
			return;
		} catch (MaxMovesLimitException e) {
			maxMovesLimitFlag = true;
			getLogger().info("Sending the user a message informing him/her that the dinosaur already reached its maximum number of available moves in this turn");
			getSocketClientHandler().write(new LayEggResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, speciesFullFlag, newBornID).generateVerbose());
			return;
		} catch (StarvationDeathException e) {
			starvationDeathFlag = true;
			getLogger().info("Sending the user a message informing him/her that the dinosaur hadn't enough energy to lay an egg");
			getSocketClientHandler().write(new LayEggResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, speciesFullFlag, newBornID).generateVerbose());
			return;
		} catch (TurnNotOwnedByPlayerException e) {
			turnNotOwnedByPlayerFlag = true;
			getLogger().info("Sending the user a message informing him/her that the current turn doesn't belong to him/her");
			getSocketClientHandler().write(new LayEggResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, speciesFullFlag, newBornID).generateVerbose());
			return;
		} catch (PlayerNotInGameException e) {
			playerNotInGameFlag = true;
			getLogger().info("Sending the user a message informing him/her that he/she is not in game");
			getSocketClientHandler().write(new LayEggResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, speciesFullFlag, newBornID).generateVerbose());
			return;
		} catch (SpeciesIsFullException e) {
			speciesFullFlag = true;
			getLogger().info("Sending the user a message informing him/her that his/her species has already reached its maximum size");
			getSocketClientHandler().write(new LayEggResponse(outcome, invalidTokenFlag, invalidDinosaurIDFlag, maxMovesLimitFlag, starvationDeathFlag, turnNotOwnedByPlayerFlag, playerNotInGameFlag, speciesFullFlag, newBornID).generateVerbose());
			return;	
		}
	}
	
	public String toString() {
		return "@deponiUovo,token=" + token + ",idDino=" + dinosaurID;
	}
	
}
