
import java.awt.image.RGBImageFilter;

class ColorShift extends RGBImageFilter {

    public ColorShift() {
        canFilterIndexColorModel = true;
    }

    @Override
    public int filterRGB(int x, int y, int rgb) {
        return ((rgb & 0xff00ff00)
                | ((rgb & 0xff0000) >> 16)
                | ((rgb & 0xff) << 16));

    }
}
