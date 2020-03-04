package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    public LinkedQueue() {
        super();
        head = tail = new Node(null);
    }

    public void push(Object element) {
        tail = tail.prev = new Node(element);
    }

    public void pop() {
        head = head.prev;
    }

    public Object head() {
        return head.prev.value;
    }

    public void clearImpl() {
        head = tail = new Node(null);
    }

    private static class Node {
        private final Object value;  // final!!
        private Node prev;

        private Node(Object value) {
            this.value = value;
        }
    }
}
