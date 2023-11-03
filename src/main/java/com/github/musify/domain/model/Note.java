package com.github.musify.domain.model;
public class Note {

    public enum BaseNote {
        C, Cmajor, D, Dmajor, E, F, Fmajor, G, Gmajor, A, Amajor, B;

        public static BaseNote fromString(String note) {
            return switch (note) {
                case "C" -> C;
                case "C#", "Db" -> Cmajor;
                case "D" -> D;
                case "D#", "Eb" -> Dmajor;
                case "E" -> E;
                case "F" -> F;
                case "F#", "Gb" -> Fmajor;
                case "G" -> G;
                case "G#", "Ab" -> Gmajor;
                case "A" -> A;
                case "A#", "Bb" -> Amajor;
                case "B" -> B;
                default -> throw new IllegalArgumentException("Invalid note: " + note);
            };
        }
    }

    private final BaseNote note;
    private final short octave;

    public Note(BaseNote note, short octave) {
        this.note = note;
        this.octave = octave;
    }

    public String toPatternString() {
        return note.toString() + octave;
    }
}
