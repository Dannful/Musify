package com.github.musify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;

import java.io.IOException;

public class MusifyApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Fase_1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage.setTitle("Musify");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private RadioButton bpmButton;

    @FXML
    public Button convertMusicButton;

    @FXML
    private MenuBar menu;

    @FXML
    private TextArea fileField;

    @FXML
    private Button playMusicButton;

    @FXML
    private TextArea textFieldForConvert;

    @FXML
    private ChoiceBox<?> instrumentChoiceFild;

    @FXML
    private ProgressBar convertProcessBar;

    @FXML
    private Slider musicProgress;

    @FXML
    private AnchorPane mainArea;

    @FXML
    private Menu help;

    @FXML
    private Label labelBpm;

    @FXML
    private Label labelFile;

    @FXML
    private Label labelInstruments;

    @FXML
    private Separator separator;

}

