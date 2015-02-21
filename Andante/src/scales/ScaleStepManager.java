package scales;

import processing.core.PApplet;
import processing.core.PImage;
import anim.Step;

public class ScaleStepManager {
	public Step ws_up, ws_down, hs_up, hs_down, turn_top, turn_top1, turn_bottom, turn_bottom1; 
	
	public ScaleStepManager() {}
	
	public void initSteps(PApplet parent) {
		ws_up = new Step("whole step up",  loadSequence(parent, new PImage[8], "data/_wsws/up/wsws_"), true, false, 22);
		ws_down = new Step("whole step down", loadSequence(parent, new PImage[8], "data/_wsws/down/wsws_"), false, false, 23);
		hs_up = new Step("half step up", loadSequence(parent, new PImage[8], "data/_wshs/up/wshs_"), true, false, 7);
		hs_down = new Step("half step down", loadSequence(parent, new PImage[8], "data/_wshs/down/wshs_"), false, false, 8);
		
		turn_top = new Step("turn top", loadSequence(parent, new PImage[8], "data/_hst/up/hst_"), true, false, 13);
		turn_top1 = new Step("turn top part 2", loadSequence(parent, new PImage[8], "data/_hst/up/hst1_"), false, false, 10);
		
		turn_bottom = new Step("turn bottom", loadSequence(parent, new PImage[8], "data/_wst/down/wst_"), false, false, 16);
		turn_bottom1 = new Step("turn bottom part 2", loadSequence(parent, new PImage[8], "data/_wst/down/wst1_"), true, false, 6);
	}
	
		
	// Helper method to load PImages;
	protected PImage[] loadSequence(PApplet parent, PImage[] images, String prefix, int resizeY) {
		for (int i = 0; i < images.length; i++) {
			String filename = prefix + i + ".png";
			images[i] = parent.loadImage(filename);
			images[i].resize(0, resizeY);
			//images[i].resize(0, 200);
		}
		return images;
	}
	
	protected PImage[] loadSequence(PApplet parent, PImage[] images, String prefix) {
		return loadSequence(parent, images, prefix, 85);
	}
	
	public Step getDefaultStep() {
		return ws_up;
	}
	
	// Figures out what step to play next
	public void setStep(ScalePlayer sp) {
		int current = sp.getCurrentNote();
		int prev = sp.getPrevNote();
		int next = sp.getNextNote();
		int diff = current - prev;
		
		if (sp.current_step.equals(turn_top))
			sp.current_step = turn_top1;
		else if (sp.current_step.equals(turn_bottom))
			sp.current_step = turn_bottom1;

		// going into a turn
		else if (prev == next) {
			if (prev < current)	
				sp.current_step = turn_top;
			else
				sp.current_step = turn_bottom;
		}

		// Whole steps and half steps
		else if (diff == 2)
			sp.current_step = ws_up;
		else if (diff == -2)
			sp.current_step = ws_down;
		else if (diff == 1)
			sp.current_step = hs_up;
		else if (diff == -1)
			sp.current_step = hs_down;
		
		sp.anim_note = current;
	}
	
	public Step getStep(ScalePlayer sp, Step current_step, int current, int prev, int next) {
		int diff = current - prev;
		
		if (current_step.equals(turn_top))
			return turn_top1;
		else if (current_step.equals(turn_bottom))
			return turn_bottom1;

		
		// Whole steps and half steps
		else if (diff == 2)
			return ws_up;
		else if (diff == -2)
			return ws_down;
		else if (diff == 1)
			return hs_up;
		else if (diff == -1)
			return hs_down;
		
		else {
			if (prev < current)	
				return turn_top;
			else
				return turn_bottom;
		}
	}
}
