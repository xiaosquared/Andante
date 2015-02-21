package scales.data;

public class Scale {
	protected int[] notes;
	protected int total_octaves = 2;
	protected int note_index;			// counter for index of next note
	protected boolean going_up = true;
	

	protected int current_note;
	protected int next_note;
	protected int prev_note;
	protected int prev2_note; 
	
	protected boolean useThirds = false;
	protected int[] thirds;
	protected int current_note_thirds;
	protected int next_note_thirds;
	protected int prev_note_thirds;
	protected int prev2_note_thirds; 
	
	protected boolean useContrary = false;
	protected int[] contrary;
	protected int current_note_contrary;
	protected int next_note_contrary;
	protected int prev_note_contrary;
	protected int prev2_note_contrary; 
	
	
	public Scale() {
		note_index = 0;
	}
	
	public void setNotes(int[] notes) {
		this.notes = notes;
		
		next_note = notes[0];
	}
	
	public void setThirds(int[] thirds) {
		this.thirds = thirds;
		next_note_thirds = thirds[0];
		useThirds = true;
	}
	
	public void setContrary(int[] contrary) {
		this.contrary = contrary;
		next_note_contrary = contrary[0];
		useContrary = true;
	}
	
	/**
	 * 
	 * @return progresses everything to the next note
	 */
	public void updateNotes() {
		
		// we've already gotten the next note in the previous iteration, so just set it as the current note
		prev2_note = prev_note;
		prev_note = current_note;
		current_note = next_note;
		
		if (useThirds) {
			prev2_note_thirds = prev_note_thirds;
			prev_note_thirds = current_note_thirds;
			current_note_thirds = next_note_thirds;
		}
		
		if (useContrary) {
			prev2_note_contrary = prev_note_contrary;
			prev_note_contrary = current_note_contrary;
			current_note_contrary = next_note_contrary;
		}
		
		// the rest of the code is really to find the next note
		
		// which octave of the scale we are on
		int current_octave = note_index / notes.length;	
		
		// which note of the scale we are on
		int current_note_index = note_index % notes.length;
		
		// if we're at the very top of the scale
		if (current_note_index == 0 && current_octave == total_octaves) {
			
			note_index--;
			going_up = false;
			
		} else {	
			if ((note_index == 0) && !going_up)
				going_up = true;
			
			if (going_up)
				note_index++;
			else
				note_index--;
		}
	
		setNextNote();	// presets the next note
	}
	
	public void setNextNote() {
		// which octave of the scale we are on
		int current_octave = note_index / notes.length;
		
		// which note of the scale we are on
		int current_note_index = note_index % notes.length;
		
		if (current_note_index == 0 && current_octave == total_octaves) {
			next_note = notes[0] + (total_octaves * 12);
			if (useThirds)
				next_note_thirds = thirds[0] + (total_octaves * 12);
			if (useContrary)
				next_note_contrary = contrary[0] - (total_octaves * 12);
		} else {
			next_note = notes[current_note_index] + (current_octave * 12);
			if (useThirds)
				next_note_thirds = thirds[current_note_index] + (current_octave * 12);
			if (useContrary)
				next_note_contrary = contrary[current_note_index] - (current_octave * 12);
		}
	}
	
	public int getNextNote() {
		return next_note;
	}
	
	public int getPrevNote() {
		return prev_note;
	}
	
	public int getPrev2Note() {
		return prev2_note;
	}
	
	public int getCurrentNote() {
		return current_note;
	}
	
	
	public int getNextNoteThirds() {
		return next_note_thirds;
	}
	
	public int getPrevNoteThirds() {
		return prev_note_thirds;
	}
	
	public int getCurrentNoteThirds() {
		return current_note_thirds;
	}

	public int getNextNoteContrary() {
		return next_note_contrary;
	}
	
	public int getPrevNoteContrary() {
		return prev_note_contrary;
	}
	
	public int getCurrentNoteContrary() {
		return current_note_contrary;
	}

	
	public int getIndex() {
		return note_index;
	}
}
