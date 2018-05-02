/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
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
 * @since	May 28, 2011@11:07:59 PM
 *
 */
package logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

//This custom formatter formats parts of a log record to a single line
public class HTMLFormatter extends Formatter
{
	// This method is called for every log records
	public String format(LogRecord rec)
	{
		StringBuffer buf = new StringBuffer(1000);

		// POWAH!
		buf.append("<tr>");
		buf.append("<td width=\"60px\">");
			buf.append(calcDate(rec.getMillis()));
		buf.append("</td>");
		buf.append("<td width=\"200px\">");
			if (rec.getSourceClassName() != null) {	
			    buf.append(rec.getSourceClassName());
			} else {
			    buf.append(rec.getLoggerName());
			}
		buf.append("<br>");	
			if (rec.getSourceMethodName() != null) {	
			    buf.append(" ");
			    buf.append("<i>");
			    buf.append(rec.getSourceMethodName());
			    buf.append("</i>");
			}
		buf.append("</td>");
		buf.append("<td width=\"50px\">");
		if (rec.getLevel().intValue() == Level.WARNING.intValue())
		{
			buf.append("<b><font color=\"#FF9900\">");
			buf.append(rec.getLevel());
			buf.append("</font></b>");
		} 
		else if (rec.getLevel().intValue() == Level.SEVERE.intValue())
		{
			buf.append("<b><font color=\"red\">");
			buf.append(rec.getLevel());
			buf.append("</font></b>");
		}
		else
		{
			buf.append(rec.getLevel());
		}
		buf.append("</td>");
		buf.append("<td>");
		buf.append(formatMessage(rec));
		buf.append('\n');
		buf.append("</td>");
		buf.append("</tr>\n");
		return buf.toString();
		
		
	}

	private String calcDate(long millisecs)
	{
		SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");
		Date resultdate = new Date(millisecs);
		Date resulttime = new Date(millisecs);
		String date = date_format.format(resultdate);
		String time = time_format.format(resulttime);
		return date + "<br>" + time;
	}

	// This method is called just after the handler using this
	// formatter is created
	public String getHead(Handler h)
	{
		return "<HTML>\n<HEAD>\n" + (new Date()) + "\n</HEAD>\n<BODY>\n<PRE>\n"
				+ "<table border>\n  "
				+ "<tr><th>Time</th><th>Log Message</th></tr>\n";
	}

	// This method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h)
	{
		return "</table>\n  </PRE></BODY>\n</HTML>\n";
	}
}
