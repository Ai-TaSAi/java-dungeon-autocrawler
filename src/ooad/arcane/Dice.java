package ooad.arcane;

// Dice just provides a static method to roll a dice, given the number of sides that the dice has is inputted in.
public class Dice {
	public static int roll (int sides) {
		// Utilizes the public random class from the GameDriver.
		return GameDriver.rd.nextInt(sides) + 1;
	}
}
