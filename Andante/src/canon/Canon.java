package canon;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Iterator;

import controlP5.*;
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
	protected boolean USE_MIDI_INPUT = true;
	protected boolean USE_MIDI_OUTPUT = true;
	public MidiInput input;
	public MidiOutput output;
	
	//** P L A Y B A C K ***//
	// ANDANTE
	CanonPlayer canon_player;
	boolean isPaused = true;
	int target_frame_rate = 15;
	int last_millis = 0;
	
	boolean one = true;
	boolean two = false;
	boolean three = false;
	boolean four = false;
	
	int canonMode = 3;	// when two voices are on
	
	boolean trails = true;

	// BLOCK MODE
	BlockPlayer block_player;
	boolean figuresNotBlocks = true;
	
	//** Standing figure ***//
	PImage standing_still;
	boolean init;

	//*** C O N T R O L L E R ***//
	ControlP5 cp5;
	ControlWindow teacherGUI;
	RadioButton voicesButton, measureButton, lengthButton, modeButton;
	
	//score
	ScoreManager score;
	
	public void setup() {
		size(1024, 535 + y_offset, P3D);
		//size(1024, 300);
		//size(50, 50);
		frameRate(target_frame_rate);
		background(0);
		noStroke();
		fill(0, 0, 0, 50);
		
		CanonStepManager.setup(this);
		canon_player = new CanonPlayer();
		
		block_player = new BlockPlayer();
		
		if (USE_MIDI_INPUT)
			input = RWMidi.getInputDevices()[0].createInput(this);
		if (USE_MIDI_OUTPUT)
			output = RWMidi.getOutputDevices()[0].createOutput();
		println(RWMidi.getOutputDeviceNames());
		
		standing_still = loadImage("data/_misc/standing.png");
		standing_still.resize(0, 85);
		//image(standing_still, 125, 50);
		//canon_player.initFigure(this);
		
		setupGUI(); // makes the controller interface
		
		score = new ScoreManager(this);
		score.draw();				
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
				.addItem("low voice", 1)
				.addItem("high voice", 2)
				.addItem("both voices", 3);
		
		measureButton = cp5.addRadioButton("measureButton").moveTo(teacherGUI)
				.setPosition(200, 260)
				.setSize(40,20)
				.setColorForeground(color(120))
				.setColorActive(color(255))
				.setColorLabel(color(255))
				.setItemsPerRow(5)
				.setSpacingColumn(80)
				.addItem("m1", 0)
				.addItem("m2", 1)
				.addItem("m3", 2)
				.addItem("m4", 3)
				.addItem("m5", 4);
		
		lengthButton = cp5.addRadioButton("lengthButton").moveTo(teacherGUI)
				.setPosition(200, 360)
				.setSize(40,20)
				.setColorForeground(color(120))
				.setColorActive(color(255))
				.setColorLabel(color(255))
				.setItemsPerRow(4)
				.setSpacingColumn(80)
				.addItem("2", 2)
				.addItem("3", 3)
				.addItem("4", 4)
				.addItem("5", 5);
		
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
	
	public void draw() {
		if (!isPaused) {
			if (figuresNotBlocks)
				canon_player.run(this);
			else
				block_player.run(this);
		}
		score.draw();
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
		System.out.println("key pressed: " + key);
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
		else if (key == 'f') {
			target_frame_rate = 20;
			frameRate(20);
		}
		else if (key == 's') {
			target_frame_rate = 12;
			frameRate(12f);
		}
		else if (keyCode == 32) {
			isPaused = !isPaused;
			canon_player.goToMeasure(this, canon_player.segment_measure_start);
			background(0);
			score.draw();
		}
		if (isPaused) {
			canon_player.pause(this);
			canon_player.resetMeasures(this);
			block_player.resetMeasures(this);
		}
	}
	
	public void mousePressed() {
		System.out.println(mouseX + ", " + mouseY);
	
		background(0);
	}
	
	  public void controlEvent(ControlEvent e) {
		  println(e.getGroup().getName());
		  println(e.getGroup().getValue());
		  
		  int value = (int) e.getGroup().getValue();
		  
		  if (e.isFrom(voicesButton)) {						// set which voice is on
			switch(value) {
				case 1:
					one = true;
					two = false;
					canonMode = 1;
					println("one");
					break;
				case 2:
					one = false;
					two = true;
					canonMode = 2;
					println("two");
					break;
				case 3:
					one = true;
					two = true;
					canonMode = 3;
					println("three");
					break;
			}
		  }
		  
		  else if (e.isFrom(measureButton)) {
			  if (value == -1)
				  return;
			  //background(0);			  
			  canon_player.goToMeasure(this, value);
			  block_player.start_measure = value;
		  }
		  
		  else if (e.isFrom(lengthButton)) {
			  canon_player.setSegmentLength(this, value);
			  block_player.measures_to_play = value -1;
		  }
		  
		  else if (e.isFrom(modeButton)) {
			  if (value == 0)
				  figuresNotBlocks = true;
			  else
				  figuresNotBlocks = false;
		  }
		  
		  canon_player.goToMeasure(this, canon_player.segment_measure_start);
		  score.draw();
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
