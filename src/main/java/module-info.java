module com.github.musify {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires jfugue;

    opens com.github.musify to javafx.fxml;
    exports com.github.musify.domain.model;
    opens com.github.musify.domain.model to javafx.fxml;
    exports com.github.musify.presentation;
    opens com.github.musify.presentation to javafx.fxml;
    exports com.github.musify;
}