package com.github.musify.data.repository;

import com.github.musify.domain.model.PatternBuilder;
import com.github.musify.domain.model.ProcessingData;

import java.util.function.Consumer;

public abstract class MusicProcesser {

    protected PatternBuilder builder;

    public MusicProcesser() {
        this.builder = new PatternBuilder();
    }

    protected PatternBuilder getBuilder() {
        return this.builder;
    }

    public abstract void receiveCharacter(ProcessingData processingData);
    public abstract void setConfiguration(PatternBuilder builder);

    public PatternBuilder process(String input, Consumer<Integer> updateProgress) {
        for (int currentCharacterPosition = 0; currentCharacterPosition < input.length(); currentCharacterPosition++) {
            String currentCharacter = String.valueOf(input.charAt(currentCharacterPosition));
            String previousCharacter = currentCharacterPosition == 0 ? null : String.valueOf(input.charAt(currentCharacterPosition - 1));
            receiveCharacter(new ProcessingData(previousCharacter, currentCharacter));
            updateProgress.accept(currentCharacterPosition);
        }
        this.builder.cleanPattern();
        return this.builder;
    }
}
