/*
 * Name:	Matthew Setiadi
 * Section:	003 
 * Lab:  	CIS2107_Lab08_Manual 
 * Goal: 	To design and implement functions taking pointers as arguments 
			to process characters and strings.
 */


#include <stdio.h>
#include <memory.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

//functions prototypes
void * upperLower(const char * s);
int convertStrtoInt(const char *s1, const char *s2, const char *s3, const char *s4);
float convertStrtoFloat(const char *s1, const char *s2, const char *s3, const char *s4);
void compareStr(const char *s1, const char *s2);
void comparePartialStr(const char *s1, const char *s2, int n);
void randomize(void);
int tokenizeTelNum(char *num);
void reverse(char *text);
int countSubstr (char * line, char * sub);
int countChar (char * line, char c);
int countWords(char *string);
void countAlpha(char *string);
void startsWithB(char *string[]);
void endsWithed(char *string[]);

int main() {

    //random generator
    srand(time(NULL));

    //test for upperLower
    const char test[] = "This iS A Test";
    upperLower(test);

    //test for convertStrtoInt
    printf("\n\nThe sum of 4 strings is: %d", convertStrtoInt("3", "4", "5", "6"));

    //test for convertStrtoFloat
    printf("\n\nThe sum of 4 strings is: %.2f", convertStrtoFloat("3.5", "4.5", "5.5", "6.5"));

    //test for compareStr
    compareStr("Test1", "Test2");

    //test for comparePartialStr
    comparePartialStr("Test1", "Test2", 4);

    //test for randomize
    randomize();

    //test for tokenize number
    char str[] = "(267) 436-6281";
    tokenizeTelNum(str);

    //test for reverse
    char line[] = "Hello world";
    reverse(line);

    //test for countSubstr
    char *line1 = "helloworldworld";
    char *substring = "world";
    printf("\n\nNumber of Substrings %s inside %s: %d\n", substring, line1, countSubstr(line1, substring));

    //test for countChar
    char w = 'w';
    printf("\nNumber of character %c inside %s: %d\n", w, line1, countChar(line1, w));

    //test for countAlpha
    char str1[] = "Hello it's me.";
    countAlpha(str1);

    //test for countWords
    char countstring[] = "hello world!";
    printf("\n\nNumber of words in string is: %d\n", countWords(countstring));

    //test for startsWithB
    char *series[] = {"bored", "hello", "Brother", "manual", "bothered", NULL};
    startsWithB(series);

    //test for endsWithed
    endsWithed(series);

}

// 1.(Displaying Strings in Uppercase and Lowercase) 
void * upperLower (const char * s) {
    printf("Uppercase: ");
    for(int i = 0; s[i] != '\0'; i++) {
        printf("%c", toupper(s[i]));
    }
    printf("\n");

    printf("Lowercase: ");
    for(int i=0; s[i] != '\0'; i++) {
        printf("%c", tolower(s[i]));
    }
    printf("\n");
    
    return NULL;
}

// 2.(Converting Strings to Integers for Calculations) 
int convertStrtoInt(const char *s1, const char *s2, const char *s3, const char *s4) {
    
    int i1 = atoi(s1);
    int i2 = atoi(s2);
    int i3 = atoi(s3);
    int i4 = atoi(s4);
    
    return i1 + i2 + i3 + i4;
}

//3.(Converting Strings to Floating Point for Calculations) 
float convertStrtoFloat(const char *s1, const char *s2, const char *s3, const char *s4) {
   
    double f1 = atof(s1);
    double f2 = atof(s2);
    double f3 = atof(s3);
    double f4 = atof(s4);
    
    return (float)(f1 + f2 + f3 + f4);
}

//4.(Comparing Strings) 
void compareStr(const char *s1, const char *s2) {
    
    printf("\n\n\nComparing '%s' and '%s':\n", s1, s2);
    int result = strcmp(s1, s2);
    
    if (result==0) {
        printf("Strings are equal\n");
    } else if (result<0) {
        printf("'%s' is less than '%s'\n", s1, s2);
    } else {
        printf("'%s' is greater than '%s'\n", s1, s2);
    }

}

//5.(Comparing Portions of Strings) 
void comparePartialStr(const char *s1, const char *s2, int n) {
   
    printf("\nComparing first %d characters of '%s' and '%s':\n", n, s1, s2);
    int result = strncmp(s1, s2, n);
    
    if (result==0) {
        printf("The first %d characters are equal.\n", n);
    } else if (result<0) {
        printf("'%s' is less than '%s'.\n", s1, s2);
    } else {
        printf("'%s' is greater than '%s'.\n", s1, s2);
    }

}

