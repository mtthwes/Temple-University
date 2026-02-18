package testpapersystem;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

        	TestBank testBank = new TestBank();

			testBank.addQuestion(new ObjectiveQuestion(5, 3, 2, "What is 2 + 2?", "4"));
			testBank.addQuestion(new FillInTheBlankQuestion(10, 5, 1, "___ was the 16th US President.", "Abraham Lincoln"));
			testBank.addQuestion(new MultipleChoiceQuestion(
			    5, 2, 1, "Who lives in a pineapple under the sea?",
			    Arrays.asList("Peter Griffin", "Scooby Doo", "Spongebob Squarepants", "Eric Cartman"),
			    "Spongebob Squarepants"
			));

			System.out.print("Enter filename to save the question bank: ");
			String questionBankFile = scanner.nextLine();
			try {
			    testBank.saveToFile(questionBankFile);
			    System.out.println("Questions saved to " + questionBankFile);
			} catch (IOException e) {
			    System.err.println("Error saving questions: " + e.getMessage());
			}

			TestBank loadedTestBank = new TestBank();
			System.out.print("Enter filename to load the question bank: ");
			String loadQuestionBankFile = scanner.nextLine();
			try {
			    loadedTestBank.loadFromFile(loadQuestionBankFile);
			    System.out.println("Questions loaded from " + loadQuestionBankFile);
			} catch (IOException e) {
			    System.err.println("Error loading questions: " + e.getMessage());
			}

			System.out.print("Enter the number of questions for the test: ");
			int numQuestions = scanner.nextInt();
			scanner.nextLine(); // consume the newline
			Test test = loadedTestBank.generateTest(numQuestions);

			System.out.println("Generated Test:");
			System.out.println(test);

			System.out.println("Answer Key:");
			System.out.println(test.toAnswerString());

			System.out.print("Enter filename to save the test: ");
			String testFile = scanner.nextLine();
			System.out.print("Enter filename to save the answer key: ");
			String answerKeyFile = scanner.nextLine();
			try {
			    test.saveToFile(testFile, answerKeyFile);
			    System.out.println("Test saved to " + testFile);
			    System.out.println("Answer key saved to " + answerKeyFile);
			} catch (IOException e) {
			    System.err.println("Error saving test or answer key: " + e.getMessage());
			}
		}
    }
}
