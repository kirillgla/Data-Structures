package borsk.editorconfig.collections

import org.junit.Assert.*
import org.junit.Test

class TreeWordSetTest {

  // containsPrefix() tests

  @Test
  fun testContainsPrefixWhenGivenWordPresent() {
    val set = TreeWordSet()
    set.add("hell")

    val contains = set.containsPrefix("hell")

    assertTrue(contains)
  }

  @Test
  fun testContainsPrefixWhenPrefixWordPresent() {
    val set = TreeWordSet()
    set.add("hello")

    val contains = set.containsPrefix("hell")

    assertTrue(contains)
  }

  @Test
  fun testContainsPrefixWhenGreaterNonPrefixWordPresent() {
    val set = TreeWordSet()
    set.add("AAAB")

    val contains = set.containsPrefix("AAAA")

    assertFalse(contains)
  }

  @Test
  fun testContainsPrefixWhenNoGreaterWordPresent() {
    val set = TreeWordSet()
    set.add("AAAA omg what's going on")

    val contains = set.containsPrefix("AAAB")

    assertFalse(contains)
  }

  @Test
  fun testContainsPrefixWhenSetIsEmpty() {
    val set = TreeWordSet()

    val contains = set.containsPrefix("hello")

    assertFalse(contains)
  }

  @Test
  fun testContainsPrefixOnComplexData() {
    val set = TreeWordSet()
    set.add("hello")
    set.add("helo")
    set.add("world")
    set.add("help")

    val result = set.containsPrefix("hell")

    assertTrue(result)
  }

  // getWordsStartingWith() tests

  @Test
  fun testGetWordsStartingWithPrefixWhenSetIsEmpty() {
    val set = TreeWordSet()

    val words = set.getWordsStartingWith("hel")
    val count = words.size.toLong()

    assertEquals(0, count)
  }

  @Test
  fun testGetWordsStartingWithPrefixWhenNoMatchesPresent() {
    val set = TreeWordSet()
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getWordsStartingWith("hel")
    val count = words.size.toLong()

    assertEquals(0, count)
  }

  @Test
  fun testGetWordsStartingWithPrefixWhenPrefixPresent() {
    val set = TreeWordSet()
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getWordsStartingWith("fall")

    assertEquals(1, words.size.toLong())
    assertEquals("fall", words[0])
  }

  @Test
  fun testGetWordsStartingWithPrefixWhenSingleMatchPresent() {
    val set = TreeWordSet()
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getWordsStartingWith("fal")

    assertEquals(1, words.size.toLong())
    assertEquals("fall", words[0])
  }

  @Test
  fun testGetWordsStartingWithPrefixWhenMultipleMatchesPresent() {
    val set = TreeWordSet()
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getWordsStartingWith("fe")

    assertEquals(2, words.size.toLong())
    assertEquals("feed", words[0])
    assertEquals("feel", words[1])
  }
}
