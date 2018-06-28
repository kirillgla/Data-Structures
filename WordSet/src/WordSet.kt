package borsk.editorconfig.collections

/**
 * A set of words that provides string query operations
 */
interface WordSet : Set<String> {
  /**
   * Determines whether or not [WordSet] contains
   * at least one string starting with [prefix]
   */
  fun containsPrefix(prefix: String): Boolean

  /**
   * Lazily acquires all strings in [WordSet] that start with [prefix]
   */
  fun getContinuationSequence(prefix: String): Sequence<String>

  /**
   * Eagerly acquires all strings in [WordSet] that start with [prefix]
   */
  fun  getContinuationIterable(prefix: String): Iterable<String>
}
