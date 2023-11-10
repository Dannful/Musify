package com.github.musify;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class StageInitializer implements ApplicationListener<MusifyApplication.StageReadyEvent> {

    private final ApplicationContext applicationContext;

    public StageInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(MusifyApplication.StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("musify-view.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Stage stage = event.getStage();
            Scene scene = new Scene(fxmlLoader.load(), 640, 400);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.jpg"))));
            stage.setResizable(false);
            stage.setTitle("Musify");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
