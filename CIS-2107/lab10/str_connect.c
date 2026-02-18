#include "str2107.h"
#include <stdlib.h>

char *str_connect(char **strs, int n, char c) {
    int total_len = 0;
    
    for (int i = 0; i < n; i++) {
        char *p = strs[i];
        while (*p++) total_len++;
    }
    if (n > 1) total_len += (n - 1);
    
    char *result = malloc((total_len + 1) * sizeof(char));
    if (!result) return NULL;
    
    char *dest = result;
    for (int i = 0; i < n; i++) {
        char *src = strs[i];
        while (*src) *dest++ = *src++;
        
        if (i < n - 1) *dest++ = c;
    }
    *dest = '\0';
    return result;
}