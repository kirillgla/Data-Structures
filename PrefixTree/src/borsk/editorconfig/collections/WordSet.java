package borsk.editorconfig.collections;

import java.util.List;
import java.util.Set;

/**
 * An interface that adds some handy methods to set of strings
 */
public interface WordSet extends Set<String> {
  /**
   * Determines whether or not set contains
   * at least one string starting with prefix
   *
   * @param prefix beginning of string to look for
   * @return whether set contains string starting with prefix or not
   */
  boolean containsPrefix(String prefix);

  /**
   * Acquires all strings in set that start with given prefix
   *
   * @param prefix beginning of every word in resulting collection
   * @return stream in which every word starts with given prefix
   */
  List<String> getWordsStartingWith(String prefix);
}
