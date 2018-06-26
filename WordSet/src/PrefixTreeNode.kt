package borsk.editorconfig.collections

import borsk.editorconfig.collections.CharMap.Companion.AllLetters
import java.lang.IllegalArgumentException

// node is left without explicit information
// about the character it represents, if any
internal class PrefixTreeNode internal constructor(
  private val myChildren: CharMap<PrefixTreeNode> = LazyCharMap(),
  private var myData: String? = null
) {

  val isWordEnd
    get() = myData != null

  val subTreeSize: Int
    get() = myChildren.map(PrefixTreeNode::subTreeSize).sum() + if (isWordEnd) 1 else 0

  fun isEmpty(): Boolean =
    myChildren.none()

  fun clear() {
    myData = null
    AllLetters.forEach(myChildren::delete)
  }

  /**
   * Performs search for [string]
   * assuming current node represents [string][[next] - 1]
   * if search succeeds, executes requested action and returns it's result;
   * otherwise return false
   */
  fun customSearch(string: String, next: Int, onStringEnd: (PrefixTreeNode) -> Boolean): Boolean {
    return when (next) {
      string.length ->
        onStringEnd(this)

      in 0 until string.length ->
        myChildren[string[next]]?.customSearch(string, next + 1, onStringEnd) ?: false

      else ->
        throw IllegalArgumentException("next")
    }
  }

  /**
   * assuming current node represents [string][[next] - 1]
   * Returns whether collection was modified or not
   */
  fun insert(string: String, next: Int = 0): Boolean {
    return when (next) {
      string.length ->
        myData?.let { false } ?: run { myData = string; true }

      in 0 until string.length -> {
        val nextChar = string[next]
        (myChildren[nextChar] ?: PrefixTreeNode().also { myChildren[nextChar] = it })
          .insert(string, next + 1)
      }

      else -> throw IllegalArgumentException("next")
    }
  }

  /**
   * Fetches all the data from subtree.
   * Returns whether any object was added to [destination] or not
   */
  private fun getAllData(destination: MutableList<String>): Boolean {
    var result = false
    myData?.let { notNullData -> result = destination.add(notNullData) }
    myChildren.forEach {
      result = result or it.getAllData(destination)
    }
    return result
  }

  /**
   * Deletes [string] from prefix tree
   * assuming current node represents [string][[next - 1]].
   * Returns whether collection has been modified or not
   */
  fun remove(string: String, next: Int = 0): Boolean =
    when (next) {
      string.length -> myData?.let { myData = null; true } ?: false

      in 0 until string.length -> {
        val nextChar = string[next]
        myChildren[nextChar]?.let { child ->
          child.remove(string, next + 1)
            .also { result ->
              if (result && child.myChildren.any())
                myChildren.delete(nextChar)
            }
        } ?: false
      }

      else -> throw IllegalArgumentException("next")
    }

  /**
   * Note: this method is very wasteful
   * and should only be used in debugging purposes
   */
  override fun toString(): String {
    var result = "{"
    if (isWordEnd) {
      result += "data;"
    }
    for (char in AllLetters) {
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
