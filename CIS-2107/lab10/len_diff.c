#include "str2107.h"

int len_diff(char *s1, char *s2) {
    int l1 = 0, l2 = 0;
    while (*s1++) l1++;
    while (*s2++) l2++;
    return l1 - l2;
}