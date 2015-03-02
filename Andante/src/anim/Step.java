package anim;

import canon.Canon;
import processing.core.PApplet;
import processing.core.PImage;

public class Step {
	String name;
	protected PImage[] sequence;
	protected int frame = 0;

	protected boolean facingUp;
	protected boolean turnInPlace = false;
	
	protected int offset = 0;
	
	protected Step previousAction;
	
	public Step() {}
	
	public Step(String name, PImage[] seq, boolean facingUp, boolean turnInPlace, int offset) {
		this.name = name;
		this.sequence = seq;
		this.facingUp = facingUp;
		this.turnInPlace = turnInPlace;
		this.offset = offset;
	}
	
	public Step(String name, PImage[] seq, boolean facingUp, boolean turnInPlace) { this(name, seq, facingUp, turnInPlace, 0); }
	
	public String getName() {
		return name;
	}
	
	public boolean isFacingUp() {
		return facingUp;
	}
	
	public void setPreviousAction(Step prev) {
		previousAction = prev;
	}
	
	public Step getPreviousAction() {
		return previousAction;
	}
	
	/**
	 * @return true if we're at the end of sequence
	 */
	public void drawFrame(PApplet parent, int last_note, int frame, int img_y) {
		int img_x = getImgX(last_note);
		//parent.println(img_x);
		
		if (facingUp) {
			img_x = img_x + 8 - sequence[0].width + offset;
		} else {
			img_x -= offset;
		}
		
		double y = ((double)img_x/692)*20 + img_y;
		
		
		if (frame < sequence.length)	// for normal 8 frame sequences
			parent.image(sequence[frame], img_x, (int) y);
		else 							// for single frame no_step
			parent.image(sequence[0], img_x, (int) y);
	
	}
	
	public int getImgX(int note) {
		int octave = note/12 - 1;
		int value = note%12;
		double noteX; 
		
		switch(value) {
			case 0:	// C
				noteX = 125;
				break;
			case 1:	// C#
				noteX = 132;
				break;
			case 2: // D
				noteX = 140;
				break;
			case 3: // Eb
				noteX = 148;
				break;
			case 4: // E
				noteX = 156;
				break;
			case 5:	// F
				noteX = 164;
				break;
			case 6:	// F#
				noteX = 172;
				break;
			case 7:	// G
				noteX = 180;
				break;
			case 8:	// G
				noteX = 188;
				break;
			case 9:	// A
				noteX = 195;
				break;
			case 10: // Bb
				noteX = 203;
				break;
			case 11: // B
				noteX = 211;
				break;	
			default:
				noteX = 0;
		}
		return (int) (noteX + (octave-1) * 92.3);
	}
	
	public PImage getLastFrame() {
		return sequence[sequence.length-1];
	}
	
}
