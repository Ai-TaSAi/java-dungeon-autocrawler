package ooad.arcane;

// Ember Knight is a subclass of Adventurer, and inherits its methods and parameters. It overrides the fight bonus method
// in order to modify its fight roll based on the element of the floor it is currently on.
public class Ember_Knight extends Adventurer {
	
	// Ember Knight constructor that specifies that the Adventurer object is of the type Ember Knight.
	public Ember_Knight () {
		type = "EK";
		name = "Ember Knight";
		
		resonance = "Fire";
		discord = "Water";
		
		health = 5;
		dodge = 0.2;
		
		treasure = 0;
	}
	
	// This function overrides the get_fight_bonus() default method in the Adventurer class, in order to account for the
	// Ember Knight's elemental resonances and discord.
	public int get_fight_bonus (String element) {
		if (element.equals(resonance)) {
			return 2;
		} else if (element.equals(discord)) {
			return -2;
		} else {
			return 0;
		}
	}
}
