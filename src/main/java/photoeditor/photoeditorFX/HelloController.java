package photoeditor.photoeditorFX;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class HelloController {
    public Label dragAndDropLabel;
    @FXML
    private ComboBox<String> comboBox1;
    @FXML
    private ImageView imageView;
    @FXML
    private Slider brightnessSlider;
    @FXML
    private Slider hueSlider;
    @FXML
    private Slider saturationSlider;
    @FXML
    private Slider contrastSlider;
    @FXML
    private UserPhoto photo;
    @FXML      // added for heightSlider
    private Slider heightSlider;
    @FXML     // added for widthSlider
    private Slider widthSlider;
    private WritableImage wim;
    //added for the quit class
    private static Stage mainStage;


    public void initialize() {

        ObservableList<String> options1 = FXCollections.observableArrayList(
                "0.0625x",
                "0.125x",
                "0.25x",
                "0.5x",
                "1x",
                "2x",
                "3x",
                "4x",
                "5x"
        );
        comboBox1.setItems(options1);

        //Creating object that'll save image with applied effect, dimensions don't affect final result.
        WritableImage wim = new WritableImage( 800, 600);

        //class that's used to set image effects
        ColorAdjust colorAdjust = new ColorAdjust();

        //listener for each color effect slider to set strength of effect on image
        brightnessSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setBrightness(brightnessSlider.getValue());
            imageView.setEffect(colorAdjust);
            photo.isPhotoEdited(true);
        });

        contrastSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setContrast(contrastSlider.getValue());
            imageView.setEffect(colorAdjust);
            photo.isPhotoEdited(true);
        });

        hueSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setHue(hueSlider.getValue());
            imageView.setEffect(colorAdjust);
            photo.isPhotoEdited(true);
        });

        saturationSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setSaturation(saturationSlider.getValue());
            imageView.setEffect(colorAdjust);
            photo.isPhotoEdited(true);
        });

        //need to keep track of scale value for height/width slider as well as the sliders.
        AtomicReference<Double> scale = new AtomicReference<>((double) 1);
        // Add listener to comboBox1 for resizing up and down from 0.0625 to 5x
        comboBox1.setOnAction(event -> {
            scale.set(parseScale(comboBox1.getValue()));

            imageView.setFitHeight(heightSlider.getValue() + (scale.get() * photo.getHeight()));
            imageView.setFitWidth(widthSlider.getValue() + (scale.get() * photo.getWidth()));
            photo.isPhotoEdited(true);
        });

        // Add listener to height slider for resizing
        heightSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setPreserveRatio(false);
            imageView.setFitHeight(heightSlider.getValue() + (scale.get() * photo.getHeight()));
             // Keep the width fixed
            photo.isPhotoEdited(true);
        });

        // Add listener to width slider for resizing
        widthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setPreserveRatio(false);
            imageView.setFitWidth(widthSlider.getValue() + (scale.get() * photo.getWidth()));
             // Keep the height fixed
            photo.isPhotoEdited(true);
        });
    }

    // Method to parse the scale value and return a double representing the scaling factor
    private static double parseScale(String selectedScale) {
        try {
            // implementation: parse "2x" to 2.0, "0.5x" to 0.5, etc.
            if (selectedScale.endsWith("x")) {
                return Double.parseDouble(selectedScale.substring(0, selectedScale.length() - 1));
            } else {
                return 1.0; // Default scale if format is invalid
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing scale: " + selectedScale);
            return 1.0; // Default scale if parsing fails
        }
    }

    @FXML
    private void onDragOver(DragEvent event) {
        if (event.getGestureSource() != imageView && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void onDragDropped(DragEvent event) throws URISyntaxException {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            String imagePath = db.getFiles().get(0).toURI().toString();

            if (typeChecker(imagePath) == true) {
                Image image = new Image(imagePath);
                imageView.setImage(image);
                dragAndDropLabel.setVisible(false);

                //Get the file path from the imagePath
                try {
                    URI uri = new URI(imagePath);
                    String directoryPath = uri.getPath();

                    //Connect Image to UserPhoto class
                    photo = new UserPhoto(directoryPath);

                    //setting aspect ratio of imageview to be the same as photo
                    imageView.setFitHeight(photo.getHeight());
                    imageView.setFitWidth(photo.getWidth());
                } catch (Exception e) {
                    System.out.println("Error in image path");
                }
            } else {
                popUpBox("Error - Accepted file types: .png, .jpg, .jpeg, .gif");
            }

        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void onDragExited(DragEvent event) {
        event.consume();
    }

    //Check if uploaded file is an accepted file type
    private boolean typeChecker(String imagePath) {
        String lowerCasePath = imagePath.toLowerCase();
        return lowerCasePath.endsWith(".png") || lowerCasePath.endsWith(".jpg") ||
                lowerCasePath.endsWith(".jpeg") || lowerCasePath.endsWith(".gif");
    }

    @FXML
    //Open a new photo file
    public void applyOpen() {
        if (photo != null) {
            JFrame openFrame = new JFrame();
            JButton[] buttons = yesNoBox(openFrame, "Do you want to save your photo?");
            JButton noButton = buttons[0];
            JButton yesButton = buttons[1];

            // Add ActionListener to the Yes button
            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openFrame.dispose(); //Clear frame
                    Platform.runLater(() -> {
                        applySave(); //Saves photo
                        deleteImage(); //Deletes photo
                        File selectedFile = selectFile(imageView.getScene().getWindow()); //Selects a file
                        displayFile(selectedFile); //displays the file
                    });
                }
            });

            // Add ActionListener to the No button
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openFrame.dispose(); //Clear frame
                    deleteImage(); // Deletes photo
                    Platform.runLater(() -> {
                        File selectedFile = selectFile(imageView.getScene().getWindow()); //Selects the file
                        displayFile(selectedFile); //Displays the file
                    });
                }
            });
            openFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } else {
            File selectedFile = selectFile(imageView.getScene().getWindow()); //Select a new file
            displayFile(selectedFile); //Display the file
        }
    }

    @FXML
    //Clear the photo workspace
    public void applyClear() {
        if (photo != null) {
            // Create JFrame quitFrame object and return yes/no buttons to assign actions
            JFrame clearFrame = new JFrame();
            JButton[] buttons = yesNoBox(clearFrame, "Do you want to save your photo?");
            JButton noButton = buttons[0];
            JButton yesButton = buttons[1];

            // Add ActionListener to the Yes button
            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearFrame.dispose(); //Clear frame
                    Platform.runLater(() -> {
                        applySave(); //Save photo
                        deleteImage(); //Delete photo object and update the imageView
                    });
                }
            });

            // Add ActionListener to the No button
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearFrame.dispose(); //Clear frame
                    deleteImage(); //Delete photo object and update the imageView
                }
            });
            // Reset all sliders to their default positions
            brightnessSlider.setValue(0);
            contrastSlider.setValue(0);
            hueSlider.setValue(0);
            saturationSlider.setValue(0);

            // Reset the ComboBox to its default value
            comboBox1.setValue("1x");

            // Reset the fit height and width of the image view to the original dimensions of application
            imageView.setFitHeight(600);
            imageView.setFitWidth(800);

            clearFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    @FXML
    //Reset photo to original Image
    public void applyReset() {
        if (photo != null) {
            //Reset settings
            // Reset all sliders to their default positions
            brightnessSlider.setValue(0);
            contrastSlider.setValue(0);
            hueSlider.setValue(0);
            saturationSlider.setValue(0);

            // Reset the ComboBox to its default value
            comboBox1.setValue("1x");

            // Reset the fit height and width of the image view to the original dimensions of application
            imageView.setFitHeight(photo.getHeight());
            imageView.setFitWidth(photo.getWidth());

            // apply reset
            Image originalImage = SwingFXUtils.toFXImage(photo.getImage(), null);
            imageView.setImage(originalImage);
        } else {
            popUpBox("Error - no photo uploaded");
        }
    }


    @FXML
    //Saves the photo
    public void applySave() {
        //If there is an image uploaded
        if (photo != null) {
            //Open file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Photo");

            //Add file options to dropdown menu
            fileChooser.getExtensionFilters().addAll(
                    //Photo can be saved as a JPEG, PNG, or a GIF
                    new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("GIF", "*.gif")
            );

            // Save the contents to a file
            File file = fileChooser.showSaveDialog(imageView.getScene().getWindow());

            //If the file is not null
            if (file != null) {
                try {
                    //saving image with applied effects
                    ImageIO.write(SwingFXUtils.fromFXImage(imageView.snapshot(null, wim), null), "png", file);
                    popUpBox("Saved successfully!");
                } catch (Exception e) {
                    System.out.println("Error saving image.");
                }
            }
        } else {
            //ERROR NO PHOTO TO SAVE
            popUpBox("Error - no photo uploaded");
        }
    }

    @FXML
    //Quits the application
    public void applyQuit() {
        // Create JFrame quitFrame object and return yes/no buttons to assign actions
        JFrame quitFrame = new JFrame();
        JButton[] buttons = yesNoBox(quitFrame, "Are you sure you want to quit?");
        JButton noButton = buttons[0];
        JButton yesButton = buttons[1];

        // Add ActionListener to the Yes button
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                quitFrame.dispose(); //Clear frame
                System.exit(0); // Exit the entire application
            }
        });

        // Add ActionListener to the No button
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                quitFrame.dispose(); //Clear frame
            }
        });
        quitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @FXML
    public void applyBlackAndWhite() {
        //Get copy of photo
        BufferedImage copyOfPhoto = createCopy(photo);

        BlackAndWhiteFilter filter = new BlackAndWhiteFilter(copyOfPhoto);
        BufferedImage updatedPhoto = filter.applyFilter();

        //If there is an updated photo
        if (updatedPhoto != null) {
            // Get the updated image from the UserPhoto object
            Image updatedImage = SwingFXUtils.toFXImage(updatedPhoto, null);

            // Set the updated image to the ImageView
            imageView.setImage(updatedImage);
        } else {
            // Handle the case where the filter was not successful
            popUpBox("Error - filter could not be applied");
        }

    }

    @FXML
    private void applyGrayscale() {
        BufferedImage copyOfPhoto = createCopy(photo);

        // Create Grayscale filter object and apply filter
        GrayscaleFilter filter = new GrayscaleFilter(copyOfPhoto);
        BufferedImage updatedPhoto = filter.applyFilter();

        //If there is an updated photo
        if (updatedPhoto != null) {
            // Get the updated image from the UserPhoto object
            Image updatedImage = SwingFXUtils.toFXImage(updatedPhoto, null);

            // Set the updated image to the ImageView
            imageView.setImage(updatedImage);
            photo.isPhotoEdited(true);
        } else {
            // Handle the case where the filter was not successful
            popUpBox("Error - filter could not be applied");
        }

    }

    @FXML
    public void applyRed() {
        BufferedImage copyOfPhoto = createCopy(photo);

        // Create red filter object and apply filter
        RedFilter filter = new RedFilter(copyOfPhoto);
        BufferedImage updatedPhoto = filter.applyFilter();

        //If there is an updated photo
        if (updatedPhoto != null) {
            // Get the updated image from the UserPhoto object
            Image updatedImage = SwingFXUtils.toFXImage(updatedPhoto, null);

            // Set the updated image to the ImageView
            imageView.setImage(updatedImage);
            photo.isPhotoEdited(true);
        } else {
            // Handle the case where the filter was not successful
            popUpBox("Error - filter could not be applied");
        }
    }

    @FXML
    public void applyBlue() {
        BufferedImage copyOfPhoto = createCopy(photo);

        // Create red filter object and apply filter
        BlueFilter filter = new BlueFilter(copyOfPhoto);
        BufferedImage updatedPhoto = filter.applyFilter();

        //If there is an updated photo
        if (updatedPhoto != null) {
            // Get the updated image from the UserPhoto object
            Image updatedImage = SwingFXUtils.toFXImage(updatedPhoto, null);

            // Set the updated image to the ImageView
            imageView.setImage(updatedImage);
            photo.isPhotoEdited(true);
        } else {
            // Handle the case where the filter was not successful
            popUpBox("Error - filter could not be applied");
        }
    }

    @FXML
    public void applyGreen() {
        BufferedImage copyOfPhoto = createCopy(photo);

        // Create red filter object and apply filter
        GreenFilter filter = new GreenFilter(copyOfPhoto);
        BufferedImage updatedPhoto = filter.applyFilter();

        //If there is an updated photo
        if (updatedPhoto != null) {
            // Get the updated image from the UserPhoto object
            Image updatedImage = SwingFXUtils.toFXImage(updatedPhoto, null);

            // Set the updated image to the ImageView
            imageView.setImage(updatedImage);
            photo.isPhotoEdited(true);
        } else {
            // Handle the case where the filter was not successful
            popUpBox("Error - filter could not be applied");
        }
    }

    //Create a copy of the photo
    private BufferedImage createCopy(UserPhoto photo) {
        //Get info about photo
        BufferedImage originalImage = photo.getImage();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int type = originalImage.getType();

        // Create a new BufferedImage as a copy of the original image
        BufferedImage copyOfPhoto = new BufferedImage(width, height, type);
        Graphics2D graphics = copyOfPhoto.createGraphics();
        graphics.drawImage(originalImage, 0, 0, null);
        graphics.dispose();

        return copyOfPhoto;
    }

    // Deletes the photo object and clears the imageView
    private void deleteImage() {
        // Set the ImageView's image property to null
        imageView.setImage(null);
        // Dispose the photo object if it's not null
        if (photo != null) {
            photo = null;
        }
        dragAndDropLabel.setVisible(true); //Unhidden label
    }

    //Select a file from the fileChooser
    private File selectFile(javafx.stage.Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        //Only allow certain photos to be uploaded
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        return fileChooser.showOpenDialog(window);
    }

    //Display a file in the imageView
    private void displayFile(File file) {
        // Handle the selected file
        if (file != null) {
            // You can perform further operations with the selected file here
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            dragAndDropLabel.setVisible(false);
            //Get the file path from the imagePath
            try {
                //Connect Image to UserPhoto class
                photo = new UserPhoto(file.getAbsolutePath());

            } catch (Exception e) {
                popUpBox("Error - could not upload photo");
            }
        }
    }

    //Creates a yes/no box with a phrase and returns two JButton object to assign actions
    private JButton[] yesNoBox(JFrame frame, String phrase) {
        // Create JFrame frame object
        frame.setSize(new Dimension(300,150));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Ask "Are you sure you want to quit?"
        JLabel label = new JLabel(phrase);
        label.setFont(new Font("Optima", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        frame.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2)); // 1 row, 2 columns
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Create YES and NO buttons with labels
        JButton noButton = new JButton("<html><center>No</center></html>");
        JButton yesButton = new JButton("<html><center>Yes</center></html>");
        // Add YES and NO buttons
        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);

        return new JButton[]{noButton, yesButton};
    }

    //Creates a pop up box with a phrase
    private void popUpBox(String phrase) {
        //Create message box
        JFrame popUpFrame = new JFrame();
        popUpFrame.setSize(new Dimension(325, 150));
        popUpFrame.setLocationRelativeTo(null);
        popUpFrame.setVisible(true);

        // Show message
        JLabel label = new JLabel(phrase);
        label.setFont(new Font("Optima", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        popUpFrame.add(label, BorderLayout.CENTER);
    }

}

