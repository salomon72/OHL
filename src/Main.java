
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

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
import java.awt.image.BufferedImage;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Main extends JFrame
        implements ActionListener, MouseListener, KeyListener, MouseMotionListener, MouseWheelListener {

    private final GamePanel gamePanel;
    final GameData gameData;
    private final Animator animator;

    // sound players
    private final SoundPlayer soundPlayerMusic;
    private final JTextField text;
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

    static int missileLevel = 1;

    public Main() throws IOException {
        setSize(1275, 665);//size of initial window
        setLocation(50, 100);//location of initial window
        setTitle("Galileo!!");//title of the initial window
        GameData.setphase(PHASE.ONE);
        Container c = getContentPane();//container for JPanel items
        animator = new Animator();
        gameData = new GameData();
        animator.setGameData(gameData);//sets gameData object for animator to use
        gamePanel = new GamePanel(animator, gameData);
        animator.setGamePanel(gamePanel);//sets the gamePanel object for animator to use
        soundPlayerMusic = new SoundPlayer("images/Shovel.wav");
        soundPlayerMusic.loop();

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

        c.add(southPanel, "South");

        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);
        gamePanel.addMouseWheelListener(this);

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
            Animator.running = false;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (Bonus.active && Bonus.powerLevel >= 1) {
            if (e.getWheelRotation() > 0) { // if wheel goes UP
                Shield.count += 3;
                Bonus.powerLevel -= 1;

            } else { //if wheel goes DOWN                
                if (Bonus.powerLevel >= 2 && !Bonus.slowMotion) { // start slow motion if not yet activated 
                    Bonus.slowTimer = System.nanoTime();
                    Bonus.slowMotion = true;
                    Bonus.powerLevel -= 2;
                }
            }
        }
    }

    private class FireTimerTask extends TimerTask {

        @Override
        public void run() {
            playerMissle = System.currentTimeMillis();
            if (missileLevel == 1) {
                Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), playerShip.getMyType());
                synchronized (gameData.figures) {
                    gameData.figures.add(f);
                }
            }
            if (missileLevel == 2) {
                Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot() - 7, playerShip.getMyType());
                Missile g = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot() + 7, playerShip.getMyType());

                List<GameFigure> l = new ArrayList<>();
                l.add(f);
                l.add(g);
                synchronized (gameData.figures) {
                    for (int i = 0; i < 2; i++) {
                        gameData.figures.add(l.get(i));
                    }
                }
            } else if (missileLevel == 3 && GameData.getphase() == PHASE.TWO || GameData.getphase() == PHASE.THREE) {
                Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot() - 15, playerShip.getMyType());
                Missile g = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot(), playerShip.getMyType());
                Missile h = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot() + 15, playerShip.getMyType());

                List<GameFigure> l = new ArrayList<>();
                l.add(f);
                l.add(g);
                l.add(h);
                synchronized (gameData.figures) {
                    for (int i = 0; i < 3; i++) {
                        gameData.figures.add(l.get(i));
                    }

                }
            }

        }
    }

    //below are all of the different keyboard and action events, some are filled some are not
    @Override
    public synchronized void mousePressed(MouseEvent me) {
        if (!Animator.paused) {
            timer = new Timer();
            task = new FireTimerTask();
            timer.scheduleAtFixedRate(task, 0, 90);
        }

    }

    @Override
    public void keyPressed(KeyEvent ke) {//changes player ship position and fires based on key pressed    
        if (ke.getKeyCode() == KeyEvent.VK_P) {
            Animator.paused = !Animator.paused;
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

        if (!Animator.paused) {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (!playerShip.mouseable) {
                        Ship.y -= 25;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!playerShip.mouseable) {
                        Ship.y += 25;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (!playerShip.mouseable) {
                        Ship.x -= 25;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!playerShip.mouseable) {
                        Ship.x += 25;
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
        if (playerShip.mouseable && !Animator.paused) { // == true.        
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
        if (!Animator.paused) {
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
        if (playerShip.mouseable && !Animator.paused) { // == true
            Ship.x = e.getX();
            Ship.y = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {//updates position based on mouse
        if (Animator.running) { // == true. When player start Game; meaning player click on start Button.           

            if (playerShip.mouseable && !Animator.paused) {
                Ship.x = e.getX();
                Ship.y = e.getY();
                if (e.getX() > 0 && e.getX() < GamePanel.PWIDTH - Ship.playerImage.getWidth(null)
                        && e.getY() > 0 && e.getY() < GamePanel.PHEIGHT - Ship.playerImage.getHeight(null)) {
                    hideMouse();    // hide mouse cursor when cursor is in the war zone; the gamePanel area 
                } else {
                    showMouse(); // show cursor otherwise
                }
            }

        }
    }

    private void hideMouse() { // hide Cursor
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor invisible = getToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "Hiding");
        gamePanel.setCursor(invisible);
    }

    private void showMouse() { // show Cursor
        gamePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public static void main(String[] args) throws IOException {//runs the Main constructor that ultimately starts the entire game
        JFrame menu = new StartUpMenu();
        menu.setResizable(false);
        menu.setVisible(true);
    }

}
