



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davidalba
 */
public class TutorialPanel extends JPanel {
 public static final int PWIDTH = 1245; // size of the game panel 852
    public static final int PHEIGHT = 610; //480
JPanel test;
    public boolean running; // state of the game.

  // private final Animator animator;//Animator object for the game panel
    private final TutorialAnimator tutorialAnimator;
    private final TutorialGameData gameData;//GameData object for the game panel
    private Graphics graphics; //graphics object for the game panel to use to render

    private Image dbImage = null;
    
    
    private HealthBar health;

    private Image backgroundImage;//image for the background of the game
    private BufferedImage planetImage;//test planet image
    private BufferedImage planetImageTransformed;//test planet image
    private float scale;
    private int scaleCount;
    private int nextStage;
    private boolean stageChange;

    private Stage currentStage;
    
    
    
     public TutorialPanel (TutorialAnimator tutorialAnimator,TutorialGameData gameData) throws IOException { //Animator animator
        this.tutorialAnimator = tutorialAnimator; //this.animator = animator
        this.gameData = gameData;

       

        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        backgroundImage = getImage(imagePath + separator + "images" + separator + "Spiral Background.gif");
        
       //backgroundImage.getScaledInstance(1275, 587, 1);
        setBackground(Color.black); // sets background color behind the background image
      //  setPreferredSize(new Dimension(1275, 587));//sets the size of the JPanel
       
       
        //planetImage = ImageIO.read(file);
        AffineTransform tx = new AffineTransform();
        tx.scale(0.5, 0.5);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        //planetImage = op.filter(planetImage, null);
        //planetImageTransformed = planetImage;
        scaleCount = 0;
        health = new HealthBar(1,null);
        scale = 1;
        

        currentStage = new Stage1();
        nextStage = 1;
        stageChange = false;

       
        setBackground(Color.black); // sets background color behind the background image
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));//sets the size of the JPanel
       
     
        
    }

    
     
      
    
    public void startTutorial(){
        running = true;
        Thread t = new Thread(tutorialAnimator); //tutorialAnimator
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
      
       
       // int width = backgroundImage.getWidth();//width of background image
       
       graphics.drawImage(backgroundImage, x, y, null);//draws image on tutorial panel
        //graphics.drawImage(backgroundImage, x + width, y, null);//draws image off screen for scrolling reasons
        //graphics.drawImage(planetImageTransformed, PWIDTH - planetImageTransformed.getWidth() / 2, PHEIGHT / 2 - planetImageTransformed.getHeight() / 2, null);
       
       System.out.println("y is" + TutorialShip.health);
        for(int i = 0; i < TutorialShip.health; i++){ //i < 5
            
          graphics.drawImage(health.getHealthimage(),30*i,10,30,30,null); //20*i, 10, 30, 30, nul   
        }        
    
       
        
      
       
        synchronized (gameData.figures) {//runs through each game figures and renders them
            TutorialGameFigures f;
            for (int i = 0; i < gameData.figures.size(); i++) {
                f = (TutorialGameFigures) gameData.figures.get(i);
                f.render(graphics);
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
             Font font = new Font("Impact", Font.BOLD, 20);//
             g.setFont(font);
            g.setColor(Color.white);
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
                String text1 = "Use the mouse to move ship around";
               // System.out.println(text);
                g.drawString(text1, 50, 50); 
               
    
                String text2 = "Click the the mouse to fire the missile ";
                g.drawString(text2, 50, 75);
                String text3 = "You can also use the keyboard by pressing m to switch and pressing space to fire";
                g.drawString(text3, 50, 100);
            }else {
                System.out.println("printScreen:graphic is null");
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
       
    }

    /*public void Tutorial(Graphics g) {
        
        
        g = this.getGraphics();
        Font font = new Font("Impact", Font.BOLD, 40);
        String text = "Your final score was";
        Color textColor = Color.WHITE;
        g.setFont(font);
        g.setColor(textColor);
        g.drawString(text, 50, 50);
        
    }*/
   
    public Stage getCurrentStage() {
        return currentStage;
    } 

   

    
  
       
     
   
   

  

    
}
