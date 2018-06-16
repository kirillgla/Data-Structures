package borsk.editorconfig.collections;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Since PlainWordSet is a mere wrapper over NavigableSet,
 * there's no need to test anything but {@code WordSet.containsPrefix(String)}
 */
public class PlainWordSetTest {
  @Test
  public void testContainsPrefixWhenGivenWordPresent() {
    WordSet set = new PlainWordSet();

    set.add("hell");
    boolean contains = set.containsPrefix("hell");

    assertTrue(contains);
  }

  @Test
  public void testContainsPrefixWhenPrefixWordPresent() {
    WordSet set = new PlainWordSet();

    set.add("hello");
    boolean contains = set.containsPrefix("hell");

    assertTrue(contains);
  }

  @Test
  public void testContainsPrefixWhenGreaterNonPrefixWordPresent() {
    WordSet set = new PlainWordSet();

    set.add("AAAB");
    boolean contains = set.containsPrefix("AAAA");

    assertFalse(contains);
  }

  @Test
  public void testContainsPrefixWhenNoGreaterWordPresent() {
    WordSet set = new PlainWordSet();

    set.add("AAAA omg what's going on");
    boolean contains = set.containsPrefix("AAAB");

    assertFalse(contains);
  }
}