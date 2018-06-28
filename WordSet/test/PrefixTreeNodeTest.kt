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
    val result = PrefixTreeNode().apply { data = "a" }.isWordEnd

    assertTrue(result)
  }

  // insert() tests

  @Test
  fun testInsertWhenValuePresent() {
    val node = PrefixTreeNode().apply { data = "A" }

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
    val node = PrefixTreeNode().apply { data = "" }

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
    val node = PrefixTreeNode().apply { data = "" }

    val result = node.remove("")
    val contains = node.isWordEnd

    assertTrue(result)
    assertFalse(contains)
  }

  @Test
  fun `test that removing value also remove associated node`() {
    val node = PrefixTreeNode()

    val empty1 = node.children.none()
    val inserted = node.insert("Zoe")
    val empty2 = node.children.none()
    val removed = node.remove("Zoe")
    val empty3 = node.children.none()

    assertTrue(empty1)
    assertTrue(inserted)
    assertFalse(empty2)
    assertTrue(removed)
    assertTrue(empty3)
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
    val node = PrefixTreeNode().apply { data = "Zoe" }

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
}

