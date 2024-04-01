package photoeditor.photoeditorFX;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackAndWhiteFilter extends Filter{
    private static final int BLACK_THRESHOLD = 128; // Threshold for black and white conversion

    public BlackAndWhiteFilter(UserPhoto photo) {
        super(photo);
    }

    @Override
    public UserPhoto applyFilter() {
        //applying filter was successful
        UserPhoto photo = getPhoto();
        if (photo != null) {
            BufferedImage image = photo.getImage();

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY);

            // Convert the original image to black and white
            Graphics2D graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            graphic.dispose();

            // Update the UserPhoto object with the updated BufferedImage
            photo.setImage(result);

            return photo;
        } else {
            //FILTER WAS NOT SUCCESSFUL
            return null;
        }
    }
}

