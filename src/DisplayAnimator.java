
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DisplayAnimator implements Runnable {

    static boolean running;
    DisplayGameData displayGameData = null;
    DisplayPanel displayPanel = null;
    private final int x = 0;
    private final int y = 0;

    @Override

    public void run() {
        running = true;
        while (running) {
            try {
                displayPanel.gameRender(x, y);

            } catch (IOException ex) {
                Logger.getLogger(TutorialAnimator.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayPanel.printScreen();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    public void setDisplayPanel(DisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
    }

    public void setDisplayGameData(DisplayGameData displayGameData) {
        this.displayGameData = displayGameData;
    }
}
