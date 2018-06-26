
import borsk.editorconfig.collections.PrefixTreeNode
import borsk.editorconfig.collections.UnsupportedCharacterException
import org.junit.Assert.*
import org.junit.Test

class PrefixTreeNodeTest {

  // isWordEnd tests

  @Test
  fun testIsWordEndOnNonWordEnd() {
    val result = PrefixTreeNode().isWordEnd

    assertFalse(result)
  }

  @Test
  fun testIsWordEndOnWordEnd() {
    val result = PrefixTreeNode(data = "a").isWordEnd

    assertTrue(result)
  }

  // insert() tests

  @Test
  fun testInsertWhenValuePresent() {
    val node = PrefixTreeNode(data = "A")

    val containsInitially = node.isWordEnd
    val result = node.insert("A", 1)
    val contains = node.isWordEnd

    assertTrue(containsInitially)
    assertFalse(result)
    assertTrue(contains)
  }

  @Test
  fun testSimpleInsert() {
    val node = PrefixTreeNode()

    val added = node.insert("A", 1)
    val contains = node.isWordEnd

    assertTrue(added)
    assertTrue(contains)
  }

  @Test
  fun testInsertTwice() {
    val node = PrefixTreeNode()

    val first = node.insert("ABC")
    val second = node.insert("ABC")

    assertTrue(first)
    assertFalse(second)
  }

  @Test(expected = UnsupportedCharacterException::class)
  fun testInsertIllegalValue() {
    val node = PrefixTreeNode()
    node.insert("this-is-sample-text#!")
  }

  // remove tests

  @Test
  fun `test remove on too short tree`() {
    val node = PrefixTreeNode(data = "")

    val result = node.remove("100500-symbols-long-but-still-valid-string")
    val stillContains = node.isWordEnd

    assertFalse(result)
    assertTrue(stillContains)
  }

  @Test
  fun `test remove on node containing other data`() {
    val node = PrefixTreeNode()
    node.insert("Zoe")

    val result = node.remove("Zoa")

    assertFalse(result)
  }

  @Test
  fun `test remove on node containing match`() {
    val node = PrefixTreeNode("")

    val result = node.remove("")
    val contains = node.isWordEnd

    assertTrue(result)
    assertFalse(contains)
  }

  // subtreeSize tests

  @Test
  fun testGetSubtreeSizeOnLeaf() {
    val node = PrefixTreeNode()

    val result = node.insert("Zoe", 3)
    val size = node.subtreeSize
    val actualSize = node.actualSubtreeSize

    assertTrue(result)
    assertEquals(1, size)
    assertEquals(1, actualSize)
  }

  @Test
  fun testSubtreeSizeAfterAdding() {
    val node = PrefixTreeNode()

    val insertedFirst = node.insert("e", 0)
    val insertedSecond = node.insert("fo")
    val insertedThird = node.insert("fe")
    val size = node.subtreeSize
    val actualSize = node.actualSubtreeSize

    assertTrue(insertedFirst)
    assertTrue(insertedSecond)
    assertTrue(insertedThird)
    assertEquals(3, size)
    assertEquals(3, actualSize)
  }

  @Test
  fun testSubtreeSizeAfterAddingAndDeleting() {
    val node = PrefixTreeNode()

    val insertedFirst = node.insert("e")
    val insertedSecond = node.insert("fo")
    val insertedThird = node.insert("fe")
    val deletedThird = node.remove("fe")
    val size = node.subtreeSize
    val actualSize = node.actualSubtreeSize

    assertTrue(insertedFirst)
    assertTrue(insertedSecond)
    assertTrue(insertedThird)
    assertTrue(deletedThird)
    assertEquals(2, size)
    assertEquals(2, actualSize)
  }

  @Test
  fun testSubtreeSizeAfterClearing() {
    val node = PrefixTreeNode()

    val insertedFirst = node.insert("e", 0)
    val insertedSecond = node.insert("fo")
    val insertedThird = node.insert("fe")
    node.clear()
    val size = node.subtreeSize
    val actualSize = node.actualSubtreeSize

    assertTrue(insertedFirst)
    assertTrue(insertedSecond)
    assertTrue(insertedThird)
    assertEquals(0, size)
    assertEquals(0, actualSize)
  }

  // isEmpty tests

  @Test
  fun testIsEmptyOnEmptyRoot() {
    val node = PrefixTreeNode()

    val empty = node.isEmpty()

    assertTrue(empty)
  }

  @Test
  fun testIsEmptyOnLeafNode() {
    val node = PrefixTreeNode("Zoe")

    val empty = node.isEmpty()

    assertFalse(empty)
  }

  @Test
  fun testIsEmptyOnNonEmptyRoot() {
    val node = PrefixTreeNode()
    node.insert("hello")

    val empty = node.isEmpty()

    assertFalse(empty)
  }

  /*// find() tests

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
  }*/
}

