package ua.edu.ucu.tools.immutablelist;

public class ImmutableLinkedList implements ImmutableList {

    private class Node {

        private Object elem;
        private Node next;
        private Node prev;

        Node(Object elem, Node next, Node prev) {
            this.elem = elem;
            this.next = next;
            this.prev = prev;
        }

        Node(Object elem) {
            this.elem = elem;
            this.next = null;
            this.prev = null;
        }

        public Object getElem() {
            return this.elem;
        }

        public Node getNext() {
            return this.next;
        }

        public Node getPrev() {
            return this.prev;
        }

        public void setElem(Object newElem) {
            this.elem = newElem;
        }

        public void setNext(Node nextNode) {
            this.next = nextNode;
        }

        public void setPrev(Node prevNode) {
            this.prev = prevNode;
        }

        private Node copyNode() {
            return new Node(this.elem, null, null);
        }

        private void addNext(Object newElem) {
            Node newNode = new Node(newElem, this.getNext(), this);
            this.setNext(newNode);
            if (this.getNext() != null) {
                this.getNext().setPrev(newNode);
            }
        }
    }

    private final Node head;
    private final Node tail;
    private final int size;

    public ImmutableLinkedList(Object elem) {
        Node nod = new Node(elem);
        this.head = nod;
        this.tail = nod;
        this.size = 1;
    }

    public ImmutableLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public ImmutableLinkedList(Node head, Node tail) {
        this.head = head;
        this.tail = tail;
        Node nod = head;
        int i = 1;
        while (nod.getNext() != null) {
            nod = nod.getNext();
            i++;
        }
        this.size = i;
    }

