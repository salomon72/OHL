
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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

    private final DisplayAnimator displayAnimator;
    private final DisplayGameData displayGameData;//GameData object for the game panel
    private Graphics graphics; //graphics object for the game panel to use to render
<<<<<<< HEAD
    
     private Image backgroundImage;
     private Image dbImage = null;
     private Image displayEnemy,displayEnemy1, displayEnemy2, displayEnemy3, displayEnemy4, 
             displayEnemy5, displayEnemy6, displayEnemy7, displayEnemy8, displayEnemy9;    
     private Image displayShip, displayShip1, displayShip2, displayShip3;
     private Image displayMissile, displayMissile0, displayMissile1, displayMissile2, displayMissile3, displayMissile4, 
             displayMissile5, displayMissile6, displayMissile7;
     private Image displayHeart;
     
     private boolean stageChange;
    private int nextStage;
    private boolean StageChange;
=======

    private final Image backgroundImage;
    private Image dbImage = null;
    private final Image displayEnemy, displayEnemy1, displayEnemy2, displayEnemy3, displayEnemy4,
            displayEnemy5, displayEnemy6, displayEnemy7, displayEnemy8, displayEnemy9;
    private Image displayShip, displayShip1, displayShip2, displayShip3;
    private final Image displayMissile, displayMissile1, displayMissile2, displayMissile3, displayMissile4,
            displayMissile5, displayMissile6, displayMissile7;
    private final Image displayHeart;

>>>>>>> origin/master
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
        displayMissile0 = getImage(imagePath + separator + "images" + separator + "missile0.png");
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
<<<<<<< HEAD
        //graphics.drawImage(displayHeart, x+30, y, null);
        graphics.drawImage(displayHeart, x+500, y+10, 30, 30, null); //x+400, y+10
        graphics.drawImage(displayMissile,x+900, y+20, null);
        
        graphics.drawImage(displayEnemy, x+10, y+100, null); //x+10,y+100
        graphics.drawImage(displayEnemy9, x+500, y+100, null); //stage 1 boss
        
        
        graphics.drawImage(displayEnemy1, x+50, y+220, null); //stage 2 enemy in the sea
        //graphics.drawImage(displayEnemy1, x+100, y+220, 50, 50, null);
        graphics.drawImage(displayEnemy2, x+140, y+220, null); //stage 2 enemy in the air
        //graphics.drawImage(displayEnemy2, x+180, y+220, 50, 50, null);
        graphics.drawImage(displayEnemy3, x+230, y+220, null); //stage 2 enemy in the sea
        graphics.drawImage(displayEnemy8, x+500, y+220, null); //stage 2 boss
        
        
        graphics.drawImage(displayEnemy4, x+50, y+390, null); //stage 3 enemy tank
        graphics.drawImage(displayEnemy5, x+140, y+390, null); //stage 3 enemy
        graphics.drawImage(displayEnemy6, x+230, y+390, null); //stage 3 enemy
        graphics.drawImage(displayEnemy7, x+500, y+390, null); //stage 3 boss
        //graphics.drawImage(displayEnemy8, x+900, y+200, null); //stage 2 boss
        //graphics.drawImage(displayEnemy9, x+1200, y+200, null); //stage 1 boss
        
        graphics.drawImage(displayMissile0, x+950, y+150, null);
        graphics.drawImage(displayMissile1, x+1020, y+150, null);
        graphics.drawImage(displayMissile3, x+1070, y+150, null);
        graphics.drawImage(displayMissile4, x+950, y+175, null);
        graphics.drawImage(displayMissile5, x+1020, y+175, null);
        graphics.drawImage(displayMissile7, x+1070, y+175, null);
        
        
        
        //displayShip.getGraphics();
        
        //if (DisplayChange) {

            
                currentDisplay = new DisplayShip(displayShip,x,y);
                collectFromDisplay();
            

            //stageChange = false;
        
        
