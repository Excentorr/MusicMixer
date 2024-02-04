/**
 *
 */

/**
 * @author Het Patel
 * Date: January 2024
 * Description: Premium class representing a type of user with additional features such as
 * 				loop library access and song duration limit.
 *
 * Method List:
 * - public Premium() - default constructor
 * - public Premium(boolean loopLibraryAccess, int songLimit) - overloaded constructor
 * - public void setLoopLibraryAccess(boolean loopLibraryAccess) - sets loop library access
 * - public void setSongDurationLimit(int songDurationLimit) - sets song duration limit
 * - public int getSongDurationLimit() - gets song duration limit
 * - public boolean getLoopLibraryAccess() - gets loop library access
 * - public String toString() - toString() method to provide a string representation of the Premium user
 * - public static void main(String[] args) - self-testing main method
 */
public class Premium extends User{

	/**
	 * Instance Data
	 */
	private boolean loopLibraryAccess;
	private int songDurationLimit;

	/**
	 * Regular Constructor
	 */
	public Premium() {
		super();
		this.loopLibraryAccess = true;
		this.songDurationLimit = 60;
	}
	
	/**
	 * Overloaded Constructor
	 */
	public Premium(boolean loopLibraryAccess, int songLimit) {
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
		// Creating premium object using the default constructor
		Premium premiumUser1 = new Premium();

		// Setting attributes using setter methods
		premiumUser1.setLoopLibraryAccess(false);
		premiumUser1.setSongDurationLimit(90);

		// Printing information using toString()
		System.out.println("Premium 1 Information:");
		System.out.println(premiumUser1.toString());

		// Creating premium object using the overloaded constructor
		Premium premiumUser2 = new Premium(true, 120);

		// Printing information using toString()
		System.out.println("\nPremium 2 Information:");
		System.out.println(premiumUser2.toString());
	}



}
