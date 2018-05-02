/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;

import commands.CommandHelper;

public class Score implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean isEstinct;
	private int score;
	private String species;
    private String username;

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }
    
    public String getSpecies() {
        return species;
    }
    
    public boolean isEstinct() {
        return isEstinct;
    }
    
    public void setSpeciesIsEstinct() {
    	isEstinct = true;
    }

    public Score(String theUsername, String theSpecies, int theScore) {
        score = theScore;
        username = theUsername;
        species = theSpecies;
        isEstinct = false; // when a new score is added to the highscore the species is never dead, it will be finalized later
    }
    
    public Score(String theHighscoreRow) throws IllegalArgumentException {
    	
    	if (theHighscoreRow == null || theHighscoreRow.isEmpty())
		{
			throw new IllegalArgumentException("The string to be parsed can't be null or empty");
		}
    	
    	if (theHighscoreRow.indexOf("{") != 0 || theHighscoreRow.indexOf("}") != theHighscoreRow.length()-1 ) {
    		throw new IllegalArgumentException("The row does not contain { and } in the correct position");
    	}
    	
    	String[] array = theHighscoreRow.substring(1, theHighscoreRow.length()-1).split(CommandHelper.getNewCommaSeparator());
		ArrayList<String> regexp = new ArrayList<String>();
		regexp.add("\\w+");
		regexp.add("\\w+");
		regexp.add("[0-9]+");
		regexp.add("(s|n)");
		ArrayList<String> args = new ArrayList<String>();
		
		for (String str : array) {
			args.add(str);
		}

		CommandHelper.validate(args, regexp);

		score = (int) Float.parseFloat(args.get(2));
		username = args.get(0);
		species = args.get(1);
		if (args.get(3).equals("s"))
			isEstinct = true;
		else
			isEstinct = false;

    	
    	
    }
    
    public void setScore(int theScore) {
    	score = theScore;
    }
    
    public String toString() {
    	if (isEstinct)
    		return "{"+username+ "," + species + "," + score + ",s}";
		else
			return "{"+username+ "," + species + "," + score + ",n}";
    			
    }
}