/* 
Name: Matthew Setiadi
Date: 10/02/2025
Course: CSI2107 
HW#: Lab 05: ““Arrays of Pointers to Functions” 

Objective: To design and implement array of function pointer. 

The purpose of this program is to modify existing code related to examslecting 
grades for individual students in a 2D matrix and determining the minimum, maximum, and each average. 

// Each of the functions has also been modified to allow for a
// menu-driven interface. This interface is an array of pointers to the functions.

*/

#include <stdio.h>
#include <stdlib.h>
void printArray(int students, int exams, int grades[students][exams]);
void minimum(int students, int exams, int grades[students][exams]);
void maximum(int students, int exams, int grades[students][exams]);
void average(int students, int exams, int grades[students][exams]);

int main() {
    int students, exams;

    printf("Enter the number of students: ");
    scanf("%d", &students);
    printf("Enter the number of exams: ");
    scanf("%d", &exams);
    puts("");

    int grades[students][exams];

    for (int i = 0; i < students; i++) {
        for (int j = 0; j < exams; j++) {
            printf("Grade Student %d, Exam %d: ", i + 1, j + 1);
            if (scanf("%d", &grades[i][j]) != 1) {
            printf("Invalid input. Please enter an integer.\n");
            exit(1);
            }
        }
    }

    void (*processGrades[4])(int, int, int[][exams]) = {printArray, minimum, maximum, average};

    int choice;

    do {
        puts("\nEnter a choice:");
        puts("\t0  Print the array of grades");
        puts("\t1  Find the minimum grade");
        puts("\t2  Find the maximum grade");
        puts("\t3  Print the average for each student");
        puts("\t4  End Program");
        printf("\nChoice: ");
        scanf("%d", &choice);

        if (choice >= 0 && choice < 4) {
            processGrades[choice](students, exams, grades);
        }

    } while (choice != 4);
    
    printf("Program ended.\n");

    return 0;
}


void printArray(int students, int exams, int grades[students][exams]){
    puts("\nThe Students Grades are:");
    for (int i = 0; i < students; i++) {
        printf("Student %d: ", i + 1);
        for (int j = 0; j < exams; j++) {
            printf("%5d", grades[i][j]);
        }
        puts("");
    }
    puts("\nGrades in 2D array format:");
    for (int i = 0; i < students; i++) {
        for (int j = 0; j < exams; j++) {
            printf("%-5d", grades[i][j]);
        }
        puts("");
    }
    
}

void minimum(int students, int exams, int grades[students][exams]){
    int lowGrade = grades[0][0];
    for (int i = 0; i < students; i++) {
        for (int j = 0; j < exams; j++) {
            if (grades[i][j] < lowGrade) {
                lowGrade = grades[i][j];
            }
        }
    }
    printf("\nLowest grade in the class is: %d\n", lowGrade);       
}

void maximum(int students, int exams, int grades[students][exams]){
    int highGrade = grades[0][0];
    for (int i = 0; i < students; i++) {
        for (int j = 0; j < exams; j++) {
            if (grades[i][j] > highGrade) {
                highGrade = grades[i][j];
            }
        }
    }
    printf("\nHighest grade in the class is: %d\n", highGrade);
}
void average(int students, int exams, int grades[students][exams]){
    puts("\nStudent's Averages:");
    for (int i = 0; i < students; i++) {
        int total = 0;
        for (int j = 0; j < exams; j++) {
            total += grades[i][j];
        }
        double avg = (double)total / exams;
        printf("\tStudent %d average: %.2f\n", i + 1, avg);
    }
}
