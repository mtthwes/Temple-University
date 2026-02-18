#include "str2107.h"
#include <stdlib.h>

char *dedup(char *s) {
    int len = 0;
    char *p = s;
    while(*p++) len++;
    
    char *result = malloc((len + 1) * sizeof(char));
    if (!result) return NULL;
    
    int k = 0;
    for (int i = 0; s[i] != '\0'; i++) {
        int found = 0;

        for (int j = 0; j < k; j++) {
            if (result[j] == s[i]) {
                found = 1;
                break;
            }
        }
        if (!found) {
            result[k++] = s[i];
        }
    }
    result[k] = '\0';
    return result;
}