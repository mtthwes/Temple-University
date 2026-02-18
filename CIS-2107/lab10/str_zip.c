#include "str2107.h"
#include <stdlib.h>

char *str_zip(char *s1, char *s2) {
    int l1 = 0, l2 = 0;
    char *p;
    
    p = s1; while(*p++) l1++;
    p = s2; while(*p++) l2++;
    
    char *result = malloc((l1 + l2 + 1) * sizeof(char));
    if (!result) return NULL;
    
    int i = 0, j = 0, k = 0;
    while (i < l1 || j < l2) {
        if (i < l1) result[k++] = s1[i++];
        if (j < l2) result[k++] = s2[j++];
    }
    result[k] = '\0';
    return result;
}