package chromatic;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import processing.core.PApplet;
import boogie.BoogieWoogie;
import rwmidi.RWMidi;
import scales.ScaleAnimation;

public class Chromatic extends ScaleAnimation {
	
	ChromaticPlayer player;
	boolean isPaused = true;
	int target_frame_rate = 12;
	
	public void setup() {
		// basic setup
		size(1024, 535 + y_offset, P3D);
		frameRate(target_frame_rate);
		colorMode(HSB);
		background(0);
		fill(0, 0, 0, 50);
		noStroke();
		//tint(50  , 50, 255, 255);	// make characters blue
		
		//properties
		y_offset = 213;
		img_y = 387;
		
		// initiate MIDI
		input = RWMidi.getInputDevices()[0].createInput(this);		
		output = RWMidi.getOutputDevices()[0].createOutput();
		println(RWMidi.getOutputDeviceNames());	// for debugging
		
		// init player
		ChromaticStepManager.setup(this);
		player = new ChromaticPlayer();
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
		PApplet.main(new String[] { location, Chromatic.class.getName() });
	}

}
