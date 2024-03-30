package photoeditor.photoeditorFX;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class HelloController {
    @FXML
    private ComboBox<String> comboBox1;
    @FXML
    private ComboBox<String> comboBox2;
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
    private WritableImage wim;
    //added for the quit class
    private static Stage mainStage;

    public void initialize() {

        ObservableList<String> options1 = FXCollections.observableArrayList(
                "1x",
                "2x",
                "3x",
                "4x",
                "5x"
        );
        comboBox1.setItems(options1);

        ObservableList<String> options2 = FXCollections.observableArrayList(
                "1x",
                "0.5x",
                "0.25x",
                "0.125x",
                "0.0625x"
        );
        comboBox2.setItems(options2);

        //Creating object that'll save image with applied effect, dimensions don't affect final result.
        WritableImage wim = new WritableImage( 800, 600);

        //class that's used to set image effects
        ColorAdjust colorAdjust = new ColorAdjust();


        //listener for each color effect slider to set strength of effect on image
        brightnessSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setBrightness(brightnessSlider.getValue());
            imageView.setEffect(colorAdjust);
            photo.isPhotoEdited();

        });

        contrastSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setContrast(contrastSlider.getValue());
            imageView.setEffect(colorAdjust);
            photo.isPhotoEdited();
        });

        hueSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setHue(hueSlider.getValue());
            imageView.setEffect(colorAdjust);
            photo.isPhotoEdited();
        });

        saturationSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setSaturation(saturationSlider.getValue());
            imageView.setEffect(colorAdjust);
            photo.isPhotoEdited();
        });

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
            Image image = new Image(imagePath);
            imageView.setImage(image);

            //Get the file path from the imagePath
            try {

                URI uri = new URI(imagePath);
                String directoryPath = uri.getPath();

                //Connect Image to UserPhoto class
                photo = new UserPhoto(directoryPath);

            } catch (Exception e) {
                System.out.println("Error in image path");
            }

        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void onDragExited(DragEvent event) {
        event.consume();
    }

    @FXML
    private void applyBlackAndWhite() {
        // Implement the action to be performed when the MenuItem is clicked
        System.out.println("apply black and white");
    }

    @FXML
    public void applyNew() {
        if (photo != null) {
            // Create JFrame quitFrame object and return yes/no buttons to assign actions
            JFrame newFrame = new JFrame();
            JButton[] buttons = yesNoBox(newFrame, "Do you want to save your photo?");
            JButton noButton = buttons[0];
            JButton yesButton = buttons[1];

            // Add ActionListener to the Yes button
            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newFrame.dispose();
                    //EDIT THIS PART TO SAVE PHOTO
                    Platform.runLater(() -> {
                        applySave();
                        deleteImage();
                    });
                }
            });

            // Add ActionListener to the No button
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newFrame.dispose();
                    deleteImage();
                }
            });

            newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    @FXML
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
                    openFrame.dispose();
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
                    openFrame.dispose();
                    deleteImage(); // Deletes photo
                    Platform.runLater(() -> {
                        File selectedFile = selectFile(imageView.getScene().getWindow()); //Selects the file
                        displayFile(selectedFile); //Displays the file
                    });
                }
            });

            openFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            File selectedFile = selectFile(imageView.getScene().getWindow());
            displayFile(selectedFile);
        }
    }

    @FXML
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
                quitFrame.dispose();
                // Exit the entire application
                System.exit(0);
            }
        });

        // Add ActionListener to the No button
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                quitFrame.dispose();
            }
        });

        quitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @FXML
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
                    System.out.println("Image saved successfully.");
                } catch (Exception e) {
                    System.out.println("Error saving image.");
                }
            }
        } else {
            //ERROR NO PHOTO TO SAVE
            errorBox("Error - no photo uploaded");
        }
    }

    // Deletes the photo object and clears the imageView
    private void deleteImage() {
        // Set the ImageView's image property to null
        imageView.setImage(null);
        // Dispose the photo object if it's not null
        if (photo != null) {
            photo = null;
        }
    }

    private File selectFile(javafx.stage.Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        //Only allow certain photos to be uploaded
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        return fileChooser.showOpenDialog(window);

    }

    private void displayFile(File file) {
        // Handle the selected file
        if (file != null) {
            // You can perform further operations with the selected file here
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);

            //Get the file path from the imagePath
            try {
                //Connect Image to UserPhoto class
                photo = new UserPhoto(file.getAbsolutePath());

            } catch (Exception e) {
                System.out.println("Error in image path");
            }
        } else {
            System.out.println("No file selected.");
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

    //Creates an error box pop with a phrase
    private void errorBox(String phrase) {
        JFrame saveFrame = new JFrame();
        saveFrame.setSize(new Dimension(300,150));
        saveFrame.setLocationRelativeTo(null);
        saveFrame.setVisible(true);

        // Show "Error - no photo uploaded"
        JLabel label = new JLabel(phrase);
        label.setFont(new Font("Optima", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        saveFrame.add(label, BorderLayout.CENTER);
    }
}

