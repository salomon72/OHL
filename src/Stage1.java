
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Stage1 implements Stage{
    
    private final BufferedImage backgroundImage;
    private final int backgroundWidth;
    
    public Stage1() throws IOException{
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        File file = new File(imagePath + separator + "images" + separator //load
                + "Stage1Background.gif");
        backgroundImage = ImageIO.read(file);
        backgroundWidth = backgroundImage.getWidth(null);
    }

    @Override
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    @Override
    public int getBackgroundWidth() {
        return backgroundWidth;
    }
}
