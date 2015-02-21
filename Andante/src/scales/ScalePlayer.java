package scales;

import anim.Step;
import scales.data.CMajorScale;
import scales.data.ChromaticScale;
import scales.data.Scale;


/**
 * 
 * Basically runs the program. Takes care of animation and MIDI I/O
 *
 */
public class ScalePlayer {

	// frames
	protected int frame_counter = 0; 
	protected int frames_per_step = 8;
	
	// scales and notes
	protected Scale my_scale;
	
	protected CMajorScale cmajor;
	protected ChromaticScale chromatic;
	
	protected int prev_note = 0;
	protected int prev_note_thirds = 0;
	protected int prev_note_contrary = 0;
	
	// step
	protected Step current_step;
	protected Step current_step_thirds;
	protected Step current_step_contrary;
	protected int anim_note = 0; // for animation because frames are offset from piano
	protected int anim_note_thirds = 0;
	protected int anim_note_contrary = 0;
	
	
	// character
	public int character = 1;
	
	// extra people
	public boolean thirds = false;
	public boolean contrary = false;
	
	public ScalePlayer() {}
	
	public void init(ScaleAnimation parent) {
		cmajor = new CMajorScale();
		my_scale = cmajor;
		
		current_step = parent.normal_steps.hs_up;
		current_step_thirds = parent.normal_steps.hs_up;
		current_step_contrary = parent.normal_steps.hs_down;
	}
	
	public void pause(ScaleAnimation parent) {
		parent.output.sendNoteOff(0, prev_note, 50);
		parent.output.sendNoteOff(0, prev_note, 50);
		parent.output.sendNoteOff(0, prev_note, 50);
	}
	
	/**
	 * Called at each frame to progress the animation
	 */
	public void run(ScaleAnimation parent) {
		
		// play note
		handleMIDI(parent);
		
		// update step animation
		int anim_frame = getAnimFrame(frame_counter % frames_per_step, parent.target_frame_rate);
		if ((anim_frame % 8) == 0) 
			updateStep(parent);
	
		// draw frame
		drawStep(parent, anim_frame);
				
		// update frame
		frame_counter++;
	}
	
	protected void handleMIDI(ScaleAnimation parent) {
		if (frame_counter % 8 == 0) {
			my_scale.updateNotes();
			int current_note = my_scale.getCurrentNote();
			prev_note = my_scale.getPrevNote();
			
			parent.output.sendNoteOn(0, current_note, 30);
			parent.output.sendNoteOff(0, prev_note, 50);
			parent.output.sendNoteOff(0, prev_note, 50);
			parent.output.sendNoteOff(0, prev_note, 50);
			parent.output.sendNoteOff(0, prev_note, 50);
			
			if (thirds) {
				int current_note_thirds = my_scale.getCurrentNoteThirds();
				prev_note_thirds = my_scale.getPrevNoteThirds();
				parent.output.sendNoteOn(0, current_note_thirds, 30);
				parent.output.sendNoteOff(0, prev_note_thirds, 50);
				parent.output.sendNoteOff(0, prev_note_thirds, 50);
				parent.output.sendNoteOff(0, prev_note_thirds, 50);
				parent.output.sendNoteOff(0, prev_note_thirds, 50);
			}
			
			if (contrary) {
				int current_note_contrary = my_scale.getCurrentNoteContrary();
				prev_note_contrary = my_scale.getPrevNoteContrary();
				parent.output.sendNoteOn(0, current_note_contrary, 30);
				parent.output.sendNoteOff(0, prev_note_contrary, 50);
				parent.output.sendNoteOff(0, prev_note_contrary, 50);
				parent.output.sendNoteOff(0, prev_note_contrary, 50);
				parent.output.sendNoteOff(0, prev_note_contrary, 50);
			}
			
		}
	}
	
	protected void updateStep(ScaleAnimation parent) {
		ScaleStepManager step_manager;
		if (character == 2)
			step_manager = parent.ostrich_steps;
		else if (character == 3)
			step_manager = parent.fat_steps;
		else if (character == 4)
			step_manager = parent.sneak_steps;
		else 
			step_manager = parent.normal_steps;
		
		// update main guy
		step_manager.setStep(this);
		
		// update thirds
		int current_note_thirds = my_scale.getCurrentNoteThirds();
		current_step_thirds = step_manager.getStep(this, current_step_thirds, current_note_thirds, my_scale.getPrevNoteThirds(), my_scale.getNextNoteThirds());
		anim_note_thirds = current_note_thirds;
		
		// update contrary
		int current_note_contrary = my_scale.getCurrentNoteContrary();
		current_step_contrary = step_manager.getStep(this, current_step_contrary, current_note_contrary, my_scale.getPrevNoteContrary(), my_scale.getNextNoteContrary());
		anim_note_contrary = current_note_contrary;
		
	}
	
	
	protected void drawStep(ScaleAnimation parent, int frame) {
		parent.background(0);
		//parent.println(frame + " " + anim_note);
		
		if (thirds) {
			if (character == 4)
				current_step_thirds.drawFrame(parent, anim_note_thirds, frame, parent.img_y+5);
			else
				current_step_thirds.drawFrame(parent, anim_note_thirds, frame, parent.img_y);
		}
		
		if (contrary) {
			if (character == 4)
				current_step_contrary.drawFrame(parent, anim_note_contrary, frame, parent.img_y+5);
			else
				current_step_contrary.drawFrame(parent, anim_note_contrary, frame, parent.img_y);
		}
		if (character == 4)
			current_step.drawFrame(parent, anim_note, frame, parent.img_y+5);
		else
			current_step.drawFrame(parent, anim_note, frame, parent.img_y);
	}
	
	protected int getAnimFrame(int frame, int fps) {
		int offset = getAnimOffset(fps);
		if (frame >= offset) {
			return frame - offset;
		} else
			return frame + 8 - offset;
	}
	
	/**
	 * 
	 * @param fps of entire animation
	 * @return how many frames prior we need to send MIDI signal
	 */
	protected int getAnimOffset(int fps) { return fps/4-2; }

	public int getPrevNote() {
		return my_scale.getPrevNote();
	}
	
	public int getPrev2Note() {
		return my_scale.getPrev2Note();
	}
	
	public int getCurrentNote() {
		return my_scale.getCurrentNote();
	}
	
	public int getNextNote() {
		return my_scale.getNextNote();
	}
	
	public int getScaleIndex() {
		return my_scale.getIndex();
	}
	
	public void setCurrentStep(Step s) {
		current_step = s;
	}
	
	public void setAnimNote(int i) {
		anim_note = i;
	}
}
