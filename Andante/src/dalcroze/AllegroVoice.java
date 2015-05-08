package dalcroze;

import java.util.ArrayList;

import anim.CanonStepManager;
import canon.FrameProfile;
import canon.Measure;
import canon.Voice;

public class AllegroVoice extends Voice {
	
	public AllegroVoice(int measure_offset, int start_note) {
		super(measure_offset, start_note);
		lastKeyFrame = new FrameProfile(start_note, start_note+1, start_note, 0, 0, 1);
		thisKeyFrame = new FrameProfile(start_note+1, start_note, start_note, 0, 0, 1);
		
		setCurrentStep(AllegroStepManager.jump_faceDown);
	}
}
