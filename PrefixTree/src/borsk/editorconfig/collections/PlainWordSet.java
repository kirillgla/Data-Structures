package borsk.editorconfig.collections;

import java.util.*;


public class PlainWordSet implements WordSet {
  private NavigableSet<String> myWords;

  public PlainWordSet() {
    myWords = new TreeSet<>();
  }

  @Override
  public boolean containsPrefix(String prefix) {
    // Note: prefix string is always
    // lexicographically smaller than or equal to whole string
    return Optional.ofNullable(myWords.ceiling(prefix))
      .map(string -> string.startsWith(prefix))
      .orElse(false);
  }

  @Override
  public int size() {
    return myWords.size();
  }

  @Override
  public boolean isEmpty() {
    return myWords.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return myWords.contains(o);
  }

  @Override
  public Iterator<String> iterator() {
    return myWords.iterator();
  }

  @Override
  public Object[] toArray() {
    return myWords.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    //noinspection SuspiciousToArrayCall
    return myWords.toArray(a);
    // This warning is lifted to caller anyway,
    // so there's no need to double-check that T is correct
  }

  @Override
  public boolean add(String s) {
    return myWords.add(s);
  }

  @Override
  public boolean remove(Object o) {
    return myWords.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return myWords.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends String> c) {
    return myWords.containsAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return myWords.retainAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return myWords.removeAll(c);
  }

  @Override
  public void clear() {
    myWords.clear();
  }
}
