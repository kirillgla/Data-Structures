
import borsk.editorconfig.collections.MutableWordSet
import borsk.editorconfig.collections.PrefixTree
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WordSetTest {
  private lateinit var set: MutableWordSet

  @Before
  fun initialize() {
    set = PrefixTree()
  }

  // containsPrefix() tests

  @Test
  fun testContainsPrefixWhenGivenWordPresent() {
    val added = set.add("hell")
    val contains = set.containsPrefix("hell")

    assertTrue(added)
    assertTrue(contains)
  }

  @Test
  fun testContainsPrefixWhenPrefixWordPresent() {
    val added = set.add("hello")
    val contains = set.containsPrefix("hell")

    assertTrue(added)
    assertTrue(contains)
  }

  @Test
  fun testContainsPrefixWhenGreaterNonPrefixWordPresent() {
    val added = set.add("AAAB")
    val contains = set.containsPrefix("AAAA")

    assertTrue(added)
    assertFalse(contains)
  }

  @Test
  fun testContainsPrefixWhenNoGreaterWordPresent() {
    val added = set.add("AAAA-omg-what-is-going-on")
    val contains = set.containsPrefix("AAAB")

    assertTrue(added)
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

  // getAllWithPrefix() tests

  @Test
  fun testGetWordsStartingWithPrefixWhenSetIsEmpty() {
    val words = set.getAllWithPrefix("hel")
    val count = words.count()

    assertEquals(0, count)
  }

  @Test
  fun testGetWordsStartingWithPrefixWhenNoMatchesPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getAllWithPrefix("hel")
    val count = words.count()

    assertEquals(0, count)
  }

  @Test
  fun testGetWordsStartingWithPrefixWhenPrefixPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getAllWithPrefix("fall")
    val iterator = words.iterator()
    val first = iterator.next()
    val hasSecond = iterator.hasNext()

    assertEquals("fall", first)
    assertFalse(hasSecond)
  }

  @Test
  fun testGetWordsStartingWithPrefixWhenSingleMatchPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getAllWithPrefix("fal")
    val iterator = words.iterator()
    val first = iterator.next()
    val hasSecond = iterator.hasNext()

    assertEquals("fall", first)
    assertFalse(hasSecond)
  }

  @Test
  fun testGetWordsStartingWithPrefixWhenMultipleMatchesPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getAllWithPrefix("fe")
    val iterator = words.iterator()
    val first = iterator.next()
    val second = iterator.next()
    val hasThird = iterator.hasNext()

    assertEquals("feed", first)
    assertEquals("feel", second)
    assertFalse(hasThird)
  }
}
