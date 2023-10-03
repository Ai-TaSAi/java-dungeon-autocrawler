package ooad.arcane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// GameDriver class - is the overall logic controller for the game. Contains many compositions and delegations.
public class GameDriver {

	// Public resources that are accessible between all classes.
	public static int floor_size = 3;
	public static int num_groups_enemies = 4;
	public static int[] center_coordinates = {1,1};
	
	// Make Random a public resource so the library doesn't need to be repeatedly imported.
	public static Random rd = new Random();
	
	// Compositions - Adventurers, Creatures, Floors.
	private ArrayList<Adventurer> adventurer_list;
	private ArrayList<Adventurer> dead_list;
	private ArrayList<Creature> creature_list;
	private ArrayList<Floor> floors;
	private Room spawn_room;
	
	// Rendering engine
	private Renderer camera;
	
	// Statistical variables that have no effect on the game. Just used to track score.
	private int s_total_enemies;
	private int treasure;
	private int round_counter;
	
	// Constructor initializes and constructs various variables. Prints out statuses.
	public GameDriver () {
		treasure = 0;
		s_total_enemies = 0;
		round_counter = 0;
		
		adventurer_list = new ArrayList<Adventurer>();
		dead_list = new ArrayList<Adventurer>();
		creature_list = new ArrayList<Creature>();
		floors = new ArrayList<Floor>();
		
		camera = new Renderer();
		
		spawn_room = new Room("Spawn", GameDriver.center_coordinates);
		
		System.out.println("New game initialized...");
	}
	
	// Secondary constructor to set up the dungeon. Split into private methods that assemble the dungeon bit bit.
	// Could be considered abstraction? Makes things a lot more readable and fixable per method.
	public void generate_dungeon () {
		spawn_adventurers();
		spawn_enemies();
		setup_floors();
		place_adventurers();
		print_adventurer_stats();
		place_enemies();
		// print_creature_stats();
		print_round_number();
		render();
	}
	
	// Creates the adventurers and adds them to the alive list.
	private void spawn_adventurers () {
		Adventurer ember_knight = new Ember_Knight();
		Adventurer zephyr_rogue = new Zephyr_Rogue();
		Adventurer mist_walker = new Mist_Walker();
		Adventurer terra_voyager = new Terra_Voyager();
		
		adventurer_list.add(ember_knight);
		adventurer_list.add(zephyr_rogue);
		adventurer_list.add(mist_walker);
		adventurer_list.add(terra_voyager);
		
		System.out.println("Adventurers commissioned...");
	}
	
	// Creates enemies and adds them to the monster list.
	private void spawn_enemies () {
		for (int i = 0; i < GameDriver.num_groups_enemies; i++) {
			Creature new_aquarid = new Aquarid();
			Creature new_fireborn = new Fireborn();
			Creature new_terravore = new Terravore();
			Creature new_zephyral = new Zephyral();
			
			creature_list.add(new_aquarid);
			creature_list.add(new_fireborn);
			creature_list.add(new_terravore);
			creature_list.add(new_zephyral);
			
			s_total_enemies += 4;
		}
		
		System.out.println("Enemies spawned...");
	}
	
	// Generates the 4 floors and populates the map with Room objects.
	private void setup_floors () {
		Floor f_fire = new Floor("Fire");
		Floor f_air = new Floor("Air");
		Floor f_water = new Floor("Water");
		Floor f_earth = new Floor("Earth");
		
		floors.add(f_fire);
		floors.add(f_air);
		floors.add(f_water);
		floors.add(f_earth);
		
		for (int i = 0; i < floors.size(); i++) {
			floors.get(i).generate_rooms();
		}
		
		System.out.println("Dungeon floors unearthed...");
	}
	
	// Place the adventurers in the spawn room.
	private void place_adventurers () {
		for (int i = 0; i < adventurer_list.size(); i++) {
			adventurer_list.get(i).set_room(spawn_room);
			spawn_room.add_adventurer(adventurer_list.get(i));
		}
		
		System.out.println("Placing adventurers at spawn...");
	}
	
	// Prints out the stats for both alive and dead adventurers.
	private void print_adventurer_stats () {
		System.out.println("Alive Adventurers:");
		for (int i = 0; i < adventurer_list.size(); i++) {
			adventurer_list.get(i).print_stats();
		}
		
		System.out.println("Dead Adventurers:");
		for (int i = 0; i < dead_list.size(); i++) {
			dead_list.get(i).print_stats();
		}
	}
	
