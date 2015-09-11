/*
 class that runs the thread that updates the screen
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class Animator implements Runnable {

    boolean running;
    GamePanel gamePanel = null;
    GameData gameData = null;
    private int x = 0;
    private int y = 0;
    private Timer time;

    public Animator() {
        ActionListener backgroundRender = new ActionListener() { //scrolling background
            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 1;
            }
        };
        time = new Timer(30, backgroundRender);//scrolling background timer
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void run() {
        running = true;
        time.start();
        while (running) {
            gameData.update();
            try {
                gamePanel.gameRender(x, y);
                if (x < -1296) { //scrolling background loop
                    x = 0;
                }
            } catch (IOException ex) {
                Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
            }
            gamePanel.printScreen();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            if (gameData.FINISHED) {
                gamePanel.gameOver();
                running = false;
            }
        }
        System.exit(0);
    }

}
