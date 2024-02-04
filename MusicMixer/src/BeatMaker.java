import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/*
Author: Parth
Date: 2024, Jan 20
Description: A class that has a JFrame. This JFrame has 2 panels, one being a panel of buttons, 
and one being a scroll pane. The pannel of buttons serves as control panel for the scroll pane.
It can skip to start and end of the track, play/pause the track, save the tracks progress, save
the track as a song (.wav) file and go back to main menu. The scroll pane (track) is a large panel
which has notes on it, these notes are toggelable and play sound swhen clicked or when the track is playing
There are 2 sizes of track for premium and free users.

Method List:
	 public BeatMaker(String user, char accType, String[] fileNames, String[] activity) // constructor
	 public JButton buttonFactory(String name){  // makes image buttons to be used 
     public Instrument[] newInstrument(String fileName, String activity){ // makes new instruments
     public void actionPerformed(ActionEvent e) {  // detect input
 */


public class BeatMaker extends JFrame implements ActionListener {
	// make the list of instruments, microphone draggable, mic recorder and file access 
    private Instrument[] instruments;
    private DraggableColor mic;
    private Recorder r;
    private FileAccess fileAccess;
    // make the panels and buttons to be displayed, with JLabels
    private JPanel panel, btnsPanel;
    private JScrollPane scrollPane;
    private JButton play, download, goStart, goEnd, saveProgress, micInput, backMenu;
    private JLabel playHead;
    // make the size of the pane in the scroll pane, length of the song and amount of beats,
    // speed and spot of the moving panes, and counters for # of instruments
    private int counter, spot, delay, size, length, beatsAmount, speed;
    // make a timer to move the pane, a boolean to see if it is currently playing, and a user
    private Timer timerAnim;
    private boolean playing;
    private String user;
    private JComboBox<String> selection[];


    public BeatMaker(String user, char accType, String[] fileNames, String[] activity) {
        setLayout(null);
        setResizable(false);
        // set user to be used later
        this.user = user;

        // set the speed (incrament the pane will move at)
        speed = 5;
        delay = 1; // delay for the timer
        timerAnim = new Timer(delay, this); // make timer
        playing = false; // not playing off start
        spot = 0; // set track to 0

        // variables for a regular user
        length = 20;
        size = 6000;
        beatsAmount = 55;

        // if a premium user increase the size
        if (accType == 'p') {
            length *= 3;
            size *= 3;
            beatsAmount = 175;
        }
        
        // make the recorder and panels and file access
        r = new Recorder();
        panel = new JPanel();
        btnsPanel = new JPanel();
        fileAccess = new FileAccess();
        
        // set dimensions of the panes
        btnsPanel.setBounds(0, 0, 1000, 75);
        btnsPanel.setBackground(new Color(38, 37, 41));
        // 552 is to make sure the last button is played
        // for some reason the scroll pane needs a extra 2 at the end to stick to incremants
        panel.setPreferredSize(new Dimension(size+552, 500));
        panel.setBackground(Color.black);
        panel.setLayout(null);

        scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(0, 75, 1000, 400);
        // https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/scrollpane.html
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(50);

        counter = 0; // # of insturments
        // make instruments
        instruments = new Instrument[0];
        instruments = newInstrument(fileNames[0], activity[0]);
        instruments = newInstrument(fileNames[1], activity[1]);
        instruments = newInstrument(fileNames[2], activity[2]);
        instruments = newInstrument(fileNames[3], activity[3]);
        
        // make the mic block
        mic = new DraggableColor(Color.BLUE, "parth.wav", 0);
        mic.changeMoveable(true);
        mic.setBounds(100, 0, 50, 50);
        if (activity[4].charAt(0) == '1') {
        	mic.setActivity(true);
        }

        // make the buttons with their images
        play = buttonFactory("paused.png");
        play.setSize(50, 50);
        download = buttonFactory("export.png");
        download.setSize(100, 50);
        goStart = buttonFactory("skipStart.png");
        goStart.setSize(50, 50);
        goEnd = buttonFactory("skipEnd.png");
        goEnd.setSize(50, 50);
        saveProgress = buttonFactory("saveProg.png");
        saveProgress.setSize(50, 50);
        micInput = buttonFactory("mic.png");
        micInput.setSize(50, 50);
        backMenu = buttonFactory("back.png");
        backMenu.setSize(50, 50);

        // put the play head in a spot that is ligned up with when a note gets played
        try {
            playHead = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("imgs/"+"playHeadP.png"))));
            playHead.setBounds(30, 30, 50, 50);
        }
        catch (Exception e) {}
        
        
        // options for all the instrumens they can choose
        String options[] = {"A.wav", "ceeday-huh-sound-effect.wav", "hihat.wav", "kick.wav", "snare.wav", 
        		"kick2.wav", "kick3.wav", "sax.wav"};
        
        // maake 4 combo boxes and force them to be selected to what was read from a file
        selection = new JComboBox[4];
        for (int i = 0; i < 4; i++) {
        	selection[i] = new JComboBox<String>(options);
        	selection[i].setBounds(10, 75+i*70, 100, 40);
        	selection[i].setSelectedItem(fileNames[i]);
        	selection[i].addActionListener(this);
        	panel.add(selection[i]);
        }
        
        panel.add(selection[0]);
       
        // add to buttons panel
        btnsPanel.add(goStart);
        btnsPanel.add(play);
        btnsPanel.add(goEnd);
        btnsPanel.add(micInput);
        btnsPanel.add(saveProgress);
        btnsPanel.add(download);
        btnsPanel.add(backMenu);
      
        // add to frame
        add(btnsPanel);
        add(playHead, 0);
        add(scrollPane);
        panel.add(mic, 0);

        revalidate();
        setSize(1000, 490);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // make a bunch of buttons  with a custom image and no back ground, and  a custom cursor
    public JButton buttonFactory(String name){
        JButton temp = null;
        try {
            temp = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("imgs/"+name))));
            temp.addActionListener(this);
            temp.setOpaque(false);
            temp.setContentAreaFilled(false);
            temp.setBorderPainted(false);
            temp.setFocusable(false);
