module photoeditor.cassiefxtest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens photoeditor.cassiefxtest to javafx.fxml;
    exports photoeditor.cassiefxtest;
}