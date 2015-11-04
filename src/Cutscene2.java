
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cutscene2 implements Cutscenes {

    private final BufferedImage backgroundImage;

    public Cutscene2() throws IOException {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        File file = new File(imagePath + separator + "images" + separator //load
                + "636400513.jpg");
        backgroundImage = ImageIO.read(file);
    }

    @Override
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }
}
