package ua.edu.ucu.tools;

import ua.edu.ucu.tools.immutablelist.ImmutableLinkedList;

public class Queue {
    private ImmutableLinkedList lst;

    public Queue() {
        lst = new ImmutableLinkedList();
    }

    public Object peek() {
        return this.lst.getFirst();
    }

    public Object dequeue() {
        Object elem = this.lst.getFirst();
        this.lst = this.lst.removeFirst();
        return elem;
    }

    public void enqueue(Object e) {
        this.lst = this.lst.addLast(e);
    }

    public String toString() {
        return this.lst.toString();
    }
}
