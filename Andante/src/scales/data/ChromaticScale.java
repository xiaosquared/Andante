package scales.data;

public class ChromaticScale extends Scale {
	
	public ChromaticScale() {
		super();
		
		int notes[] = {61, 62, 63, 64, 
						65, 66, 67, 68, 
						69, 70, 71, 72,
						73, 72, 71, 70,
						69, 68, 67, 66,
						65, 64, 63, 62};
		
		prev_note = 60;
		prev2_note = 59;
		
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
