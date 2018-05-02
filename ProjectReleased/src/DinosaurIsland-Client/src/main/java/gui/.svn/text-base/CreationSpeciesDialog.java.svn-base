/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import communication.Client;

import exceptions.IllegalResponseException;
import exceptions.InvalidTokenException;
import exceptions.NameAlreadyTakenException;
import exceptions.SpeciesAlreadyCreatedException;

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
 * @since	Jun 3, 2011@11:53:05 AM
 *
 */
public class CreationSpeciesDialog extends JDialog implements DocumentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldSpeciesName;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private ImageIcon speciesHerbivore;
	private ImageIcon speciesCarnivorous;
	private ImageIcon speciesHerbivoreInfo;
	private ImageIcon speciesCarnivorousInfo;
	Image previewSpecies;
	Image previewSpeciesInfo;
	private JToggleButton tglbtnHerbivore;
	private JToggleButton tglbtnCarnivorous;
	private JButton btnCreate;
	private JButton btnCancel;
	
	/**
	 * This is used to check whether a string contains alphanumeric characters
	 */
	private static Pattern alphanumericPattern = Pattern.compile("^[A-Za-z0-9_]+$");
	
    private static boolean isAlphanumeric(String s) {
        return alphanumericPattern.matcher(s).matches();
    }
	
	/**
	 * Create the frame.
	 */
	public CreationSpeciesDialog() {
		
		speciesHerbivore = new ImageIcon("images/herbivore.png");
		speciesCarnivorous = new ImageIcon("images/carnivorous.png");
		
		speciesHerbivoreInfo = new ImageIcon("images/HerbivoreInfo.png");
		speciesCarnivorousInfo = new ImageIcon("images/CarnivorousInfo.png");
		
		previewSpecies = speciesHerbivore.getImage();
		previewSpeciesInfo = speciesHerbivoreInfo.getImage();
		
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setTitle("Creation Species");
		setBounds(100, 100, 670, 276);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panelSpeciesDetails = new JPanel();
		panelSpeciesDetails.setBorder(null);
		contentPane.add(panelSpeciesDetails);
		panelSpeciesDetails.setLayout(new BoxLayout(panelSpeciesDetails, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSpeciesDetails.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_2.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblName = new JLabel("Name");
		panel_1.add(lblName);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		
		textFieldSpeciesName = new JTextField();
		panel_1.add(textFieldSpeciesName);
		textFieldSpeciesName.getDocument().addDocumentListener(this);
		textFieldSpeciesName.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textFieldSpeciesName.setColumns(10);
		
		JLabel lblSelectSpecies = new JLabel("Select Species");
		panel_1.add(lblSelectSpecies);
		lblSelectSpecies.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		tglbtnHerbivore = new JToggleButton("Herbivore");
		tglbtnHerbivore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (previewSpecies != speciesHerbivore.getImage()) {
					previewSpecies = speciesHerbivore.getImage();
					previewSpeciesInfo = speciesHerbivoreInfo.getImage();
					repaint();
				}						
			}
		});
		tglbtnHerbivore.setSelected(true);
		buttonGroup.add(tglbtnHerbivore);
		panel_4.add(tglbtnHerbivore);
		
		tglbtnCarnivorous = new JToggleButton("Carnivorous");
		tglbtnCarnivorous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (previewSpecies != speciesCarnivorous.getImage()) {
					previewSpecies = speciesCarnivorous.getImage();
					previewSpeciesInfo = speciesCarnivorousInfo.getImage();
					repaint();
				}
			}
		});
		buttonGroup.add(tglbtnCarnivorous);
		panel_4.add(tglbtnCarnivorous);
		
		JPanel panel_3 = new JPanel();
		panelSpeciesDetails.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnCancel = new JButton("Cancel");
		panel_3.add(btnCancel);
		btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JPanel panelSpeciesPreview = new JPanel();
		panelSpeciesPreview.setBorder(null);
		contentPane.add(panelSpeciesPreview);
		panelSpeciesPreview.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Preview", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSpeciesPreview.add(panel);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		panelSpeciesPreview.add(panel_5, BorderLayout.SOUTH);
		
		btnCreate = new JButton("Create");
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				btnCreate.setEnabled(false);
				btnCancel.setEnabled(false);
				
				try
				{
					/* We must call the method from outside the event dispatch thread */
					 Thread queryThread = new Thread() {
					      public void run() {
					    	  
					    	  	btnCreate.setEnabled(false);
					    	  
								String theSpeciesName = textFieldSpeciesName.getText();
								String theSpeciesType;
								
								if (!isAlphanumeric(theSpeciesName)) {
						    		  JOptionPane.showMessageDialog(contentPane, "The species name must contain only alphanumeric characters.", "Error", JOptionPane.ERROR_MESSAGE);
						    		  btnCreate.setEnabled(true);
						    		  return;
						    	  }
								
								if (tglbtnHerbivore.isSelected()) {
									theSpeciesType = "e";
								} else {
									theSpeciesType = "c";
								}

						    	try {
									Client.getServerHandler().createSpecies(theSpeciesName, theSpeciesType);
									JOptionPane.showMessageDialog(contentPane, "Species created succesfully.\nYou can now enter the game pressing the Enter button.", "Species creation completed", JOptionPane.INFORMATION_MESSAGE);
				                    dispose();
								} catch (TimeoutException e) {
									JOptionPane.showMessageDialog(contentPane, "Can't connect to the server. Connection timed out.\nCheck the connection options or try again later.", "Connection Error", JOptionPane.ERROR_MESSAGE);
									btnCreate.setEnabled(true);
								} catch (NameAlreadyTakenException e) {
									JOptionPane.showMessageDialog(contentPane, "The name you chose for your new species is already taken.\nUse another name instead.", "Error", JOptionPane.ERROR_MESSAGE);
									btnCreate.setEnabled(true);
								} catch (SpeciesAlreadyCreatedException e) {
									JOptionPane.showMessageDialog(contentPane, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
									btnCreate.setEnabled(true);
								} catch (InvalidTokenException e) {
									JOptionPane.showMessageDialog(contentPane, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
									btnCreate.setEnabled(true);
								} catch (IllegalResponseException e) {
									JOptionPane.showMessageDialog(contentPane, "The server didn't respond correctly: you will be disconnected from the server", "Connection Error", JOptionPane.ERROR_MESSAGE);
									btnCreate.setEnabled(true);
								} catch (RemoteException e) {
									JOptionPane.showMessageDialog(contentPane, "Can't reach the server.\nCheck the connection options or try again later.", "Remote Error", JOptionPane.ERROR_MESSAGE);
									e.printStackTrace();
									btnCreate.setEnabled(true);
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
									btnCreate.setEnabled(true);
								}
					      	}
					    };
					    queryThread.start();
				} catch (Exception theException) {
					theException.printStackTrace();
				}				
				btnCreate.setEnabled(true);
				btnCancel.setEnabled(true);
			}
		});
		panel_5.add(btnCreate);
		btnCreate.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCreate.setActionCommand("Create");
		
		JPanel panelSpeciesInfo = new JPanel();
		panelSpeciesInfo.setBorder(new TitledBorder(null, "Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panelSpeciesInfo);
		panelSpeciesInfo.setLayout(new GridLayout(0, 1, 0, 0));
	}
	
   public void paint(Graphics g) {
	   super.paint(g);
       Graphics2D g2d = (Graphics2D) g;
       g2d.drawImage(previewSpecies, 275, 75, null);
       g2d.drawImage(previewSpeciesInfo, 470, 55, null); 
	   Toolkit.getDefaultToolkit().sync();
   }

	/* {@inheritDoc}
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 * 
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		checkSpeciesName();		
	}
	
	/* {@inheritDoc}
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 * 
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		checkSpeciesName();		
	}
	
	/* {@inheritDoc}
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 * 
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		checkSpeciesName();
	}
	
	private void checkSpeciesName() {
		
		String theSpeciesName = new String(textFieldSpeciesName.getText());

		if (theSpeciesName.isEmpty()) {
			btnCreate.setEnabled(false);
		}
		else {
			btnCreate.setEnabled(true);
		}
	}
}
