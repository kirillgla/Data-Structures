package borsk.editorconfig.collections

import borsk.editorconfig.collections.CharMap.Companion.Dash
import borsk.editorconfig.collections.CharMap.Companion.Digits
import borsk.editorconfig.collections.CharMap.Companion.FirstDigit
import borsk.editorconfig.collections.CharMap.Companion.FirstLowercase
import borsk.editorconfig.collections.CharMap.Companion.FirstUppercase
import borsk.editorconfig.collections.CharMap.Companion.LettersInAlphabet
import borsk.editorconfig.collections.CharMap.Companion.LowercaseLetters
import borsk.editorconfig.collections.CharMap.Companion.NumberOfDigits
import borsk.editorconfig.collections.CharMap.Companion.Underscore
import borsk.editorconfig.collections.CharMap.Companion.UppercaseLetters
import java.util.*
import kotlin.NoSuchElementException

class SimpleCharMap<E>(supplier: (value: Int) -> Array<E?>) : CharMap<E> {
  private val myUppercaseValues: Array<E?> = supplier(LettersInAlphabet)
  private val myLowercaseValues: Array<E?> = supplier(LettersInAlphabet)
  private val myDigitsValues: Array<E?> = supplier(NumberOfDigits)
  private var myUnderscoreValue: E? = null
  private var myDashValue: E? = null

  private var myModCount = 0

  override operator fun get(index: Char): E? =
    when (index) {
      in UppercaseLetters -> myUppercaseValues[index - FirstUppercase]
      in LowercaseLetters -> myLowercaseValues[index - FirstLowercase]
      in Digits -> myDigitsValues[index - FirstDigit]
      Underscore -> myUnderscoreValue
      Dash -> myDashValue
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

      in Digits -> {
        if (myDigitsValues[index - FirstDigit] != value) {
          myDigitsValues[index - FirstDigit] = value
          myModCount += 1
        }
      }

      Underscore -> {
        if (myUnderscoreValue != value) {
          myUnderscoreValue = value
          myModCount += 1
        }
      }

      Dash -> {
        if (myDashValue != value) {
          myDashValue = value
          myModCount += 1
        }
      }

      else -> throw UnsupportedCharacterException()
    }
  }

  override fun delete(index: Char) {
    when (index) {
      in UppercaseLetters -> {
        if (myUppercaseValues[index - FirstUppercase] != null) {
          myUppercaseValues[index - FirstUppercase] = null
          myModCount += 1
        }
      }

      in LowercaseLetters -> {
        if (myLowercaseValues[index - FirstLowercase] != null) {
          myLowercaseValues[index - FirstLowercase] = null
          myModCount += 1
        }
      }

      in Digits -> {
        if (myDigitsValues[index - FirstDigit] != null) {
          myDigitsValues[index - FirstDigit] = null
          myModCount += 1
        }
      }

      Underscore -> {
        if (myUnderscoreValue != null) {
          myUnderscoreValue = null
          myModCount += 1
        }
      }

      Dash -> {
        if (myDashValue != null) {
          myDashValue = null
          myModCount += 1
        }
      }
    }
  }

  override fun clear() {
    if (!isEmpty()) {
      myModCount++
    }

    for (letter in UppercaseLetters)
      myUppercaseValues[letter - FirstUppercase] = null
    for (letter in LowercaseLetters)
      myLowercaseValues[letter - FirstLowercase] = null
    for (letter in Digits) {
      myDigitsValues[letter - FirstDigit] = null
    }
    myUnderscoreValue = null
    myDashValue = null
  }

  override fun isEmpty(): Boolean =
    myUppercaseValues.all { it == null }
      && myLowercaseValues.all { it == null }
      && myDigitsValues.all { it == null }
      && myUnderscoreValue == null
      && myDashValue == null

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
      set(value) {
        field = value
        nextIndex = getNextIndex()
      }
    private var nextIndex: Char? = null

    /**
     * TODO: optimize this function
     */
    private fun getNextIndex(): Char? =
      findDashIndex(currentIndex)
        ?: findDigitIndex(currentIndex)
        ?: findUppercaseIndex(currentIndex)
        ?: findUnderscoreIndex(currentIndex)
        ?: findLowercaseIndex(currentIndex)

    override fun hasNext(): Boolean {
      checkModCount()
      return nextIndex != null
    }

    override fun next(): E {
      checkModCount()
      currentIndex = nextIndex ?: throw NoSuchElementException()
      return map[currentIndex] ?: throw ConcurrentModificationException()
    }

    // TODO: rewrite in a more straightforward imperative style
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

    private fun findDigitIndex(start: Char): Char? =
      map.myDigitsValues
        .mapIndexedNotNull { index, element ->
          if (element == null || index <= start - FirstDigit) null
          else index
        }
        .firstOrNull()?.plus(FirstDigit.toInt())?.toChar()

    private fun findUnderscoreIndex(start: Char): Char? =
      if (map.myUnderscoreValue == null || Underscore <= start) null
      else Underscore

    private fun findDashIndex(start: Char): Char? =
      if (map.myDashValue == null || Dash <= start) null
      else Dash

    private fun checkModCount() {
      if (myExpectedModCount != map.myModCount)
        throw ConcurrentModificationException()
    }
  }
}
