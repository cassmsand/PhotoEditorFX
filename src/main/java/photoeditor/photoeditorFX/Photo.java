package photoeditor.cassiephotoeditorFX;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public interface Photo {
        public String getFileName();
        public long getFileSize();
        public int getWidth();
        public int getHeight();
        public int getPhotoNum();
        public void setWidth(int newWidth);
        public void setHeight(int newHeight);
        public boolean isFilterAdded();

}
