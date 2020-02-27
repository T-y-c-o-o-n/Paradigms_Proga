package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    public LinkedQueue() {
        super();
        tail = new Node(null, head);
    }

    public void enqueue(Object element) {
        tail.prev = new Node(element, head);
        tail = tail.prev;
        size++;
    }

    public Object dequeue() {
        assert size > 0;

        Object element = head.value;
        head = head.prev;
        size--;
        return element;
    }

    public Object element() {
        assert size > 0;

        return head.value;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    private /*static ??? */ class Node {
        private final Object value;  // final!!
        private Node prev;

        private Node(Object value, Node prev) {
            this.value = value;
            this.prev = prev;
        }
    }
}
