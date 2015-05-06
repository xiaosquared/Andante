package canon;

import java.util.ArrayList;

import processing.core.PVector;
import anim.*;


public class Voice {
	protected ArrayList<Measure> measures;
	int beats_per_measure = 4;
	
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
	
	public void initBlocks(int pixels_per_beat, int startY, float note_width, float x_offset, int epsilon) {
		for (int i = 0; i < measures.size(); i++) {
			float measure_offset =  beats_per_measure * pixels_per_beat * i;
			Measure m = measures.get(i);
			Measure m_next;
			if (i < measures.size() - 1)
				m_next = measures.get(i+1);
			else m_next = measures.get(0);
			
			// initialize arrays for blocks in each measure
			m.initBlockArrays(new PVector[m.notes.length], new int[m.notes.length]);
			
			for (int k = 0; k < m.beats.length; k ++) {		 
				float offset = m.beats[k] * pixels_per_beat;
				int y = (int) (startY - offset);
				int x = (int) ((m.notes[k]-21) * note_width + x_offset);
				
				int length = pixels_per_beat - epsilon;		// by default, length is 1 beat
				
				if ((m.beats[k] % 1 != 0) ||				// check to see if it's 8th notes
				   ((k < m.beats.length - 1) && m.beats[k+1] % 1 != 0))
					length = length/2; 
				
				else if ((k < m.beats.length - 1) && (m.beats[k+1] - m.beats[k] > 1))
					length = (int) (pixels_per_beat * (m.beats[k+1] - m.beats[k]) - epsilon);
				
				else if (m_next.beats[0] > 1) {				// TODO: there's a bug here...check for syncopation 
					if ((m.beats[k] == 1) && (m.beats.length == 1))
						length = pixels_per_beat * 5 - epsilon;
					else if (m.beats[k] == 3)
						length = pixels_per_beat * 3 - epsilon;
				}
				y -= length;								// since y specifies the top of the rect
				
				m.block_xys[k] = new PVector((int)x, (int)y);
				m.block_lengths[k] = length;
			}
		}
			
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
