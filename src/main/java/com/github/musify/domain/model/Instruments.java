package com.github.musify.domain.model;

public enum Instruments {

    PIANO((short) 0), AGOGO((short) 114), HARPSICHORD((short) 7), TUBULAR_BELLS((short) 15), FLUTE((short) 76), CHURCH_ORGAN((short) 20);

    private final short midiIndex;

    Instruments(short midiIndex) {
        this.midiIndex = midiIndex;
    }

    public short getMidiIndex() {
        return this.midiIndex;
    }
}
