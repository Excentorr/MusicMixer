import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * 
 * Author: parth
 * Date: 2024, Jan 20
 * Description:
 * This class extends the Draggable class to create a draggable panel with a specified color, 
 * note, and count.
 *
 * Method List:
 * - public DraggableColor(Color color, String note, int count) - constructor
 * - public static void main(String[] args) - self-testing main method
 */
public class DraggableColor extends Draggable{
	private int count;
	
	// construcot just with a colour
	public DraggableColor(Color color, String note, int count) {
		super(note, 0);
		changeMoveable(false);
		setColor(color);
		setNote(note);
		this.count = count;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        DraggableColor draggableColor = new DraggableColor(Color.RED, "A.wav", 0);

        frame.setBounds(0, 0, 500, 500);
        panel.setBounds(0, 0, 500, 500);
        draggableColor.setBounds(0, 0, 50, 50);

        panel.add(draggableColor);
        frame.add(panel);

        frame.revalidate();
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}

}
