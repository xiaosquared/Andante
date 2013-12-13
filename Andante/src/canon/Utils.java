package canon;

public class Utils {
	
	
	static int getMidiNumber(String note) {
		if (note.equals("G2"))
			return 43;
		if (note.equals("A2"))
			return 45;
		if (note.equals("B2"))
			return 47;
		if (note.equals("C3"))
			return 48;
		if (note.equals("D3"))
			return 50;
		if (note.equals("E3"))
			return 52;
		if (note.equals("F3"))
			return 53;
		if (note.equals("G3"))
			return 55;
		if (note.equals("A3"))
			return 57;
		if (note.equals("B3"))
			return 59;
		if (note.equals("C4"))
			return 60;
		if (note.equals("D4"))
			return 62;
		if (note.equals("E4"))
			return 64;
		if (note.equals("F4"))
			return 65;
		if (note.equals("G4"))
			return 67;
		if (note.equals("A4"))
			return 69;
		if (note.equals("B4"))
			return 71;
		if (note.equals("C5"))
			return 72;
		if (note.equals("D5"))
			return 74;
		if (note.equals("E5"))
			return 76;
		if (note.equals("F5"))
			return 77;
		if (note.equals("G5"))
			return 79;
		if (note.equals("A5"))
			return 81;
		if (note.equals("B5"))
			return 83;
		else
			return 0;
	}
}
