package gui;

import java.awt.EventQueue;
import java.util.Locale;
import java.util.logging.Logger;
import javax.swing.UIManager;

import communication.Server;

/**
 * <b>Overview</b><br>
 * <p>
 * This class is the entry point of the server program.
 *
 * <b>Responsibilities</b><br>
 * <p>
 * Let the server start as well as the GUI.
 * </p>
 *
 * <b>Collaborators</b><br>
 * <p>
 * The class {@link Server} and the class {@link ServerMainWindow}, that are started by this class.
 * </p>
 * 
 * @author	RAA
 * @version 1.6
 *
 */
public class DinosaurIslandServerStarter {

	/** 
	 * The method main is the entry point of the server applicaton.
	 * 
	 * @param args are the arguments passed to the application          
	 *
	 * 
	 */
	public static void main(String[] args) {

        Server.getInstance();
		Locale.setDefault(Locale.ENGLISH);
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
					    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					} catch (Throwable e) {
						Logger.getLogger("server.main").config("Unable to set the nimbus look and feel");
					}
					ServerMainWindow.getInstance();
					
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}