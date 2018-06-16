package borsk.editorconfig.collections;

import java.util.*;

public final class PlainWordSet implements WordSet {
    private final NavigableSet<String> myWords;

    public PlainWordSet() {
        myWords = new TreeSet<>();
    }

    @Override
    public boolean containsPrefix(String prefix) {
        // Note: prefix substring is always
        // lexicographically smaller than or equal to whole string
        // Also, this functional-style solution is a little weird
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
    public boolean contains(Object object) {
        return myWords.contains(object);
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
    public <T> T[] toArray(T[] array) {
        //noinspection SuspiciousToArrayCall
        return myWords.toArray(array);
        // This warning is lifted to caller anyway,
        // so there's no need to double-check that T is correct
    }

    @Override
    public boolean add(String string) {
        return myWords.add(string);
    }

    @Override
    public boolean remove(Object object) {
        return myWords.remove(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return myWords.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends String> collection) {
        return myWords.containsAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return myWords.retainAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return myWords.removeAll(collection);
    }

    @Override
    public void clear() {
        myWords.clear();
    }
}