=======
        graphics.drawImage(displayHeart, x + 300, y + 10, 30, 30, null); //change size of heart
        graphics.drawImage(displayEnemy, x + 600, y + 10, null); //y+150
        graphics.drawImage(displayMissile, x + 900, y + 20, null);
        graphics.drawImage(displayEnemy1, x, y + 100, null);
        graphics.drawImage(displayEnemy2, x + 300, y + 100, null);
        graphics.drawImage(displayEnemy3, x + 600, y + 100, null);
        graphics.drawImage(displayEnemy4, x + 900, y + 100, null);
        currentDisplay = new DisplayShip(displayShip, x, y);
        collectFromDisplay();

>>>>>>> origin/master
        synchronized (displayGameData.figures) {//runs through each game figures and renders them
            GameFigure f;
            for (GameFigure figure : displayGameData.figures) {
                f = (GameFigure) figure;
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

<<<<<<< HEAD
    
     
    
public void printScreen() { //use active rendering to put the buffered image on-screen
        Graphics g, h;
=======
    public void printScreen() { //use active rendering to put the buffered image on-screen
        Graphics g;
>>>>>>> origin/master
        try {
            g = this.getGraphics();
            h = this.getGraphics();
            Font font = new Font("Impact", Font.BOLD, 15);//
            Font font2 = new Font("Imapct", Font.BOLD, 20);
            g.setFont(font);
            g.setColor(Color.RED);
            h.setFont(font2);
            h.setColor(Color.white);
            
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
                String text1 = "This is the player ship controlled through the game"; //playership
                g.drawString(text1, 70, 50);
<<<<<<< HEAD
                String text2 = "This is one of the five hearts comprising the player's health"; 
                g.drawString(text2, 450, 50); //650, 50
                String text3 = ("This is the player missile");
                g.drawString(text3, 850, 50); //50, 180
                
                String text4 = ("This is the first type of enemy encountered in space, stage 1"); 
                g.drawString(text4, 70, 130);
                String text5 = ("This is the boss that appears at the end of stage 1");
                g.drawString(text5, 600, 130);
                String text6 = ("These are the enemies of stage 2, sea");
                g.drawString(text6, 30, 210);
                String text7 = ("submarine");
                g.drawString(text7, 35, 280);
                String text8 = ("jet");
                g.drawString(text8, 150, 280);
                String text9 = ("battleship");
                g.drawString(text9, 220, 280);
                String text10 = ("This is the boss that appears at the end of stage 2");
                g.drawString(text10, 560, 260);
                String text11 = ("These are the enemies of stage 3, land");
                g.drawString(text11,30, 380);
                //String text12 = ("Battletank");
                //g.drawString(text12, 35, 460);
                String text12 = ("This is the boss that appears at the end of stage 3");
                g.drawString(text12, 590, 430);
                String text13 = ("These are the enemies' weapons");
                g.drawString(text13, 950, 130);
                String text100 = ("In this game the player will control a flying spaceship which runs through each of three stages.");
                g.drawString(text100, 200,500);
                String text101 = ("The player fires bullets and other weapons at various enemies, which differ in point values.");
                g.drawString(text101, 200, 515);
                String text102 = ("The player can also acquire different power-ups throughout the game by defeating enemies and acheiving a certain amount of points");
                g.drawString(text102, 200, 530);
=======

                String text2 = ("This is one of the five hearts comprising the player's health"); //heart
                g.drawString(text2, 300, 50);
                String text3 = "This is the first type of enemy encountered in space"; //enemy0
                g.drawString(text3, 650, 50); //650, 50
                String text4 = ("This is the default missle"); //missile0
                g.drawString(text4, 900, 50); //50, 180
                String text5 = ("This is the default missile");
                String text100 = ("In this game the player will control a flying spaceship which runs through each of three stages. The player fires bullets and other weapons");
>>>>>>> origin/master
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
