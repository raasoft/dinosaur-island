/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import util.StackTraceUtils;
import util.Vector2D;

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
 * @since	Jun 1, 2011@5:16:10 PM
 *
 */

public final class CommandHelper {
	
	static final Logger logger = Logger.getLogger("common.commandhelper");
	
	private final static CommandHelper uniqueInstance = new CommandHelper();
	private static final String newCommaSeparator = new String("/");
	
	CommandHelper() {
		logger.setLevel(Level.ALL);
	}
	
	public static String getNewCommaSeparator() {
		return newCommaSeparator;
	}
	
	public static String getCommandTag(ArrayList<String> theTokens) {
		return theTokens.get(0);
	}
	
	public static String getVector2DRegexp(String theCommaSeparator) { 
		return "\\{\\d+(\\.\\d+)*"+theCommaSeparator+"\\d+(\\.\\d+)*\\}";
	}
	
	public static Vector2D parseVector(String theStringToBeParsed, String theCommaSeparator) throws IllegalArgumentException {
		
		if (theStringToBeParsed == null || theStringToBeParsed.isEmpty())
		{
			throw new IllegalArgumentException("The string to be parsed can't be null or empty");
		}
		
		ArrayList<String> arguments = new ArrayList<String>();		  
		arguments.add(theStringToBeParsed);
		
		ArrayList<String> regexpPatternArguments = new ArrayList<String>();
		regexpPatternArguments.add(getVector2DRegexp(theCommaSeparator));

		CommandHelper.validate(arguments, regexpPatternArguments);
		
		String temporaryString = theStringToBeParsed.substring(1, theStringToBeParsed.length()-1);
		int commaPosition = temporaryString.indexOf(theCommaSeparator);
		
		float value1 = Float.parseFloat(temporaryString.substring(0, commaPosition));
		float value2 = Float.parseFloat(temporaryString.substring(commaPosition + 1, temporaryString.length()));
		
		return new Vector2D(value1, value2);
			
	}
	
	public static ArrayList<String> getCommandArguments(ArrayList<String> theTokens) {
		if (theTokens.size() > 1) {
			ArrayList<String> theArgs = new ArrayList<String>(theTokens.subList(1, theTokens.size()));
			return theArgs;
		}
		else
			return new ArrayList<String>();
	}
	
	public static CommandHelper getInstance() {
		return uniqueInstance;
	}
	
	public static ArrayList<String> tokenizeArguments(String theStringToBeParsed) throws IllegalArgumentException {
		
		if (theStringToBeParsed == null) {
			throw new IllegalArgumentException("Invalid string to tokenize: it can't be null");
		}
		
		int commaPosition = theStringToBeParsed.indexOf(',');
		
		String commandTag = "";
		String argumentsString = "";
		ArrayList<String> tokens = new ArrayList<String>();
		String temporaryString = "";
		
		/* The smaller Response may be only one letter long, so "@c,arg=1,..."
		 * so the commaPosition must be at least in the third position of the string,
		 * then in position 2.
		 */
		if (commaPosition > 1)
		{
			commandTag = theStringToBeParsed.substring(0, commaPosition);
			/* if there are curly braces, inside them there will be commas that can 
			 * interfere with the later split in token, so it is better to turn them
			 * into a new "comma separator" that is obtained by calling getNewCommaSperator().
			 */
			int openedCurlyBracePosition;
			int closedCurlyBracePosition;
			int openedSquaredBracePosition;
			int closedSquaredBracePosition;
			int index = 0;
			
			for  (	openedCurlyBracePosition = theStringToBeParsed.substring(index).indexOf('{'); 
					openedCurlyBracePosition != -1  && theStringToBeParsed.substring(index).indexOf(',') != -1; 
					openedCurlyBracePosition = theStringToBeParsed.substring(index).indexOf('{')) {
			
				closedCurlyBracePosition = theStringToBeParsed.substring(index).indexOf('}');
					
				if (closedCurlyBracePosition == -1) {
					throw new IllegalArgumentException("Invalid string to tokenize: the number of the { doesn't match the number of the }");
				}
				
				if (closedCurlyBracePosition < openedCurlyBracePosition) {
					throw new IllegalArgumentException("The curly braces are not well formatted");
				}
				
				temporaryString = theStringToBeParsed.substring(index).substring(openedCurlyBracePosition, closedCurlyBracePosition+1);
				temporaryString = temporaryString.replace(",", getNewCommaSeparator());
				theStringToBeParsed = theStringToBeParsed.substring(0, index)  +
				theStringToBeParsed.substring(index, index+openedCurlyBracePosition) + 
				temporaryString + 
				theStringToBeParsed.substring(index).substring(closedCurlyBracePosition+1, theStringToBeParsed.substring(index).length());				
				index += closedCurlyBracePosition+1;
				//System.out.println("theStringToBeParsed: " +theStringToBeParsed);
				if (index > theStringToBeParsed.length())
					break;
			}
			index++;
		/*	System.out.println("theStringToBeParse2d: " +theStringToBeParsed.substring(index));
			System.out.println("opened square braces initial: " +theStringToBeParsed.substring(index).indexOf('['));
			System.out.println("comma  initial: " +theStringToBeParsed.substring(index).indexOf(','));
			*/
			
			if (index <= theStringToBeParsed.length()) {
				
				for  (	openedSquaredBracePosition = theStringToBeParsed.substring(index).indexOf('['); 
				openedSquaredBracePosition != -1 && theStringToBeParsed.substring(index).indexOf(',') != -1; 
				openedSquaredBracePosition = theStringToBeParsed.substring(index).indexOf('[')) {
		
					closedSquaredBracePosition = theStringToBeParsed.substring(index).indexOf(']');
					
					if (closedSquaredBracePosition == -1) {
						throw new IllegalArgumentException("Invalid string to tokenize: the number of the { doesn't match the number of the }");
					}
					
					if (closedSquaredBracePosition < openedSquaredBracePosition) {
						throw new IllegalArgumentException("The squared braces are not well formatted");
					}
					
					//System.out.println("index: " +index);
					//System.out.println("openedSquaredBracePosition: " +openedSquaredBracePosition);
					//System.out.println("closedSquaredBracePosition: " +closedSquaredBracePosition);
					
					temporaryString = theStringToBeParsed.substring(index).substring(openedSquaredBracePosition, closedSquaredBracePosition+1);
					
					//System.out.println("temporaryString before replace: " +temporaryString);
					
					temporaryString = temporaryString.replace(",", getNewCommaSeparator());
					
					//System.out.println("temporaryString after replace: " +temporaryString);
					
					theStringToBeParsed = theStringToBeParsed.substring(0, index)  +
					theStringToBeParsed.substring(index, index+openedSquaredBracePosition) + 
					temporaryString + 
					theStringToBeParsed.substring(index).substring(closedSquaredBracePosition+1, theStringToBeParsed.substring(index).length());				
					
					
					index += closedSquaredBracePosition+1;
					//
					if (index > theStringToBeParsed.length())
						break;
				}
			}
	
			argumentsString = theStringToBeParsed.substring(commaPosition+1);
		}
		else {
			commandTag = theStringToBeParsed;
		}
		tokens.add(commandTag);
		Collections.addAll(tokens, argumentsString.split(","));
	
		return tokens;
	}

