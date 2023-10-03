package ooad.arcane;

import java.util.ArrayList;

//Aquarids are a subclass of Creature that inherit its methods, but not its constructor.
public class Aquarid extends Creature {

	// This constructor will specify that the creature is of the type Aquarid. It fills it with the appropriate data that is
	// inherent only to Aquarid.
	public Aquarid () {
		type = "A";
		element = "Water";
		name = "Aquarid";
	}
	
	// Aquarids have the most sophisticated movement override of all the Creature subclasses. Like Zephyrals, it uses all three
	// input parameters in the function override from the Creature superclass.
	public Room move (ArrayList<Room> room_options, Room current_room, boolean player_on_floor) {
		
		// If the current room has a player, return immediately.
		if (current_room.adventurers().size() > 0) {
			curr_room = current_room;
			return curr_room;
		}
		
		// First go through the adjacent rooms. If there is a player in that room, designate that as the room to move to.
		// Return there and then.
		int rm_idx = -1;
		
		for (int i = 0; i < room_options.size(); i++) {
			if (room_options.get(i).adventurers().size() > 0) {
				rm_idx = i;
			}
		}
		
		if (rm_idx != -1) {
			curr_room = room_options.get(rm_idx);
			return curr_room;
		}
		
		// Next, check if players are on the floor. 
		if (!player_on_floor) {
			// If there are no players, select a random room.
			curr_room = room_options.get(GameDriver.rd.nextInt(room_options.size()));
			return curr_room;
		} else {
			// If there are players, do not move.
			curr_room = current_room;
			return curr_room;
		}
	}
	
}
