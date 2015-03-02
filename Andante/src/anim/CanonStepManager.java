package anim;

import canon.Voice;
import processing.core.PApplet;
import processing.core.PImage;

public class CanonStepManager {
	
	public static Step jump_faceUp, jump_faceDown; 
	public static Step whole_step_up, half_step_up, whole_step_down, half_step_down;
	public static Step wsTurn_up, wsTurn_up1;
	public static Step wsTurn_up1_dbt_ws, wsTurn_up1_dbt_hs;
	public static Step wsTurn_down, wsTurn_down1;
	
	public static Step hsTurn_up, hsTurn_up1;
	public static Step hsTurn_up1_dbt;
	public static Step hsTurn_down, hsTurn_down1;
	
	public static Step wsT_up_inplace, wsT_down_inplace, hsT_up_inplace, hsT_down_inplace, inplace_up;
	public static Step third, hs_a3; 
	public static Step fifth, fifth_turn, octave;
	public static Step dbt_wsws, dbt_hsws, dbt_wshs; // all dbt are going down
	public static Step no_step_up, no_step_down;
	public static NoStep no_step; 

	public static void setup(PApplet parent) {
		jump_faceUp = new Step("jump face up", loadSequence(parent, new PImage[8], "data/_j/up/j_"), true, false, 14);
		jump_faceDown = new Step("jump face down", loadSequence(parent, new PImage[8], "data/_j/down/j_"), false, false, 16);
		
		whole_step_up = new Step("whole step up", loadSequence(parent, new PImage[8], "data/_wsws/up/wsws_"), true, false, 22);
		whole_step_down = new Step("whole step down", loadSequence(parent, new PImage[8], "data/_wsws/down/wsws_"), false, false, 23);
		half_step_up = new Step("half step up", loadSequence(parent, new PImage[8], "data/_wshs/up/wshs_"), true, false, 7);
		half_step_down = new Step("half step down", loadSequence(parent, new PImage[8], "data/_wshs/down/wshs_"), false, false, 8);

		wsTurn_up = new Step("whole step turn up", loadSequence(parent, new PImage[8], "data/_wst/up/wst_"), true, false, 14);
		wsTurn_up1 = new Step("whole step turn up latter", loadSequence(parent, new PImage[8], "data/_wst/up/wst1_"), false, false, 8);
		
		wsTurn_up1_dbt_ws = new Step("wsTurn up dbtws", loadSequence(parent, new PImage[8], "data/_wst1_dbt/ws/wst1_dbt_"), false, false, 23);
		wsTurn_up1_dbt_hs = new Step("wsTurn up dbtws", loadSequence(parent, new PImage[8], "data/_wst1_dbt/hs/wst1_dbt_"), false, false, 31);
		
		wsTurn_down = new Step("wsTurn down", loadSequence(parent, new PImage[8], "data/_wst/down/wst_"), false, false, 16);
		wsTurn_down1 = new Step("wsTurn down latter", loadSequence(parent, new PImage[8], "data/_wst/down/wst1_"), true, false, 6);
		
		hsTurn_up = new Step("hsTurn up", loadSequence(parent, new PImage[8], "data/_hst/up/hst_"), true, false, 13);
		hsTurn_up1 = new Step("hsTurn up latter", loadSequence(parent, new PImage[8], "data/_hst/up/hst1_"), false, false, 10);
		
		hsTurn_down = new Step("hsTurn down", loadSequence(parent, new PImage[8], "data/_hst/down/hst_"), false, false, 15);
		hsTurn_down1 = new Step("hsTurn down latter", loadSequence(parent, new PImage[8], "data/_hst/down/hst1_"), true, false, 8);
		
		hsTurn_up1_dbt =  new Step("hsTurn up latter dbt", loadSequence(parent, new PImage[8], "data/_hst1_dbt/hst1_dbt_"), false, false, 25);
		
		wsT_up_inplace = new Step("wst_up_i", loadSequence(parent, new PImage[8], "data/_wst/up/wst1_inplace_"), false, true, 25);
		wsT_down_inplace = new Step("wst_down_i", loadSequence(parent, new PImage[8], "data/_wst/down/wst1_inplace_"), true, true, 23);
		hsT_up_inplace = new Step("hst_up_i", loadSequence(parent, new PImage[8], "data/_hst/up/hst1_inplace_"), false, true, 17);
		hsT_down_inplace = new Step("hst_down_i", loadSequence(parent, new PImage[8], "data/_hst/down/hst1_inplace_"), true, true, 15);
		inplace_up = new Step("inplace_up", loadSequence(parent, new PImage[8], "data/_inplace/up/inplace_"), false, true, 25);
		
		third = new Step("third", loadSequence(parent, new PImage[8], "data/_3rd/3rd_"), true, false, 7);
		hs_a3 = new Step("hs_a3", loadSequence(parent, new PImage[8], "data/_hs_a3/hs_a3_"), false, false, 17);
		fifth = new Step("fifth", loadSequence(parent, new PImage[8], "data/_5th/5th_"), true ,false, 15);
		fifth_turn = new Step("fifth_turn", loadSequence(parent, new PImage[8], "data/_5th/turn/5th_"), true, false, 15);
		octave = new Step("octave", loadSequence(parent, new PImage[8], "data/_8ve/8ve_"), false, false, 17);
		
		no_step_up = new Step("no_step_up", loadSequence(parent, new PImage[8], "data/_nostep/up/_nostep_"), true, false, 20);
		no_step_down = new Step("no_step_down", loadSequence(parent, new PImage[8], "data/_nostep/down/_nostep_"), true, false, 13);
		
		dbt_wsws = new Step("dbt_wsws", loadSequence(parent, new PImage[8], "data/_dbt/wsws/wsws_dbt_"), false, false, 23);
		dbt_wshs = new Step("dbt_wshs", loadSequence(parent, new PImage[8], "data/_dbt/wshs/wshs_dbt_"), false, false, 31);
		dbt_hsws = new Step("dbt_hsws", loadSequence(parent, new PImage[8], "data/_dbt/hsws/hsws_dbt_"), false, false, 28);
		
		no_step = new NoStep(jump_faceUp.getLastFrame());
		//System.out.println("setup!!");
	}
	
