/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import logging.Loggable;
import util.AlarmTimer;

import beans.Highscore;
import beans.PlayersList;
import beans.Score;

import communication.Client;

import exceptions.GameAccessDeniedException;
import exceptions.IllegalResponseException;
import exceptions.InvalidTokenException;
import exceptions.MaxInGamePlayersExceededException;
import exceptions.NegativeResponseException;
import exceptions.SpeciesNotCreatedException;
import gui.ingame.dinosaurislandgame.GameManager;

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
 * @author	AXXO
 * @version 1.0
 * @since	Jun 3, 2011@12:04:46 PM
 *
 */


public class WaitingRoomWindow {
	
	static private WaitingRoomWindow uniqueInstance = new WaitingRoomWindow();
	private JFrame frameWaitingRoom;
	private HighscoreAndPlayersListPolling pollingTask;
	private JTextPane textPanePlayers;
	private JTextPane textPaneScoreboard;
	JButton btnEnter;
	JButton btnDisconnect;

	static public WaitingRoomWindow getInstance() {
		return uniqueInstance;
	}
	
	public void resumeWaitingRoom() {
		btnEnter.setEnabled(true);
		btnDisconnect.setEnabled(true);
		getFrameWaitingRoom().setVisible(true);
        pollingTask.firstStart();
		
	}
	
	public JFrame getFrameWaitingRoom() {
		return frameWaitingRoom;
	}

	void handleServerDisconnection() {
		
		btnEnter.setEnabled(true);
		btnDisconnect.setEnabled(true);
		frameWaitingRoom.dispose();
		ClientMainWindow.getInstance().getFrmDinosaurislandClient().setVisible(true);
		
	}
	
	/**
	 * Create the application.
	 */
	public WaitingRoomWindow() {
		initialize();
	}
	
	void updatePlayersList(PlayersList thePlayerList) {
		
		String list = "";
		if (thePlayerList.getStrings().size() < 8) {
			list = "<tr color=green>" +
			"<td><b>Players:</td></b>" +
			"<td>" + thePlayerList.getStringsNumber()+"/8</td></tr>";
			
		} else {
			list = "<tr color=red>" +
			"<td><b>Players:</td></b>" +
			"<td>" + thePlayerList.getStringsNumber()+"/8</td></tr>";
		}
		for (String str : thePlayerList.getStrings()) {
			list = list + "<tr>" + str + "</tr>";
		}
		
		textPanePlayers.setText(list);
	}
	
