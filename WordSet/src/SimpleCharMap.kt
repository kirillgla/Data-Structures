package borsk.editorconfig.collections

import borsk.editorconfig.collections.CharMap.Companion.FirstLowercase
import borsk.editorconfig.collections.CharMap.Companion.FirstUppercase
import borsk.editorconfig.collections.CharMap.Companion.LettersInAlphabet
import borsk.editorconfig.collections.CharMap.Companion.LowercaseLetters
import borsk.editorconfig.collections.CharMap.Companion.Underscore
import borsk.editorconfig.collections.CharMap.Companion.UppercaseLetters
import java.util.*
import kotlin.NoSuchElementException

class SimpleCharMap<E>(supplier: (value: Int) -> Array<E?>) : CharMap<E> {
  private val myUppercaseValues: Array<E?> = supplier(LettersInAlphabet)
  private val myLowercaseValues: Array<E?> = supplier(LettersInAlphabet)
  private var myUnderscoreValue: E? = null

  private var myModCount = 0

  override operator fun get(index: Char): E? =
    when (index) {
      in UppercaseLetters -> myUppercaseValues[index - FirstUppercase]
      in LowercaseLetters -> myLowercaseValues[index - FirstLowercase]
      Underscore -> myUnderscoreValue
      else -> null
    }

  override operator fun set(index: Char, value: E) {
    when (index) {
      in UppercaseLetters -> {
        if (myUppercaseValues[index - FirstUppercase] != value) {
          myUppercaseValues[index - FirstUppercase] = value
          myModCount += 1
        }
      }
      in LowercaseLetters -> {
        if (myLowercaseValues[index - FirstLowercase] != value) {
          myLowercaseValues[index - FirstLowercase] = value
          myModCount += 1
        }
      }
      Underscore -> {
        if (myUnderscoreValue != value) {
          myUnderscoreValue = value
          myModCount += 1
        }
      }
      else -> throw UnsupportedCharacterException()
    }
  }

  override fun delete(index: Char) {
    myModCount += 1
    when (index) {
      in UppercaseLetters -> myUppercaseValues[index - FirstUppercase] = null
      in LowercaseLetters -> myLowercaseValues[index - FirstLowercase] = null
      Underscore -> myUnderscoreValue = null
    }
  }

  override fun clear() {
    myModCount++
    for (letter in UppercaseLetters)
      myUppercaseValues[letter - FirstUppercase] = null
    for (letter in LowercaseLetters)
      myLowercaseValues[letter - FirstLowercase] = null
    myUnderscoreValue = null
  }

  override fun isEmpty(): Boolean =
    myUppercaseValues.all { it == null }
      && myLowercaseValues.all { it == null }
      && myUnderscoreValue == null

  override fun iterator(): Iterator<E> =
    SimpleCharMapIterator(this)

  companion object {
    inline operator fun <reified T> invoke(): SimpleCharMap<T> {
      return SimpleCharMap<T> { Array(it) { null } }
    }
  }

  /**
   * Iterates over map elements.
   * Doesn't support concurrent modifications.
   * Early binding.
   */
  private class SimpleCharMapIterator<E>(private val map: SimpleCharMap<E>) : Iterator<E> {
    private val myExpectedModCount = map.myModCount
    private var currentIndex: Char = 0.toChar()

    /**
     * returns index of next non-null element.
     * Note: this property can be optimized
     */
    private val nextIndex: Char?
      get() = findUppercaseIndex(currentIndex)
        ?: findLowercaseIndex(currentIndex)
        ?: findUnderscoreIndex(currentIndex)

    override fun hasNext(): Boolean {
      checkModCount()
      return nextIndex != null
    }

    override fun next(): E {
      checkModCount()
      currentIndex = nextIndex ?: throw NoSuchElementException()
      return map[currentIndex]!!
    }

    private fun findUppercaseIndex(start: Char): Char? =
      map.myUppercaseValues
        .mapIndexedNotNull { index, element ->
          if (element == null || index <= start - FirstUppercase) null
          else index
        }
        .firstOrNull()?.plus(FirstLowercase.toInt())?.toChar()

    private fun findLowercaseIndex(start: Char): Char? =
      map.myLowercaseValues
        .mapIndexedNotNull { index, element ->
          if (element == null || index <= start - FirstLowercase) null
          else index
        }
        .firstOrNull()?.plus(FirstLowercase.toInt())?.toChar()

    private fun findUnderscoreIndex(start: Char): Char? =
      if (map.myUnderscoreValue == null || Underscore <= start) null
      else Underscore

    private fun checkModCount() {
      if (myExpectedModCount != map.myModCount)
        throw ConcurrentModificationException()
    }
  }
}
