package borsk.editorconfig.collections

import java.util.*

class TreeWordSet : TreeSet<String>(), MutableWordSet {
  override fun containsPrefix(prefix: String): Boolean =
    ceiling(prefix)?.startsWith(prefix) ?: false

  override fun getContinuationSequence(prefix: String): Sequence<String> =
    tailSet(prefix).asSequence().takeWhile { it.startsWith(prefix) }

  override fun getContinuationIterable(prefix: String): Iterable<String> =
    tailSet(prefix).takeWhile { it.startsWith(prefix) }
}
