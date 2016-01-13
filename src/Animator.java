/*
 class that runs the thread that updates the screen
 */
/*hhg*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class Animator implements Runnable {

    volatile static boolean running;
    volatile static boolean cutsceneRunning = false;
    volatile static boolean endcutscene = false;
    GamePanel gamePanel = null;
    GameData gameData = null;
    volatile static boolean paused = false;
    private int x = 0;
    private int y = 0;
    public Timer backgroundScrollTimer; //private
    public Timer planetScaleTimer; //private
    ActionListener backgroundRender;
    ActionListener planetScale;

    public Animator() {
        backgroundRender = (ActionEvent e) -> {
            x -= 1;
        } //scrolling background
                ;
        planetScale = (ActionEvent e) -> {
            gamePanel.transformPlanet();
        } //scrolling background
                ;
        backgroundScrollTimer = new Timer(30, backgroundRender);//scrolling background timer
        planetScaleTimer = new Timer(100, planetScale);

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
        backgroundScrollTimer.start();
        planetScaleTimer.start();
        while (running && !Thread.interrupted()) {

            if (!paused) { // implement all methods if & only if the game is not paused. 
                if (!cutsceneRunning) {
                    gameData.update();
                    backgroundScrollTimer.start();
                    planetScaleTimer.start();
                    try {
                        gamePanel.gameRender(x, y);
                        if (x < -gamePanel.getCurrentStage().getBackgroundWidth()) { //scrolling background loop
                            x = 0;
                        }
                    } catch (IOException ex) {
                    }

                    try {
                        Thread.sleep(13);
                        gamePanel.printScreen();
                    } catch (InterruptedException e) {

                    }
                } else {
                    if (!endcutscene) {
                        try {
                            backgroundScrollTimer.stop();
                            planetScaleTimer.stop();
                            x = 0;
                            gameData.update();
                            gamePanel.gameRender(x, y);
                            try {
                                Thread.sleep(14);
                                gamePanel.printScreen();
                            } catch (InterruptedException e) {

                            }
                        } catch (IOException ex) {
                            Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);

                        }
                    } else {
                        endcutscene = true;
                        cutsceneRunning = false;
                        backgroundScrollTimer.start();
                    }
                }
            } else { // stop timers when the game is paused
                backgroundScrollTimer.stop();
                planetScaleTimer.stop();
                gamePanel.GamePaused();
            }
            if (gameData.FINISHED) {
                if (Ship.health < 1) {
                    try {
                        gamePanel.gameOver();
                        gameData.FINISHED = false;
                        Ship.health = 5;
                    } catch (IOException ex) {
                        Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        gamePanel.gameWin();
                        if (!gameData.gameEnd) {
                            gameData.FINISHED = false;
                        } else {
                            running = false;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        System.exit(0);
    }

    public void startTimer() {
        backgroundScrollTimer.start();
    }
}
