/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package communication;


import org.junit.Before;
import org.junit.Test;

import commands.responses.ChangeTurnResponseFactory;
import commands.responses.CreateSpeciesResponseFactory;
import commands.responses.CreateUserResponseFactory;
import commands.responses.HighscoreResponseFactory;
import commands.responses.MainMapResponseFactory;
import commands.responses.MoveDinosaurResponseFactory;
import commands.responses.PlayersListResponseFactory;
import commands.responses.Response;


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
 * @since	May 24, 2011@10:42:12 AM
 *
 */

public class ResponseGenerationTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void createUserResponseTest()
	{
		Response response;
		response = CreateUserResponseFactory.getInstance().createResponse("@ok");
		System.out.println("Response : "+response);
		
		response = CreateUserResponseFactory.getInstance().createResponse("@no");
		System.out.println("Response : "+response);
		
		response = CreateUserResponseFactory.getInstance().createResponse("@no,@usernameOccupato");
		System.out.println("Response : "+response);
		
		response = CreateSpeciesResponseFactory.getInstance().createResponse("@ok");
		System.out.println("Response : "+response);
		
		response = CreateSpeciesResponseFactory.getInstance().createResponse("@no,@nomeRazzaOccupato");
		System.out.println("Response : "+response);
			
		response = CreateSpeciesResponseFactory.getInstance().createResponse("@no,@razzaGiaCreata");
		System.out.println("Response : "+response);
		
		try {
		response = CreateSpeciesResponseFactory.getInstance().createResponse("@no,@ciao");
		System.out.println("Response : "+response);
		} catch (IllegalArgumentException e) {
			System.out.println("Response non valido");
		}
		
		response = PlayersListResponseFactory.getInstance().createResponse("@listaGiocatori,raa,axxo,raaxxo,ciaociao,cmestai,tu,nonce");
		System.out.println("Response : "+response);
		
		response = CreateSpeciesResponseFactory.getInstance().createResponse("@no,@tokenNonValido");
		System.out.println("Response : "+response);		
		
		response = ChangeTurnResponseFactory.getInstance().createResponse("@cambioTurno,RAAXXO");
		System.out.println("Response : "+response);
		
		response = MainMapResponseFactory.getInstance().createResponse("@mappaGenerale,{3,3},[a][t][c];[v][a][c];[a][c][v];");
		System.out.println("Response : "+response);
		
		response = MainMapResponseFactory.getInstance().createResponse("@mappaGenerale,{39,39},[a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a][a];[a][v][t][t][v][t][v][t][v][v][v][t][v][t][t][t][t][v][t][t][t][t][t][v][t][v][t][t][v][t][t][v][t][t][t][t][v][v][t];[a][v][t][v][v][t][v][v][t][t][t][t][t][v][t][t][v][t][t][t][v][v][v][v][t][v][v][v][t][t][v][v][t][t][v][v][v][v][t];[a][t][v][v][v][v][v][t][v][t][t][v][t][t][t][v][t][v][t][v][v][t][t][t][a][a][a][a][t][v][t][v][t][t][a][a][a][v][v];[a][v][t][v][t][t][a][a][a][a][a][t][v][v][a][a][a][a][v][t][t][v][t][v][a][a][a][a][v][v][v][v][t][v][a][a][a][v][t];[a][t][v][t][t][v][v][v][v][v][t][t][v][t][a][a][a][a][v][t][v][v][t][t][a][a][a][a][v][v][t][t][v][v][a][a][a][v][v];[a][t][t][t][v][v][t][v][t][v][t][t][v][t][a][a][a][a][t][v][v][v][v][t][v][t][v][t][v][t][v][t][t][v][a][a][a][v][t];[a][t][v][t][t][t][v][t][t][v][v][v][t][t][v][v][t][t][t][v][v][v][t][v][v][v][t][t][v][t][t][v][t][t][t][v][v][t][t];[a][t][v][t][v][t][t][v][v][t][t][t][t][t][t][v][t][v][v][v][t][t][t][v][t][t][t][v][v][v][t][v][v][v][v][v][t][t][v];[a][t][v][t][t][t][t][v][t][v][t][v][v][v][v][t][t][t][t][t][t][v][v][v][v][v][t][v][t][t][t][t][v][t][v][v][t][v][v];[a][t][v][v][t][v][v][v][v][t][t][v][v][v][v][t][v][t][t][t][t][t][v][t][v][t][t][t][v][v][t][v][v][t][t][t][t][v][t];[a][t][t][t][t][t][v][v][v][t][v][v][t][t][v][v][v][t][v][t][v][v][v][v][v][v][t][a][a][t][t][v][a][a][a][a][t][t][t];[a][t][t][t][a][a][a][a][a][t][v][v][v][v][t][t][a][a][a][a][v][v][t][t][v][v][v][a][a][v][v][v][a][a][a][a][t][v][v];[a][t][v][t][t][t][t][t][t][t][v][t][v][v][t][v][a][a][a][a][t][t][t][t][v][v][v][a][a][v][v][t][a][a][a][a][t][t][t];[a][t][v][v][t][v][v][t][v][t][v][t][v][t][t][t][a][a][a][a][t][v][v][v][v][v][v][a][a][v][v][t][t][v][v][t][t][v][t];[a][t][t][t][v][t][t][t][v][v][t][v][t][v][t][t][t][t][t][v][v][t][v][t][t][v][t][a][a][v][t][t][v][v][t][t][v][v][t];[a][t][v][t][v][t][t][t][v][v][t][v][t][t][v][v][t][v][t][t][v][v][v][t][v][v][t][a][a][v][t][t][v][t][v][t][v][t][v];[a][t][v][v][t][t][v][t][t][v][v][t][v][v][v][v][t][v][t][v][v][v][t][t][v][t][t][t][v][v][t][v][v][v][v][v][v][v][v];[a][t][v][v][v][v][v][t][t][t][v][t][v][t][v][t][t][v][v][v][t][t][v][v][v][v][t][t][t][t][v][t][v][t][t][v][t][v][t];[a][t][t][t][t][t][v][v][v][t][t][t][v][t][t][t][v][v][t][v][v][t][t][v][v][t][v][t][v][t][v][v][t][t][v][t][v][t][v];[a][t][t][t][v][t][t][v][v][v][v][v][t][t][v][v][v][t][v][v][v][v][t][t][v][t][v][v][v][t][v][t][t][v][a][a][t][t][v];[a][t][t][v][v][t][v][v][v][v][t][v][t][t][v][a][a][a][v][v][v][t][t][v][t][v][t][t][t][v][t][t][v][t][a][a][v][v][t];[a][t][t][t][v][a][a][a][a][a][v][t][t][v][t][a][a][a][t][t][v][v][t][t][t][v][v][a][a][t][t][t][t][t][a][a][t][v][t];[a][t][v][t][v][v][v][v][v][t][t][t][v][v][t][a][a][a][v][t][v][v][t][v][t][t][v][a][a][t][t][v][t][t][a][a][v][v][t];[a][t][t][t][v][t][v][t][v][t][t][t][v][v][t][a][a][a][v][t][t][v][t][v][v][v][v][a][a][t][v][t][v][v][a][a][t][v][v];[a][t][v][v][v][v][v][t][t][v][v][t][t][v][v][t][t][t][v][t][t][t][t][t][v][v][v][a][a][v][t][t][t][t][a][a][v][t][v];[a][t][t][v][v][t][v][t][t][v][v][t][v][v][v][v][v][v][t][t][t][v][t][v][v][v][v][a][a][v][t][t][t][t][v][t][t][v][t];[a][t][t][t][v][t][t][v][t][v][t][t][v][t][v][v][t][t][t][t][v][v][t][v][t][v][v][a][a][v][v][v][v][t][v][v][v][v][v];[a][t][v][v][v][t][v][t][v][t][t][v][t][v][v][t][v][v][v][v][v][v][v][t][v][v][v][v][v][v][v][v][t][v][t][t][t][t][v];[a][t][t][v][t][a][a][a][a][a][t][v][t][v][v][t][v][t][v][t][t][v][v][t][v][v][t][v][t][t][v][t][t][a][a][a][a][t][v];[a][t][v][t][t][t][v][v][v][t][t][v][v][v][t][v][v][v][v][v][v][v][v][t][v][v][a][a][a][v][t][t][t][a][a][a][a][v][t];[a][t][v][t][v][v][v][v][v][v][t][v][t][a][a][a][a][a][a][v][t][t][v][t][t][t][a][a][a][t][v][t][t][a][a][a][a][t][v];[a][t][t][t][t][t][v][v][t][v][v][t][t][a][a][a][a][a][a][v][t][t][v][v][t][v][a][a][a][v][t][t][v][v][v][v][t][v][t];[a][t][t][t][t][t][v][v][v][t][t][v][t][t][t][v][v][v][t][t][v][t][v][t][v][v][a][a][a][t][v][t][v][v][v][t][t][v][v];[a][t][t][t][v][t][v][t][t][v][v][t][t][t][t][t][v][v][t][t][v][v][t][v][v][t][t][t][v][v][t][v][v][t][t][v][v][v][v];[a][t][v][t][v][v][v][v][t][t][t][t][v][t][t][v][v][v][t][v][v][t][t][v][t][t][t][v][t][v][v][v][v][v][t][t][v][t][v];[a][t][t][v][t][v][t][t][v][v][t][t][v][t][v][t][t][v][v][v][v][v][v][t][t][t][v][t][t][t][t][v][t][t][v][t][v][t][v];[a][t][v][t][t][t][v][v][v][t][t][v][v][t][t][t][v][v][t][t][v][v][v][t][v][t][t][v][v][t][v][v][v][v][v][v][v][t][t];[a][t][v][t][v][t][t][t][t][t][v][v][t][t][t][v][t][v][v][v][v][v][v][t][v][t][t][v][t][t][v][t][v][t][t][t][v][v][v];");
		System.out.println("Response : "+response);
		
		response = HighscoreResponseFactory.getInstance().createResponse("@classifica,{user1,RAZZA1,5,s},{user2,RAZZA2,50,n},{user3,RAZZA3,500,s}");
		System.out.println("Response : "+response);
		
		response = MoveDinosaurResponseFactory.getInstance().createResponse("@ok,@combattimento,p");
		System.out.println("Response : "+response);
		
		response = MoveDinosaurResponseFactory.getInstance().createResponse("@ok,@combattimento,v");
		System.out.println("Response : "+response);
		
		response = MoveDinosaurResponseFactory.getInstance().createResponse("@ok");
		System.out.println("Response : "+response);
		
	}
}
