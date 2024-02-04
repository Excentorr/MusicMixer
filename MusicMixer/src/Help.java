/**
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Het Patel
 * Date: January 2024
 * Description:
 *
 *
 */
public class Help extends JFrame implements ActionListener {

    private JPanel frontPanel, backPanel;
    private JTextArea taSearch;
    private JButton btnSortA, btnSortB, btnSortC;
    private JButton btnAdd, btnRemove;
    private JTextField tfLoopList;
    private JLabel lblSort, lblList, lblTitle, lblBackground;

    private int height, width;

    /**
     *
     */
    public Help() {
        super("Loop Library");

        width = 500;
        height = 750;

        Font f1 = new Font("Arial", Font.PLAIN, 16);

        // >>>>>>> Panels
        backPanel = new JPanel();
        backPanel.setLayout(new GridLayout(1, 1));
        backPanel.setBounds(0, 0, width, height);

        frontPanel = new JPanel();
        frontPanel.setLayout(null);
        frontPanel.setOpaque(false);
        frontPanel.setBounds(0, 0, width, height);

        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>> Creating Components + Adding to panel

        // JLabels

        lblBackground = new JLabel("");
        lblBackground.setBounds(40, 70, 235, 41);
        backPanel.add(lblBackground);

        lblTitle = new JLabel("Loop Library");
        lblTitle.setFont(f1);
        lblTitle.setBounds(getBounds());


        // JTextArea

        // JTextField

        // JButton



        // add panels
        add(frontPanel);
        add(backPanel);



        setSize(width, height);
        setLocation(100, 100);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    }


}
