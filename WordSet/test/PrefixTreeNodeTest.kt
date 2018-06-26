/*
import borsk.editorconfig.collections.PrefixTreeNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PrefixTreeNodeTest {

  // isWordEnd tests

  @Test
  fun testIsWordEndOnNonWordEnd() {
    val result = PrefixTreeNode().isWordEnd

    assertFalse(result)
  }

  @Test
  fun testIsWordEndOnWordEnd() {
    val node = PrefixTreeNode(myData = "a")
    val result = node.isWordEnd

    assertTrue(result)
  }

  // find() tests

  @Test
  fun testFindOnPresentValue() {
    val node = PrefixTreeNode(myData = "a")
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

    assertTrue(result)
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

  // containsPrefix() tests

  @Test
  fun testFindPrefixOnPrefixPresent() {
    node.myChildren['x'] = PrefixTreeNode().also { it.data = "ax" }
    val result = node.containsPrefix("a", 0)

    assertTrue(result)
  }

  @Test
  fun testFindPrefixOnCorrectPrefixNotPresent() {
    node.myChildren['c'] = PrefixTreeNode().also { it.data = "ac" }
    val result = node.containsPrefix("ab", 0)

    assertFalse(result)
  }

  @Test
  fun testFindPrefixOnIncorrectPrefix() {
    val result = node.containsPrefix("a%", 0)

    assertFalse(result)
  }

  // subTreeSize tests

  @Test
  fun testSubTreeSizeOnLastNode() {
    node.data = "Q"
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

  // add() tests

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

  @Test(expected = IllegalArgumentException::class)
  fun testAddThrowsOnInvalidInput() {
    node.add("", 0)
  }

  // findAllPrefixed() tests

  @Test
  fun testFindAllPrefixedOnEmptySet() {
    val result = node.findAllPrefixed("Zoe", 0)
    assertEquals(0, result.size)
  }

  @Test
  fun testFindAllPrefixedWhenNoPrefixPresent() {
    node.add("Zoe", 0)
    val result = node.findAllPrefixed("aaa", 0)
    assertEquals(0, result.size)
  }

  @Test
  fun testFindAllPrefixedWhenExactMatchPresent() {
    node.add("Zoe", 0)
    val result = node.findAllPrefixed("Zoe", 0)
    assertEquals(1, result.size)
    assertEquals("Zoe", result[0])
  }

  @Test
  fun testFindAllPrefixedWhenShorterWordPresent() {
    node.add("Zo", 0)
    val result = node.findAllPrefixed("Zoe", 0)
    assertEquals(0, result.size)
  }

  @Test
  fun testFindAllPrefixedWhenLongerWordPresent() {
    node.add("Zoe-zoe", 0)
    val result = node.findAllPrefixed("Zoe", 0)
    assertEquals(1, result.size)
    assertEquals("Zoe-zoe", result[0])
  }

  @Test
  fun testFindAllPrefixedOnComplicatedData() {
    // node represents letter 'f'

    node.myChildren['e'] = PrefixTreeNode().also {
      it.myChildren['e'] = PrefixTreeNode().also {
        it.myChildren['d'] = PrefixTreeNode().also { it.data = "feed" }
        it.myChildren['l'] = PrefixTreeNode().also { it.data = "feel" }
      }
    }

    node.myChildren['o'] = PrefixTreeNode().also {
      it.myChildren['o'] = PrefixTreeNode().also { it.data = "foo" }
    }

    node.myChildren['a'] = PrefixTreeNode().also {
      it.myChildren['r'] = PrefixTreeNode().also { it.data = "far" }
      it.myChildren['l'] = PrefixTreeNode().also {
        it.myChildren['l'] = PrefixTreeNode().also { it.data = "fall" }
      }
    }

    val result = node.findAllPrefixed("fe", 0)
    assertEquals(2, result.size)
    assertTrue(result.containsAll(listOf("feed", "feel")))
  }

  // TODO: test remove
}

*/
