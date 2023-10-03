package ooad.arcane;

//Terra Voyager is a subclass of Adventurer, and inherits its methods and parameters. It overrides the fight bonus method
//in order to modify its fight roll based on the element of the floor it is currently on.
public class Terra_Voyager extends Adventurer{

	// Terra Voyager constructor that specifies that the Adventurer object is of the type Mist Walker.
	public Terra_Voyager () {
		type = "TV";
		name = "Terra Voyager";
		
		resonance = "Earth";
		discord = "Fire";
		
		health = 7;
		dodge = 0.1;
		
		treasure = 0;
	}
	
	// This function overrides the get_armor() default method in the Adventurer class, in order to account for the
	// Mist Walker's elemental resonances and discord.
	public int get_armor (String element) {
		if (element.equals(resonance)) {
			return 1;
		} else if (element.equals(discord)) {
			return -1;
		} else {
			return 0;
		}
	}
}
