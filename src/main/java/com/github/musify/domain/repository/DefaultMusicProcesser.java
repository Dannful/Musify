package com.github.musify.domain.repository;

import com.github.musify.data.repository.MusicProcesser;
import com.github.musify.domain.model.Instruments;
import com.github.musify.domain.model.Note;
import com.github.musify.domain.model.PatternBuilder;
import com.github.musify.domain.model.ProcessingData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DefaultMusicProcesser extends MusicProcesser {

    public DefaultMusicProcesser() {
        super();
    }

    @Override
    public void setConfiguration(PatternBuilder builder) {
        this.builder = builder;
    }

    private void ifMatches(ProcessingData processingData, Runnable action) {
        if (processingData.getCurrentCharacterRegex() == null || processingData.getCurrentCharacter().matches(processingData.getCurrentCharacterRegex()))
            if (processingData.getPreviousCharacterRegex() == null || processingData.getPreviousCharacter() == null || processingData.getPreviousCharacter().matches(processingData.getPreviousCharacterRegex()))
                action.run();
    }

    @Override
    public void receiveCharacter(ProcessingData processingData) {
        ifMatches(processingData.withRegexes(null, "[a-g]"), () -> getBuilder().addNote(new Note(Note.BaseNote.fromString(processingData.getCurrentCharacter().toUpperCase(Locale.ROOT)), getBuilder().getOctave())));
        ifMatches(processingData.withRegexes("[a-g]", "[^h-za-z0-9;\n.!,? ]"), () -> getBuilder().appendBreak());
        ifMatches(processingData.withRegexes(null, " "), () -> getBuilder().setVolume((short) (getBuilder().getVolume() * 2)));
        ifMatches(processingData.withRegexes(null, "!"), () -> getBuilder().setInstrument(Instruments.AGOGO));
        ifMatches(processingData.withRegexes(null, ";"), () -> getBuilder().setInstrument(Instruments.TUBULAR_BELLS));
        ifMatches(processingData.withRegexes(null, "\n"), () -> getBuilder().setInstrument(Instruments.FLUTE));
        ifMatches(processingData.withRegexes(null, "i|o|u"), () -> getBuilder().setInstrument(Instruments.HARPSICHORD));
    }
}
