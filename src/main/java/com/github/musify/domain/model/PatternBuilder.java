package com.github.musify.domain.model;

import org.jfugue.pattern.Pattern;

import java.util.Stack;
import java.util.regex.Matcher;

public class PatternBuilder {

    private static class CustomPattern {

        private final Pattern pattern;
        private final boolean isForeign;

        public CustomPattern(Pattern pattern) {
            this(pattern, false);
        }

        public CustomPattern(Pattern pattern, boolean isForeign) {
            this.pattern = pattern;
            this.isForeign = isForeign;
        }

        public Pattern getPattern() {
            return this.pattern;
        }

        public boolean isForeign() {
            return this.isForeign;
        }
    }

    private static final short DEFAULT_VOLUME = 60;
    private static final Instruments DEFAULT_INSTRUMENT = Instruments.PIANO;
    private static final short DEFAULT_OCTAVE = 5;
    private static final int DEFAULT_TEMPO = 60;

    private final Stack<CustomPattern> patterns = new Stack<>();

    private short volume;
    private short instrument;
    private short octave;
    private int tempo;
    private double duration;

    public static short getDefaultVolume() {
        return DEFAULT_VOLUME;
    }

    public PatternBuilder() {
        this(DEFAULT_INSTRUMENT, DEFAULT_TEMPO);
    }

    public PatternBuilder(Instruments instrument, int tempo) {
        patterns.push(new CustomPattern(new Pattern()));
        this.octave = DEFAULT_OCTAVE;
        setVolume(DEFAULT_VOLUME);
        setInstrument(instrument);
        setTempo(tempo);
        this.duration = 0;
    }

    private void appendHeader() {
        patterns.peek().getPattern().add("| I" + this.instrument + " T" + this.tempo + " :CON(7, " + volume + ")");
    }

    public void setVolume(short newVolume) {
        if (newVolume < 0 || newVolume > 127)
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
        if (newOctave <= org.jfugue.theory.Note.MIN_OCTAVE || newOctave >= org.jfugue.theory.Note.MAX_OCTAVE)
            newOctave = DEFAULT_OCTAVE;
        this.octave = newOctave;
    }

    public void addNote(Note note) {
        patterns.peek().getPattern().add(note.toPatternString() + " ");
        duration += 1000 / (tempo / 60d);
    }

    public void appendBreak() {
        patterns.peek().getPattern().add("| T60 :CON(7,0) C5w C5w C5w | ");
        appendHeader();
        duration += 3000;
    }

