package ua.edu.ucu.iters;

import ua.edu.ucu.tools.Queue;
import ua.edu.ucu.tools.Node;

import java.util.Iterator;
import java.util.Map;

public class RWayTrieIterator implements Iterator<String>  {

    private Queue queue;
    private Queue lettersQueue;


    public RWayTrieIterator(Node head, String prefix) {
        queue = new Queue();
        lettersQueue = new Queue();
        queue.enqueue(head);
        lettersQueue.enqueue(prefix);
    }

    private void step() {

    }

    @Override
    public boolean hasNext() {
        if (queue.peek() != null &&
                ((Node) queue.peek()).letter == 'H' &&
                ((Node) queue.peek()).next.isEmpty()) {
            return false;
        }
        return queue.peek() != null;
    }

    @Override
    public String next() {
        Node node;
        String prevWord;
        if (queue.peek() == null) {
            return null;
        }
        do {
            node = (Node) queue.dequeue();
            if (node == null) {
                return null;
            }
            prevWord = (String) lettersQueue.dequeue();
            for (Map.Entry nextMapElem: node.next.entrySet()) {
                Node nextNode = (Node) nextMapElem.getValue();
                queue.enqueue(nextNode);
                lettersQueue.enqueue(prevWord + nextNode.letter);
            }
        }
        while (node.weight == null);
        return prevWord;
    }

    public static Iterable<String> rWayTrieIterable(Node node, String prefix) {
        return () -> new RWayTrieIterator(node, prefix);
    }
}
