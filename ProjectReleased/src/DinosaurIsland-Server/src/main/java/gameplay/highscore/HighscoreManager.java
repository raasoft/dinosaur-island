/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay.highscore;

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
 * @author	727700
 * @version 1.0
 * @since	14/giu/2011@15.09.24
 *
 */
import java.util.*;
import java.io.*;
import beans.Highscore;
import beans.Score;

public class HighscoreManager {
    // An arraylist of the type "score" we will use to work with the scores inside the class
    private Hashtable<String, Score> scores;

    //Initialising an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    static private HighscoreManager uniqueInstance = new HighscoreManager();
    
    public static HighscoreManager getInstance() {
    	return uniqueInstance;
    }
    
    private HighscoreManager() {
        //initialising the scores-arraylist
        scores = new Hashtable<String, Score>();
    }
    
    public Highscore getSortedHighscore() {
        return new Highscore(getSortedHighscoreList());
    }
    
    public Highscore saveData() {
    	return getSortedHighscore();
    }
    
    public void loadData(Highscore theHighscore) {
    	
    	scores.clear();
    	
    	for (Score score : theHighscore.getHighscore()) {
    		scores.put(getTag(score.getUsername(),score.getSpecies()), score);
    	}
    	
    }
    
    private ArrayList<Score> getSortedHighscoreList() {
        ScoreComparator comparator = new ScoreComparator();
        
        ArrayList<Score> ordinatedScoreList = new ArrayList<Score>(scores.values());
        Collections.sort(ordinatedScoreList, comparator);
        
        return ordinatedScoreList;
    }
    
    public void setScore(String theUsername, String theSpecies, int theScore) {
    	
    	if (theUsername == null) {
    		throw new NullPointerException();
    	}
    	
    	if (theSpecies == null) {
    		throw new NullPointerException();
    	}
    	
    	if (theScore < 0) {
    		throw new IllegalArgumentException("The score can't be negative");
    	}
    	
    	if (scores.containsKey(theScore) == false) {
    		Score newScore = new Score(theUsername, theSpecies, theScore);
    		scores.put(getTag(theUsername, theSpecies), newScore);
    	} else {
    		Score oldScore = scores.get(getTag(theUsername, theSpecies));
    		oldScore.setScore(theScore);
    	}
    	
    }
    
    private String getTag(String theUsername, String theSpecies) {
    	return theUsername+","+theSpecies;
    }
    

    public void setScoreAsFinal(String theUsername, String theSpecies) {
    	
    	if (theUsername == null) {
    		throw new NullPointerException();
    	}
    	
     	if (theSpecies == null) {
    		throw new NullPointerException();
    	}
     	
     	String theScore = getTag(theUsername, theSpecies);
    	
    	if (scores.containsKey(theScore) == false) {
    		throw new IllegalArgumentException("The given highscore entry does not exist yet.");
    	}
    	
    	scores.get(theScore).setSpeciesIsEstinct();
    	
    }
    
    
}