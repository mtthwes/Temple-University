
// Matthew Setiadi
// Assignment 3
// This program calculates the overall course grade based on the weighted average of homework and two exams.


import java.util.Scanner;


public class Calculator {
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("This program accepts your homework and two exam scores as input and computes your grade in the course.");
        System.out.println();
     

        // Input weights for homework and Exam 1 
      
		System.out.print("Homework Weight: ");
		int HwWeight = input.nextInt();
        System.out.println();

		System.out.print("Exam 1 Weight: ");
		int Ex1weight = input.nextInt();
        System.out.println();
        
        // Find Exam 2 weight 
		int Ex2weight = 100-HwWeight-Ex1weight;
		
		System.out.println("Using weights of " + HwWeight + " " + Ex1weight + " " + Ex2weight);
        System.out.println();
		
        // HW calculation
        System.out.println("Homework: ");
        System.out.println();

        System.out.print("Number of Assignments? ");
        int numAssignment = input.nextInt();
        if (numAssignment <=0) numAssignment = 1;
        System.out.println();

        System.out.print("Average Homework grade? ");
        double avgHw = input.nextDouble();
        if (avgHw <0) avgHw = 0;
        System.out.println();

        System.out.print("Number of late days used? ");
        int numLate = input.nextInt();
        System.out.println();

        System.out.print("Number of labs attended? ");
        int labs = input.nextInt();
        System.out.println();

        // HW max points 
        int maxAssignmentScore = numAssignment * 10;
        int maxLabs = numAssignment * 4;
        
        		
        
        // Adjust for late days
        if(numLate > numAssignment/2) {
        	avgHw *= 0.9;
        }
        	else if(numLate==0 && avgHw<10) {
        		avgHw +=5;
        }
        
        // HW Total points
        double totalHWpoints = ((avgHw * numAssignment) + (labs*4));
        totalHWpoints = Math.min(totalHWpoints, maxAssignmentScore+maxLabs); 
        //use math.min so it limits the maximum to total points
        
        
        // HW weighted score
        double HWScore = HwWeight * (totalHWpoints / (maxAssignmentScore+maxLabs));
        System.out.println("Total points = " + totalHWpoints + " / " + (maxAssignmentScore + maxLabs));
        System.out.println();
        System.out.println("Weighted score = " + HWScore);
        System.out.println();

        // Exam 1 calculation
        System.out.print("Exam 1: ");
        double Ex1score = input.nextInt();
        if(Ex1score < 0) Ex1score=0;
        System.out.println();
        
        System.out.print("Curve? ");
        double curvEx1 = input.nextInt();
        Ex1score = Math.min(Ex1score + curvEx1, 100);
        System.out.println();
        
        double Ex1Final = Ex1weight * Ex1score/100;
        System.out.println("Total points = " + Ex1score + " / 100");
        System.out.println();
        System.out.println("Weighted score = " + Ex1Final);
        System.out.println();

        
        // Exam 2 calculation
        System.out.print("Exam 2: ");
        double Ex2score = input.nextInt();
        if(Ex2score < 0) Ex2score=0;
        System.out.println();
        
        System.out.print("Curve? ");
        double curvEx2 = input.nextInt();
        Ex2score = Math.min(Ex2score + curvEx2, 100);
        System.out.println();
        
        double Ex2Final = Ex2weight * Ex2score/100;
        System.out.println("Total points = " + Ex2score + " / 100");
        System.out.println();
        System.out.println("Weighted score = " + Ex2Final);
        System.out.println();

        
        // Final course grade calculation
        double courseGrade = HWScore + Ex1Final + Ex2Final;
        System.out.println("Course grade = " + courseGrade);
        
        input.close();
		
	    }


	}