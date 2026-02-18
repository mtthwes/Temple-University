#include "str2107.h"
#include <ctype.h>

int ends_with_ignore_case(char *s, char *suff) {
    int len_s = 0;
    int len_suff = 0;
    
    char *temp_s = s;
    while (*temp_s++) len_s++;
    
    char *temp_suff = suff;
    while (*temp_suff++) len_suff++;
    
    if (len_suff > len_s) return 0;
    
    s += (len_s - len_suff); 
    
    while (*suff) {
        if (tolower(*s) != tolower(*suff)) return 0;
        s++;
        suff++;
    }
    return 1;
}