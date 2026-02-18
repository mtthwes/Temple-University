#include "str2107.h"

int find(char *h, char *n) {
    int h_idx = 0;
    if (!h || !n) return -1;
    
    while (*h) {
        char *h_ptr = h;
        char *n_ptr = n;
        
        while (*h_ptr && *n_ptr && (*h_ptr == *n_ptr)) {
            h_ptr++;
            n_ptr++;
        }
        
        if (*n_ptr == '\0') { 
            return h_idx;
        }
        h++;
        h_idx++;
    }
    return -1; 
}