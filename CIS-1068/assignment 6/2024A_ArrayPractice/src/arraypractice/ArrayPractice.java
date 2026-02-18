package arraypractice;

public class ArrayPractice {
 
	/* sets every item in A[] to initialValue */
  public static void initialize(int A[], int initialValue) {
	  for (int i = 0; i < A.length; i++) {
          A[i] = initialValue;
	  }
  }

  
  /* returns the number of times that x appears in A[] */
  public static int numOccurrences(int A[], int x) {
      int count = 0;
      for (int value : A) {
          if (value == x) {
              count++;
          }
      }
      return count;
  }

  /* replaces all occurrences of x with y */
  public static void replace(int []A, int x, int y) {
	  for (int i = 0; i < A.length; i++) {
          if (A[i] == x) {
              A[i] = y;
          }
	  }
  }

  /* returns the index of the first occurrence of
   * x in A[] or -1 if x doesn't exist in A[] */
  public static int find(int A[], int x) {
	        for (int i = 0; i < A.length; i++) {
	            if (A[i] == x) {
	                return i;
	            }
	        }
	        return -1;
	   
  }

  /* Returns the index of the first occurrence of
   * item within the first n elements of A[] or -1
   * if item is not among the first n elements of A[] */
  public static int findN(int A[], int item, int n) {
	  for (int i = 0; i < Math.min(n, A.length); i++) {
          if (A[i] == item) {
              return i;
          }
      }
      return -1;
  }

  /* returns the index of the last occurrence of
   * x in A[] or -1 if x doesn't exist in A[] */
  public static int findLast(int A[], int x) {
	  for (int i = A.length - 1; i >= 0; i--) {
          if (A[i] == x) {
              return i;
          }
      }
      return -1;
  }

  /* returns the largest item found in A */
  public static int largest(int A[]) {
	  int max = A[0];
      for (int value : A) {
          if (value > max) {
              max = value;
          }
      }
      return max;
  }

  /* returns the index of the largest item found in A */
  public static int indexOfLargest(int A[]) {
	  int maxIndex = 0;
      for (int i = 1; i < A.length; i++) {
          if (A[i] > A[maxIndex]) {
              maxIndex = i;
          }
      }
      return maxIndex;
  }  

  /* returns a reference to a two dimensional array with
   * n rows and n columns with 1s down the top-left to 
   * bottom-right diagonal and with 0s everywhere else
   * 
   * if n <= 0 returns null
   */
  public static int[][] eye(int n) {
	  if (n <= 0) return null;
      int[][] result = new int[n][n];
      for (int i = 0; i < n; i++) {
          result[i][i] = 1; 
      }
      return result;
  }

  /* inserts n into A[] at A[index] shifting all */
  /*  the previous items one place to the right. For example */
  /*  if A is  */
  /*   |---+---+---+---+---+---+---+---+---+---| */
  /*   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | */
  /*   |---+---+---+---+---+---+---+---+---+---| */
  /*   | 5 | 7 | 6 | 9 | 4 | 3 | 0 | 0 | 0 | 0 | */
  /*   |---+---+---+---+---+---+---+---+---+---| */

  /*   and we call insert(A, 15, 1), A then becomes */

  /*   |---+----+---+---+---+---+---+---+---+---| */
  /*   | 0 |  1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | */
  /*   |---+----+---+---+---+---+---+---+---+---| */
  /*   | 5 | 15 | 7 | 6 | 9 | 4 | 3 | 0 | 0 | 0 | */
  /*   |---+----+---+---+---+---+---+---+---+---| */
  /*  the element in A[] that's in the right-most */
  /*    position is removed.                      */
  /*                                              */
  /*  if index < 0 or index >= A.length-1, the method */
  /*                                    does nothing */
  public static void insert(int A[], int n, int index) {
	  if (index < 0 || index >= A.length - 1) return; 
      for (int i = A.length - 1; i > index; i--) {
          A[i] = A[i - 1]; 
      }
      A[index] = n;
  }


  /* returns a new array consisting of all of the
   * elements of A[] */
  public static int[] copy(int A[]) {
	  int[] newArray = new int[A.length];
      for (int i = 0; i < A.length; i++) {
          newArray[i] = A[i];
      }
      return newArray;
  }

  /* Returns a new array consisting of all of the
     first n elements of A[]. If n>A.length, returns a
     new array of size n, with the first A.length elements
     exactly the same as A, and the remaining n-A.length elements
     set to 0. If n<=0, returns null. */
  public static int[] copyN(int A[], int n) {
	  if (n <= 0) return null;
      int[] newArray = new int[n];
      for (int i = 0; i < Math.min(A.length, n); i++) {
          newArray[i] = A[i];
      }
      // Fill remaining space with 0 if n > A.length
      for (int i = A.length; i < n; i++) {
          newArray[i] = 0;
      }
      return newArray;
  }

  /* returns a new array consisting of all of the
   * elements of A[] followed by all of the
   * elements of B[]. For example, if 
   A[] is: {10,20,30} and 
   B[] is: {5, 9, 38}, the method returns the
   array : {10,20,30,5,9,38} */
  public static int[] copyAll(int A[], int B[]) {
	  int[] newArray = new int[A.length + B.length];
      for (int i = 0; i < A.length; i++) {
          newArray[i] = A[i];
      }
      for (int i = 0; i < B.length; i++) {
          newArray[A.length + i] = B[i];
      }
      return newArray;
  }

  /* reverses the order of the elements in A[].
   * For example, if A[] is:
   {10,20,30,40,50}, after the method, A[] would
   be {50,40,30,20,10} */
  public static void reverse(int A[]) {
      int left = 0;
      int right = A.length - 1;
      while (left < right) {
          int temp = A[left];
          A[left] = A[right];
          A[right] = temp;
          left++;
          right--;
      }
  }

  /* Extra credit:
   *
   * Returns a new array consisting of all of the
   * elements of A, but with no duplicates. For example,
   * if A[] is {10,20,5,32,5,10,9,32,8}, the method returns
   * the array {10,20,5,32,9,8} */
  public static int[] uniques(int A[]) {
	  boolean[] seen = new boolean[10000]; // Use a boolean array to track seen values (assuming values range within a known limit)
      int count = 0;
      
      // First pass: count unique elements
      for (int value : A) {
          if (!seen[value]) {
              seen[value] = true;
              count++;
          }
      }
      
      int[] uniqueArray = new int[count];
      int index = 0;

      // Second pass: populate the new array with unique elements
      for (int value : A) {
          if (seen[value]) {
              uniqueArray[index++] = value;
              seen[value] = false; // Mark it as added
          }
      }

      return uniqueArray;
  }
}
