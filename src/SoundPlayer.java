
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer { 
  private Clip clip;
  
  // constructor
  public SoundPlayer(String file) {
    try {
      AudioInputStream input;

      // get audiosystem clip
      clip = AudioSystem.getClip();

      // open audio input stream
      input = AudioSystem.getAudioInputStream(new File(file));
      
      // open clip
      clip.open(input);
    } catch (Exception e) {
      System.err.println("ERROR > unable to play sound file");    
      e.printStackTrace();
    }
  }
  
  // play sound
  public void play() {
    clip.setFramePosition(0);
    clip.loop(0);
    clip.start();
  }
  
  // loop sound
  public void loop() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  // stop sound
  public void stop() {
    clip.stop();
  }
}

