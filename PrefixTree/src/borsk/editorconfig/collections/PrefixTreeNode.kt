package borsk.editorconfig.collections

/**
 * This class represents a character in word;
 * although the character is never explicitly stored.
 * The character represented is inferred
 * according to where the node is stored
 */
class PrefixTreeNode {
  var isWordEnd = false
    private set

  private val children: CharMap<PrefixTreeNode> = SimpleCharMap<PrefixTreeNode> { Array(it) { null } }

  /**
   * Searches for [prefix] in current subtree.
   * [prefix] [[current]] is assumed to be
   * equal to character represented by current node
   */
  fun containsPrefix(prefix: String, current: Int): Boolean =
    current >= prefix.lastIndex || (children[prefix[current + 1]]?.containsPrefix(prefix, current + 1) ?: false)

  fun getWordsStartingWith(prefix: String, list: MutableList<String>) {

  }

  /**
   * Fills list with all strings in subtree.
   * StringBuilder is assumed to contain
   * all characters up to the one represented by this node, inclusive
   * Note that in concern of performance
   * it is highly recommended to provide [StringBuilder]
   * with proper initial capacity
   */
  private fun getSubTreeWords(prefix: StringBuilder, list: MutableList<String>) {
    if (isWordEnd)
      list.add(prefix.toString())

    prefix.append(' ') // Any character
    for (char in CharMap.allLetters) {
      prefix[prefix.lastIndex] = char
      children[char]?.getSubTreeWords(prefix, list)
    }
    prefix.setLength(prefix.length - 1)
  }
}

