/**
 *
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Het Patel
 * Date: January 2024
 * Description:  
 * This class represents a registration frame for the Music Mixer application.
 *
 * Method List:
 * - public Register() - Constructor
 * - public void actionPerformed(ActionEvent e) - Action Listener
 * - public static void main(String[] args) - Main Method
 */
public class Register implements ActionListener {


	/**
	 * Instance Data/Attributes
	 */
	private JFrame step1, step2;
	private JPanel[] frontPanel, backPanel;
	private JLabel[] lblBackground;
	private int width, height;
	private String background;
	private JLabel[] lblTitle;

	// Step 1 Components
	private JLabel lblFirstName, lblLastName, lblEmail;
	private JTextArea taFirstName, taLastName, taEmail;
	private JButton btnCancel, btnNext;

	// Step 2 Components
	private JLabel lblUsername, lblPassword, lblAccountType;
	private JTextArea taUsername, taPassword;
	private JComboBox cbAccountType;
	private JButton btnBack, btnRegister;

	/**
	 *
	 */
	public Register() {
		// initialize width and height variables
		width = 400;
		height = 400;
		background = "violetTheme.png";
		int numberOfFrames = 2;

		// creating 2 font variables
		Font f1 = new Font("Arial", Font.PLAIN, 16);
		Font f2 = new Font("Arial", Font.PLAIN, 18);

		// Initializing arrays
		backPanel = new JPanel[numberOfFrames];
		frontPanel = new JPanel[numberOfFrames];
		lblBackground = new JLabel[numberOfFrames];
		lblTitle = new JLabel[numberOfFrames];

		//>>>>>>>>> JFrames
		step1 = new JFrame();
		step1.setSize(width, height);
		step1.setLocation(100, 100);
		step1.setResizable(false);

		step2 = new JFrame();
		step2.setSize(width, height);
		step2.setLocation(100, 100);
		step2.setResizable(false);

		// >>>>>>> Panels
		for (int i = 0; i < numberOfFrames; i++) {
			backPanel[i] = new JPanel();
			backPanel[i].setLayout(new GridLayout(1, 1));
			backPanel[i].setBounds(0, 0, width, height);     // sending size of panel

			frontPanel[i] = new JPanel();    // creating new JPanel
			frontPanel[i].setLayout(null);
			frontPanel[i].setOpaque(false);
			frontPanel[i].setBounds(0, 0, width, height); // sending size of panel
		}

		// >>>>>>>>>>>>>>>>> Creating Components + Adding to Panel
		try {
			/**
			 * Adding Components for frontPanel[0]
			 */
			// JFrame
			
			//creating new JLabel and setting bounds, foreground and font
			lblFirstName = new JLabel("First Name:");
			lblFirstName.setBounds(40, 60, 235, 41);
			lblFirstName.setForeground(Color.WHITE);
			lblFirstName.setFont(f1);
			frontPanel[0].add(lblFirstName);

			//creating new JLabel and setting bounds, foreground and font
			lblLastName = new JLabel("Last Name:");
			lblLastName.setBounds(40, 130, 235, 41);
			lblLastName.setForeground(Color.WHITE);
			lblLastName.setFont(f1);
			frontPanel[0].add(lblLastName);

			//creating new JLabel and setting bounds, foreground and font
			lblEmail = new JLabel("Email:");
			lblEmail.setBounds(40, 200, 235, 41);
			lblEmail.setForeground(Color.WHITE);
			lblEmail.setFont(f1);
			frontPanel[0].add(lblEmail);

			// JTextArea
			//creating new JTextArea and setting bounds, border and font
			taFirstName = new JTextArea();
			taFirstName.setBounds(40, 100, 320, 24);
			taFirstName.setFont(f2);
			taFirstName.setBorder(new LineBorder(new Color(32, 32, 32)));
			frontPanel[0].add(taFirstName);

			//creating new JTextArea and setting bounds, border and font
			taLastName = new JTextArea();
			taLastName.setBounds(40, 170, 320, 24);
			taLastName.setFont(f2);
			taLastName.setBorder(new LineBorder(new Color(32, 32, 32)));
			frontPanel[0].add(taLastName);

			//creating new JTextArea and setting bounds, border and font
			taEmail = new JTextArea();
			taEmail.setBounds(40, 240, 320, 24);
			taEmail.setFont(f2);
			taEmail.setBorder(new LineBorder(new Color(32, 32, 32)));
			frontPanel[0].add(taEmail);

			// JButton
			//creating new JButton and setting bounds and border
			btnCancel = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/CancelButton.png"))));
			btnCancel.setBounds(40, 280, 155, 36);
			btnCancel.setBorderPainted(false);
			frontPanel[0].add(btnCancel);
			
			//creating new JButton and setting bounds and border
			btnNext = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/NextButton.png"))));
			btnNext.setBounds(205, 280, 155, 36);
			btnNext.setBorderPainted(false);
			frontPanel[0].add(btnNext);


			/**
			 * Adding Components for frontPanel[1]
			 */
			// JLabels
			/**
			 * Creating JLabels, setting their bounds, fonts, and foreground
			 * and adding them to a panel
			 */
			for (int i = 0; i < numberOfFrames; i++) {
				lblBackground[i] = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("imgs/" + background))));
				lblBackground[i].setBounds(40, 70, width, height);
				backPanel[i].add(lblBackground[i]);

