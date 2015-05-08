package dalcroze;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.ControlWindow;
import controlP5.RadioButton;
import canon.Canon;
import canon.CanonPlayer;
import processing.core.PApplet;
import processing.core.PImage;
import rwmidi.MidiInput;
import rwmidi.MidiOutput;
import rwmidi.Note;
import rwmidi.RWMidi;

public class Allegro extends PApplet {
	private static final long serialVersionUID = 1L;
	
	int y_offset = 215;
	public int img_y = 385;
	
	//*** M I D I ***//
	private boolean USE_MIDI_INPUT = true;
	private boolean USE_MIDI_OUTPUT = true;
	public MidiInput input;
	public MidiOutput output;
	
	//** P L A Y B A C K ***//
	AllegroPlayer allegro_player;
	boolean isPaused = true;
	int target_frame_rate = 15;
	int last_millis = 0;
	
	AllegroBlockPlayer block_player;
	boolean figuresNotBlocks = true;
	
	//** Standing figure ***//
	PImage standing_still;
	boolean init;
	
	//* C O N T R O L L E R *..
	ControlP5 cp5;
	ControlWindow teacherGUI;
	RadioButton voicesButton, measureButton, lengthButton, modeButton;
	int voicesMode = 3;
	
	public void setup() {
		size(1024, 535 + y_offset, P3D);
		frameRate(target_frame_rate);
		background(0);
		noStroke();
		fill(0, 0, 0, 50);
		
		AllegroStepManager.setup(this);
		allegro_player = new AllegroPlayer();
		
		block_player = new AllegroBlockPlayer();
		
		if (USE_MIDI_INPUT)
			input = RWMidi.getInputDevices()[0].createInput(this);
		if (USE_MIDI_OUTPUT)
			output = RWMidi.getOutputDevices()[0].createOutput();
		println(RWMidi.getOutputDeviceNames());
		
		standing_still = loadImage("data/_misc/standing.png");
		standing_still.resize(0, 85);
		
		setupGUI();
	}
	
	private void setupGUI() {
		cp5 = new ControlP5(this);
		teacherGUI = cp5.addControlWindow("TeacherGUI", 0, 0, 800, 600).setBackground(20);
		voicesButton = cp5.addRadioButton("voicesButton").moveTo(teacherGUI)
				.setPosition(200, 160)
				.setSize(40,20)
				.setColorForeground(color(120))
				.setColorActive(color(255))
				.setColorLabel(color(255))
				.setItemsPerRow(3)
				.setSpacingColumn(80)
				.addItem("RH", 1)
				.addItem("LH", 2)
				.addItem("Both", 3);
		
		measureButton = cp5.addRadioButton("measureButton").moveTo(teacherGUI)
				.setPosition(200, 260)
				.setSize(40,20)
				.setColorForeground(color(120))
				.setColorActive(color(255))
				.setColorLabel(color(255))
				.setItemsPerRow(5)
				.setSpacingColumn(80)
				.addItem("m1", 0)
				.addItem("m2", 2)
				.addItem("m3", 4)
				.addItem("m4", 6);
		
		lengthButton = cp5.addRadioButton("lengthButton").moveTo(teacherGUI)
				.setPosition(200, 360)
				.setSize(40,20)
				.setColorForeground(color(120))
				.setColorActive(color(255))
				.setColorLabel(color(255))
				.setItemsPerRow(4)
				.setSpacingColumn(80)
				.addItem("2", 2)
				.addItem("3", 4)
				.addItem("4", 8)
				.addItem("5", 12);
		
		modeButton = cp5.addRadioButton("modeButton").moveTo(teacherGUI)
				.setPosition(200, 460)
				.setSize(40,20)
				.setColorForeground(color(120))
				.setColorActive(color(255))
				.setColorLabel(color(255))
				.setItemsPerRow(3)
				.setSpacingColumn(80)
				.addItem("andante", 0)
				.addItem("blocks", 1);
	}
	
	/**
	 * Delegates to the Player class to do the heavy lifting
	 */
	public void draw() {
		if (!isPaused) {
			if (figuresNotBlocks)
				allegro_player.run(this);
			else
				block_player.run(this);
		}
	}

	public void keyPressed() {
		if (keyCode == 32)
			isPaused = !isPaused;
		
		if (isPaused) {
			allegro_player.pause(this);
			allegro_player.resetMeasures(this);
			block_player.resetMeasures(this);
		}
	}
	
	public void mousePressed() {
		System.out.println(mouseX + ", " + mouseY);
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
	
	public void controlEvent(ControlEvent e) {
		  println(e.getGroup().getName());
		  println(e.getGroup().getValue());
		  
		  int value = (int) e.getGroup().getValue();
		  
		  if (e.isFrom(voicesButton)) {						// set which voice is on
			switch(value) {
				case 1:
					voicesMode = 1;
					println("one");
					break;
				case 2:
					voicesMode = 2;
					println("two");
					break;
				case 3:
					voicesMode = 3;
					println("three");
					break;
			}
		  }
		  
		  else if (e.isFrom(measureButton)) {
			  if (value == -1)
				  return;
			  //background(0);			  
			  allegro_player.goToMeasure(value);
			  block_player.start_measure = value;
		  }
		  
		  else if (e.isFrom(lengthButton)) {
			  allegro_player.setSegmentLength(value);
			  block_player.measures_to_play = value -1;
		  }
		  
		  else if (e.isFrom(modeButton)) {
			  if (value == 0)
				  figuresNotBlocks = true;
			  else
				  figuresNotBlocks = false;
		  }
		  
		 allegro_player.goToMeasure(allegro_player.segment_measure_start);
		 // score.draw();
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
	    PApplet.main(new String[] { location, Allegro.class.getName() });
	}
}
