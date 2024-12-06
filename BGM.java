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

public class BGM {
	public void BGMusic(String BGMPath) {

		try {
			File backsound = new File(BGMPath);

			if(backsound.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(backsound);
				Clip clip = AudioSystem.getClip();

				clip.open(audioInput);
				clip.loop(clip.LOOP_CONTINUOUSLY);
				clip.start();
			}else {
				System.out.println("Can't find BGM file");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}


}