


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
     private Image displayEnemy,displayEnemy1, displayEnemy2, displayEnemy3, displayEnemy4, 
             displayEnemy5, displayEnemy6, displayEnemy7, displayEnemy8, displayEnemy9;    
     private Image displayShip, displayShip1, displayShip2, displayShip3;
     private Image displayMissile, displayMissile1, displayMissile2, displayMissile3, displayMissile4, 
             displayMissile5, displayMissile6, displayMissile7;
     private Image displayHeart;
     
     private boolean stageChange;
    private int nextStage;
    private boolean StageChange;
    private Display currentDisplay;
     
     public DisplayPanel(DisplayAnimator displayAnimator, DisplayGameData displayGameData) throws IOException { //Animator animator
        this.displayAnimator = displayAnimator;
        this.displayGameData = displayGameData;

        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        backgroundImage = getImage(imagePath + separator + "images" + separator + "743755-background.jpg");
        displayShip = getImage(imagePath + separator + "images" + separator + "playerShip.png");
        displayShip1 = getImage(imagePath + separator + "images" + separator + "ship1.png");
        displayShip2 = getImage(imagePath + separator + "images" + separator + "ship2.png");
        displayShip3 = getImage(imagePath + separator + "images" + separator + "ship3.png");
        displayEnemy = getImage(imagePath + separator + "images" + separator + "enemy0.png");
        displayEnemy1 = getImage(imagePath + separator + "images" + separator + "Enemy1.png");
        displayEnemy2 = getImage(imagePath + separator + "images" + separator + "enemy2.png");
        displayEnemy3 = getImage(imagePath + separator + "images" + separator + "Enemy3.png");
        displayEnemy4 = getImage(imagePath + separator + "images" + separator + "enemy4.png");
        displayEnemy5 = getImage(imagePath + separator + "images" + separator + "enemy5.png");
        displayEnemy6 = getImage(imagePath + separator + "images" + separator + "enemy6.png");
        displayEnemy7 = getImage(imagePath + separator + "images" + separator + "enemy7.png");
        displayEnemy8 = getImage(imagePath + separator + "images" + separator + "enemy8.png");
        displayEnemy9 = getImage(imagePath + separator + "images" + separator + "enemy9.png");
        displayMissile = getImage(imagePath + separator + "images" + separator + "missile.png");
        displayMissile1 = getImage(imagePath + separator + "images" + separator + "missile1.png");
        displayMissile2 = getImage(imagePath + separator + "images" + separator + "missile2.png");
        displayMissile3 = getImage(imagePath + separator + "images" + separator + "missile3.png");
        displayMissile4 = getImage(imagePath + separator + "images" + separator + "missile4.png");
        displayMissile5 = getImage(imagePath + separator + "images" + separator + "missile5.png");
        displayMissile6 = getImage(imagePath + separator + "images" + separator + "missile6.png");
        displayMissile7 = getImage(imagePath + separator + "images" + separator + "missile7.png");
        displayHeart = getImage(imagePath + separator + "images" + separator + "heart.png");
        
        
        
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
        //graphics.drawImage(displayHeart, x+30, y, null);
        graphics.drawImage(displayHeart, x+300, y+10, 30, 30, null); //change size of heart
        graphics.drawImage(displayEnemy, x+600, y+10, null); //y+150
        graphics.drawImage(displayMissile,x+900, y+20, null);
        graphics.drawImage(displayEnemy1, x, y+100, null); 
        graphics.drawImage(displayEnemy2, x+300, y+100, null);
        graphics.drawImage(displayEnemy3, x+600, y+100, null);
        graphics.drawImage(displayEnemy4, x+900, y+100, null);
        
        
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
            Font font = new Font("Impact", Font.BOLD, 10);//
            g.setFont(font);
            g.setColor(Color.white);
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
                String text1 = "This is the player ship controlled through the game"; //playership
                g.drawString(text1, 70, 50);
                
                //g.drawImage(dbImage, 0,0 , null); 
                String text2 = ("This is one of the five hearts comprising the player's health"); //heart
                g.drawString(text2, 300, 50);
                String text3 = "This is the first type of enemy encountered in space"; //enemy0
                g.drawString(text3, 650, 50); //650, 50
                String text4 = ("This is the default missle"); //missile0
                g.drawString(text4, 900, 50); //50, 180
                String text5 = ("This is the default missile");
                
                
                String text100 = ("In this game the player will control a flying spaceship which runs through each of three stages. The player fires bullets and other weapons");
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

