import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;

/**@author Parth
 * Date: 2024, Jan 20
 * Description: The Instrument class is a list of DraggableColor objects. It allows you to set all their note,
 * get their note, get a specific draggable, add a draggable to a panel and get total number of draggables
 *
 * Method List:
 * - public Instrument(int counter, int amount, String sound, String activity) - constructor 
 * - public void setInstrument(String fileName) - sets the instrument using the specified file name for all beats
 * - public String getInstrument() - gets the instrument of the first beat
 * - public Draggable getBeat(int spot) - gets a specific beat at the given spot
 * - public void addBeats(JPanel panel) - adds beats to the specified JPanel
 * - public int getBeatsCount() - gets the number of beats in the instrument
 * - public static void main(String[] args) - self-testing main method
 *
 */

public class Instrument {
	// amount of notes and draggables
    private int amount;
    private DraggableColor[] beats;

    
    public Instrument(int counter, int amount, String sound, String activity){
    this.amount = amount; // set amount
    // make an arry of amount and set each one to a colour and sound
        beats = new DraggableColor[amount];
        for (int i = 0; i < amount; i++) {
            beats[i] = new DraggableColor(new Color(0, 0, 200), sound, i);
            // put them all in a line and make them active is the string we read from,
            // a file is a 1 at its index, 0 for inactive
            beats[i].setBounds(150+i * 100, 70*counter+70, 50, 50);
            if (activity.charAt(i) == '1') beats[i].setActivity(true);
        }
    }

    // change the note names
    public void setInstrument(String fileName) {
        for (int i = 0; i < amount; i++) {
            beats[i].setNote(fileName);
        }
    }

    // get a note name
    public String getInstrument() {
        return beats[0].getNote();
    }

    // get a DraggableColor
    public Draggable getBeat(int spot) {
        return beats[spot];
    }

    // add the draggables to a panel
    public void addBeats(JPanel panel){
        for (int i = 0; i < amount; i++) {
            panel.add(beats[i]);
        }
    }

    // return total # of beats
    public int getBeatsCount() {
        return amount;
    }


    public static void main (String[] args) {
    	 // Create a JFrame to test the Instrument class
        JFrame frame = new JFrame("Instrument Test");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create an Instrument with 5 beats
        Instrument instrument = new Instrument(1, 5, "sound.wav", "10101");

        // Set the instrument note names
        instrument.setInstrument("A.wav");

        // Get the instrument of the first beat and print it
        String firstInstrument = instrument.getInstrument();
        System.out.println("First Instrument: " + firstInstrument);

        // Get and print a specific beat
        Draggable specificBeat = instrument.getBeat(2);
        System.out.println("Specific Beat Note: " + specificBeat.getNote());

        // Add beats to a JPanel and add the panel to the JFrame
        JPanel panel = new JPanel();
        instrument.addBeats(panel);
        frame.add(panel, BorderLayout.CENTER);

        // Print the total number of beats
        int beatsCount = instrument.getBeatsCount();
        System.out.println("Total Number of Beats: " + beatsCount);

        // Make the frame visible
        frame.setVisible(true);
    }
}
