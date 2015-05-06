package canon;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import processing.core.PApplet;
import rwmidi.MidiInput;
import rwmidi.MidiOutput;
import rwmidi.RWMidi;

public class Blocks extends Canon {

	int y_offset = 215;		// adjustment for projection onto keys
	boolean isPaused = false;
	
	public MidiInput input;
	public MidiOutput output;
	
	BlockPlayer player;
	
	public void setup() {
		size(1024, 535 + y_offset, P3D);
		background(0);
		stroke(255);
		
		player = new BlockPlayer();
		
		input = RWMidi.getInputDevices()[0].createInput(this);
		output = RWMidi.getOutputDevices()[0].createOutput();
		println(RWMidi.getOutputDeviceNames());
		
//		notes = new int[3];
//		notes[0] = 60;
//		notes[1] = 64;
//		notes[2] = 67;
	}
	
	public void draw() {
		if (!isPaused)
			player.run(this);
	
	}
	
	public void mousePressed() {
		println(mouseX + ", " + mouseY);
	}
	
	public void keyPressed() {
		isPaused = !isPaused;
		
		if (isPaused) {
			for (int i = 36; i < 76; i++) {
				output.sendNoteOff(0, i, 40);
				output.sendNoteOff(0, i, 40);
				output.sendNoteOff(0, i, 40);
				output.sendNoteOff(0, i, 40);
			}
		}
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
	    PApplet.main(new String[] { location, Blocks.class.getName() });
	}
}
