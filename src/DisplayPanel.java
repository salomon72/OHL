


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davidalba
 */
public class DisplayPanel extends JPanel {
    
    public static final int PWIDTH = 1245; // size of the game panel 852
    public static final int PHEIGHT = 610; //480
    JPanel test;
    public boolean running; // state of the game.
    
    private DisplayAnimator displayAnimator;
    private DisplayGameData displayGameData;//GameData object for the game panel
    private Graphics graphics; //graphics object for the game panel to use to render
    
     private Image backgroundImage;
     private Image dbImage = null;
     private Image displayEnemy;    
     private Image displayShip;
     private Image displayMissile;
     
     private boolean stageChange;
    private int nextStage;
    private boolean StageChange;
    private Display currentDisplay;
     
     public DisplayPanel(DisplayAnimator displayAnimator, DisplayGameData displayGameData) throws IOException { //Animator animator
        this.displayAnimator = displayAnimator;
        this.displayGameData = displayGameData;

        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        backgroundImage = getImage(imagePath + separator + "images" + separator + "Stage1Background.gif");
        displayShip = getImage(imagePath + separator + "images" + separator + "playerShip.png");
        displayEnemy = getImage(imagePath + separator + "images" + separator + "enemy0.png");
        displayMissile = getImage(imagePath + separator + "images" + separator + "missile.png");
        
        
        
        setBackground(Color.black); // sets background color behind the background image
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));//sets the size of the JPanel
     }

    
      public void startDisplay() { //starts the thread for the animator
        running = true;

        Thread t = new Thread(displayAnimator);
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
        graphics.drawImage(backgroundImage, x, y, null);
        graphics.drawImage(dbImage, x, y, test);
        graphics.drawImage(displayShip, x, y, null);
        graphics.drawImage(displayEnemy, x, y+150, null);
        graphics.drawImage(displayMissile,x+650, y, null);
        
        //displayShip.getGraphics();
        
        //if (DisplayChange) {

            
                currentDisplay = new DisplayShip(displayShip,x,y);
                collectFromDisplay();
            

            //stageChange = false;
        
        
        synchronized (displayGameData.figures) {//runs through each game figures and renders them
            GameFigure f;
            for (int i = 0; i < displayGameData.figures.size(); i++) {
                f = (GameFigure) displayGameData.figures.get(i);
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
    
     private void collectFromDisplay() {
        displayShip = (Image) currentDisplay.getCharacterImage();
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
                String text1 = "This is the player ship controlled through the game";
                g.drawString(text1, 70, 50);
                
                //g.drawImage(dbImage, 0,0 , null);
                String text2 = "This is the first type of enemy encountered in space";
                g.drawString(text2, 50, 180); 
                String text3 = ("This is the default missle");
                g.drawString(text3, 650, 50);
                //g.drawString(text3, 50, 100);
                //g.drawImage(displayShip,0,0,null);
                
                String text4 = ("In this game the player will control a flying spaceship which runs through each of three stages. The player fires bullets and other weapons");
                //g.drawString(text4, 50,100);
            } else {
                System.out.println("printScreen:graphic is null");
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
          

    }
  }

