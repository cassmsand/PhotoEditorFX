package photoeditor.photoeditorFX;

import java.awt.image.BufferedImage;

public class BlueFilter extends Filter{

    public BlueFilter(UserPhoto photo) {
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
                    int blueComponent = pixelValue & 0xff;

                    pixelValue = (alpha << 24) | (0 << 16) | (0  << 8) | blueComponent;

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
