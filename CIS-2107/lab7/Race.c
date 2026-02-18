//Name: Matthew Setiadi
//Date: 10/02/2025
//CIS2017 Lab7: Race
//The purpose of this program is to simulate and display the race between the tortoise and the hare using a random number
//generator and preset move types. The animal positions are accessed and modified using pointers, and the display is
//controlled using the sleep() function. Sometimes it takes a while due to slippage.


#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>

#define INT_MIN 1
#define INT_MAX 10

int randomNumberGenerator();
void tortMove(int *tPtr);
void hareMove(int *hPtr);
void printRace(int hPos,int tPos);
int min(int hMin, int tMin);

int main(){
    srand((unsigned)time(NULL));

    int hPos =1;						//hare start position is 1, cannot slip past 1
    int tPos = 1;						//tortoise start position is 1, cannot slip past 1

    puts("\tON YOUR MARK, GET SET");
    puts("\tBANG               !!!!");	//the spacing is modeled after the sample
    puts("\tAND THEY'RE OFF    !!!!");	//the spacing is modeled after the sample

    while(hPos != 70 && tPos!=70){
        //sleep(1);						//slows down the race
        hareMove(&hPos);
        tortMove(&tPos);
        printRace(hPos,tPos);
        puts("");
    }

    if (tPos >= 70 && hPos >= 70) {
        puts("It's a tie.");
    } else if (tPos >= 70) {
        puts("TORTOISE WINS!!! YAY!!!");
    } else {
        puts("Hare wins. Yuch.");
    }
}

//Finds minimum of hare or tortoise position to decide which position to print first
int min(int hMin, int tMin){
    return (hMin < tMin) ? hMin : tMin;
}

//Prints the position of the H and T
void printRace(int hPos,int tPos){
    int first = min(hPos, tPos);  

    for (int i = 1; i < first; i++) {
        printf(" ");
    }

    if (hPos == tPos) {
        printf("OUCH!!!");
    } 
    else if (hPos > tPos) {
        printf("T");
        for (int i = 0; i < (hPos - tPos - 1); i++) {
            printf(" ");
        }
        printf("H");
    } 
    else {
        printf("H");
        for (int i = 0; i < (tPos - hPos - 1); i++) {
            printf(" ");
        }
        printf("T");
    }
}

//Controls the tortoise movement
void tortMove(int *tPtr){
    int i = randomNumberGenerator();

    if (i >= 1 && i <= 5) {       
        *tPtr += 3;
    } else if (i >= 6 && i <= 7) { 
        *tPtr -= 6;
    } else {                     
        *tPtr += 1;
    }

    if (*tPtr < 1) {
        *tPtr = 1;
    }
}

//Controls the hare movement
void hareMove(int *hPtr){
    int i = randomNumberGenerator();

    if (i >= 1 && i <= 2) {         
    } else if (i >= 3 && i <= 4) {  
        *hPtr += 9;
    } else if (i == 5) {            
        *hPtr -= 12;
    } else if (i >= 6 && i <= 8) {  
        *hPtr += 1;
    } else {                       
        *hPtr -= 2;
    }
    if (*hPtr < 1) {
        *hPtr = 1;
    }
}

//Generates random number from 1-10
int randomNumberGenerator(){
    return (rand() % INT_MAX) + INT_MIN;
}
