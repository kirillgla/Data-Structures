package borsk.editorconfig.collections

import borsk.editorconfig.collections.CharMap.Companion.allLetters
import java.lang.IllegalArgumentException

/**
 * Represents a character in [PrefixTree].
 */
internal open class PrefixTreeNode {
  // The odd decision was to leave the node
  // without information about the character it represents
  protected val myChildren: CharMap<PrefixTreeNode> = SimpleCharMap()
  var data: String? = null
    internal set

  val isWordEnd
    get() = data != null

  val subTreeSize: Int
    get() = myChildren.map { it.subTreeSize }.sum() + if (isWordEnd) 1 else 0

  /**
   * Performs search for [string] assuming current node represents [string][[next] - 1]
   * and executes requested action if search succeeds.
   */
  protected fun customSearch(string: String, next: Int, onStringEnd: (PrefixTreeNode) -> Boolean): Boolean =
    // FIXME: I renamed parameter to 'next' but did not update usages
    when (next) {
      string.length - 1 ->
        onStringEnd(this)

      in 0 until string.length - 1 -> {
        val nextIndex = next + 1
        val nextChar = string[nextIndex]
        myChildren[nextChar]?.customSearch(string, nextIndex, onStringEnd) ?: false
      }

      else ->
        throw IllegalArgumentException("next")
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

  /**
   * Fetches all the data from subtree.
   * Returns whether any object was added to [destination] or not
   */
  fun getAllData(destination: MutableList<String>): Boolean {
    var result = false
    data?.let { notNullData -> result = destination.add(notNullData) }
    myChildren.forEach {
      result = result or it.getAllData(destination)
    }
    return result
  }

  /**
   * Deletes [string] from prefix tree.
   * Current node represents [string][[current]].
   * Returns whether collection has been modified or not
   */
  fun remove(string: String, current: Int): Boolean =
    when (current) {
      in 0 until string.length - 1 -> {
        val nextIndex = current + 1
        val nextChar = string[nextIndex]
        myChildren[nextChar]?.let { child ->
          child.remove(string, nextIndex).also { result ->
            if (result && child.myChildren.any())
              myChildren.delete(nextChar)
          }
        } ?: false
      }

      string.length - 1 -> data?.let { data = null; true } ?: false

      else -> throw IllegalArgumentException("current")
    }

  fun isEmpty(): Boolean =
    myChildren.none()

  /**
   * Returns text representation of current object.
   * Note: this method is very wasteful
   * and should only be used in debugging purposes
   */
  override fun toString(): String {
    var result = "{"
    if (isWordEnd) {
      result += "data;"
    }
    for (char in allLetters) {
      val child = myChildren[char]
      if (child != null) {
        result += char
        result += ":"
        result += child.toString()
      }
    }
    result += "};"
    return result
  }
}
