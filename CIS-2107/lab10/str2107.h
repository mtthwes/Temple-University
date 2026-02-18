#ifndef STR2107_H
#define STR2107_H

int all_letters(char *s);
int num_in_range(char *s1, char b, char t);
int diff(char *s1, char *s2);
int len_diff(char *s1, char *s2);
int find(char *h, char *n);
char *ptr_to(char *h, char *n);
int is_empty(char *s);
int strcmp_ign_case(char *s1, char *s2);
int ends_with_ignore_case(char *s, char *suff);

void shorten(char *s, int new_len);
void rm_left_space(char *s);
void rm_right_space(char *s);
void rm_space(char *s);
void capitalize(char *s);
void take_last(char *s, int n);
void rm_empties(char **words);

char *str_zip(char *s1, char *s2);
char *dedup(char *s);
char *pad(char *s, int d);
char *repeat(char *s, int x, char sep);
char *replace(char *s, char *pat, char *rep);
char *str_connect(char **strs, int n, char c);
char **str_chop_all(char *s, char c);

#endif