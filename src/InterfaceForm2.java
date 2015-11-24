

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
public class InterfaceForm2 extends JPanel {
    
    public static final int PWIDTH = 1275; // size of the game panel
    public static final int PHEIGHT = 587;

    public boolean running; // state of the game.

    //private final Animator animator;//Animator object for the game panel
    //private final GameData gameData;//GameData object for the game panel
    private Graphics graphics; //graphics object for the game panel to use to render
   

    private BufferedImage dbImage = null;
    private final BufferedImage backgroundImage;//image for the background of the game
    
    
    public InterfaceForm2() throws IOException {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
         File file = new File(imagePath + separator + "images" + separator //load
                + "Stage1Background.gif"); //"Stage1Background.gif"
        backgroundImage = ImageIO.read(file);
        //setBackground(Color.black); // sets background color behind the background image
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));//sets the size of the JPanel
        
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }
    
     public static Image getImage(String fileName) {//functions that reads image files
        Image image = null; //
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
            if (dbImage != null) {
                g.drawImage(dbImage, 0, 0, null);
            } else {
                System.out.println("printScreen:graphic is null");
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
    }
    
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }
}
