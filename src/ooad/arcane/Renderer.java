package ooad.arcane;

import java.util.ArrayList;

// Renderer that is delegated information from the GameDriver to print out the dungeon on the console.
public class Renderer {
	
	// "Graphical" components of the renderer. Kept encapsulated from other classes.
	private String spaces = "                                    ";
	int num_spaces = spaces.length();
	private String divider_sr = "+------------------------------------+";
	private String sr_walls_d = "|                                    |";
	private String divider_floor = "+------------------------------------+------------------------------------+------------------------------------+";
	private String divider_walls = "|                                    |                                    |                                    |";
	
	// Render method renders first the start room, then each floor (Abstraction!)
	public void render (ArrayList<Floor> f, Room s) {
		// Render start room first.
		render_start_room (s);
		// Render each floor second.
		render_floors(f);
	}
	
	// Render start room. Much of this code is just formatting.
	public void render_start_room (Room s) {
		String sr_string = "";
		
		if (s.adventurers().size() == 0) {
			System.out.println();
			System.out.println("============= START ROOM =============");
			System.out.println(divider_sr);
			System.out.println(sr_walls_d);
			System.out.println("|" + spaces + "|");
			System.out.println(sr_walls_d);
			System.out.println(divider_sr);
			return;
		}
		
		for (int i = 0; i < s.adventurers().size(); i++) {
			sr_string += s.adventurers().get(i).type() + ",";
		}
		
		sr_string = sr_string.substring(0, sr_string.length() - 1);
		
		int half_spaces = (num_spaces - sr_string.length()) / 2;
		
		sr_string = spaces.substring(0, half_spaces) + sr_string + spaces.substring(0, half_spaces);
		
		if (sr_string.length() > num_spaces) {
			sr_string = sr_string.substring(0,num_spaces);
		} else if (sr_string.length() < num_spaces) {
			sr_string = sr_string + spaces;
			sr_string = sr_string.substring(0,num_spaces);
		}
		
		sr_string = "|" + sr_string + "|";
		
		System.out.println();
		System.out.println("============= START ROOM =============");
		System.out.println(divider_sr);
		System.out.println(sr_walls_d);
		System.out.println(sr_string);
		System.out.println(sr_walls_d);
		System.out.println(divider_sr);
	}
	
	// Render each floor. Much of this code is just formatting to output the correct looking string.
	public void render_floors (ArrayList<Floor> f) {
		int render_counter = 0;
		String temp_str = "";
		int half_spaces = 0;
		
		for (int i = 0; i < f.size(); i++) {
			
			System.out.println("\n============= " + f.get(i).element().toUpperCase() + " FLOOR =============");
			
			System.out.println(divider_floor);
			System.out.print(divider_walls + "\n|");
			
			render_counter = 0;
			for (int x = 0; x < GameDriver.floor_size; x++) {
				for (int y = 0; y < GameDriver.floor_size; y++) {
					render_counter++;
					int[] query_coords = {x, y};
					temp_str = "";
					
					Room temp_r = f.get(i).coord_to_room(query_coords);
					
					// Adventurers
					for (int j = 0; j < temp_r.adventurers().size(); j++) {
						temp_str += temp_r.adventurers().get(j).type() + ",";
					}
					
					if(temp_str.length() != 0) {
						temp_str = temp_str.substring(0, temp_str.length() - 1);
					}
					
					temp_str += " | ";
					
					for (int j = 0; j < temp_r.creatures().size(); j++) {
						temp_str += temp_r.creatures().get(j).get_type() + ",";
					}
					
					if(temp_str.length() > 3) {
						temp_str = temp_str.substring(0, temp_str.length() - 1);
					}
					
					half_spaces = (num_spaces - temp_str.length()) / 2;
					
					temp_str = spaces.substring(0, half_spaces) + temp_str + spaces.substring(0, half_spaces);
					
					if (temp_str.length() > num_spaces) {
						temp_str = temp_str.substring(0,num_spaces);
					} else if (temp_str.length() < num_spaces) {
						temp_str = temp_str + spaces;
						temp_str = temp_str.substring(0,num_spaces);
					}
					
					temp_str = temp_str + "|";
					
					System.out.print(temp_str);
					
					if ((render_counter % 3 == 0) && (render_counter != 9)) {
						System.out.println();
						System.out.println(divider_walls);
						System.out.println(divider_floor);
						System.out.print(divider_walls + "\n|");
					} else if ((render_counter % 3 == 0) && (render_counter == 9)) {
						System.out.println();
						System.out.println(divider_walls);
						System.out.println(divider_floor);
					}
				}
			}
		}
	}
}

// Rendering template.

// "                                    "

/*

+------------------------------------+
|                                    |
|                                    |
|                                    |
+------------------------------------+

+------------------------------------+------------------------------------+------------------------------------+ RC = 0
|                                    |                                    |                                    |
|                                    |                                    |                                    |
|                                    |                                    |                                    |
+------------------------------------+------------------------------------+------------------------------------+ RC = 3, AFTER
|                                    |                                    |                                    |
|                                    |                                    |                                    |
|                                    |                                    |                                    |
+------------------------------------+------------------------------------+------------------------------------+ RC = 6, AFTER
|                                    |                                    |                                    |
|                                    |                                    |                                    |
|                                    |                                    |                                    |
+------------------------------------+------------------------------------+------------------------------------+ RC = 9, AFTER


*/