package com.github.musify.domain.model;

import java.util.function.Consumer;

public abstract class MusicProcesser {

    private final String input;
    private final PatternBuilder builder;

    public MusicProcesser(String input) {
        this.input = input;
        this.builder = new PatternBuilder();
    }

    protected PatternBuilder getBuilder() {
        return this.builder;
    }

    public abstract void receiveCharacter(ProcessingData processingData);

    public PatternBuilder process(Consumer<Integer> updateProgress) {
        for (int currentCharacterPosition = 0; currentCharacterPosition < this.input.length(); currentCharacterPosition++) {
            String currentCharacter = String.valueOf(this.input.charAt(currentCharacterPosition));
            String previousCharacter = currentCharacterPosition == 0 ? null : String.valueOf(this.input.charAt(currentCharacterPosition - 1));
            receiveCharacter(new ProcessingData(previousCharacter, currentCharacter));
            updateProgress.accept(currentCharacterPosition);
        }
        this.builder.cleanPattern();
        return this.builder;
    }
}
