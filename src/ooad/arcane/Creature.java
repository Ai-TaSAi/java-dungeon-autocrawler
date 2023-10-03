package ooad.arcane;

import java.util.ArrayList;

// Creature superclass, is the overarching class for Creature subclasses Terravore, Fireborn, Aquarid, Zephyral.
public class Creature {
	// Protected variables are those that are passable between the superclass and its subclasses, but not to outside classes.
	// This is a form of encapsulation, to ensure that only the relevant variables are accessed by the relevant classes.
	protected String type;
	protected String name;
	
	protected String element;
	protected Room curr_room;
	
	// The default function for move, that just returns the current room (The Creature stays still).
	// Even though all other subclasses implement this function, declaring it as an abstract method is not a good idea,
	// as that forces the class to be declared as abstract as well. Thus, the function needs to concretely exist.
	public Room move (ArrayList<Room> room_options, Room current_room, boolean player_on_floor) {
		// Default movement is to do nothing.
		curr_room = current_room;
		return current_room;
	}
	
	// Java objects use a lot of getter and setter methods. This is another part of encapsulation, as the private encapsulated
	// variables should not be directly accessed, rather only modified and viewed by functions that grant permission to view them.
	public void set_room (Room r) {
		curr_room = r;
	}
	
	public Room get_curr_room () {
		return curr_room;
	}
	
	public String get_element () {
		return element;
	}
	
	public String get_name () {
		return name;
	}
	
	public String get_type () {
		return type;
	}
	
	// A simple, easier to read function to return if there is an enemy in the room. This is used in the fighting sequence in the
	// GameDriver class, and is an example of abstraction, as the Creature is being treated as an actual entity, with a field of vision.
	public boolean sees_enemy () {
		return curr_room.adventurers().size() > 0;
	}
	
	// The fight method is universal across all creatures. Thus, it can be concretely defined in the superclass, and all subclasses 
	// will inherit it as a function they can execute. 
	public int fight (Adventurer opponent) {
		// 0 - Draw / 1 - Adventurer win / 2 - Monster win
		// This function is coded to be nearly universal across both adventurers and creatures - It will return a code that denotes
		// a specific condition not relative to the initiator of the fight. This helps standardization of this already massive mess.
		String e = opponent.get_curr_room().element();
		
		// Roll dice. Adventurers get a potential bonus based on the room element.
		int creature_dice_roll = Dice.roll(6) + Dice.roll(6);
		int opponent_dice_roll = Dice.roll(6) + Dice.roll(6) + opponent.get_fight_bonus(e);
		
		// Logic to determine who wins. This includes factoring the adventurer's dodge change.
		if (creature_dice_roll == opponent_dice_roll) {
			return 0;
		} else if (creature_dice_roll < opponent_dice_roll) {
			return 1;
		} else {
			if (GameDriver.rd.nextDouble() <= opponent.get_dodge_chance(e)) {
				return 0;
			} else {
				return 2;
			}
		}
	}
}