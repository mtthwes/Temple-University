#include "str2107.h"
#include <ctype.h>

int all_letters(char *s) {
    if (!s) return 0;
    while (*s) {
        if (!isalpha(*s)) { 
            return 0;
        }
        s++;
    }
    return 1;
}