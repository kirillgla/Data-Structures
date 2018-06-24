package borsk.editorconfig.collections

internal class PrefixTreeRoot: PrefixTreeNode() {
  fun find(string: String, start: Int = 0): Boolean =
    if (string.isEmpty()) isWordEnd
    else customSearch(string, start) { it.isWordEnd }

  fun findPrefix(string: String, start: Int = 0): Boolean =
    customSearch(string, start) { true }

  // TODO: return a lazy iterator and rewrite the method in more efficient way
  fun findAllPrefixed(prefix: String, start: Int = 0): List<String> =
    ArrayList<String>().also { list -> customSearch(prefix, start) { node -> node.getAllData(list) } }
}
