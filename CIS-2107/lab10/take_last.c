#include "str2107.h"

void take_last(char *s, int n) {
    int len = 0;
    char *p = s;
    while (*p++) len++;
    
    if (n >= len) return;
    
    char *start = s + len - n;
    while (*start) {
        *s++ = *start++;
    }
    *s = '\0';
}