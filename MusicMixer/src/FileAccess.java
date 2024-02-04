import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Het Patel
 * Date: January 2024
 * Description:
 * This class provides file access methods for reading, writing, and manipulating user data
 * and beat information in a music application. It includes methods for checking, reading, and
 * updating data in files.
 *
 * Method List:
 * - public int check(String check, int index, String filename) - checks if a string value is found in a file
 * - public int check2(String check, String filename) - checks if a string value is found in a file
 * - public String readPassword(int userIndex, String filename) - gets password from file
 * - public String getUserData(String filename, int index) throws IOException - gets user data record from file
 * - public int getFileSize(String filename) throws IOException - gets file size
 * - public boolean insert(String record, String filename) - inserts a new record into the file, rewriting the file with "EOF" at the last line
 * - private String[] readFileToArray(String filename) throws IOException - converts each line in the file into a different index within an array
 * - private void writeArrayToFile(String[] lines, String filename) throws IOException - writes each index within an array into a new line inside a file
 * - public String[][] getBeatData(String filename, int index, int numOfSounds) - gets user beat data
 * - public boolean saveUserBeatData(String filename, String id, String[] audio, String[] activeBeats) - saves all the user's beat data to the file
 * - public void saveNewUserBeats(String filename, String id, char accType) - saves new user beats to the file
 * - public boolean saveLoginData(User user, String filename) - saves or updates user login data in the file
 * - public static void main(String[] args) - self testing main menu
 *
 */
public class FileAccess {

	/**
	 * checks if a string value is found in a file
	 */
	public int check(String check, int index, String filename) {
		try {

			InputStreamReader fr = new InputStreamReader(getClass().getResourceAsStream(filename));
			BufferedReader reader = new BufferedReader (fr);

			// Initialize variables to keep track of line index and the current line
			int lineIndex = 0;
			String line;

			// Loop through each line in the file
			while (true) {
				line = reader.readLine(); // Read a line from the file

				// Check for the end of file marker
				if (line.equals("EOF")) {
					// Close the reader and stream and return -1 if end of file is reached
					reader.close();
					fr.close();
					return -1;
				}

				// Split the line into an array using "/" as the delimiter
				String[] info = line.split("/");

				// Check if the array length is greater than the specified index and if the check value matches the element at the index
				if (info.length > index && check.equals(info[index])) {
					// Close the reader and stream and return the current line index if a match is found
					reader.close();
					fr.close();
					return lineIndex;
				}

				lineIndex++; // lineIndex + 1
			}

		} catch (IOException e) {
			return -1; 
		}
	}

