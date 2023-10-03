package ooad.arcane;

//Mist Walker is a subclass of Adventurer, and inherits its methods and parameters. It overrides the fight bonus method
//in order to modify its fight roll based on the element of the floor it is currently on.
public class Mist_Walker extends Adventurer {

	// Mist Walker constructor that specifies that the Adventurer object is of the type Mist Walker.
	public Mist_Walker () {
		type = "MW";
		name = "Mist Walker";
		
		resonance = "Water";
		discord = "Air";
		
		health = 3;
		dodge = 0.5;
		
		treasure = 0;
	}
	
	// This function overrides the get_dodge_chance() default method in the Adventurer class, in order to account for the
	// Mist Walker's elemental resonances and discord.
	public double get_dodge_chance (String element) {
		if (element.equals(resonance)) {
			return dodge + 0.25;
		} else if (element.equals(discord)) {
			return dodge - 0.25;
		} else {
			return dodge;
		}
	}
}
