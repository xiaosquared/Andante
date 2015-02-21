package scales;

import processing.core.PApplet;
import processing.core.PImage;
import anim.Step;

public class ScaleStepManagerOstrich extends ScaleStepManager {
	
	public ScaleStepManagerOstrich() {
		super();
	}
	
	public void initSteps(PApplet parent) {
		ws_up = new Step("whole step up",  loadSequence(parent, new PImage[8], "data/__Ostrich/_ws/up/wsws_"), true, false, 23);
		ws_down = new Step("whole step down", loadSequence(parent, new PImage[8], "data/__Ostrich/_ws/down/wsws_"), false, false, 27);
		hs_up = new Step("half step up", loadSequence(parent, new PImage[8], "data/__Ostrich/_hs/Up/wshs_"), true, false, 25);
		hs_down = new Step("half step down", loadSequence(parent, new PImage[8], "data/__Ostrich/_hs/Down/wshs_"), false, false, 25);
		
		turn_top = new Step("turn top", loadSequence(parent, new PImage[8], "data/__Ostrich/_hst/up/hst_"), true, false, 25);
		turn_top1 = new Step("turn top part 2", loadSequence(parent, new PImage[8], "data/__Ostrich/_hst/up/hst1_"), false, false, 27);
		
		turn_bottom = new Step("turn bottom", loadSequence(parent, new PImage[8], "data/__Ostrich/_wst/down/wst_"), false, false, 30);
		turn_bottom1 = new Step("turn bottom part 2", loadSequence(parent, new PImage[8], "data/__Ostrich/_wst/down/wst1_"), true, false, 23);
	}

//	public void setStep(ScalePlayer sp) {
//		sp.current_step = turn_bottom1;
//		sp.anim_note = 60;
//	}
}
