package dev.madlabcoffee.services;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioService {
    private String MUSIC = "assets/audio/SwayThisWay.wav";
    private String SOUND = "assets/audio/collision.wav";
    private Clip music;
    private Clip sound;
    public AudioService() {
        restartMusic();
    }  // End of the 'Constructor'

    private Clip initAudio(String path) {
        try {
            AudioInputStream audioIn = AudioSystem
                    .getAudioInputStream(new File(path).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (IOException exc) {
            System.out.println("Failed to load the audio: " + exc.getMessage());
        } catch (UnsupportedAudioFileException exc) {
            System.out.println("Unsupported audio type: " + exc.getMessage());
        } catch (LineUnavailableException exc) {
            System.out.println("Audio unavailable: " + exc.getMessage());
        }
        return null;
    }  // End of the 'initAudio' method

    public boolean hasMusic() {
        return music != null;
    }  // End of the 'hasMusic' method

    public void pauseMusic() {
        music.stop();
    }  // End of the 'pauseMusic' method

    public void resumeMusic() {
        music.start();
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }  // End of the 'resumeMusic' method

    public void restartMusic() {
        music = initAudio(MUSIC);
        assert music != null;
        music.start();
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }  // End of the 'restartMusic' method

    public void playSound() {
        sound = initAudio(SOUND);
        if (sound != null) {
            sound.start();
        } else {
            System.out.println("Failed to load sound");
        }
    }  // End of the 'playSound' method
}  // End of the 'AudioService' class

// END OF FILE
