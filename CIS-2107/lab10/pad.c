#include "str2107.h"
#include <stdlib.h>

char *pad(char *s, int d) {
    if (s == NULL) return NULL;
    
    int len = 0;
    char *p = s;
    while(*p++) len++;
    
    int remainder = len % d;
    int needed = (remainder == 0) ? 0 : (d - remainder);
    int new_total = len + needed;
    
    char *result = malloc((new_total + 1) * sizeof(char));
    if (!result) return NULL;
    
    int i;
    for (i = 0; i < len; i++) result[i] = s[i];
    for (; i < new_total; i++) result[i] = ' '; 
    result[new_total] = '\0';
    
    return result;
}