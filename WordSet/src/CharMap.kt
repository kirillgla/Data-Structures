package borsk.editorconfig.collections

/**
 * Maps valid characters to matching elements
 */
interface CharMap<E>: Iterable<E> {
  // TODO: implement lazy char map
  operator fun get(index: Char): E?
  operator fun set(index: Char, value: E)
  fun delete(index: Char)
  fun clear()
  fun isEmpty(): Boolean

  companion object {
    const val LettersInAlphabet = 'Z' - 'A' + 1
    const val FirstUppercase = 'A'
    private const val LastUppercase = 'Z'
    const val FirstLowercase = 'a'
    private const val LastLowercase = 'z'
    val UppercaseLetters = FirstUppercase..LastUppercase
    val LowercaseLetters = FirstLowercase..LastLowercase
    const val Underscore = '_'

    // val allLetters = UppercaseLetters.toList() + LowercaseLetters.toList() + Underscore
  }
}
