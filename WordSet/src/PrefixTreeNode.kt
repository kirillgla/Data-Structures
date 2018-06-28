package borsk.editorconfig.collections

import borsk.editorconfig.collections.CharMap.Companion.AllLetters
import java.lang.IllegalArgumentException
import kotlin.coroutines.experimental.buildSequence

// node is left without explicit information
// about the character it represents, if any
internal class PrefixTreeNode {

  internal val myChildren: CharMap<PrefixTreeNode> = LazyCharMap()

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
    get() = myChildren.map(PrefixTreeNode::subtreeSize).sum() + if (isWordEnd) 1 else 0

  fun isEmpty(): Boolean =
    subtreeSize == 0

  fun clear() {
    data = null
    myChildren.clear()
    subtreeSize = 0
  }

  /**
   * Performs search for [string]
   * assuming current node represents [string][[next] - 1]
   * if search succeeds, executes requested action and returns it's result;
   * otherwise return false
   */
  fun customSearch(string: String, next: Int = 0, onStringEnd: (PrefixTreeNode) -> Boolean): Boolean {
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
        val node = myChildren[nextChar] ?: PrefixTreeNode().also { myChildren[nextChar] = it }
        val result = node.insert(string, next + 1)
        if (result) {
          subtreeSize += 1
        }

        result
      }

      else -> throw IllegalArgumentException("next")
    }
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
        val child = myChildren[nextChar] ?: return false
        val result = child.remove(string, next + 1)
        if (result) {
          subtreeSize -= 1
          if (child.isEmpty()) {
            myChildren.delete(nextChar)
          }
        }
        return result
      }

      else -> throw IllegalArgumentException("next")
    }
  }

  fun getDataSequence(expectedModCount: Int, tree: PrefixTree): Sequence<String> {
    tree.checkModCount(expectedModCount)
    return buildSequence {
      data?.let { yield(it) }
      myChildren.forEach {
        yieldAll(it.getDataSequence(expectedModCount, tree))
      }
    }
  }

  fun getDataList(): List<String> =
    ArrayList<String>(subtreeSize).also(this::getDataList)

  private fun getDataList(list: MutableList<String>) {
    data?.let(list::add)
    myChildren.forEach { it.getDataList(list) }
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
