package com.github.musify.presentation;

import com.github.musify.domain.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class MusifyController {

    @FXML
    public Spinner<Integer> bpmSpinner;
    @FXML
    public Button stopMusicButton;
    @FXML
    public Button loadFileButton;
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
    private ChoiceBox<String> instrumentChoiceField;

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

    private PatternBuilder processedSong;
    private ManagedPlayer managedPlayer;
    private Timeline timeline;

    public void initialize() {
        textFieldForConvert.textProperty().addListener((observable, oldValue, newValue) -> convertMusicButton.setDisable(newValue.isBlank()));
        SpinnerValueFactory.IntegerSpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(60, 1000, 60);
        spinnerValueFactory.setAmountToStepBy(60);
        bpmSpinner.setValueFactory(spinnerValueFactory);
    }

    private void lockUI() {
        textFieldForConvert.setDisable(true);
        convertMusicButton.setDisable(true);
        playMusicButton.setDisable(true);
        bpmSpinner.setDisable(true);
        instrumentChoiceField.setDisable(true);
        loadFileButton.setDisable(true);
    }

    private void unlockUI() {
        textFieldForConvert.setDisable(false);
        convertMusicButton.setDisable(false);
        playMusicButton.setDisable(false);
        bpmSpinner.setDisable(false);
        instrumentChoiceField.setDisable(false);
        stopMusicButton.setDisable(true);
        loadFileButton.setDisable(false);
    }

    @FXML
    private void onGenerateClicked() {
        lockUI();
        final Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                MusicProcesser processer = new DefaultMusicProcesser(textFieldForConvert.getText(), Instruments.valueOf(instrumentChoiceField.getValue().toUpperCase(Locale.ROOT).replaceAll(" ", "_")), bpmSpinner.getValue().shortValue());
                processedSong = processer.process((progress) -> updateProgress(progress, textFieldForConvert.getText().length() - 1));
                unlockUI();
                return null;
            }
        };
        convertProcessBar.progressProperty().bind(task.progressProperty());
        Thread taskThread = new Thread(task);
        taskThread.start();
    }


    @FXML
    private void onPlayClicked() {
        lockUI();
        stopMusicButton.setDisable(false);
        Player player = new Player();
        managedPlayer = player.getManagedPlayer();
        new Thread(() -> player.play(processedSong.build())).start();
        timeline = new Timeline();
        musicProgress.setMax(processedSong.getDuration());
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(musicProgress.valueProperty(), 0d)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(processedSong.getDuration()), new KeyValue(musicProgress.valueProperty(), processedSong.getDuration())));
        timeline.play();
        timeline.setOnFinished(event -> unlockUI());
    }

    @FXML
    private void onStopClicked() {
        managedPlayer.finish();
        timeline.stop();
        musicProgress.setValue(0);
        unlockUI();
    }

    @FXML
    private void onLoadFileClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open text file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selected = fileChooser.showOpenDialog(mainArea.getScene().getWindow());
        if (selected == null)
            return;
        try {
            FileInputStream fileInputStream = new FileInputStream(selected);
            BufferedReader bufferedReader = new BufferedReader(new java.io.InputStreamReader(fileInputStream));
            textFieldForConvert.setText(bufferedReader.lines().collect(Collectors.joining("\n")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}