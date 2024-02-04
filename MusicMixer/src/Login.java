import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author Het Patel
 * Date: January 2024
 * Description:
 * This class represents a login window with a graphical user interface (GUI),
 * allowing users to enter their username and password to log in. It extends JFrame
 * and implements ActionListener for handling button events.
 *
 * Method List:
 * - public Login() - default constructor to initialize the GUI components
 * - public void actionPerformed(ActionEvent e) - method to handle button click events
 * - public static void main(String[] args) - main method for self-testing the Login class
 */
public class Login extends JFrame implements ActionListener {


	// Declaring variables for GUI
	private JPanel frontPanel, backPanel;
	private JLabel lblBackground;
	private JLabel lblEnterUsername, lblEnterPassword, lblTitle;
	private JTextArea taUsername, taPassword;
	private JButton btnLogin, btnRegister;

	// Declaring variables as a placeholder
	private int width, height;
	private String background;

	/**
	 *
	 */
	public Login() {
		super("Login");

		// initializing variables
		width = 400;
		height = 350;
		background = "violetTheme.png";

		// creating 2 fonts
		Font f1 = new Font("Arial", Font.PLAIN, 16);
		Font f2 = new Font("Arial", Font.PLAIN, 18);

		// >>>>>>> Panels
		backPanel = new JPanel();	// creating new JPanel
		backPanel.setLayout(new GridLayout(1, 1));	// set layout
		backPanel.setBounds(0, 0, width, height);	 // sending size of panel

		frontPanel = new JPanel();	// creating new JPanel
		frontPanel.setLayout(null); // set layout
		frontPanel.setOpaque(false);
		frontPanel.setBounds(0, 0, width, height); // sending size of panel

		// >>>>>>>>>>>>>>>>> Creating Components + Adding to Panel
		try {
			// JLabels
			// creates new JLabel with a background image and sets bounds, colour and font
			lblBackground = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("imgs/" + background))));
			lblBackground.setBounds(40, 70, width, height);
			backPanel.add(lblBackground); //adds to panel

			// creates new JLabel with text and sets bounds, colour and font
			lblEnterUsername = new JLabel("Username");
			lblEnterUsername.setBounds(40, 60, 235, 41);
			lblEnterUsername.setForeground(Color.WHITE);
			lblEnterUsername.setFont(f1);
			frontPanel.add(lblEnterUsername); //adds to panel

			// creates new JLabel with text and sets bounds, colour and font 
			lblEnterPassword = new JLabel("Password");
			lblEnterPassword.setBounds(40, 130, 235, 41);
			lblEnterPassword.setForeground(Color.WHITE);
			lblEnterPassword.setFont(f1);
			frontPanel.add(lblEnterPassword); //adds to panel

			// creates new JLabel with a title image and sets bounds, colour and font 
			lblTitle = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("imgs/LoginTitle.png"))));
			lblTitle.setBounds(0, 0, width, height);
			lblTitle.setForeground(Color.WHITE);
			lblTitle.setFont(f1);
			frontPanel.add(lblTitle); //adds to panel

			// JTextArea
			// creates new JTextArea and sets bounds, colour and font 
			taUsername = new JTextArea("");
			taUsername.setBounds(40, 100, 320, 24);
			taUsername.setFont(f2);
			taUsername.setBorder(new LineBorder(new Color(32, 32, 32)));
			frontPanel.add(taUsername); //adds to panel

			// creates new JTextArea and sets bounds, colour and font 
			taPassword = new JTextArea("");
			taPassword.setBounds(40, 170, 320, 24);
			taPassword.setFont(f2);
			taPassword.setBorder(new LineBorder(new Color(32, 32, 32)));
			frontPanel.add(taPassword); //adds to panel

			// JButton
			// creates new JButton and sets an image to it. And Setting the bounds, and borderPainted to false
			btnLogin = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/LoginButton.png"))));
			btnLogin.setBounds(40, 240, 155, 36);
			btnLogin.setBorderPainted(false);
			frontPanel.add(btnLogin); //adds to panel

			// creates new JButton and sets an image to it. And Setting the bounds, and borderPainted to false
			btnRegister = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/RegisterButton.png"))));
			btnRegister.setBounds(205, 240, 155, 36);
			btnRegister.setBorderPainted(false);
			frontPanel.add(btnRegister); //adds to panel
		} catch (Exception r) {
			r.printStackTrace(); // prints errors if found
		}


		// add panels
		add(frontPanel);
		add(backPanel);

		// adds action listener to both buttons
		btnLogin.addActionListener(this);
		btnRegister.addActionListener(this);

		// sets relevant settings for the JFrame
		setSize(width, height);
		setLocation(30, 120);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {

		// declare and initializing variables
		String filename = "LoginInfo.txt";
		FileAccess fileAccess = new FileAccess();	// creating new FileAccess object

		/**
		 * Checks which button is being clicked using ActionEvent
		 */
		if (e.getSource() == btnLogin) {
			/**
			 * Checks if either JTextAreas are empty, and display a message to the user to fill them in if they are not
			 */
			if (!(taUsername.getText().isEmpty()) || !(taPassword.getText().isEmpty())) {

				// Gets the index location of the username entered in the file and stores it in variable
				int userIndex = fileAccess.check(taUsername.getText(), 0, filename);
				String record = null; // declare and initializing variable to null

				/**
				 * if index from the check method used above is -1, the username was not found within 
				 * the file. And will display an error message to the user to check username
				 */
				if (userIndex != -1) {
					/**
					 *  gets storedPassword that is in the same index as the username and checks if the 
					 *  password matches if it doesn't error message will be displayed
					 *  
					 *  if the password is correct, program will dispose frame, get the linked user data, and
					 *  create a new User object to process the record of the user, and calls the MainMenu class
					 *  using the record as the argument.
					 */
					String storedPassword = fileAccess.readPassword(userIndex, filename);
					if (taPassword.getText().equals(storedPassword)) {
						this.dispose();

						try {
							record = fileAccess.getUserData(filename, userIndex);
						} catch (IOException ex) {
							JOptionPane.showMessageDialog(null, "Error! Unable to access file.",
									"File Error", JOptionPane.ERROR_MESSAGE);
						}

						User user = new User();
						user.processString(record);

						new MainMenu(user.getAccountID());

					} else {
						JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.",
								"Login Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Username not found. Please check your username and try again.",
							"Login Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please enter a username and/or password to login.", "Login Error", JOptionPane.ERROR_MESSAGE);
			}
		}   else if (e.getSource() == btnRegister) {
			this.dispose();	// disposes frame
			new Register();	// calls register class
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Login gui = new Login();
	}
}