	// Section header for each round of the game.
	private void print_round_number() {
		System.out.println("======================================== ROUND " + round_counter + " ========================================");
	}
	
	// Feeds initial spawn room coordinates to the enemies.
	private void place_enemies () {
		for (int i = 0; i < creature_list.size(); i++) {
			Room r = assign_random_room(creature_list.get(i));
			creature_list.get(i).set_room(r);
		}
		
		System.out.println("Enemies positioned...");
	}
	
	// Prints out creature stats. Mostly used for debugging. Not used in the final 
	private void print_creature_stats () {
		for (int i = 0; i < creature_list.size(); i++) {
			Creature cc = creature_list.get(i);
			System.out.print(cc.get_name() + " - ");
			System.out.print(cc.get_curr_room().element() + " (");
			System.out.println(cc.get_curr_room().coordinates()[0] + ", " + cc.get_curr_room().coordinates()[1] + ")");
		}
	}
	
	// Given a Room, uses its element to find the index of the floor within the floors ArrayList.
	private int floor_index (Room r) {
		int floor_idx = -1;
		
		for (int i = 0; i < floors.size(); i++) {
			if (floors.get(i).element().equals(r.element())) {
				floor_idx = i;
			}
		}
		
		return floor_idx;
	}
	
	// Assigns a creature to a random room within the floor it should spawn on. Has special checks for Fireborns.
	private Room assign_random_room (Creature c) {
		
		int floor_idx = -1;
		
		for (int i = 0; i < floors.size(); i++) {
			if (floors.get(i).element().equals(c.get_element())) {
				floor_idx = i;
			}
		}
		
		if (floors.get(floor_idx).element().equals("Fire")) {
			// Special fire spawn rules.
			Room assigned_room = floors.get(floor_idx).assign_random_room(true, c);
			return assigned_room;
		} else {
			// All other creatures.
			Room assigned_room = floors.get(floor_idx).assign_random_room(false, c);
			return assigned_room;
		}
	}
	
	// Updates the treasure total by checking the treasure values of both dead and alive adventurers.
	private void update_treasure_total () {
		
		treasure = 0;
		
		for (int i = 0; i < adventurer_list.size(); i++) {
			treasure += adventurer_list.get(i).get_treasure();
		}
		
		for (int i = 0; i < dead_list.size(); i++) {
			treasure += dead_list.get(i).get_treasure();
		}
	}
	
