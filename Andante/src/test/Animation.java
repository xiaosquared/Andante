package test;
import processing.core.PApplet;
import processing.core.PImage;


public class Animation {
	private Step four_step_right, four_step_left;
	private Step three_step_right, three_step_left;
	private Step turn_left1, turn_left2;
	private Step turn_right1, turn_right2;
	private Step currentStep;
	
	public Animation(PApplet parent) {
		
		three_step_right = new Step(new Note(NoteType.D_SHARP, 1), 
				new Note(NoteType.G_SHARP, 1), 
				loadSequence(parent, new PImage[8], "data/3step_right/3sr_"), true);
		
		three_step_left = new Step(new Note(NoteType.A_SHARP, 7), 
				new Note(NoteType.F_SHARP, 7), 
				loadSequence(parent, new PImage[8], "data/3step_left/3sl_"), false);
		
		four_step_right = new Step(new Note(NoteType.A_SHARP, 1), 
				new Note(NoteType.D_SHARP, 2), 
				loadSequence(parent, new PImage[8], "data/4step_right/4sr_"), true);
		
		four_step_left = new Step(new Note(NoteType.D_SHARP, 7), 
				new Note(NoteType.A_SHARP, 6), 
				loadSequence(parent, new PImage[8], "data/4step_left/4sl_"), false);
		
		turn_left1 = new Step(new Note(NoteType.F_SHARP, 4), 
				new Note(NoteType.A_SHARP, 5), 
				loadSequence(parent, new PImage[8], "data/turn_left/tl1_"), true);
		
		turn_left2 = new Step(new Note(NoteType.A_SHARP, 5), 
				new Note(NoteType.F_SHARP, 4), 
				loadSequence(parent, new PImage[8], "data/turn_left/tl2_"), false);
		
		turn_right1 = new Step(new Note(NoteType.F_SHARP, 4), 
				new Note(NoteType.A_SHARP, 5), 
				loadSequence(parent, new PImage[8], "data/turn_right/tr1_"), false);
		
		turn_right2 = new Step(new Note(NoteType.A_SHARP, 5), 
				new Note(NoteType.F_SHARP, 4), 
				loadSequence(parent, new PImage[8], "data/turn_right/tr2_"), true);
		
		currentStep = three_step_left;
	}
	
	public void drawFrame(Pentatonic parent, int y) {
		if(currentStep.drawFrame(parent, y)) {
			getNextStep();
		}
	}
	
	private void getNextStep() {
		
		// if we're in the middle of a turn, finish it
		if (currentStep == turn_left1) {
			turn_left2.setStartNote(currentStep.getEndNote());
			turn_left2.setEndNote(currentStep.getStartNote());
			turn_left2.setHackX(currentStep.getX());
			currentStep = turn_left2; 
			return; 
		} 
		if (currentStep == turn_right1) {
			turn_right2.setStartNote(currentStep.getEndNote());
			turn_right2.setEndNote(currentStep.getStartNote());
			turn_right2.setHackX(currentStep.getX());
			currentStep = turn_right2; return; 
		}
		
		Note n = currentStep.getEndNote();
		Note n2;
		
		// if we're going up
		if (currentStep.goingUp) {
	
			// if we're on F#, special case
			if (n.type == NoteType.F_SHARP) {
				//if ((n.octave == 7) || (Math.random() < .5))
				if (n.octave == 7)
					currentStep = turn_left1;
				else
					currentStep = three_step_right;
			} 
			
			// by default
			else
				currentStep = four_step_right;
			
			n2 = n.oneNoteUp();
		}
			
		// if we're going down
		else {
		
			// if we're on A# special case
			if (n.type == NoteType.A_SHARP) {
				//if ((n.octave <= 2) || (Math.random() < .5)) 
				if (n.octave <= 2)
					currentStep = turn_right1;
				else 
					currentStep = three_step_left;
			}
			
			// default
			else
				currentStep = four_step_left;
			
			n2 = n.oneNoteDown();
		}

		currentStep.setStartNote(n);
		currentStep.setEndNote(n2);
	}
	
	private PImage[] loadSequence(PApplet parent, PImage[] images, String prefix) {
		for (int i = 0; i < images.length; i++) {
			String filename = prefix + i + ".jpg";
			images[i] = parent.loadImage(filename);
			images[i].resize(0, 89);
		}
		return images;
	} 
}
