package ooad.arcane;

import java.util.ArrayList;

// Room class. Is aggregated into floors. Keeps track of its elements and what's in the current room.
public class Room {
	
	// Private unaccessible data (encapsulation) that stores the coordinates of the room, as well as its element.
	private int[] coordinates;
	private String element;
	
	// Keeps track of adventurers and creatures (for both updating rooms and displaying in the renderer)
	private ArrayList<Adventurer> adventurers_in_room;
	private ArrayList<Creature> creatures_in_room;
	
	// Constructor that assigns element and coordinates.
	public Room (String e, int[] c) {
		coordinates = c;
		element = e;
		
		adventurers_in_room = new ArrayList<Adventurer>();
		creatures_in_room = new ArrayList<Creature>();
	}
	
	// Getter method for coordinates.
	public int[] coordinates () {
		return coordinates;
	}
	
	// Getter method for adventurers.
	public ArrayList<Adventurer> adventurers () {
		return adventurers_in_room;
	}
	
	// Getter method for creatures.
	public ArrayList<Creature> creatures () {
		return creatures_in_room;
	}
	
	// Getter method for element.
	public String element () {
		return element;
	}
	
	// Setter method that adds an adventurer to a room.
	public boolean add_adventurer (Adventurer a) {
		adventurers_in_room.add(a);
		return true;
	}
	
	// Setter method that removes a particular adventurer from a room.
	public boolean remove_adventurer (Adventurer a) {
		
		int size = adventurers_in_room.size();
		
		if (size == 0) {
			return false;
		} else {
			int rm_i = -1;
			
			for (int i = 0; i < size; i++) {
				if (a.type().equals(adventurers_in_room.get(i).type())) {
					rm_i = i;
				}
			}
			
			if (rm_i != -1) {
				adventurers_in_room.remove(rm_i);
				return true;
			} else {
				return false;
			}
		}
	}
	
	// Setter method that adds a creature to a room. These are the same as adventurers, but take different parameters.
	public boolean add_creature (Creature c) {
		creatures_in_room.add(c);
		return true;
	}
	
	// Setter method that removes a particular creature from a room.
	public boolean remove_creature (Creature c) {
		
		int size = creatures_in_room.size();
		
		if (size == 0) {
			return false;
		} else {
			int rm_i = -1;
			
			for (int i = 0; i < size; i++) {
				if (c.get_type().equals(creatures_in_room.get(i).get_type())) {
					rm_i = i;
				}
			}
			
			if (rm_i != -1) {
				creatures_in_room.remove(rm_i);
				return true;
			} else {
				return false;
			}
		}
	}


}
