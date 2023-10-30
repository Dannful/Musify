package com.github.musify.presentation;

import com.github.musify.domain.model.MusicProcesser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class MusifyController {

    @FXML
    private TextArea inputText;

    @FXML
    private Button convertButton;

    @FXML
    private void onConvertClick() {
        final String text = inputText.getText();
        if (text.isBlank())
            return;
        MusicProcesser processer = new MusicProcesser(text);
        Pattern pattern = processer.process();
        Player player = new Player();
        new Thread(() -> player.play(pattern), "Music playing").start();
    }
}