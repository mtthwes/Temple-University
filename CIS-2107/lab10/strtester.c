#include <stdio.h>
#include <stdlib.h>
#include "str2107.h"

int main() {
    printf("CIS2107 String Library Tester\n\n");

    char *t1 = "Hello";
    char *t2 = "He11o";

    printf("all_letters(\"%s\"): %d\n", t1, all_letters(t1));
    printf("all_letters(\"%s\"): %d\n", t2, all_letters(t2));

    printf("num_in_range(\"bobby\", 'a', 'h'): %d\n",
        num_in_range("bobby", 'a', 'h'));

    printf("diff(\"book\", \"back\"): %d\n",
        diff("book", "back"));

    char short_test[] = "Hello World";
    shorten(short_test, 5);
    printf("shorten(\"Hello World\", 5): \"%s\"\n", short_test);

    printf("len_diff(\"Philadelphia\", \"Hello\"): %d\n",
        len_diff("Philadelphia", "Hello"));

    char space_test[] = "   Hello   ";
    rm_space(space_test);
    printf("rm_space(\"   Hello   \"): \"%s\"\n", space_test);

    printf("find(\"Hello\", \"l\"): %d\n", find("Hello", "l"));
    printf("find(\"Hello\", \"q\"): %d\n", find("Hello", "q"));

    char *zipped = str_zip("Spongebob", "Patrick");
    printf("str_zip: %s\n", zipped);
    free(zipped);

    char cap_test[] = "hello world";
    capitalize(cap_test);
    printf("capitalize: \"%s\"\n", cap_test);

    char *deduped = dedup("There's always a wheel in the car show.");
    printf("dedup: \"%s\"\n", deduped);
    free(deduped);

    char *padded = pad("test", 6);
    printf("pad(\"test\", 6): \"%s\"\n", padded);
    free(padded);

    char *replaced = replace("Eagles X", "X", "Fly Eagles Fly");
    printf("replace: \"%s\"\n", replaced);
    free(replaced);

    char *strs[] = {"Washington", "Adams", "Jefferson"};
    char *connected = str_connect(strs, 3, '+');
    printf("str_connect: \"%s\"\n", connected);
    free(connected);

    char *chop_str = "I/am/ready";
    char **chopped = str_chop_all(chop_str, '/');
    printf("str_chop_all(\"I/am/ready\"):\n");

    for (int i = 0; chopped[i] != NULL; i++) {
        printf("Token %d: %s\n", i, chopped[i]);
        free(chopped[i]);
    }
    free(chopped);

    return 0;
}
