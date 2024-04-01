package photoeditor.photoeditorFX;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RedFilter extends Filter {

    public RedFilter(UserPhoto photo) {
        super(photo);
    }

    @Override
    public UserPhoto applyFilter() {
        //applying filter was successful
        UserPhoto photo = getPhoto();
        if (photo != null) {
            BufferedImage image = photo.getImage();

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

            photo.setImage(image);

            return photo;

        } else {
            //FILTER WAS NOT SUCCESSFUL
            return null;
        }
    }
}
