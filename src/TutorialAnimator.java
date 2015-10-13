/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davidalba
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class TutorialAnimator implements Runnable {
     boolean running;
    //GamePanel gamePanel = null;
    TutorialGameData gameData = null;
    TutorialPanel tutorialPanel = null;
    private int x = 0;
    private int y = 0;
    private final Timer backgroundScrollTimer;
    //private final Timer planetScaleTimer;

    public TutorialAnimator() {
        ActionListener backgroundRender = new ActionListener() { //scrolling background
            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 1;
            }
        };
     
        backgroundScrollTimer = new Timer(30, backgroundRender);//scrolling background timer
        //planetScaleTimer = new Timer(100, planetScale);
    }

   

    public void setTutorialPanel(TutorialPanel tutorialPanel){
        this.tutorialPanel = tutorialPanel;
        
    }
    public void setGameData(TutorialGameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void run() {
        running = true;
        //backgroundScrollTimer.start();
        //planetScaleTimer.start();
        while (running) {
            gameData.update();
            try {
                //gamePanel.gameRender(x, y);
                tutorialPanel.gameRender(x, y);
                /*if (x < -tutorialPanel.getCurrentStage().getBackgroundWidth()) {
                    x = 0;
                }*/
                
            } catch (IOException ex) {
                Logger.getLogger(TutorialAnimator.class.getName()).log(Level.SEVERE, null, ex);
            }
            //gamePanel.printScreen();
            tutorialPanel.printScreen();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
             
            
            if (gameData.FINISHED) {
                //gamePanel.gameOver();
                running = false;
            }
            
           
        }
        
     
        
        
        System.exit(0);
        
        
        
    }
}
