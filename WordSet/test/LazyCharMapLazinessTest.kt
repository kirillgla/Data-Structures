
import borsk.editorconfig.collections.LazyCharMap
import org.junit.Assert
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LazyCharMapLazinessTest {
  private lateinit var map: LazyCharMap<String>

  @Before
  fun initialize() {
    map = LazyCharMap()
  }

  private fun assertUninitialized() =
    Assert.assertFalse(map.myValuesDelegate.isInitialized())

  @Test
  fun `test that map is created uninitialized`() {
    assertUninitialized()
  }

  @Test
  fun `test that getting value doesn't initialize myValues`() {
    val data = map['f']
    assertNull(data)
    assertUninitialized()
  }

  @Test
  fun `test that delete doesn't initialize myValues`() {
    map.delete('t')
    assertUninitialized()
  }

  @Test
  fun `test that clear() doesn't initialize myValues`() {
    map.clear()
    assertUninitialized()
  }

  @Test
  fun `test that isEmpty doesn't initialize myValues`() {
    val result = map.isEmpty()
    assertTrue(result)
    assertUninitialized()
  }

  @Test
  fun `test that iterating doesn't initialize myValues`() {
    map.forEach(::println)
    assertUninitialized()
  }
}
