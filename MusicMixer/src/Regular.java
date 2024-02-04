/**
 *
 */

/**
 * @author Het Patel
 * Date: January 2024
 * Description: 
 * This class represents a regular user account with limited access to the music mixer.
 * It extends the User class.
 * 
 * Method List:
 * - public Regular() - default constructor
 * - public Regular(boolean loopLibraryAccess, int songLimit) - overloaded constructor
 * - public void setLoopLibraryAccess(boolean loopLibraryAccess) - sets loop library access
 * - public void setSongDurationLimit(int songDurationLimit) - sets song duration limit
 * - public int getSongDurationLimit() - gets song duration limit
 * - public boolean getLoopLibraryAccess() - gets loop library access
 * - public String toString() - toString() method to provide a string representation of the Regular user
 * - public static void main(String[] args) - self-testing main method
 */
public class Regular extends User{

	/**
	 * Instance Data
	 */
	private boolean loopLibraryAccess;
	private int songDurationLimit;

	/**
	 * Regular Constructor
	 */
	public Regular() {
		super();
		this.loopLibraryAccess = false;
		this.songDurationLimit = 30;
	}

	/**
	 * Overloaded Constructor
	 */
	public Regular(boolean loopLibraryAccess, int songLimit) {
		super();
		this.loopLibraryAccess = loopLibraryAccess;
		this.songDurationLimit = songLimit;
	}

	/**
	 * setters
	 */
	public void setLoopLibraryAccess(boolean loopLibraryAccess) {
		this.loopLibraryAccess = loopLibraryAccess;
	}

	public void setSongDurationLimit(int songDurationLimit) {
		this.songDurationLimit = songDurationLimit;
	}

	/**
	 * getters
	 */
	public int getSongDurationLimit() {
		return songDurationLimit;
	}

	public boolean getLoopLibraryAccess() {
		return loopLibraryAccess;
	}

	/**
	 * toString method
	 */
	public String toString() {
		return "Regular{" +
				"loopLibraryAccess=" + loopLibraryAccess +
				", songDurationLimit=" + songDurationLimit +
				'}';
	}

	/**
	 * Self Testing Main Method
	 * @param args
	 */
	public static void main(String[] args) {
		// Creating Regular object using the default constructor
		Regular regularUser1 = new Regular();

		// Setting attributes using setter methods
		regularUser1.setLoopLibraryAccess(true);
		regularUser1.setSongDurationLimit(45);

		// Printing information using toString()
		System.out.println("Regular User 1 Information:");
		System.out.println(regularUser1.toString());

		// Creating Regular object using overloaded constructor
		Regular regularUser2 = new Regular(false, 60);

		// Printing information using toString()
		System.out.println("\nRegular User 2 Information:");
		System.out.println(regularUser2.toString());
	}




}
