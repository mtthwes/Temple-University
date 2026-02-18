#include "str2107.h"
#include <ctype.h>

void rm_right_space(char *s) {
    int len = 0;
    char *p = s;
    while (*p++) len++;
    
    p = s + len - 1;
    while (p >= s && isspace(*p)) {
        *p = '\0'; 
        p--;
    }
}  