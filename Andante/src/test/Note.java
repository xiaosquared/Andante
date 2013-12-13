package test;

public class Note {
	
	public NoteType type;
	public int octave;
	
	public Note(NoteType type, int octave) {
		this.type = type;
		this.octave = octave;
	}
		
	public Note oneNoteUp() {
		NoteType newType;
		int newOctave = octave;
		if (type == NoteType.C_SHARP)
			newType = NoteType.F_SHARP;
		else if (type == NoteType.D_SHARP)
			newType = NoteType.G_SHARP;
		else if (type == NoteType.F_SHARP)
			newType = NoteType.A_SHARP;
		else if (type == NoteType.A_SHARP) {
			newType = NoteType.D_SHARP;
			newOctave++;
		} else {
			newType = NoteType.C_SHARP;
			newOctave++;
		}
		return new Note(newType, newOctave);
	}
	
	public Note oneNoteDown() {
		NoteType newType;
		int newOctave = octave;
		if (type == NoteType.A_SHARP)
			newType = NoteType.F_SHARP;
		else if (type == NoteType.G_SHARP)
			newType = NoteType.D_SHARP;
		else if (type == NoteType.F_SHARP)
			newType = NoteType.C_SHARP;
		else if (type == NoteType.D_SHARP) {
			newType = NoteType.A_SHARP;
			newOctave--;
		} else {
			newType = NoteType.G_SHARP;
			newOctave--;
		}
		return new Note(newType, newOctave);
	}
	
	public String toString() {
		String typeString;
		if (type == NoteType.A_SHARP)
			typeString = "A#";
		else if (type == NoteType.G_SHARP)
			typeString = "G#";
		else if (type == NoteType.F_SHARP)
			typeString = "F#";
		else if (type == NoteType.D_SHARP)
			typeString = "D#";
		else 
			typeString = "C#";
		return typeString + " " + octave;
	}

	public int getMidiValue() {
		int noteValue;

		if (type == NoteType.C_SHARP)
			noteValue = 13;
		else if (type == NoteType.D_SHARP)
			noteValue = 15;
		else if (type == NoteType.F_SHARP)
			noteValue = 18;
		else if (type == NoteType.G_SHARP)
			noteValue = 20;
		else 
			noteValue = 22;
		
		return noteValue + 12*octave;
	}

	public int getAnimationX() {
		double noteX;
		switch(type) {
			case C_SHARP:
				noteX = 125;
				break;
			case D_SHARP:
				noteX = 140;
				break;
			case F_SHARP:
				noteX = 164;
				break;
			case G_SHARP:
				noteX = 179;
				break;
			case A_SHARP:
				noteX = 194;
				break;
			default: 
				noteX = 100;
		}
		
		return (int) (noteX + (octave-1) * 92.3);
	}
}
