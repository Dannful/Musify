package com.github.musify.domain.model;

public class ProcessingData {

    private final String previousCharacter, currentCharacter;
    private final String previousCharacterRegex, currentCharacterRegex;

    public ProcessingData(String previousCharacter, String currentCharacter) {
        this(previousCharacter, currentCharacter, null, null);
    }

    public ProcessingData(String previousCharacter, String currentCharacter, String previousCharacterRegex, String currentCharacterRegex) {
        this.previousCharacter = previousCharacter;
        this.currentCharacter = currentCharacter;
        this.previousCharacterRegex = previousCharacterRegex;
        this.currentCharacterRegex = currentCharacterRegex;
    }

    public String getPreviousCharacter() {
        return this.previousCharacter;
    }

    public String getCurrentCharacter() {
        return this.currentCharacter;
    }

    public String getPreviousCharacterRegex() {
        return this.previousCharacterRegex;
    }

    public String getCurrentCharacterRegex() {
        return this.currentCharacterRegex;
    }

    public ProcessingData withRegexes(String previousCharacterRegex, String currentCharacterRegex) {
        return new ProcessingData(this.previousCharacter, this.currentCharacter, previousCharacterRegex, currentCharacterRegex);
    }
}
