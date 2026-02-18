import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringPracticeTest extends StringPractice {
  @Test
  void testIsPunct() {
    assertTrue(StringPractice.isPunct('.'));
    assertTrue(StringPractice.isPunct(';'));
    assertTrue(StringPractice.isPunct('?'));
    assertTrue(StringPractice.isPunct('\''));
    assertTrue(StringPractice.isPunct(','));
    assertTrue(StringPractice.isPunct(':'));
    assertTrue(StringPractice.isPunct('!'));
    assertFalse(StringPractice.isPunct(' '));
    assertFalse(StringPractice.isPunct('_'));
    assertFalse(StringPractice.isPunct('a'));
    assertFalse(StringPractice.isPunct('\t'));
    assertFalse(StringPractice.isPunct('\n'));
  }

  @Test
  void testNumPunct() {
    assertEquals(0, StringPractice.numPunct("cheesy poofs"));
    assertEquals(0, StringPractice.numPunct(""));
    assertEquals(1, StringPractice.numPunct("!"));
    assertEquals(1, StringPractice.numPunct("! "));
    assertEquals(1, StringPractice.numPunct("! "));
    assertEquals(1, StringPractice.numPunct(" !"));
    assertEquals(2, StringPractice.numPunct(".!"));
    assertEquals(2, StringPractice.numPunct(". !"));
    assertEquals(2, StringPractice.numPunct("There's always money in the banana stand."));
  }

  @Test
  void testNumPunctStringInt() {
    assertEquals(0, StringPractice.numPunct("cheesy poofs", 4));
    assertEquals(0, StringPractice.numPunct("", 1));
    assertEquals(1, StringPractice.numPunct("!", 0));
    assertEquals(0, StringPractice.numPunct("!", 1));
    assertEquals(0, StringPractice.numPunct("! ", 1));
    assertEquals(1, StringPractice.numPunct("! ", 0));
    assertEquals(1, StringPractice.numPunct(" !", 0));
    assertEquals(2, StringPractice.numPunct(".!", 0));
    assertEquals(1, StringPractice.numPunct(".!", 1));
    assertEquals(0, StringPractice.numPunct(".!", 2));
    assertEquals(2, StringPractice.numPunct(". !", 0));
    assertEquals(1, StringPractice.numPunct(". !", 1));
    assertEquals(1, StringPractice.numPunct(". !", 2));
    assertEquals(0, StringPractice.numPunct(". !", 3));
    assertEquals(2, StringPractice.numPunct("There's always money in the banana stand.", 5));
    assertEquals(1, StringPractice.numPunct("There's always money in the banana stand.", 6));
  }

  @Test
  void testIndexOfFirstPunctString() {
    assertEquals(-1, StringPractice.indexOfFirstPunct("cheesy poofs"));
    assertEquals(-1, StringPractice.indexOfFirstPunct(""));
    assertEquals(0, StringPractice.indexOfFirstPunct("!"));
    assertEquals(1, StringPractice.indexOfFirstPunct(" !"));
    assertEquals(0, StringPractice.indexOfFirstPunct(". !"));
    assertEquals(5, StringPractice.indexOfFirstPunct("There's always money in the banana stand."));
  }


  @Test
  void testIndexOfLastPunct() {
    assertEquals(-1, StringPractice.indexOfLastPunct("cheesy poofs"));
    assertEquals(-1, StringPractice.indexOfLastPunct(""));
    assertEquals(0, StringPractice.indexOfLastPunct("!"));
    assertEquals(1, StringPractice.indexOfLastPunct(" !"));
    assertEquals(2, StringPractice.indexOfLastPunct(". !"));
    assertEquals(40, StringPractice.indexOfLastPunct("There's always money in the banana stand."));
  }

  @Test
  void testSubstitute() {
    assertEquals("Apple", StringPractice.substitute("apple", 'a', 'A'));
    assertEquals("apple", StringPractice.substitute("apple", 'a', 'a'));
    assertEquals("apple", StringPractice.substitute("apple", ' ', 'a'));
    assertEquals(" pple", StringPractice.substitute("apple", 'a', ' '));
    assertEquals("applE", StringPractice.substitute("apple", 'e', 'E'));
    assertEquals("apple", StringPractice.substitute("apple", ' ', 'e'));
    assertEquals("appl ", StringPractice.substitute("apple", 'e', ' '));
    assertEquals("a  le", StringPractice.substitute("apple", 'p', ' '));
  }

  @Test
  void testSubstitutePunct() {
    assertEquals("cheesy poofs", StringPractice.substitutePunct("cheesy poofs"));
    assertEquals("", StringPractice.substitutePunct(""));
    assertEquals(" ", StringPractice.substitutePunct("!"));
    assertEquals("  ", StringPractice.substitutePunct("! "));
    assertEquals("  ", StringPractice.substitutePunct(" !"));
    assertEquals("  ", StringPractice.substitutePunct(".!"));
    assertEquals("   ", StringPractice.substitutePunct(". !"));
    assertEquals("There s always money in the banana stand ",
                 StringPractice.substitutePunct("There's always money in the banana stand."));
  }

  @Test
  void testWithoutPunct() {
    assertEquals(StringPractice.withoutPunct("cheesy poofs"), "cheesy poofs");
    assertEquals(StringPractice.withoutPunct(""), "");
    assertEquals(StringPractice.withoutPunct("!"), "");
    assertEquals(StringPractice.withoutPunct("! "), " ");
    assertEquals(StringPractice.withoutPunct(" !"), " ");
    assertEquals(StringPractice.withoutPunct(".!"), "");
    assertEquals(StringPractice.withoutPunct(". !"), " ");
    assertEquals(StringPractice.withoutPunct("There's always money in the banana stand."),
                 "Theres always money in the banana stand");
  }

  @Test
  void testFoundIn() {
    assertFalse(StringPractice.foundIn("cheesy poofs", '.'));
    assertFalse(StringPractice.foundIn("cheesy poofs", 'q'));
    assertFalse(StringPractice.foundIn("", '.'));
    assertFalse(StringPractice.foundIn(" ", '.'));
    assertTrue(StringPractice.foundIn("cheesy poofs", 's'));
    assertTrue(StringPractice.foundIn("cheesy poofs", ' '));
    assertTrue(StringPractice.foundIn(" ", ' '));
  }

  @Test
  void testOnlyPunct() {
    assertFalse(StringPractice.onlyPunct("!cheesy poofs"));
    assertFalse(StringPractice.onlyPunct("cheesy poofs"));
    assertFalse(StringPractice.onlyPunct("!,!?:,abc"));
    assertTrue(StringPractice.onlyPunct("!"));
    assertTrue(StringPractice.onlyPunct("!,!?:,"));
  }

  @Test
  void testNoPunct() {
    assertFalse(StringPractice.noPunct("!cheesy poofs"));
    assertFalse(StringPractice.noPunct("cheesy! poofs!"));
    assertFalse(StringPractice.noPunct("!,!?:,abc"));
    assertTrue(StringPractice.noPunct("Four score"));
    assertTrue(StringPractice.noPunct(" "));
    assertTrue(StringPractice.noPunct(""));
  }
	  
  @Test
  void testconsecutivePuncts() {
    assertFalse(StringPractice.consecutivePuncts(""));
    assertFalse(StringPractice.consecutivePuncts("a"));
    assertFalse(StringPractice.consecutivePuncts("!"));
    assertFalse(StringPractice.consecutivePuncts("!a,"));
    assertTrue(StringPractice.consecutivePuncts("!,"));
    assertTrue(StringPractice.consecutivePuncts("a!,"));
    assertTrue(StringPractice.consecutivePuncts("a,!"));
  }

  @Test
  void testNumPunctAfterNonPunct() {
	  assertEquals(0, StringPractice.numPunctAfterNonPunct("cheesypoofs"));
	  assertEquals(0, StringPractice.numPunctAfterNonPunct(";!"));
	  assertEquals(1, StringPractice.numPunctAfterNonPunct("abc!"));
	  assertEquals(1, StringPractice.numPunctAfterNonPunct("1;2"));
	  assertEquals(1, StringPractice.numPunctAfterNonPunct("gg?"));
	  assertEquals(1, StringPractice.numPunctAfterNonPunct("a, abc"));
	  assertEquals(2, StringPractice.numPunctAfterNonPunct("32.abc!"));
	  assertEquals(2, StringPractice.numPunctAfterNonPunct("abc.123!"));
	
	    
  }
}
