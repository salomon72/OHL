
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

public class InterfaceForm2 extends JPanel {

    public static final int PWIDTH = 1275; // size of the game panel
    public static final int PHEIGHT = 587;

    public boolean running; // state of the game.

    private BufferedImage dbImage = null;
    private final BufferedImage backgroundImage;//image for the background of the game
    private final BufferedImage playerShip;

    public InterfaceForm2() throws IOException {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        File file = new File(imagePath + separator + "images" + separator //load
                + "Stage1Background.gif"); //"Stage1Background.gif"
        backgroundImage = ImageIO.read(file);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));//sets the size of the JPanel
        File file2 = new File(imagePath + separator + "images" + separator //load
                + "Galileo.jpg"); //playerShip
        playerShip = ImageIO.read(file2);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("Impact", Font.BOLD, 150);//
        g.setFont(font);//
        g.setColor(Color.ORANGE);
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(playerShip, 0, 0, 1275, 590, null); //show playership on time screen            
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

    public BufferedImage getPlayerShipImage() {
        return playerShip;
    }
}
