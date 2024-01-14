package com.github.musify.domain.repository;

import com.github.musify.data.repository.MusicProcesser;
import com.github.musify.domain.model.Instruments;
import com.github.musify.domain.model.Note;
import com.github.musify.domain.model.PatternBuilder;
import com.github.musify.domain.model.ProcessingData;

import java.util.Locale;

public class DefaultMusicProcesser extends MusicProcesser {

    public DefaultMusicProcesser() {
        super();
    }

    @Override
    public void setConfiguration(PatternBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void receiveCharacter(ProcessingData processingData) {
        final String breakPattern = "[^A-Ga-g !oOiIuU0-9?\\n;,]";
        ifMatches(processingData.withRegexes(null, "[A-G]"), () -> getBuilder().addNote(new Note(Note.BaseNote.fromString(processingData.getCurrentCharacter().toUpperCase(Locale.ROOT)), getBuilder().getOctave())));
        ifMatches(processingData.withRegexes("[A-G]", breakPattern), () -> getBuilder().addNote(new Note(Note.BaseNote.fromString(processingData.getPreviousCharacter().toUpperCase(Locale.ROOT)), getBuilder().getOctave())));
        ifMatches(processingData.withRegexes("[^A-G]", breakPattern), () -> getBuilder().appendBreak());
        ifMatches(processingData.withRegexes(null, " "), () -> getBuilder().setVolume((short) (getBuilder().getVolume() * 2)));
        ifMatches(processingData.withRegexes(null, "!"), () -> getBuilder().setInstrument(Instruments.AGOGO));
        ifMatches(processingData.withRegexes(null, ";"), () -> getBuilder().setInstrument(Instruments.TUBULAR_BELLS));
        ifMatches(processingData.withRegexes(null, "\n"), () -> getBuilder().setInstrument(Instruments.FLUTE));
        ifMatches(processingData.withRegexes(null, "[iouIOU]"), () -> getBuilder().setInstrument(Instruments.HARPSICHORD));
    }
}
