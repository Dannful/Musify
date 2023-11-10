package com.github.musify.domain.model;

import java.util.Locale;

public class DefaultMusicProcesser extends MusicProcesser {

    public DefaultMusicProcesser(String input, Instruments defaultInstrument, short defaultTempo) {
        super(input);
        getBuilder().setInstrument(defaultInstrument);
        getBuilder().setTempo(defaultTempo);
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
