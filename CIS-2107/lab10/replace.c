#include "str2107.h"
#include <stdlib.h>

char *replace(char *s, char *pat, char *rep) {
    int s_len = 0, pat_len = 0, rep_len = 0;
    char *p;
    
    p = s; while(*p++) s_len++;
    p = pat; while(*p++) pat_len++;
    p = rep; while(*p++) rep_len++;
    
    int count = 0;
    char *temp = s;
    while (*temp) {
        char *h = temp;
        char *n = pat;
        while (*h && *n && *h == *n) { h++; n++; }
        if (*n == '\0') {
            count++;
            temp += pat_len; 
        } else {
            temp++;
        }
    }
    
    int new_len = s_len - (count * pat_len) + (count * rep_len);
    char *result = malloc((new_len + 1) * sizeof(char));
    if (!result) return NULL;
    
    char *dest = result;
    char *src = s;
    
    while (*src) {
        char *h = src;
        char *n = pat;
        while (*h && *n && *h == *n) { h++; n++; }
        
        if (*n == '\0') {
            char *r = rep;
            while (*r) *dest++ = *r++;
            src += pat_len;
        } else {
            *dest++ = *src++;
        }
    }
    *dest = '\0';
    return result;
}