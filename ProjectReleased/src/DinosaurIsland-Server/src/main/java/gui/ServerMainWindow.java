/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gui;

import exceptions.ServerStartupErrorException;
import gameplay.Game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import logging.HTMLFormatter;
import logging.TextPaneHandler;

import communication.PlayerManager;
import communication.Server;
import communication.identifiers.TokenManager;
import javax.swing.JTextField;

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
 * @version 1.6
 * @since	May 22, 2011@12:22:41 PM
 *
 */
public class ServerMainWindow {

	private JFrame frmDinosaurislandServer;
	private JLabel lblServerStatus;
	private JButton btnStopServer;
	private JButton btnStartServer;
	private static JComboBox comboBox;
	private static JPanel panelSwitchableLoggers;
	
	static private ArrayList<JScrollPane> logScrollPanes;
	static private ArrayList<JTextPane> logTextPanes;
	static private Vector<String> logComboModel;

	static final ServerMainWindow uniqueInstance = new ServerMainWindow();
	private static JSpinner spinnerMaxConnections;
	private JLabel lblRegisteredPlayersValue;
	private JLabel lblLoggedPlayersValue;
	private JLabel lblInGamePlayersValue;
	private static JSpinner spinnerSocketPort;
	private static JSpinner spinnerRMIPort;
	private JLabel lblIPAddressValue;
	private JTextField txtRMIBindAddressValue;
	
	static public ServerMainWindow getInstance() {
		return uniqueInstance;
	}
	
	public void updateRegisteredPlayersValue() {
		lblRegisteredPlayersValue.setText(""+PlayerManager.getInstance().getRegisteredPlayersNumber());
	}
	
	public void updateLoggedPlayersValue() {
		lblLoggedPlayersValue.setText(""+TokenManager.getInstance().getUsedIdentifiersNumber());
	}
	
	public void updateInGamePlayersValue() {
		lblInGamePlayersValue.setText(""+Game.getInstance().getInGamePlayersNumber());
	}
	
	public JButton getStopServerButton() {
		return btnStopServer;
	}
	
	public Integer getMaxConnections() {
		return (Integer) spinnerMaxConnections.getValue();
	}
	
	public String getIPAddress() {
		return txtRMIBindAddressValue.getText();
	}
	
	public Integer getSocketPort() {
		return (Integer) spinnerSocketPort.getValue();
	}
	
	public Integer getRMIPort() {
		return (Integer) spinnerRMIPort.getValue();
	}
	
