
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davidalba
 */
public class DisplayAnimator implements Runnable {
    
    boolean running;
    DisplayGameData displayGameData = null;
    DisplayPanel displayPanel = null;
    private int x = 0;
    private int y = 0;
    @Override
     
    public void run() {
         running = true;
        while (running) {
            displayGameData.update();
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

        System.exit(0);

    }
    
    
    public void setDisplayPanel(DisplayPanel displayPanel) {
        this.displayPanel = displayPanel;

    }

    public void setDisplayGameData(DisplayGameData displayGameData) {
        this.displayGameData = displayGameData;
    }

   
}
