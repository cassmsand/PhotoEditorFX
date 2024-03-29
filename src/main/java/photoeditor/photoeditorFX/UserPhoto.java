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
    private long fileSize;
    private int width, height;
    private int photoNum;
    private boolean isPhotoEdited; //needs to be updated to 'true' once any changes to the photo are made

    private int counter = 0;

    public UserPhoto (String photoPath) {
        try {
            // Using the photoPath, create a File object
            photoFile = new File(photoPath);

            // Convert the photoFile to a BufferedImage object
            image = ImageIO.read(photoFile);

            // Set fileName
            fileName = photoFile.getName();
            System.out.println("File name: " + fileName);

            // Set fileSize
            fileSize = photoFile.length();
            System.out.println("File size: " + fileSize + " bytes");

            // Get the height and width of the buffered image
            this.width = image.getWidth();
            this.height = image.getHeight();
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

    public File getFile() { return photoFile; }

    public BufferedImage getImage() { return image; }

    public String getFileName() { return fileName; }

    public long getFileSize() { return fileSize; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public int getPhotoNum() { return photoNum; }

    public void setWidth(int newWidth) { width = newWidth; }

    public void setHeight(int newHeight) { height = newHeight; }

    public boolean isPhotoEdited() { this.isPhotoEdited = true; return isPhotoEdited;}

    public void savePhoto(ImageView imageView,WritableImage w) {

        if (image != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Photo");

            fileChooser.getExtensionFilters().addAll(
                    //Photo can be saved as a JPEG, PNG, or a GIF
                    new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("GIF", "*.gif")
            );

            File file = fileChooser.showSaveDialog(imageView.getScene().getWindow());
            if (file != null) {
                try {
                    //saving image with applied effects
                    ImageIO.write(SwingFXUtils.fromFXImage(imageView.snapshot(null,w), null), "png", file);
                    System.out.println("Image saved successfully.");
                } catch (Exception e) {
                    System.out.println("Error saving image.");
                }
            }
        } else {
            System.out.println("No image to save.");
        }
    }

}

