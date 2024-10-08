package photoeditor.photoeditorFX;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RedFilter extends Filter {

    public RedFilter(BufferedImage photo) {
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
                    int redComponent = (pixelValue >> 16) & 0xff;

                    pixelValue = (alpha << 24) | redComponent << 16 | (0  << 8) | 0;

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