	// Runs a single round within the dungeon.
	public void run_round () {
		
		round_counter += 1;
		
		// For every adventurer... Move >>> Search >>> Fight.
		for (int i = adventurer_list.size() - 1; i >= 0; i--) {
			
			// Adventurers receive updated room data.
			Room current_updated = get_updated_data(adventurer_list.get(i).get_curr_room());
			ArrayList<Room> options = get_adjacent_rooms(true, adventurer_list.get(i).get_curr_room());
			
			// Adventurers move.
			Room new_room = adventurer_list.get(i).move(options, current_updated);
			
			update_layout_adventurer(current_updated, new_room, adventurer_list.get(i));
			
			// Adventurers receive updated room data.
			current_updated = get_updated_data(adventurer_list.get(i).get_curr_room());
			adventurer_list.get(i).set_room(current_updated);
			
			// Adventurers search
			adventurer_list.get(i).search();
			
			// Adventurers fight -- if there are enemies, scour the enemy list for enemies sharing the same room.
			// Then, simulate a fight against each enemy that matches the search.
			if (adventurer_list.get(i).sees_enemy()) {
				for (int j = creature_list.size() - 1; j >= 0; j--) {
					if (!(i > adventurer_list.size() - 1 || i < 0 || j > creature_list.size() - 1 || j < 0)) {
						if (Arrays.equals(adventurer_list.get(i).get_curr_room().coordinates(), creature_list.get(j).get_curr_room().coordinates())) {
							if (adventurer_list.get(i).get_curr_room().element().equals(creature_list.get(j).get_curr_room().element())) {
								// Matching rooms
								int outcome = adventurer_list.get(i).fight();
								int floor_idx = floor_index(adventurer_list.get(i).get_curr_room());
								
								switch (outcome) {
								case 0:
									// Draw - Nothing happens.
									break;
								case 1:
									// Player victory - Remove the creature, decrement j to check the next creature.
									floors.get(floor_idx).remove_creature(creature_list.get(j), adventurer_list.get(i).get_curr_room());
									creature_list.remove(j);
									break;
								case 2:
									// Enemy victory - Harm player, move to graveyard if need be, increment dead counter.
									if (adventurer_list.get(i).change_health(2)) {
										floors.get(floor_idx).remove_adventurer(adventurer_list.get(i).get_curr_room(), adventurer_list.get(i));
										dead_list.add(adventurer_list.get(i));
										adventurer_list.remove(i);
									}
									break;
								default:
									// Draw
								}
							}
						}
					}
				}
			}
			
		}
		
		// For every creature... Move >>> Fight
		for (int i = creature_list.size() - 1; i >= 0; i--) {
			// Creatures receive updated data.
			Room current_updated = get_updated_data(creature_list.get(i).get_curr_room());
			ArrayList<Room> options = get_adjacent_rooms(false, creature_list.get(i).get_curr_room());
			
			int floor_idx = floor_index(current_updated);
			
			// Creatures move.
			Room new_room = creature_list.get(i).move(options, current_updated, floors.get(floor_idx).has_player());
			update_layout_creature(current_updated, new_room, creature_list.get(i));
			//System.out.println(creature_list.get(i).get_name() + " moving to (" + new_room.coordinates()[0] + ", " + new_room.coordinates()[1] + ")");
			
			// Creatures receive updated data.
			current_updated = get_updated_data(creature_list.get(i).get_curr_room());
			creature_list.get(i).set_room(current_updated);
			
			// Creatures fight, if possible.
			if (creature_list.get(i).sees_enemy()) {
				for (int j = adventurer_list.size() - 1; j >= 0; j--) {
					if (!(i > creature_list.size() - 1 || i < 0 || j > adventurer_list.size() - 1 || j < 0)) {
						if (Arrays.equals(creature_list.get(i).get_curr_room().coordinates(), adventurer_list.get(j).get_curr_room().coordinates())) {
							if (creature_list.get(i).get_curr_room().element().equals(adventurer_list.get(j).get_curr_room().element())) {
								// Matching rooms
								int outcome = creature_list.get(i).fight(adventurer_list.get(j));
								
								switch (outcome) {
								case 0:
									// Draw - Nothing happens.
									break;
								case 1:
									// Player victory - Remove the creature, decrement j to check the next creature.
									floors.get(floor_idx).remove_creature(creature_list.get(i), adventurer_list.get(j).get_curr_room());
									creature_list.remove(i);
									break;
								case 2:
									// Enemy victory - Harm player, move to graveyard if need be, increment dead counter.
									if (adventurer_list.get(j).change_health(2)) {
										floors.get(floor_idx).remove_adventurer(adventurer_list.get(j).get_curr_room(), adventurer_list.get(j));
										dead_list.add(adventurer_list.get(j));
										adventurer_list.remove(j);
									}
									break;
								default:
									// Draw
								}
							}
						}
					}
				}
			}
		}
		
		// Update the treasure total. Print out the round number heading. Render visuals. Print adventurer stats.
		// Abstraction, I think.
		update_treasure_total();
		print_round_number();
		render();
		print_adventurer_stats();
	}
	
	// The "move" method to pass along to the floor. Moves the creature by removing it from the current room, and placing it in the
	// room it's chosen. This doesn't take any individual IDs for creatures. 
	private void update_layout_creature (Room past_room, Room present_room, Creature c) {
		int floor_idx = floor_index(present_room);
		
		floors.get(floor_idx).remove_creature(c, past_room);
		
		floors.get(floor_idx).place_creature(c, present_room);
	}
	
