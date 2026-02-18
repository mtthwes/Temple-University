#include "str2107.h"

int diff(char *s1, char *s2) {
    int differences = 0;
    while (*s1 && *s2) {
        if (*s1 != *s2) {
            differences++;
        }
        s1++;
        s2++;
    }

    while (*s1) { differences++; s1++; }
    while (*s2) { differences++; s2++; }
    return differences;
}