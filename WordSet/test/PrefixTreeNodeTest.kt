import borsk.editorconfig.collections.PrefixTreeNode
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PrefixTreeNodeTest {
  private lateinit var node: PrefixTreeNode

  @Before
  fun initialize() {
    node = PrefixTreeNode()
  }

  @Test
  fun testIsWordEndOnNonWordEnd() {
    val result = node.isWordEnd

    assertFalse(result)
  }

  @Test
  fun testIsWordEndOnWordEnd() {
    node.data = ""
    val result = node.isWordEnd

    assertTrue(result)
  }

  @Test
  fun testFindOnPresentValue() {
    node.data = ""
    val found = node.find("a", 0)

    assertTrue(found)
  }

  @Test
  fun testFindOnNotPresentValue() {
    val found = node.find("a", 0)

    assertFalse(found)
  }

  @Test
  fun testFindOnTooLongString() {
    val found = node.find("John", 0)

    assertFalse(found)
  }

  @Test(expected = IllegalArgumentException::class)
  fun testFindOnInvalidIndex() {
    node.find("oops", 100500)
  }

  @Test
  fun testFindInComplicatedStructureWithTargetPresent() {
    node.myChildren['e'] = PrefixTreeNode().also {
      it.myChildren['e'] = PrefixTreeNode().also { it.data = "hee" }
      it.myChildren['f'] = PrefixTreeNode().also { it.data = "hef" }
    }
    val result = node.find("hee", 0)

    assertTrue(result, "Failed to find value present in node")
  }

  @Test
  fun testFindInComplicatedStructureWithTargetNotPresent() {
    node.myChildren['e'] = PrefixTreeNode().also {
      it.myChildren['e'] = PrefixTreeNode().also { it.data = "hee" }
      it.myChildren['f'] = PrefixTreeNode().also { it.data = "hef" }
    }
    val result = node.find("hen", 0)

    assertFalse(result)
  }

  @Test
  fun testFindPrefixOnPrefixPresent() {
    val result = node.findPrefix("a", 0)

    assertTrue(result)
  }

  @Test
  fun testFindPrefixOnCorrectPrefixNotPresent() {
    val result = node.findPrefix("ab", 0)

    assertFalse(result)
  }

  @Test
  fun testFindPrefixOnIncorrectPrefix() {
    val result = node.findPrefix("a%", 0)

    assertFalse(result)
  }

  @Test
  fun testSubTreeSizeOnLastNode() {
    node.data = ""
    val size = node.subTreeSize

    assertEquals(1, size)
  }

  @Test
  fun testSubTreeSizeInComplicatedTree() {
    node.myChildren['e'] = PrefixTreeNode().also { it.data = "e" }
    node.myChildren['f'] = PrefixTreeNode().also {
      it.myChildren['o'] = PrefixTreeNode().also { it.data = "fo" }
      it.myChildren['a'] = PrefixTreeNode().also { it.data = "fa" }
    }
    val size = node.subTreeSize

    assertEquals(3, size)
  }

  @Test
  fun testAddWhenValuePresent() {
    node.data = "A"
    val result = node.add("A", 0)
    val contains = node.isWordEnd

    assertFalse(result)
    assertTrue(contains)
  }

  @Test
  fun testAddWhenValueNotPresent() {
    val result = node.add("A", 0)
    assertTrue(result)
  }

  @Test
  fun testAddRecursionWhenValuePresent() {
    node.myChildren['B'] = PrefixTreeNode().also {
      it.myChildren['C'] = PrefixTreeNode().also {
        it.data = "ABC"
      }
    }
    val result = node.add("ABC", 0)
    val value = node.myChildren['B']?.myChildren?.get('C')?.data

    assertFalse(result)
    assertEquals("ABC", value)
  }

  @Test
  fun testAddRecursionWhenValueNotPresent() {
    node.myChildren['B'] = PrefixTreeNode().also {
      it.data = "AB"
      it.myChildren['C'] = PrefixTreeNode().also {
        it.myChildren['D'] = PrefixTreeNode().also {
          it.data = "ABCD"
        }
      }
    }
    val result = node.add("ABC", 0)
    val savedAB = node.myChildren['B']?.data
    val savedABC = node.myChildren['B']?.myChildren?.get('C')?.data
    val savedABCD = node.myChildren['B']?.myChildren?.get('C')?.myChildren?.get('D')?.data

    assertTrue(result)
    assertEquals("AB", savedAB)
    assertEquals("ABC", savedABC)
    assertEquals("ABCD", savedABCD)
  }
}
