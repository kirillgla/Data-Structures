package borsk.editorconfig.collections

import java.util.*

class PlainWordSet : WordSet {
  private val myWords: NavigableSet<String> = TreeSet<String>()

  override val size
    get() = myWords.size

  // Implementation note: prefix substring is always
  // lexicographically smaller than or equal to whole string,
  override fun containsPrefix(prefix: String) =
    myWords.ceiling(prefix)?.startsWith(prefix) ?: false

  override fun getWordsStartingWith(prefix: String) =
    myWords.tailSet(prefix).takeWhile { it.startsWith(prefix) }

  // TODO: get rid of the following code
  // the following code is not informative.
  // I'm almost sure kotlin provides some way of getting rid of it
  // companion objects might be the answer, idk what's that

  override fun add(element: String?) =
    myWords.add(element)

  override fun addAll(elements: Collection<String>) =
    myWords.addAll(elements)

  override fun clear() =
    myWords.clear()

  override fun iterator() =
    myWords.iterator()

  override fun remove(element: String?) =
    myWords.remove(element)

  override fun removeAll(elements: Collection<String>) =
    myWords.removeAll(elements)

  override fun retainAll(elements: Collection<String>) =
    myWords.retainAll(elements)

  override fun contains(element: String?) =
    myWords.contains(element)

  override fun containsAll(elements: Collection<String>) =
    myWords.containsAll(elements)

  override fun isEmpty() =
    myWords.isEmpty()
}
