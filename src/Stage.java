
import java.awt.image.BufferedImage;

public interface Stage {

    public BufferedImage getBackgroundImage();

    public BufferedImage getPlanetImage();

    public int getBackgroundWidth();
    
    public void resetCount();
}
