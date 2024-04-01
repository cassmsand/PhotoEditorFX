package photoeditor.photoeditorFX;

import java.awt.image.BufferedImage;

public class GreenFilter extends Filter {

    public GreenFilter(BufferedImage photo) {
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
                    int greenComponent = (pixelValue >> 8) & 0xff;

                    pixelValue = (alpha << 24) | (0 << 16) | (greenComponent << 8) | 0;

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
