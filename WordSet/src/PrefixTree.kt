package borsk.editorconfig.collections

import java.util.*

class PrefixTree : MutableWordSet {
  private val myChildren: CharMap<PrefixTreeNode> = SimpleCharMap()
  // private var myModCount = 0

  override val size: Int
    get() = myChildren.map { it.subTreeSize }.sum()

  override fun containsPrefix(prefix: String): Boolean =
    if (prefix.isEmpty()) !isEmpty()
    else myChildren[prefix[0]]?.findPrefix(prefix) ?: false

  override fun getWordsStartingWith(prefix: String): List<String> =
    if (prefix.isEmpty())
      myChildren.fold(ArrayList()) { list, node -> list.also { node.getAllData(it) } }
    else
      myChildren[prefix[0]]?.findAllPrefixed(prefix) ?: Collections.emptyList()

  override fun add(element: String): Boolean =
    if (element.isEmpty()) false
    else (myChildren[element[0]] ?: PrefixTreeNode().also { myChildren[element[0]] = it })
      .add(element, 0)

  override fun addAll(elements: Collection<String>): Boolean =
    elements.fold(false) { accumulator, current -> accumulator or add(current) }

  override fun clear() =
    myChildren.clear()

  override fun remove(element: String): Boolean =
    if (element.isEmpty()) false
    else myChildren[element[0]]?.remove(element, 0) ?: false

  override fun removeAll(elements: Collection<String>): Boolean =
    elements.fold(false) { accumulator, current -> accumulator or remove(current) }

  override fun retainAll(elements: Collection<String>): Boolean {
    TODO("not implemented")
  }

  override fun contains(element: String): Boolean =
    myChildren[element[0]]?.find(element) ?: false

  override fun containsAll(elements: Collection<String>): Boolean =
    elements.fold(true) { acc, current -> acc && contains(current) }

  override fun isEmpty(): Boolean =
    myChildren.isEmpty()

  override fun iterator(): MutableIterator<String> =
    TODO("Not implemented")

  override fun toString(): String {
    var result = "Prefix tree:{"
    for (char in CharMap.allLetters) {
      val child = myChildren[char]
      if (child != null) {
        result += char
        result += ":"
        result += child.toString()
      }
    }
    result += "};"
    return result
  }
}
