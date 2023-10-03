package ooad.arcane;

import java.util.ArrayList;
import java.util.Arrays;

//Fireborns are a subclass of Creature that inherit its methods, but not its constructor.
public class Fireborn extends Creature {
	
	// This constructor will specify that the creature is of the type Fireborn. It fills it with the appropriate data that is
	// inherent only to Fireborn.
	public Fireborn () {
		type = "F";
		element = "Fire";
		name = "Fireborn";
	}
	
	// Zephyrals override the move function of creatures. While the variables passed through must remain the same for the function to
	// work for inheritance with the superclass, the specific creature does not need to use all of them. However, Fireborns specifically
	// do utilize all of these parameters.
	public Room move (ArrayList<Room> room_options, Room current_room, boolean player_on_floor) {
		
		// If the player is on the floor, don't move.
		if (player_on_floor) {
			curr_room = current_room;
			return curr_room;
		}
		
		// Code to determine a new room that is not the center room.
		boolean room_found = false;
		
		int num_rooms = room_options.size();
		
		Room return_room = room_options.get(GameDriver.rd.nextInt(num_rooms));
		
		while (!room_found) {
			// Utilize the publicly available designated center coordinate room to avoid walking in there on the move room check.
			if (!Arrays.equals(return_room.coordinates(), GameDriver.center_coordinates)) {
				room_found = true;
				break;
			}
			
			// Assign a new room if the previous one was the center.
			return_room = room_options.get(GameDriver.rd.nextInt(num_rooms));
		}
		
		curr_room = return_room;
		return curr_room;
	}
}
