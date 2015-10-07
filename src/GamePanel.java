
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
    public static final int PHEIGHT = 587;

    public boolean running; // state of the game.

    private final Animator animator;//Animator object for the game panel
    private final GameData gameData;//GameData object for the game panel
    private Graphics graphics; //graphics object for the game panel to use to render

    private Image dbImage = null;
    private final Image gameOver;//image to display upon game over
    private HealthBar health;
    private final Image gamewin;

    private BufferedImage backgroundImage;//image for the background of the game
    private BufferedImage planetImage;//test planet image
    private BufferedImage planetImageTransformed;//test planet image
    private float scale;
    private int scaleCount;
    private int nextStage;
    private boolean stageChange;

    private Stage currentStage;

    public GamePanel(Animator animator, GameData gameData) throws IOException {
        this.animator = animator;
        this.gameData = gameData;

        currentStage = new Stage1();
        nextStage = 1;
        stageChange = false;

        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        gameOver = getImage(imagePath + separator + "images" + separator //load game over screen from image file
                + "gameover.gif");
        gamewin = getImage(imagePath + separator + "images" + separator //load game over screen from image file
                + "win.gif");
        setBackground(Color.black); // sets background color behind the background image
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));//sets the size of the JPanel
        collectFromStage();
        File file = new File(imagePath + separator + "images" + separator //load
                + "planet.png");
        planetImage = ImageIO.read(file);
        AffineTransform tx = new AffineTransform();
        tx.scale(0.5, 0.5);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        planetImage = op.filter(planetImage, null);
        planetImageTransformed = planetImage;
        scaleCount = 0;
        scale = 1;
        health = new HealthBar(1, null);
    }

    public void startGame() { //starts the thread for the animator
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
        graphics.drawImage(backgroundImage, x, y, null);//draws image on main game panel
        graphics.drawImage(backgroundImage, x + width, y, null);//draws image off screen for scrolling reasons
        if (nextStage == 1) {
<<<<<<< HEAD
=======
                graphics.drawImage(planetImageTransformed, PWIDTH - planetImageTransformed.getWidth() / 2, PHEIGHT / 2 - planetImageTransformed.getHeight() / 2, null);
            }
        //System.out.println("y is" + Ship.health);
        for(int i = 0; i < Ship.health; i++){ //i < 5
            
          graphics.drawImage(health.getHealthimage(),30*i,10,30,30,null); //20*i, 10, 30, 30, nul   
        }        
    
        if (nextStage == 1) {
>>>>>>> origin/master
            graphics.drawImage(planetImageTransformed, PWIDTH - planetImageTransformed.getWidth() / 2, PHEIGHT / 2 - planetImageTransformed.getHeight() / 2, null);
        }
        int healthmap = gameData.score.health*5/GameData.MAXHEALTH;
        if(healthmap == 0) healthmap =1;
       // System.out.println("healthmap:"+healthmap);
        for (int i = 0; i < 5; i++) {
            if(i < healthmap)
            graphics.drawImage(health.getHealthimage(), 20 * i, 10, 30, 30, null);
            else graphics.drawImage(health.getHealthimageBreak(), 20 * i, 10, 30, 30, null);
        }
         //health.update(gameData.score.health);
        if (stageChange) {

            if (nextStage == 1) {
                currentStage = new Stage1();
                planetImageTransformed = planetImage;
                scaleCount = 0;
                scale = 1;
                collectFromStage();
            }

            if (nextStage == 2) {
                currentStage = new Stage2();
                collectFromStage();
            }
            
            if (nextStage == 3) {
                currentStage = new Stage3();
                collectFromStage();
            }

            stageChange = false;
        }

        synchronized (gameData.figures) {//runs through each game figures and renders them
            GameFigure f;
            for (int i = 0; i < gameData.figures.size(); i++) {
                f = (GameFigure) gameData.figures.get(i);
                f.render(graphics);
            }
        }
    }

    private void collectFromStage() {
        backgroundImage = currentStage.getBackgroundImage();
    }

    public void transformPlanet() {
        if (nextStage == 1) {
            if (scaleCount <= 1000) {
                AffineTransform tx = new AffineTransform();
                tx.scale(scale, scale);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                planetImageTransformed = op.filter(planetImage, null);
                scale = (float) (scale + 0.001);
                scaleCount++;
            }
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
            g.setColor(Color.red);
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
                String text = "Score:" + gameData.score.score;//text of displated score
               // System.out.println(text);
                g.drawString(text, 50, 50);
                
                
            }else {
                System.out.println("printScreen:graphic is null");
            }
            //System.out.println("Toolkit.getDefaultToolkit().sync();");
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics error1: " + e);
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
            System.out.println("Graphics error2: " + e);
        }
    }
    public void gameWin() {//game over function
        Graphics g;
        int score = gameData.score.score;//collect score from observers
        try {
            g = this.getGraphics();
            Font font = new Font("Impact", Font.BOLD, 40);//font of displayed score
            String text = "Your final score was: " + score;//text of displated score
            Color textColor = Color.WHITE; //color of score text
            g.setFont(font);
            g.setColor(textColor);
            if (gamewin != null) {//drawns the gave over image and displays the text
                g.drawImage(gamewin, 0, 0, null);
                g.drawString(text, 50, 50);
            }
            Thread.sleep(7000);//after sleep, game exits
            Toolkit.getDefaultToolkit().sync();  //sync the display on some systems
            g.dispose();
        } catch (InterruptedException e) {
            System.out.println("Graphics error2: " + e);
        }
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public int getNextStage() {
        return nextStage;
    }

    public void setNextStage(int nextStage) {
        this.nextStage = nextStage;
    }

    public boolean isStageChange() {
        return stageChange;
    }

    public void setStageChange(boolean stageChange) {
        this.stageChange = stageChange;
    }

}
