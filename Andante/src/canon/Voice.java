package canon;

import java.util.ArrayList;

import anim.*;


public class Voice {
	protected ArrayList<Measure> measures;
	
	protected int local_measure = 0;			// which measure we are on within this voice
	protected int measure_offset;			// how many measures in the beginning before we start
	
	protected FrameProfile thisKeyFrame;
	protected FrameProfile lastKeyFrame;
	
	//* Animation
	protected Step current_step;
	protected int anim_note;
	
	// rhythm note
	protected int rhythm_note;
	
	public Voice(int measure_offset, int start_note) {
		lastKeyFrame = new FrameProfile(start_note, start_note, start_note, 0, 0, 1);
		thisKeyFrame = new FrameProfile(start_note, start_note, start_note, 0, 0, 1);
		
		this.measures = new ArrayList<Measure>();
		this.measure_offset = measure_offset;
		
		setCurrentStep(CanonStepManager.jump_faceUp); 
	}
	
	public FrameProfile getThisKeyFrame() { return thisKeyFrame; }
	public FrameProfile getLastKeyFrame() { return lastKeyFrame; }
	
	public int getAnimNote() {return anim_note; }
	public void setAnimNote(int n) { anim_note = n; }
	
	public void setThisKeyFrame(int n2, int n1, int nn, float b2, float b1, float nb) {
		thisKeyFrame = new FrameProfile(n2, n1, nn, b2, b1, nb);
	}
	
	public void setLastkeyFrame(int n2, int n1, int nn, float b2, float b1, float nb) {
		lastKeyFrame = new FrameProfile(n2, n1, nn, b2, b1, nb);
	}
	
	public int getLastPlayedNote() {
		return thisKeyFrame.getN1();
	}
	
	public int getN2() {
		return thisKeyFrame.getN2();
	}
	
	public float getLastPlayedBeat() {
		return thisKeyFrame.getB1();
	}
	
	public int getMeasureOffset() { return measure_offset; }
	
	public void setLocalMeasure(int m) { local_measure = m; }
	
	public int getLocalMeasure() { return local_measure; }
	
	public void resetMeasure(int m) {
		measures.get(m).resetMeasure();
	}
	
	public Step getCurrentStep() { return current_step; }
	public void setCurrentStep(Step s) { current_step = s; }
	
	// returns the first note of that measure
	public int getFirstNoteOfMeasure(int m) {
		if (measures.get(m).getFirstBeat() == 1)
			return measures.get(m).getFirstNote();
		else return measures.get(m-1).getLastNote();
	}
	
	// note that is played if we're in rhythm-only mode
	public int getRhythmNote() {
		return getFirstNoteOfMeasure(0);
	}
	
	public Measure getMeasure(int global_measure) {
		int local_measure = global_measure - measure_offset;
		if (local_measure >= 0) 
			return measures.get(local_measure);
		else 
			return null;
	}
	
	public Measure getMeasureFromId(int id) {
		if (id < measures.size())
			return measures.get(id);
		else return measures.get(0);
	}
	
	public boolean atEnd() {
		if (local_measure >= measures.size())
			return true;
		return false;
	}
	
	public ArrayList<Measure> getMeasures() {
		return measures;
	}
}
