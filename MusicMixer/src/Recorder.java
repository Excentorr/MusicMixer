import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

/**
 *
 */

/**
 * @author PARTH
 *
 * // NOT MY CODE I JUST USE IT
 * // PROMPTS TO START RECORDING AND ONCE STARTED
 * // BASICALLY KEEP RECORDING AUDIO FROM A MIC UNTIL THEY STOP
 * // IF A ERORR HAPPENS THEN SAY NOT SUPPORTED MIC
 * //https://www.youtube.com/watch?v=WSyTrdjKeqQ
 */
public class Recorder {
	public void record(String fileName) {
		try {
			AudioFormat af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

			DataLine.Info dataInfo  = new  DataLine.Info(TargetDataLine.class, af);

			if (!AudioSystem.isLineSupported(dataInfo)) {
				JOptionPane.showMessageDialog(null, "Microphone not supported");
				return;
			}

			TargetDataLine targetLine = (TargetDataLine)AudioSystem.getLine(dataInfo);
			targetLine.open();

			JOptionPane.showMessageDialog(null, "Press OK to start recording");

			targetLine.start();

			Thread audioRecorderThread = new Thread() {
				@Override public void run()
				{
					AudioInputStream rS = new AudioInputStream(targetLine);
					File outputFile = new File("sounds/"+fileName);
					try {
						AudioSystem.write(rS, AudioFileFormat.Type.WAVE, outputFile);
					}
					catch(Exception e1) {

					}
				}
			};

			audioRecorderThread.start();
			JOptionPane.showMessageDialog(null, "Press OK to stop recording");
			targetLine.stop();
			targetLine.close();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Microphone not supported");
		}
	}



	public static void main(String[] args) {
		Recorder r = new Recorder();
		r.record("testRecording.wav");
	}
}