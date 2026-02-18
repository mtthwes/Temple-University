/*
* Name: Matthew Setiadi
* Date: 09/28/2025
* Course: CIS2107
* Lab: Lab 05 – Processing 2D Arrays
* Problem: Implement functions to process 2D arrays including finding max, row sums,
* column sums, checking if square, and displaying results.
*/

#include <stdio.h>

int max(int rows, int cols, int arr[rows][cols]);
int rowSum(int rows, int cols, int arr[rows][cols], int rowIndex);
int columnSum(int rows, int cols, int arr[rows][cols], int colIndex);
int isSquare(int rows, int cols);
void displayOutputs(int rows, int cols, int arr[rows][cols]);

int main() {
    int rows, cols; 

    puts("Let's create a 2Dim array!\n");
    printf("How many rows? ");
    scanf("%d", &rows);
    printf("How many columns? ");
    scanf("%d", &cols);
    puts("");

    int arr[rows][cols];

    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++) {
            printf("     enter [%d][%d]: ", i, j);
            scanf("%d", &arr[i][j]);
        }

    printf("\nThe maximum value in the array is %d\n\n", max(rows, cols, arr));

    for (int i = 0; i < rows; i++)
        printf("Sum of row %d = %d\n", i + 1, rowSum(rows, cols, arr, i));

    puts(""); 

    for (int j = 0; j < cols; j++)
        printf("Sum of column %d = %d\n", j + 1, columnSum(rows, cols, arr, j));

    if (isSquare(rows, cols))
        printf("\nThis is a square array.\n");
    else
        printf("\nThis is not a square array.\n");

    printf("\nHere is your 2Dim array:\n");
    displayOutputs(rows, cols, arr);

    return 0;
}


int max(int rows, int cols, int arr[rows][cols]) {
    int maxVal = arr[0][0];
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
            if (arr[i][j] > maxVal) maxVal = arr[i][j];
    return maxVal;
}

int rowSum(int rows, int cols, int arr[rows][cols], int rowIndex) {
    int sum = 0;
    for (int j = 0; j < cols; j++) sum += arr[rowIndex][j];
    return sum;
}

int columnSum(int rows, int cols, int arr[rows][cols], int colIndex) {
    int sum = 0;
    for (int i = 0; i < rows; i++) sum += arr[i][colIndex];
    return sum;
}

int isSquare(int rows, int cols) {
    return rows == cols;
}

void displayOutputs(int rows, int cols, int arr[rows][cols]) {
    for (int i = 0; i < rows; i++) {
        printf("[");
        for (int j = 0; j < cols; j++) {
            printf("%2d", arr[i][j]);
            if (j < cols - 1) printf(", ");
        }
        printf("]\n");
    }
}