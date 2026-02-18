#include "str2107.h"
#include <ctype.h>
#include <stdio.h>

int is_empty(char *s) {
    if (s == NULL) return 1;
    if (*s == '\0') return 1;
    
    while (*s) {
        if (!isspace(*s)) return 0; 
    }
    return 1;
}