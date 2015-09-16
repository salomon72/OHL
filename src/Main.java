
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
//hi
public class Main extends JFrame
        implements ActionListener, MouseListener, KeyListener, MouseMotionListener {

    private final GamePanel gamePanel;
    private final GameData gameData;
    private final Animator animator;
    private final JButton quitButton;
    private final Ship playerShip;
    private long playerMissle = 0;
    private final long firingInterval = 40;//interval between player missles

    public Main() throws IOException {
        setSize(1275, 610);//size of initial window
        setLocation(100, 100);//location of initial window
        setTitle("Galileo!!");//title of the initial window
        Container c = getContentPane();//container for JPanel items
        animator = new Animator();
        gameData = new GameData();
        gamePanel = new GamePanel(animator, gameData);
        animator.setGamePanel(gamePanel);//sets the gamePanel object for animator to use
        animator.setGameData(gameData);//sets gameData object for animator to use
        c.add(gamePanel, "Center");//centers the gamePanel on the JPanel

        JPanel southPanel = new JPanel();

        quitButton = new JButton("Quit");
        southPanel.add(quitButton);
        c.add(southPanel, "South");

        gamePanel.addMouseListener(this);
        gamePanel.setFocusable(true); // receives keyboard data
        gamePanel.addKeyListener(this);
        quitButton.addActionListener(this);
        gamePanel.addMouseMotionListener(this);
        playerShip = (Ship) gameData.figures.get(0); //gets player ship object from figures
        gamePanel.startGame();//starts the game 
    }

    @Override
    public void actionPerformed(ActionEvent ae) {//controls the quit button
        if (ae.getSource() == quitButton) {
            System.exit(0);
        }
    }

    //below are all of the different keyboard and action events, some are filled some are not
    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {//changes player ship position and fires based on key pressed
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (playerShip.y > 4) {
                    playerShip.y -= 15;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (playerShip.y < GamePanel.PHEIGHT - 123) {
                    playerShip.y += 15;
                }
                break;
            case KeyEvent.VK_SPACE:
                if (System.currentTimeMillis() - playerMissle < firingInterval) {
                    break;
                }
                playerMissle = System.currentTimeMillis();
                Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot());
                synchronized (gameData.figures) {
                    gameData.figures.add(f);
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) { //fires a missle from the player on a normal mouse click
        if (System.currentTimeMillis() - playerMissle < firingInterval) {
            return;
        }
        playerMissle = System.currentTimeMillis();
        Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot());
        synchronized (gameData.figures) {
            gameData.figures.add(f);
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
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
        playerShip.x = e.getX() - 10;
        playerShip.y = e.getY() - 25;
        if (System.currentTimeMillis() - playerMissle < firingInterval) {
            return;
        }
        playerMissle = System.currentTimeMillis();
        Missile f = new Missile(playerShip.getXofMissileShoot(), playerShip.getYofMissileShoot());
        synchronized (gameData.figures) {
            gameData.figures.add(f);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {//updates position based on mouse
        playerShip.x = e.getX() - 10;
        playerShip.y = e.getY() - 25;
    }

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