	/**
	 * Create the application.
	 */
	private ServerMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDinosaurislandServer = new JFrame();
		frmDinosaurislandServer.setIconImage(Toolkit.getDefaultToolkit().getImage(ServerMainWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/homeFolder.gif")));
		frmDinosaurislandServer.setTitle("Dinosaur Island Server v.1.0");
		frmDinosaurislandServer.setVisible(true);
		frmDinosaurislandServer.setBounds(100, 100, 1024, 600);
		frmDinosaurislandServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDinosaurislandServer.getContentPane().setLayout(new BoxLayout(frmDinosaurislandServer.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panelServer = new JPanel();
		frmDinosaurislandServer.getContentPane().add(panelServer);
		panelServer.setLayout(new BoxLayout(panelServer, BoxLayout.Y_AXIS));
		
		JPanel panelServerStatus = new JPanel();
		panelServerStatus.setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelServer.add(panelServerStatus);
		panelServerStatus.setLayout(new BoxLayout(panelServerStatus, BoxLayout.Y_AXIS));
		
		JPanel panelRunningLabel = new JPanel();
		panelServerStatus.add(panelRunningLabel);
		panelRunningLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelRunningLabel.setLayout(new BoxLayout(panelRunningLabel, BoxLayout.X_AXIS));
		
		lblServerStatus = new JLabel("Server is: Stopped");
		lblServerStatus.setHorizontalAlignment(SwingConstants.LEFT);
		panelRunningLabel.add(lblServerStatus);
		
		JPanel panelStatusButtons = new JPanel();
		panelServerStatus.add(panelStatusButtons);
		
		btnStartServer = new JButton("Start");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try
				{
					/* We must call the method from outside the event dispatch thread */
					 Thread queryThread = new Thread() {
					      public void run() {
					    	  try {
								Server.getInstance().start();
								lblLoggedPlayersValue.setText("0");
								lblInGamePlayersValue.setText("0");
							} catch (ServerStartupErrorException e) {
								lblServerStatus.setText("Server is: Stopped");
								btnStartServer.setEnabled(true);
								btnStopServer.setEnabled(false);
								JOptionPane.showMessageDialog(frmDinosaurislandServer, e.getMessage(), "Startup Error", JOptionPane.ERROR_MESSAGE);
								
							} catch (IOException e) {
								
								JOptionPane.showMessageDialog(frmDinosaurislandServer, e.getMessage(), "Startup Error", JOptionPane.ERROR_MESSAGE);
								lblServerStatus.setText("Server is: Stopped");
								btnStartServer.setEnabled(true);
								btnStopServer.setEnabled(false);
							}
					      }
					    };
					    queryThread.start();

				}
				catch (Exception theException)
				{
					lblServerStatus.setText("Server is: Stopped");
					btnStartServer.setEnabled(true);
					btnStopServer.setEnabled(false);
				}
				lblServerStatus.setText("Server is: Running");
				btnStartServer.setEnabled(false);
				spinnerMaxConnections.setEnabled(false);
				spinnerSocketPort.setEnabled(false);
				spinnerRMIPort.setEnabled(false);
				txtRMIBindAddressValue.setEnabled(false);
			}
		});
		panelStatusButtons.setLayout(new GridLayout(0, 2, 0, 0));
		panelStatusButtons.add(btnStartServer);
		
		btnStopServer = new JButton("Stop");
		panelStatusButtons.add(btnStopServer);
		btnStopServer.setEnabled(false);
		
		JPanel panelStatusFields = new JPanel();
		panelServerStatus.add(panelStatusFields);
		panelStatusFields.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panelMaxConnections = new JPanel();
		panelStatusFields.add(panelMaxConnections);
		panelMaxConnections.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelMaxConnectionsLabel = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelMaxConnectionsLabel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panelMaxConnections.add(panelMaxConnectionsLabel);
		
		JLabel lblMaxConnections = new JLabel("Max Connections");
		lblMaxConnections.setHorizontalAlignment(SwingConstants.LEFT);
		panelMaxConnectionsLabel.add(lblMaxConnections);
		lblMaxConnections.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblMaxConnections.setVerticalAlignment(SwingConstants.BOTTOM);
		
		JPanel panelMaxConnectionsSpinner = new JPanel();
		panelMaxConnections.add(panelMaxConnectionsSpinner);
		
		spinnerMaxConnections = new JSpinner();
		panelMaxConnectionsSpinner.add(spinnerMaxConnections);
		spinnerMaxConnections.setPreferredSize(new Dimension(90, 28));
		spinnerMaxConnections.setModel(new SpinnerNumberModel(new Integer(200), new Integer(8), null, new Integer(1)));
		JSpinner.NumberEditor spinnerMaxConnectionsEditor = new JSpinner.NumberEditor(spinnerMaxConnections, "#");
		spinnerMaxConnectionsEditor.setAlignmentX(Component.RIGHT_ALIGNMENT);
		spinnerMaxConnections.setEditor(spinnerMaxConnectionsEditor);
		
		JPanel panelRegisteredPlayers = new JPanel();
		panelStatusFields.add(panelRegisteredPlayers);
		panelRegisteredPlayers.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelRegisteredPlayersLabel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelRegisteredPlayersLabel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelRegisteredPlayers.add(panelRegisteredPlayersLabel);
		
		JLabel lblRegisteredPlayers = new JLabel("Players Registered");
		panelRegisteredPlayersLabel.add(lblRegisteredPlayers);
		lblRegisteredPlayers.setHorizontalAlignment(SwingConstants.LEFT);
		
		JPanel panelRegisteredPlayersValue = new JPanel();
		panelRegisteredPlayers.add(panelRegisteredPlayersValue);
		
		lblRegisteredPlayersValue = new JLabel("-");
		panelRegisteredPlayersValue.add(lblRegisteredPlayersValue);
		lblRegisteredPlayersValue.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel panelLoggedPlayers = new JPanel();
		panelStatusFields.add(panelLoggedPlayers);
		panelLoggedPlayers.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelLoggedPlayersLabel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panelLoggedPlayersLabel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		panelLoggedPlayers.add(panelLoggedPlayersLabel);
		
		JLabel lblLoggedPlayers = new JLabel("Players Logged");
		panelLoggedPlayersLabel.add(lblLoggedPlayers);
		
		JPanel panelLoggedPlayersValue = new JPanel();
		panelLoggedPlayers.add(panelLoggedPlayersValue);
		
		lblLoggedPlayersValue = new JLabel("-");
		panelLoggedPlayersValue.add(lblLoggedPlayersValue);
		lblLoggedPlayersValue.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel panelInGamePlayers = new JPanel();
		panelStatusFields.add(panelInGamePlayers);
		panelInGamePlayers.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelInGamePlayersLabel = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panelInGamePlayersLabel.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		panelInGamePlayers.add(panelInGamePlayersLabel);
		
		JLabel lblInGamePlayers = new JLabel("Players in Game");
		panelInGamePlayersLabel.add(lblInGamePlayers);
		
		JPanel panelInGamePlayersValue = new JPanel();
		panelInGamePlayers.add(panelInGamePlayersValue);
		
		lblInGamePlayersValue = new JLabel("-");
		panelInGamePlayersValue.add(lblInGamePlayersValue);
		lblInGamePlayersValue.setHorizontalAlignment(SwingConstants.CENTER);
		btnStopServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try
				{
					/* We must call the method from outside the event dispatch thread */
					 Thread queryThread = new Thread() {
					      public void run() {
					    	  Server.getInstance().stop();
					      }
					    };
					    queryThread.start();
				}
				catch (Exception theException)
				{
					theException.printStackTrace();
				}
				lblServerStatus.setText("Server is: Stopped");
				btnStartServer.setEnabled(true);
				btnStopServer.setEnabled(false);
				spinnerMaxConnections.setEnabled(true);
				spinnerSocketPort.setEnabled(true);
				spinnerRMIPort.setEnabled(true);
				txtRMIBindAddressValue.setEnabled(true);
				lblRegisteredPlayersValue.setText("-");
				lblInGamePlayersValue.setText("-");
				lblLoggedPlayersValue.setText("-");
			}
		});
		
		JPanel panelInfo = new JPanel();
		panelInfo.setBorder(new TitledBorder(null, "Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelServer.add(panelInfo);
		panelInfo.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelSocketPortLabel = new JPanel();
		panelInfo.add(panelSocketPortLabel);
		
		JLabel lblSocketPort = new JLabel("Socket Port");
		panelSocketPortLabel.add(lblSocketPort);
		
		JPanel panelSocketPortSpinner = new JPanel();
		panelInfo.add(panelSocketPortSpinner);
		
		spinnerSocketPort = new JSpinner();
		panelSocketPortSpinner.add(spinnerSocketPort);
		spinnerSocketPort.setModel(new SpinnerNumberModel(3466, 1024, 65535, 1));
		JSpinner.NumberEditor spinnerSocketPortEditor = new JSpinner.NumberEditor(spinnerSocketPort, "#");
		spinnerSocketPort.setEditor(spinnerSocketPortEditor);
		
		JPanel panelRMIPortLabel = new JPanel();
		panelInfo.add(panelRMIPortLabel);
		
		JLabel lblRMIPort = new JLabel("RMI Port");
		panelRMIPortLabel.add(lblRMIPort);
		
		JPanel panelRMIPortSpinner = new JPanel();
		panelInfo.add(panelRMIPortSpinner);
		
		spinnerRMIPort = new JSpinner();
		panelRMIPortSpinner.add(spinnerRMIPort);
		spinnerRMIPort.setModel(new SpinnerNumberModel(1099, 1024, 65535, 1));
		JSpinner.NumberEditor spinnerRMIPortEditor = new JSpinner.NumberEditor(spinnerRMIPort, "#");
		spinnerRMIPort.setEditor(spinnerRMIPortEditor);
		
		JPanel panelIPAddressLabel = new JPanel();
		panelInfo.add(panelIPAddressLabel);
		
		JLabel lblIPAddress = new JLabel("IP Address");
		panelIPAddressLabel.add(lblIPAddress);
		
		JPanel panelIPAddressValue = new JPanel();
		panelInfo.add(panelIPAddressValue);
		try {
			lblIPAddressValue = new JLabel(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		panelIPAddressValue.add(lblIPAddressValue);
		
		JPanel panelRMIBindAddress = new JPanel();
		panelInfo.add(panelRMIBindAddress);
		
		JLabel lblRMIBindAddress = new JLabel("RMI Bind Address");
		panelRMIBindAddress.add(lblRMIBindAddress);
		
		JPanel panelRMIBindAddressValue = new JPanel();
		panelInfo.add(panelRMIBindAddressValue);
		
		txtRMIBindAddressValue = new JTextField();
		txtRMIBindAddressValue.setHorizontalAlignment(SwingConstants.CENTER);
		panelRMIBindAddressValue.add(txtRMIBindAddressValue);
		txtRMIBindAddressValue.setColumns(10);
		txtRMIBindAddressValue.setText(lblIPAddressValue.getText());
		
		JPanel panelLogging = new JPanel();
		panelLogging.setBorder(new TitledBorder(null, "Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmDinosaurislandServer.getContentPane().add(panelLogging);
		panelLogging.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panelLogging.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				   CardLayout cl = (CardLayout)(panelSwitchableLoggers.getLayout());
				    cl.show(panelSwitchableLoggers, (String)e.getItem());
			}
		});
		panel.add(comboBox, BorderLayout.NORTH);
		logComboModel = new Vector<String>();
		logComboModel.add("Server");
		comboBox.setModel(new DefaultComboBoxModel(logComboModel));
		//comboBox.setModel(new DefaultComboBoxModel(new String[] {"Server"}));
		
		JPanel panelFilter = new JPanel();
		panel.add(panelFilter, BorderLayout.SOUTH);
		panelFilter.setLayout(new GridLayout(2, 2, 0, 0));
		
		panelSwitchableLoggers = new JPanel();
		panel.add(panelSwitchableLoggers, BorderLayout.CENTER);
		panelSwitchableLoggers.setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPaneServer = new JScrollPane();
		scrollPaneServer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelSwitchableLoggers.add(scrollPaneServer, "Server");
		
		JTextPane textPaneLogger = new JTextPane();
		scrollPaneServer.setViewportView(textPaneLogger);
		textPaneLogger.setContentType("text/html");
		textPaneLogger.setDropMode(DropMode.INSERT);
		textPaneLogger.setEditable(false);
		Server.setupJdkLoggerHandler(textPaneLogger);
		
		logScrollPanes = new ArrayList<JScrollPane>();
		logTextPanes = new ArrayList<JTextPane>();

		Game.getInstance().setupJdkLoggerHandler();
		
		JMenuBar menuBar = new JMenuBar();
		frmDinosaurislandServer.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit              Alt+F4");
		mnFile.add(mntmExit);
		mntmExit.setHorizontalAlignment(SwingConstants.CENTER);
		mntmExit.setMnemonic('x');
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		JMenu mnHelp = new JMenu("?");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmDinosaurislandServer, "Dinosaur Island Server Application\n\nCoded by:\nRiccardo Ancona\nAlessandro Ditta\n\nPolitecnico di Milano - Sede di Cremona\n© 2011", "DinosaurIsland Server",JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnHelp.add(mntmAbout);
	}

	public static void addNewLoggerTextPane(Logger theLogger, String theName) {
		//public static synchronized void addNewLoggerTextPane(ServerHandler theServerHandler, Logger theLogger) {
		
		logScrollPanes.add(new JScrollPane());
		logTextPanes.add(new JTextPane());
		
		JScrollPane sp =  logScrollPanes.get(logScrollPanes.size()-1);
		JTextPane tp = logTextPanes.get(logTextPanes.size()-1);
		
		logComboModel.add(theName);
		panelSwitchableLoggers.add(sp ,theName);
		comboBox.setModel(new DefaultComboBoxModel(logComboModel));
		sp.setViewportView(tp);
		
		tp.setContentType("text/html");
		//tp.setText("<html><head></head><body><b>"+i+"</b></body></html>");
		tp.setEditable(false);
		tp.setDropMode(DropMode.INSERT);
		
		TextPaneHandler tph = new TextPaneHandler();
		tph.setTextPane(tp);
		tph.setFormatter(new HTMLFormatter());
		theLogger.addHandler(tph);
		
	}
	
	public static void addNewClientLoggerTextPane(Logger theLogger) {
		addNewLoggerTextPane(theLogger, "Client "+logScrollPanes.size());
	}

}
