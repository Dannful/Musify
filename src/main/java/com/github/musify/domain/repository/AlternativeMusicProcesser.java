package com.github.musify.domain.repository;

import com.github.musify.data.repository.MusicProcesser;
import com.github.musify.domain.model.Instruments;
import com.github.musify.domain.model.Note;
import com.github.musify.domain.model.PatternBuilder;
import com.github.musify.domain.model.ProcessingData;

import java.util.Locale;
import java.util.Random;

public class AlternativeMusicProcesser extends MusicProcesser {

    public AlternativeMusicProcesser() {
        super();
    }

    @Override
    public void setConfiguration(PatternBuilder builder) {
        this.builder = builder;
    }

    private void octavePattern(ProcessingData data) {
        if (data.getPreviousCharacter().equals("R")) {
            if (data.getCurrentCharacter().equals("+"))
                getBuilder().setOctave((short) (getBuilder().getOctave() + 1));
            else if (data.getCurrentCharacter().equals("-"))
                getBuilder().setOctave((short) (getBuilder().getOctave() - 1));
        }
    }

    private void bpmPattern(ProcessingData data) {
        if (data.getCurrentCharacter().equals("+") && data.getPreviousCharacter().equals("M")
                && data.getCharacterAt(2).equals("P") && data.getCharacterAt(3).equals("B")) {
            getBuilder().setTempo(getBuilder().getTempo() + 80);
        }
    }

    @Override
    public void receiveCharacter(ProcessingData processingData) {
        ifMatches(processingData.withRegexes(null, "[a-gA-G]"), () -> getBuilder().addNote(new Note(Note.BaseNote.fromString(processingData.getCurrentCharacter().toUpperCase(Locale.ROOT)), getBuilder().getOctave())));
        ifMatches(processingData.withRegexes(null, " "), () -> getBuilder().appendBreak());
        ifMatches(processingData.withRegexes(null, "[+]"), () -> getBuilder().setVolume((short) (getBuilder().getVolume() * 2)));
        ifMatches(processingData.withRegexes(null, "-"), () -> getBuilder().setVolume(PatternBuilder.getDefaultVolume()));
        ifMatches(processingData.withRegexes("[a-gA-G]", "[oOiIuU]"), () -> getBuilder().addNote(new Note(Note.BaseNote.fromString(processingData.getPreviousCharacter().toUpperCase(Locale.ROOT)), getBuilder().getOctave())));
        ifMatches(processingData.withRegexes("[^a-gA-G]", "[oOiIuU]"), () -> getBuilder().playPhone());
        octavePattern(processingData);
        ifMatches(processingData.withRegexes(null, "[?]"), () -> {
            Random random = new Random();
            getBuilder().addNote(new Note(Note.BaseNote.values()[random.nextInt(Note.BaseNote.values().length)], getBuilder().getOctave()));
        });
        ifMatches(processingData.withRegexes(null, "\n"), () -> {
            Random random = new Random();
            getBuilder().setInstrument(Instruments.values()[random.nextInt(Instruments.values().length)]);
        });
        bpmPattern(processingData);
        ifMatches(processingData.withRegexes(null, ";"), () -> {
            Random random = new Random();
            getBuilder().setTempo(random.nextInt(builder.getTempo() + 360));
        });
    }
}
