package canon;

import processing.core.PApplet;

public class BlockPlayer {
	// some voices in the Canon
	public Voice v1;
	public Voice v2;
	public Voice v3;
	public Voice v4;

	// keep track of frames
	int frame_counter = 0;
	int frames_per_measure = 32; // TODO: does this depend on framerate?
	int beats_per_measure = 4;
	int global_measure = 0;
	int total_measures;
	int start_measure = 0;
	int measures_to_play = 6;
	
	// drawing!
	int startY = 454;		// TODO: currently super arbitrary. figure out what it is on the piano
	float deltaY = 0;
	
	int pixels_per_beat = 20;
	//float pixels_per_frame = .66f;
	float pixels_per_frame = 2.5f;
	
	int epsilon = 4;
	float note_width = 7.72f;
	int block_width = 4;
	float x_offset = 105f;
	
	int screen_y_top = 321;
	int screen_y_bottom = 471;
	
	// playing notes
	int send_note_line = 455;
	int last_note_played = 0;
	
	public BlockPlayer() {
		v1 = new Voice(0, 48);
		v2 = new Voice(1, 55);
		v3 = new Voice(2, 62);
		v4 = new Voice(3, 69);
		
		XMLManager.readXML("data/voice1.xml", v1.measures, 0);
		XMLManager.readXML("data/voice2.xml", v2.measures, 0);
		XMLManager.readXML("data/voice3.xml", v3.measures, 0);
		XMLManager.readXML("data/voice4.xml", v4.measures, 0);
		
		v1.initBlocks(pixels_per_beat, 0, note_width,  x_offset, epsilon); 
		v2.initBlocks(pixels_per_beat, 0, note_width, x_offset, epsilon);
		
		total_measures = v1.measures.size();
	}
	
	public void run(Canon parent) {
		parent.background(0);
		
		// everything but the last measure
		int start_measure_offset = - pixels_per_beat * 4 * start_measure;
		int last_measure = start_measure + measures_to_play;
		for (int i = start_measure; i < last_measure; i++) {
			Measure m_v1 = v1.measures.get(i);
			Measure m_v2 = v2.measures.get(i);
			
			if (parent.canonMode !=2) {
				parent.fill(125, 255, 255, 200);
				drawBlockPlayMusic(parent, i, m_v1, 0, start_measure_offset, false);
				if (parent.canonMode == 3) {
					parent.fill(255, 255, 125, 200);
					drawBlockPlayMusic(parent, i, m_v2, 1, start_measure_offset, false);
				}
			} else { 
				parent.fill(255, 255, 125, 200);
				drawBlockPlayMusic(parent, i, m_v2, 0, start_measure_offset, false);
			}
		}
		
		// The first note of the last measure
		
		Measure m_v1 = v1.measures.get(last_measure);
		Measure m_v2 = v2.measures.get(last_measure);
		if (parent.canonMode != 2) {
			parent.fill(125, 255, 255, 200);
			drawBlockPlayMusic(parent, last_measure, m_v1, 0, start_measure_offset, true);
			if (parent.canonMode == 3) {
				parent.fill(255, 255, 125, 200);
				drawBlockPlayMusic(parent, last_measure, m_v2, 1, start_measure_offset, true);
			}
		} else {
			parent.fill(255, 255, 125, 200);
			drawBlockPlayMusic(parent, last_measure, m_v2, 0, start_measure_offset, true);
		}
	
	
		// cover the parts we don't want to see
		parent.fill(0);
		parent.noStroke();
		parent.quad(98, 464, 1000, 482, 1000, 675, 1, 677);
		parent.rect(183, 0, 711, 310);

		// shift the whole thing down
		deltaY += pixels_per_frame;
		if (last_measure == 5)
			deltaY %= beats_per_measure * pixels_per_beat * (last_measure - start_measure + 2.4 + parent.canonMode/3);
		else
			deltaY %= beats_per_measure * pixels_per_beat * (last_measure - start_measure + 1 + parent.canonMode/3);
	}
	
	public void resetMeasures(Canon parent) {
		deltaY = 0;
		parent.output.sendNoteOff(0, last_note_played, 40);
		parent.output.sendNoteOff(0, last_note_played, 40);
		parent.output.sendNoteOff(0, last_note_played, 40);
		parent.output.sendNoteOff(0, last_note_played, 40);
		parent.output.sendNoteOff(0, last_note_played, 40);
	}
	
	/**
	 * 
	 * @param parent
	 * @param i
	 * @param m
	 * @param voice_offset - to offset the different voices of this canon
	 * @param loop_offset - to offset the whole thing: for looping
	 */
	private void drawBlockPlayMusic(Canon parent, int i, Measure m, int voice_offset, int loop_offset, boolean just_the_tip) {
		int k_length = m.beats.length;
		if (just_the_tip)
			k_length = 1;
		for (int k = 0; k < k_length; k ++) {
			int block_y = (int) (startY + m.block_xys[k].y + deltaY - pixels_per_beat * 4 * (i + voice_offset) - loop_offset);
			parent.rect(m.block_xys[k].x, block_y, block_width, m.block_lengths[k]);
			
			// playing sound
			if ((block_y + m.block_lengths[k] > send_note_line) 
					&& (block_y + m.block_lengths[k] < send_note_line + pixels_per_frame * 2)
					&& m.notes_on[k] < 0) {
				parent.output.sendNoteOn(0, m.notes[k], m.velocity[k]);
				int lastNotePlayed = m.notes[k];
				m.notes_on[k] = 0;
			} else if ((block_y > send_note_line) && m.notes_on[k] == 0) {
				parent.output.sendNoteOff(0, m.notes[k], 40);
				parent.output.sendNoteOff(0, m.notes[k], 40);
				parent.output.sendNoteOff(0, m.notes[k], 40);
				parent.output.sendNoteOff(0, m.notes[k], 40);
				parent.output.sendNoteOff(0, m.notes[k], 40);
				parent.output.sendNoteOff(0, m.notes[k], 40);
				m.notes_on[k] = -1;
			}
		}
	}
}
