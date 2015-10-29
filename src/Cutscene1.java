
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author davidalba
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Cutscene1 implements Cutscenes {

    private final BufferedImage backgroundImage;
    //private final BufferedImage planetImage;
    private final int backgroundWidth;
    private final BufferedImage shipImage;
    private int count = 0;

    public Cutscene1() throws IOException {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        File file = new File(imagePath + separator + "images" + separator //load
                + "79755173.jpg"); //"Stage1Background.gif"
        backgroundImage = ImageIO.read(file);
        //file = new File(imagePath + separator + "images" + separator //load
        //   + "planetCutscene.png");
        // planetImage = ImageIO.read(file);
        backgroundWidth = backgroundImage.getWidth(null);
        file = new File(imagePath + separator + "images" + separator
                + "playerShip.png");
        shipImage = ImageIO.read(file);
    }

    @Override
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    /*@Override
     public BufferedImage getPlanetImage() {
     return planetImage;
     }*/

    /* @Override
     public int getBackgroundWidth() {
     return backgroundWidth;
     }*/
}
