package edu.temple.datastructures.dyee.assessment;

public class CozaLozaWoza {
	public static void main(String[] args) {
		boolean hasCoza = false;
		boolean hasLoza = false;
		boolean hasWoza = false;

		for (int i = 1; i <= 110; i++) {
			hasCoza = 0 == i % 3;
			hasLoza = 0 == i % 5;
			hasWoza = 0 == i % 7;

			if (hasCoza || hasLoza || hasWoza) {
				if (hasCoza) {
					System.out.print("Coza");
				}

				if (hasLoza) {
					System.out.print("Loza");
				}

				if (hasWoza) {
					System.out.print("Woza");
				}
			} else {
				System.out.print(i);
			}

			System.out.print(" ");

			if (0 == i % 11) {
				System.out.println("");
			}
		}
	}
}
