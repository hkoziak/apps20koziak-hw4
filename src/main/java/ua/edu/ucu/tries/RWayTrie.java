package ua.edu.ucu.tries;

import ua.edu.ucu.iters.RWayTrieIterator;
import ua.edu.ucu.tools.Node;

public class RWayTrie implements Trie{

    public static int LETTERS_NUM = 26;
    private int size = 0;
    public final Node start;

    public RWayTrie() {
        start = new Node('H');
    }

    @Override
    public void add(Tuple t) {
        String word = t.term;
        Object weight = t.weight;
        Node current = this.start;
        for (int i = 0; i < word.length(); i++) {
            current = current.addNext(word.charAt(i));
        }
        current.last = true;
        current.weight = weight;
        size += 1;
    }

    @Override
    public boolean contains(String word) {
        Node current = this.start;
        for (int i = 0; i < word.length(); i++) {
            if (current.next.containsKey(word.charAt(i))) {
                current = current.next.get(word.charAt(i));
            }
            else {
                return false;
            }
        }
        return current.last;
    }

    @Override
    public boolean delete(String word) {
        if (! this.contains(word)) {
            return false;
        }
        Node current = this.start;
        Node notToDeleteNode = this.start;
        int letterIdx = 0;
        int i;
        for (i = 0; i < word.length(); i++) {
            if (current.next.containsKey(word.charAt(i))) {
                current = current.next.get(word.charAt(i));
            }
            else {
                System.out.println("No such words");
                return false;
            }
            if (current.last && i !=word.length() - 1) {
                notToDeleteNode = current;
                letterIdx = i;
            }
        }
        if (current.next.isEmpty()) {
            notToDeleteNode.next.remove(word.charAt(letterIdx));
            size -= 1;
            return true;
        }
        current.last = false;
        current.weight = null;
        size -= 1;
        return true;

    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Node current = this.start;
        for (int i = 0; i < s.length(); i++) {
            if (current.next.containsKey(s.charAt(i))) {
                current = current.next.get(s.charAt(i));
            }
            else {
                return null;
            }
        }
        return RWayTrieIterator.RWayTrieIterable(current, s);
    }

    @Override
    public int size() {
        return size;
    }
}
