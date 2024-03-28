package photoeditor.photoeditorFX;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

    //UPDATE TO WHEN PHOTO IS UPLOADED
    public class UserPhoto implements Photo {
        private File photoFile;
        private String fileName;
        private long fileSize;
        private int width, height;
        private int photoNum;
        private boolean isPhotoEdited; //needs to be updated to 'true' once any changes to the photo are made

        private int counter = 0;

        public UserPhoto (String photoPath) {
            try {
                // Using the photoPath, create a photoFile object
                photoFile = new File(photoPath);

                //Set photo file name to fileName
                fileName = photoFile.getName();
                System.out.println("File name: " + fileName);

                //Set photo file size to fileSize
                fileSize = photoFile.length();
                System.out.println("File size: " + fileSize + " bytes");

                // Get the height and width of the image
                BufferedImage bufferedImage = ImageIO.read(photoFile);
                this.width = bufferedImage.getWidth();
                this.height = bufferedImage.getHeight();
                System.out.println("Width: " + getWidth() + " pixels");
                System.out.println("Height: " + getHeight() + " pixels");

                // Set photo number
                counter++;
                photoNum = counter;
                System.out.println("Photo number: " + photoNum);

                isPhotoEdited = false;

            } catch (Exception e) {
                System.out.println("Error - could not create Photo");
            }

        }

        public File getPhotoFile() { return photoFile; }

        public String getFileName() { return fileName; }

        public long getFileSize() { return fileSize; }

        public int getWidth() { return width; }

        public int getHeight() { return height; }

        public int getPhotoNum() { return photoNum; }

        public void setWidth(int newWidth) { width = newWidth; }

        public void setHeight(int newHeight) { height = newHeight; }

        public boolean isPhotoEdited() { return isPhotoEdited; }

    }


