package com.github.musify.domain.model;

import org.jfugue.pattern.Pattern;

import java.util.regex.Matcher;

public class PatternBuilder {

    private static final short DEFAULT_VOLUME = 60;
    private static final Instruments DEFAULT_INSTRUMENT = Instruments.PIANO;
    private static final short DEFAULT_OCTAVE = 5;
    private static final int DEFAULT_TEMPO = 60;

    private final Pattern pattern;
    private short volume;
    private short instrument;
    private short octave;
    private int tempo;

    public PatternBuilder() {
        this.instrument = DEFAULT_INSTRUMENT.getMidiIndex();
        this.volume = DEFAULT_VOLUME;
        this.octave = DEFAULT_OCTAVE;
        this.tempo = DEFAULT_TEMPO;
        this.pattern = new Pattern();
    }

    private void appendHeader() {
        this.pattern.add("| I" + this.instrument + " T" + this.tempo + " :CON(7, " + volume + ")");
    }

    public void setVolume(short newVolume) {
        if (!(newVolume < 0 || newVolume > 127))
            newVolume = DEFAULT_VOLUME;
        this.volume = newVolume;
        appendHeader();
    }

    public void setTempo(int newTempo) {
        if (this.tempo == newTempo)
            return;
        this.tempo = newTempo;
        appendHeader();
    }

    public void setInstrument(short newInstrumentMidiIndex) {
        if (this.instrument == newInstrumentMidiIndex)
            return;
        this.instrument = newInstrumentMidiIndex;
        appendHeader();
    }

    public void setInstrument(Instruments newInstrument) {
        setInstrument(newInstrument.getMidiIndex());
    }

    public void setOctave(short newOctave) {
        if (newOctave < 0 || newOctave > 10)
            newOctave = DEFAULT_OCTAVE;
        this.octave = newOctave;
    }

    public void addNote(Note note) {
        pattern.add(note.toPatternString() + " ");
    }

    public void appendBreak() {
        pattern.add("R/2 ");
        setVolume(volume);
    }

    public void cleanPattern() {
        String aux = pattern.toString();
        pattern.clear();
        aux = aux.trim().replaceAll(" +", " ");
        Matcher matcher = java.util.regex.Pattern.compile("[^|]+(?<=[A-G][0-9]{1,2})(?: \\||$)").matcher(aux);
        StringBuilder newPattern = new StringBuilder();
        while (matcher.find())
            newPattern.append(matcher.group());
        String result = newPattern.toString().trim();
        if(result.startsWith("|"))
            result = result.substring(1);
        if(result.endsWith("|"))
            result = result.substring(0, result.length() - 1);
        pattern.add(result);
    }

    public int getTempo() {
        return this.tempo;
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