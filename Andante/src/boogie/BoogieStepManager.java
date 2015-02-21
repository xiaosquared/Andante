package boogie;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import scales.ScalePlayer;
import anim.Step;

public class BoogieStepManager {
	public static Step M3_up, m3_up, M3_down, m3_down;
	public static Step M3_turn, M3_turn1, BDB_turn, BDB_turn1, ECE_turn, ECE_turn1;
	public static Step hs_turn, hs_turn1;
	public static Step ws_up, ws_down;
	public static Step GEF_turn, GEF_turn1;
	public static Step ACE_turn, ACE_turn1;
	public static Step BFA_turn, BFA_turn1;
	
	public static ArrayList<Step> stepOrder;
	
	public static void setup(PApplet parent) {
		// load the steps
		loadSteps(parent);
		
		// Order the steps
		setStepOrder();
		System.out.println(stepOrder.size());
	}
	
	private static void loadSteps(PApplet parent) {
		M3_up = new Step("Major 3rd up",  loadSequence(parent, new PImage[8], "data/__boogie/major3/up/"), true, false, 19);
		M3_down = new Step("Major 3rd down",  loadSequence(parent, new PImage[8], "data/__boogie/major3/down/"), false, false, 22);
		m3_up = new Step("Minor 3rd up",  loadSequence(parent, new PImage[8], "data/__boogie/minor3/up/"), true, false, 29);
		m3_down = new Step("Minor 3rd down",  loadSequence(parent, new PImage[8], "data/__boogie/minor3/down/"), false, false, 31);
		ws_up = new Step("whole step up",  loadSequence(parent, new PImage[8], "data/__boogie/ws/up/"), true, false, 36);
		ws_down = new Step("whole step down",  loadSequence(parent, new PImage[8], "data/__boogie/ws/down/"), false, false, 29);
		
		hs_turn = new Step("Half step turn",  loadSequence(parent, new PImage[8], "data/__boogie/hst/one/"), true, false, 26);
		hs_turn1 = new Step("Half step turn 1",  loadSequence(parent, new PImage[8], "data/__boogie/hst/two/"), false, false, 38);
		
		M3_turn = new Step("Major 3rd turn",  loadSequence(parent, new PImage[8], "data/__boogie/e-c-e/1/"), false, false, 24);
		M3_turn1 = new Step("Major 3rd turn 1",  loadSequence(parent, new PImage[8], "data/__boogie/e-c-e/2/"), true, false, 13);
		
		BDB_turn = new Step("minor 3rd top turn",  loadSequence(parent, new PImage[8], "data/__boogie/b-d-b/1/"), true, false, 14);
		BDB_turn1 = new Step("minor 3rd top turn 1",  loadSequence(parent, new PImage[8], "data/__boogie/b-d-b/2/"), false, false, 33);
		
		ECE_turn = new Step("minor 3rd bottom turn",  loadSequence(parent, new PImage[8], "data/__boogie/e-c-e/1/"), false, false, 23);
		ECE_turn1 = new Step("minor 3rd bottom turn 1",  loadSequence(parent, new PImage[8], "data/__boogie/e-c-e/2/"), true, false, 12);
		
		GEF_turn = new Step("GEF turn",  loadSequence(parent, new PImage[8], "data/__boogie/g-e-f/1/"), false, false, 33);
		GEF_turn1 = new Step("GEF turn 1",  loadSequence(parent, new PImage[8], "data/__boogie/g-e-f/2/"), true, false, 30);
		
		ACE_turn = new Step("ACE turn",  loadSequence(parent, new PImage[8], "data/__boogie/a-c-e/1/"), false, false, 20);
		ACE_turn1 = new Step("ACE turn 1",  loadSequence(parent, new PImage[8], "data/__boogie/a-c-e/2/"), true, false, 46);
		
		BFA_turn = new Step("ACE turn",  loadSequence(parent, new PImage[8], "data/__boogie/b-f-a/1/"), false, false, 15);
		BFA_turn1 = new Step("ACE turn 1",  loadSequence(parent, new PImage[8], "data/__boogie/a-c-e/2/"), true, false, 46);
	}
	
	public static void setStep(ScalePlayer sp) {
		int current = sp.getCurrentNote();
		
//		System.out.println(stepOrder.get(sp.getScaleIndex()).getName());
		sp.setCurrentStep(stepOrder.get(sp.getScaleIndex()));
		sp.setAnimNote(current);
		
		// test
//		sp.setCurrentStep(BFA_turn1);
//		sp.setAnimNote(60);
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
	
	private static void setStepOrder() {
		stepOrder = new ArrayList<Step>();
		stepOrder.add(m3_down); // G->E
		stepOrder.add(M3_turn); // E->C
		stepOrder.add(M3_turn1); // C->E
		stepOrder.add(m3_up); // E->G
		stepOrder.add(ws_up); // G->A
		stepOrder.add(hs_turn); // A->Bb
		stepOrder.add(hs_turn1); // Bb->A
		stepOrder.add(ws_down); // A->G
		stepOrder.add(m3_down); // G->E
		
		stepOrder.add(M3_turn); // E->C
		stepOrder.add(M3_turn1); // C->E
		stepOrder.add(m3_up); // E->G
		stepOrder.add(ws_up); // G->A
		stepOrder.add(hs_turn); // A->Bb
		stepOrder.add(hs_turn1); // Bb->A
		stepOrder.add(ws_down); // A->G
		stepOrder.add(GEF_turn); // G->E
		
		stepOrder.add(GEF_turn1); // E->F
		stepOrder.add(M3_up); // F->A
		stepOrder.add(m3_up); // A->C
		stepOrder.add(ws_up); // C->D
		stepOrder.add(hs_turn); // D->Eb
		stepOrder.add(hs_turn1); // Eb->D
		stepOrder.add(ws_down); // D->C
		stepOrder.add(m3_down); // C->A
		
		stepOrder.add(ACE_turn); // A->C
		stepOrder.add(ACE_turn1); // C->E
		stepOrder.add(m3_up); // E->G
		stepOrder.add(ws_up); // G->A
		stepOrder.add(hs_turn); // A->Bb
		stepOrder.add(hs_turn1); // Bb->A
		stepOrder.add(ws_down); // A->G
		stepOrder.add(ECE_turn); // G->E
		
		stepOrder.add(ECE_turn1); // E->G
		stepOrder.add(M3_up); // G->B
		stepOrder.add(BDB_turn); // B->D
		stepOrder.add(BDB_turn1); // D->B
		
		stepOrder.add(BFA_turn); // B->F
		stepOrder.add(BFA_turn1); // F->A
		stepOrder.add(BDB_turn); // A->C
		stepOrder.add(BDB_turn1); // C->A
		
		stepOrder.add(ACE_turn); // A->C
		stepOrder.add(ACE_turn1); // C->E
		stepOrder.add(m3_up); // E->G
		stepOrder.add(ws_up); // G->A
		stepOrder.add(hs_turn); // A->Bb
		stepOrder.add(hs_turn1); // Bb->A
		stepOrder.add(ws_down); // A->G
		
	}
}
