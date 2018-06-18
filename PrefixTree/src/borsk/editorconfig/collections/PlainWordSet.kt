package borsk.editorconfig.collections

import java.util.*

class PlainWordSet : TreeSet<String>(), WordSet {
  override fun containsPrefix(prefix: String) =
    ceiling(prefix)?.startsWith(prefix) ?: false

  override fun getWordsStartingWith(prefix: String) =
    tailSet(prefix).takeWhile { it.startsWith(prefix) }
}
