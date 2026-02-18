#include "str2107.h"
#include <stdio.h> 

char *ptr_to(char *h, char *n) {
    if (!h || !n) return NULL;
    
    while (*h) {
        char *h_ptr = h;
        char *n_ptr = n;
        
        while (*h_ptr && *n_ptr && (*h_ptr == *n_ptr)) {
            h_ptr++;
            n_ptr++;
        }
        if (*n_ptr == '\0') return h;
        h++;
    }
    return NULL;
}