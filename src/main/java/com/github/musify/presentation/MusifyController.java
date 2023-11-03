package com.github.musify.presentation;

import com.github.musify.domain.model.MusicProcesser;
import com.github.musify.domain.model.Note;
import com.github.musify.domain.model.PatternBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.jfugue.player.Player;

public class MusifyController {

    @FXML
    private TextArea inputText;

    @FXML
    private Button convertButton;

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
        final String text = inputText.getText();
        if (text.isBlank())
            return;
        MusicProcesser processer = new MusicProcesser(text);
        new Thread(() -> new Player().play(processer.process()), "Main music").start();
//        new Thread(this::playSomethingCool, "Main music").start(); uncomment this to listen to something cool
    }
}