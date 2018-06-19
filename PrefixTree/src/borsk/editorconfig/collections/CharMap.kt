package borsk.editorconfig.collections

/**
 * Maps valid characters to matching elements
 */
interface CharMap<E> {
  operator fun get(index: Char): E?
  operator fun set(index: Char, value: E)

  companion object {
    const val LettersInAlphabet = 'Z' - 'A' + 1
    val UppercaseLetters = 'A'..'Z'
    val LowercaseLetters = 'a'..'z'
    const val FirstUppercase = 'A'
    const val FirstLowercase = 'a'
    const val Underscore = '_'

    val allLetters = UppercaseLetters.toList() + LowercaseLetters.toList() + Underscore
  }
}