	void updateHighscore(Highscore theHighscore) {
		
		String list = "<table align="+'"'+"center"+'"'+ ",style=\"width: 100%;\">" +
				"<tr style="+'"'+"border-width: 1px; border-style: solid"+'"'+">" +
				"<td width=15px><b>Pos</b></td>" +
				"<td><b>Username</b></td>" +
				"<td><b>Species</b></td>" +
				"<td width=50px><b>Score</b></td>" +
				"<td width=25px><b>Extinct</b></td>" +
				"</tr>";
		
		int pos = 1;
		for (Score row : theHighscore.getHighscore()) {
			
			if (row.isEstinct() == true) {
				list += "<tr style="+'"'+"border-width: 1px; border-style: solid; font: black"+'"'+">" +
				"<td>" + pos + "</td>" +
				"<td>" + row.getUsername() + "</td>" +
				"<td>" + row.getSpecies() + "</td>" +
				"<td>" + row.getScore() + "</td>" +
				"<td>yes</td></tr>";
				
			} else {
				list += "<tr color=\"red\", style="+'"'+"border-width: 1px; border-style: solid;"+'"'+">" +
				"<td>" + pos + "</td>" +
				"<td>" + row.getUsername() + "</td>" +
				"<td>" + row.getSpecies() + "</td>" +
				"<td>" + row.getScore() + "</td>" +
				"<td>no</td></tr>";
			}
			pos++;
		}
		
		list += "</table>";
		
		textPaneScoreboard.setText(list);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
        frameWaitingRoom = new JFrame();
		frameWaitingRoom.setVisible(false);
		frameWaitingRoom.setUndecorated(false);
		frameWaitingRoom.setResizable(false);
		frameWaitingRoom.setTitle(" Waiting Room for user "+Client.getUsername()+" - Dinosaur Island");
		frameWaitingRoom.setBounds(100, 100, 1024, 600);
		frameWaitingRoom.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frameWaitingRoom.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelTop = new JPanel();
		frameWaitingRoom.getContentPane().add(panelTop, BorderLayout.CENTER);
		panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.X_AXIS));
		
		JSplitPane splitPaneWaitingRoom = new JSplitPane();
		splitPaneWaitingRoom.setEnabled(false);
		splitPaneWaitingRoom.setBorder(null);
		panelTop.add(splitPaneWaitingRoom);
		
		JPanel panelScoreboard = new JPanel();
		splitPaneWaitingRoom.setLeftComponent(panelScoreboard);
		panelScoreboard.setBorder(new TitledBorder(null, "Scoreboard", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(59, 59, 59)));
		panelScoreboard.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPaneScoreboard = new JScrollPane();
		panelScoreboard.add(scrollPaneScoreboard);
		
		textPaneScoreboard = new JTextPane();
		textPaneScoreboard.setEditable(false);
		textPaneScoreboard.setContentType("text/html");
		scrollPaneScoreboard.setViewportView(textPaneScoreboard);
		
		JPanel panelPlayers = new JPanel();
		splitPaneWaitingRoom.setRightComponent(panelPlayers);
		panelPlayers.setBorder(new TitledBorder(null, "Players", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelPlayers.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPanePlayers = new JScrollPane();
		scrollPanePlayers.setAutoscrolls(true);
		panelPlayers.add(scrollPanePlayers);
		
		textPanePlayers = new JTextPane();
		textPanePlayers.setContentType("text/html");
		textPanePlayers.setAutoscrolls(true);
		scrollPanePlayers.setViewportView(textPanePlayers);
		textPanePlayers.setEditable(false);
		splitPaneWaitingRoom.setDividerLocation(800);
		
		JPanel panelButtons = new JPanel();
		frameWaitingRoom.getContentPane().add(panelButtons, BorderLayout.SOUTH);
		panelButtons.setLayout(new BorderLayout(0, 0));
		
		JPanel panelButtonsDisconnect = new JPanel();
		panelButtons.add(panelButtonsDisconnect, BorderLayout.EAST);
		panelButtonsDisconnect.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				btnEnter.setEnabled(false);
				btnDisconnect.setEnabled(false);
				
				try {
					
					pollingTask.reset();
					Client.getServerHandler().logout();
					JOptionPane.showMessageDialog(frameWaitingRoom, "User logged out");
				}
				catch (TimeoutException e) {
					Client.getServerHandler().disconnect();
					JOptionPane.showMessageDialog(frameWaitingRoom, "Can't disconnect from the server. Connection timed out.\nCheck the connection options or try again later.", "Connection Error", JOptionPane.ERROR_MESSAGE);
				} 
				catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(frameWaitingRoom, e.getMessage(), "Logout Error", JOptionPane.ERROR_MESSAGE);
				}
				catch (IllegalResponseException e) {
					JOptionPane.showMessageDialog(frameWaitingRoom, "The server can't log you out.", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
				catch (InvalidTokenException e) {
					JOptionPane.showMessageDialog(frameWaitingRoom, e.getMessage(), "Logout error", JOptionPane.ERROR_MESSAGE);
				} 
				catch (RemoteException e) {
					JOptionPane.showMessageDialog(frameWaitingRoom, "Can't reach the server.\nCheck the connection options or try again later.", "Remote Error", JOptionPane.ERROR_MESSAGE);
				}
				
				btnEnter.setEnabled(true);
				btnDisconnect.setEnabled(true);
				frameWaitingRoom.dispose();
				ClientMainWindow.getInstance().getFrmDinosaurislandClient().setVisible(true);
			}
		});
		btnDisconnect.setPreferredSize(new Dimension(200, 27));
		panelButtonsDisconnect.add(btnDisconnect);
		
		JPanel panelDisconnectButtonSpacer = new JPanel();
		panelButtonsDisconnect.add(panelDisconnectButtonSpacer);
		
		JPanel panelEnter = new JPanel();
		panelButtons.add(panelEnter, BorderLayout.WEST);
		panelEnter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panelEnterButtonSpacer = new JPanel();
		panelEnter.add(panelEnterButtonSpacer);
		
		btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
				try
				{
					pollingTask.reset();
					btnEnter.setEnabled(false);
					btnDisconnect.setEnabled(false);
					
					/* We must call the method from outside the event dispatch thread */
					 Thread queryThread = new Thread() {
					      public void run() {
					    	  
					    	  try {
					    		  
					    		  Client.getServerHandler().enterGame();
					    		  frameWaitingRoom.setVisible(false);
					    		  frameWaitingRoom.dispose();
					    		  
					    		  Thread queryThread = new Thread() {
					    			  public void run() {
					    				  GameManager.getInstance().run();	  
					    			  }
					    		  };
					    		  queryThread.start();
					    		  
					    		  
								} catch (TimeoutException e) {
									JOptionPane.showMessageDialog(frameWaitingRoom, "Can't connect to the server. Connection timed out.\nCheck the connection options or try again later.", "Connection Error", JOptionPane.ERROR_MESSAGE);
									btnEnter.setEnabled(true);
									btnDisconnect.setEnabled(true);
									frameWaitingRoom.dispose();
									ClientMainWindow.getInstance().getFrmDinosaurislandClient().setVisible(true);
								} catch (MaxInGamePlayersExceededException e) {
									JOptionPane.showMessageDialog(frameWaitingRoom, e.getMessage(), "Game Error", JOptionPane.ERROR_MESSAGE);
									btnEnter.setEnabled(true);
									btnDisconnect.setEnabled(true);
								} catch (SpeciesNotCreatedException e) {
																		
									/* Yes will give response = 0; No will give response = 1 */
									int response = JOptionPane.showConfirmDialog(frameWaitingRoom, "You haven't created a species yet.\nDo you want to create a new species right now?", "Question", JOptionPane.YES_NO_OPTION);
									
									if (response == 0) {
										
										CreationSpeciesDialog creationSpeciesWindow = new CreationSpeciesDialog();
										creationSpeciesWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
										creationSpeciesWindow.setVisible(true);
									}
									
									btnEnter.setEnabled(true);									
									pollingTask.restart();
									
								} catch (IllegalResponseException e) {
									JOptionPane.showMessageDialog(frameWaitingRoom, e.getMessage(), "Game Error", JOptionPane.ERROR_MESSAGE);
									btnEnter.setEnabled(true);
									btnDisconnect.setEnabled(true);
									frameWaitingRoom.dispose();
									ClientMainWindow.getInstance().getFrmDinosaurislandClient().setVisible(true);
								} catch (GameAccessDeniedException e) {
									JOptionPane.showMessageDialog(frameWaitingRoom, e.getMessage(), "Game Error", JOptionPane.ERROR_MESSAGE);
									btnEnter.setEnabled(true);
								} catch (IllegalArgumentException e) {
									btnEnter.setEnabled(true);
									btnDisconnect.setEnabled(true);
									frameWaitingRoom.dispose();
									ClientMainWindow.getInstance().getFrmDinosaurislandClient().setVisible(true);
								} catch (InvalidTokenException e) {
									JOptionPane.showMessageDialog(frameWaitingRoom, e.getMessage(), "Game Error", JOptionPane.ERROR_MESSAGE);
									btnEnter.setEnabled(true);
									btnDisconnect.setEnabled(true);
									frameWaitingRoom.dispose();
									ClientMainWindow.getInstance().getFrmDinosaurislandClient().setVisible(true);
								} catch (RemoteException e) {
									JOptionPane.showMessageDialog(frameWaitingRoom, "Can't reach the server.\nCheck the connection options or try again later.", "Remote Error", JOptionPane.ERROR_MESSAGE);
									btnEnter.setEnabled(true);
									btnDisconnect.setEnabled(true);
									frameWaitingRoom.dispose();
									ClientMainWindow.getInstance().getFrmDinosaurislandClient().setVisible(true);
								}			
					      }
					    };
					    queryThread.start();
				}
				catch (Exception theException) {
					btnEnter.setEnabled(true);
					theException.printStackTrace();
				}
			}
		});
	
		btnEnter.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEnter.setPreferredSize(new Dimension(200, 27));
		panelEnter.add(btnEnter);
		
		pollingTask = new HighscoreAndPlayersListPolling(this);
        pollingTask.start();
	}

}


