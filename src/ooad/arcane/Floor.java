package ooad.arcane;

import java.util.ArrayList;

// Floor is mostly a aggregation of Room objects, with specific functions to modify the various rooms in the class.
public class Floor {
	// Each floor has an element and a room map (these are both private as per the concept of encapsulation)
	private String element;
	private Room[][] map;
	
	// Constructor. All it takes is the element of the floor.
	public Floor (String e) {
		element = e;
		map = new Room[GameDriver.floor_size][GameDriver.floor_size];
	}
	
	// Room generation is its own method. It creates a GameDriver.floor_size ^ 2 map with a Room object in each class.
	// The room object is simply used to store the updated status of monsters and adventurers on the floor.
	// The floor layout each floor looks like so:
	/*
	 * Room[x, y]
	 * 
	 * +-------+-------+-------+
	 * | (0,0) | (1,0) | (2,0) | 
	 * +-------+-------+-------+
	 * | (0,1) | (1,1) | (2,1) | 
	 * +-------+-------+-------+
	 * | (0,2) | (1,2) | (2,2) | 
	 * +-------+-------+-------+
	 */
	public void generate_rooms () {
		for (int i = 0; i < GameDriver.floor_size; i++) {
			for (int j = 0; j < GameDriver.floor_size; j++) {
				
				int[] temp_coords = {i, j};
				Room temp_room = new Room(element, temp_coords);
				
				map[i][j] = temp_room;
			}
		}
	}
	
	// Getter method for the element of the floor.
	public String element () {
		return element;
	}
	
	// Spawn method for initial monster placement within the floors. The boolean flag indicates whether the spawn should avoid the 
	// center room, and is true when it comes to spawning Fireborns. Aside from that, a random room within the floor is selected.
	public Room assign_random_room (boolean avoid_center, Creature c) {
		int x = GameDriver.rd.nextInt(GameDriver.floor_size);
		int y = GameDriver.rd.nextInt(GameDriver.floor_size);
		
		if (avoid_center) {
			while (x == GameDriver.center_coordinates[0] && y == GameDriver.center_coordinates[1]) {
				x = GameDriver.rd.nextInt(GameDriver.floor_size);
				y = GameDriver.rd.nextInt(GameDriver.floor_size);
			}
		}
		
		map[x][y].add_creature(c);
		
		return map[x][y];
	}
	
	// Place and remove methods for the adventurers. Encapsulation. No private variables are directly modified. All are through
	// get and set methods.
	
	// Place method for adventurers. Puts them in a room on the floor.
	public void place_adventurer (Room r, Adventurer a) {
		int[] coords = r.coordinates();
		map[coords[0]][coords[1]].add_adventurer(a);
	}
	
	// Remove method for adventurers. Removes them from a room.
	public void remove_adventurer (Room r, Adventurer a) {
		int[] coords = r.coordinates();
		map[coords[0]][coords[1]].remove_adventurer(a);
	}
	
	// Place method for creatures. Puts them in a room on the floor.
	public void place_creature (Creature c, Room r) {
		map[r.coordinates()[0]][r.coordinates()[1]].add_creature(c);
	}
	
	// Remove method for creatures. Removes them from a room.
	public void remove_creature (Creature c, Room r) {
		map[r.coordinates()[0]][r.coordinates()[1]].remove_creature(c);
	}
	
	// Finds the room specified (via coordinates) and returns that room (used for updating monster and adventurer current room data).
	public Room return_room_data (Room r) {
		int[] coords = r.coordinates();
		return map[coords[0]][coords[1]];
	}
	
	// Checks each room in a floor to see if it has a player. If it does, return. This is used for monster movement logic.
	public boolean has_player () {
		for (int i = 0; i < GameDriver.floor_size; i++) {
			for (int j = 0; j < GameDriver.floor_size; j++) {
				if (map[i][j].adventurers().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Function that takes a room, locates it within the floor, and returns all adjacent rooms. Used in the move methods in other classes.
	public ArrayList<Room> return_adj_rooms (Room r) {
		ArrayList<Room> return_list = new ArrayList<Room>();
		int[] coords = r.coordinates();
		
		int x = coords[0];
		int y = coords[1];
		
		// Checks to see if the room is out of bounds first, if so, don't add it to the return list.
		if (x-1 >= 0) {
			return_list.add(map[x-1][y]);
		}
		
		if (x+1 < GameDriver.floor_size) {
			return_list.add(map[x+1][y]);
		}
		
		if (y-1 >= 0) {
			return_list.add(map[x][y-1]);
		}
		
		if (y+1 < GameDriver.floor_size) {
			return_list.add(map[x][y+1]);
		}
		
		return return_list;
		
	}
	
	// Locates a room from its coordinates. Mostly used to account for Zephyrals teleporting and entering a room without having the
	// actual room data itself. Will retrieve the room using coordinates.
	public Room coord_to_room (int[] coords) {
		return map[coords[0]][coords[1]];
	}
}
