package chromatic;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import scales.ScalePlayer;
import anim.Step;

public class ChromaticStepManager {
	public static Step wb_up, wb_down; 
	public static Step bw_up, bw_down, ww_up, ww_down;
	public static Step top_turn, top_turn1, bottom_turn, bottom_turn1;
	
	private static ArrayList<Step> stepOrder;
	
	public static void setup(PApplet parent) {
		// load the steps
		loadSteps(parent);

		// Order the steps
		setStepOrder();
		System.out.println(stepOrder.size());
	}
	
	private static void loadSteps(PApplet parent) {
		wb_up = new Step("top turn",  loadSequence(parent, new PImage[8], "data/__boogie/hs_wb/up/"), true, false, 36);
		wb_down = new Step("top turn",  loadSequence(parent, new PImage[8], "data/__boogie/hs_wb/down/"), false, false, 27);
		
		bw_up = new Step("top turn",  loadSequence(parent, new PImage[8], "data/__boogie/hs_bw/up/"), true, false, 36);
		bw_down = new Step("top turn",  loadSequence(parent, new PImage[8], "data/__boogie/hs_bw/down/"), false, false, 26);
		
		ww_up = new Step("top turn",  loadSequence(parent, new PImage[8], "data/__boogie/hs_ww/up/"), true, false, 26);
		ww_down = new Step("top turn",  loadSequence(parent, new PImage[8], "data/__boogie/hs_ww/down/"), false, false, 42);
		
		top_turn = new Step("top turn",  loadSequence(parent, new PImage[8], "data/__boogie/hst/one/"), true, false, 26);
		top_turn1 = new Step("top turn 1",  loadSequence(parent, new PImage[8], "data/__boogie/hst/two/"), false, false, 35);
		
		bottom_turn = new Step("top turn",  loadSequence(parent, new PImage[8], "data/__boogie/hst_bottom/one/"), false, false, 29);
		bottom_turn1 = new Step("top turn 1",  loadSequence(parent, new PImage[8], "data/__boogie/hst_bottom/two/"), true, false, 33);
	}
	
	public static void setStep(ScalePlayer sp) {
		int current = sp.getCurrentNote();
		
//		System.out.println(stepOrder.get(sp.getScaleIndex()).getName());
		sp.setCurrentStep(stepOrder.get(sp.getScaleIndex()));
		sp.setAnimNote(current);
		
		// test
//		sp.setCurrentStep(bottom_turn);
//		sp.setAnimNote(60);
	}
	
	private static void setStepOrder() {
		stepOrder = new ArrayList<Step>();
		stepOrder.add(bw_down); // Eb->D
		stepOrder.add(bottom_turn); // D->C#
		stepOrder.add(bottom_turn1); // C#->D
		stepOrder.add(wb_up); // D->Eb
		stepOrder.add(bw_up); // Eb->E
		stepOrder.add(ww_up); // E->F
		stepOrder.add(wb_up); // F->F#
		stepOrder.add(bw_up); // F#->G
		stepOrder.add(wb_up); // G->G#
		stepOrder.add(bw_up); // G#->A
		stepOrder.add(wb_up); // A->Bb
		stepOrder.add(bw_up); // Bb->B
		stepOrder.add(ww_up); // B->C
		stepOrder.add(top_turn); // C->C#
		stepOrder.add(top_turn1); // C#->C
		stepOrder.add(ww_down); // C->B
		stepOrder.add(wb_down); // B->Bb
		stepOrder.add(bw_down); // Bb->A
		stepOrder.add(wb_down); // A->G#
		stepOrder.add(bw_down); // G#->G
		stepOrder.add(wb_down); // G->F#
		stepOrder.add(bw_down); // F#->F
		stepOrder.add(ww_down); // F->E
		stepOrder.add(wb_down); // E->Eb
		
	}
		
	// Helper method to load PImages;
	static private PImage[] loadSequence(PApplet parent, PImage[] images, String prefix) {
		for (int i = 0; i < images.length; i++) {
			String filename = prefix + i + ".png";
			images[i] = parent.loadImage(filename);
			images[i].resize(0, 78);
			//images[i].resize(0, 200);
		}
		return images;
	}
}
