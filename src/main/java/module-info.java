module com.github.musify {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires jfugue;

    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;

    opens com.github.musify to javafx.fxml, spring.core;
    opens com.github.musify.data.repository to javafx.fxml, spring.core;
    opens com.github.musify.domain.repository to javafx.fxml, spring.core;
    opens com.github.musify.domain.model to javafx.fxml, spring.core;
    opens com.github.musify.presentation to javafx.fxml, spring.core;

    exports com.github.musify;
    exports com.github.musify.data.repository;
    exports com.github.musify.domain.repository;
    exports com.github.musify.domain.model;
    exports com.github.musify.presentation;
}