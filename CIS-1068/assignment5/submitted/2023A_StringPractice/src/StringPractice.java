public class StringPractice {
	

  public static boolean isPunct(char c) {
   return c == '\'' || c == ',' || c == '.' || c == ';' || c == ':' || c == '!' || c == '?';

  }

  
  public static int numPunct(String s) {
	  int count = 0;
      for (int i = 0; i < s.length(); i++) {
          if (isPunct(s.charAt(i))) {
              count++;
          }
      }
      return count;
  }
  
  
  public static int numPunct(String s, int startIndex) {
	  int count = 0;
      for (int i = startIndex; i < s.length(); i++) {
          if (isPunct(s.charAt(i))) {
              count++;
          }
      }
      return count;  
      }


  public static int indexOfFirstPunct(String s) {
	  for (int i = 0; i < s.length(); i++) {
          if (isPunct(s.charAt(i))) {
              return i;
          }
      }
      return -1;  
      }

  
  public static int indexOfLastPunct(String s) {
	  for (int i = s.length() - 1; i >= 0; i--) {
          if (isPunct(s.charAt(i))) {
              return i;
          }
      }
      return -1;
      }

  public static String substitute(String s, char oldChar, char newChar) {
	  String result = "";
	    for (int i = 0; i < s.length(); i++) {
	        char currentChar = s.charAt(i);
	        if (currentChar == oldChar) {
	            result += newChar;  
	        } else {
	            result += currentChar;  
	        }
	    }
	    return result;
	    }

 
  public static String substitutePunct(String s) {
	  String result = "";
	  for (int i = 0; i < s.length(); i++) 
          if (isPunct(s.charAt(i))) {
              result += " ";
          } else {
              result += s.charAt(i);
          }
      
      return result;
  }
  
  
  public static String withoutPunct(String s) {
	  String result = "";
	  for (int i = 0; i < s.length(); i++) 
          if (isPunct(s.charAt(i))) {
              result += "";
          } else {
              result += s.charAt(i);
          }
      
      return result;
      }

  
  public static boolean foundIn(String s, char c) {
      return s.indexOf(c) >= 0;
  }


  public static int numPunctAfterNonPunct(String s) {
	  int count = 0;
	 for (int i = 1; i < s.length(); i++) {
          if (!isPunct(s.charAt(i - 1)) && isPunct(s.charAt(i))) {
          count++;
      }
	  }
	 return count;
      }
	 
 
  public static boolean onlyPunct(String s) {
	  for (int i = 0; i < s.length(); i++) {
          if (!isPunct(s.charAt(i))) {
              return false;
          }
      }
      return true;
      }

  
  public static boolean noPunct(String s) {
      return numPunct(s) == 0;
  }
  
      
  public static boolean consecutivePuncts(String s) {
	  for (int i = 1; i < s.length(); i++) {
          if (isPunct(s.charAt(i - 1)) && isPunct(s.charAt(i))) {
              return true;
          }
      }
      return false;
      }
}
