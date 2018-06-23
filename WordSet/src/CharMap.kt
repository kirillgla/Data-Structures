package borsk.editorconfig.collections

/**
 * Maps valid characters to matching elements
 */
interface CharMap<E> : Iterable<E> {
  // TODO: implement lazy char map
  operator fun get(index: Char): E?

  operator fun set(index: Char, value: E)
  fun delete(index: Char)
  fun clear()
  fun isEmpty(): Boolean

  companion object {
    const val FirstUppercase = 'A'
    private const val LastUppercase = 'Z'
    val UppercaseLetters = FirstUppercase..LastUppercase

    const val FirstLowercase = 'a'
    private const val LastLowercase = 'z'
    val LowercaseLetters = FirstLowercase..LastLowercase

    const val LettersInAlphabet = LastUppercase - FirstUppercase + 1

    const val FirstDigit = '0'
    private const val LastDigit = '9'
    val Digits = FirstDigit..LastDigit

    const val NumberOfDigits = LastDigit - FirstDigit + 1

    const val Underscore = '_'
    const val Dash = '-'

    val allLetters = UppercaseLetters + LowercaseLetters + Digits + Underscore + Dash
  }
}
