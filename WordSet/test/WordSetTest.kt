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

  // size tests

  @Test
  fun testSizeOnEmptySet() {
    val size = set.size

    assertEquals(0, size)
  }

  // contains() tests

  @Test
  fun testContainsOnEmptySet() {
    val contains = set.contains("Zoe")

    assertFalse(contains)
  }

  @Test
  fun testContainsWhenNoMatch() {
    set.add("5810")

    val contains = set.contains("425")

    assertFalse(contains)
  }

  @Test
  fun testContainsWhenContinuationPresent() {
    set.add("12345")

    val contains = set.contains("123")

    assertFalse(contains)
  }

  @Test
  fun testContainsTooLongContinuation() {
    set.add("123")

    val contains = set.contains("12345")

    assertFalse(contains)
  }

  @Test
  fun testContainsWhenMatchPresent() {
    set.add("123")
    set.add("456")
    set.add("189")

    val contains = set.contains("123")

    assertTrue(contains)
  }

  @Test
  fun testContainsOnEmptySetWithInvalidInput() {
    val contains = set.contains("wow$")

    assertFalse(contains)
  }

  @Test
  fun testContainsOnNonEmptySetWithInvalidInput() {
    set.add("Hello")
    set.add("Hell")

    val contains = set.contains("wow$")

    assertFalse(contains)
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

  // getContinuationIterable() tests

  @Test
  fun testGetContinuationIterableWhenSetIsEmpty() {
    val words = set.getContinuationIterable("hel")
    val count = words.count()

    assertEquals(0, count)
  }

  @Test
  fun testGetContinuationIterableWhenNoMatchesPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getContinuationIterable("hel")
    val count = words.count()

    assertEquals(0, count)
  }

  @Test
  fun testGetContinuationIterableWhenPrefixPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getContinuationIterable("fall")
    val iterator = words.iterator()
    val first = iterator.next()
    val hasSecond = iterator.hasNext()

    assertEquals("fall", first)
    assertFalse(hasSecond)
  }

  @Test
  fun testGetContinuationIterableWhenSingleMatchPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getContinuationIterable("fal")
    val iterator = words.iterator()
    val first = iterator.next()
    val hasSecond = iterator.hasNext()

    assertEquals("fall", first)
    assertFalse(hasSecond)
  }

  @Test
  fun testGetContinuationIterableWhenMultipleMatchesPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getContinuationIterable("fe")
    val iterator = words.iterator()
    val first = iterator.next()
    val second = iterator.next()
    val hasThird = iterator.hasNext()

    assertEquals("feed", first)
    assertEquals("feel", second)
    assertFalse(hasThird)
  }

  // getContinuationSequence() tests

  @Test
  fun testGetContinuationSequenceWhenSetIsEmpty() {
    val words = set.getContinuationSequence("hel")
    val count = words.count()

    assertEquals(0, count)
  }

  @Test
  fun testGetContinuationSequenceWhenNoMatchesPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getContinuationSequence("hel")
    val count = words.count()

    assertEquals(0, count)
  }

  @Test
  fun testGetContinuationSequenceWhenPrefixPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getContinuationSequence("fall")
    val iterator = words.iterator()
    val first = iterator.next()
    val hasSecond = iterator.hasNext()

    assertEquals("fall", first)
    assertFalse(hasSecond)
  }

  @Test
  fun testGetContinuationSequenceWhenSingleMatchPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getContinuationSequence("fal")
    val iterator = words.iterator()
    val first = iterator.next()
    val hasSecond = iterator.hasNext()

    assertEquals("fall", first)
    assertFalse(hasSecond)
  }

  @Test
  fun testGetContinuationSequenceWhenMultipleMatchesPresent() {
    set.add("feed")
    set.add("feel")
    set.add("foo")
    set.add("far")
    set.add("fall")

    val words = set.getContinuationSequence("fe")
    val iterator = words.iterator()
    val first = iterator.next()
    val second = iterator.next()
    val hasThird = iterator.hasNext()

    assertEquals("feed", first)
    assertEquals("feel", second)
    assertFalse(hasThird)
  }
}
