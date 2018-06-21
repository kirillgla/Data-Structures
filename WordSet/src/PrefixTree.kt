package borsk.editorconfig.collections

class PrefixTree : MutableWordSet {
  private val myChildren: CharMap<PrefixTreeNode> = SimpleCharMap()
  // private var myModCount = 0

  override val size: Int
    get() = myChildren.map { it.subTreeSize }.sum()

  override fun containsPrefix(prefix: String): Boolean {
    if (prefix.isEmpty()) {
      return !isEmpty()
    }
    return myChildren[prefix[0]]?.findPrefix(prefix, 0) ?: false
  }

  override fun getWordsStartingWith(prefix: String): List<String> {
    TODO("not implemented")
  }

  override fun add(element: String): Boolean {
//    if (myChildren)
    TODO()
  }

  override fun addAll(elements: Collection<String>): Boolean =
    elements.fold(false) { accumulator, current -> accumulator or add(current) }

  override fun clear() =
    myChildren.clear()

  override fun remove(element: String): Boolean {
    TODO("not implemented")
  }

  override fun removeAll(elements: Collection<String>): Boolean =
    elements.fold(false) { accumulator, current -> accumulator or remove(current) }

  override fun retainAll(elements: Collection<String>): Boolean {
    TODO("not implemented")
  }

  override fun contains(element: String): Boolean =
    myChildren[element[0]]?.find(element, 0) ?: false

  override fun containsAll(elements: Collection<String>): Boolean =
    elements.fold(true) { acc, current -> acc && contains(current) }

  override fun isEmpty(): Boolean =
    myChildren.isEmpty()

  override fun iterator(): MutableIterator<String> {
    TODO("not implemented")
  }
}
