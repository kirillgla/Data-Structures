
import borsk.editorconfig.collections.CharMap
import borsk.editorconfig.collections.LazyCharMap
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.NoSuchElementException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LazyCharMapIteratorTest {
  private lateinit var map: CharMap<String>

  @Before
  fun initialize() {
    map = LazyCharMap()
  }

  @Test
  fun `test hasNext() when map is empty`() {
    val iterator = map.iterator()
    val hasNext = iterator.hasNext()

    assertFalse(hasNext)
  }

  @Test
  fun `test hasNext() when map is not empty`() {
    map['e'] = "oops"
    val iterator = map.iterator()
    val hasNext = iterator.hasNext()

    assertTrue(hasNext)
  }

  @Test(expected = NoSuchElementException::class)
  fun `test next() when map is empty`() {
    val iterator = map.iterator()
    iterator.next()
  }

  @Test
  fun `test next() when map contains one element`() {
    map['e'] = "foo"
    val iterator = map.iterator()

    val hasFirst = iterator.hasNext()
    val first = iterator.next()
    val hasSecond = iterator.hasNext()

    assertTrue(hasFirst)
    assertEquals("foo", first)
    assertFalse(hasSecond)
    assertFailsWith<NoSuchElementException> { iterator.next() }
  }

  @Test
  fun `test next() when map contains many elements`() {
    map['e'] = "foo"
    map['f'] = "bar"
    map['g'] = "bas"
    val iterator = map.iterator()

    val hasFirst = iterator.hasNext()
    val first = iterator.next()
    val hasSecond = iterator.hasNext()
    val second = iterator.next()
    val hasThird = iterator.hasNext()
    val third = iterator.next()
    val hasFourth = iterator.hasNext()

    assertTrue(hasFirst)
    assertTrue(hasSecond)
    assertTrue(hasThird)
    assertFalse(hasFourth)

    assertEquals("foo", first)
    assertEquals("bar", second)
    assertEquals("bas", third)

    assertFailsWith<NoSuchElementException> { iterator.next() }
  }

  @Test(expected = ConcurrentModificationException::class)
  fun `test that next() throws when collection is modified`() {
    map['e'] = "foo"
    map['g'] = "bar"
    val iterator = map.iterator()
    iterator.next()
    map['f'] = "bas"
    iterator.next()
  }

  @Test
  fun `test that complicated SimpleCharMap maps to ints correctly`() {
    map['e'] = "foo"
    map['f'] = "bar"
    map['g'] = "bas"
    val data = map.map { it }.toTypedArray()

    assertEquals(3, data.size)
    assertEquals("foo", data[0])
    assertEquals("bar", data[1])
    assertEquals("bas", data[2])
  }
}
