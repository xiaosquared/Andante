package boogie;

import scales.data.Scale;

public class BoogieLine extends Scale {
	
	public BoogieLine() {
		super();
		
		int[] notes = 	{36, 40, 43, 45, 46, 45, 43, 40, 
						36, 40, 43, 45, 46, 45, 43, 40, 
						41, 45, 48, 50, 51, 50, 48, 45,
						36, 40, 43, 45, 46, 45, 43, 40, 
						43, 47, 50, 47, 41, 45, 48, 45,
						36, 40, 43, 45, 46, 45, 43, 40};
		System.out.println("notes " + notes.length);
		prev_note = 35;
		prev2_note = 33;
		super.setNotes(notes);
	}
	
	public void updateNotes() {
		// we've already gotten the next note in the previous iteration, so just set it as the current note
		prev2_note = prev_note;
		prev_note = current_note;
		current_note = next_note;
		
		// increment note_index. set to 0 if at end 
		note_index++;
		if (note_index == notes.length)
			note_index = 0;
		
		// find the next note that's coming
		setNextNote();
	}
	
	public void setNextNote() {
		next_note = notes[note_index];
	}

}
