#include "str2107.h"
#include <ctype.h>

void capitalize(char *s) {
    int new_word = 1;
    while (*s) {
        if (isspace(*s)) {
            new_word = 1;
        } else if (new_word) {
            *s = toupper(*s);
            new_word = 0;
        } else {
            *s = tolower(*s);
        }
        s++;
    }
}