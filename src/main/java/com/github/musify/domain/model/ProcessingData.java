package com.github.musify.domain.model;

public class ProcessingData {

    private final String previousCharacterRegex, currentCharacterRegex;
    private final String text;

    public ProcessingData(String text) {
        this(text, null, null);
    }

    public ProcessingData(String text, String previousCharacterRegex, String currentCharacterRegex) {
        this.text = text;
        this.previousCharacterRegex = previousCharacterRegex;
        this.currentCharacterRegex = currentCharacterRegex;
    }

    public String getText() {
        return this.text;
    }

    public String getCharacterAt(int offset) {
        int index = this.text.length() - offset - 1;
        if(index < 0 || index >= this.text.length())
            return "";
        return String.valueOf(this.text.charAt(index));
    }

    public String getPreviousCharacter() {
        return getCharacterAt(1);
    }

    public String getCurrentCharacter() {
        return getCharacterAt(0);
    }

    public String getPreviousCharacterRegex() {
        return this.previousCharacterRegex;
    }

    public String getCurrentCharacterRegex() {
        return this.currentCharacterRegex;
    }

    public ProcessingData withRegexes(String previousCharacterRegex, String currentCharacterRegex) {
        return new ProcessingData(this.text, previousCharacterRegex, currentCharacterRegex);
    }
}
