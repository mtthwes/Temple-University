import java.io.*;
import java.util.*;

public class CheatingHangmanExtra {
    public static void main(String[] args) {
        // load file
        List<String> dictionary = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/dictionary.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    dictionary.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading dictionary: " + e.getMessage());
            return;
        }
        if (dictionary.isEmpty()) {
            System.out.println("Dictionary is empty.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;
        while (playAgain) {
            // ask word length and filter dictionary words of that length
            System.out.print("What word length would you like? ");
            int wordLength = getValidInt(scanner);
            Set<String> currentWords = new TreeSet<>();
            for (String w : dictionary) {
                if (w.length() == wordLength) {
                    currentWords.add(w.toLowerCase());
                }
            }
            if (currentWords.isEmpty()) {
                System.out.println("No words of that length found.");
                continue;
            }

            // ask for guesses
            System.out.print("How many guesses would you like? ");
            int remainingGuesses = getValidInt(scanner);

            Set<Character> guessedLetters = new TreeSet<>();
            Set<Character> missedLetters = new TreeSet<>();
            char[] pattern = new char[wordLength];
            Arrays.fill(pattern, '_');

            // game loop
            while (remainingGuesses > 0 && new String(pattern).contains("_")) {
                StringBuilder patternSB = new StringBuilder();
                for (int i = 0; i < pattern.length; i++) {
                    patternSB.append(pattern[i]);
                    if (i < pattern.length - 1) {
                        patternSB.append(",");
                    }
                }
                System.out.println("\nWord: " + patternSB.toString());
                System.out.println("Misses: " + missedLetters);
                System.out.println("Guesses remaining: " + remainingGuesses);
                System.out.print("Your next guess: ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                    System.out.println("Invalid input. Please enter a single letter.");
                    continue;
                }
                char guess = input.charAt(0);
                if (guessedLetters.contains(guess)) {
                    System.out.println("You already guessed that letter.");
                    continue;
                }
                guessedLetters.add(guess);

                Map<String, Set<String>> families = new HashMap<>();
                for (String word : currentWords) {
                    StringBuilder keySB = new StringBuilder();
                    for (int i = 0; i < word.length(); i++) {
                        keySB.append(guessedLetters.contains(word.charAt(i)) ? word.charAt(i) : '_');
                    }
                    String key = keySB.toString();
                    families.computeIfAbsent(key, k -> new TreeSet<>()).add(word);
                }

                String bestKey = "";
                // Randomly choose strategy: 50% chance for each
                if (Math.random() < 0.5) {
                    // Strategy 1: Choose the family with the most words.
                    int maxSize = 0;
                    for (Map.Entry<String, Set<String>> entry : families.entrySet()) {
                        if (entry.getValue().size() > maxSize) {
                            maxSize = entry.getValue().size();
                            bestKey = entry.getKey();
                        }
                    }
                } else {
                    // Strategy 2: Choose the family that reveals the guessed letter the fewest times.
                    int minRevealed = Integer.MAX_VALUE;
                    int candidateFamilySize = 0;
                    for (Map.Entry<String, Set<String>> entry : families.entrySet()) {
                        int count = 0;
                        for (int i = 0; i < entry.getKey().length(); i++) {
                            if (entry.getKey().charAt(i) == guess) {
                                count++;
                            }
                        }
                        if (count < minRevealed) {
                            minRevealed = count;
                            bestKey = entry.getKey();
                            candidateFamilySize = entry.getValue().size();
                        } else if (count == minRevealed) {
                            if (entry.getValue().size() > candidateFamilySize) {
                                bestKey = entry.getKey();
                                candidateFamilySize = entry.getValue().size();
                            }
                        }
                    }
                }
                currentWords = families.get(bestKey);

                // Update the pattern using the best key from the chosen strategy
                boolean found = false;
                for (int i = 0; i < wordLength; i++) {
                    if (pattern[i] == '_' && bestKey.charAt(i) != '_') {
                        pattern[i] = bestKey.charAt(i);
                        if (bestKey.charAt(i) == guess)
                            found = true;
                    }
                }
                if (!found) {
                    missedLetters.add(guess);
                    remainingGuesses--;
                    System.out.println("Sorry, no '" + guess + "' in the word.");
                } else {
                    System.out.println("Good guess!");
                }
            }

            // end of game
            if (new String(pattern).contains("_")) {
                String finalWord = currentWords.iterator().next();
                System.out.println("\nSorry - you lost. The word was: " + finalWord);
            } else {
                System.out.println("\nYOU WON!! The word was: " + new String(pattern));
            }

            // play again
            System.out.print("Would you like to play again? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            while (!response.equals("yes") && !response.equals("no")) {
                System.out.print("Please answer yes or no: ");
                response = scanner.nextLine().trim().toLowerCase();
            }
            playAgain = response.equals("yes");
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private static int getValidInt(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                scanner.nextLine();
                return num;
            } else {
                System.out.println("Invalid number, please try again.");
                scanner.nextLine();
            }
        }
    }
}
