package ooad.arcane;

import java.util.ArrayList;

// The adventurer superclass that passes its traits down to the Ember Knight, Zephyr Rogue, Mist Walker, and Terra Voyager.
public class Adventurer {
	
	// Protected variables define the Adventurer object. These are shared between the superclass and the subclasses.
	protected String type;
	protected String name;
	
	protected String resonance;
	protected String discord;
	
	protected int health;
	protected double dodge;
	
	protected int treasure;
	
	protected Room curr_room;
	
	// Get method for the type of adventurer (Used for rendering)
	public String type () {
		return type;
	}
	
	// Concrete methods that affect rolls for various methods. These don't do much on their own, but are overriden by
	// various subclasses to factor in their elemental resonances and discords.
	public int get_fight_bonus (String element) {
		return 0;
	}
	
	public double get_dodge_chance (String element) {
		return dodge;
	}
	
	public int get_search_bonus (String element) {
		return 0;
	}
	
	public int get_armor (String element) {
		return 0;
	}
	
	// Get method for HP
	public int get_health () {
		return health;
	}
	
	// Damage method that inflicts damage to an Adventurer. This also checks if the adventurer is dead, via the boolean returned.
	// If the health of the Adventurer falls below 1, returns true to let the GameDriver handle an adventurer death.
	public boolean change_health (int damage) {
		String element = curr_room.element();
		
		health = health - (damage - get_armor(element));
		
		if (health <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// Search function that first checks to see if there are any creatures. If there are, return immediately, as Adventurers cannot
	// search when there are monsters in the room.
	public void search () {
		
		if (curr_room.creatures().size() > 0) {
			return;
		}
		
		int dice_roll = Dice.roll(6) + Dice.roll(6) + get_search_bonus(curr_room.element());
		
		// Increments the adventurer's personal treasure stash.
		if (dice_roll >= 11) {
			treasure += 1;
		} 
	}
	
	// Get method for treasure stat.
	public int get_treasure () {
		return treasure;
	}
	
	// Prints out the necessary stats (and room coordinates for debugging) for the Adventurer.
	public void print_stats () {
		System.out.print(name);
		// If the HP is negative, change it to just 0.
		int show_hp = 0;
		if (health > show_hp) {
			show_hp = health;
		}
		System.out.print(" - HP: " + show_hp);
		System.out.print(" - Treasure: " + treasure);
		System.out.println(" - Room: " + curr_room.element() + " (" + curr_room.coordinates()[0] + ", " + curr_room.coordinates()[1] + ")");
	}
	
	// Method for fight. Very similar to the Creature fight method, just without the opponent parameter, since all creatures are treated
	// the same in the fight method. There is nothing that must be accounted for, since the element parameter is under the Adventurer.
	public int fight () {
		String element = curr_room.element();
		
		// 0 - Draw / 1 - Adventurer win / 2 - Monster win
		int creature_dice_roll = Dice.roll(6) + Dice.roll(6);
		int self_dice_roll = Dice.roll(6) + Dice.roll(6) + get_fight_bonus(element);
		
		// Same universal codes for who won or drew.
		if (creature_dice_roll == self_dice_roll) {
			return 0;
		} else if (creature_dice_roll < self_dice_roll) {
			return 1;
		} else {
			if (GameDriver.rd.nextDouble() <= get_dodge_chance(element)) {
				return 0;
			} else {
				return 2;
			}
		}
	}
	
	// Method to determine if a fight must break out.
	public boolean sees_enemy () {
		return curr_room.creatures().size() > 0;
	}
	
	// Setter method for the room that the adventurer is in.
	public void set_room (Room r) {
		curr_room = r;
	}
	// Getter method for the room that the adventurer is in (mostly used for getting coordinates for the updated room)
	public Room get_curr_room () {
		return curr_room;
	}
	
	// Move function. Just a random movement unless there's a monster in the room, in which case, return the current room.
	public Room move (ArrayList<Room> room_options, Room current_room) {
		
		// If the creature is in the room, don't move.
		if (current_room.creatures().size() > 0) {
			curr_room = current_room;
			return curr_room;
		}
		
		// Get a random room to move to.
		int num_rooms = room_options.size();
		
		curr_room = room_options.get(GameDriver.rd.nextInt(num_rooms));
		return curr_room;
	}
}
