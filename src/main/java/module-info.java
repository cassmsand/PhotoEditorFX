module photoeditor.cassiefxtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens photoeditor.photoeditorFX to javafx.fxml;
    exports photoeditor.photoeditorFX;
}