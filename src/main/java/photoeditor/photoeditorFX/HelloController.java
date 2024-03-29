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

    public void initialize() {
        WritableImage wim = new WritableImage(800, 600);
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

        //class that's used to set image effects
        ColorAdjust colorAdjust = new ColorAdjust();


        //listener for each color effect slider to set strength of effect on image
        brightnessSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setBrightness(brightnessSlider.getValue());
            imageView.setEffect(colorAdjust);

        });

        contrastSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setContrast(contrastSlider.getValue());
            imageView.setEffect(colorAdjust);

        });

        hueSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setHue(hueSlider.getValue());
            imageView.setEffect(colorAdjust);

        });

        saturationSlider.valueProperty().addListener((observableValue, number, t1) -> {
            colorAdjust.setSaturation(saturationSlider.getValue());
            imageView.setEffect(colorAdjust);

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
    public void applySave() {
        imageView.snapshot(null,wim);
        photo.savePhoto(imageView,wim);
    }
}