	/**
	 * checks if a string value is found in a file
	 */
	public int check2(String check, String filename) {
		try {

			InputStreamReader fr = new InputStreamReader(getClass().getResourceAsStream(filename));
			BufferedReader reader = new BufferedReader (fr);

			// Initialize variables to keep track of line index and the current line
			int lineIndex = 0;
			String line;

			// loop
			while (true) {
				line = reader.readLine();	// reads line

				// checks if EOF is in line and closes reader and fr and returns -1
				if (line.equals("EOF")) {
					reader.close();
					fr.close();
					return -1;
				}

				// if wanted String is found, index is returned
				if (check.equals(line)) {
					reader.close();
					fr.close();
					return lineIndex;
				}

				lineIndex++; // index + 1
			}

		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * Gets Password from file
	 */
	public String readPassword(int userIndex, String filename) {
		try {
			// Read the line for the specified user index and split it to get the password
			String[] file = readFileToArray(filename);

			String line = file[userIndex];

			String[] userInfo = line.split("/");
			if (userInfo.length > 1) {
				return userInfo[1]; // Assuming password is stored at index 1
			} else {
				return null; // Handle the case where password is not found
			}

		} catch (IOException e) {
			e.printStackTrace();; // Handle IO exception
		}
		return "error";
	}

	/**
	 * Retrieves user data from a specified file at the given index.
	 */
	public String getUserData(String filename, int index) throws IOException {
		String record = ""; // declare and initializes variable

		try {

			InputStreamReader fr = new InputStreamReader(getClass().getResourceAsStream(filename));
			BufferedReader reader = new BufferedReader (fr);

			// reads line to go to the information at the index
			for (int i = 0; i < index; i++) {
				reader.readLine();
			}

			record = reader.readLine(); // reads line

			// Close the file reader
			fr.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return record;
	}

	/**
	 * Gets the number of lines in a specified file, representing its size.
	 *
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 */
	public int getFileSize(String filename) throws IOException {
		int size = 0; // declare and intializes variable

		try {

			InputStreamReader fr = new InputStreamReader(getClass().getResourceAsStream(filename));
			BufferedReader reader = new BufferedReader (fr);

			String line; // declares variable
			// checks if line is equal to "EOF"
			while ((line = reader.readLine()) != null && !line.equals("EOF")) {
				size++; // adds 1 to size
			}

			// closes file reader
			fr.close();
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return size; // returns size
	}

	/**
	 * Inserts a new record into the file, rewriting the file with "EOF" at the last line
	 */
	public boolean insert(String record, String filename) {
		try {
			// Read the original lines without the last line "EOF"
			String[] originalLines = readFileToArray(filename);

			// Append the new record to the end of the lines
			String[] newLines = new String[originalLines.length + 1];

			for (int i = 0; i < originalLines.length; i++) {
				newLines[i] = originalLines[i]; // copies originalLines to newLines
			}
			newLines[originalLines.length] = record; // stores record is array

			// Write the modified lines back to the file with "EOF" at the end
			writeArrayToFile(newLines, filename);

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to make each line in the file into a different index within an array
	 */
	private String[] readFileToArray(String filename) throws IOException {
		try {
			InputStreamReader fr = new InputStreamReader(getClass().getResourceAsStream(filename));
			BufferedReader reader = new BufferedReader (fr);

			// declare and initialize variables
			int size = getFileSize(filename);
			String[] lines = new String[size];

			// reads lines in file to lines array
			for (int i = 0; i < size; i++) {
				lines[i] = reader.readLine();
			}

			// closes files
			fr.close();
			reader.close();

			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to write each index within an array into a new line inside a file.
	 */
	private void writeArrayToFile(String[] lines, String filename) throws IOException {
		try {

			FileOutputStream file = new FileOutputStream("src/"+filename);
			PrintWriter writer = new PrintWriter (file);

			// writes lines array to file
			for (int i = 0; i < lines.length; i++) {
				writer.println(lines[i]);
			}
			// writes EOF and closes file reader
			writer.write("EOF");
			writer.flush();
			file.close();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * gets user beat data
	 * String[0][i] - Audio File Names
	 * String[1][i] - Activated Beats
	 */
	public String[][] getBeatData(String filename, int index, int numOfSounds) {
		String[][] data = new String[3][numOfSounds];
		try {
			InputStreamReader fr = new InputStreamReader(getClass().getResourceAsStream(filename));
			BufferedReader reader = new BufferedReader (fr);

			// reads file to the index
			for (int i = 0; i < index + 1; i++) {
				reader.readLine();
			}

			// stores the beat information into data[][]
			for (int i = 0; i < numOfSounds; i++) {
				data[0][i] = reader.readLine();
				data[1][i] = reader.readLine();

			}

			// closes file reader
			reader.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * Saves all the user's data to the file.
	 */
	public boolean saveUserBeatData(String filename, String id, String[] audio, String[] activeBeats) {
		try {

			// Read the original lines without the last line "EOF" from BalanceInfo.txt
			String[] originalLines = readFileToArray(filename);

			// Find the line numbers for the specified account numbers
			int beatLineNumber = check(id, 0, filename);

			if (beatLineNumber != -1) {
				// Update the savings and chequing balances in the array and write the modified balance lines back to BalanceInfo.txt
				originalLines[beatLineNumber] = String.format("%s", id);

				int x = beatLineNumber + 1; // declare and initalizes variable

				// stores information into array
				for (int i = 0; i < audio.length; i++) {
					originalLines[x] = String.format("%s", audio[i]);
					originalLines[x + 1] = String.format("%s", activeBeats[i]);
					x += 2;
				}

				// writes it back into file
				writeArrayToFile(originalLines, filename);


			} else {
				// Append new balance information at the bottom
				String record = id + "\n" +
						audio[0] + "\n" + activeBeats[0] + "\n" +
						audio[1] + "\n" + activeBeats[1] + "\n" +
						audio[2] + "\n" + activeBeats[2] + "\n" +
						audio[3] + "\n" + activeBeats[3] + "\n" +
						audio[4] + "\n" + activeBeats[4] + "\n";

				insert(record, filename); // calls insert method to add to the bottom of the file

			}

			return true;
		}catch (IOException e) {
			return false;
		}

	}

	public void saveNewUserBeats(String filename, String id, char accType) {

		// declare and initializes variables
		String[] audioName = {"ceeday-huh-sound-effect.wav", "hihat.wav", "kick.wav", "snare.wav", id + ".wav"};
		String activeBeats;

		// checks account type, and sets 0s accordingly
		if (accType == 'p') {
			activeBeats = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
		} else {
			activeBeats = "0000000000000000000000000000000000000000000000000000000";
		}

		// creates record
		String record = id + "\n" +
				audioName[0] + "\n" + activeBeats + "\n" +
				audioName[1] + "\n" + activeBeats + "\n" +
				audioName[2] + "\n" + activeBeats + "\n" +
				audioName[3] + "\n" + activeBeats + "\n" +
				audioName[4] + "\n" + "0";

		// inserts record to the file
		insert(record, filename);

	}

	/**
	 * Saves User's Login Data/ Updates user's login data
	 */
	public boolean saveLoginData(User user, String filename) {

		try {
			// Read the original lines without the last line "EOF" from filename
			String[] originalUserLines = readFileToArray(filename);

			// Find the line number for the specified username
			int index = check(user.getUsername(), 0, filename);

			if (index != -1) {
				// Update the customer information in the array
				originalUserLines[index] = String.format("%s/%s/%s/%s/%s/%s/%s",
						user.getUsername(), user.getPassword(), user.getAccountID(), user.getAccountType(), user.getFirstName(), user.getLastName(), user.getEmail());

				// Write the modified customer lines back to CustomerInfo.txt
				writeArrayToFile(originalUserLines, "CustomerInfo.txt");
			} else {
				// Append new customer information at the bottom
				String newCustomerLine = String.format("%s/%s/%s/%s/%s/%s/%s",
						user.getUsername(), user.getPassword(), user.getAccountID(), user.getAccountType(), user.getFirstName(), user.getLastName(), user.getEmail());
				insert(newCustomerLine, filename);
			}

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static void main(String[] args) {
		// Creating an instance of FileAccess
		FileAccess fileAccess = new FileAccess();
		String filename = "FileAccessTesting1.txt";
		String filename2 = "FileAccessTesting2.txt";

		// Testing check method
		int checkIndex = fileAccess.check("4", 0, filename);
		System.out.println("Check result for username123: " + checkIndex);

		// Testing check2 method
		int check2Index = fileAccess.check2("stored", filename);
		System.out.println("Check2 result for someString: " + check2Index);

		// Testing readPassword method
		String password = fileAccess.readPassword(2, filename);
		System.out.println("Password for index 2: " + password);

		try {
			// Testing getUserData method
			String userData = fileAccess.getUserData(filename, 1);
			System.out.println("User data for index 1: " + userData);

			// Testing getFileSize method
			int fileSize = fileAccess.getFileSize(filename);
			System.out.println("File size: " + fileSize);

			// Testing insert method
			boolean insertResult = fileAccess.insert("newRecord", filename);
			System.out.println("Insert result: " + insertResult);

			// Testing getBeatData method
			String[][] beatData = fileAccess.getBeatData(filename2, 0, 5);
			for (int i = 0; i < beatData[0].length; i++) {
				System.out.println("Audio: " + beatData[0][i] + ", Beats: " + beatData[1][i]);
			}

			// Testing saveUserBeatData method
			String[] audio = {"audio1", "audio2", "audio3", "audio4", "audio5"};
			String[] activeBeats = {"001000", "010100", "100001", "110011", "111111"};
			boolean saveUserBeatDataResult = fileAccess.saveUserBeatData(filename2, "userID123", audio, activeBeats);
			System.out.println("Save User Beat Data result: " + saveUserBeatDataResult);

			// Testing saveNewUserBeats method
			fileAccess.saveNewUserBeats(filename2, "newUserID", 'p');

			// Creating a User instance for testing saveLoginData method
			User testUser = new User("testUser", "testLastName", "testEmail", "testUsername", "testPassword", 'r');
			boolean saveLoginDataResult = fileAccess.saveLoginData(testUser, filename);
			System.out.println("Save Login Data result: " + saveLoginDataResult);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


