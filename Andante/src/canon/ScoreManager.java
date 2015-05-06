package canon;

public class ScoreManager {
	Canon parent;
	
	int v1_start = 0;
	int v2_start = 0;
	int length = 5;
	
	int start_x = 405;
	int measure_width = 20;
	int v1_y = 165;
	int v2_y = 153;
	int voice_height = 10; 
	
	public ScoreManager(Canon parent) {
		this.parent = parent;
	}
	
	public void draw() {
		//clear background
		parent.fill(0);
		parent.rect(359, 104, 300, 200);
		
		drawScorePlacement();
		drawVoices();
	}
	
	public void drawScorePlacement() {
		parent.stroke(255);
		parent.line(395, 115, 522, 115);
		parent.line(522, 115, 525, 249);
		parent.line(525, 249, 384, 250);
		parent.line(384, 250, 395, 115);
		parent.noStroke();
	}
	
	public void drawVoices() {
		// voice 1
		if (v1_start != -1) {
			parent.fill(125, 255, 255, 90);
			parent.rect(start_x + measure_width * v1_start, v1_y, measure_width * length, voice_height);
		}
		
		// voice 2
		if (v2_start != -1) {
			parent.fill(255, 255, 125, 90); 
			parent.rect(start_x + measure_width * v2_start, v2_y, measure_width * length, voice_height);
		}
	}
	
	public void setMeasures(int start, boolean v1, boolean v2, int length) {
//		parent.println("SET! " + start);
//		parent.println("v1 " + v1);
//		parent.println("v2 " + v2);
//		parent.println("length " + length);
		
		if (v1)
			v1_start = start;
		else 
			v1_start = -1;
			
		if (v2)			
			v2_start = start + 1;
		else
			v2_start = -1;
		
		this.length = length;
	}
}
