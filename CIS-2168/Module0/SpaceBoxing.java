package edu.temple.datastructures.dyee.assessment;

import java.util.Scanner;

public class SpaceBoxing {
	public static void main(String[] args) {
		Double earthWeight = null;
		Integer planetNumber = null;

		try (Scanner userInput = new Scanner(System.in)) {
			do {
				System.out.println("What is your weight on Earth?");
				try {
					String enteredWeight = userInput.next();
					earthWeight = Double.parseDouble(enteredWeight);
				} catch (NumberFormatException ex) {
					System.out.println("Please enter a number.");
				}
			} while (null == earthWeight);

			do {
				System.out.println("Which planet do you want to fight on?");
				System.out.println("1 - Venus");
				System.out.println("2 - Mars");
				System.out.println("3 - Jupiter");
				System.out.println("4 - Saturn");
				System.out.println("2 - Uranus");
				System.out.println("2 - Neptune");
				try {
					String enteredPlanet = userInput.next();
					planetNumber = Integer.parseInt(enteredPlanet);
				} catch (NumberFormatException e) {
					System.out.println("Please enter a valid planet.");
				}
			} while (null == planetNumber);
		}

		String planetName;
		double relativeGravity = 1.0;
		switch (planetNumber) {
			case 1:
				planetName = "Venus";
				relativeGravity = 0.78;
				break;
			case 2:
				planetName = "Mars";
				relativeGravity = 0.39;
				break;
			case 3:
				planetName = "Jupiter";
				relativeGravity = 2.65;
				break;
			case 4:
				planetName = "Saturn";
				relativeGravity = 1.17;
				break;
			case 5:
				planetName = "Uranus";
				relativeGravity = 1.05;
				break;
			case 6:
				planetName = "Neptune";
				relativeGravity = 1.23;
				break;
			default:
				planetName = "";
				relativeGravity = 1.0;
		}
		double relativeWeight = earthWeight * relativeGravity;
		System.out.println("Your weight on " + planetName + " would be " + relativeWeight + ".");
	}

}
