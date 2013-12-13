package anim;

import canon.Canon;
import processing.core.PImage;

public class NoStep extends Step {
	
	private static PImage img;
	String name = "no step";
	
	public NoStep(PImage img) {
		this.img = img;
		this.sequence = new PImage[1];
		sequence[0] = img;
	}
		
	@Override
	public void setPreviousAction(Step prev) {
		if (prev instanceof NoStep)
			return;
		else
			super.setPreviousAction(prev);
	}
	
	public void update(PImage img, boolean faceUp, int offset) {
		super.facingUp = faceUp;
		super.offset = offset;
		this.img = img;
		sequence[0] = img;
	}
}
