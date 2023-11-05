package com.github.musify;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MusifyApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MusifyApplication.class.getResource("musify-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        stage.getIcons().add(new Image("file:src/main/resources/com/github/musify/icon.jpg"));
        stage.setResizable(false);
        stage.setTitle("Musify");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}