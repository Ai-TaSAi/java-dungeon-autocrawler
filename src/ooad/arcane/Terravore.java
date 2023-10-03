package ooad.arcane;

import java.util.ArrayList;

// Terravores are a subclass of Creature that inherit its methods, but not its constructor.
public class Terravore extends Creature {
	
	// This constructor will specify that the creature is of the type Terravore. It fills it with the appropriate data that is
	// inherent only to Terravores.
	public Terravore () {
		type = "T";
		element = "Earth";
		name = "Terravore";
	}
	
	// Terravores override movement behavior native to Creatures. They do not move. However, to override methods, the same
	// variables must be passed through, even if they are not used.
	public Room move (ArrayList<Room> room_options, Room current_room, boolean player_on_floor) {
		// Terravores do not move.
		curr_room = current_room;
		return curr_room;
	}
	
}
