package canon;

import anim.Step;
import anim.CanonStepManager;
import processing.core.PApplet;
import processing.core.PImage;

public class Player {
	
	// some voices in the Canon
	Voice v1;
	Voice v2;
	Voice v3;
	Voice v4;
	
	// keep track of frames
	int frame_counter = 0;
	int anim_frame_counter;
	int frames_per_measure = 32;
	
	int global_measure = 0;
	int total_measures;
	
	// initial fade in
	int c_start_x = 75;
	int init_frame_counter = 0;

	public Player() {
		v1 = new Voice(0, 48);
		v2 = new Voice(1, 55);
		v3 = new Voice(2, 62);
		v4 = new Voice(3, 69);
		
		XMLManager.readXML("data/voice1.xml", v1.measures, 0);
		XMLManager.readXML("data/voice2.xml", v2.measures, 0);
		XMLManager.readXML("data/voice3.xml", v3.measures, 0);
		XMLManager.readXML("data/voice4.xml", v4.measures, 0);
		
		total_measures = v1.measures.size();
	}
	

	public void initFigure(Canon parent) {
		parent.background(0);
		
		int alpha = 255 * init_frame_counter / frames_per_measure; 
		parent.tint(255, alpha);
		
		parent.image(parent.standing_still, c_start_x, 50);
		
		initUpdateFrame(parent);
	}
	
	public void pause(Canon parent) {
		noteOff(v1, parent);
		noteOff(v2, parent);
		noteOff(v3, parent);
		noteOff(v4, parent);
	}
	
	public void run(Canon parent) {
		// Figure out what beat we're on based on the frame
		int my_frame = frame_counter % frames_per_measure;
		float my_beat = getMyBeat(my_frame);
		
		// Figure out what frame of measure we're on for animation
		int anim_frame = getAnimFrame(my_frame, parent.target_frame_rate);

		if  ((anim_frame % 8) == 0) {
			//StepManager.setStepTest(v1);
			CanonStepManager.setStep(v1);
			CanonStepManager.setStep(v2);
			CanonStepManager.setStep(v3);
			CanonStepManager.setStep(v4);
		}
		drawStep(parent, anim_frame % 8 );
		
		
		// if we're not on a beat that sends midi signals things, return
		if (my_beat == -1) {
			updateFrame();
			return;
		}
		
		playMidiVoices(parent, my_beat);
		
		updateFrame();
	}
	
	private void drawStep(Canon parent, int frame) {
		
		if (parent.trails) 
			parent.rect(134, 363, 500, 120);
		else
			parent.background(0);
			
		if (parent.one) {
			parent.tint(255, 255, 255, 90);
			v1.getCurrentStep().drawFrame(parent, v1.getAnimNote(), frame, parent.img_y);
		}
		if (parent.two) {
			parent.tint(255, 255, 125, 90);
			v2.getCurrentStep().drawFrame(parent, v2.getAnimNote(), frame, parent.img_y);
		}
		if (parent.three) {
			parent.tint(125, 255, 255, 90);
			v3.getCurrentStep().drawFrame(parent, v3.getAnimNote(), frame, parent.img_y);
		}
		if (parent.four) {
			parent.tint(255, 124, 255, 90);
			v4.getCurrentStep().drawFrame(parent, v4.getAnimNote(), frame, parent.img_y);
		}
	}
	
	private void playMidiVoices(Canon parent, float my_beat) {
		int looping_global_measure = global_measure % total_measures;
		
		playVoice(v1, my_beat, looping_global_measure, parent, parent.one);
		playVoice(v2, my_beat, looping_global_measure, parent, parent.two);
		playVoice(v3, my_beat, looping_global_measure, parent, parent.three);
		playVoice(v4, my_beat, looping_global_measure, parent, parent.four);

	}
	
	public int getAnimFrame(int frame, int fps) {
		int offset = getAnimOffset(fps);
		if (frame >= offset)
			return frame - offset;
		return frame + frames_per_measure - offset;
	}

	private int getAnimOffset(int fps) {
		//return 1;
		return fps/4-2;
	}
	
	private void updateFrame() {
		frame_counter ++;
		//System.out.println(frame_counter);
		if ((frame_counter % frames_per_measure) == 0) {
			//frame_counter = 0;
			incrementMeasure();
		}
	}
	
	private void noteOff(Voice v, Canon parent) {
		parent.output.sendNoteOff(0, v.getLastPlayedNote(), 30);
		parent.output.sendNoteOff(0, v.getN2(), 30);
	}
	
	private void playVoice(Voice v, float my_beat, int global_measure_looped, Canon parent, boolean playNote) {
		if (global_measure > v.getMeasureOffset() - 1) {
			Measure m = null;
			if (global_measure_looped > v.getMeasureOffset() - 1) 
				m = v.getMeasure(global_measure_looped);
			else 
				m = v.getMeasure(global_measure_looped + total_measures);

			float current_beat_m = m.getCurrentBeat();
			handleNotes(current_beat_m, my_beat, m, v, parent, playNote);
		}

	}
	
	/**
	 * @param current_beat_m Ð the beat of the measure that the next note arrives on
	 * @param my_beat Ð the beat we're on when playing the measure
	 */
	private void handleNotes(float current_beat_m, float my_beat, Measure m, Voice v, Canon parent, boolean playNote) {
		if (current_beat_m == my_beat) {
	
			int current_note = m.getCurrentNote();
			float current_beat = m.getCurrentBeat();
			int current_note_velocity = m.getCurrentNoteVelocity();
			
			float[] container = new float[2];	// an array to store results of helper method
			getNextNoteAndBeat(m, v, container);
			int new_nn = (int) container[0];
			float new_nb = container[1];
			
			int new_n2 = v.getLastPlayedNote();
			float new_b2 = v.getLastPlayedBeat();
			
			// send notes
			if (playNote) 
				parent.output.sendNoteOn(0, current_note, current_note_velocity);
			parent.output.sendNoteOff(0, v.getLastPlayedNote(), 50);
			parent.output.sendNoteOff(0, v.getLastPlayedNote(), 50);
			parent.output.sendNoteOff(0, v.getLastPlayedNote(), 50);
			parent.output.sendNoteOff(0, v.getLastPlayedNote(), 50);
			
			// store info about note/beat profile of this keyframe
			v.setThisKeyFrame(new_n2, current_note, (int) container[0], 
					new_b2, current_beat, container[1]);

			m.incrementBeat();
			
			if (m.atMeasureEnd())
				m.resetMeasure();
		}
	}
	
	private void getNextNoteAndBeat(Measure m, Voice v, float[] container) {
		float beat = m.getNextBeat();
		if (beat != -1) {
			container[0] = m.getNextNote();
			container[1] = m.getNextBeat();
		} else {
			Measure m2 = v.getMeasureFromId(m.id + 1);
			container[0] = m2.getCurrentNote();
			container[1] = m2.getCurrentBeat();
		}
	}

	// if we have finished fading in, toggle init in Canon to false
	private void initUpdateFrame(Canon parent) {
		init_frame_counter ++;
		if (init_frame_counter == frames_per_measure) {
			init_frame_counter = 0;
			parent.init = false;
			parent.isPaused = false;
		}
	}
	

	private void incrementMeasure() {
		global_measure++;
	}
	
	private float getMyBeat(int frame) {
		switch (frame) {
		case 0:
			return 1;
		case 4:
			return 1.5f;
		case 8:
			return 2;
		case 12:
			return 2.5f;
		case 16:
			return 3;
		case 20:
			return 3.5f;
		case 24:
			return 4;
		case 28:
			return 4.5f;
		default:
			return -1;
		}
	}
}
