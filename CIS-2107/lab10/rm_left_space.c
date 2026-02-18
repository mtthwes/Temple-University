#include "str2107.h"
#include <ctype.h>

void rm_left_space(char *s) {
    char *p = s;
    while (*p && isspace(*p)) {
        p++;
    }
    while (*p) {
        *s++ = *p++;
    }
    *s = '\0';
}