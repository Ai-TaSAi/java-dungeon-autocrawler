package ooad.arcane;

//Zephyr Rogue is a subclass of Adventurer, and inherits its methods and parameters. It overrides the fight bonus method
//in order to modify its fight roll based on the element of the floor it is currently on.
public class Zephyr_Rogue extends Adventurer {
	
	// Ember Knight constructor that specifies that the Adventurer object is of the type Zephyr Rouge.
	public Zephyr_Rogue () {
		type = "ZR";
		name = "Zephyr Rogue";
		
		resonance = "Air";
		discord = "Earth";
		
		health = 3;
		dodge = 0.25;
		
		treasure = 0;
	}
	
	// This function overrides the get_search_bonus() default method in the Adventurer class, in order to account for the
	// Zephyr Rogue's elemental resonances and discord.
	public int get_search_bonus (String element) {
		if (element.equals(resonance)) {
			return 2;
		} else if (element.equals(discord)) {
			return -2;
		} else {
			return 0;
		}
	}
}
