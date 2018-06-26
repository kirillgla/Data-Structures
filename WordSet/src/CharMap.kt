package borsk.editorconfig.collections

/**
 * Maps valid characters to matching elements
 */
internal interface CharMap<E> : Iterable<E> {
  operator fun get(index: Char): E?

  operator fun set(index: Char, value: E)
  fun delete(index: Char)
  fun clear()
  fun isEmpty(): Boolean

  companion object {
    const val FirstUppercase = 'A'
    private const val LastUppercase = 'Z'
    val UppercaseLetters = FirstUppercase..LastUppercase

    const val NumberOfUppercaseLetters = LastUppercase - FirstUppercase + 1

    const val FirstLowercase = 'a'
    private const val LastLowercase = 'z'
    val LowercaseLetters = FirstLowercase..LastLowercase

    const val NumberOfLowercaseLetters = LastLowercase - FirstLowercase + 1

    const val FirstDigit = '0'
    private const val LastDigit = '9'
    val Digits = FirstDigit..LastDigit

    const val NumberOfDigits = LastDigit - FirstDigit + 1

    const val Underscore = '_'
    const val Dash = '-'

    val AllLetters = ArrayList<Char>().apply {
      add(Dash)
      addAll(Digits)
      addAll(UppercaseLetters)
      add(Underscore)
      addAll(LowercaseLetters)
    }

    val LettersCount = AllLetters.size
  }
}
