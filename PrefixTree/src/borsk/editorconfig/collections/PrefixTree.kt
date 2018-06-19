package borsk.editorconfig.collections

class PrefixTree : WordSet {
  private val children: CharMap<PrefixTreeNode> = SimpleCharMap<PrefixTreeNode> { Array(it) { null } }

  override var size: Int = 0
    private set

  override fun containsPrefix(prefix: String): Boolean =
    children[prefix[0]]?.containsPrefix(prefix, 0) ?: false

  override fun getWordsStartingWith(prefix: String): List<String> =
    ArrayList<String>().also { children[prefix[0]]?.getWordsStartingWith(prefix, it) }

  override fun add(element: String): Boolean {
    TODO("not implemented")
  }

  override fun addAll(elements: Collection<String>): Boolean {
    TODO("not implemented")
  }

  override fun clear() {
    TODO("not implemented")
  }

  override fun iterator(): MutableIterator<String> {
    TODO("not implemented")
  }

  override fun remove(element: String): Boolean {
    TODO("not implemented")
  }

  override fun removeAll(elements: Collection<String>): Boolean {
    TODO("not implemented")
  }

  override fun retainAll(elements: Collection<String>): Boolean {
    TODO("not implemented")
  }

  override fun contains(element: String): Boolean {
    TODO("not implemented")
  }

  override fun containsAll(elements: Collection<String>): Boolean {
    TODO("not implemented")
  }

  override fun isEmpty(): Boolean {
    TODO("not implemented")
  }
}
