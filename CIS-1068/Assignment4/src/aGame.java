import java.util.Scanner;
import java.util.Random;

public class aGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int roundsPlayed = 0;
        int userWins = 0;
        int computerWins = 0;

        printRules();

        boolean playGame = true;
        while (playGame) {
            String userMove = getUserMove(scanner);
            String computerMove = generateComputerMove(random);
            String result = determineWinner(userMove, computerMove);
            printRoundSummary(userMove, computerMove, result);
            
            roundsPlayed++;
            if (result.equals("win")) {
                userWins++;
            } else if (result.equals("lose")) {
                computerWins++;
            }
            
            playGame = askToContinue(scanner);
        }

        printFinalStats(roundsPlayed, userWins, computerWins);
    }

    static void printRules() {
        System.out.println("Welcome to Baggebo! Here are the rules:");
        System.out.println("Revskar beats Vittsjo, Kloven");
        System.out.println("Vittsjo beats Oxberg, Tvaro");
        System.out.println("Oxberg beats Revskar, Kloven");
        System.out.println("Kloven beats Tvaro, Vittsjo");
        System.out.println("Tvaro beats Revskar, Oxberg");
        System.out.println("The computer wins in the event of a tie.");
    }

    static String getUserMove(Scanner scanner) {
        String[] validMoves = {"Kloven", "Oxberg", "Vittsjo", "Tvaro", "Revskar"};
        String userMove = "";
        boolean isValid = false;

        while (!isValid) {
            System.out.println();
        	System.out.println("Enter your move (Kloven, Oxberg, Vittsjo, Tvaro, Revskar):");
            userMove = scanner.nextLine();
            for (String move : validMoves) {
                if (move.equalsIgnoreCase(userMove)) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                System.out.println("Invalid move. Please try again.");
            }
        }
        return userMove;
    }

    static String generateComputerMove(Random random) {
        String[] moves = {"Kloven", "Oxberg", "Vittsjo", "Tvaro", "Revskar"};
        int moveIndex = random.nextInt(moves.length);
        String computerMove = moves[moveIndex];
        return computerMove;
    }

    static String determineWinner(String userMove, String computerMove) {
        if (userMove.equalsIgnoreCase(computerMove)) {
            return "lose"; 
        }

        if (userMove.equalsIgnoreCase("Revskar")) {
            if (computerMove.equals("Vittsjo") || computerMove.equals("Kloven")) {
                return "win"; 
            }
        } else if (userMove.equalsIgnoreCase("Vittsjo")) {
            if (computerMove.equals("Oxberg") || computerMove.equals("Tvaro")) {
                return "win"; 
            }
        } else if (userMove.equalsIgnoreCase("Oxberg")) {
            if (computerMove.equals("Revskar") || computerMove.equals("Kloven")) {
                return "win";
            }
        } else if (userMove.equalsIgnoreCase("Kloven")) {
            if (computerMove.equals("Tvaro") || computerMove.equals("Vittsjo")) {
                return "win";
            }
        } else if (userMove.equalsIgnoreCase("Tvaro")) {
            if (computerMove.equals("Revskar") || computerMove.equals("Oxberg")) {
                return "win";
            }
        }

        return "lose"; 
    }

    static void printRoundSummary(String userMove, String computerMove, String result) {
        System.out.println("Your move: " + userMove);
        System.out.println("Computer's move: " + computerMove);
        if (result.equals("win")) {
            System.out.println("You win this round!");
        } else {
            System.out.println("Computer wins this round!");
        }
    }

    static boolean askToContinue(Scanner scanner) {
        System.out.println("Do you want to play another round? (y/n):");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("y");
    }

    static void printFinalStats(int roundsPlayed, int userWins, int computerWins) {
        System.out.println();
    	System.out.println("Game Over!");
        System.out.println("Rounds played: " + roundsPlayed);
        System.out.println("User wins: " + userWins);
        System.out.println("Computer wins: " + computerWins);
    }
}
