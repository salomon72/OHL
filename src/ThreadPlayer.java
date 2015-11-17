
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.player.Player;

public class ThreadPlayer extends Thread {

    private String strAudio;

    public void run() {
        try {
            FileInputStream fis = new FileInputStream(strAudio);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Player player = new Player(bis);
            player.play();
        } catch (FileNotFoundException | JavaLayerException ex) {
        }

    }

    public ThreadPlayer() {
        strAudio = "";
    }

    public ThreadPlayer(String strPath) {
        strAudio = strPath;
    }

    public static void play(String strPath) {
        ThreadPlayer player = new ThreadPlayer(strPath);
        player.start();
    }

    public static void main(String args[]) {

    }

}
