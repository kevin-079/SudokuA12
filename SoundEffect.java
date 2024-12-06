/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231008 - Batara Haryo Yudanto
 * 2 - 5026231079 - Kevin Nathanael
 * 3 - 5026231089 - Yusuf Acala Sadurjaya Sri Krisna
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundEffect {
    public void playSound(String soundPath) {

        try {
            File sound = new File(soundPath);

            if(sound.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(sound);
                Clip clip = AudioSystem.getClip();

                clip.open(audioInput);
                clip.start();
            }else {
                System.out.println("Can't find sound file");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
