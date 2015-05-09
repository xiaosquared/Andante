package dalcroze;

import canon.Canon;
import canon.Measure;
import canon.Voice;
import canon.XMLManager;

public class AllegroBlockPlayer {
	Voice RH, LH1, LH2, LH3;
	
	int pixels_per_beat = 20;
	float note_width = 7.72f;
	float x_offset = 105f;
	int epsilon = 4;
	
	int startY = 454;		// TODO: currently super arbitrary. figure out what it is on the piano
	float deltaY = 0;
	float pixels_per_frame = 2.5f;
	int beats_per_measure = 4;
	
	int start_measure = 0;
	int measures_to_play = 8;
	int total_measures;
	
	int block_width = 4;

	// playing notes
	int send_note_line = 455;
	int last_note_RH = 0;
	int last_note_LH1 = 0;
	int last_note_LH2 = 0;
	int last_note_LH3 = 0;
	int last_note_LH4 = 0;

	int frames_per_measure = 32;
	int frame = 0;
	int total_frames = (measures_to_play - start_measure) * frames_per_measure;
	
	public AllegroBlockPlayer() {
		RH = new AllegroVoice(0, 72); 	//C5
		LH1 = new AllegroVoice(0, 48);	//C3
		LH2 = new AllegroVoice(0, 52);	//E3
		LH3 = new AllegroVoice(0, 55);	//G3
		
		XMLManager.readXML("data/allegro_RH.xml", RH.measures, 0);
		XMLManager.readXML("data/allegro_LH1.xml", LH1.measures, 0);
		XMLManager.readXML("data/allegro_LH2.xml", LH2.measures, 0);
		XMLManager.readXML("data/allegro_LH3.xml", LH3.measures, 0);
		
		RH.initBlocks(pixels_per_beat, 0, note_width,  x_offset, epsilon); 
		LH1.initBlocks(pixels_per_beat, 0, note_width,  x_offset, epsilon); 
		LH2.initBlocks(pixels_per_beat, 0, note_width,  x_offset, epsilon); 
		LH3.initBlocks(pixels_per_beat, 0, note_width,  x_offset, epsilon); 
		
		
		total_measures = RH.measures.size();
	}

	public void run(Allegro parent) {
		parent.background(0);
		
		// everything but the last measure
		int start_measure_offset = - pixels_per_beat * 4 * start_measure;
		int last_measure = start_measure + measures_to_play;
		for (int i = start_measure; i < parent.min(last_measure, total_measures); i++) {
			Measure m = RH.measures.get(i);
			Measure m_1 = LH1.measures.get(i);
			Measure m_2 = LH2.measures.get(i);
			Measure m_3 = LH3.measures.get(i);
			
			int n;
			if (parent.voicesMode != 1) {
				n = drawBlockPlayMusic(parent, i, m_1, start_measure_offset, true);
				if (n!= 0)
					last_note_LH1 = n;

				n = drawBlockPlayMusic(parent, i, m_2, start_measure_offset, true);
				if (n!= 0)
					last_note_LH2 = n;

				n = drawBlockPlayMusic(parent, i, m_3, start_measure_offset, true);
				if (n!= 0)
					last_note_LH3 = n;

				if (parent.voicesMode == 3) {
					n = drawBlockPlayMusic(parent, i, m, start_measure_offset, false);
					if (n!= 0)
						last_note_RH = n;
				}
			} else { 
				n = drawBlockPlayMusic(parent, i, m, start_measure_offset, false);
				if (n!= 0)
					last_note_RH = n;
			}
			
		}

		// cover the parts we don't want to see
		parent.fill(0);
		parent.noStroke();
		parent.quad(98, 464, 1000, 482, 1000, 675, 1, 677);
		parent.rect(183, 0, 711, 310);

		// shift the whole thing down
		deltaY += pixels_per_frame;
		deltaY %= beats_per_measure * pixels_per_beat * (parent.min(last_measure, total_measures) - start_measure + .4);
		frame++;
		frame %= total_frames;
	}
	
	public void resetMeasures(Allegro parent) {
		deltaY = 0;
		parent.output.sendNoteOff(0, last_note_RH, 40);
		parent.output.sendNoteOff(0, last_note_RH, 40);
		parent.output.sendNoteOff(0, last_note_RH, 40);
		parent.output.sendNoteOff(0, last_note_RH, 40);
		
		parent.output.sendNoteOff(0, last_note_LH1, 40);
		parent.output.sendNoteOff(0, last_note_LH1, 40);
		parent.output.sendNoteOff(0, last_note_LH1, 40);
		parent.output.sendNoteOff(0, last_note_LH1, 40);
		
		parent.output.sendNoteOff(0, last_note_LH2, 40);
		parent.output.sendNoteOff(0, last_note_LH2, 40);
		parent.output.sendNoteOff(0, last_note_LH2, 40);
		parent.output.sendNoteOff(0, last_note_LH2, 40);
		
		parent.output.sendNoteOff(0, last_note_LH3, 40);
		parent.output.sendNoteOff(0, last_note_LH3, 40);
		parent.output.sendNoteOff(0, last_note_LH3, 40);
		parent.output.sendNoteOff(0, last_note_LH3, 40);
		
		for (int i = start_measure; i < start_measure + measures_to_play; i++) {
			Measure m_rh = RH.measures.get(i);
			Measure m_lh1 = LH1.measures.get(i);
			Measure m_lh2 = LH2.measures.get(i);
			Measure m_lh3 = LH3.measures.get(i);
			for (int k = 0; k < m_rh.beats.length; k++)
				m_rh.notes_on[k] = -1;
			for (int k = 0; k < m_lh1.beats.length; k++)
				m_lh1.notes_on[k] = -1;
			for (int k = 0; k < m_lh2.beats.length; k++)
				m_lh2.notes_on[k] = -1;
			for (int k = 0; k < m_lh3.beats.length; k++)
				m_lh3.notes_on[k] = -1;
		}
	}
	
	/**
	 * 
	 * @param parent
	 * @param i
	 * @param m
	 * @param voice_offset - to offset the different voices of this canon
	 * @param loop_offset - to offset the whole thing: for looping
	 */
	private int drawBlockPlayMusic(Allegro parent, int i, Measure m, int loop_offset, boolean color_harmonies) {
		int lastNotePlayed = 0;
		for (int k = 0; k < m.beats.length; k ++) {
			// coloring harmonies - super clunky :-(
			if (color_harmonies) 
				if ((k == 1 && (i != 7)) || (i == 0) || (k == 0 && i == 7))
					parent.fill(255);
				else if ((k == 0) && ((i == 1) || (i == 4) || (i == 5) || (i == 6))) 
					parent.fill(255, 255, 125, 200);
				else 
					parent.fill(125, 255, 255, 200);
			else
				parent.fill(255);
			
			int block_y = (int) (startY + m.block_xys[k].y + deltaY - pixels_per_beat * 4 *i - loop_offset);
			parent.rect(m.block_xys[k].x, block_y, block_width, m.block_lengths[k]);

			// playing sound
			if ((block_y + m.block_lengths[k] > send_note_line) 
					&& (block_y + m.block_lengths[k] < send_note_line + pixels_per_frame * 2)
					&& m.notes_on[k] < 0) {
				parent.output.sendNoteOn(0, m.notes[k], m.velocity[k]);
				lastNotePlayed = m.notes[k];
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
		return lastNotePlayed;
	}
}