	static private PImage[] loadSequence(PApplet parent, PImage[] images, String prefix) {
		for (int i = 0; i < images.length; i++) {
			String filename = prefix + i + ".png";
			images[i] = parent.loadImage(filename);
			images[i].resize(0, 85);
			//images[i].resize(0, 200);
		}
		return images;
	}

	public static void setStepTest(Voice v) {
		v.setCurrentStep(dbt_hsws);
	}
	
	//TODO: Going to a new measure. Set what step it starts with. Maybe need to make it face another way 
	public static void goToMeasureStep(Voice v, int m) {
		v.setCurrentStep(no_step_up);
	}
	
	public static void setStep(Voice v) {
	
		int n2 = v.getThisKeyFrame().getN2();
		int n1 = v.getThisKeyFrame().getN1();
		int n0 = v.getThisKeyFrame().getNn();
		float b2 = v.getThisKeyFrame().getB2();
		float b1 = v.getThisKeyFrame().getB1();
		float b0 = v.getThisKeyFrame().getBn();
	
		// keep last set of notes
		int last_n2 = v.getLastKeyFrame().getN2();
		int last_n1 = v.getLastKeyFrame().getN1();
		float last_b2 = v.getLastKeyFrame().getB2();
		float last_b1 = v.getLastKeyFrame().getB1();
		
		//System.out.print("n2: " + n2);
		//System.out.print(" b2: " + b2);
		//System.out.print(" -- n1: " + n1);
		//System.out.print(" b1: " + b1);
		//System.out.print(" -- n0: " + n0);
		//System.out.print(" b0: " + b0);
		//System.out.print(" -- last_b1: " + last_b1);
		//System.out.print(" -- last_b2: " + last_b2);
		
		// SPECIAL HACK FOR MEASURE 6
		if ((b0 - b1 > 2) && (n0 == n1)){
			// second
			if (v.getCurrentStep().equals(wsTurn_up)) {
				v.setCurrentStep(wsT_up_inplace);
				//System.out.println(" --TURN IN PLACE");
			}
			// first 
			else if (!v.getCurrentStep().equals(wsT_up_inplace)) {
				v.setCurrentStep(wsTurn_up);
				//System.out.println(" -- WS TURN UP");
			}
			//third
			else if (v.getCurrentStep().equals(wsT_up_inplace)) {
				v.setCurrentStep(no_step_down);
//				no_step.update(v.getCurrentStep().getLastFrame(), v.getCurrentStep().facingUp, v.getCurrentStep().offset);
//				no_step.setPreviousAction(v.getCurrentStep());
//				v.setCurrentStep(no_step);
				//System.out.println(" --NO STEP h");
			}
		}
		
		// NO STEP or TURN IN PLACE
		else if ((b1 == last_b1) && (b2 == last_b2)) {
			// TURN IN PLACE after DBT
			if (((b2 * 2) % 2 == 1) && (n0 >= n1) && (v.getCurrentStep().turnInPlace == false)) {
				v.setCurrentStep(inplace_up);
				//System.out.println(" -- IN PLACE UP");
			}
			
			// for end of the line
			else if ((n2 - n1 >=2) && (n0 == n1) && (v.getCurrentStep().turnInPlace == false)) {
				v.setCurrentStep(inplace_up);
				//System.out.println(" -- IN PLACE UP - end");
			}
			
			// TURN IN PLACE after turn
			else if ((n0 - n1 > 2) && (v.getCurrentStep().turnInPlace == false)) {
				if (n2 - n1 >= 2){
					v.setCurrentStep(wsT_down_inplace);
					//System.out.println(" -- WST- DOWN1 - INPLACE");
				} else if (n2 - n1 == 1) {
					v.setCurrentStep(wsT_up_inplace);
					//System.out.println(" -- HST- DOWN1 - INPLACE");
				} 
			}
			else if ((n1 - n2 > 2) && (v.getCurrentStep().turnInPlace == false)) {
			
				if (n2 - n1 <= -2) {
					v.setCurrentStep(hsT_down_inplace);
					//System.out.println(" -- WST- UP1 - INPLACE");
				} else if (n2 - n1 == -1) {
					v.setCurrentStep(hsT_up_inplace);
					//System.out.println(" -- HST- UP1 - INPLACE");
				}
			}
			// NO STEP
			else {
				Step c = v.getCurrentStep();
				if (c.equals(no_step_up) || c.equals(no_step_down) || c.equals(no_step) || c.equals(wsTurn_up) || c.equals(wsTurn_down) || c.equals(hsTurn_up) || c.equals(hsTurn_down)) {
					no_step.update(v.getCurrentStep().getLastFrame(), v.getCurrentStep().facingUp, v.getCurrentStep().offset);
					no_step.setPreviousAction(v.getCurrentStep());
					v.setCurrentStep(no_step);
				} else if (c.facingUp) {
					v.setCurrentStep(no_step_up);
				} else
					v.setCurrentStep(no_step_down);
				//System.out.println(" --NO STEP s");
			}
		}
		
		// JUMP
		else if (n1 == n2) {
			if (n0 >= n1) {
				v.setCurrentStep(jump_faceUp);
				//System.out.println(" --JUMP UP");
			} else {
				v.setCurrentStep(jump_faceDown);
				//System.out.println(" --JUMP DOWN");
			}
		}
		
		// DOUBLE TIME
		else if ((b2 * 2) % 2 == 1) {
			// Coming out of a turn WS turn
			if (v.getCurrentStep().equals(wsTurn_up)) {
				if (n2 - n1 == 2) {
					v.setCurrentStep(wsTurn_up1_dbt_ws);
					//System.out.println(" --WS TURN ONE DBT WS");
				} else if (n2 - n1 == 1) {
					v.setCurrentStep(wsTurn_up1_dbt_hs);
					//System.out.println(" --WS TURN ONE DBT HS");
				}
			}
			// Coming out of a HS turn
			else if (v.getCurrentStep().equals(hsTurn_up)) {
				v.setCurrentStep(hsTurn_up1_dbt);
				//System.out.println(" -- HS TURN DBT");
			}
			
			// Not coming out of turn
			else {
				// first part is WS
				if (last_n1 - n2 == 2) {
					if (n2 - n1 == 2) {
						v.setCurrentStep(dbt_wsws);
						//System.out.println(" -- DBT WSWS");
					} else {
						v.setCurrentStep(dbt_wshs);
						//System.out.println(" --DBT WSHS");
					}
				} // first part is HS 
				else {
					v.setCurrentStep(dbt_hsws);
					//System.out.println(" -- DBT HSWS");
				}
			}
		}
		
		// WHOLE STEP UP
		else if (n1 - n2 == 2) {
			// first part of turn
			if (n1 - n0 > 0 ) {
				v.setCurrentStep(wsTurn_up);
				//System.out.println(" --WS TURN UP");
			}
			// second part of turn
			else if(v.getCurrentStep().equals(wsTurn_down) || (v.getCurrentStep().equals(no_step) && 
					v.getCurrentStep().getPreviousAction().equals(wsTurn_down))) {
				v.setCurrentStep(wsTurn_down1);
				//System.out.println(" --WS TURN DOWN 1");
			}
			// normal whole step
			else {
				v.setCurrentStep(whole_step_up);
				//System.out.println(" --WS UP");
			}
		}

		// WHOLE STEP DOWN
		else if (n1 - n2 == -2) {
			// first part of turn
			if (n1 - n0 < 0 ) {
				v.setCurrentStep(wsTurn_down);
				//System.out.println(" --WS TURN DOWN");
			}
			// second part of turn
			else if(v.getCurrentStep().equals(wsTurn_up) || (v.getCurrentStep().equals(no_step) && 
					v.getCurrentStep().getPreviousAction().equals(wsTurn_up))) {
				v.setCurrentStep(wsTurn_up1);
				//System.out.println(" --WS TURN UP 1");
			}
			// normal WS down
			else {
				v.setCurrentStep(whole_step_down);
				//System.out.println(" --WS DOWN");
			}
		}
		
		// HALF STEP UP
		else if (n1 - n2 == 1) {
			// first part of turn
			if (n1 - n0 > 0) {
				v.setCurrentStep(hsTurn_up);
				//System.out.println(" --HS TURN UP");
			}
			// second part of turn
			else if(v.getCurrentStep().equals(hsTurn_down) || (v.getCurrentStep().equals(no_step) && 
					v.getCurrentStep().getPreviousAction().equals(hsTurn_down))) {
				v.setCurrentStep(wsTurn_down1);
				//System.out.println(" --HS TURN DOWN 1");
			}
			// normal HS up
			else {
				v.setCurrentStep(half_step_up);
				//System.out.println(" --HS UP");
			}
		}
		
		// HALF STEP DOWN
		else if (n1 - n2 == -1) {
			// turn down from third
			if (v.getCurrentStep().equals(third)) {
				v.setCurrentStep(hs_a3);
				//System.out.println(" --HS DOWN FROM 3rd");
			} 
			// first part of turn
			else if (n1 - n0 < 0 ) {
				v.setCurrentStep(hsTurn_down);
				//System.out.println(" --HS TURN DOWN");
			}
			// second part of turn
			else if(v.getCurrentStep().equals(hsTurn_up) || (v.getCurrentStep().equals(no_step) && 
					v.getCurrentStep().getPreviousAction().equals(hsTurn_up))) {
				v.setCurrentStep(hsTurn_up1);
				//System.out.println(" --HS TURN UP 1");
			}
			else {
				v.setCurrentStep(half_step_down);
				//System.out.println(" --HS DOWN");
			}
		}

		// THIRD
		else if (n1 - n2 == 3) {
			v.setCurrentStep(third);
			//System.out.println(" --3rd");
		}
		
		// FIFTH
		else if (n1 - n2 == 7) {
			if (n0  > n1) {
				v.setCurrentStep(fifth);
				//System.out.println(" --5th");
			} else {
				v.setCurrentStep(fifth_turn);
				//System.out.println(" --5th turn");
			}
		}
		
		// OCTAVE
		else if (n2 - n1 == 12) {
			v.setCurrentStep(octave);
			//System.out.println(" --8ve");
		}
		
		else {
			//no_step.setLastFrame(v.getCurrentStep().getLastFrame());
			//v.setCurrentStep(no_step);
			//System.out.println(" --NOT ASSIGNED");
		}
		
		v.setAnimNote(n1);
		v.setLastkeyFrame(n2, n1, n0, b2, b1, b0);
	}
}
