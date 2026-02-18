package edu.temple.datastructures.dyee.assessment;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class GradesStatistics {
	private static DecimalFormat decimalFormat = new DecimalFormat("0.00");

	public static void main(String[] args) {
		int numberOfStudents = 0;
		Double[] grades;

		try (Scanner userInput = new Scanner(System.in)) {
			do {
				System.out.println("Enter the number of students:");
				try {
					String numberOfStudentsInput = userInput.next();
					numberOfStudents = Integer.parseInt(numberOfStudentsInput);
				} catch (NumberFormatException ex) {
					System.out.println("Please enter a valid number.");
				}
			} while (0 == numberOfStudents);

			grades = new Double[numberOfStudents];
			for (int studentNumber = 0; studentNumber < numberOfStudents; studentNumber++) {
				do {
					System.out.println("Enter the grade for student " + (studentNumber + 1) + ": ");
					try {
						String gradeInput = userInput.next();
						grades[studentNumber] = Double.parseDouble(gradeInput);
					} catch (NumberFormatException e) {
						System.out.println("Please enter a valid grade percentage.");
					}
				} while (null == grades[studentNumber]);
			}
		}

		double sumOfGrades = 0.0;
		for (Double grade : grades) {
			sumOfGrades += grade;
		}
		double average = sumOfGrades / numberOfStudents;

		Double[] copyOfGrades = grades.clone();
		Arrays.sort(copyOfGrades);
		double median;
		if (0 == numberOfStudents % 2) {
			median = (copyOfGrades[numberOfStudents / 2] + copyOfGrades[numberOfStudents / 2 - 1]) / 2;
		} else {
			median = copyOfGrades[numberOfStudents / 2];
		}

		double minimumGrade = copyOfGrades[0];

		double maximumGrade = copyOfGrades[numberOfStudents - 1];

		double sumOfSquaredDeviations = 0.0;
		for (Double grade : grades) {
			sumOfSquaredDeviations += Math.pow((grade - average), 2);
		}
		double standardDeviation = Math.sqrt(sumOfSquaredDeviations / numberOfStudents);


		System.out.println("The grades are: " + Arrays.toString(grades) + ".");
		System.out.println("The average is: " + decimalFormat.format(average) + ".");
		System.out.println("The median is: " + decimalFormat.format(median) + ".");
		System.out.println("The minimum is: " + decimalFormat.format(minimumGrade) + ".");
		System.out.println("The maximum is: " + decimalFormat.format(maximumGrade) + ".");
		System.out.println("The standard deviation is: " + decimalFormat.format(standardDeviation) + ".");
	}
}
