package com.github.musify.presentation;

import com.github.musify.data.repository.MusicProcesser;
import com.github.musify.domain.model.Instruments;
import com.github.musify.domain.model.PatternBuilder;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class MusifyController {

    @FXML
    public Spinner<Integer> bpmSpinner;
    @FXML
    public Button stopMusicButton;
    @FXML
    public Button loadFileButton;
    @FXML
    public Button convertMusicButton;

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
    private Button saveToButton;

    private PatternBuilder processedSong;
    private ManagedPlayer managedPlayer;
    private Timeline timeline;

    private MusicProcesser musicProcesser;

    @Autowired
    public void setMusicProcesser(MusicProcesser musicProcesser) {
        this.musicProcesser = musicProcesser;
    }

    public void initialize() {
        textFieldForConvert.textProperty().addListener((observable, oldValue, newValue) -> convertMusicButton.setDisable(newValue.isBlank()));
        SpinnerValueFactory.IntegerSpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(60, 960, 60);
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
        saveToButton.setDisable(true);
    }

    private void unlockUI() {
        textFieldForConvert.setDisable(false);
        convertMusicButton.setDisable(false);
        playMusicButton.setDisable(false);
        bpmSpinner.setDisable(false);
        instrumentChoiceField.setDisable(false);
        stopMusicButton.setDisable(true);
        loadFileButton.setDisable(false);
        saveToButton.setDisable(false);
    }

    @FXML
    private void onGenerateClicked() {
        if (textFieldForConvert.getText().isBlank())
            return;
        lockUI();
        final Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                PatternBuilder defaultBuilder = new PatternBuilder();
                defaultBuilder.setInstrument(Instruments.valueOf(instrumentChoiceField.getValue().toUpperCase(Locale.ROOT).replaceAll(" ", "_")));
                defaultBuilder.setTempo(bpmSpinner.getValue());
                musicProcesser.setConfiguration(defaultBuilder);
                processedSong = musicProcesser.process(textFieldForConvert.getText(), (progress) -> updateProgress(progress, textFieldForConvert.getText().length() - 1));
                processedSong.cleanPatterns();
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
        Pattern build = processedSong.build();
        System.out.println(build.toString());
        new Thread(() -> player.play(build)).start();
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

    @FXML
    private void onSaveToClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save to MIDI file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MIDI file", "*.mid"));
        File selected = fileChooser.showSaveDialog(mainArea.getScene().getWindow());
        if (selected == null)
            return;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(selected);
            MidiFileManager.savePatternToMidi(processedSong.build(), fileOutputStream);
            fileOutputStream.close();
        } catch (Exception ignored) {
        }
    }
}