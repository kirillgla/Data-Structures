package borsk.editorconfig.collections

import kotlin.coroutines.experimental.buildSequence

internal fun PrefixTreeNode.getDataSequence(expectedModCount: Int, tree: PrefixTree): Sequence<String> {
  tree.checkModCount(expectedModCount)
  return buildSequence {
    data?.let { yield(it) }
    children.forEach {
      yieldAll(it.getDataSequence(expectedModCount, tree))
    }
  }
}

internal fun PrefixTreeNode.getDataList(): List<String> {
  val list: MutableList<String> = ArrayList()

  fun retrieveData(node: PrefixTreeNode) {
    node.data?.let(list::add)
    node.children.forEach(::retrieveData)
  }

  retrieveData(this)

  return list
}
