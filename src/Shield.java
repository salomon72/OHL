
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Shield {
    private Image shieldImage;
    static int count;
    
    public Shield() {
     String imagePath = System.getProperty("user.dir");
     String separator = System.getProperty("file.separator");
     shieldImage = getImage(imagePath + separator + "images" + separator
             + "shield3.png");
     count = 0;
 }  

 public static Image getImage(String fileName) {
     Image image = null;
     try {
         image = ImageIO.read(new File(fileName));
     } catch (IOException ioe) {
         System.out.println("Error: Cannot open image:" + fileName);
         JOptionPane.showMessageDialog(null, "Error: Cannot open image:" + fileName);
     }
     return image;
 }

    public Image getShieldImage() {
        return shieldImage;
    } 
    
}
