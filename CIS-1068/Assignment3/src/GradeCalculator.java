import java.util.Scanner;

public class GradeCalculator {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("This program calculates your final grade based on homework and two exams.");
        System.out.println("If one score weights 100% or two combined score weight totals 100%, then the other score/s will not be calculated.");

        System.out.println();

        // Get weight for homework
        int homeworkWeight = getValidWeight(input, "Homework Weight (0-100): ", 0, 100);
        
        // Get Exam 1 and 2 if homework weight is less than 100%
        if (homeworkWeight <100) {

        	// Get weight for Exam 1 
        	int exam1Weight = getValidWeight(input, "Exam 1 Weight (0-" + (100 - homeworkWeight) + "): ", 0, 100 - homeworkWeight);

        	// Calculate weight for Exam 2
        	int exam2Weight = 100 - homeworkWeight - exam1Weight;

        	System.out.println("Using weights: Homework = " + homeworkWeight + "%, Exam 1 = " 
                            + exam1Weight + "%, Exam 2 = " + exam2Weight + "%.");
        	System.out.println();

        	// Calculate homework score only if homework weight is greater than 0
        	double homeworkScore = 0;
        	if (homeworkWeight > 0 || homeworkWeight>99) {
            homeworkScore = calculateHomeworkScore(input, homeworkWeight);
	        }
	
	        // Calculate exam 1 score only if exam 1 weight is greater than 0
	        double exam1Score = 0;
	        if (exam1Weight > 0) {
	            exam1Score = calculateExamScore(input, exam1Weight, "Exam 1");
	        }
	
	        // Calculate exam 2 score only if exam 2 weight is greater than 0
	        double exam2Score = 0;
	        if (exam2Weight > 0) {
	            exam2Score = calculateExamScore(input, exam2Weight, "Exam 2");
	        }
	
	        // Calculate final course grade
	        double finalGrade = homeworkScore + exam1Score + exam2Score;
	        System.out.printf("Final course grade: %.2f\n", finalGrade);
	    }
	    
        // Homework weight is 100% just do Final calculation        
        else {
	        System.out.println("Using weights: Homework = " + homeworkWeight + "%, Exam 1 = 0%, Exam 2 = 0%.");
	        System.out.println();
	        double homeworkScore = 0;
	        homeworkScore = calculateHomeworkScore(input, homeworkWeight);
	        double finalGrade = homeworkScore;
	        System.out.printf("Final course grade: %.2f\n", finalGrade);
        }
        
        input.close();
    }     
        

    // Get valid weight input with a range limit
    public static int getValidWeight(Scanner input, String prompt, int min, int max) {
        System.out.print(prompt);
        int weight = input.nextInt();
        while (weight < min || weight > max) {
            System.out.println("Invalid weight. Please enter a value between " + min + " and " + max + ".");
            System.out.print(prompt);
            weight = input.nextInt();
        }
        return weight;
    }

    // Calculate homework score
    public static double calculateHomeworkScore(Scanner input, int homeworkWeight) {
        System.out.println("Homework Calculation:");
        
        System.out.print("Number of assignments: ");
        int numAssignments = input.nextInt();
        numAssignments = Math.max(numAssignments, 1);  // Ensure at least 1 assignment

        System.out.print("Average homework grade: ");
        double avgHomework = input.nextDouble();
        avgHomework = Math.max(avgHomework, 0);  // Ensure non-negative score

        System.out.print("Number of late days used: ");
        int lateDays = input.nextInt();

        System.out.print("Number of labs attended: ");
        int labsAttended = input.nextInt();

        int maxHomeworkPoints = numAssignments * 10;
        int maxLabPoints = numAssignments * 4;

        // Adjust for late days
        if (lateDays > numAssignments / 2) {
            avgHomework *= 0.9;  // 10% penalty for too many late days
        } else if (lateDays == 0 && avgHomework < 10) {
            avgHomework += 5;  // Bonus for no late days and low average
        }

        double totalHomeworkPoints = (avgHomework * numAssignments) + (labsAttended * 4);
        totalHomeworkPoints = Math.min(totalHomeworkPoints, maxHomeworkPoints + maxLabPoints);

        System.out.printf("Total homework points: %.2f / %d\n", totalHomeworkPoints, (maxHomeworkPoints + maxLabPoints));

        double homeworkScore = homeworkWeight * (totalHomeworkPoints / (maxHomeworkPoints + maxLabPoints));
        System.out.printf("Weighted homework score: %.2f\n\n", homeworkScore);

        return homeworkScore;
    }

    // Calculate exam score
    public static double calculateExamScore(Scanner input, int examWeight, String examName) {
        System.out.println(examName + " Calculation:");

        System.out.print(examName + " score: ");
        double examScore = input.nextDouble();
        examScore = Math.max(examScore, 0);  // Ensure non-negative score

        System.out.print("Curve applied to " + examName + ": ");
        double examCurve = input.nextDouble();

        examScore = Math.min(examScore + examCurve, 100);  // Ensure max score is 100

        System.out.printf("Total points for %s: %.2f / 100\n", examName, examScore);
        double weightedExamScore = examWeight * (examScore / 100);
        System.out.printf("Weighted %s score: %.2f\n\n", examName, weightedExamScore);

        return weightedExamScore;
    }
}
