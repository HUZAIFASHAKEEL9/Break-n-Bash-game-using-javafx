module com.example.breaknbash {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.breaknbash to javafx.fxml;
    exports com.example.breaknbash;
}