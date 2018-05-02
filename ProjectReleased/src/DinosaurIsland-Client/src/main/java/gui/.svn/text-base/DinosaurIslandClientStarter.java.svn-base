/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gui;

import java.awt.EventQueue;
import java.util.Locale;

import javax.swing.UIManager;

import communication.Client;
/**
 * <b>Overview</b><br>
 * <p>
 * This class is the entry point of the client program.
 *
 * <b>Responsibilities</b><br>
 * <p>
 * Let the client start as well as the GUI.
 * </p>
 *
 * <b>Collaborators</b><br>
 * <p>
 * The class {@link Client} and the class {@link ClientMainWindow}, that are started by this class.
 * </p>
 * 
 * @author	RAA
 * @version 1.6
 *
 */
public class DinosaurIslandClientStarter {

	/** 
	 * The method main is the entry point of the server applicaton.
	 * 
	 * @param args are the arguments passed to the application          
	 *
	 * 
	 */
	public static void main(String args[]) {
		
		System.out.println("Starting Dinosaur Island Client");
		Client.getInstance();
		Locale.setDefault(Locale.ENGLISH);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
					    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					} catch (Throwable e) {
						System.out.println("Unable to set the nimbus look and feel");
					}
					ClientMainWindow.getInstance();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
