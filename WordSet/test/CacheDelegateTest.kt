import borsk.editorconfig.collections.cache
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CacheDelegateTest {
  @Test
  fun testCacheDelegateValueIsConsistent() {
    val random = Random()
    val cached: Int? by cache { random.nextInt() }
    val data1: Int? = cached
    val data2: Int? = cached
    val data3: Int? = cached

    assertEquals(data1, data2)
    assertEquals(data1, data3)
  }

  @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "UNUSED_VALUE")
  @Test(expected = IllegalStateException::class)
  fun testCacheProhibitsRandomWrites() {
    var cached: Int? by cache { 7 }
    cached = 8
  }

  @Test
  fun testCacheAllowsFlush() {
    val random = Random(123456789L)
    var cached: Int? by cache { random.nextInt() }
    val first: Int? = cached
    cached = null
    val second: Int? = cached
    assertNotEquals(first, second)
  }
}
