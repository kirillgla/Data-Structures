package borsk.editorconfig.collections

class EagerTreeWordSet: TreeWordSet() {
  override fun getAllWithPrefix(prefix: String): Sequence<String> =
    tailSet(prefix).takeWhile { it.startsWith(prefix) }.asSequence()
}
