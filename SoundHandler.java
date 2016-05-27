import java.io.*;
import javax.sound.sampled.*;

// Handle sounds

public class SoundHandler {

	// invoke a new runnable for sounds
	void playSound(File theSound) {
	    // with the runnable, amend internal methods.
		Runnable soundThread = new Runnable() {
	        private File theSound;
	        public void run() {
	        	playSoundClip(this.theSound);
	        }
	        public Runnable setFile(File theSound) {
	            this.theSound = theSound;
	            return this;
	        }
	    }.setFile(theSound);
	    // Throw the sound to a new thread to stop hangups.
	    new Thread(soundThread).start();
	}

	
	// Play a soundClip
	void playSoundClip(File theSound) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(theSound);
	        try {
	            Clip clip = AudioSystem.getClip();
	            clip.open(audioInputStream);
	            // ATTEMPT to play - factorise out, as this can go horribly wrong.
	            attemptPlay(clip);
	        } catch (LineUnavailableException e) { e.printStackTrace(); } 
	        finally { audioInputStream.close(); }
	    } catch (Exception e) { e.printStackTrace(); } 
	}
	
	// Try to play, but bail if all goes wrong!
	private void attemptPlay(Clip clip) {
		try {
            clip.start();
            try {
            	// A 5 second sleep should allow suitable time for the program to complete
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clip.drain();
        } finally {
            clip.close();
        }
	}
	
}
