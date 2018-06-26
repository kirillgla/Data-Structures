import borsk.editorconfig.collections.LazyCharMap
import borsk.editorconfig.collections.UnsupportedCharacterException
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class LazyCharMapLazinessTest {
  private lateinit var map: LazyCharMap<String>

  @Before
  fun initialize() {
    map = LazyCharMap()
  }

  private fun assertUninitialized() =
    assertFalse(map.myValuesDelegate.isInitialized())

  private fun assertInitialized() =
    assertTrue(map.myValuesDelegate.isInitialized())

  @Test
  fun `test that map is created uninitialized`() {
    assertUninitialized()
  }

  @Test
  fun `test that get() doesn't initialize myValues`() {
    val data = map['f']

    assertNull(data)
    assertUninitialized()
  }

  @Test
  fun `test that delete() doesn't initialize myValues`() {
    map.delete('t')

    assertUninitialized()
  }

  @Test
  fun `test that clear() doesn't initialize myValues`() {
    map.clear()

    assertUninitialized()
  }

  @Test
  fun `test that isEmpty() doesn't initialize myValues`() {
    val result = map.isEmpty()

    assertTrue(result)
    assertUninitialized()
  }

  @Test
  fun `test that iterating doesn't initialize myValues`() {
    map.forEach(::println)

    assertUninitialized()
  }

  @Test
  fun `test that valid set() does initialize myValues`() {
    map['w'] = "Zoe"

    assertInitialized()
  }

  @Test
  fun `test that invalid set doesn't initialize myValues`() {
    assertFailsWith<UnsupportedCharacterException> { map['$'] = "Zoe" }
    assertUninitialized()
  }

  @Test
  fun `test that calling  iterator next doesn't initialize myValues `() {
    val iterator = map.iterator()

    assertFailsWith<NoSuchElementException> { iterator.next() }
    assertUninitialized()
  }
}
