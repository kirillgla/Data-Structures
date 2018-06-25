package borsk.editorconfig.collections

import java.util.*

abstract class TreeWordSet : TreeSet<String>(), MutableWordSet {
  override fun containsPrefix(prefix: String): Boolean =
    ceiling(prefix)?.startsWith(prefix) ?: false
}
