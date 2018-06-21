package borsk.editorconfig.collections

import java.lang.IllegalArgumentException

internal class PrefixTreeNode {
  internal val myChildren: CharMap<PrefixTreeNode> = SimpleCharMap()
  var data: String? = null
    internal set

  val isWordEnd
    get() = data != null

  val subTreeSize: Int
    get() = myChildren.map { it.subTreeSize }.sum() + if (isWordEnd) 1 else 0

  fun find(string: String, current: Int) =
    customSearch(string, current) { it.isWordEnd }

  fun findPrefix(string: String, current: Int) =
    customSearch(string, current) { true }

  private fun customSearch(string: String, current: Int, onStringEnd: (PrefixTreeNode) -> Boolean): Boolean =
    when (current) {
      string.length - 1 ->
        onStringEnd(this)

      in 0 until string.length - 1 -> {
        val nextIndex = current + 1
        val nextChar = string[nextIndex]
        myChildren[nextChar]?.find(string, nextIndex) ?: false
      }

      else ->
        throw IllegalArgumentException("current")
    }

  fun add(string: String, current: Int): Boolean =
    when (current) {
      string.length - 1 ->
        if (data == null) {
          data = string
          true
        } else {
          false
        }

      in 0 until string.length - 1 -> {
        val nextIndex = current + 1
        val nextChar = string[nextIndex]
        val child = myChildren[nextChar] ?: PrefixTreeNode().also { myChildren[nextChar] = it }
        child.add(string, nextIndex)
      }

      else -> throw IllegalArgumentException("current")
    }
}