    private void indexCheck(int index)
            throws IndexOutOfBoundsException {
        if (index > this.size - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void indexCheckPlusOne(int index)
            throws IndexOutOfBoundsException {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node goTo(int index, Node nod) {
        Node newNode = nod;
        for (int i = 0; i < index; i++) {
            newNode = newNode.getNext();
        }
        return newNode;
    }

    private Node[] copyAll() {
        if (this.head == null) {
            return new Node[] {null, null};
        }
        Node newHead = this.head.copyNode();
        Node iterNode = this.head;
        Node prevNode = null;
        Node iterNodeNew = newHead;
        while (iterNode.getNext() != null) {
            iterNodeNew.setNext(iterNode.getNext().copyNode());
            iterNodeNew.setPrev(prevNode);
            prevNode = iterNodeNew;
            iterNodeNew = iterNodeNew.getNext();
            iterNode = iterNode.getNext();
        }
        iterNodeNew.setPrev(prevNode);
        return new Node[] {newHead, iterNodeNew};
    }

    public ImmutableLinkedList addFirst(Object e) {
        if (this.isEmpty()) {
            return new ImmutableLinkedList(e);
        }
        else {
            Node[] newHeadAndTail = this.copyAll();
            Node newHead = new Node(e, newHeadAndTail[0], null);
            newHeadAndTail[0].setPrev(newHead);
            return new ImmutableLinkedList(newHead, newHeadAndTail[1]);
        }
    }

    public ImmutableLinkedList addLast(Object e) {
        if (this.isEmpty()) {
            return new ImmutableLinkedList(e);
        }
        else {
            Node[] newHeadAndTail = this.copyAll();
            newHeadAndTail[1].addNext(e);
            return new ImmutableLinkedList(newHeadAndTail[0],
                    newHeadAndTail[1].getNext());
        }
    }

    public Object getFirst() {
        if (this.isEmpty()) {
            return null;
        }
        else {
            return this.head.getElem();
        }
    }

    public Object getLast() {
        if (this.isEmpty()) {
            return null;
        }
        else {
            return this.tail.getElem();
        }
    }

    public ImmutableLinkedList removeFirst() {
        if (this.isEmpty() || this.size == 1) {
            return new ImmutableLinkedList();
        }
        else {
            Node[] newHeadAndTail = this.copyAll();
            newHeadAndTail[0].getNext().setPrev(null);
            return new ImmutableLinkedList(newHeadAndTail[0].getNext(),
                    newHeadAndTail[1]);
        }
    }

    public ImmutableLinkedList removeLast() {
        if (this.isEmpty()) {
            return new ImmutableLinkedList();
        }
        else {
            Node[] newHeadAndTail = this.copyAll();
            newHeadAndTail[1].getPrev().setNext(null);
            return new ImmutableLinkedList(newHeadAndTail[0],
                    newHeadAndTail[1].getPrev());
        }
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return this.addLast(e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e)
            throws IndexOutOfBoundsException {
        indexCheckPlusOne(index);
        if (index == 0) {
            return this.addFirst(e);
        }
        else if (index == this.size) {
            return this.addLast(e);
        }
        else {
            Node[] newHeadAndTail = this.copyAll();
            Node prevNode = goTo(index - 1, newHeadAndTail[0]);
            prevNode.addNext(e);
            return new ImmutableLinkedList(newHeadAndTail[0],
                    newHeadAndTail[1]);
        }
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        // Not using ImmutableLinkedList to implement addAll(Object[] c)
        // because it will cause the complexity of the function increase
        // and be O(n + len(c) ) instead O( len(c) )
        Node[] newHeadAndTail = this.copyAll();
        for (Object elem: c) {
            if (newHeadAndTail[0] == null) {
                newHeadAndTail[0] = new Node(elem);
                newHeadAndTail[1] = newHeadAndTail[0];
            }
            else {
                newHeadAndTail[1].addNext(elem);
                newHeadAndTail[1] = newHeadAndTail[1].getNext();
            }
        }

        return new ImmutableLinkedList(newHeadAndTail[0], newHeadAndTail[1]);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c)
            throws IndexOutOfBoundsException {
        indexCheckPlusOne(index);

        if (index == 0) {
            Node headNode = new Node(c[0]);
            Node indexNode = headNode;
            for (int i = 1; i < c.length; i++) {
                indexNode = new Node(c[i], null, indexNode);
                indexNode.getPrev().setNext(indexNode);
            }

            if (this.isEmpty()) {
                return new ImmutableLinkedList(headNode, indexNode);
            }
            else {
                Node[] newHeadAndTail = this.copyAll();
                indexNode.setNext(newHeadAndTail[0]);
                newHeadAndTail[0].setPrev(indexNode);
                return new ImmutableLinkedList(headNode, newHeadAndTail[1]);
            }
        }
        else if (index == this.size) {
            return this.addAll(c);
        }
        else {
            Node[] newHeadAndTail = this.copyAll();
            Node prevNode = goTo(index - 1, newHeadAndTail[0]);
            for (Object elem : c) {
                prevNode.addNext(elem);
                prevNode = prevNode.getNext();
            }
            return new ImmutableLinkedList(newHeadAndTail[0],
                    newHeadAndTail[1]);
        }
    }

    @Override
    public Object get(int index) throws IndexOutOfBoundsException {
        indexCheck(index);
        return goTo(index, this.head).getElem();
    }

    @Override
    public ImmutableLinkedList remove(int index)
            throws IndexOutOfBoundsException {
        indexCheck(index);
        if (index == 0) {
            return this.removeFirst();
        }
        else if (index == this.size - 1) {
            return this.removeLast();
        }
        Node[] newHeadAndTail = this.copyAll();
        Node prevToRemove = goTo(index - 1, newHeadAndTail[0]);
        Node nextToRemove = prevToRemove.getNext().getNext();
        prevToRemove.setNext(nextToRemove);
        nextToRemove.setPrev(prevToRemove);
        return new ImmutableLinkedList(newHeadAndTail[0], newHeadAndTail[1]);
    }

    @Override
    public ImmutableLinkedList set(int index, Object e)
            throws IndexOutOfBoundsException {
        indexCheck(index);
        Node[] newHeadAndTail = this.copyAll();
        goTo(index, newHeadAndTail[0]).setElem(e);
        return new ImmutableLinkedList(newHeadAndTail[0], newHeadAndTail[1]);
    }

    @Override
    public int indexOf(Object e) {
        int i = 0;
        Node indexNode = this.head;
        while (indexNode != null) {
            if (indexNode.getElem().equals(e)) {
                return i;
            }
            indexNode = indexNode.getNext();
            i++;
        }
        return -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[this.size];
        Node indexNode = this.head;
        int i = 0;
        while (indexNode != null) {
            newArray[i] = indexNode.getElem();
            indexNode = indexNode.getNext();
            i++;
        }
        return newArray;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        Node iterNode = this.head;
        while (iterNode != null) {
            buf.append(String.valueOf(iterNode.getElem()));
            if (iterNode.getNext() != null) {
                buf.append(", ");
            }
            iterNode = iterNode.getNext();
        }
        return buf.toString();
    }
}
