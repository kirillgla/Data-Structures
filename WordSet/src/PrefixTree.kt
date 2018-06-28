package borsk.editorconfig.collections

import java.util.*

class PrefixTree : MutableWordSet {
  private val myRoot = PrefixTreeNode()
  private var myModCount = 0

  internal fun checkModCount(expectedModCount: Int) {
    if (myModCount != expectedModCount) {
      throw ConcurrentModificationException()
    }
  }

  override val size: Int
    get() = myRoot.subtreeSize

  override fun containsPrefix(prefix: String) = try {
    myRoot.customSearch(prefix) { true }
  } catch (exception: UnsupportedCharacterException) {
    false
  }

  override fun getContinuationSequence(prefix: String): Sequence<String> {
    var result: Sequence<String>? = null
    val found = myRoot.customSearch(prefix) { result = it.getDataSequence(myModCount, this); true }
    assert((result != null) == found)
    return result ?: emptySequence()
  }

  override fun getContinuationIterable(prefix: String): Iterable<String> {
    var result: Iterable<String>? = null
    val found = myRoot.customSearch(prefix) { result = it.getDataList(); true }
    assert((result != null) == found)
    return result ?: emptyList()
  }

  override fun contains(element: String) = try {
    myRoot.customSearch(element, 0, PrefixTreeNode::isWordEnd)
  } catch (exception: UnsupportedCharacterException) {
    false
  }

  override fun containsAll(elements: Collection<String>): Boolean =
    elements.all(this::contains)

  override fun isEmpty(): Boolean =
    myRoot.isEmpty()

  override fun add(element: String): Boolean {
    val result = myRoot.insert(element)
    if (result) {
      myModCount += 1
    }

    return result
  }

  // Note: using .asSequence() might improve performance
  override fun addAll(elements: Collection<String>): Boolean =
    elements.map(this::add).fold(false) { acc, element -> acc or element }

  override fun remove(element: String): Boolean {
    val result = try {
      myRoot.remove(element)
    } catch (exception: UnsupportedCharacterException) {
      false
    }
    if (result) {
      myModCount += 1
    }

    return result
  }

  // Note: using .asSequence() might improve performance
  override fun removeAll(elements: Collection<String>): Boolean =
    elements.map(this::remove).fold(false) { acc, element -> acc or element }

  override fun retainAll(elements: Collection<String>): Boolean {
    var result = false
    val iterator = iterator()
    while (iterator.hasNext()) {
      val data = iterator.next()
      if (data !in elements) {
        iterator.remove()
        result = true
      }
    }
    return result
  }

  override fun clear() =
    myRoot.clear()

  override fun iterator(): MutableIterator<String> {
    TODO("not implemented")
  }

  override fun toString(): String =
    "Prefix tree:$myRoot"
}
