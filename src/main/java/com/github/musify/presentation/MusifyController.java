package com.github.musify.presentation;

import com.github.musify.domain.model.MusicProcesser;
import com.github.musify.domain.model.Note;
import com.github.musify.domain.model.PatternBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.jfugue.player.Player;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

public class MusifyController {

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

    private void playSomethingCool() {
        Player player = new Player();
        PatternBuilder builder = new PatternBuilder();
        builder.addNote(new Note(Note.BaseNote.A, (short) 2));
        builder.addNote(new Note(Note.BaseNote.E, (short) 4));
        builder.addNote(new Note(Note.BaseNote.E, (short) 4));
        builder.addNote(new Note(Note.BaseNote.C, (short) 4));
        builder.addNote(new Note(Note.BaseNote.B, (short) 3));
        builder.addNote(new Note(Note.BaseNote.B, (short) 3));
        builder.addNote(new Note(Note.BaseNote.B, (short) 3));
        builder.addNote(new Note(Note.BaseNote.B, (short) 3));
        builder.addNote(new Note(Note.BaseNote.C, (short) 4));
        builder.addNote(new Note(Note.BaseNote.A, (short) 3));
        builder.addNote(new Note(Note.BaseNote.E, (short) 4));
        builder.addNote(new Note(Note.BaseNote.E, (short) 4));
        builder.addNote(new Note(Note.BaseNote.C, (short) 4));
        builder.cleanPattern();
        new Thread(() -> player.play(builder.build()), "Cool music").start();
    }

    @FXML
    private void onConvertClick() {
        final String text = textFieldForConvert.getText();
        if (text.isBlank())
            return;
        MusicProcesser processer = new MusicProcesser(text);
        new Thread(() -> new Player().play(processer.process()), "Main music").start();
//        new Thread(this::playSomethingCool, "Main music").start(); uncomment this to listen to something cool
    }
}