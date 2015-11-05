
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class Main extends JFrame
        implements ActionListener, MouseListener, KeyListener, MouseMotionListener {

    private final GamePanel gamePanel;
    private final GameData gameData;
    private final Animator animator;

    private final JTextField text;
    private final JButton stage1test;
    private final JButton cutscene1test;
    private final JButton stage2test;
    private final JButton cutscene2test;
    private final JButton stage3test;
    private final JButton quitButton;

    // button's font & Colors
    Font btnFont;
    Border borderLine;
    Color btnColor;

    private final Ship playerShip;
    private long playerMissle = 0;
    private final long firingInterval = 90;//interval between player missles
    private final char key;

    private Timer timer;
    private TimerTask task;
    
    static int powLevel = 1;

    public Main() throws IOException {
        setSize(1275, 665);//size of initial window
        setLocation(50, 100);//location of initial window
        setTitle("Galileo!!");//title of the initial window
        GameData.setphase(PHASE.ONE);
        Container c = getContentPane();//container for JPanel items
        animator = new Animator();
        gameData = new GameData();
        gamePanel = new GamePanel(animator, gameData);
        animator.setGamePanel(gamePanel);//sets the gamePanel object for animator to use
        animator.setGameData(gameData);//sets gameData object for animator to use
        c.add(gamePanel, "Center");//centers the gamePanel on the JPanel

        JPanel southPanel = new JPanel();

        key = 'p'; // for test, player want to shoot using 'p'

        text = new JTextField(50); // for test to show what is used for control spaceship
        text.setEditable(false);
        text.setText("MOUSE (default) : Control the Ship using the mouse. Click for shooting. ## PRESS 'm' TO SWITCH ");
        text.setVisible(true);
        southPanel.add(text);

        // Buttons' fonts
        btnFont = new Font("Bodoni MT Black", Font.ROMAN_BASELINE, 25);
        borderLine = new LineBorder(Color.BLUE, 5);
        btnColor = new Color(190, 175, 170);

        quitButton = new JButton("Quit");
        quitButton.setVisible(true);
        quitButton.setFont(btnFont);
        quitButton.setBorder(borderLine);
        quitButton.setBackground(btnColor);
        southPanel.add(quitButton);

        //BEGIN TEMPORARY BUTTONS
        stage1test = new JButton("Stage1");
        stage1test.setVisible(true);
        stage1test.setFont(btnFont);
        stage1test.setBorder(borderLine);
        stage1test.setBackground(btnColor);
        southPanel.add(stage1test);

        cutscene1test = new JButton("Cutscene1");
        cutscene1test.setVisible(true);
        cutscene1test.setFont(btnFont);
        cutscene1test.setBorder(borderLine);
        cutscene1test.setBackground(btnColor);
        southPanel.add(cutscene1test);

        stage2test = new JButton("Stage2");
        stage2test.setVisible(true);
        stage2test.setFont(btnFont);
        stage2test.setBorder(borderLine);
        stage2test.setBackground(btnColor);
        southPanel.add(stage2test);

        cutscene2test = new JButton("Cutscene2");
        cutscene2test.setVisible(true);
        cutscene2test.setFont(btnFont);
        cutscene2test.setBorder(borderLine);
        cutscene2test.setBackground(btnColor);
        southPanel.add(cutscene2test);

        stage3test = new JButton("Stage3");
        stage3test.setVisible(true);
        stage3test.setFont(btnFont);
        stage3test.setBorder(borderLine);
        stage3test.setBackground(btnColor);
        southPanel.add(stage3test);

        stage1test.setFocusable(false);
        stage1test.addActionListener(this);

        cutscene1test.setFocusable(false);
        cutscene1test.addActionListener(this);

        stage2test.setFocusable(false);
        stage2test.addActionListener(this);

        cutscene2test.setFocusable(false);
        cutscene2test.addActionListener(this);

        stage3test.setFocusable(false);
        stage3test.addActionListener(this);
        //END TEMPORARY BUTTONS

        c.add(southPanel, "South");

        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);

        gamePanel.setFocusable(true); // receives keyboard data
        gamePanel.addKeyListener(this);

        text.setFocusable(false);
        text.addActionListener(this);

        quitButton.addActionListener(this);
        quitButton.setFocusable(false); // "Quit" button should not receive keyboard data   

        playerShip = (Ship) gameData.figures.get(0); //gets player ship object from figures
        gamePanel.startGame();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {//controls the quit button
        if (ae.getSource() == quitButton) {
            animator.running = false;
        }
        //BEGIN TEMPRARY BUTTON LISTENERS
        if (ae.getSource() == stage1test) {
            try {
                GameData.setphase(PHASE.ONE);
                animator.gamePanel.setNextStage(1);
                animator.gamePanel.setStageChange(true);
                gameData.setStateChanged(1, false);
                Animator.endcutscene = true;
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == stage2test) {
            try {
                GameData.setphase(PHASE.TWO);
                animator.gamePanel.setNextStage(2);
                animator.gamePanel.setStageChange(true);
                gameData.setStateChanged(2, false);
                animator.startTimer();
                Animator.endcutscene = true;
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ae.getSource() == stage3test) {
            try {
                GameData.setphase(PHASE.THREE);
                animator.gamePanel.setNextStage(3);
                animator.gamePanel.setStageChange(true);
                gameData.setStateChanged(3, false);
                Animator.endcutscene = true;
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == cutscene1test) {
            try {
                animator.gamePanel.setNextCutscene(1);
                animator.gamePanel.setCutsceneChange(true);
                GameData.setphase(PHASE.TWO);
                animator.gamePanel.setNextStage(2);
                animator.gamePanel.setStageChange(true);
                gameData.setStateChanged(2, true);
                Animator.cutsceneRunning = true;
                Animator.endcutscene = false;
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (ae.getSource() == cutscene2test) {
            try {
                animator.gamePanel.setNextCutscene(2);
                animator.gamePanel.setCutsceneChange(true);
                GameData.setphase(PHASE.THREE);
                animator.gamePanel.setNextStage(3);
                animator.gamePanel.setStageChange(true);
                gameData.setStateChanged(3, true);
                Animator.cutsceneRunning = true;
                Animator.endcutscene = false;
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            //END TEMPORARY BUTTON LISTENERS
        }
    }

    private class FireTimerTask extends TimerTask {

        @Override
        public void run() {
            
            playerMissle = System.currentTimeMillis();
            if(powLevel ==1 ){
                Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), playerShip.getMyType());
                synchronized (gameData.figures) {
                    gameData.figures.add(f);
                    
                }
            }
            else if(powLevel == 2){                
                Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot()-5, playerShip.getMyType());          
                Missile g = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot()+5, playerShip.getMyType());
                            
                List <GameFigure> l = new ArrayList<>();
                l.add(f);
                l.add(g);            
                synchronized (gameData.figures) {
                    for(int i=0; i < powLevel; i++)
                        gameData.figures.add(l.get(i));                       
                }            
            }
            else if(powLevel == 3){
                Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot()-10, playerShip.getMyType());          
                Missile g = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), playerShip.getMyType());
                Missile h = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot()+10, playerShip.getMyType());

                List <GameFigure> l = new ArrayList<>();
                l.add(f);
                l.add(g);
                l.add(h);            
                synchronized (gameData.figures) {
                    for(int i=0; i < 3; i++)
                        gameData.figures.add(l.get(i));
                    
                }
            }
                     
        
        }   
    }

    //below are all of the different keyboard and action events, some are filled some are not
    @Override
    public void mousePressed(MouseEvent me) {
        if (!animator.paused) {
            timer = new Timer();
            task = new FireTimerTask();
            timer.scheduleAtFixedRate(task, 0, 90);
        }

    }

    @Override
    public void keyPressed(KeyEvent ke) {//changes player ship position and fires based on key pressed    
        if (ke.getKeyCode() == KeyEvent.VK_P) {
            animator.paused = !animator.paused;
        }

        // spaceship fires whenever the default key for shooting
        // has been changed by the player.
        if (ke.getKeyChar() == key) {
            if (!playerShip.mouseable) { // == false. meaning not mouseable
                playerMissle = System.currentTimeMillis();
                Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), playerShip.getMyType());
                synchronized (gameData.figures) {
                    gameData.figures.add(f);
                }
            }
        }

        if (!animator.paused) {
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
                        text.setText("KEYBOARD : Control the Ship using direction key.'Space' for shooting.  ## PRESS 'm' TO SWITCH ");
                        showMouse();
                    } else {
                        text.setText("MOUSE (default) : Control the Ship using the mouse. Click for shooting.  ## PRESS 'm' TO SWITCH ");
                        hideMouse();
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if (!playerShip.mouseable) { // == false. meaning not mouseable
                        if (System.currentTimeMillis() - playerMissle < firingInterval) {
                            break;
                        }
                        playerMissle = System.currentTimeMillis();
                        Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), playerShip.getMyType());
                        synchronized (gameData.figures) {
                            gameData.figures.add(f);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) { //fires a missle from the player on a normal mouse click
        if (playerShip.mouseable && !animator.paused) { // == true.        
            if (System.currentTimeMillis() - playerMissle < firingInterval) {
                return;
            }
            playerMissle = System.currentTimeMillis();
            Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), playerShip.getMyType());
            synchronized (gameData.figures) {
                gameData.figures.add(f);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (!animator.paused) {
            timer.cancel();
        }
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
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {//fires missles from the player as long as the player clicks and drags, also updates position
        if (playerShip.mouseable && !animator.paused) { // == true
            playerShip.x = e.getX();
            playerShip.y = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {//updates position based on mouse
        if (gamePanel.running) { // == true. When player start Game; meaning player click on start Button.           

            if (playerShip.mouseable && !animator.paused) {
                playerShip.x = e.getX();
                playerShip.y = e.getY();

                if (e.getX() > 0 && e.getX() < GamePanel.PWIDTH - playerShip.playerImage.getWidth(null)
                        && e.getY() > 0 && e.getY() < GamePanel.PHEIGHT - playerShip.playerImage.getHeight(null)) {
                    hideMouse();    // hide mouse cursor when cursor is in the war zone; the gamePanel area 
                } else {
                    showMouse(); // show cursor otherwise
                }
            }

        }
    }

    //==================================================================
    private void hideMouse() { // hide Cursor
        ImageIcon invisi = new ImageIcon(new byte[0]);
        Cursor invisible = getToolkit().createCustomCursor(
                invisi.getImage(), new Point(0, 0), "Hiding");
        gamePanel.setCursor(invisible);
    }

    private void showMouse() { // show Cursor
        gamePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    //==================================================================

    public static void main(String[] args) throws IOException {//runs the Main constructor that ultimately starts the entire game
        JFrame menu = new InterfaceForm();
        menu.setSize(500, 350);
        menu.setLocation(300, 100);
        menu.setResizable(false);
        menu.setVisible(true);
    }

}
