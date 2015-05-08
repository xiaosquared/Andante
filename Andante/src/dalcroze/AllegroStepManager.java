package dalcroze;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import anim.NoStep;
import anim.Step;
import canon.Voice;

public class AllegroStepManager {
	
	public static Step jump_faceUp, jump_faceDown;
	public static Step whole_step_up, half_step_up, whole_step_down, half_step_down;
	public static Step no_step_up, no_step_down;
	public static Step fifth_down, dbt_wshs, dbt_wsws, dbt_hw3rd, no_step, third_down;
	
	public static ArrayList<Step> allegro_steps;
	
	public static void setup(Allegro parent) {
		jump_faceUp = new Step("jump face up", loadSequence(parent, new PImage[8], "data/_j/up/j_"), true, false, 14);
		jump_faceDown = new Step("jump face down", loadSequence(parent, new PImage[8], "data/_j/down/j_"), false, false, 16);
		
		fifth_down = new Step("fifth", loadSequence(parent, new PImage[8], "data/_5th/down/5th_"), false ,false, 15);

		
		whole_step_up = new Step("whole step up", loadSequence(parent, new PImage[8], "data/_wsws/up/wsws_"), true, false, 22);
		whole_step_down = new Step("whole step down", loadSequence(parent, new PImage[8], "data/_wsws/down/wsws_"), false, false, 23);
		half_step_up = new Step("half step up", loadSequence(parent, new PImage[8], "data/_wshs/up/wshs_"), true, false, 7);
		half_step_down = new Step("half step down", loadSequence(parent, new PImage[8], "data/_wshs/down/wshs_"), false, false, 8);
		
		no_step_up = new Step("no_step_up", loadSequence(parent, new PImage[8], "data/_nostep/up/_nostep_"), true, false, 20);
		no_step_down = new Step("no_step_down", loadSequence(parent, new PImage[8], "data/_nostep/down/_nostep_"), true, false, 13);
		
		dbt_wshs = new Step("dbt_wshs", loadSequence(parent, new PImage[8], "data/_dbt/wshs/up/wshs_dbt_"), true, false, 31);
		dbt_wsws = new Step("dbt_wsws", loadSequence(parent, new PImage[8], "data/_dbt/wsws/up/wsws_dbt_"), true, false, 42) ;
		dbt_hw3rd = new Step("dbt_wsws", loadSequence(parent, new PImage[8], "data/_dbt/hsws/hw3/hw3rd_turn_"), true, false, 31);
		
		third_down = new Step("third", loadSequence(parent, new PImage[8], "data/_3rd/down/3rd_"), false, false, 7);
		
		no_step = new NoStep(jump_faceDown.getLastFrame(), false, 16);
		setStepSequence();
	}
	
	private static PImage[] loadSequence(PApplet parent, PImage[] images, String prefix) {
		for (int i = 0; i < images.length; i++) {
			String filename = prefix + i + ".png";
			images[i] = parent.loadImage(filename);
			images[i].resize(0, 85);
			//images[i].resize(0, 200);
		}
		return images;
	}
	
	public static void setStep(Voice v, int measure) {
		System.out.println(v.note_index);
		
		v.setCurrentStep(allegro_steps.get(v.note_index));
		v.setAnimNote(v.getThisKeyFrame().getN1());

		v.note_index++;
		if (v.note_index == allegro_steps.size())
			v.note_index = 0;
	}
	
	private static void setStepSequence() {
		allegro_steps = new ArrayList<Step>();
		allegro_steps.add(jump_faceDown);
		allegro_steps.add(jump_faceDown);
		allegro_steps.add(fifth_down);
		allegro_steps.add(jump_faceDown);
		
		allegro_steps.add(whole_step_up);
		allegro_steps.add(dbt_hw3rd);
		allegro_steps.add(whole_step_down);
		allegro_steps.add(jump_faceDown);
		
		allegro_steps.add(whole_step_down);
		allegro_steps.add(jump_faceDown);
		allegro_steps.add(half_step_down);
		allegro_steps.add(jump_faceDown);
		
		allegro_steps.add(whole_step_down);
		allegro_steps.add(dbt_wsws);
		allegro_steps.add(third_down);
		allegro_steps.add(no_step);
		
		allegro_steps.add(jump_faceDown);
		allegro_steps.add(jump_faceDown);
		allegro_steps.add(whole_step_down);
		allegro_steps.add(fifth_down);
		
		allegro_steps.add(jump_faceDown);
		allegro_steps.add(jump_faceDown);
		allegro_steps.add(whole_step_down);
		allegro_steps.add(fifth_down);

		allegro_steps.add(jump_faceUp);
		allegro_steps.add(whole_step_up);
		allegro_steps.add(half_step_up);
		allegro_steps.add(third_down);
		
		allegro_steps.add(half_step_down);
		allegro_steps.add(third_down);
		allegro_steps.add(whole_step_down);
		allegro_steps.add(no_step);
	}
}
