package borsk.editorconfig.collections;

import java.util.Set;

public interface WordSet extends Set<String> {
    boolean containsPrefix(String word);
}
