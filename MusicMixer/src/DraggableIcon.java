import java.awt.Color;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 */

/**@author parth
 * Date: 2024, Jan 20
 * Description: The DraggableIcon class is a draggable icon with a label.
 * Extends the Draggable class and includes methods to set and remove the icon.
 * 
 * Method List:
 * - public DraggableIcon(String note, String iconName) - constructor to create a DraggableIcon with a specified note and icon name
 * - public void setIcon(String fileName) - sets the icon using the specified file name
 * - public void removeIcon() - removes the icon
 * - public static void main(String[] args) - self-testing main method

 */
public class DraggableIcon extends Draggable{
	private JLabel icon;
	private Clip clip;
	
	// constructor just with a icon
	public DraggableIcon(String note, String iconName) {
		super(note, 1);
		changeMoveable(false);
		setIcon(iconName);
		setNote(note);
		setOpaque(true);
	}
	
	// allows you to set icon
	public void setIcon(String fileName) {
		try {
			// add a icon on the panel and set the size to the icons size
			setOpaque(true);
			icon = new JLabel();
			icon.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("imgs/" + fileName))));
			add(icon);
			setSize(icon.getPreferredSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	// makes a sound
    public void makeSound(){
        try {
            File inputFile = new File("sounds/" + getNote());

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputFile);

            if (clip != null && clip.isRunning()) {
                // If the clip is running, pause it
                clip.stop();
            } else {
                // if the clip is not running or is stopped, start playing
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// tester
	public static void main(String[] args) {
	        JFrame t = new JFrame();
	        JPanel p = new JPanel();
	        DraggableIcon d = new DraggableIcon("A.wav", "playBtn.png");

	        t.setBounds(0, 0, 500, 500);
	        p.setBounds(0, 0, 500, 500);
	        d.setBounds(0, 0, 5, 50);

	        p.add(d);
	        t.add(p);

	        t.revalidate();
	        t.setSize(1000, 800);
	        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        t.setVisible(true);
	}

}
