package borsk.editorconfig.collections

import borsk.editorconfig.collections.CharMap.Companion.Dash
import borsk.editorconfig.collections.CharMap.Companion.Digits
import borsk.editorconfig.collections.CharMap.Companion.FirstDigit
import borsk.editorconfig.collections.CharMap.Companion.FirstLowercase
import borsk.editorconfig.collections.CharMap.Companion.FirstUppercase
import borsk.editorconfig.collections.CharMap.Companion.LettersCount
import borsk.editorconfig.collections.CharMap.Companion.LowercaseLetters
import borsk.editorconfig.collections.CharMap.Companion.NumberOfDigits
import borsk.editorconfig.collections.CharMap.Companion.NumberOfUppercaseLetters
import borsk.editorconfig.collections.CharMap.Companion.Underscore
import borsk.editorconfig.collections.CharMap.Companion.UppercaseLetters
import java.util.*

internal class LazyCharMap<E>(supplier: (Int) -> Array<E?>) : CharMap<E> {
  internal val myValuesDelegate = lazy { supplier(LettersCount) }
  private val myValues: Array<E?> by myValuesDelegate
  private var myModCount = 0

  override operator fun get(index: Char): E? {
    return if (myValuesDelegate.isInitialized()) {
      myValues[actualIndex(index)]
    } else {
      null
    }
  }

  override operator fun set(index: Char, value: E) {
    val actual = actualIndex(index)
    if (myValues[actual] != value) {
      myValues[actual] = value
      myModCount += 1
    }
  }

  override fun delete(index: Char) {
    if (myValuesDelegate.isInitialized()) {
      val actual = actualIndex(index)
      if (myValues[actual] != null) {
        myValues[actual] = null
        myModCount += 1
      }
    }
  }

  override fun clear() {
    if (myValuesDelegate.isInitialized()) {
      repeat(LettersCount) {
        myValues[it] = null
      }
      myModCount += 1
    }
  }

  override fun isEmpty(): Boolean =
    !myValuesDelegate.isInitialized() || myValues.all { it == null }

  override operator fun iterator(): Iterator<E> {
    return LazyCharMapIterator(this)
  }

  private class LazyCharMapIterator<E>(val map: LazyCharMap<E>) : Iterator<E> {
    val myExpectedModCount = map.myModCount

    var currentIndex: Int = -1
      set(value) {
        field = value
        nextIndex = calculateNextIndex()
      }

    var nextIndex: Int? = calculateNextIndex()

    override fun hasNext(): Boolean {
      checkModCount()
      return nextIndex != null
    }

    override fun next(): E {
      checkModCount()
      currentIndex = nextIndex ?: throw NoSuchElementException()
      return map.myValues[currentIndex] ?: throw ConcurrentModificationException()
    }

    fun calculateNextIndex(): Int? {
      checkModCount()
      if (!map.myValuesDelegate.isInitialized() || currentIndex >= LettersCount) {
        return null
      }

      for (index in currentIndex + 1 until LettersCount) {
        if (map.myValues[index] != null) {
          return index
        }
      }
      return null
    }

    fun checkModCount() {
      if (myExpectedModCount != map.myModCount) {
        throw ConcurrentModificationException()
      }
    }
  }

  companion object {
    inline operator fun <reified T> invoke() =
      LazyCharMap { Array<T?>(it) { null } }

    fun actualIndex(index: Char): Int {
      return when (index) {
        Dash -> 0
        in Digits -> 1 + (index - FirstDigit)
        in UppercaseLetters -> 1 + NumberOfDigits + (index - FirstUppercase)
        Underscore -> 1 + NumberOfDigits + NumberOfUppercaseLetters
        in LowercaseLetters -> 1 + NumberOfDigits + NumberOfUppercaseLetters + 1 + (index - FirstLowercase)
        else -> throw UnsupportedCharacterException()
      }
    }
  }
}
