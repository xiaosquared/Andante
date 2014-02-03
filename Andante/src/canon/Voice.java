package canon;

import java.util.ArrayList;

import anim.*;


public class Voice {
	ArrayList<Measure> measures;
	
	int local_measure = 0;			// which measure we are on within this voice
	int measure_offset;			// how many measures in the beginning before we start
	
	FrameProfile thisKeyFrame;
	FrameProfile lastKeyFrame;
	
	//* Animation
	Step current_step;
	int anim_note;
	
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
	
	public Step getCurrentStep() { return current_step; }
	public void setCurrentStep(Step s) { current_step = s; }
		
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
}
