
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cutscene1 implements Cutscenes {

    private final BufferedImage backgroundImage;

    public Cutscene1() throws IOException {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        File file = new File(imagePath + separator + "images" + separator //load
                + "79755173.jpg"); //"Stage1Background.gif"
        backgroundImage = ImageIO.read(file);
    }

    @Override
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }
}