// https://stackoverflow.com/questions/27194858/jbutton-default-cursor#:~:text=This%20is%20how%20to%20set,HAND_CURSOR))%3B
            temp.setCursor(new Cursor(Cursor.HAND_CURSOR)); // set selection  cursor
        }
        catch (Exception e) {}
        return temp;
    }

    // overwrite the instruments array with one thats larger with a new instrument
    public Instrument[] newInstrument(String fileName, String activity){
        Instrument[] temp = new Instrument[counter+1];
        for (int i = 0; i < counter; i++) {
            temp[i] = instruments[i]; // save old data
        }
        // add new instrument
        temp[counter] = new Instrument(counter, beatsAmount, fileName, activity); 
        temp[counter].addBeats(panel); // add to panel
        counter++; // increase instrument count
        return temp;
    }

    // on action event
    public void actionPerformed(ActionEvent e) {
    	// if play button is clicked
        if(e.getSource() == play){
            try {
                playing = !playing; // toggle between play and pause and their image
                if (playing) {
                    play.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("imgs/" + "playing.png"))));
                    timerAnim.start(); // start track if playing
                } else {
                    play.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("imgs/" + "paused.png"))));
                    timerAnim.stop(); // pause track if not playing
                }
                repaint();
            }
            catch (Exception e1) {}
        }
        
        // IF ANY OF THE BOXES WERE CHANGED CHANGE THE ACCORDING INSTRUMENTS NOTE
        // if first combo box was selected
        if (e.getSource() == selection[0]) {
        	instruments[0].setInstrument(selection[0].getSelectedItem().toString());
        }
        
        // if first combo box was selected
        if (e.getSource() == selection[1]) {
        	instruments[1].setInstrument(selection[1].getSelectedItem().toString());
        }
        
        // if first combo box was selected
        if (e.getSource() == selection[2]) {
        	instruments[2].setInstrument(selection[2].getSelectedItem().toString());
        }
        
        // if first combo box was selected
        if (e.getSource() == selection[3]) {
        	instruments[3].setInstrument(selection[3].getSelectedItem().toString());
        }
        
        // if save progress button is clicked
        if (e.getSource() == saveProgress){
        	// make them info arrays
        	 String tempSounds[] = new String[5];
             String activeBeats[] = new String[5];

             // get each beat in each instrument and set 1 for active and 0 for inactive to a string
             for (int i = 0; i < instruments.length; i++) {
            	tempSounds[i] = "";
            	activeBeats[i] = "";
             	tempSounds[i] = instruments[i].getInstrument();
             	for (int j = 0; j < beatsAmount; j++) {
             		if (instruments[i].getBeat(j).getActivity()) {
             			activeBeats[i] = activeBeats[i] + 1;
             		}
             		else {
             			activeBeats[i] = activeBeats[i] + 0;
             		}
             	}
             }
             
             // same thing just for the microphone once
             tempSounds[4] = mic.getNote();
             if (mic.getActivity()) {
            	 activeBeats[4] = "1";
             }
             else {
            	 activeBeats[4] = "0";
             }
             
             
             // save their info to a file
             fileAccess.saveUserBeatData("BeatsInfo.txt", user, tempSounds, activeBeats);
             JOptionPane.showMessageDialog(this, "Progress Saved!"); // say to the user its  saved
        }

        // if mic input button was clicked
        if (e.getSource() == micInput){
        	// make a new recorder
            r = new Recorder();
            // record audio and save it to a file called their userID as different people will have differnt recordings
            r.record(user + ".wav");

            // get size of audio file
            // then scale the mic draggable accordingly
            try {
                File inputFile = new File("sounds/" + user + ".wav");

                //https://stackoverflow.com/a/3009973/22589268 // get size of it
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputFile);
                AudioFormat format = audioInputStream.getFormat();
                long frames = audioInputStream.getFrameLength();
                double durationInSeconds = (frames+0.0) / format.getFrameRate();
                long durationInMilliSeconds = (long)(durationInSeconds * 1000);

                // display size
                mic.setSize((int)(durationInSeconds*300), 50);
                mic.setNote(user + ".wav");
            }
            catch (Exception e1) {}
        }
        
        // if go to start is pressed set pane to the start
        if (e.getSource() == goStart){
            scrollPane.getHorizontalScrollBar().setValue(0);
        }

        // if go to end is pressed set pane to the end
        if (e.getSource() == goEnd){
            scrollPane.getHorizontalScrollBar().setValue(size);
        }

        // if download button was pressed
        if (e.getSource() == download){     	
            try {
                int count = 0; // counter to see what the last not played was
                AudioMerger merger = new AudioMerger(); // audio merger

                for (int i = 0; i < panel.getWidth(); i+=speed){ // same speed as track playing
                    if (count >= beatsAmount) break; // to not crash after playing last beat
                    // if there is a beat at the spot
                    if (instruments[0].getBeat(count).getX() == i) {
                    	// for every instrument, check if there is a active beat, 
                    	// if there is add it to the file at its specific time
                        for (int j = 0; j < counter; j++) { 
                            if (instruments[j].getBeat(count).getActivity()){
                                MergeSound sound = new MergeSound(new File("sounds/" + instruments[j].getInstrument()));
                                merger.addSound(i/300.0, sound);
                            }
                        }
                        count++; // next colum of beats
                    }
                    else if (mic.getX() == i && mic.getActivity()){ // also check for mic
                        MergeSound sound = new MergeSound(new File("sounds/" + mic.getNote()));
                        merger.addSound(i/300.0, sound);
                    }
                }

                // make it the lenght according to the account type
                merger.merge(length);
                
                // ask for what the user wants to call it
            	String name = JOptionPane.showInputDialog("Song Name")+".wav";
                merger.saveToFile(new File("sounds/"+name)); // save it as a .wav
               
                // add it to the list of song records
            	SongRecord sR = new SongRecord(name, new DraggableIcon("name.wav", "playBtn.png"));
            	SongList sL = new SongList();
            	sL.readFromFile("Songs.txt", true);
            	sL.insert(sR);
            	sL.saveToFile("Songs.txt");        	
            }
            catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "Error when saving song");
            }

        }
        
        // if back was rpessed close and open menu
        if (e.getSource() == backMenu) {
        	this.dispose();
        	new MainMenu(user);
        }

        // for each timer fire
        if (e.getSource() == timerAnim){

        	//move scroll bar per fire
            scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue()+speed);
            
            // if it is a in line with a beat (For efficency), 
            // check for active beats and mic and play them if it is active
            if (scrollPane.getHorizontalScrollBar().getValue() % 100 == 0){
            	
                if (scrollPane.getHorizontalScrollBar().getValue() == mic.getX() && mic.getActivity()){
                    mic.makeSound();
                }
                
                spot = scrollPane.getHorizontalScrollBar().getValue()-100;
                for (int i = 0; i < counter; i++) {
                    if (instruments[i].getBeat(spot / 100).getActivity()) { // if beat is active make sound
                        instruments[i].getBeat(spot / 100).makeSound(); 
                    }
                }
                
            }
        }
    }


    public static void main(String[] args) {
    	// test with preset stuff, this tests methods and functionality
        String user = "parth";
        char accType = 'p';
        String[] fileNames = {"ceeday-huh-sound-effect.wav", "hihat.wav", "kick.wav", "snare.wav", "mic.wav"};
        String[] activity = new String[5];

        for (int i = 0; i < 4; i++) {
            activity[i] = "";
            for (int j = 0; j < 175; j++) {
                if (j%2==0) {
                    activity[i] = activity[i] + 1;
                }
                else {
                    activity[i] = activity[i] + 0;
                }
            }
        }
        activity[4] = "0";

        new BeatMaker(user, accType, fileNames, activity);
    }

}
