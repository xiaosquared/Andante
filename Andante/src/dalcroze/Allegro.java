package dalcroze;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.ControlWindow;
import controlP5.RadioButton;
import canon.Canon;
import canon.CanonPlayer;
import canon.ScoreManager;
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
	int start_measure = 0;
	int measures_to_play = 2;
	
	// S C O R E
	AllegroScoreManager score;
	
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
		
		score = new AllegroScoreManager(this);
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
				.addItem("RIGHT HAND", 1)
				.addItem("LEFT HAND", 2)
				.addItem("Both", 3);
		
		measureButton = cp5.addRadioButton("measureButton").moveTo(teacherGUI)
				.setPosition(200, 240)
				.setSize(40,20)
				.setColorForeground(color(120))
				.setColorActive(color(255))
				.setColorLabel(color(255))
				.setItemsPerRow(4)
				.setSpacingColumn(80)
				.addItem("A1", 0)
				.addItem("A2", 2)
				.addItem("B1", 4)
				.addItem("B2", 6)
				.addItem("A all", 7)
				.addItem("B all", 8)
				.addItem("A & B all", 9);
		
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
		score.draw();
	}

	public void keyPressed() {
		if (keyCode == 32) {
			isPaused = !isPaused;
			allegro_player.goToMeasure(allegro_player.segment_measure_start);

		}
			
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
			  if (value < 7) {
				  measures_to_play = 2;
				  start_measure = value;
			  } else if ((value == 7) || (value == 8)) {
				  measures_to_play = 4;
				  if (value == 7) {
					  start_measure = 0;
				  } else {
					  start_measure = 4;					  
				  }
			  } else if (value == 9) {
				  measures_to_play = 8;
				  start_measure = 0;
			  }
		  }
		  else if (e.isFrom(modeButton)) {
			  if (value == 0)
				  figuresNotBlocks = true;
			  else
				  figuresNotBlocks = false;
		  }

		  
		  block_player.measures_to_play = measures_to_play;
		  block_player.start_measure = start_measure; 
		  
		  allegro_player.setSegmentLength(measures_to_play);
		  allegro_player.segment_measure_start = start_measure;
		  allegro_player.goToMeasure(allegro_player.segment_measure_start);

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
	    PApplet.main(new String[] { location, Allegro.class.getName() });
	}
}
