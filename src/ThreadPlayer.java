/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class ThreadPlayer extends Thread {

    private String strAudio;

    public void run() {

        //** add this into your application code as appropriate
// Open an input stream  to the audio file.
        try {
            FileInputStream fis = new FileInputStream(strAudio);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Player player = new Player(bis);
            player.play();
        } catch (Exception ex) {
            System.out.println(ex.getCause() + ex.getMessage());
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
