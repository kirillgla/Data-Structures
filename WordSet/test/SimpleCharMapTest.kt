import borsk.editorconfig.collections.CharMap
import borsk.editorconfig.collections.SimpleCharMap
import borsk.editorconfig.collections.UnsupportedCharacterException
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SimpleCharMapTest {
  private lateinit var map: CharMap<String>

  @Before
  fun initialize() {
    map = SimpleCharMap()
  }

  @Test
  fun testStoresValidValue() {
    map['E'] = "hello"
    val value = map['E']

    assertEquals("hello", value)
  }

  @Test(expected = UnsupportedCharacterException::class)
  fun testThrowsOnSetInvalidIndex() {
    map['Ф'] = "hello"
  }

  @Test
  fun testHasNoInitialValue() {
    val value = map['R']

    assertNull(value)
  }

  @Test
  fun testReturnsNoValueOnInvalidIndex() {
    val value = map['$']

    assertNull(value)
  }

  @Test
  fun testDeletesNonExistingValue() {
    map.delete('E')
  }

  @Test
  fun testDeletesInvalidIndex() {
    map.delete('%')
  }

  @Test
  fun testDeleteActualIndex() {
    map['E'] = "John"
    map.delete('E')
    val value = map['E']

    assertNull(value)
  }

  @Test
  fun `test that delete doesn't affect unrelated values when the one being deleted is present`() {
    map['E'] = "John"
    map['F'] = "Mary"
    map.delete('E')
    val value = map['F']

    assertEquals("Mary", value)
  }

  @Test
  fun `test that delete doesn't affect unrelated values when the one being deleted is not present`() {
    map['F'] = "Mary"
    map.delete('E')
    val value = map['F']

    assertEquals("Mary", value)
  }

  @Test
  fun testDeletingInvalidLetter() {
    map.delete('5')
  }

  @Test
  fun testClearWhenMapIsEmpty() {
    map.clear()
  }

  @Test
  fun testClearWhenMapContainsValues() {
    map['E'] = "John"
    map['F'] = "Mary"
    map.clear()
    val value = map['E'] ?: map['F']

    assertNull(value)
  }

  @Test
  fun testIsEmptyOnEmptyCharMap() {
    val result = map.isEmpty()
    assertTrue(result)
  }

  @Test
  fun testIsEmptyOnFilledCharMap() {
    map['e'] = "Zoe"
    val result = map.isEmpty()
    assertFalse(result)
  }
}
