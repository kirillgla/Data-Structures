import borsk.editorconfig.collections.MutableWordSet
import borsk.editorconfig.collections.PrefixTree
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@Ignore("PrefixTree is not yet implemented")
class WordSetTest {
  private lateinit var set: MutableWordSet

  @Before
  fun initialize() {
    set = PrefixTree()
  }

  // containsPrefix() tests

  @Test
  fun testContainsPrefixWhenGivenWordPresent() {
    set.add("hell")
    val contains = set.containsPrefix("hell")
    assertTrue(contains)
  }

  @Test
  fun testContainsPrefixWhenPrefixWordPresent() {
    set.add("hello")
    val contains = set.containsPrefix("hell")
    assertTrue(contains)
  }

  @Test
  fun testContainsPrefixWhenGreaterNonPrefixWordPresent() {
    set.add("AAAB")
    val contains = set.containsPrefix("AAAA")
    assertFalse(contains)
  }

  @Test
  fun testContainsPrefixWhenNoGreaterWordPresent() {
    set.add("AAAA omg what's going on")
    val contains = set.containsPrefix("AAAB")
    assertFalse(contains)
  }

  @Test
  fun testContainsPrefixWhenSetIsEmpty() {
    val contains = set.containsPrefix("hello")
    assertFalse(contains)
  }

  @Test
  fun testContainsPrefixOnComplexData() {
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
    val words = set.getWordsStartingWith("hel")
    val count = words.size.toLong()

    assertEquals(0, count)
  }

  @Test
  fun testGetWordsStartingWithPrefixWhenNoMatchesPresent() {
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
