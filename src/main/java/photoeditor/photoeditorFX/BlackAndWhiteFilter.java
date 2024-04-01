package photoeditor.photoeditorFX;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackAndWhiteFilter extends Filter{

    public BlackAndWhiteFilter(BufferedImage photo) {
        super(photo);
    }

    @Override
    public BufferedImage applyFilter() {
        //applying filter was successful
        BufferedImage image = getPhoto();
        if (image != null) {

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY);

            // Convert the original image to black and white
            Graphics2D graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            graphic.dispose();

            return result;
        } else {
            //FILTER WAS NOT SUCCESSFUL
            return null;
        }
    }
}

