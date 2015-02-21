package chromatic;

import scales.ScaleAnimation;
import scales.ScalePlayer;
import scales.data.ChromaticScale;

public class ChromaticPlayer extends ScalePlayer {

	int color = 0;
	
	public ChromaticPlayer() { super();}

	public void init(ScaleAnimation parent) {
		my_scale = new ChromaticScale();
		current_step = ChromaticStepManager.bw_up;
	}
	
	@Override
	protected void updateStep(ScaleAnimation parent) {
		ChromaticStepManager.setStep(this); 
	}
	
	@Override
	protected void handleMIDI(ScaleAnimation parent) {
		if (frame_counter % 8 == 0) {
			my_scale.updateNotes();
			int current_note = my_scale.getCurrentNote();
			prev_note = my_scale.getPrevNote();
			
			parent.output.sendNoteOn(0, current_note, 50);
			parent.output.sendNoteOff(0, prev_note, 30);
			parent.output.sendNoteOff(0, prev_note, 30);
			parent.output.sendNoteOff(0, prev_note, 30);
			parent.output.sendNoteOff(0, prev_note, 30);
		}
	}
	
	@Override
	public void drawStep(ScaleAnimation parent, int frame) {
		
		// for trails
		parent.rect(134, 363, 500, 120);
		
		color+=5;
		if (color >= 255) 
			color = 0;
		parent.tint(color, 255, 255, 255);
		current_step.drawFrame(parent, anim_note, frame, parent.img_y);
	}
}