    public void playPhone() {
        String ringPattern = "| T70 KEY:Cmaj I112 :CE(7,127) I112 :CE(7,127) I9 :CE(7,127) :CE(7,127) G#7/0.010416666666666666a115 R/0.009114583333333334 G#7oa127 Ro E7/0.010416666666666666a105 R/0.00390625 G#7/0.010416666666666666a116 E7oa127 R/0.0013020833333333356 E7/0.010416666666666666a99 G#7/0.006510416666666667a127 R/0.002604166666666668 G#7oa97 R/0.0013020833333333356 E7oa127 R/0.005208333333333336 E7oa113 R/0.006510416666666664 G#7oa116 R/0.00390625 G#7/0.006510416666666667a127 E7/0.006510416666666667a127 R/0.005208333333333336 E7oa103 R/0.006510416666666671 G#7oa98 R/0.005208333333333336 G#7oa127 R/0.006510416666666664 E7oa101 E7/0.006510416666666667a127 R/0.006510416666666657 G#7oa117 G#7oa127 R/0.005208333333333329 E7oa97 E7/0.006510416666666667a127 R/0.005208333333333329 G#7oa111 R/0.006510416666666671 E7oa106 R/0.005208333333333329 G#7oa127 R/0.006510416666666671 G#7oa116 E7oa127 R/0.005208333333333329 E7oa109 R/0.00390625 G#7oa127 R/0.005208333333333329 G#7oa103 R/0.006510416666666671 E7oa107 E7oa127 G#7/0.006510416666666667a127 R/0.007812500000000014 G#7oa98 E7oa127 Ro E7oa107 R/0.00390625 G#7oa109 R/0.00390625 G#7/0.006510416666666667a127 R/0.005208333333333343 E7oa102 R/0.0013020833333333426 E7oa127 R/0.006510416666666657 G#7oa115 G#7/0.006510416666666667a127 R/0.005208333333333315 E7oa112 R/0.005208333333333343 G#7oa117 R/0.0013020833333333426 E7oa127 R/0.005208333333333343 E7oa97 R/0.002604166666666685 G#7/0.006510416666666667a127 Ro G#7oa114 E7oa127 Ro E7oa105 G#7/0.006510416666666667a127 R/0.005208333333333315 G#7oa98 R/0.0013020833333333426 E7oa127 R/0.006510416666666657 E7oa108 R/0.006510416666666685 G#7oa116 R/0.00390625 G#7/0.006510416666666667a127 E7/0.006510416666666667a127 R/0.005208333333333343 E7oa113 R/0.005208333333333315 G#7oa105 R/0.005208333333333343 G#7oa127 R/0.006510416666666657 E7oa114 E7/0.006510416666666667a127 R/0.006510416666666685 G#7oa113 G#7oa127 R/0.005208333333333343 E7oa112 E7/0.006510416666666667a127 R/0.006510416666666657 G#7oa108 R/0.006510416666666685 E7oa108 R/0.00520833333333337 G#7oa127 R/0.005208333333333315 G#7oa99 E7oa127 R/0.00651041666666663 E7oa100 R/0.00390625 G#7oa127 R/0.006510416666666685 G#7oa113 R/0.00520833333333337 E7oa115 E7oa127 G#7/0.006510416666666667a127 R/0.006510416666666685 G#7oa101 E7oa127 Ro E7oa99 R/0.00651041666666663 G#7oa110 R/0.00390625 G#7/0.006510416666666667a127 R/0.00390625 E7oa107 R/0.0013020833333333148 E7oa127 R/0.00520833333333337 G#7oa103 G#7/0.006510416666666667a127 R/0.00651041666666663 E7oa113 R/0.0013020833333333148 E7oa127 :CE(7,127) R/0.7981770833333333 G#7/0.010416666666666666a115";
        long measuredRingTime = 6305;
        patterns.push(new CustomPattern(new Pattern(ringPattern), true));
        duration += measuredRingTime;
    }

    public void cleanPatterns() {
        for (CustomPattern pattern : patterns) {
            if (pattern.isForeign())
                continue;
            String aux = pattern.getPattern().toString();
            pattern.getPattern().clear();
            aux = aux.trim().replaceAll("\\s+", " ");
            Matcher matcher = java.util.regex.Pattern.compile("[^|]+(?<=(?:[A-GR]|R/)[0-9]{1,2}[qhw])(?: \\||$)").matcher(aux);
            StringBuilder newPattern = new StringBuilder();
            while (matcher.find())
                newPattern.append(matcher.group());
            String result = newPattern.toString().trim();
            if (result.startsWith("|"))
                result = result.substring(1);
            if (result.endsWith("|"))
                result = result.substring(0, result.length() - 1);
            pattern.getPattern().add(result);
        }
    }

    public short getVolume() {
        return this.volume;
    }

    public short getOctave() {
        return this.octave;
    }

    public Pattern build() {
        Pattern resultingPattern = new Pattern();
        patterns.forEach(pattern -> resultingPattern.add(pattern.getPattern()));
        return resultingPattern;
    }

    public String toString() {
        final StringBuilder resultingPattern = new StringBuilder();
        patterns.forEach(pattern -> resultingPattern.append(pattern.getPattern().toString()));
        return resultingPattern.toString();
    }

    public double getDuration() {
        return this.duration;
    }

    public int getTempo() {
        return this.tempo;
    }
}