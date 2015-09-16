
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
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
<<<<<<< HEAD
//hi
=======
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

>>>>>>> origin/master
public class Main extends JFrame
        implements ActionListener, MouseListener, KeyListener, MouseMotionListener {

    private final GamePanel gamePanel;
    private final GameData gameData;
    private final Animator animator;

    private final JTextField text;
    private final JButton startButton;
    private final JButton restartButton;
    //private final JButton SettingsButton;
    private final JButton quitButton;

    // button's font & Colors
    Font btnFont;
    Border borderLine;
    Color btnColor;

    private final Ship playerShip;
    private long playerMissle = 0;
    private final long firingInterval = 40;//interval between player missles
    private final char key;

    private Timer timer = new Timer();
    private TimerTask task = new FireTimerTask();

    public Main() throws IOException {
        setSize(1275, 610);//size of initial window
        setLocation(50, 100);//location of initial window
        setTitle("Galileo!!");//title of the initial window
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
        text.setVisible(false);
        southPanel.add(text);

        // Buttons' fonts
        btnFont = new Font("Bodoni MT Black", Font.ROMAN_BASELINE, 25);
        borderLine = new LineBorder(Color.BLUE, 5);
        btnColor = new Color(190, 175, 170);

        startButton = new JButton("Start Game");
        startButton.setFont(btnFont);
        startButton.setBorder(borderLine);
        startButton.setBackground(btnColor);
        southPanel.add(startButton);

        restartButton = new JButton("ReStart Game");
        restartButton.setVisible(false);
        restartButton.setFont(btnFont);
        restartButton.setBorder(borderLine);
        restartButton.setBackground(btnColor);
        southPanel.add(restartButton);

        quitButton = new JButton("Quit");
        quitButton.setVisible(false);
        quitButton.setFont(btnFont);
        quitButton.setBorder(borderLine);
        quitButton.setBackground(btnColor);
        southPanel.add(quitButton);
        c.add(southPanel, "South");

        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);

        gamePanel.setFocusable(true); // receives keyboard data
        gamePanel.addKeyListener(this);

        text.setFocusable(false);
        text.addActionListener(this);

        startButton.setFocusable(false);
        startButton.addActionListener(this);

        restartButton.setFocusable(false);
        restartButton.addActionListener(this);

        quitButton.addActionListener(this);
        quitButton.setFocusable(false); // "Quit" button should not receive keyboard data   

        playerShip = (Ship) gameData.figures.get(0); //gets player ship object from figures

    }

    @Override
    public void actionPerformed(ActionEvent ae) {//controls the quit button
        if (ae.getSource() == startButton) { // start game.
            gamePanel.startGame();
            text.setVisible(true);
            startButton.setVisible(false);
            //restartButton.setVisible(true);
            quitButton.setVisible(true);
        } /* else if (ae.getSource() == restartButton) { // restart game.
         gameData.reset();
                        
         } */

        if (ae.getSource() == quitButton) {
            animator.running = false;
        }
    }

    private class FireTimerTask extends TimerTask {

        @Override
        public void run() {
            playerMissle = System.currentTimeMillis();
            Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot());
            synchronized (gameData.figures) {
                gameData.figures.add(f);
            }
        }
    }

    //below are all of the different keyboard and action events, some are filled some are not
    @Override
    public void mousePressed(MouseEvent me) {
        timer.scheduleAtFixedRate(task, 0, 40);
    }

    @Override
    public void keyPressed(KeyEvent ke) {//changes player ship position and fires based on key pressed
        // spaceship fires whenever the default key for shooting
        // has been changed by the player.
        if (ke.getKeyChar() == key) {
            if (!playerShip.mouseable) { // == false. meaning not mouseable
                playerMissle = System.currentTimeMillis();
                Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot());
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
                    Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot());
                    synchronized (gameData.figures) {
                        gameData.figures.add(f);
                    }
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) { //fires a missle from the player on a normal mouse click
        if (playerShip.mouseable) { // == true.        
            if (System.currentTimeMillis() - playerMissle < firingInterval) {
                return;
            }
            playerMissle = System.currentTimeMillis();
            Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot());
            synchronized (gameData.figures) {
                gameData.figures.add(f);
            }
        }
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
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {//fires missles from the player as long as the player clicks and drags, also updates position
        if (playerShip.mouseable) { // == true
            playerShip.x = e.getX();
            playerShip.y = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {//updates position based on mouse
        if (gamePanel.running) { // == true. When player start Game; meaning player click on start Button.           

            if (playerShip.mouseable) { // == true

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
       /* JFrame game = new Main();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setResizable(false);
        game.setVisible(true);*/
        
        JFrame menu = new InterfaceForm();
        menu.setSize(500, 350);
        menu.setLocation(300,100);
        menu.setResizable(false);
        menu.setVisible(true);
    }

}