class HighscoreAndPlayersListPolling extends AlarmTimer implements Loggable {
	
	Logger logger;
	WaitingRoomWindow window;
	
	static private int POLLING_INTERVAL = 10000;
	
	HighscoreAndPlayersListPolling(WaitingRoomWindow theWindow) {
		super(POLLING_INTERVAL);
		window = theWindow;
		setupLogger();
	}
		
	void firstStart() {
		setNewInterval(50);
	}
	
	public void actionToBePerformed() {
		
		if (Client.getServerHandler().isServerDown() == true) {
			
			window.handleServerDisconnection();
			JOptionPane.showMessageDialog(window.getFrameWaitingRoom(), "Connection lost with the server.", "Remote Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
		PlayersList playersList;
		try {
			playersList = Client.getServerHandler().getInGamePlayersList();
			window.updatePlayersList(playersList);
		} catch (RemoteException e) {
			window.handleServerDisconnection();
			JOptionPane.showMessageDialog(window.getFrameWaitingRoom(), "Connection lost with the server.", "Remote Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IllegalResponseException e) {

			e.printStackTrace();
		} catch (InvalidTokenException e) {

			e.printStackTrace();
		} catch (NegativeResponseException e) {

			e.printStackTrace();
		} catch (TimeoutException e) {

			e.printStackTrace();
		}
		
		Highscore highscore;
		try {
			highscore = Client.getServerHandler().getHighscore();
			window.updateHighscore(highscore);
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (IllegalResponseException e) {

			e.printStackTrace();
		} catch (InvalidTokenException e) {
			e.printStackTrace();
		} catch (NegativeResponseException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
		restart();
	}
	
	void restart() {
		reset();
		setNewInterval(POLLING_INTERVAL);
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#setupLogger()
	 * 
	 */
	@Override
	public void setupLogger() {
		logger = Logger.getLogger("client.gamemanager.keepalivetimer");
		logger.setParent(Logger.getLogger("client.main"));
		logger.setLevel(Level.ALL);
		
	}

	/* {@inheritDoc}
	 * @see logging.Loggable#getLogger()
	 * 
	 */
	@Override
	public Logger getLogger() {
		return logger;
		
	}

}
