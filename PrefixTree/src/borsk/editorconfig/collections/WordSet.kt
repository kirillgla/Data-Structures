package borsk.editorconfig.collections

/**
 * A set of words that provides string query operations
 */
interface WordSet : MutableSet<String> {
  /**
   * Determines whether or not [WordSet] contains
   * at least one string starting with [prefix]
   */
  fun containsPrefix(prefix: String): Boolean

  /**
   * Acquires all strings in [WordSet] that start with [prefix]
   */
  fun getWordsStartingWith(prefix: String): List<String>
}
