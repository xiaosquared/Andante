package boogie;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import processing.core.PApplet;
import rwmidi.MidiInput;
import rwmidi.MidiOutput;
import rwmidi.RWMidi;
import scales.ScaleAnimation;
import scales.ScalePlayer;

public class BoogieWoogie extends ScaleAnimation {
	
	//*** PLAYBACK ***//
	BoogiePlayer player;
	boolean isPaused = true;
	int target_frame_rate = 20;
	
	
	public void setup() {
		// basic setup
		size(1024, 535 + y_offset, P3D);
		frameRate(target_frame_rate);
		background(0);
		noStroke();
		tint(50, 50, 255, 255);	// make characters blue
		
		//properties
		y_offset = 210;
		img_y = 387;
		
		// initiate MIDI
		input = RWMidi.getInputDevices()[0].createInput(this);		
		output = RWMidi.getOutputDevices()[0].createOutput();
		println(RWMidi.getOutputDeviceNames());	// for debugging
		
		// init player
		BoogieStepManager.setup(this);
		player = new BoogiePlayer();
		player.init(this);
	}
	
	public void draw() {
		if (!isPaused)
			player.run(this);
	}
	
	// Right now key presses control the program
		public void keyPressed() {
			isPaused = !isPaused;
			if (isPaused)
				player.pause(this);
			println("pressed something");
		}
		
		public static void main(String[] args) {
			// places window on second screen automatically if there's additional display
		
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
		    PApplet.main(new String[] { location, BoogieWoogie.class.getName() });
		}

}
