/**
 *
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * @author Het Patel
 * Date: January 2024
 * Description:
 * This class represents the main menu of a music application, providing options
 * for accessing a song library, a beat maker, help information, and logging out.
 * 
 * Method List:
 * - public MainMenu(String accountID) - constructor to initialize the main menu
 * - public void actionPerformed(ActionEvent e) - handles action events for buttons
 * - public static void main(String[] args) - main method
 */
public class MainMenu extends JFrame implements ActionListener {

	// Declaring variables for GUI
	private JPanel frontPanel, backPanel;
	private JLabel lblBackground;
	private JButton btnLoop, btnBeatMaker;
	private JButton btnHelp, btnLogout;

	// Declaring variables as a placeholder
	private int width, height;
	private String background;

	private User userData;

	private FileAccess fileAccess;


	/**
	 *
	 */
	public MainMenu(String accountID) {
		super("Main Menu");

		// initializing variables
		width = 900;
		height = 650;
		background = "violetTheme.png";

		fileAccess = new FileAccess();	// creates new fileaccess object
		String filename = "LoginInfo.txt"; // declare and initializing filename variable
		userData = new User(); // creates new user object
		Font f1 = new Font("Arial", Font.PLAIN, 16);
		
		/**
		 * Uses account ID to get user's record, and stores the information into
		 * userData object, using processString method.
		 */
		try {
			int index = fileAccess.check(accountID, 2, "LoginInfo.txt");
			String record = fileAccess.getUserData(filename, index);
			userData.processString(record);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// >>>>>>> Panels
		backPanel = new JPanel();    // creating new JPanel
		backPanel.setLayout(new GridLayout(1, 1));
		backPanel.setBounds(0, 0, width, height);     // sending size of panel

		frontPanel = new JPanel();    // creating new JPanel
		frontPanel.setLayout(null);
		frontPanel.setOpaque(false);
		frontPanel.setBounds(0, 0, width, height); // sending size of panel

		// >>>>>>>>>>>>>>>>> Creating Components + Adding to Panel

		try {
			// JLabels
			// creates JLabel and sets bounds
			lblBackground = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("imgs/" + background))));
			lblBackground.setBounds(40, 70, width, height);
			backPanel.add(lblBackground);

			// JButton
			/**
			 * creates Jbuttons, sets image, borderPainted, and bounds
			 * adds to panel.
			 */
			btnLoop = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/LoopsLibraryButton.png"))));
			btnLoop.setBorderPainted(false);
			btnLoop.setBounds(width / 2 - 155, height / 2 - 115, 150, 150);
			frontPanel.add(btnLoop);

			btnBeatMaker = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/BeatMakerButton.png"))));
			btnBeatMaker.setBorderPainted(false);
			btnBeatMaker.setBounds(width / 2 + 5, height / 2 - 115, 150, 150);
			frontPanel.add(btnBeatMaker);

			btnHelp = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/HelpButton.png"))));
			btnHelp.setBorderPainted(false);
			btnHelp.setContentAreaFilled(false);
			btnHelp.setBounds(width - 110, height - 90, 40, 40);
			frontPanel.add(btnHelp);

			btnLogout = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/logoutButton.png"))));
			btnLogout.setBorderPainted(false);
			btnLogout.setContentAreaFilled(false);
			btnLogout.setBounds(width - 70, height - 90, 40, 40);
			frontPanel.add(btnLogout);

		} catch (Exception r) {
			r.printStackTrace();
		}
		// add panels
		add(frontPanel);
		add(backPanel);

		// adds action listener to btns
		btnLoop.addActionListener(this);
		btnBeatMaker.addActionListener(this);
		btnHelp.addActionListener(this);
		btnLogout.addActionListener(this);

		// sets attributes of JFrame
		setSize(width, height);
		setLocation(30, 120);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// declare and initializing file name variable
		String filename = "BeatsInfo.txt";

		// Checks which button is clicked
		if (e.getSource() == btnLoop) {
			this.dispose(); // disposes frame
			try {
				new SongLibrary(userData.getAccountID(), userData.getFirstName()); //calls song library
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == btnBeatMaker) {

			int index = fileAccess.check2(userData.getAccountID(), filename); // calls check2 method to locate beat information

			String[][] beatData = fileAccess.getBeatData(filename, index, 5); // gets beat information

			this.dispose(); // closes frame
			new BeatMaker(userData.getAccountID(), userData.getAccountType(), beatData[0], beatData[1]); // calls BeatMaker

		} else if (e.getSource() == btnHelp) {
			this.dispose(); //closes frame
			JOptionPane.showMessageDialog(null, "If you are using this application for the first time, register and make a new account. Enter all the information that is being asked. Then log back in again with the same username and password you entered while registering. \r\n"
					+ "If you already have an account, please log in using your username and password \r\n"
					+ "\r\n"
					+ " After logging in, there are two options. Beat Maker and Song Library. Beat Maker is where you can create beats using different types of sounds and sound-tiles. You can export the beats that you create as well as save your progress as you work on the beats. \r\n"
					+ "\r\n"
					+ "Song Library is where the files that are exported from Beat maker are saved and available to play. Song Library also consist of several pre-loaded songs which you can use. However, if you don't want them in the song library, you can also delete them.");

		} else if (e.getSource() == btnLogout) {
			this.dispose(); // closes frame
			new Login(); // calls Login
		} 

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
