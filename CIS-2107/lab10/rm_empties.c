#include "str2107.h"
#include <stdio.h>

void rm_empties(char **words) {
    char **read = words;
    char **write = words;
    
    while (*read != NULL) {
        int len = 0;
        char *p = *read;
        while(*p++) len++;

        if (len > 0) {
            *write = *read;
            write++;
        }
        read++;
    }
    *write = NULL;
}