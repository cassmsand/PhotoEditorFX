package photoeditor.photoeditorFX;

import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

//UPDATE TO WHEN PHOTO IS UPLOADED
public class UserPhoto implements Photo {
    private File photoFile;
    private BufferedImage image;
    private String fileName;
    private String filePath;
    private long fileSize;
    private int width, height;
    private int photoNum;
    private boolean isPhotoEdited; //needs to be updated to 'true' once any changes to the photo are made

    private int counter = 0;

    public UserPhoto (String photoPath) {
        try {
            // Using the photoPath, create a File object
            photoFile = new File(photoPath);

            //Assign filePath
            filePath = photoPath;

            // Convert the photoFile to a BufferedImage object
            image = ImageIO.read(photoFile);

            // Set fileName
            fileName = photoFile.getName();

            // Set fileSize
            fileSize = photoFile.length();

            // Get the height and width of the buffered image
            this.width = image.getWidth();
            this.height = image.getHeight();

            // Set photo number
            counter++;
            photoNum = counter;

            isPhotoEdited = false;

        } catch (Exception e) {
            System.out.println("Error - could not create Photo");
        }

    }

    public File getFile() { return photoFile; }

    public BufferedImage getImage() { return image; }

    public String getFileName() { return fileName; }

    public String getFilePath() { return filePath; }

    public long getFileSize() { return fileSize; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public int getPhotoNum() { return photoNum; }

    public void setImage(BufferedImage newImage) { image = newImage; }

    public void setWidth(int newWidth) { width = newWidth; }

    public void setHeight(int newHeight) { height = newHeight; }

    public void isPhotoEdited(Boolean bool) { this.isPhotoEdited = bool;}

    public boolean isPhotoEdited() { return isPhotoEdited; }
}

