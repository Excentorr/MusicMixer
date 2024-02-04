import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 
 * @author shubhpatel
 * 
 * Date: Jan 20 2024
 * Description: 
 * This class represents a Song Library GUI application where users can manage and organize their music library.
 * It provides features such as sorting, searching, favoriting, and deleting songs.
 * 
 * Method List: 
 * Methods:
 * - public SongLibrary(String id, String name) throws IOException - constructor for creating a SongLibrary instance
 * - private JPanel createPanel(int index) throws IOException - creates a panel for displaying a song at the given index
 * - public void actionPerformed(ActionEvent e) - handles actions performed on buttons and checkboxes
 * - public void onlyFavsPanel() throws IOException - displays panels for favorite songs only
 * - public void updatePanels() throws IOException - updates and displays all panels
 * - private JButton createFavouriteButton(int index) - creates a favorite button for a song at the given index
 * - private JButton createDeleteButton(int index) throws IOException - creates a delete button for a song at the given index
 * - private JButton[] removeButtonElement(JButton[] array, int indexToRemove) - removes a button from the array
 * - private JPanel[] removePanelElement(JPanel[] array, int indexToRemove) - removes a panel from the array
 * - public static void main(String[] args) throws IOException - main method for testing the SongLibrary class
 *
 */

public class SongLibrary extends JFrame implements ActionListener {
	/**
	 * private attributes
	 */
	private SongList songs, favourites;
	private JPanel panels[], contentPanel;
	private JButton sortName, sortSize, sortDateCreated, sortDateAccessed, btnHome, btnQuit, btnHelp;
	private JScrollPane scrollPane;
	private JTextField searchField;
	private JLabel searchLabel, greetingLabel;
	private int[] sortToggles, likeToggles;
	private ImageIcon liked, unliked;
	private SimpleDateFormat df;
	private JButton favouriteBtns[], deleteBtns[];
	private JCheckBox checkBox;
	private String AccId; 

