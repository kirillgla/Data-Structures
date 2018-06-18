package borsk.editorconfig.collections;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Since PlainWordSet is a mere wrapper over NavigableSet,
 * there's no need to test anything but {@code WordSet.containsPrefix(String)}
 */
public class PlainWordSetTest {

  // containsPrefix() tests

  @Test
  public void testContainsPrefixWhenGivenWordPresent() {
    final WordSet set = new PlainWordSet();
    set.add("hell");

    final boolean contains = set.containsPrefix("hell");

    assertTrue(contains);
  }

  @Test
  public void testContainsPrefixWhenPrefixWordPresent() {
    final WordSet set = new PlainWordSet();
    set.add("hello");

    final boolean contains = set.containsPrefix("hell");

    assertTrue(contains);
  }

  @Test
  public void testContainsPrefixWhenGreaterNonPrefixWordPresent() {
    final WordSet set = new PlainWordSet();
    set.add("AAAB");

    final boolean contains = set.containsPrefix("AAAA");

    assertFalse(contains);
  }

  @Test
  public void testContainsPrefixWhenNoGreaterWordPresent() {
    final WordSet set = new PlainWordSet();
    set.add("AAAA omg what's going on");

    final boolean contains = set.containsPrefix("AAAB");

    assertFalse(contains);
  }

  @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
  @Test
  public void testContainsPrefixWhenSetIsEmpty() {
    final WordSet set = new PlainWordSet();

    final boolean contains = set.containsPrefix("hello");

    assertFalse(contains);
  }

  @Test
  public void testContainsPrefixOnComplexData() {
    final WordSet set = new PlainWordSet();
    set.add("hello");
    set.add("helo");
    set.add("world");
    set.add("help");

    final boolean result = set.containsPrefix("hell");

    assertTrue(result);
  }

  // getWordsStartingWith() tests

  @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
  @Test
  public void testGetWordsStartingWithPrefixWhenSetIsEmpty() {
    final WordSet set = new PlainWordSet();

    final List<String> words = set.getWordsStartingWith("hel");
    final long count = words.size();

    assertEquals(0, count);
  }

  @Test
  public void testGetWordsStartingWithPrefixWhenNoMatchesPresent() {
    final WordSet set = new PlainWordSet();
    set.add("feed");
    set.add("feel");
    set.add("foo");
    set.add("far");
    set.add("fall");

    final List<String> words = set.getWordsStartingWith("hel");
    final long count = words.size();

    assertEquals(0, count);
  }

  @Test
  public void testGetWordsStartingWithPrefixWhenPrefixPresent() {
    final WordSet set = new PlainWordSet();
    set.add("feed");
    set.add("feel");
    set.add("foo");
    set.add("far");
    set.add("fall");

    final List<String> words = set.getWordsStartingWith("fall");

    assertEquals(1, words.size());
    assertEquals("fall", words.get(0));
  }

  @Test
  public void testGetWordsStartingWithPrefixWhenSingleMatchPresent() {
    final WordSet set = new PlainWordSet();
    set.add("feed");
    set.add("feel");
    set.add("foo");
    set.add("far");
    set.add("fall");

    final List<String> words = set.getWordsStartingWith("fal");

    assertEquals(1, words.size());
    assertEquals("fall", words.get(0));
  }

  @Test
  public void testGetWordsStartingWithPrefixWhenMultipleMatchesPresent() {
    final WordSet set = new PlainWordSet();
    set.add("feed");
    set.add("feel");
    set.add("foo");
    set.add("far");
    set.add("fall");

    final List<String> words = set.getWordsStartingWith("fe");

    assertEquals(2, words.size());
    assertEquals("feed", words.get(0));
    assertEquals("feel", words.get(1));
  }
}
