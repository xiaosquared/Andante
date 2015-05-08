package dalcroze;

import canon.Canon;
import canon.Measure;
import canon.Voice;
import canon.XMLManager;

public class AllegroPlayer {
	// voices: only 2
	Voice RH, LH1, LH2, LH3;
	
	// keep track of frames
	int frame_counter = 0;
	int anim_frame_counter;
	int frames_per_measure = 32;
	
	int global_measure = 0;
	int total_measures;
	
	
	// playing a small segment. Length is how many measures.
	int segment_measure_start = 0;
	int segment_measure_length = 2;
	int frames_in_segment = -1;
	int segment_frame_counter = 0;
	//***********************************************************
	
	
	public AllegroPlayer() {
		RH = new AllegroVoice(0, 72); 	//C5
		LH1 = new AllegroVoice(0, 48);	//C3
		LH2 = new AllegroVoice(0, 52);	//E3
		LH3 = new AllegroVoice(0, 55);	//G3
		
		XMLManager.readXML("data/allegro_RH.xml", RH.getMeasures(), 0);
		XMLManager.readXML("data/allegro_LH1.xml", LH1.measures, 0);
		XMLManager.readXML("data/allegro_LH2.xml", LH2.measures, 0);
		XMLManager.readXML("data/allegro_LH3.xml", LH3.measures, 0);
		
		total_measures = RH.getMeasures().size();
	}
	
	public void pause(Allegro parent) {
		noteOff(RH, parent);
		noteOff(LH1, parent);
		noteOff(LH2, parent);
		noteOff(LH3, parent);
		
		RH.note_index = 0;
		LH1.note_index = 0;
		LH2.note_index = 0;
		LH3.note_index = 0;
		resetMeasures(parent);
		restart();
	}
	
	public void resetMeasures(Allegro parent) { 			// kind of brute force but oh well... 
		for (int i = 0; i < RH.measures.size(); i++) {
			RH.resetMeasure(i);
			LH1.resetMeasure(i);
			LH2.resetMeasure(i);
			LH3.resetMeasure(i);
		}
	}
	
	public void restart() {
		frame_counter = 0;
		global_measure = 0;
		
	}
	
	public void goToMeasure(int measure) {
		segment_measure_start = measure;
		frames_in_segment = segment_measure_length * frames_per_measure;
		frame_counter = 0;
		global_measure = measure;
		
		RH.note_index = measure * 4;
	}
	
	public void setSegmentLength(int l) {
		segment_measure_length = l;
		frames_in_segment = segment_measure_length * frames_per_measure;
	}
	
	private void endOfSegment(Allegro parent) {
		// reset measures
		resetMeasures(parent);
		
		// reset notes
		noteOff(RH, parent);
		noteOff(LH1, parent);
		
		goToMeasure(global_measure - segment_measure_length);
	}
	
	public void run(Allegro parent) {
		// Figure out what beat we're on based on the frame
		int my_frame = frame_counter % frames_per_measure;
		float my_beat = getMyBeat(my_frame);
		
		// Takes care of the animation visuals
		int anim_frame = getAnimFrame(my_frame, parent.target_frame_rate);
		if ((anim_frame % 8) == 0) {
			
			AllegroStepManager.setStep(RH, global_measure);
		}
		drawStep(parent, anim_frame % 8);
		
		//set harmony
		if ((anim_frame % 8 ) == 7) {
			int prev_harmony = RH.harmony;
			switch(RH.note_index) {
			case 0: case 6: case 10: case 14: case 18: case 22: case 28:  RH.harmony = 1; break;
			case 4: case 16: case 20: case 24: RH.harmony = 4; break;
			case 8: case 12: case 30: RH.harmony = 5; break;
			}
			if (RH.harmony != prev_harmony)
				AllegroHarmony.fade_in = 0;
		}
		
		if (frame_counter == frames_in_segment) {  
			endOfSegment(parent);
			return;
		}
		
		// if we're not on a beat that sends MIDI signals, update the frame/measure and return
		if (my_beat == -1) {
			updateFrame();
			return;
		}
		
		// else sent MIDI signals and update the frame
		playMidiVoices(parent, my_beat);
		updateFrame();
	}
	
	/****************************************************
	 * Utility methods for animation
	 */
	private void drawStep(Allegro parent, int frame) {
		parent.fill(0);
		parent.rect(390, 350, 534, 475);
		parent.fill(0);
		parent.rect(200, 350, 190, 150);
		
		if (parent.voicesMode != 2) {

			parent.tint(255, 255, 255, 200);
			RH.getCurrentStep().drawFrame(parent, RH.getAnimNote(), frame, parent.img_y);
			
			if (parent.voicesMode == 3)
				AllegroHarmony.drawHarmony(parent, RH.harmony);
		} else		
			AllegroHarmony.drawHarmony(parent, RH.harmony);
	
	}
	
	/**
	 * Which frame of of the animation we are on?
	 */
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
	
	/**************************************************
	 * Utility methods to take care of sending to MIDI
	 */
	private void playMidiVoices(Allegro parent, float my_beat) {
		int looping_global_measure = global_measure % total_measures;
		playVoice(RH, my_beat, looping_global_measure, parent, true);
		playVoice(LH1, my_beat, looping_global_measure, parent, true);
		playVoice(LH2, my_beat, looping_global_measure, parent, true);
		playVoice(LH3, my_beat, looping_global_measure, parent, true);
	}
	
	private void playVoice(Voice v, float my_beat, int global_measure_looped, Allegro parent, boolean playNote) {
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
	private void handleNotes(float current_beat_m, float my_beat, Measure m, Voice v, Allegro parent, boolean playNote) {
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
			parent.output.sendNoteOff(0, v.getLastPlayedNote(), 50);
			parent.output.sendNoteOff(0, v.getLastPlayedNote(), 50);
			parent.output.sendNoteOff(0, v.getLastPlayedNote(), 50);
			parent.output.sendNoteOff(0, v.getLastPlayedNote(), 50);
			if (playNote) 
				parent.output.sendNoteOn(0, current_note, current_note_velocity);
			
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
			Measure m2 = v.getMeasureFromId(m.getId() + 1);
			container[0] = m2.getCurrentNote();
			container[1] = m2.getCurrentBeat();
		}
	}
	
	
	/**
	 * Utility method to unplay MIDI notes currently sent to the piano
	 */
	private void noteOff(Voice v, Allegro parent) {
		parent.output.sendNoteOff(0, v.getLastPlayedNote(), 30);
		parent.output.sendNoteOff(0, v.getLastPlayedNote(), 30);
		parent.output.sendNoteOff(0, v.getLastPlayedNote(), 30);
		parent.output.sendNoteOff(0, v.getLastPlayedNote(), 30);
		parent.output.sendNoteOff(0, v.getLastPlayedNote(), 30);
		parent.output.sendNoteOff(0, v.getN2(), 30);
		parent.output.sendNoteOff(0, v.getN2(), 30);
		parent.output.sendNoteOff(0, v.getN2(), 30);
		parent.output.sendNoteOff(0, v.getN2(), 30);
		parent.output.sendNoteOff(0, v.getN2(), 30);
		
	}
	
	
	
	/**************************************************
	 * General timing methods
	 */
	
	/**
	 * Utility that increments the frame and measure
	 */
	private void updateFrame() {
		frame_counter ++;
		//System.out.println(frame_counter);
		if ((frame_counter % frames_per_measure) == 0) {
			//frame_counter = 0;
			global_measure++;
		}
	}
		
	/**
	 * Utility method to find what beat we are on
	 */
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
