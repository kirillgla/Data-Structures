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
   * Acquires all strings in [WordSet] that start with [prefix].
   * Some implementations may not be lazy
   */
  fun getAllWithPrefix(prefix: String): Sequence<String>
}