	// The "move" method to pass along to the floor. Moves the adventurer. Special conditions for moving between the spawn room and 
	// the other rooms in the floor.
	private void update_layout_adventurer (Room past_room, Room present_room, Adventurer a) {
		// Check if past room was the spawn room. Delete player from spawn, place on floor.
		if (past_room.element().equals("Spawn")) {
			spawn_room.remove_adventurer(a);
			
			int floor_idx = floor_index(present_room);
			
			floors.get(floor_idx).place_adventurer(present_room, a);
			
		} else if (present_room.element().equals("Spawn")) {
			// Check to see if the player is moving into the spawn room. Delete from floor, place in spawn room.
			int floor_idx = floor_index(present_room);
			
			floors.get(floor_idx).remove_adventurer(past_room, a);
			
			spawn_room.add_adventurer(a);
		} else {
			// Move between rooms on a single floor.
			int floor_idx = floor_index(present_room);
			
			floors.get(floor_idx).remove_adventurer(past_room, a);
			
			floors.get(floor_idx).place_adventurer(present_room, a);
		}
	}
	
	// Queries for adjacent rooms from Floors. Has a boolean flag that modifies output depending on adventurer or creature.
	private ArrayList<Room> get_adjacent_rooms (boolean is_adventurer, Room r) {
		ArrayList<Room> adj_rooms = new ArrayList<Room>();
		
		// Spawn room is hard coded to return the center of each floor.
		if (r.element().equals("Spawn")) {
			for (int i = 0; i < floors.size(); i++) {
				adj_rooms.add(floors.get(i).return_room_data(r));
			}
			
			return adj_rooms;
		}
		
		// If the adventurer is at the center of a floor, add the spawn room as an option to move to.
		if (is_adventurer && r.coordinates().equals(center_coordinates)) {
			adj_rooms.add(spawn_room);
		}
		
		// Otherwise, return the 2-4 rooms surrounding the current room.
		int floor_idx = floor_index(r);
		
		ArrayList<Room> floor_adjacencies = floors.get(floor_idx).return_adj_rooms(r);
		
		// Combines the adjacent rooms with the spawn room (if applicable) and returns the ArrayList.
		adj_rooms.addAll(floor_adjacencies);
		
		return adj_rooms;
	}
	
	// Returns a room with the most up to date creature and adventurer data. Used for movement, searching, and fighting.
	private Room get_updated_data (Room r) {
		
		if (r.element().equals("Spawn")) {
			return spawn_room;
		}
		
		int floor_idx = floor_index(r);
		
		return floors.get(floor_idx).return_room_data(r);
	}
	
	// Calls the renderer with the floor and spawn room data.
	private void render () {
		camera.render(floors, spawn_room);
	}
	
	// Runs the game. 
	public void run_game () {
		
		// Checks win condition codes. 0 = game continue / 1 = Adventurers win / 2 = Adventurers loose.
		int game_status = game_won();
		
		// Run a round if no end conditions are met.
		while (game_status == 0) {
			run_round();
			game_status = game_won();
		} 
		
		// Once it's over, print out the ending based on the ID that's been returned.
		System.out.println("======================================== GAME OVER! ========================================");
		
		// Adventurer win.
		if (game_status == 1) {
			System.out.println("Joyful be the villagers! The adventurers have conquered the dungeon!");
			System.out.println("Adventurer victory!");
			System.out.println("Total treasure amassed: " + treasure);
			
			int enemies_slain = s_total_enemies - creature_list.size();
			System.out.println("Total enemies slain: " + enemies_slain);
			
			System.out.println("Adventurer deaths: " + dead_list.size());
			
		} else if (game_status == 2) {
			// Adventurer loss
			System.out.println("Only further misfortune awaits! The dungeon has consumed more souls...");
			System.out.println("Adventurer defeat!");
			System.out.println("Total treasure amassed: " + treasure);
			
			int enemies_slain = s_total_enemies - creature_list.size();
			System.out.println("Total enemies slain: " + enemies_slain);
			
			System.out.println("Adventurer deaths:" + dead_list.size());
		}
		
	}
	
	// Private method that internally decides if the game is over.
	private int game_won () {
		// 0 - No events / 1 - Adventurer Victory / 2 - Adventurer Defeat
		if (treasure >= 50 || creature_list.size() == 0) {
			// Treasure exceeds 50 pieces, or all creatures slain.
			return 1;
		} else if (dead_list.size() == 4) {
			// Everyone's dead. :(
			return 2;
		} else {
			// Game continues.
			return 0;
		}
	}
	
	// What we see at the highest level. Abstraction to the highest degree. Assemble the dungeon and run the game.
    public static void main(String[] args) {
        GameDriver arcane = new GameDriver();
        arcane.generate_dungeon();
        arcane.run_game();
    }

}
