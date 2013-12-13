package canon;

public class FrameProfile {
	int two_notes_back = 20; 			// the note prior to what we just played
	int last_played_note = 20;			// the note that we just played
	int next_note = 20;
	
	float two_beats_back = 0;
	float last_played_beat = 1;
	float next_beat = 1;
	
	public FrameProfile(int n2, int n1, int nn, float b2, float b1, float nb) {
		two_notes_back = n2;
		last_played_note = n1;
		next_note = nn;
		
		two_beats_back = b2;
		last_played_beat = b1;
		next_beat = nb;
	}
	
	public int getN2() { return two_notes_back; }
	public void setN2(int note) { this.two_notes_back = note; }
	
	public int getN1() { return last_played_note; }
	public void setN1(int note) { last_played_note = note; }

	public int getNn() { return next_note; }
	public void setNn(int note) { next_note = note; }

	public float getB2() { return two_beats_back; }
	public void setB2(float beat) { two_beats_back = beat; }

	public float getB1() { return last_played_beat; }
	public void setB1(float beat) { last_played_beat = beat; }
	
	public float getBn() { return next_beat; }
	public void setBn(float beat) { next_beat = beat; }
}