				lblTitle[i] = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("imgs/RegisterTitle.png"))));
				lblTitle[i].setBounds(0, 0, width, height);
				lblTitle[i].setForeground(Color.WHITE);
				lblTitle[i].setFont(f1);
				frontPanel[i].add(lblTitle[i]);

			}

			lblUsername = new JLabel("Username:");
			lblUsername.setBounds(40, 60, 235, 41);
			lblUsername.setForeground(Color.WHITE);
			lblUsername.setFont(f1);
			frontPanel[1].add(lblUsername);

			lblPassword = new JLabel("Password:");
			lblPassword.setBounds(40, 130, 235, 41);
			lblPassword.setForeground(Color.WHITE);
			lblPassword.setFont(f1);
			frontPanel[1].add(lblPassword);

			lblAccountType = new JLabel("Account Type");
			lblAccountType.setBounds(40, 215, 235, 41);
			lblAccountType.setForeground(Color.WHITE);
			lblAccountType.setFont(f1);
			frontPanel[1].add(lblAccountType);

			// JTextArea
			/**
			 * Creating JTextArea, setting their bounds, fonts, and border
			 * and adding them to a panel
			 */
			taUsername = new JTextArea();
			taUsername.setBounds(40, 100, 320, 24);
			taUsername.setFont(f2);
			taUsername.setBorder(new LineBorder(new Color(32, 32, 32)));
			frontPanel[1].add(taUsername);

			taPassword = new JTextArea();
			taPassword.setBounds(40, 170, 320, 24);
			taPassword.setFont(f2);
			taPassword.setBorder(new LineBorder(new Color(32, 32, 32)));
			frontPanel[1].add(taPassword);

			// JButton
			/**
			 * Creating JButton, setting their bounds, border
			 * and adding them to a panel
			 */
			btnBack = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/BackButton.png"))));
			btnBack.setBounds(40, 280, 155, 36);
			btnBack.setBorderPainted(false);
			frontPanel[1].add(btnBack);

			btnRegister = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/RegisterButton.png"))));
			btnRegister.setBounds(205, 280, 155, 36);
			btnRegister.setBorderPainted(false);
			frontPanel[1].add(btnRegister);

			// JComboBox
			/**
			 * Creating JComboBox, setting their bounds and fonts
			 * and adding them to a panel
			 */
			// declare and initializing accounts array
			String[] accounts = {"Regular", "Premium"};

			cbAccountType = new JComboBox(accounts);
			cbAccountType.setBounds(180, 220, 170, 30);
			cbAccountType.setFont(f1);
			frontPanel[1].add(cbAccountType);

		} catch (Exception r) {
			r.printStackTrace();
		}

		// add panels
		frontPanel[0].add(backPanel[0]); // Add the background panel to the registration panel
		frontPanel[0].setVisible(true); // Make the registration panel visible
		step1.add(frontPanel[0]); // Add the registration panel to the frame
		step1.setVisible(true); // Make the frame visible

		frontPanel[1].add(backPanel[1]); // Add the background panel to the registration panel
		frontPanel[1].setVisible(true); // Make the registration panel visible

		step2.add(frontPanel[1]); // Add the registration panel to the frame
		step2.setVisible(false); // Make the frame visible

		// adding action listener to each button
		btnCancel.addActionListener(this);
		btnBack.addActionListener(this);
		btnNext.addActionListener(this);
		btnRegister.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
	    // Filename for login information
	    String loginFilename = "LoginInfo.txt";
	    
	    // Create an instance of FileAccess for file operations
	    FileAccess fileAccess = new FileAccess();

	    /**
	     * Checks what button is clicked
	     */
	    if (e.getSource() == btnCancel) {
	        // close current frames and calls Login frame
	        step1.dispose();
	        step2.dispose();
	        new Login();
	    } else if (e.getSource() == btnNext) {
	    	/**
	    	 * If first name and last name fields are empty, error message will be displayed
	    	 */
	        if (!taFirstName.getText().isEmpty() && !taLastName.getText().isEmpty()) {
	            step1.setVisible(false);
	            step2.setVisible(true);
	        } else {
	            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } else if (e.getSource() == btnBack) {
	        // switch back to the previous step
	        step1.setVisible(true);
	        step2.setVisible(false);
	    } else if (e.getSource() == btnRegister) {
	        
	    	// If username and password fields are empty, will display error, otherwise will continue
	        if (!taUsername.getText().isEmpty() && !taPassword.getText().isEmpty()) {

	            // Check if the username is already taken
	            int index = fileAccess.check(taUsername.getText(), 0, loginFilename);
	            if (index == -1) {
	                // Create a new User object and set its attributes
	                User customer = new User();
	                customer.setFirstName(taFirstName.getText());
	                customer.setLastName(taLastName.getText());
	                customer.setEmail(taEmail.getText());
	                customer.setUsername(taUsername.getText());
	                customer.setPassword(taPassword.getText());

	                // Sets account type based on the selected item in the combo box
	                if (cbAccountType.getSelectedItem().equals("Premium")) {
	                    customer.setAccountType('p');
	                } else {
	                    customer.setAccountType('r');
	                }

	                // Save login data for the new user
	                fileAccess.saveLoginData(customer, loginFilename);

	                // Save new user beats information
	                fileAccess.saveNewUserBeats("BeatsInfo.txt", customer.getAccountID(), customer.getAccountType());

	                // Close current frames and open Login frame
	                step1.dispose();
	                step2.dispose();
	                new Login();

	            } else {
	                JOptionPane.showMessageDialog(null, "Username Taken", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
	            }

	        } else {
	            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}


}
