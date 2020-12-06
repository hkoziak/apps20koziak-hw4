package ua.edu.ucu.tools;

import java.util.HashMap;

public class Node {

    public final char letter;
    public Object weight;
    public HashMap<Character, Node> next;
    public boolean last;

    public Node(char letter) {
        this.letter = letter;
        this.next = new HashMap<Character, Node>();
        this.weight = null;
        this.last = false;
    }

    public Node addNext(char s) {
        if (!this.next.containsKey(s)) {
            this.next.put(s, new Node(s));
        }
        return this.next.get(s);
    }
}
