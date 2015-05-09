package dalcroze;

public class AllegroHarmony {
	
	static int base_y = 471;
	static int column_width = 5;
	static int column_height = -80;
	static int fade_in = 0;
	
	public static void drawHarmony(Allegro parent, int chord) {
		if (chord == 1) {
			parent.fill(255, 0, 0, parent.min(255, fade_in * 20));
			parent.rect(311, base_y, column_width, column_height);
			parent.rect(343, base_y, column_width, column_height);
			parent.rect(365, base_y, column_width, column_height);
			parent.rect(308, base_y + column_height, 65, -10);
		}
		
		else if (chord == 4) {
			parent.fill(0, 255, 0, parent.min(255, fade_in * 20));
			parent.rect(311, base_y, column_width, column_height);
			parent.rect(351, base_y, column_width, column_height);
			parent.rect(381, base_y, column_width, column_height);
			parent.arc(348, base_y + column_height, 80, 40, parent.PI, parent.TWO_PI);
		}
		
		else if (chord == 5) {
			parent.fill(0, 100, 0, parent.min(255, fade_in * 20));
			parent.rect(303, base_y, column_width, column_height);
			parent.rect(351, base_y, column_width, column_height);
			parent.rect(365, base_y, column_width, column_height);
			parent.triangle(301, 392, 338, 366, 372, 392);
		}
		fade_in ++;
	}
}
