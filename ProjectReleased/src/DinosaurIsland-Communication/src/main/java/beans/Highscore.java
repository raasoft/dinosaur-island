/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Highscore implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<Score> highscore;
	
	public Highscore() {
		highscore = new ArrayList<Score>();
	}

	public Highscore(ArrayList<Score> theHighscoreList) {
		highscore = new ArrayList<Score>(theHighscoreList);
	}
	
	public ArrayList<Score> getHighscore() {
		return highscore;
	}
	
	public int size() {
		return highscore.size();
	}
	
	public String toString() {
		String str = "";
		
		for (Score score : highscore) {
			if (!str.equals(""))
				str+=",";
			str += score.toString(); 
		}
		
		return str;
	}
	
	
}
