package test;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import processing.core.PApplet;
import processing.core.PImage;
import rwmidi.MidiInput;
import rwmidi.MidiOutput;
import rwmidi.RWMidi;


public class Pentatonic extends PApplet {
	private static final long serialVersionUID = 1L;
	
	int y_offset = 215;		// adjustment for projection onto keys
	int x_offset = 0;
	
	//*** M I D I ***//
	private boolean USE_MIDI_INPUT = true;
	private boolean USE_MIDI_OUTPUT = true;
	public MidiInput input;
	public MidiOutput output;
	
	//*** A N I M A T I O N ***//
	Animation anim = new Animation(this);
	boolean isPaused = false;
	
	//int img_y = 385;
	int img_y = 376; 
	
	//*** N O T E S ***//
	Note note1 = new Note(NoteType.C_SHARP, 4);
	Note note2 = new Note(NoteType.D_SHARP, 4);
	
	boolean noteOn;
	
	
	//***************//
	//** S E T U P **//
	//***************//
	public void setup() {
		size(1024, 535 + y_offset);
		background(0);
		frameRate(10);
		
		if (USE_MIDI_INPUT)
			input = RWMidi.getInputDevices()[0].createInput(this);
		if (USE_MIDI_OUTPUT)
			output = RWMidi.getOutputDevices()[0].createOutput();
	
		println("DONE SETUP");
	}
	
	private void loadImages(PImage[] images, String prefix) {
		for (int i = 0; i < images.length; i++) {
			String filename = prefix + i + ".jpg";
			images[i] = loadImage(filename);
			images[i].resize(0, 89);
		}	
	}
	
	public void draw() {
		if (isPaused)
			return;
		anim.drawFrame(this, img_y);
	}
	
	public void keyPressed() {
//		println("test note output");
//		output.sendNoteOn(0, 50, 20);
//		delay(500);
//		output.sendNoteOff(0, 50, 20);
//		
		if (key == CODED) {
			if (keyCode == UP)
				img_y -=5;
			else if (keyCode == DOWN)
				img_y +=5;
			
			println("y: " + img_y);
		} else if (key == 32)
			isPaused = !isPaused;
	}
	
	public void mousePressed() {
		println(mouseX + ", " + mouseY);
	}
	
	
	public static void main(String[] args) {
		/* 
		 * places window on second screen automatically if there's additional display
		 * 
		 */
		int primary_width;
		int screen_y = 0;
		
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice devices[] = environment.getScreenDevices();
		String location;
		if (devices.length > 1) {
			primary_width = devices[0].getDisplayMode().getWidth();
			location = "--location=" +primary_width+ "," + screen_y;
		} else {
			location="--location=0,0";
		}
	    PApplet.main(new String[] { location, Pentatonic.class.getName() });
	}
}