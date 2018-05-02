/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import javax.naming.AuthenticationException;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import communication.Client;
import communication.RMIServerHandler;
import communication.SocketServerHandler;

import exceptions.IllegalResponseException;
import exceptions.NotFoundException;
import exceptions.PasswordMismatchException;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;

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
 * @since	May 24, 2011@9:48:51 AM
 *
 */
public class ClientMainWindow implements DocumentListener {

	private JTextField txtHost;
	private JSpinner spinnerPort;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	private JRadioButton rdbtnSocket;
	private JRadioButton rdbtnRMI;
	private JButton btnLogin;
	private JButton btnSaveConnectionSettings;
	private JCheckBox chckbxSaveLogin;
	
	private static ClientMainWindow uniqueInstance = new ClientMainWindow();

	private Properties config = new Properties();
	
	private JFrame frmDinosaurislandClient;
	/**
	 * @return the frmDinosaurislandClient
	 */
	public JFrame getFrmDinosaurislandClient() {
		return frmDinosaurislandClient;
	}
	
	public static ClientMainWindow getInstance() {
		return uniqueInstance;
	}
	
	private RegistrationDialog dialog;

	/**
	 * @return the dialog
	 */
	public RegistrationDialog getDialog() {
		return dialog;
	}
	
	public String getHost() {
		return txtHost.getText();
	}
	
	public Integer getPort() {
		return (Integer) spinnerPort.getValue();
	}
	
	/**
	 * This is used to check whether a string contains alphanumeric characters
	 */
	private static Pattern alphanumericPattern = Pattern.compile("^[A-Za-z0-9_]+$");
	
    private static boolean isAlphanumeric(String s) {
        return alphanumericPattern.matcher(s).matches();
    }

	/**
	 * Create the application.
	 */
	public ClientMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmDinosaurislandClient = new JFrame();
		frmDinosaurislandClient.setResizable(false);
		frmDinosaurislandClient.setTitle("Dinosaur Island Client v.1.2");
		frmDinosaurislandClient.setVisible(true);
		frmDinosaurislandClient.setBounds(100, 100, 300, 301);
		frmDinosaurislandClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDinosaurislandClient.getContentPane().setLayout(new BoxLayout(frmDinosaurislandClient.getContentPane(), BoxLayout.X_AXIS));
		
		/*
		 * We create a button group to group the Socket and RMI options
		 */
		ButtonGroup connectionTypeRadioGroup = new ButtonGroup();
		
