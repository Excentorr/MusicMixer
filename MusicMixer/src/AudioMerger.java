import static java.lang.Math.ceil;
import static java.lang.Math.round;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

// PARTH
// NOT MY CODE I GOT IT FROM A GUY ON GITHUB:
// https://stackoverflow.com/a/70537485/22589268
public class AudioMerger {

    private short audioFormat = 1;
    private int sampleRate = 44100;
    private short sampleSize = 16;
    private short channels = 2;
    private short blockAlign = (short) (sampleSize * channels / 8);
    private int byteRate = sampleRate * sampleSize * channels / 8;
    private ByteBuffer audioBuffer;
    private ArrayList<MergeSound> sounds = new ArrayList<MergeSound>();
    private ArrayList<Integer> offsets = new ArrayList<Integer>();

    public void addSound(double offsetInSeconds, MergeSound sound) {

        if (sound.getAudioFormat() != audioFormat)
            new RuntimeException("Incompatible AudioFormat");
        if (sound.getSampleRate() != sampleRate)
            new RuntimeException("Incompatible SampleRate");
        if (sound.getSampleSize() != sampleSize)
            new RuntimeException("Incompatible SampleSize");
        if (sound.getChannels() != channels)
            new RuntimeException("Incompatible amount of Channels");

        int offset = secondsToByte(offsetInSeconds);
        offset = offset % 2 == 0 ? offset : offset + 1;// ensure we start at short when merging

        sounds.add(sound);
        offsets.add(offset);
    }

    public void merge(double durationInSeconds) {
        audioBuffer = ByteBuffer.allocate(secondsToByte(durationInSeconds));

        for (int i = 0; i < sounds.size(); i++) {

            ByteBuffer buffer = sounds.get(i).getBuffer();
            int offset1 = offsets.get(i);

            // iterate over all sound data to append it
            while (buffer.hasRemaining()) {

                int position = offset1 + buffer.position();// the global position in audioBuffer

                // exit if audio plays after end
                if (position >= audioBuffer.capacity())
                    return;

                // add the audio data to the vars
                short sum = Short.reverseBytes(buffer.getShort());
                int matches = 1;

                // make sure later entries dont override the previsously merged
                //continue only if theres empty audio data
                if (audioBuffer.getShort(position) == 0) {

                    // iterate over the other sounds and check if the need to be merged
                    for (int j = i + 1; j < sounds.size(); j++) {// set j to i+1 to avoid all previous
                        ByteBuffer mergeBuffer = sounds.get(j).getBuffer();
                        int mergeOffset = offsets.get(j);

                        // check if this soundfile contains data that has to be merged
                        if (position >= mergeOffset && position < mergeOffset + mergeBuffer.capacity()) {
                            sum += Short.reverseBytes(mergeBuffer.getShort(position - mergeOffset));
                            matches++;
                        }
                    }
//make sure to cast to float 3/1=1 BUT round(3/1f)=2 for example
                    audioBuffer.putShort(position, Short.reverseBytes((short) round(sum / (float) matches)));
                }
            }
            buffer.rewind();// So the sound can be added again
        }
    }

    private int secondsToByte(double seconds) {
        return (int) ceil(seconds * byteRate);
    }

    public void saveToFile(File file) throws IOException {

        byte[] audioData = audioBuffer.array();

        int audioSize = audioData.length;
        int fileSize = audioSize + 44;

        // The stream that writes the audio file to the disk
        DataOutputStream out = new DataOutputStream(new FileOutputStream(file));

        // Write Header
        out.writeBytes("RIFF");// 0-4 ChunkId always RIFF
        out.writeInt(Integer.reverseBytes(fileSize));// 5-8 ChunkSize always audio-length +header-length(44)
        out.writeBytes("WAVE");// 9-12 Format always WAVE
        out.writeBytes("fmt ");// 13-16 Subchunk1 ID always "fmt " with trailing whitespace
        out.writeInt(Integer.reverseBytes(16)); // 17-20 Subchunk1 Size always 16
        out.writeShort(Short.reverseBytes(audioFormat));// 21-22 Audio-Format 1 for PCM PulseAudio
        out.writeShort(Short.reverseBytes(channels));// 23-24 Num-Channels 1 for mono, 2 for stereo
        out.writeInt(Integer.reverseBytes(sampleRate));// 25-28 Sample-Rate
        out.writeInt(Integer.reverseBytes(byteRate));// 29-32 Byte Rate
        out.writeShort(Short.reverseBytes(blockAlign));// 33-34 Block Align
        out.writeShort(Short.reverseBytes(sampleSize));// 35-36 Bits-Per-Sample
        out.writeBytes("data");// 37-40 Subchunk2 ID always data
        out.writeInt(Integer.reverseBytes(audioSize));// 41-44 Subchunk 2 Size audio-length

        out.write(audioData);// append the merged data
        out.close();// close the stream properly
    }

}