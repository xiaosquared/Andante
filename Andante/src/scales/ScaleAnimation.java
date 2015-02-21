package scales;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import canon.Canon;
import processing.core.PApplet;
import rwmidi.MidiInput;
import rwmidi.MidiOutput;
import rwmidi.RWMidi;

public class ScaleAnimation extends PApplet {

	protected int y_offset = 215;		// adjustment for projection onto keys
	public int img_y = 380;
	
	//*** MIDI ***//
	public MidiInput input;
	public MidiOutput output;
	
	//*** PLAYBACK ***//
	ScalePlayer player;
	boolean isPaused = true;
	protected int target_frame_rate = 10;
	
	ScaleStepManager normal_steps;
	ScaleStepManagerOstrich ostrich_steps;
	ScaleStepManagerFat fat_steps;
	ScaleStepManagerSneak sneak_steps;
	
	public void setup() {
		// basic setup of sketch
		size(1024, 535 + y_offset, P3D);
		background(0);
		frameRate(target_frame_rate);
		noStroke();
		
		// initiate MIDI
		input = RWMidi.getInputDevices()[0].createInput(this);		
		output = RWMidi.getOutputDevices()[0].createOutput();
		println(RWMidi.getOutputDeviceNames());	// for debugging
		
		// init scale player
		normal_steps = new ScaleStepManager();
		normal_steps.initSteps(this);
		ostrich_steps = new ScaleStepManagerOstrich();
		ostrich_steps.initSteps(this);
		fat_steps = new ScaleStepManagerFat();
		fat_steps.initSteps(this);
		sneak_steps = new ScaleStepManagerSneak();
		sneak_steps.initSteps(this);
		
		player = new ScalePlayer();
		player.init(this);;
		
	}
	
	
	public void draw() {
		if (!isPaused)
			player.run(this);
	}
	
	public int getFrameRate() {
		return target_frame_rate;
	}
	
	// Right now key presses control the program
	public void keyPressed() {
		if (keyCode == 32) {
			isPaused = !isPaused;
			if (isPaused)
				player.pause(this);
		} else if (key == '1')
			player.character = 1;
		else if (key == '2')
			player.character = 2;
		else if (key == '3')
			player.character = 3;
		else if (key == '4')
			player.character = 4;
		else if (key == 't') {
			player.thirds = !player.thirds;
			if (player.thirds)
				player.contrary = false;
		} else if (key == 'c') {
			player.contrary = !player.contrary;
			if (player.contrary)
				player.thirds = false;
		}
		else if (key == 'f')
			frameRate(20);
		else if (key == 's')
			frameRate(10);
		
		println("pressed " + key);
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
	    PApplet.main(new String[] { location, ScaleAnimation.class.getName() });
	}
}
