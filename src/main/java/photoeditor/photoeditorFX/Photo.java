package photoeditor.photoeditorFX;

import java.awt.image.BufferedImage;
import java.io.File;

public interface Photo {
        public File getFile();
        public BufferedImage getImage();
        public String getFileName();
        public long getFileSize();
        public int getWidth();
        public int getHeight();
        public int getPhotoNum();
        public void setWidth(int newWidth);
        public void setHeight(int newHeight);
        public void isPhotoEdited(Boolean bool);

}
