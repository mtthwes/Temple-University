#include "str2107.h"

void shorten(char *s, int new_len) {
    int len = 0;
    char *temp = s;
    while (*temp++) len++;
    
    if (len <= new_len) return;
    
    s[new_len] = '\0'; 
}
