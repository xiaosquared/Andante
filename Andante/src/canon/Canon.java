package canon;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Iterator;
import processing.opengl.PGraphicsOpenGL;

import anim.CanonStepManager;
import processing.core.PApplet;
import processing.core.PImage;
import rwmidi.MidiInput;
import rwmidi.MidiOutput;
import rwmidi.Note;
import rwmidi.RWMidi;
import test.Pentatonic;

public class Canon extends PApplet{  
	private static final long serialVersionUID = 1L;
	
	int y_offset = 215;		// adjustment for projection onto keys
	public int img_y = 385; 
	
	//*** M I D I ***//
	private boolean USE_MIDI_INPUT = true;
	private boolean USE_MIDI_OUTPUT = true;
	public MidiInput input;
	public MidiOutput output;
	
	//** P L A Y B A C K ***//
	Player canon_player;
	boolean isPaused = true;
	int target_frame_rate = 20;
	int last_millis = 0;
	
	boolean one = true;
	boolean two = false;
	boolean three = false;
	boolean four = false;
	
	boolean trails = true;
	
	//** Standing figure ***//
	PImage standing_still;
	boolean init;
	
	public void setup() {
		size(1024, 535 + y_offset, P3D);
		//size(1024, 300);
		//size(50, 50);
		frameRate(target_frame_rate);
		background(0);
		noStroke();
		fill(0, 0, 0, 50);
		
		CanonStepManager.setup(this);
		canon_player = new Player();
		
		if (USE_MIDI_INPUT)
			input = RWMidi.getInputDevices()[0].createInput(this);
		if (USE_MIDI_OUTPUT)
			output = RWMidi.getOutputDevices()[0].createOutput();
		println(RWMidi.getOutputDeviceNames());
		
		standing_still = loadImage("data/_misc/standing.png");
		standing_still.resize(0, 85);
		//image(standing_still, 125, 50);
		//canon_player.initFigure(this);
		
		
	}
	
	public void draw() {
		if (!isPaused)
			canon_player.run(this);
	}
	
	public void noteOnReceived(Note n) {
		if (n.getPitch() == 48)
			println("START!");
		println("note on " + n.getPitch() + ", " + n.getVelocity());
	}
	
	public void noteOffReceived(Note n) {
		if (n.getPitch() == 48)
			println("really start!");
	}
	
	public void keyPressed() {
		if (key == '1')
			one = !one;
		else if (key == '2')
			two = !two;
		else if (key == '3')
			three = !three;
		else if (key == '4')
			four = !four;
		else if (key == 't')
			trails = !trails;
		else if (keyCode == 32)
			isPaused = !isPaused;
			
		if (isPaused) {
			canon_player.pause(this);
		}
	}
	
	public void mousePressed() {
		System.out.println(mouseX + ", " + mouseY);
		
		background(0);
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
	    PApplet.main(new String[] { location, Canon.class.getName() });
	}
	
}