	/**
	 * constructor for Jframe
	 * @param ID
	 * @param name
	 * @throws IOException
	 */
	public SongLibrary(String ID, String name) throws IOException {
		super("Song Library");

		// initialize the variables passed in from main menu
		this.AccId = ID; 

		setLayout(null);	// set layout to null
		songs = new SongList("songs.txt");	// read from the file 

		favourites = new SongList();	// create a new list of favourites

		// greeting label
		greetingLabel = new JLabel(name + "'s Song Library");
		greetingLabel.setForeground(Color.WHITE);
		greetingLabel.setBounds(26, 0, 319, 45);
		greetingLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// sort name btn
		sortName = new JButton();
		sortName.setBounds(100, 135, 100, 40);
		sortName.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("imgs/NameSortButton.png"))));
		sortName.setBorderPainted(false);
		sortName.addActionListener(this);

		// sort size btn
		sortSize = new JButton();
		sortSize.setBounds(280, 135, 100, 40);
		sortSize.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("imgs/SizeSortButton.png"))));
		sortSize.setBorderPainted(false);
		sortSize.addActionListener(this);

		// sort date created btn
		sortDateCreated = new JButton();
		sortDateCreated.setBounds(460, 135, 100, 40);
		sortDateCreated.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("imgs/DateAddedSortButton.png"))));
		sortDateCreated.setBorderPainted(false);
		sortDateCreated.addActionListener(this);

		// sort date modified btn
		sortDateAccessed = new JButton();
		sortDateAccessed.setBounds(640, 135, 100, 40);
		sortDateAccessed.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("imgs/LastModifiedSortButton.png"))));
		sortDateAccessed.setBorderPainted(false);
		sortDateAccessed.addActionListener(this);

		// creating the check box 
		checkBox = new JCheckBox("Favourites Only");
		checkBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkBox.setForeground(Color.WHITE);
		checkBox.setBorderPainted(false);
		checkBox.setOpaque(false);
		checkBox.setBounds(690, 60, 181, 35);
		checkBox.addActionListener(this);

		// home btn
		btnHome = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/homeButton.png"))));
		btnHome.setBounds(15, 545, 100, 40);
		btnHome.setBorderPainted(false);
		btnHome.addActionListener(this);

		// quit btn
		btnQuit = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/quitButton.png"))));
		btnQuit.setBounds(780, 545, 100, 40);
		btnQuit.setBorderPainted(false);
		btnQuit.addActionListener(this);

		// help btn
		btnHelp = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/helpButton2.png"))));
		btnHelp.setBounds(670, 545, 100, 40);
		btnHelp.setBorderPainted(false);
		btnHelp.addActionListener(this);

		// initialize the image icons to the images 
		liked = new ImageIcon(ImageIO.read(getClass().getResource("imgs/Liked_Heart.png")));
		unliked = new ImageIcon(ImageIO.read(getClass().getResource("imgs/Unliked_Heart.png")));

		// like toggles are used to determine if the file is liked or not 
		likeToggles = new int[songs.getSize()];	// initialize and create a new array of toggles 
		for (int i = 0; i < likeToggles.length; i++) {
			if (songs.getSongRecord(i).getIsFav()) {	// if it is a favourite toggle key is 1
				likeToggles[i] = 1;
			} else {
				likeToggles[i] = -1;	// if it is not favourite then toggle key is 2
			}

		}

		// create and initialize the array for favourite buttons
		favouriteBtns = new JButton[songs.getSize()];

		for (int i = 0; i < favouriteBtns.length; i++) {
			favouriteBtns[i] = new JButton();	// initialize the array
			favouriteBtns[i].addActionListener(this);
			favouriteBtns[i].setActionCommand(Integer.toString(i));  // Set the index as the action command
			// change image based on the status of being favourite
			if (songs.getSongRecord(i).getIsFav()) {
				favouriteBtns[i].setIcon(liked);
				favourites.insert(songs.getSongRecord(i));
			} else {
				favouriteBtns[i].setIcon(unliked);
			}

		}


		// create and initialize the array for delete buttons
		deleteBtns = new JButton[songs.getSize()];

		for (int i = 0; i < deleteBtns.length; i++) {
			deleteBtns[i] = new JButton();// initialize the array

			deleteBtns[i].addActionListener(this);
			deleteBtns[i].setActionCommand(Integer.toString(i));
			deleteBtns[i].setIcon(new ImageIcon(ImageIO.read(getClass().getResource("imgs/deleteBtn.png"))));
		}


		// Create an array of panels
		panels = new JPanel[songs.getSize()];

		sortToggles = new int[4];	// toggle for all 4 sortings

		// initialize the sort toggles to 1
		for (int i = 0; i < sortToggles.length; i++) {
			sortToggles[i] = 1;
		}
		// Create and add panels dynamically
		for (int i = 0; i < panels.length; i++) {
			panels[i] = createPanel(i);
			if (i % 2 != 0) {
				panels[i].setBackground(Color.LIGHT_GRAY);
			}
			add(panels[i]);
		}

		contentPanel = new JPanel();	// create a content panel 

		// Add panels to a panel with a vertical BoxLayout
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));


		// Iterate through the array of panels and add each panel to the contentPanel.
		for (JPanel panel : panels) {
			contentPanel.add(panel);
		}

		// Wrap the content panel in a JScrollPane
		scrollPane = new JScrollPane(contentPanel);

		// Set the bounds for the scroll pane
		scrollPane.setBounds(16, 180, 869, 357);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


		// Add search bar component
		searchField = new JTextField();
		searchField.setBounds(79, 55, 562, 47);
		searchField.setBorder(null);
		searchField.setFont(new Font("Tahoma", Font.PLAIN, 30));

		// add the search label 
		searchLabel = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("imgs/searchBarBack.png"))));
		searchLabel.setBounds(18, 43, 650, 69);


		// add the search field as a document listener 
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			// source: https://docs.oracle.com/javase/tutorial/uiswing/events/documentlistener.html
			public void changedUpdate(DocumentEvent e) {
			}	// do not need this for simple text changes

			public void insertUpdate(DocumentEvent e) {
				removeUpdate(e); // if a letter was added go to other method to prevent repeated code
			}

			public void removeUpdate(DocumentEvent e) {	// if a letter was removed
				// get the search term 
				String searchTerm = searchField.getText().toLowerCase();
				songs.insertionSort('a');	// sort the songs
				int index = songs.binarySearch(searchTerm);

				contentPanel.removeAll();	// remove all 

				if (index >= 0) {	// if found
					try {
						// display on the panel 
						panels[index] = createPanel(index);
						searchField.setForeground(new Color(15, 187, 27));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					contentPanel.add(panels[index]);	// add it to contentpanel

				} else {	// else not found change color to red 
					searchField.setForeground(Color.RED);

					// The search term is not found, add all panels
					for (int i = 0; i < panels.length; i++) {
						contentPanel.add(panels[i]);
					}

				}

				repaint();
				revalidate();
			}
		});

		// add all the components 
		add(scrollPane);
		add(searchField);
		add(searchLabel);

		add(greetingLabel);
		add(sortName);
		add(sortSize);
		add(sortDateCreated);
		add(sortDateAccessed);
		add(checkBox);
		add(btnHome);
		add(btnQuit);
		add(btnHelp);
		add(new JLabel(new ImageIcon("src/imgs/violetTheme.png"))).setBounds(0, 0, 900, 600);

		// modify the frame
		setVisible(true);
		setSize(900, 630);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * createPanel method 
	 * creates a panel which has a playbtn, file name 
	 * file size, creation date, modification date, like button 
	 * and delete button 
	 * @param index
	 * @return
	 * @throws IOException
	 */
	private JPanel createPanel(int index) throws IOException {
		// set up to make the FileTime into readable time 
		df = new SimpleDateFormat("MM/dd/yyyy");

		// create a JPanel 
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// create a like btn
		JButton favouriteBtn = createFavouriteButton(index);

		favouriteBtn.setBorderPainted(false);

		// create a delete btn 
		JButton deleteBtn = createDeleteButton(index);
		deleteBtn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("imgs/deleteBtn.png"))));


		//source: https://stackoverflow.com/questions/8367500/how-to-hide-background-of-jbutton-which-containt-icon-image
		deleteBtn.setBorderPainted(false);

		// Set the icon based on whether the song is a favorite
		if (songs.getSongRecord(index).getIsFav()) {
			favouriteBtn.setIcon(liked);
		} else {
			favouriteBtn.setIcon(unliked);
		}

		// Play button on the left
		panel.add(songs.getSongRecord(index).getPlayBtn());


		// source: https://stackoverflow.com/questions/6507695/how-do-i-set-the-horizontal-gap-for-just-one-part-of-a-flowlayout
		panel.add(Box.createRigidArea(new Dimension(20, 0)));

		// fie name 
		panel.add(new JLabel(songs.getSongRecord(index).getFileName()));

		panel.add(Box.createRigidArea(new Dimension(100, 0)));


		// file size
		panel.add(new JLabel(songs.getSongRecord(index).getFileSize() / 1024 + " KB"));

		panel.add(Box.createRigidArea(new Dimension(100, 0)));

		// date created 
		panel.add(new JLabel(df.format(songs.getSongRecord(index).getDateCreated().toMillis())));

		panel.add(Box.createRigidArea(new Dimension(100, 0)));

		//date modified
		panel.add(new JLabel(df.format(songs.getSongRecord(index).getDateAccessed().toMillis())));

		panel.add(favouriteBtn);	// add the like btn

		// Add the like button to the original array
		favouriteBtns[index] = favouriteBtn;

		panel.add(deleteBtn);	// add the delete btn

		deleteBtns[index] = deleteBtn;// add the delete btn to the orignial array

		panel.setBounds(0, 0, 869, 357); // Adjust the size as needed

		return panel;	// return the panel 
	}

	/**
	 * the onlyFavsPanel 
	 * only the files that are favourite are 
	 * kept in the panel 
	 * @throws IOException
	 */
	public void onlyFavsPanel() throws IOException {
		// integer array for all the indexes of the favourite files
		int[] favsIndexes = new int[favourites.getSize()];

		// loop through the array
		for (int i = 0; i < favourites.getSize(); i++) {
			// sort both the songs and the favourite song list 
			songs.insertionSort('a');
			favourites.insertionSort('a');

			// get the index of the liked file 
			int favIndex = songs.binarySearch(favourites.getSongRecord(i).getFileName());

			// if found then the favsIndexes[i] = the index
			if (favIndex >= 0 && songs.getSongRecord(favIndex).getIsFav()) {
				favsIndexes[i] = favIndex;
			}

		}

		// Remove all existing panels from the provided contentPanel
		contentPanel.removeAll();

		for (int i = 0; i < favsIndexes.length; i++) {
			// a Jpanel array for all the favourites
			JPanel[] favPanel = new JPanel[favsIndexes.length];

			// add them to the content panel 
			favPanel[i] = createPanel(favsIndexes[i]);

			// Add the panel for the favorite item to the provided contentPanel
			contentPanel.add(favPanel[i]);
		}


		repaint();
		revalidate();
	}

	/**
	 * method to update panels after they are edited 
	 * 
	 * @throws IOException
	 */
	public void updatePanels() throws IOException {
		// remove all the panels from the content panel 
		contentPanel.removeAll();

		// make the new jpanels with the updated info and add them 
		// to the content panel 
		for (int i = 0; i < panels.length; i++) {
			panels[i] = createPanel(i);
			if (i % 2 != 0) {
				panels[i].setBackground(Color.LIGHT_GRAY);
			}
			contentPanel.add(panels[i]);
		}

		repaint();
		revalidate();
	}


	/**
	 * Method to create favourite button at an index
	 * @param index
	 * @return
	 */
	private JButton createFavouriteButton(int index) {
		// create a Jbutton for like button 
		JButton favouriteBtn = new JButton();
		favouriteBtn.addActionListener(this);
		// set action command 
		favouriteBtn.setActionCommand(Integer.toString(index));

		// Set the icon based on whether the song is a favorite
		if (songs.getSongRecord(index).getIsFav()) {
			favouriteBtn.setIcon(liked);
		} else {
			favouriteBtn.setIcon(unliked);
		}

		return favouriteBtn;	// return the created button
	}

	/**
	 * Method ot create delete button at an index 
	 * @param index
	 * @return
	 * @throws IOException
	 */
	private JButton createDeleteButton(int index) throws IOException {
		// create a delete button 
		JButton deleteBtn = new JButton();
		deleteBtn.addActionListener(this);
		// add action command 
		deleteBtn.setActionCommand(Integer.toString(index));

		// set icon to delete btn
		deleteBtn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("imgs/deleteBtn.png"))));

		return deleteBtn;
	}

	/**
	 * method to remove an element from a jbutton array
	 * @param array
	 * @param indexToRemove
	 * @return
	 */
	private JButton[] removeButtonElement(JButton[] array, int indexToRemove) {
		// create a new array with one less size
		JButton[] newArray = new JButton[array.length - 1];

		// loop through the old array
		for (int i = 0, j = 0; i < array.length; i++) {

			// if i is not the index to remove then add old info to new 
			if (i != indexToRemove) {
				newArray[j++] = array[i];
			}


		}	
		return newArray;	// return the new array

	}

	/**
	 * method to remove an element from a JPanel array
	 * @param array
	 * @param indexToRemove
	 * @return
	 */
	private JPanel[] removePanelElement(JPanel[] array, int indexToRemove) {
		// create an new Jpanel array with one less size
		JPanel[] newArray = new JPanel[array.length - 1];
		// loop through the old array
		for (int i = 0, j = 0; i < array.length; i++) {
			// if i is not the index to remove then add old info to new 
			if (i != indexToRemove) {
				newArray[j++] = array[i];
			}
		}
		return newArray;	// return the new array
	}


	/**
	 * ActionPerformed method
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sortName) {	// if sort name button
			sortToggles[0] *= -1;	// change the toggle

			// if toggle is 1
			if (sortToggles[0] > 0) {
				songs.insertionSort('a'); // sort in ascending order 	
			} else {	// else sort in descending order 
				songs.insertionSort('d');
			}
			try {
				updatePanels();	// update the panels
				songs.saveToFile("songs.txt");	// save info to the file 
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}


		} 

		else if (e.getSource() == sortSize) {// if sort size button
			sortToggles[1] *= -1;// change the toggle

			if (sortToggles[1] > 0) {// sort in ascending order 	
				songs.quickSort(0, songs.getSize() - 1, 'a');

			} else {// else sort in descending order 
				songs.quickSort(0, songs.getSize() - 1, 'd');

			}
			try {
				updatePanels();	// update the panels
				songs.saveToFile("songs.txt");	// save info to the file 
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		} 

		else if (e.getSource() == sortDateCreated) {// if sort date created button
			sortToggles[2] *= -1;// change the toggle

			if (sortToggles[2] > 0) {// sort in ascending order 
				songs.quickSort(0, songs.getSize() - 1, 'a');

			} else {// else sort in descending order 
				songs.quickSort(0, songs.getSize() - 1, 'd');

			}
			try {
				updatePanels();	// update the panels
				songs.saveToFile("songs.txt");	// save info to the file 
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		} 

		else if (e.getSource() == sortDateAccessed) {// if sort date modified button
			sortToggles[3] *= -1;// change the toggle 

			if (sortToggles[3] > 0) { // sort in ascending order
				songs.quickSort(0, songs.getSize() - 1, 'a');

			} else {// else sort in descending order 
				songs.quickSort(0, songs.getSize() - 1, 'd');

			}
			try {
				updatePanels();	// update the panels
				songs.saveToFile("songs.txt");	// save info to the file 
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}

		else if (e.getSource() == checkBox) { // if check box is clicked
			if (checkBox.isSelected()) { // if it is selected
				try {
					onlyFavsPanel();	// only have the favourite panels
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {	// if not selected
				try {
					updatePanels();	// add all panels
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		} 

		else if (e.getSource() == btnHome) { // if btn home
			this.dispose();	// close this frame and call main menu
			new MainMenu(AccId);
		} 

		// if btn quit close this frame
		else if (e.getSource() == btnQuit) {
			this.dispose();
		} 

		// if btn help show the help message 
		else if(e.getSource() == btnHelp) {
			JOptionPane.showMessageDialog(null, "This is the Song Library for Music Mixer." +
					"\nHere you will find pre-loaded music files which can be played.\n" +
					"If you do not want them, you are free to delete them.\n" +
					"In this song library, you will also find the beats that you saved from our " +
					"Beat Maker. Enjoy!");
		}

		// loop to figure out if any of the jbuttons in the like btn array were clicked

		// loop through the array
		for (int i = 0; i < favouriteBtns.length; i++) {
			// if the particular button was the source
			if (e.getSource() == favouriteBtns[i]) {
				likeToggles[i] *= -1;	// change toggle

				// if it is toggle is 1
				if (likeToggles[i] > 0) {
					// update the status 
					songs.getSongRecord(i).setIsFav(true);
					favouriteBtns[i].setIcon(liked);	// change image
					favourites.insert(songs.getSongRecord(i));//insert it to the favourites song list
				} 

				else { // if it is already liked
					// unlike (update the status)
					songs.getSongRecord(i).setIsFav(false);
					favouriteBtns[i].setIcon(unliked);	// change image 
					favourites.delete(songs.getSongRecord(i));	// delete from the favourite song list
				}
				repaint();


				// save the updated info to the file
				try {
					songs.saveToFile("songs.txt");
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}

				break;	// break out after the button was found 
			}
		}


		// loop to figure out if any of the jbuttons in the delete btn array were clicked

		for(int i = 0; i<deleteBtns.length; i++) {
			// if the particular button was the source
			if(e.getSource() == deleteBtns[i]) {
				songs.insertionSort('a');	// sort the song list 

				// ask for conformation 
				int n = JOptionPane.showConfirmDialog(null, "Do you wish to delete " + songs.getSongRecord(i).getFileName() + "?");

				// if the user wants to continue 
				if (n == JOptionPane.OK_OPTION) {
					try {
						// if delete is successful 
						if (songs.delete(songs.getSongRecord(i))) {

							// inform the user 
							JOptionPane.showMessageDialog(null, "Delete Successful");

							// Remove references from the favouritebtns, deletebtns, and the panels arrays
							favouriteBtns = removeButtonElement(favouriteBtns, i);
							deleteBtns = removeButtonElement(deleteBtns, i);
							panels = removePanelElement(panels, i);

							updatePanels();	// update the panels 

						}
						// save the updated info to the file 
						songs.saveToFile("songs.txt");
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}

				}

				break;	// break after all the tasks are done and the button has been found 
			}


		}


	}



	public static void main(String[] args) throws IOException {
		new SongLibrary("ID", "User");
	}


}
