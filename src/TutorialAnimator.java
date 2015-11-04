
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TutorialAnimator implements Runnable {

    boolean running;
    TutorialGameData gameData = null;
    TutorialPanel tutorialPanel = null;
    private int x = 0;
    private int y = 0;

    public TutorialAnimator() {
        ActionListener backgroundRender = new ActionListener() { //scrolling background
            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 1;
            }
        };
    }

    public void setTutorialPanel(TutorialPanel tutorialPanel) {
        this.tutorialPanel = tutorialPanel;

    }

    public void setGameData(TutorialGameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            gameData.update();
            try {
                tutorialPanel.gameRender(x, y);

            } catch (IOException ex) {
                Logger.getLogger(TutorialAnimator.class.getName()).log(Level.SEVERE, null, ex);
            }
            tutorialPanel.printScreen();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }

            if (gameData.FINISHED) {
                running = false;
            }

        }

        System.exit(0);

    }
}
