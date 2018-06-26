package borsk.editorconfig.collections

// TODO: use sequences wherever possible
class PrefixTree : MutableWordSet {
  private val myRoot = PrefixTreeNode()
  // private var myModCount = 0

  override val size: Int
    get() = myRoot.subtreeSize

  override fun containsPrefix(prefix: String): Boolean =
    myRoot.customSearch(prefix, 0) { true }

  override fun getAllWithPrefix(prefix: String): Sequence<String> =
  // ArrayList<String>().also { list -> myRoot.customSearch(prefix, 0) { node -> node.getAllData(list) } }
    TODO("return a lazy iterator and rewrite the method in more efficient way")

  override fun contains(element: String): Boolean =
    myRoot.customSearch(element, 0, PrefixTreeNode::isWordEnd)

  override fun containsAll(elements: Collection<String>): Boolean =
    elements.all(this::contains) // No need for using sequences

  override fun isEmpty(): Boolean =
    myRoot.isEmpty()

  override fun add(element: String): Boolean =
    myRoot.insert(element)

  override fun addAll(elements: Collection<String>): Boolean =
    elements.asSequence().map(this::add).fold(false) { acc, element -> acc or element }

  override fun remove(element: String): Boolean =
    myRoot.remove(element)

  override fun removeAll(elements: Collection<String>): Boolean =
    elements.asSequence().map(this::remove).fold(false) { acc, element -> acc or element }

  override fun retainAll(elements: Collection<String>): Boolean {
    TODO("not implemented")
  }

  override fun clear() =
    myRoot.clear()

  override fun iterator(): MutableIterator<String> {
    TODO("not implemented")
  }

  override fun toString(): String =
    "Prefix tree:$myRoot"
}
