package com.github.musify.data.repository;

import com.github.musify.domain.model.PatternBuilder;
import com.github.musify.domain.model.ProcessingData;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MusicProcesser {

    protected void ifMatches(ProcessingData processingData, Runnable action) {
        if (processingData.getCurrentCharacterRegex() == null || processingData.getCurrentCharacter().matches(processingData.getCurrentCharacterRegex()))
            if (processingData.getPreviousCharacterRegex() == null || processingData.getPreviousCharacter() == null || processingData.getPreviousCharacter().matches(processingData.getPreviousCharacterRegex()))
                action.run();
    }

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
        final StringBuilder currentProcessing = new StringBuilder();
        for (int currentCharacterPosition = 0; currentCharacterPosition < input.length(); currentCharacterPosition++) {
            String currentCharacter = String.valueOf(input.charAt(currentCharacterPosition));
            currentProcessing.append(currentCharacter);
            receiveCharacter(new ProcessingData(currentProcessing.toString()));
            updateProgress.accept(currentCharacterPosition);
        }
        return this.builder;
    }
}
