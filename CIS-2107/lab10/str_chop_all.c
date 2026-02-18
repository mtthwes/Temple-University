#include "str2107.h"
#include <stdlib.h>

char **str_chop_all(char *s, char c) {
    int tokens = 1;
    char *p = s;
    while (*p) {
        if (*p == c) tokens++;
        p++;
    }
    
    char **result = malloc((tokens + 1) * sizeof(char *));
    if (!result) return NULL;
    
    int token_idx = 0;
    char *start = s;
    p = s;
    
    while (1) {
        if (*p == c || *p == '\0') {
            int len = p - start;
            result[token_idx] = malloc((len + 1) * sizeof(char));
            
            for (int i = 0; i < len; i++) {
                result[token_idx][i] = start[i];
            }
            result[token_idx][len] = '\0';
            
            token_idx++;
            if (*p == '\0') break;
            start = p + 1;
        }
        p++;
    }
    result[token_idx] = NULL;
    return result;
}