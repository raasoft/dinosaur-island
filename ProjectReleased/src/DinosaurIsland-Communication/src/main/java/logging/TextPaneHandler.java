/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package logging;

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
 * @since	May 27, 2011@11:55:18 PM
 *
 */

import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.html.HTMLEditorKit;

public class TextPaneHandler extends Handler {
	
	private JTextPane jTextPane = null;
	
	public void setTextPane(JTextPane theJTextPane) {
		jTextPane = theJTextPane;
	}
	
	private Level level = Level.ALL; // The logging level for this handler, which is configurable.
	
	/*
	 * Must include filtering mechanism as it is not included in the (lame) Abstract Handler class.
	 */
	public TextPaneHandler() {
		
		Filter filter = new Filter() {
			public boolean isLoggable(LogRecord record) {
				return record.getLevel().intValue() >= level.intValue();
			}};
		this.setFilter(filter);
	}
	
	@Override
	public void publish(LogRecord logRecord) {
		// Must filter our own logRecords, (lame) Abstract Handler does not do it for us.
		if (!getFilter().isLoggable(logRecord)) return;
		
		final String message = new HTMLFormatter().format(logRecord);
		
		// Append formatted message to textareas using the Swing Thread.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				StringBuffer sb9 = new StringBuffer();
				String pointtext = message;

				sb9.append("<html><body>");
				sb9.append(pointtext);	
				sb9.append("</body></html>");	


				try{
				//Document doc = (Document) jTextPane.getDocument();

				((HTMLEditorKit)jTextPane.getEditorKit()).read(
				new java.io.StringReader (sb9.toString())
				,jTextPane.getDocument()
				,jTextPane.getDocument().getLength());
				} 
				catch (Throwable bl)
				{
				System.out.println("-- " + bl.getMessage());
				} 			
			}
			
		});
	}
	@Override
	public void close() throws SecurityException {}
	@Override
	public void flush() {}

	/**
	 * Must capture level to use in our custom filter, because this is not done in the
	 * abstract class.
	 */
	@Override
	public void setLevel(Level level) {
		this.level = level;
		super.setLevel(level);
	}
}