		JPanel panel = new JPanel();
		frmDinosaurislandClient.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelMain = new JPanel();
		tabbedPane.addTab("Main", null, panelMain, null);
		panelMain.setLayout(new BorderLayout(0, 0));
		
		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMain.add(panelLogin, BorderLayout.NORTH);
		panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.X_AXIS));
		
		JPanel panelLoginField = new JPanel();
		panelLogin.add(panelLoginField);
		panelLoginField.setLayout(new BoxLayout(panelLoginField, BoxLayout.Y_AXIS));
		
		JLabel lblUsername = new JLabel("Username");
		panelLoginField.add(lblUsername);
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		
		textFieldUsername = new JTextField();
		panelLoginField.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		panelLoginField.add(lblPassword);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		
		passwordField = new JPasswordField();
		passwordField.setFocusTraversalPolicyProvider(true);
		panelLoginField.add(passwordField);
		passwordField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		passwordField.setColumns(10);
		
		JPanel panelLoginButton = new JPanel();
		panelLogin.add(panelLoginButton);
		panelLoginButton.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnLogin = new JButton("LOGIN");
		frmDinosaurislandClient.getRootPane().setDefaultButton(btnLogin);
		btnLogin.setMultiClickThreshhold(1000L);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					/* We must call the method from outside the event dispatch thread */
					 Thread queryThread = new Thread() {
					      public void run() {
					    	
					    	  btnLogin.setEnabled(false);
					    	  
					    	  String theHost = txtHost.getText();
					    	  Integer thePort = (Integer) spinnerPort.getValue();
					    	  
					    	  Client.setPort(thePort);
					    	  Client.setIpHost(theHost);
					    	  
					    	  String theUsername = textFieldUsername.getText();
					    	  String thePassword = new String(passwordField.getPassword());
					    	  
					    	  if (chckbxSaveLogin.isSelected()) {
					    		  try {
									saveLoginFields();
								} catch (IOException e) {
									JOptionPane.showMessageDialog(frmDinosaurislandClient, "Error saving Login information in file", "Config File Error", JOptionPane.ERROR_MESSAGE);
								}
					    	  }
					    	  
					    	  if (!isAlphanumeric(theUsername) || !isAlphanumeric(thePassword)) {
					    		  JOptionPane.showMessageDialog(frmDinosaurislandClient, "Username and password must contain only alphanumeric characters.", "Login Error", JOptionPane.ERROR_MESSAGE);
					    		  btnLogin.setEnabled(true);
					    		  return;
					    	  }
					    	 
					    	  
					    	  try {
								Client.getServerHandler().login(theUsername, thePassword);
								Client.setUsername(theUsername);
					
								frmDinosaurislandClient.setFocusable(false);
								frmDinosaurislandClient.setVisible(false);
								WaitingRoomWindow.getInstance().resumeWaitingRoom();
								
					    	  } catch (ConnectException e) {
								JOptionPane.showMessageDialog(frmDinosaurislandClient, "Can't connect to the server.\nCheck the connection options or try again later.", "Connection Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							} catch (AuthenticationException e) {
								JOptionPane.showMessageDialog(frmDinosaurislandClient, e.getMessage() ,"Login Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							} catch (PasswordMismatchException e) {
								JOptionPane.showMessageDialog(frmDinosaurislandClient, e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							} catch (NotFoundException e) {
								JOptionPane.showMessageDialog(frmDinosaurislandClient, e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							} catch (IllegalResponseException e) {
								JOptionPane.showMessageDialog(frmDinosaurislandClient, e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							} catch (TimeoutException e) {
								JOptionPane.showMessageDialog(frmDinosaurislandClient, "Can't connect to the server. Connection timed out.\nCheck the connection options or try again later.", "Connection Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							} catch (RemoteException e) {
								JOptionPane.showMessageDialog(frmDinosaurislandClient, "Can't connect to the server.\nCheck the connection options or try again later.", "Connection Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								JOptionPane.showMessageDialog(frmDinosaurislandClient, e.getMessage() ,"Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							} finally {
								btnLogin.setEnabled(true);
							}
					    	  
					    	  /*
					    	   * It clears the String containing the password characters
					    	   */
					    	  thePassword = "0";  
					    	  
					      }
					    };
					    queryThread.start();
				}
				catch (Exception theException)
				{
					theException.printStackTrace();
				}	
			}
		});
		panelLoginButton.add(btnLogin);
		
		chckbxSaveLogin = new JCheckBox("Remember");
		chckbxSaveLogin.setHorizontalAlignment(SwingConstants.CENTER);
		panelLoginButton.add(chckbxSaveLogin);
		
		JPanel panelExtButton = new JPanel();
		panelExtButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelMain.add(panelExtButton, BorderLayout.SOUTH);
		panelExtButton.setLayout(new BorderLayout(0, 0));
		
		JPanel panelExtButton2 = new JPanel();
		panelExtButton.add(panelExtButton2, BorderLayout.SOUTH);
		panelExtButton2.setLayout(new GridLayout(1, 3, 0, 0));
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					frmDinosaurislandClient.setFocusable(false);
					RegistrationDialog dialog = new RegistrationDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
					
				
		panelExtButton2.add(btnRegister);
		
		JButton btnHomepage = new JButton("Homepage");
		panelExtButton2.add(btnHomepage);
		
		JButton btnExit = new JButton("Quit");
		panelExtButton2.add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
		
		JPanel panelOption = new JPanel();
		tabbedPane.addTab("Options", null, panelOption, null);
		panelOption.setLayout(new BorderLayout(0, 0));
		
		JPanel panelConnection = new JPanel();
		panelConnection.setBorder(new TitledBorder(null, "Connect to", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelOption.add(panelConnection, BorderLayout.NORTH);
		
		JLabel lblHost = new JLabel("Host");
		lblHost.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtHost = new JTextField();
		txtHost.setPreferredSize(new Dimension(4, 10));
		txtHost.setHorizontalAlignment(SwingConstants.RIGHT);
		txtHost.setColumns(10);
		txtHost.setText("localhost");
		txtHost.getDocument().addDocumentListener(this);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		
		spinnerPort = new JSpinner();
		spinnerPort.setModel(new SpinnerNumberModel(3466, 1024, 65535, 1));
		JSpinner.NumberEditor spinnerPortEditor = new JSpinner.NumberEditor(spinnerPort, "#");
		spinnerPort.setEditor(spinnerPortEditor);
		spinnerPort.setValue(3466);
		
		spinnerPort.addChangeListener( new ChangeListener() {
	        /**
	         * Invoked when the JSpinner changes, even one notch in
	         * moving to another value
	         *
	         * @param arg0 a ChangeEvent object
	         */

			@Override
			public void stateChanged(ChangeEvent arg0) {
				setConnectionSettings();
			}
		} ); 
		
		panelConnection.setLayout(new GridLayout(0, 2, 0, 0));
		panelConnection.add(lblHost);
		panelConnection.add(txtHost);
		panelConnection.add(lblPort);
		panelConnection.add(spinnerPort);
		
		btnSaveConnectionSettings = new JButton("Save Settings");
		btnSaveConnectionSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					saveConnectionSettings();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(frmDinosaurislandClient, "Error saving connection settings in file", "Config File Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelConnection.add(btnSaveConnectionSettings);
		
		
		JPanel panelConnectionType = new JPanel();
		panelConnectionType.setBorder(new TitledBorder(null, "Connection Method", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelOption.add(panelConnectionType, BorderLayout.SOUTH);
		
		rdbtnSocket = new JRadioButton("Socket");
		rdbtnSocket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.setServerHandler(SocketServerHandler.getInstance());
			}
		});
		panelConnectionType.add(rdbtnSocket);
		rdbtnSocket.setSelected(true);
		
		rdbtnRMI = new JRadioButton("RMI");
		rdbtnRMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.setServerHandler(RMIServerHandler.getInstance());
			}
		});
		panelConnectionType.add(rdbtnRMI);
		connectionTypeRadioGroup.add(rdbtnSocket);
		connectionTypeRadioGroup.add(rdbtnRMI);
		
		setConnectionSettings();
		
		JPanel panelLogo = new JPanel();
		panel.add(panelLogo, BorderLayout.NORTH);
		
		JLabel lblLogo = new JLabel("");
		ImageIcon logo = new ImageIcon("images/DinosaurislandLogo.png");
		lblLogo.setIcon(logo);
		panelLogo.add(lblLogo);
	
		try {
			loadSettings();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/* {@inheritDoc}
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 * 
	 */
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		setConnectionSettings();
		
	}

	/* {@inheritDoc}
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 * 
	 */
	@Override
	public void insertUpdate(DocumentEvent arg0) {
		setConnectionSettings();
		
	}

	/* {@inheritDoc}
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 * 
	 */
	@Override
	public void removeUpdate(DocumentEvent arg0) {
		setConnectionSettings();
		
	}
	
	/** 
	 * The method setConnectionSettings saves the Host address and the Port number in {@link Client}
	 *  
	 * @since	Jun 1, 2011@5:49:01 PM
	 * 
	 */
	private void setConnectionSettings() {
		Client.setIpHost(getHost());
		Client.setPort(getPort());
	}
	
	private void loadSettings() throws Exception {
		FileInputStream input = new FileInputStream("config.properties");
		config.load(input);
		input.close();
		
		 textFieldUsername.setText(config.getProperty("Username"));
		 passwordField.setText(config.getProperty("Password"));
		 txtHost.setText(config.getProperty("Host"));
		 spinnerPort.setValue(new Integer(config.getProperty("Port")));
		 if (config.getProperty("Connection").equals("Socket")) {
			 rdbtnSocket.setSelected(true);
			 rdbtnRMI.setSelected(false);
			 Client.setServerHandler(SocketServerHandler.getInstance());
		 }
		 if (config.getProperty("Connection").equals("RMI")) {
			 rdbtnSocket.setSelected(false);
			 rdbtnRMI.setSelected(true);
			 Client.setServerHandler(RMIServerHandler.getInstance());
		 }
	}
	
	private void saveConnectionSettings() throws IOException {
		config.setProperty("Host", getHost());
		config.setProperty("Port", getPort().toString());
		if (rdbtnSocket.isSelected()) {
			config.setProperty("Connection", "Socket");
		} else {
			config.setProperty("Connection", "RMI");
		}
		FileOutputStream output = new FileOutputStream("config.properties");
		config.store(output, "--- Client Settings ---");
		output.close();
	}
	
	private void saveLoginFields() throws IOException {
		config.setProperty("Username", textFieldUsername.getText());
		config.setProperty("Password", new String(passwordField.getPassword()));
		FileOutputStream output = new FileOutputStream("config.properties");
		config.store(output, "--- Client Settings ---");
		output.close();
	}
}
