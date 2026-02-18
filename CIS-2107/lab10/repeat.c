#include "str2107.h"
#include <stdlib.h>

char *repeat(char *s, int x, char sep) {
    if (s == NULL) return NULL;
    
    int len = 0;
    char *p = s;
    while(*p++) len++;
    
    int total_len = (len * x) + (x - 1);
    if (x <= 0) total_len = 0;

    char *result = malloc((total_len + 1) * sizeof(char));
    if (!result) return NULL;
    
    char *curr = result;
    for (int i = 0; i < x; i++) {
        char *src = s;
        while (*src) {
            *curr++ = *src++;
        }
        if (i < x - 1) {
            *curr++ = sep;
        }
    }
    *curr = '\0';
    return result;
}