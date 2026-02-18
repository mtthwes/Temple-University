import java.util.*;

public class ArrayListMethods {

    /**
     * Returns true if all the items in the supplied list are unique.
     *
     * @param <E>  the type of the list elements
     * @param list the input list
     * @return true if the list has no duplicate elements; false otherwise
     * @throws IllegalArgumentException if list is null
     */
    public static <E> boolean unique(List<E> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).equals(list.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a new List of integers containing all the integers from the input list
     * that are multiples of the given integer.
     *
     * @param list     the input list of integers
     * @param multiple the number that elements should be a multiple of
     * @return a new list containing only the multiples of the given number
     * @throws IllegalArgumentException if list is null or multiple is zero
     */
    public static List<Integer> allMultiples(List<Integer> list, int multiple) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null.");
        }
        if (multiple == 0) {
            throw new IllegalArgumentException("Multiple cannot be zero (division by zero error).");
        }
        List<Integer> result = new ArrayList<>();
        for (Integer num : list) {
            if (num % multiple == 0) {
                result.add(num);
            }
        }
        return result;
    }

    /**
     * Returns a new List of Strings that contains all the Strings from the input list
     * that are exactly the specified length.
     *
     * @param list   the input list of strings
     * @param length the desired length of strings to include
     * @return a new list containing only the strings of the given length
     * @throws IllegalArgumentException if list is null
     */
    public static List<String> allStringsOfSize(List<String> list, int length) {
        List<String> result = new ArrayList<>();
        for (String str : list) {
            if (str.length() == length) {
                result.add(str);
            }
        }
        return result;
    }

    /**
     * Returns true if the two lists are permutations of each other.
     * Two lists are permutations if they contain the same elements in the same counts,
     * regardless of the order.
     *
     * @param <E>   the type of the list elements
     * @param list1 the first list
     * @param list2 the second list
     * @return true if list1 and list2 are permutations of each other; false otherwise
     * @throws IllegalArgumentException if either list is null
     */
    public static <E extends Comparable<E>> boolean isPermutation(List<E> list1, List<E> list2) {
        if (list1 == null || list2 == null) {
            throw new IllegalArgumentException("Lists cannot be null.");
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        // Sort both lists
        List<E> sortedList1 = new ArrayList<>(list1);
        List<E> sortedList2 = new ArrayList<>(list2);
        Collections.sort(sortedList1);
        Collections.sort(sortedList2);

        return sortedList1.equals(sortedList2);
    }

    /**
     * Splits the input string into a list of words using whitespace as the delimiter.
     *
     * @param input the input string to tokenize
     * @return a list of words extracted from the input string
     */
    public static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            return tokens;
        }
        // Split the string on one or more whitespace characters.
        String[] words = input.split("\\s+");
        for (String word : words) {
            tokens.add("\"" + word + "\"");
        }
        return tokens;
    }

    // Challenge
    public static List<String> sanitize(String input) {
        List<String> tokens = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            return tokens;
        }
        String[] words = input.split("\\s+");
        for (String word : words) {
            // Remove punctuation at the beginning and end of the word.
            String sanitized = word.replaceAll("^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");
            if (!sanitized.isEmpty()) {
                tokens.add("\"" + sanitized + "\"");
            }
        }
        return tokens;
    }

    /**
     * Removes all occurrences of the specified item from the provided list.
     *
     * @param <E>  the type of the list elements
     * @param list the list from which items are to be removed
     * @param item the item to remove
     * @return the list after all occurrences of the item have been removed
     * @throws IllegalArgumentException if list is null
     */
    public static <E> List<E> removeAll(List<E> list, E item) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null.");
        }
        // removeIf uses the predicate to remove elements that match the condition.
        list.removeIf(element -> (Objects.equals(item, element)));
        return list;
    }

    // main method to test each of the methods.
    public static void main(String[] args) {
        // Test unique()
        List<Integer> listWithDuplicates = new ArrayList<>();
        listWithDuplicates.add(1);
        listWithDuplicates.add(2);
        listWithDuplicates.add(3);
        listWithDuplicates.add(2);
        listWithDuplicates.add(2);
        System.out.println("unique (should be false): " + unique(listWithDuplicates));

        List<Integer> uniqueList = new ArrayList<>();
        uniqueList.add(1);
        uniqueList.add(2);
        uniqueList.add(3);
        uniqueList.add(8);
        uniqueList.add(9);
        System.out.println("unique (should be true): " + unique(uniqueList));

        // Test allMultiples()
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(25);
        numbers.add(2);
        numbers.add(5);
        numbers.add(30);
        numbers.add(19);
        numbers.add(57);
        numbers.add(570);
        numbers.add(570);
        numbers.add(2);
        numbers.add(25);
        System.out.println("allMultiples for 5: " + allMultiples(numbers, 5)); // Expected: [25, 5, 30, 25]

        // Test allStringsOfSize()
        List<String> words = new ArrayList<>();
        words.add("I");
        words.add("like");
        words.add("to");
        words.add("eat");
        words.add("eat");
        words.add("eat");
        words.add("apples");
        words.add("apples");
        words.add("and");
        words.add("bananas");
        System.out.println("allStringsOfSize (length 6): " + allStringsOfSize(words, 6)); // Expected: [eat, eat, eat, and]

        // Test isPermutation()
        List<Integer> perm1 = new ArrayList<>();
        perm1.add(1);
        perm1.add(2);
        perm1.add(4);
        List<Integer> perm2 = new ArrayList<>();
        perm2.add(2);
        perm2.add(1);
        perm2.add(4);
        System.out.println("isPermutation (should be true): " + isPermutation(perm1, perm2));

        // Modify perm2 and test again.
        perm2.add(4);
        System.out.println("isPermutation (should be false): " + isPermutation(perm1, perm2));

        // Test tokenize()
        String sentence1 = "Hello, world!";
        System.out.println("tokenize: " + tokenize(sentence1)); // Expected: ["Hello,", "world!"]

        // Test sanitize()
        String sentence2 = "Hello, world!";
        System.out.println("sanitize: " + sanitize(sentence2)); // Expected: ["Hello", "world"]

        // Test removeAll()
        List<Integer> listToModify = new ArrayList<>();
        listToModify.add(1);
        listToModify.add(4);
        listToModify.add(5);
        listToModify.add(6);
        listToModify.add(7);
        listToModify.add(8);
        listToModify.add(5);
        listToModify.add(5);
        listToModify.add(2);
        listToModify.add(2);
        listToModify.add(12);
        listToModify.add(8);
        System.out.println("Initial List: " + listToModify); // Expected: [1, 4, 6, 2]
        System.out.println("removeAll (removing 8): " + removeAll(listToModify, 8)); // Expected: [1, 4, 6, 2]
    }
}
