package borsk.editorconfig.collections

class LazyTreeWordSet: TreeWordSet() {
  override fun getAllWithPrefix(prefix: String): Sequence<String> =
    tailSet(prefix).asSequence().takeWhile { it.startsWith(prefix) }
}
