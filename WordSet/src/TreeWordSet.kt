package borsk.editorconfig.collections

import java.util.*

class TreeWordSet : TreeSet<String>(), MutableWordSet {
  override fun containsPrefix(prefix: String): Boolean =
    ceiling(prefix)?.startsWith(prefix) ?: false

  override fun getWordsStartingWith(prefix: String): List<String> =
    tailSet(prefix).takeWhile { it.startsWith(prefix) }
}