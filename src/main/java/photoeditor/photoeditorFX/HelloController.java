package photoeditor.photoeditorFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;


public class HelloController {
    //added for the quit class
    private static Stage mainStage;
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

    public void applyNew() {
        if (photo != null) {
            // Create JFrame quitFrame object
            JFrame newFrame = new JFrame();
            newFrame.setSize(new Dimension(300, 150));
            newFrame.setLocationRelativeTo(null);
            newFrame.setVisible(true);

            // Ask "Do you want to save your photo?
            JLabel label = new JLabel("Do you want to save your photo?");
            label.setFont(new Font("Optima", Font.PLAIN, 16));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            newFrame.add(label, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new GridLayout(1, 2)); // 1 row, 2 columns
            newFrame.add(buttonPanel, BorderLayout.SOUTH);

            // Create YES and NO buttons with labels
            JButton noButton = new JButton("<html><center>No</center></html>");
            JButton yesButton = new JButton("<html><center>Yes</center></html>");
            // Add YES and NO buttons
            buttonPanel.add(noButton);
            buttonPanel.add(yesButton);

            // Add ActionListener to the Yes button
            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Yes button clicked");
                    newFrame.dispose();
                    //EDIT THIS PART TO SAVE PHOTO
                }
            });

            // Add ActionListener to the No button
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("No button clicked");
                    newFrame.dispose();
                }
            });

            newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        //EDIT THIS PART
    }

    @FXML
    public void applyQuit() {
        // Create JFrame quitFrame object
        JFrame quitFrame = new JFrame();
        quitFrame.setSize(new Dimension(300,150));
        quitFrame.setLocationRelativeTo(null);
        quitFrame.setVisible(true);

        // Ask "Are you sure you want to quit?"
        JLabel label = new JLabel("Are you sure you want to quit?");
        label.setFont(new Font("Optima", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        quitFrame.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2)); // 1 row, 2 columns
        quitFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Create YES and NO buttons with labels
        JButton noButton = new JButton("<html><center>No</center></html>");
        JButton yesButton = new JButton("<html><center>Yes</center></html>");
        // Add YES and NO buttons
        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);

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
        //Pull Save Photo method from UserPhoto Object
        if (photo != null) {
            photo.savePhoto(imageView, wim);
        } else {
            //ERROR NO PHOTO TO SAVE
            JFrame saveFrame = new JFrame();
            saveFrame.setSize(new Dimension(300,150));
            saveFrame.setLocationRelativeTo(null);
            saveFrame.setVisible(true);

            // Error - no photo uploaded"
            JLabel label = new JLabel("Error - no photo uploaded");
            label.setFont(new Font("Optima", Font.PLAIN, 16));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            saveFrame.add(label, BorderLayout.CENTER);
        }
    }

}

