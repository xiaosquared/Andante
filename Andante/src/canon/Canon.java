package canon;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Iterator;

import controlP5.*;
import processing.opengl.PGraphicsOpenGL;
import anim.CanonStepManager;
import processing.core.PApplet;
import processing.core.PFont;
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

	int canonMode = 3;	// when two voices are on
	int startMeasure = 0;
	int measures_to_play = 5;
	
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
		teacherGUI = cp5.addControlWindow("TeacherGUI", 0, 0, 1440, 1050).setBackground(20);
		voicesButton = cp5.addRadioButton("voicesButton").moveTo(teacherGUI)
				.setPosition(200, 160)
				.setSize(40,20)
				.setColorForeground(color(120))
				.setColorActive(color(255))
				.setColorLabel(color(255))
				.setItemsPerRow(3)
				.setSpacingColumn(80)
				.addItem("LEFT HAND", 1)
				.addItem("RIGHT HAND", 2)
				.addItem("BOTH HANDS", 3);
		
		measureButton = cp5.addRadioButton("measureButton").moveTo(teacherGUI)
				.setPosition(200, 260)
				.setSize(40,20)
				.setColorForeground(color(120))
				.setColorActive(color(255))
				.setColorLabel(color(255))
				.setItemsPerRow(5)
				.setSpacingColumn(80)
				.addItem("PART A", 0)
				.addItem("PART B", 1)
				.addItem("Whole Piece: A + B", 2);
		
		modeButton = cp5.addRadioButton("modeButton").moveTo(teacherGUI)
				.setPosition(1100, 800)
				.setSize(40,20)
				.setColorForeground(color(55))
				.setColorActive(color(25))
				.setColorLabel(color(55))
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
		  
		  if (e.isFrom(measureButton)) {
			  if (value == -1)
				  return;
			  if (value == 0) {
				  startMeasure = 0;
				  measures_to_play = 2;
			  } else if (value == 1) {
				  startMeasure = 2;
				  measures_to_play = 2;
			  } else if (value == 2) {
				  startMeasure = 0;
				  measures_to_play = 4;
			  }
		  }
		  
		  if (e.isFrom(voicesButton)) {						// set which voice is on
				switch(value) {
					case 1:
						one = true;
						two = false;
						canonMode = 1;
						break;
					case 2:
						one = false;
						two = true;
						canonMode = 2;
						break;
					case 3:
						canonMode = 3;
						break;
				}
		  }
		  
		  else if (e.isFrom(modeButton)) {
			  if (value == 0)
				  figuresNotBlocks = true;
			  else
				  figuresNotBlocks = false;
		  }
		  
		  if (canonMode == 3) {
			  one = true;
			  two = true;
		  }
		  
		  block_player.start_measure = startMeasure;
		  block_player.measures_to_play = measures_to_play;
		  canon_player.segment_measure_start = startMeasure;
		  canon_player.setSegmentLength(this, measures_to_play);
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