	public static void validate(ArrayList<String> theArguments, ArrayList<String> theArgumentsRegexp) throws IllegalArgumentException	{
		
		if (theArgumentsRegexp == null) {
			IllegalArgumentException e = new IllegalArgumentException("The regexp argument list cannot be null");
			throw e;
		}
		
		if (theArgumentsRegexp.size() == 0)
			return;
		
		int expectedMandatoryArgumentsNumber = 0;

		/* If a regexp ends with a "?" the argument is not mandatory */
		for (String str : theArgumentsRegexp) {
			if ( str.endsWith("?") == false )
				expectedMandatoryArgumentsNumber++;	
		}
		
		if (theArguments.size() < expectedMandatoryArgumentsNumber) {
			IllegalArgumentException ex = new IllegalArgumentException("This command have " + theArguments.size() + " arguments instead of " + expectedMandatoryArgumentsNumber + " mandatory arguments");
			logger.warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		if (theArguments.size() > theArgumentsRegexp.size()) {
			IllegalArgumentException ex = new IllegalArgumentException("This command have " + theArguments.size() + " arguments instead of " + theArgumentsRegexp.size() + " arguments");
			logger.warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		
		Scanner scanner;
		MatchResult result;
		
		for (int i = 0; i < theArguments.size(); i++) {
			scanner =  new Scanner(theArguments.get(i));
			if (theArguments == null || theArguments.get(i).isEmpty() && theArgumentsRegexp.get(i).endsWith("?") == false) {				
				IllegalArgumentException ex = new IllegalArgumentException("Argument no. "+(i+1)+" cannot be null or empty");
				logger.warning(StackTraceUtils.getThrowMessage(ex));
				throw ex;
				
			}
			scanner.findInLine(theArgumentsRegexp.get(i));
			try {
				result = scanner.match();
				//System.out.println("i start-" + i +"-end");
				//System.out.println("result.group(0) start-" +  result.group(0)+"-end");
				//System.out.println("theArguments.get(i) start-" +  theArguments.get(i) +"-end");
				if (result.group(0).equals(theArguments.get(i)) == false)
					throw new IllegalStateException();
			}
			catch (IllegalStateException e)	{
				if (theArguments.get(i).length() > 0 ) {
					IllegalArgumentException ex = new IllegalArgumentException("Argument no. "+(i+1)+" doesn't match the regular expression assigned to it. "+theArguments.get(i) + " vs " +theArgumentsRegexp.get(i));
					logger.warning(StackTraceUtils.getThrowMessage(ex));
					throw ex;
				}
			}
			
			scanner.close();
		}
	}
	
	public static String getPingStringCommand() {
		return "#PING#";
	}
	
	/* this functions checks whether EACH string in theArguments is an alphanumeric string */
	public static void checkStringsAlphanumeric(ArrayList<String> theArguments) throws IllegalArgumentException {
		
		ArrayList<String> regexpPatternArguments = new ArrayList<String>();
		for (int i = 0; i < regexpPatternArguments.size(); i++ ) {
			regexpPatternArguments.add("\\w+");
		}

		validate(theArguments, regexpPatternArguments);
	}
}
