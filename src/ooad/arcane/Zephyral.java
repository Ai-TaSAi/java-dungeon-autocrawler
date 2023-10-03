package ooad.arcane;

import java.util.ArrayList;

//Zephyrals are a subclass of Creature that inherit its methods, but not its constructor.
public class Zephyral extends Creature {
	
	// This constructor will specify that the creature is of the type Zephyral. It fills it with the appropriate data that is
	// inherent only to Zephyrals.
	public Zephyral () {
		type = "Z";
		element = "Air";
		name = "Zephyral";
	}
	
	// Zephyrals override the move function of creatures. While the variables passed through must remain the same for the function to
	// work for inheritance with the superclass, the specific creature does not need to use all of them.
	
	// In fact, this uses the GameDriver.floor_size global variable to decide which room it must teleport to, as the variables passed in
	// are not sufficient enough to give it the full number of options to move to.
	public Room move (ArrayList<Room> room_options, Room current_room, boolean player_on_floor) {
		
		// If the player is on the floor, don't move.
		if (player_on_floor) {
			curr_room = current_room;
			return curr_room;
		}
		
		// Get a random room to move to.
		int[] random_coords = {GameDriver.rd.nextInt(GameDriver.floor_size), GameDriver.rd.nextInt(GameDriver.floor_size)};
		
		// Constructing a room like this is only good when used in a function that locates the actual room.
		// This room has no data besides its element and coordinate points, and is solely used because the move() function
		// must return a room and not a pair of coordinate points.
		curr_room = new Room(element, random_coords);
		
		return curr_room;
	}
}
