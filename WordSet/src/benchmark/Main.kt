package borsk.editorconfig.collections.benchmark

import borsk.editorconfig.collections.CharMap
import borsk.editorconfig.collections.PrefixTree
import borsk.editorconfig.collections.TreeWordSet
import java.util.*

const val Size = 10
const val MaxWordLength = 5
const val MaxSearchWordLength = MaxWordLength / 2

val random = Random()

val content = Array(Size) { getRandomString(MaxWordLength) }

val prefixTree = PrefixTree().apply { content.forEach { this.add(it) } }
val treeWordSet = TreeWordSet().apply { content.forEach { this.add(it) } }

val searchContent = Array(Size) { getRandomString(MaxSearchWordLength) }

private var sizeSum = 0

fun getRandomString(max: Int): String {
  val wordLength = random.nextInt(max)
  val builder = StringBuilder(wordLength)

  for (index in 1..wordLength) {
    builder.append(getRandomChar())
  }

  return builder.toString()
}

fun getRandomChar(): Char {
  val index = random.nextInt(CharMap.LettersCount)
  return CharMap.AllLetters[index]
}

fun main(args: Array<String>) {
  val numbers = (1..100).toList()

  val list = numbers
    .filter { it % 16 == 0 }
    .also(::println)
    .map { "0x" + it.toString(16) }
    .forEach(::println)
}

/* Winners in:
 *
 * PrefixTree:
 *     containsPrefix
 *
 * TreeWordSet:
 *     add
 *     contains
 *     getContinuationSequence
 *     getContinuationIterable
 */

fun fillPrefixTree() {
  val tree = PrefixTree()
  content.forEach { tree.add(it) }
  sizeSum += tree.size
}

fun fillTreeWordSet() {
  val tree = TreeWordSet()
  content.forEach { tree.add(it) }
  sizeSum += tree.size
}

fun searchInPrefixTree() {
  content.forEach { if (it !in prefixTree) throw IllegalStateException() }
}

fun searchInTreeWordSet() {
  content.forEach { if (it !in treeWordSet) throw IllegalStateException() }
}

fun findPrefixInPrefixTree() {
  searchContent.map(prefixTree::containsPrefix)
}

fun findPrefixInTreeWordSet() {
  searchContent.map(treeWordSet::containsPrefix)
}

fun getContinuationSequenceInPrefixTree() {
  searchContent.map(prefixTree::getContinuationSequence).take(12).forEach {}
}

fun getContinuationSequenceInTreeWordSet() {
  searchContent.map(treeWordSet::getContinuationSequence).take(12).forEach {}
}

fun getContinuationIterableInPrefixTree() {
  searchContent.map(prefixTree::getContinuationIterable).take(12).forEach {}
}

fun getContinuationIterableInTreeWordSet() {
  searchContent.map(treeWordSet::getContinuationIterable).take(12).forEach {}
}
