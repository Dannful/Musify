package com.github.musify.domain.model;

import org.jfugue.pattern.Pattern;

import java.util.Locale;
import java.util.function.Consumer;

public class MusicProcesser {

    private final String input;
    private final PatternBuilder builder;

    private int currentCharacterPosition;

    public MusicProcesser(String input, PatternBuilder builder) {
        this.input = input;
        this.currentCharacterPosition = 0;
        this.builder = builder;
    }

    private boolean appendNote(String character) {
        if (character.matches("[a-g]")) {
            this.builder.addNote(new Note(Note.BaseNote.fromString(character.toUpperCase(Locale.ROOT)), this.builder.getOctave()));
            return true;
        }
        return false;
    }

    private void appendPrevious(String previousCharacter, String character) {
        if (character.matches("[^h-za-z0-9;\n.!,? ]"))
            if (!appendNote(previousCharacter))
                this.builder.appendBreak();
    }

    private void doubleVolume(String character) {
        if (character.equals(" "))
            this.builder.setVolume((short) (this.builder.getVolume() * 2));
    }

    private void updateToAgogo(String character) {
        if (character.equals("!"))
            this.builder.setInstrument(Instruments.AGOGO);
    }

    private void updateToHarpsicord(String character) {
        character = character.toLowerCase(Locale.ROOT);
        if (character.equals("i") || character.equals("o") || character.equals("u"))
            this.builder.setInstrument(Instruments.HARPSICHORD);
    }

    private void updateToTubularBells(String character) {
        if (character.equals(";"))
            this.builder.setInstrument(Instruments.TUBULAR_BELLS);
    }

    private void updateToFlute(String character) {
        if (character.equals("\n"))
            this.builder.setInstrument(Instruments.FLUTE);
    }

    private void updateToChurchOrgan(String character) {
        if (character.equals(","))
            this.builder.setInstrument(Instruments.CHURCH_ORGAN);
    }

    private void increaseOneOctave(String character) {
        if (character.equals(".") || character.equals("?"))
            this.builder.setOctave((short) (this.builder.getOctave() + 1));
    }

    private void updateInstrumentBasedOnCurrent(String character) {
        if (character.matches("[0-9]"))
            this.builder.setInstrument((short) (this.builder.getInstrumentMidiIndex() + Integer.parseInt(character)));
    }

    public int getCurrentCharacterPosition() {
        return this.currentCharacterPosition;
    }

    public PatternBuilder process(Consumer<Integer> updateProgress) {
        String lastCharacter = "";
        for (currentCharacterPosition = 0; currentCharacterPosition < this.input.length(); currentCharacterPosition++) {
            updateProgress.accept(currentCharacterPosition);
            char each = this.input.charAt(currentCharacterPosition);
            String character = String.valueOf(each);
            appendNote(character);
            appendPrevious(lastCharacter, character);
            doubleVolume(character);
            updateToAgogo(character);
            updateToHarpsicord(character);
            updateToTubularBells(character);
            updateToFlute(character);
            updateToChurchOrgan(character);
            increaseOneOctave(character);
            updateInstrumentBasedOnCurrent(character);
            lastCharacter = character;
        }
        this.builder.cleanPattern();
        return this.builder;
    }
}
