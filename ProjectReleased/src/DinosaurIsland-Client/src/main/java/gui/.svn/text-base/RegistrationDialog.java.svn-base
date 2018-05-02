/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import communication.Client;

import exceptions.IllegalResponseException;
import exceptions.NameAlreadyTakenException;

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
 * @author	Ale
 * @version 1.0
 * @since	May 27, 2011@11:07:01 PM
 *
 */
public class RegistrationDialog extends JDialog implements DocumentListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ClientMainWindow window = ClientMainWindow.getInstance();
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldUsername;
	private JPasswordField passwordFieldPassword;
	private JPasswordField passwordFieldConfirmPassword;
	private JLabel lblPassword;
	private JLabel lblConfirmPassword;
	private JButton btnRegister;
	private JTextPane txtpnComment;
	private JButton btnCancel;
	
	/**
	 * This is used to check whether a string contains alphanumeric characters
	 */
	private static Pattern alphanumericPattern = Pattern.compile("^[A-Za-z0-9_]+$");
	
    private static boolean isAlphanumeric(String s) {
        return alphanumericPattern.matcher(s).matches();
    }
	
	/**
	 * Create the dialog.
	 */
	public RegistrationDialog() {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Registration");
		setBounds(100, 100, 282, 234);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel panelRegistration = new JPanel();
			contentPanel.setLayout(new BorderLayout(0, 0));
			contentPanel.add(panelRegistration);
			
			JPanel panelUsername = new JPanel();
			panelUsername.setBorder(new TitledBorder(null, "", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(59, 59, 59)));
			panelUsername.setLayout(new GridLayout(0, 2, 0, 5));
			
			JLabel lblUsername = new JLabel("Username:");
			panelUsername.add(lblUsername);
		
					
			textFieldUsername = new JTextField();
			panelUsername.add(textFieldUsername);
			textFieldUsername.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			lblUsername.setLabelFor(textFieldUsername);
			
			lblPassword = new JLabel("Password:");
			panelUsername.add(lblPassword);
			
						
			passwordFieldPassword = new JPasswordField();
			passwordFieldPassword.getDocument().addDocumentListener(this);
			panelUsername.add(passwordFieldPassword);
			lblPassword.setLabelFor(passwordFieldPassword);
			
			lblConfirmPassword = new JLabel("Confirm Password:");
			panelUsername.add(lblConfirmPassword);
			
			passwordFieldConfirmPassword = new JPasswordField();
			passwordFieldConfirmPassword.getDocument().addDocumentListener(this);
			panelUsername.add(passwordFieldConfirmPassword);
			
			
			
			JPanel panelButton = new JPanel();
			panelButton.setAlignmentX(0.0f);
			panelButton.setLayout(new BorderLayout(0, 0));
			
			JPanel panelButton2 = new JPanel();
			panelButton.add(panelButton2, BorderLayout.SOUTH);
			panelButton2.setLayout(new GridLayout(1, 2, 0, 0));
			
			btnRegister = new JButton("Register!");
			panelRegistration.getRootPane().setDefaultButton(btnRegister);
			btnRegister.setMultiClickThreshhold(500L);
			
			btnRegister.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					btnRegister.setEnabled(false);
					btnCancel.setEnabled(false);
					
					try
					{
						/* We must call the method from outside the event dispatch thread */
						 Thread queryThread = new Thread() {
						      public void run() {
						    	  
									String theUsername = textFieldUsername.getText();
									String thePassword = new String(passwordFieldPassword.getPassword());
									
									if (!isAlphanumeric(theUsername) || !isAlphanumeric(thePassword)) {
							    		  JOptionPane.showMessageDialog(contentPanel, "Username and password must contain only alphanumeric characters.", "Registration Error", JOptionPane.ERROR_MESSAGE);
							    		  return;
							    	  }
  
							    	try {
										Client.getServerHandler().register(theUsername, thePassword);
										JOptionPane.showMessageDialog(contentPanel, "Registered succesfully.", "Registration completed", JOptionPane.INFORMATION_MESSAGE);
					                    RegistrationDialog.this.dispose();
									} catch (TimeoutException e) {
										JOptionPane.showMessageDialog(contentPanel, "Can't connect to the server. Connection timed out.\nCheck the connection options or try again later.", "Connection Error", JOptionPane.ERROR_MESSAGE);
									} catch (NameAlreadyTakenException e) {
										JOptionPane.showMessageDialog(contentPanel, "The username you want to register is already taken.\nUse another username instead.", "Registration Error", JOptionPane.ERROR_MESSAGE);
									} catch (ConnectException e) {
										JOptionPane.showMessageDialog(contentPanel, "Can't connect to the server.\nCheck the connection options or try again later.", "Connection Error", JOptionPane.ERROR_MESSAGE);
									} catch (IllegalResponseException e) {
										JOptionPane.showMessageDialog(contentPanel, "The username and password may contain illegal characters.\nPlease retype and try again.\nIf the problem persists, the server may be unresponsive.\nTry to register later.", "Registration Error", JOptionPane.ERROR_MESSAGE);
									} catch (RemoteException e) {
										JOptionPane.showMessageDialog(null, "Can't reach the server.\nCheck the connection options or try again later.", "Remote Error", JOptionPane.ERROR_MESSAGE);
										e.printStackTrace();
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
					
					btnRegister.setEnabled(true);
					btnCancel.setEnabled(true);

					
				}
			});
			panelButton2.add(btnRegister);
			
			/*
			 * Cancel Button
			 */
			btnCancel = new JButton("Cancel");
			btnCancel.setMultiClickThreshhold(500L);
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
                    RegistrationDialog.this.dispose();
				}
			});
			panelButton2.add(btnCancel);
			panelRegistration.setLayout(new BorderLayout(0, 0));
			panelRegistration.add(panelUsername, BorderLayout.NORTH);
			panelRegistration.add(panelButton, BorderLayout.CENTER);
			
			txtpnComment = new JTextPane();
			txtpnComment.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			txtpnComment.setBorder(null);
			txtpnComment.setEditable(false);
			txtpnComment.setBackground(UIManager.getColor("EditorPane.disabledText"));
			txtpnComment.setContentType("text/html");
			txtpnComment.setText("<body bgcolor=\"d6d9df\"><center><p>Username and Password must contain only alphanumeric characters</p>\n</center></body>");
			panelButton.add(txtpnComment, BorderLayout.CENTER);
		}
	}

	
	




	/* {@inheritDoc}
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 * 
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		checkPassword();
	}



	/* {@inheritDoc}
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 * 
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		checkPassword();
	}



	/* {@inheritDoc}
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 * 
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		checkPassword();
		
	}
	
	private void checkPassword () {
		
		String thePassword = new String(passwordFieldPassword.getPassword());
		String theConfirmedPassword = new String(passwordFieldConfirmPassword.getPassword());
		  
		/*
		 * If passwords do not match the labels become RED and the Register button is disabled 
		 */
		if (thePassword.equals(theConfirmedPassword) && !thePassword.isEmpty()) {
			lblPassword.setForeground(Color.GREEN);
			lblConfirmPassword.setForeground(Color.GREEN);
			btnRegister.setEnabled(true);
		}
		else {
			lblPassword.setForeground(Color.RED);
			lblConfirmPassword.setForeground(Color.RED);
			btnRegister.setEnabled(false);
		}
		
	}

}
