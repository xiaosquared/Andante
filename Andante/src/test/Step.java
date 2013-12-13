package test;
import processing.core.PApplet;
import processing.core.PImage;


public class Step {
	
	private Note startNote;
	private Note endNote;
	
	public boolean goingUp;

	private PImage[] sequence;
	
	private int frame = 0;
	private int x;
	
	public Step(Note startNote, Note endNote, PImage[] sequence, boolean goingUp) {
		this.startNote = startNote;
		this.endNote = endNote;
		this.sequence = sequence;
		this.goingUp = goingUp;
		
		if (goingUp)
			x = startNote.getAnimationX();
		else
			x = startNote.getAnimationX() - sequence[0].width + 22;
	}
	
	/*
	 * Total hack for the second half of the turn to look ok
	 */
	public void setHackX(int x) {
		this.x = x;
	}
	
	public int getX(){
		return x;
	}
	
	public Note getStartNote() {
		return startNote;
	}
	
	public Note getEndNote() {
		return endNote;
	}
	
	public void setStartNote(Note newNote) {
		startNote = newNote;
		if (goingUp)
			x = startNote.getAnimationX();
	}
	
	public void setEndNote(Note newNote) {
		endNote = newNote;
		if (!goingUp)
			x = startNote.getAnimationX() - sequence[0].width + 22;
	}
	
	/**
	 * @return true if we're at the end of sequence
	 */
	public boolean drawFrame(Pentatonic parent, int img_y) {
		if (frame == 0)
			parent.output.sendNoteOn(0, endNote.getMidiValue(), 50);
		else
			parent.output.sendNoteOff(0, startNote.getMidiValue(), 30);
		
		
		double y = ((double)x/692)*20 + img_y;
		parent.image(sequence[frame], x, (int) y);
		frame++;
		if (frame == sequence.length) {
			frame = 0;
			return true;
		}
		else
			return false;
	}
}
