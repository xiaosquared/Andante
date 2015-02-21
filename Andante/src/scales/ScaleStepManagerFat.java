package scales;

import processing.core.PApplet;
import processing.core.PImage;
import anim.Step;

public class ScaleStepManagerFat extends ScaleStepManager {
	
	public ScaleStepManagerFat() {
		super();
	}
	
	public void initSteps(PApplet parent) {
		ws_up = new Step("whole step up",  loadSequence(parent, new PImage[8], "data/__Fat/_ws/up/wsws_"), true, false, 7);
		ws_down = new Step("whole step down", loadSequence(parent, new PImage[8], "data/__Fat/_ws/down/wsws_"), false, false, 11);
		hs_up = new Step("half step up", loadSequence(parent, new PImage[8], "data/__Fat/_hs/Up/wshs_"), true, false, 14);
		hs_down = new Step("half step down", loadSequence(parent, new PImage[8], "data/__Fat/_hs/Down/wshs_"), false, false, 13);
		
		turn_top = new Step("turn top", loadSequence(parent, new PImage[8], "data/__Fat/_hst/up/hst_"), true, false, 11);
		turn_top1 = new Step("turn top part 2", loadSequence(parent, new PImage[8], "data/__Fat/_hst/up/hst1_"), false, false, 10);
		
		turn_bottom = new Step("turn bottom", loadSequence(parent, new PImage[8], "data/__Fat/_wst/down/wst_"), false, false, 9);
		turn_bottom1 = new Step("turn bottom part 2", loadSequence(parent, new PImage[8], "data/__Fat/_wst/down/wst1_"), true, false, 10);
	}

//	public void setStep(ScalePlayer sp) {
//		sp.current_step = hs_up;
//		sp.anim_note = 60;
//	}  
}
