/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

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
 * @since	May 31, 2011@11:23:34 AM
 *
 */
public class StackTraceUtils {

	  public static String getStackTrace(Throwable aThrowable) {
		    final Writer result = new StringWriter();
		    final PrintWriter printWriter = new PrintWriter(result);
		    aThrowable.printStackTrace(printWriter);
		    return result.toString();
		  }
	  
		public static String getThrowMessage(Throwable e) {
			return "THROW: "+getStackTrace(e);
		}
		
		public static String getCatchMessage(Throwable e) {
			return "CATCH: "+getStackTrace(e);
		}

		  /*
		  public static String getCustomStackTrace(Throwable aThrowable) {
		    //add the class name and any message passed to constructor
		    final StringBuilder result = new StringBuilder( "BOO-BOO: " );
		    result.append(aThrowable.toString());
		    final String NEW_LINE = System.getProperty("line.separator");
		    result.append(NEW_LINE);

		    //add each element of the stack trace
		    for (StackTraceElement element : aThrowable.getStackTrace() ){
		      result.append( element );
		      result.append( NEW_LINE );
		    }
		    return result.toString();
		  }*/
	
}
