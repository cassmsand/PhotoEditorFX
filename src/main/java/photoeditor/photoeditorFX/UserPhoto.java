package photoeditor.photoeditorFX;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

    public class UserPhoto implements Photo {
        private String photoPath;
        private File photoFile;
        private String fileName;
        private long fileSize;
        private int width, height;
        private int photoNum;
        private boolean isFilterAdded; //needs to be updated to 'true' once any filter is added, and set to 'false' if removed
        private Filter photoFilter; //needs to be updated to the correct filter once added

        private int counter = 0;

        public UserPhoto (String photoPath) {
            try {
                // Using the photoPath, create a photoFile object
                photoFile = new File(photoPath);

                fileName = photoFile.getName();
                fileSize = photoFile.length();

                // Get the height and width of the image
                BufferedImage bufferedImage = ImageIO.read(photoFile);
                this.width = bufferedImage.getWidth();
                this.height = bufferedImage.getHeight();

                // Set photo number
                counter++;
                photoNum = counter;

                isFilterAdded = false;
                photoFilter = null;

            } catch (IOException e) {
                System.out.println("Error - could not create Photo");
            }

        }

        public String getFileName() { return fileName; }

        public long getFileSize() { return fileSize; }

        public int getWidth() { return width; }

        public int getHeight() { return height; }

        public int getPhotoNum() { return photoNum; }

        public void setWidth(int newWidth) { width = newWidth; }

        public void setHeight(int newHeight) { height = newHeight; }

        public boolean isFilterAdded() { return isFilterAdded; }

    }


