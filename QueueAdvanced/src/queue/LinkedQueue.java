package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    public LinkedQueue() {
        super();
        head = tail = new Node(null, null);
    }

    public void enqueue(Object element) {
        tail = tail.prev = new Node(element, head);
        size++;
    }

    public Object dequeue() {
        assert size > 0;

        head = head.prev;
        size--;
        return head.value;
    }

    public Object element() {
        assert size > 0;

        return head.prev.value;
    }

    public void clear() {
        head = tail = new Node(null, null);
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
