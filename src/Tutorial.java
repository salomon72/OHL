
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import static java.awt.SystemColor.text;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davidalba
 */
public class Tutorial extends JFrame
        implements ActionListener, MouseListener, KeyListener, MouseMotionListener {
    //private final GamePanel gamePanel;
    private final TutorialPanel tutorialPanel;
     private final TutorialGameData gameData;
     
    //private final Animator animator;
    private final TutorialAnimator tutorialAnimator;
    private JTextField text;
    private JTextField text2;
    private final JButton quitButton;
    private final JButton back;
    
    Font btnFont;
    Border borderLine;
    Color btnColor;
    
     private final char key;
      private final TutorialShip playerShip;
    private long playerMissle = 0;
    private final long firingInterval = 90;
    
    private Timer timer;
    private TimerTask task;
    
     
    
    public Tutorial() throws IOException {
        setSize(1275, 650);//size of initial window /1245,960
        setLocation(50, 100);//location of initial window
        setTitle("Galileo Tutorial");//title of the initial window
        Container c = getContentPane();//container for JPanel items
        //animator = new Animator();
        gameData = new TutorialGameData();
       tutorialAnimator = new TutorialAnimator();
        tutorialPanel = new TutorialPanel(tutorialAnimator, gameData); //animator, gameData
        tutorialAnimator.setTutorialPanel(tutorialPanel);//sets the gamePanel object for animator to use //animator.setTutorialPanel(tutorialPanel)
        tutorialAnimator.setGameData(gameData);//sets gameData object for animator to us //animator.setGameData(gameData)
        c.add(tutorialPanel, "Center");//centers the gamePanel on the JPanel
        
        
        JPanel southPanel = new JPanel();
        
        key = 'p';
        
        /*text = new JTextField(20); // for test to show what is used for control spaceship
        text.setEditable(false);
        text.setText("Use the mouse to move ship around");
        text.setVisible(true);
        southPanel.add(text);*/
        
        /*text = new JTextField(20); // for test to show what is used for control spaceship
        text.setEditable(false);
        text.setText("Click the mouse to fire the missle");
        text.setVisible(true);
        southPanel.add(text);*/
       
        
        
       /* text2 = new JTextField(45);
        text2.setEditable(false);
        text2.setText("You can also use the keyboard by pressing m to switch and pressing space to fire");
        text2.setVisible(true);
        southPanel.add(text2);*/
        
     btnFont = new Font("Bodoni MT Black", Font.ROMAN_BASELINE, 25);
        borderLine =new LineBorder(Color.BLUE, 5);
        btnColor = new Color(190, 175, 170);
        
        back = new JButton("Return");
        back.setVisible(true);
        back.setFont(btnFont);
        back.setBorder(borderLine);
        back.setBackground(btnColor);
        southPanel.add(back);
        
        
        btnFont = new Font("Bodoni MT Black", Font.ROMAN_BASELINE, 25);
        borderLine = new LineBorder(Color.BLUE, 5);
        btnColor = new Color(190, 175, 170);
        
        
        
        quitButton = new JButton("Quit");
        quitButton.setVisible(true);
        quitButton.setFont(btnFont);
        quitButton.setBorder(borderLine);
        quitButton.setBackground(btnColor);
        southPanel.add(quitButton);
        
       
        
        tutorialPanel.addMouseListener(this);
        tutorialPanel.addMouseMotionListener(this);

        tutorialPanel.setFocusable(true); // receives keyboard data
        tutorialPanel.addKeyListener(this);
        
        //text.setFocusable(false);
        //text.addActionListener(this);
        
        
        
         quitButton.addActionListener(this);
        quitButton.setFocusable(false);
        
          back.addActionListener(this);
          back.setFocusable(false);
        
        c.add(southPanel, "South");
        
        playerShip = (TutorialShip) gameData.figures.get(0);
        tutorialPanel.startTutorial();
       
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == quitButton) {
          tutorialAnimator.running = false; //animator.running = false;
        }
        if (ae.getSource() == back) {
           
            
            dispose();
      
     
        }//To change body of generated methods, choose Tools | Templates.
    }
    
    private class FireTimerTask extends TimerTask {

        @Override
        public void run() {
            playerMissle = System.currentTimeMillis();
            TutorialMissile f = new TutorialMissile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), 1);
            synchronized (gameData.figures) {
                gameData.figures.add(f);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
       if (playerShip.mouseable) { // == true.        
            if (System.currentTimeMillis() - playerMissle < firingInterval) {
                return;
            }
            playerMissle = System.currentTimeMillis();
            TutorialMissile f = new TutorialMissile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), 1);
            synchronized (gameData.figures) {
                gameData.figures.add(f);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        timer = new Timer();
        task = new Tutorial.FireTimerTask();
        timer.scheduleAtFixedRate(task, 0, 90);
        }

    @Override
    public void mouseReleased(MouseEvent me) {
       timer.cancel();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
       
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void keyTyped(KeyEvent ke) {
       
    }

    @Override
    public void keyPressed(KeyEvent ke) {
         if (ke.getKeyChar() == key) {
            if (!playerShip.mouseable) { // == false. meaning not mouseable
                playerMissle = System.currentTimeMillis();
                TutorialMissile f = new TutorialMissile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), 1);
                synchronized (gameData.figures) {
                    gameData.figures.add(f);
                }
            }
        }

        switch (ke.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (!playerShip.mouseable) {
                    playerShip.y -= 25;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!playerShip.mouseable) {
                    playerShip.y += 25;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!playerShip.mouseable) {
                    playerShip.x -= 25;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!playerShip.mouseable) {
                    playerShip.x += 25;
                }
                break;
            case KeyEvent.VK_M: // activate or de-activate mouse control
                playerShip.mouseable = !playerShip.mouseable;

                // Notify the user about the changes%%
                if (!playerShip.mouseable) {
                    //text.setText("KEYBOARD : Control the Ship using direction key.'Space' for shooting.  ## PRESS 'm' TO SWITCH ");
                    showMouse();
                } else {
                    //text.setText("MOUSE (default) : Control the Ship using the mouse. Click for shooting.  ## PRESS 'm' TO SWITCH ");
                    hideMouse();
                }
                break;
            case KeyEvent.VK_SPACE:
                if (!playerShip.mouseable) { // == false. meaning not mouseable
                    if (System.currentTimeMillis() - playerMissle < firingInterval) {
                        break;
                    }
                    playerMissle = System.currentTimeMillis();
                    TutorialMissile f = new TutorialMissile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), 1);
                    synchronized (gameData.figures) {
                        gameData.figures.add(f);
                    }
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      
    }

    @Override
    public void mouseDragged(MouseEvent me) {
         if (playerShip.mouseable) { // == true
            playerShip.x = me.getX();
            playerShip.y = me.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
       if (tutorialPanel.running) { // == true. When player start Game; meaning player click on start Button.           

            if (playerShip.mouseable) { // == true

                playerShip.x = me.getX();
                playerShip.y = me.getY();

                if (me.getX() > 0 && me.getX() < GamePanel.PWIDTH - playerShip.playerImage.getWidth(null)
                        && me.getY() > 0 && me.getY() < GamePanel.PHEIGHT - playerShip.playerImage.getHeight(null)) {
                    hideMouse();    // hide mouse cursor when cursor is in the war zone; the gamePanel area 
                } else {
                    showMouse(); // show cursor otherwise
                }
            }

        }
    }
    
    private void hideMouse() { // hide Cursor
        ImageIcon invisi = new ImageIcon(new byte[0]);
        Cursor invisible = getToolkit().createCustomCursor(
                invisi.getImage(), new Point(0, 0), "Hiding");
        tutorialPanel.setCursor(invisible);
    }

    private void showMouse() { // show Cursor
        tutorialPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
