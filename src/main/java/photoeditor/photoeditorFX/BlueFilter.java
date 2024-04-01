package photoeditor.photoeditorFX;

import java.awt.image.BufferedImage;

public class BlueFilter extends Filter{

    public BlueFilter(BufferedImage photo) {
        super(photo);
    }

    @Override
    public BufferedImage applyFilter() {
        //applying filter was successful
        BufferedImage image = getPhoto();
        if (image != null) {

            int width = image.getWidth();
            int height = image.getHeight();

            //Convert to red image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x <width; x++) {
                    int pixelValue = image.getRGB(x, y);

                    int alpha = (pixelValue >> 24) & 0xff;
                    int blueComponent = pixelValue & 0xff;

                    pixelValue = (alpha << 24) | (0 << 16) | (0  << 8) | blueComponent;

                    image.setRGB(x, y, pixelValue);
                }
            }

            return image;

        } else {
            //FILTER WAS NOT SUCCESSFUL
            return null;
        }
    }
}