//6.(Random Sentences) 
void randomize(void) {
   
   const char *article[] = {"the", "a", "one", "some", "any"};
   const char *noun[] = {"boy", "girl", "dog", "town", "car"};
   const char *verb[] = {"drove", "jumped", "ran", "walked", "skipped"};
   const char *preposition[] = {"to", "from", "over", "under", "on"};

   int numSentences = 20; 
   char sentence[200];
   
   printf("\n\n20 Random Sentences\n\n");
   
   for (int i=0; i<numSentences; i++) {

       const char *art1 = article[rand() % 5];
       const char *noun1 = noun[rand() % 5];
       const char *verb1 = verb[rand() % 5];
       const char *prep1 = preposition[rand() % 5];
       const char *art2 = article[rand() % 5];
       const char *noun2 = noun[rand() % 5];
       
       strcpy(sentence, art1);
       strcat(sentence, " ");
       strcat(sentence, noun1);
       strcat(sentence, " ");
       strcat(sentence, verb1);
       strcat(sentence, " ");
       strcat(sentence, prep1);
       strcat(sentence, " ");
       strcat(sentence, art2);
       strcat(sentence, " ");
       strcat(sentence, noun2);
       
       sentence[0] = toupper(sentence[0]);
       printf("%s.\n", sentence);
   }
}

//7.(Tokenizing Telephone Numbers) 
int tokenizeTelNum(char *num) {
  
    char numCopy[30];
    strcpy(numCopy, num);
    
    printf("\n\nTokenizing Phone Number: %s\n", num);
    
    const char *delims = "() -";
    char *areaCode = strtok(numCopy, delims);
    char *firstThree = strtok(NULL, delims);
    char *lastFour = strtok(NULL, delims);
    
    if (areaCode && firstThree && lastFour) {
        char sevenDigit[8];
        strcpy(sevenDigit, firstThree);
        strcat(sevenDigit, lastFour);
        
        int areaCodeInt = atoi(areaCode);
        long phoneNumLong = atol(sevenDigit);
        
        printf("Area Code (int): %d\n", areaCodeInt);
        printf("Phone Number (long): %ld\n", phoneNumLong);
        
    } else {
        printf("Invalid phone number format.\n");
    }
    
    return 0; 
}

//8.(Displaying a Sentence with Its Words Reversed) 
void reverse(char *text) {
    
    char *words[50];
    int wordCount = 0;
    
    char buffer[1000]; 
    if (strlen(text)+1 > 1000) {
        printf("String too long for reverse buffer.\n");
        return;
    }
    strcpy(buffer, text);
    
    const char *delims = " \t\n"; 

    char *token = strtok(buffer, delims);
    while (token != NULL && wordCount < 50) {
        words[wordCount] = token;
        wordCount++;
       token = strtok(NULL, delims);
    }
    
    printf("\nReversed sentence: ");
    for (int i = wordCount-1; i >= 0; i--) {
        printf("%s ", words[i]);
    }
    printf("\n");

}

//9.(Counting the Occurrences of a Substring) 
int countSubstr (char * line, char * sub) {
    
    int count = 0;
    char *ptr = line; 
    int subLen = strlen(sub);
    
    if (subLen == 0) return 0; 
    
    while ((ptr = strstr(ptr, sub)) != NULL) {
        count++;
       ptr += subLen; 
    }
    
    return count;
  
}

//10.(Counting the Occurrences of a Character) 
int countChar (char *line, char c) {
    
    int count = 0;
    char *ptr = line; 

    while ((ptr = strchr(ptr, c)) != NULL) {
        count++;
        ptr++; 
    }
    
    return count;
}


//11.(Counting the Letters of the Alphabet in a String) 
void countAlpha(char *string) {
int alphaCounts[26] = {0};
    
    printf("\n\nAlphabet counts for: '%s'\n", string);
    
    for (int i = 0; i<26; i++) {
        char lower = 'a' + i;
        char upper = 'A' + i;
        
        alphaCounts[i] = countChar(string, lower) + countChar(string, upper);
    }
    
    printf("Letter Counts: \n");
    for (int i=0; i<26; i++) {
        if (alphaCounts[i]>0) {
            printf("%c/%c: %d\n", 'A' + i, 'a' + i, alphaCounts[i]);
        }
    }
 
}

//12.(Counting the Number of Words in a String) 
int countWords(char *string) {
   
    int count = 0;
    char buffer[1000]; 
    if (strlen(string)+1>1000) {
        printf("String too long for countWords buffer.\n");
        return 0;
    }
    strcpy(buffer, string);

    const char *delims = " \n";
    
    char *token = strtok(buffer, delims);
    while (token != NULL) {
        count++;
        token = strtok(NULL, delims);
    }
    
    return count;
}

//13.(Strings Starting with "b") 
void startsWithB(char *string[]) {

    printf("\n\nStrings starting with 'b' or 'B':\n");
    
    for (int i=0; string[i] != NULL; i++) {
        if (string[i][0] == 'b' || string[i][0] == 'B') {
            printf("%s\n", string[i]);
        }
    }

}

//14.(Strings Ending with "ed") 
void endsWithed(char *string[]) {
    printf("\n\nStrings ending with 'ed':\n");
    
    for (int i=0; string[i] != NULL; i++) {
        int len = strlen(string[i]);
        
        if (len>=2) {
            if (strcmp(&string[i][len-2], "ed") == 0) {
                printf("%s\n", string[i]);
            }
        }
    }

}