package com.github.musify.domain.model;

import org.jfugue.pattern.Pattern;

public class PatternBuilder {

    private static final short DEFAULT_VOLUME = 60;
    private static final Instruments DEFAULT_INSTRUMENT = Instruments.PIANO;
    private static final short DEFAULT_OCTAVE = 5;

    private final Pattern pattern;
    private short volume;
    private short instrument;
    private short octave;

    public PatternBuilder() {
        this.instrument = DEFAULT_INSTRUMENT.getMidiIndex();
        this.volume = DEFAULT_VOLUME;
        this.octave = DEFAULT_OCTAVE;
        this.pattern = new Pattern();
    }

    public void setVolume(short newVolume) {
        if (!(newVolume < 0 || newVolume > 127))
            newVolume = DEFAULT_VOLUME;
        this.volume = newVolume;
        pattern.add("| :CON(7, " + volume + ") ");
    }

    public void setInstrument(short newInstrumentMidiIndex) {
        if(this.instrument == newInstrumentMidiIndex)
            return;
        this.instrument = newInstrumentMidiIndex;
        pattern.add("| I" + newInstrumentMidiIndex + " ");
    }

    public void setInstrument(Instruments newInstrument) {
        setInstrument(newInstrument.getMidiIndex());
    }

    public void setOctave(short newOctave) {
        if (!(newOctave < 0 || newOctave > 10))
            newOctave = DEFAULT_OCTAVE;
        this.octave = newOctave;
    }

    public void addNote(Notes note) {
        pattern.add(note.name() + octave + "s ");
    }

    public void appendBreak() {
        pattern.add("| :CON(7, 0) C C C C C C ");
        setVolume(volume);
    }

    public void cleanPattern() {
        String aux = pattern.toString();
        pattern.clear();
        pattern.add(aux.trim().replaceAll(" +", " "));
    }

    public short getVolume() {
        return this.volume;
    }

    public short getOctave() {
        return this.octave;
    }

    public short getInstrumentMidiIndex() {
        return this.instrument;
    }

    public Pattern build() {
        return this.pattern;
    }
}