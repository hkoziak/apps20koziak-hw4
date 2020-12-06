package ua.edu.ucu.autocomplete;

import ua.edu.ucu.iters.RWayTrieWIterator;
import ua.edu.ucu.tries.Tuple;
import ua.edu.ucu.tries.Trie;

public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int added = 0;
        for (String stringLst: strings) {
            String[] words = stringLst.split(" ");
            for (String word: words) {
                trie.add(new Tuple(word, word.length()));
                added += 1;
            }
        }
        return added;
    }

    public boolean contains(String word) {
        return this.trie.contains(word);
    }

    public boolean delete(String word) {
        return this.trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() >= 2) {
            return this.trie.wordsWithPrefix(pref);
        }
        return null;
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() >= 2 && k > 0) {
            return RWayTrieWIterator.rWayTrieWIterable(this.trie, pref, k);
        }
        return null;
    }

    public int size() {
        return this.trie.size();
    }
}
