
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    public static final int PWIDTH = 1275; // size of the game panel
    public static final int PHEIGHT = 530;

    public boolean running; // state of the game.

    private final Animator animator;//Animator object for the game panel
    private final GameData gameData;//GameData object for the game panel
    private Graphics graphics; //graphics object for the game panel to use to render

    private Image dbImage = null;
    private final Image gameOver;//image to display upon game over
    private HealthBar health;

    private BufferedImage backgroundImage;//image for the background of the game
    private BufferedImage planetImage;//test planet image
    private BufferedImage planetImageTransformed;//test planet image
    private float scale = 1;
    private int scaleCount;

    public GamePanel(Animator animator, GameData gameData) throws IOException {
        this.animator = animator;
        this.gameData = gameData;

        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        gameOver = getImage(imagePath + separator + "images" + separator //load game over screen from image file
                + "win2.gif");
        setBackground(Color.black); // sets background color behind the background image
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));//sets the size of the JPanel
        File file = new File(imagePath + separator + "images" + separator //load
                + "background.gif");
        backgroundImage = ImageIO.read(file);
        file = new File(imagePath + separator + "images" + separator //load
                + "planet.png");
        planetImage = ImageIO.read(file);
        AffineTransform tx = new AffineTransform();
        tx.scale(0.5, 0.5);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        planetImage = op.filter(planetImage, null);
        planetImageTransformed = planetImage;
        scaleCount = 0;
        health = new HealthBar(1, null);
    }

    public void startGame() { //starts the threat for the animator
        running = true;
        Thread t = new Thread(animator);
        t.start();
    }

    public void gameRender(int x, int y) throws IOException { // called each iteration of the animator thread
        if (dbImage == null) {
            dbImage = createImage(PWIDTH, PHEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                graphics = dbImage.getGraphics();
            }
        }
        int width = backgroundImage.getWidth();//width of background image
        int height = backgroundImage.getHeight();//height of background image
        graphics.drawImage(backgroundImage, x, y, null);//draws image on main game panel
        graphics.drawImage(backgroundImage, x + width, y, null);//draws image off screen for scrolling reasons
        graphics.drawImage(planetImageTransformed, PWIDTH - planetImageTransformed.getWidth() / 2, PHEIGHT / 2 - planetImageTransformed.getHeight() / 2, null);
        for(int i = 0; i < 5; i++){
            graphics.drawImage(health.getHealthimage(),20*i,10,30,30,null);
        }        

        synchronized (gameData.figures) {//runs through each game figures and renders them
            GameFigure f;
            for (int i = 0; i < gameData.figures.size(); i++) {
                f = (GameFigure) gameData.figures.get(i);
                f.render(graphics);
            }
        }

    }

    public void transformPlanet() {
        if (scaleCount <= 1000) {
            AffineTransform tx = new AffineTransform();
            tx.scale(scale, scale);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            planetImageTransformed = op.filter(planetImage, null);
            scale = (float) (scale + 0.001);
            scaleCount++;
        }
    }

    public static Image getImage(String fileName) {//functions that reads image files
        Image image = null;
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException ioe) {
            System.out.println("Error: Cannot open image:" + fileName);
            JOptionPane.showMessageDialog(null, "Error: Cannot open image:" + fileName);
        }
        return image;
    }

    public void printScreen() { //use active rendering to put the buffered image on-screen
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
    }

    public void gameOver() {//game over function
        Graphics g;
        int score = gameData.score.score;//collect score from observers
        try {
            g = this.getGraphics();
            Font font = new Font("Impact", Font.BOLD, 40);//font of displayed score
            String text = "Your final score was: " + score;//text of displated score
            Color textColor = Color.WHITE; //color of score text
            g.setFont(font);
            g.setColor(textColor);
            if (gameOver != null) {//drawns the gave over image and displays the text
                g.drawImage(gameOver, 0, 0, null);
                g.drawString(text, 50, 50);
            }
            Thread.sleep(7000);//after sleep, game exits
            Toolkit.getDefaultToolkit().sync();  //sync the display on some systems
            g.dispose();
        } catch (InterruptedException e) {
            System.out.println("Graphics error: " + e);
        }
    }
}
