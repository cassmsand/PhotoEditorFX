package photoeditor.cassiephotoeditorFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class HelloController {
    @FXML
    private ComboBox<String> comboBox1;
    @FXML
    private ComboBox<String> comboBox2;
    @FXML
    private ImageView imageView;
   // @FXML
    //private MenuItem applyblackandwhite;

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
    }

    @FXML
    private void onDragOver(DragEvent event) {
        if (event.getGestureSource() != imageView && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            String imagePath = db.getFiles().get(0).toURI().toString();
            Image image = new Image(imagePath);
            imageView.setImage(image);
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
}