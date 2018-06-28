package borsk.editorconfig.collections

import borsk.editorconfig.collections.CharMap.Companion.AllLetters
import java.lang.IllegalArgumentException

// node is left without explicit information
// about the character it represents, if any
internal class PrefixTreeNode {

  internal val children: CharMap<PrefixTreeNode> = LazyCharMap()

  var data: String? = null
    set(value) {
      if (field == null && value != null) {
        subtreeSize += 1
      } else if (field != null && value == null) {
        subtreeSize -= 1
      }

      field = value
    }

  val isWordEnd
    get() = data != null

  var subtreeSize: Int = 0
    private set

  internal val actualSubtreeSize
    get() = children.map(PrefixTreeNode::subtreeSize).sum() + if (isWordEnd) 1 else 0

  fun isEmpty(): Boolean =
    subtreeSize == 0

  fun clear() {
    data = null
    children.clear()
    subtreeSize = 0
  }

  /**
   * Performs search for [string]
   * assuming current node represents [string][[next] - 1]
   * if search succeeds, executes requested action and returns it's result;
   * otherwise return false
   */
  fun customSearch(string: String, next: Int = 0, onStringEnd: (PrefixTreeNode) -> Boolean): Boolean = when (next) {
    string.length -> onStringEnd(this)
    in 0 until string.length -> children[string[next]]?.customSearch(string, next + 1, onStringEnd) ?: false
    else -> throw IllegalArgumentException("next")
  }

  /**
   * assuming current node represents [string][[next] - 1]
   * Returns whether collection was modified or not
   */
  fun insert(string: String, next: Int = 0): Boolean = when (next) {
    string.length -> {
      if (data == null) {
        data = string
        true
      } else {
        false
      }
    }

    in 0 until string.length -> {
      val nextChar = string[next]
      val node = children[nextChar] ?: PrefixTreeNode().also { children[nextChar] = it }
      val result = node.insert(string, next + 1)
      if (result) {
        subtreeSize += 1
      }

      result
    }

    else -> throw IllegalArgumentException("next")
  }

  /**
   * Deletes [string] from prefix tree
   * assuming current node represents [string][[next - 1]].
   * Returns whether collection has been modified or not
   */
  fun remove(string: String, next: Int = 0): Boolean {
    when (next) {
      string.length -> return data?.let { data = null; true } ?: false

      in 0 until string.length -> {
        val nextChar = string[next]
        val child = children[nextChar] ?: return false
        val result = child.remove(string, next + 1)
        if (result) {
          subtreeSize -= 1
          if (child.isEmpty()) {
            children.delete(nextChar)
          }
        }
        return result
      }

      else -> throw IllegalArgumentException("next")
    }
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
      val child = children[char]
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
