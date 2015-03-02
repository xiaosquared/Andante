package canon;

public class Measure {
	int id;
	
	int[] notes;	// notes in the measure
	float[] beats;  // on which beats do they occur
	int[] velocity;
	
	int currentBeat = 0;
	
	public Measure(int[] notes, float[] beats, int[] velocity, int id) {
		this.id = id;
		this.notes = notes;
		this.beats = beats;
		this.velocity = velocity;
	}
	
	public void resetMeasure() {
		currentBeat = 0;
	}
	
	public boolean atMeasureEnd() {
		return currentBeat == beats.length;
	}
	
	public float getCurrentBeat() {
		if (currentBeat < beats.length)
			return beats[currentBeat];
		else
			return -1;
	}
	
	public float getNextBeat() {
		if ((currentBeat + 1) < beats.length)
			return beats[currentBeat+1];
		else return -1;
	}
	
	public int getNextNote() {
		return notes[currentBeat + 1];
	}
	
	public int getCurrentNote() {
		return notes[currentBeat];
	}
	
	public int getCurrentNoteVelocity() {
		return velocity[currentBeat];
	}
	
	public int getId() {
		return id;
	}
	
	public void incrementBeat() {
		currentBeat++;
	}
	
	/**
	 * @return first note of the measure. 
	 */
	public int getFirstNote() {
		return notes[0];
	}
	
	public int getLastNote() {
		return notes[notes.length-1];
	}
	
	public float getFirstBeat() {
		return beats[0];
	}
}
