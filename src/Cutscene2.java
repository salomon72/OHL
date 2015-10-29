
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author davidalba
 */
public class Cutscene2 implements Cutscenes {

    private final BufferedImage backgroundImage;
    private final int backgroundWidth;
    private int count = 0;

    public Cutscene2() throws IOException {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        File file = new File(imagePath + separator + "images" + separator //load
                + "636400513.jpg");
        backgroundImage = ImageIO.read(file);
        backgroundWidth = backgroundImage.getWidth(null);
    }

    @Override
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    /* @Override
     public BufferedImage getPlanetImage() {
     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }*/

    /*@Override
     public int getBackgroundWidth() {
     return backgroundWidth;
     }*/
}
