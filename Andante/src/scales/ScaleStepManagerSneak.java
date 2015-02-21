package scales;

import processing.core.PApplet;
import processing.core.PImage;
import anim.Step;

public class ScaleStepManagerSneak extends ScaleStepManager{
	public ScaleStepManagerSneak() {
		super();
	}
	
	public void initSteps(PApplet parent) {
		int scaleHeight = 76;
		ws_up = new Step("whole step up",  loadSequence(parent, new PImage[8], "data/__Sneak/_ws/up/ws_", scaleHeight), true, false, 31);
		ws_down = new Step("whole step down", loadSequence(parent, new PImage[8], "data/__Sneak/_ws/down/ws_", scaleHeight), false, false, 22);
		hs_up = new Step("half step up", loadSequence(parent, new PImage[8], "data/__Sneak/_hs/Up/hs_", scaleHeight), true, false, 9);
		hs_down = new Step("half step down", loadSequence(parent, new PImage[8], "data/__Sneak/_hs/Down/hs_", scaleHeight), false, false, 10);
		
		turn_top = new Step("turn top", loadSequence(parent, new PImage[8], "data/__Sneak/_hst/up/hst_", scaleHeight), true, false, 9);
		turn_top1 = new Step("turn top part 2", loadSequence(parent, new PImage[8], "data/__Sneak/_hst/up/hst1_", scaleHeight), false, false, 10);
		
		turn_bottom = new Step("turn bottom", loadSequence(parent, new PImage[8], "data/__Sneak/_wst/down/wst_", scaleHeight), false, false, 8);
		turn_bottom1 = new Step("turn bottom part 2", loadSequence(parent, new PImage[8], "data/__Sneak/_wst/down/wst1_", scaleHeight), true, false, 8);
	}
	
//	public void setStep(ScalePlayer sp) {
//		sp.current_step = turn_bottom1;
//		sp.anim_note = 60;
//	}
}
