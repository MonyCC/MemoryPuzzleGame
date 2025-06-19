package application.util;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class SoundUtil {

    public static void play(String filename) {
        try {
            URL url = SoundUtil.class.getResource("/application/assets/audio/" + filename);
            if (url == null) {
                System.err.println("Sound file not found: " + filename);
                return;
            }
            AudioClip clip = new AudioClip(url.toExternalForm());
            clip.play();
        } catch (Exception e) {
            System.err.println("Failed to play sound: " + filename);
            e.printStackTrace();
        }
    }

    public static AudioClip loopMusic(String filename) {
        try {
            URL url = SoundUtil.class.getResource("/application/assets/audio/" + filename);
            if (url == null) {
                System.err.println("Sound file not found: " + filename);
                return null;
            }
            AudioClip clip = new AudioClip(url.toExternalForm());
            clip.setCycleCount(AudioClip.INDEFINITE);
            clip.play();
            return clip;
        } catch (Exception e) {
            System.err.println("Failed to loop sound: " + filename);
            e.printStackTrace();
            return null;
        }
    }
}
