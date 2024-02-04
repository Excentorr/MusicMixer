import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * 
 * Author: parth
 * Date: January 20, 2024
 * Description:
 * A class that creates a draggable panel object capable of playing a sound and toggling
 * between active and inactive states upon click. The panel also has a specified type.
 * 
 * Method List:
 * - public Draggable(String note, int type) - constructor
 * - public void setNote(String fileName) - sets the note file name
 * - public String getNote() - gets the note file name
 * - public void setColor(Color c) - sets the background color of the panel
 * - public void setActivity(boolean active) - sets the activity state and updates the color
 * - public boolean getActivity() - gets the current activity state
 * - public void changeMoveable(Boolean valid) - changes the moveable status of the panel
 * - public void mousePressed(MouseEvent e) - handles mouse press events, plays sound, and toggles activity
 * - public void mouseDragged(MouseEvent e) - handles mouse drag events to move the component
 * - public void mouseClicked(MouseEvent e) - not used
 * - public void mouseReleased(MouseEvent e) - not used
 * - public void mouseEntered(MouseEvent e) - not used
 * - public void mouseExited(MouseEvent e) - not used
 * - public void mouseMoved(MouseEvent e) - not used
 * - public void makeSound() - plays a sound associated with the panel
 * - public static void main(String[] args) - self-testing main method
 */

public class Draggable extends JPanel implements MouseListener, MouseMotionListener {
	// location and type 
	private int x, y, type;
	// name of note in files
	private String note;
	private boolean moveable;  // can  be moved or not
	private boolean active; // is active or not
	
    // constructor
	public Draggable(String note, int type) {
    	// set notes, not moveable, and add action listeners and cursor type
		setNote(note);
		changeMoveable(false);
		addMouseListener(this);
		addMouseMotionListener(this);
		// https://stackoverflow.com/questions/27194858/jbutton-default-cursor#:~:text=This%20is%20how%20to%20set,HAND_CURSOR))%3B
		// set selected cursor
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.active = false;
		this.type = type;
	}

    // set note to a file
	public void setNote(String fileName) {
		note = fileName;
	}
	// return the note
	public String getNote() {
		return note;
	}
	// get backgrond colour
	public void setColor(Color c) {
		setBackground(c);
	}

	// set activity
	public void setActivity(boolean active) {
		this.active = active;

		// set colour depending on activity
		if (type ==  0) {
			if (active) {
				setColor(new Color(200, 0, 0));
			} else {
				setColor(new Color(0, 0, 200));
			}
			repaint();
		}
	}
	public boolean getActivity() {  // return active or not
		return active;
	}

	// chang eif it can be moved
	public void changeMoveable(Boolean valid){moveable = valid;}


	public void mousePressed(MouseEvent e) {
		// play sound if its a left click
		if(SwingUtilities.isLeftMouseButton(e)) {
			makeSound();  // play  music if clicked
			setActivity(!active); // switch active
		}

		// to make it so that the component stick to where the mouse pressed it,
		// instead of the corner of the component when moving
		x = e.getX();
	}

	@Override // move the component
	public void mouseDragged(MouseEvent e) {
		// check if its a right click to drag and moveable
		if(SwingUtilities.isRightMouseButton(e) && moveable) {
			// move to the mouse
			int newX = e.getX() - x + getLocation().x;

			// Round to the nearest multiple of 50
			newX = Math.round(newX / 100) * 100;
			// set its location
			setLocation(newX, getLocation().y);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}


	// makes a sound
	public void makeSound(){
		try{
			// open file and play its sound
			// https://stackoverflow.com/a/26318/22589268
			File inputFile = new File("sounds/" + note);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputFile);

			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();		
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}

	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Draggable Components");
	    frame.setLayout(null);

	    // Create a draggable component
	    Draggable component = new Draggable("heha.wav", 0);
	    component.setBounds(50, 50, 50, 50);
	    component.changeMoveable(true);

	    // Test setNote and getNote methods
	    System.out.println("Initial Note: " + component.getNote());
	    component.setNote("A.wav");
	    System.out.println("Updated Note: " + component.getNote());

	    // Test setColor and setActivity methods
	    System.out.println("Initial Activity State: " + component.getActivity());
	    component.setActivity(true);
	    System.out.println("New Activity State: " + component.getActivity());


	    frame.add(component);
	    frame.revalidate();
	    frame.setSize(300, 200);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);

	}
}
