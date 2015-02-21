package boogie;

import scales.ScaleAnimation;
import scales.ScalePlayer;

public class BoogiePlayer extends ScalePlayer {
	
	public BoogiePlayer() { super();}
	
	public void init(ScaleAnimation parent) {
		my_scale = new BoogieLine();
		current_step = BoogieStepManager.M3_turn;
	}
	
	@Override
	protected void updateStep(ScaleAnimation parent) {
		BoogieStepManager.setStep(this); 
	}
	
//	@Override
//	protected void drawStep(ScaleAnimation parent, int frame) {
//		//System.out.println("draw");
//	}
	
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

	protected int getAnimOffset(int fps) { return fps/4; }
}
