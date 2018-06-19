package borsk.editorconfig.collections

import borsk.editorconfig.collections.CharMap.Companion.FirstLowercase
import borsk.editorconfig.collections.CharMap.Companion.FirstUppercase
import borsk.editorconfig.collections.CharMap.Companion.LettersInAlphabet
import borsk.editorconfig.collections.CharMap.Companion.LowercaseLetters
import borsk.editorconfig.collections.CharMap.Companion.Underscore
import borsk.editorconfig.collections.CharMap.Companion.UppercaseLetters

class SimpleCharMap<E>(supplier: (value: Int) -> Array<E?>) : CharMap<E> {
  private val myUppercaseValues: Array<E?> = supplier(LettersInAlphabet)
  private val myLowercaseValues: Array<E?> = supplier(LettersInAlphabet)
  private var myUnderscoreValue: E? = null

  override operator fun get(index: Char): E? =
    when (index) {
      in UppercaseLetters -> myUppercaseValues[index - FirstUppercase]
      in LowercaseLetters -> myLowercaseValues[index - FirstLowercase]
      Underscore -> myUnderscoreValue
      else -> null
    }

  override operator fun set(index: Char, value: E) =
    when (index) {
      in UppercaseLetters -> myUppercaseValues[index - FirstUppercase] = value
      in LowercaseLetters -> myLowercaseValues[index - FirstLowercase] = value
      Underscore -> myUnderscoreValue = value
      else -> throw UnsupportedCharacterException()
    }
}
