package canon;

import processing.core.PVector;

public class Measure {
	int id;
	
	public int[] notes;	// notes in the measure
	public int[] notes_on; // valus < 0 -> not played yet; value = 0 -> playing; value > 0 -> played
	public float[] beats;  // on which beats do they occur
	public int[] velocity;
	
	// for blocks
	public PVector[] block_xys;
	public int[] block_lengths;
	
	int currentBeat = 0;
	
	public Measure(int[] notes, float[] beats, int[] velocity, int id) {
		this.id = id;
		this.notes = notes;
		this.beats = beats;
		this.velocity = velocity;
		
		notes_on = new int[notes.length];
		for (int i = 0; i < notes.length; i++) {
			notes_on[i] = -1;
		}
	}
	
	public void initBlockArrays(PVector[] block_xys, int[] block_lengths) {
		this.block_xys = block_xys;
		this.block_lengths = block_lengths;
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
