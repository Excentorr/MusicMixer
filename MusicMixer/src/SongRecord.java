import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import javax.swing.JFrame;

/**
 * @author Shubhpatel
 * Date: Jan 10 2024
 * Description: This class represents a song record which contains the information
 *              for an audio file
 *              
 * Method List: 
 *
 *    - public SongRecord(String fileName, DraggableIcon d) throws IOException - creates a new song record
 *    - public SongRecord(String input) - creates a new song record from a string
 *    - public String getFileName()
 *    - public long getFileSize()
 *    - public FileTime getDateCreated()
 *    - public FileTime getDateAccessed()
 *    - public DraggableIcon getPlayBtn()
 *    - public boolean getIsFav()
 *    - public void setFileName(String fileName)
 *    - public void setFileSize(long fileSize)
 *    - public void setDateCreated(FileTime dateCreated)
 *    - public void setDateAccessed(FileTime dateAccessed)
 *    - public void setPlayBtn(DraggableIcon newBtn)
 *    - public void setIsFav(boolean isFav)
 *    - public void processString(String record) - process a formatted string
 *    - public String toString() - turns the record into a string
 *    - public static void main(String[] args) throws IOException
 *
 */

public class SongRecord {
	/**
	 * private attributes
	 */
	private String fileName;
	private long fileSize;
	private FileTime dateCreated;   // FileTime variable to get the creation date
	private FileTime dateAccessed;  // FileTime variable to get the modified date
	private DraggableIcon playBtn;
	private boolean isFav;

	/**
	 * constructor for song record
	 * makes a record based on the file given and the
	 * draggable object
	 * @param fileName
	 * @param d
	 * @throws IOException
	 */
	public SongRecord(String fileName, DraggableIcon d) throws IOException {

		// make a file object with the filename
		File inputFile = new File("sounds/" + fileName);

		// get the file path
		Path file = Paths.get(inputFile.getPath());

		// creating a object of BasicFileAttributes
		BasicFileAttributes attr = Files.readAttributes(
				file, BasicFileAttributes.class);

		// initialize the variables to the info provided
		this.fileName = fileName;
		this.fileSize = attr.size();
		this.dateCreated = attr.creationTime();
		this.dateAccessed = attr.lastAccessTime();
		this.playBtn = d;
		this.playBtn.setNote(fileName);
		this.isFav = false;
	}

	/**
	 * Overloaded constructor
	 * @param input
	 * takes in a formatted string input and makes
	 * a record using that info
	 */
	public SongRecord(String input) {
		processString(input);
	}

	/**
	 * getter for file name
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * setter for file name
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * file size getter
	 * @return
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * setter for file size
	 * @param fileSize
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * getter for date created
	 * @return
	 */
	public FileTime getDateCreated() {
		return dateCreated;
	}

	/**
	 * setter for date created
	 * @param dateCreated
	 */
	public void setDateCreated(FileTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * getter for file modified
	 * @return
	 */
	public FileTime getDateAccessed() {
		return dateAccessed;
	}

	/**
	 * setter for file modified
	 * @param dateAccessed
	 */
	public void setDateAccessed(FileTime dateAccessed) {
		this.dateAccessed = dateAccessed;
	}

	/**
	 * getter for play btn
	 * @return
	 */
	public DraggableIcon getPlayBtn() {
		return playBtn;
	}

	/**
	 * setter for playbtn
	 * @param newBtn
	 */
	public void setPlayBtn(DraggableIcon newBtn) {
		this.playBtn = newBtn;
	}

	/**
	 * getter for is favourite
	 * @return
	 */
	public boolean getIsFav() {
		return isFav;
	}

	/**
	 * setter for is favourite
	 * @param isFav
	 */
	public void setIsFav(boolean isFav) {
		this.isFav = isFav;
	}

	/**
	 * processString method
	 * takes in a formatted string and
	 * initializes variables
	 * @param record
	 */
	public void processString(String record) {
		// split the input into different parts
		String[] info = record.split("/");

		// initialize the variables with new info
		this.fileName = info[0];
		this.fileSize = Integer.parseInt(info[1]);
		// change the format to milliseconds to make it easier to carry across files
		this.dateCreated = FileTime.fromMillis(Long.parseLong((info[2])));
		this.dateAccessed = FileTime.fromMillis(Long.parseLong((info[3])));
		this.isFav = Boolean.parseBoolean(info[4]);

		// create a new draggable with the 
		this.playBtn = new DraggableIcon(info[0], "playBtn.png");
	}

	/**
	 * toString for record 
	 */
	public String toString() {
		return getFileName() + ", " + getFileSize() + ", " + getDateCreated()+ ", " + 
				getDateAccessed() + ", "+ getIsFav();
	}

	/**
	 * Self testing main
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// test the main constructor
		DraggableIcon d = new DraggableIcon("A.wav", "playBtn.png");

		// create a song record
		SongRecord s = new SongRecord("A.wav", d);

		System.out.println(s.getFileName());
		System.out.println(s.getFileSize());
		System.out.println(s.getDateCreated());
		System.out.println(s.getDateAccessed());
		System.out.println(s.getIsFav());

		// test the second constructor 
		String formattedString = "B.wav/2000/1641885471000/1641885472000/true";


		SongRecord s2 = new SongRecord(formattedString);

		// print to see the new info 
		System.out.println("\n\n" + s2.getFileName());
		System.out.println(s2.getFileSize());
		System.out.println(s2.getDateCreated());
		System.out.println(s2.getDateAccessed());
		System.out.println(s2.getIsFav());

		s2.setFileName("Joke.wav");
		s2.setFileSize(213123);
		s2.setDateCreated(FileTime.fromMillis(System.currentTimeMillis()));
		s2.setDateCreated(FileTime.fromMillis(System.currentTimeMillis()));
		s2.setIsFav(false);



		// print difference information using setters
		System.out.println("\n\n" + s2.getFileName());
		System.out.println(s2.getFileSize());
		System.out.println(s2.getDateCreated());
		System.out.println(s2.getDateAccessed());
		System.out.println(s2.getIsFav());



		// testing the sound system and the setters
		d.setBounds(0, 0, 50, 50);

		JFrame f = new JFrame();
		f.setLayout(null);
		f.add(s.getPlayBtn());	// add the draggable to the frame 

		System.out.println(s.getPlayBtn().getNote());

		// create a new draggable icon 
		DraggableIcon dNew = new DraggableIcon("B.wav", "playBtn.png"); 
		dNew.setBounds(100, 0, 50, 50);
		// set the icon to the record
		s.setPlayBtn(dNew);


		System.out.println(s.getPlayBtn().getNote()); 


		f.add(s.getPlayBtn()); 


		f.setVisible(true);
		f.setSize(400, 400);





	}

}
