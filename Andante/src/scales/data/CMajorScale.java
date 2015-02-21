package scales.data;

public class CMajorScale extends Scale {

	
	public CMajorScale() {
		super();
		
		int[] notes = {60, 62, 64, 65, 67, 69, 71};
		int[] thirds = {64, 65, 67, 69, 71, 72, 74};
		int[] contrary = {60, 59, 57, 55, 53, 52, 50}; 
		
		prev_note = 59;
		prev2_note = 57;
		
		prev_note_thirds = 62;
		prev2_note_thirds = 60;

		prev_note_contrary = 62;
		prev2_note_contrary = 64;
				
		super.setNotes(notes);
		super.setThirds(thirds);
		super.setContrary(contrary);
	}
	
	
}
