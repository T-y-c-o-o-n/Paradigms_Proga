package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    public LinkedQueue() {
        super();
        head = tail = new Node(null);
    }

    public void enqueueImpl(Object element) {
        tail = tail.prev = new Node(element);
    }

    public void dequeueImpl() {
        head = head.prev;
    }

    public Object head() {
        return head.prev.value;
    }

    public void clearImpl() {
        head = tail = new Node(null);
    }

    public Object[] toArray() {
        Object[] result = new Object[size()];
        int i = 0;
        Node temp = head;
        while (temp != tail) {
            temp = temp.prev;
            result[i++] = temp.value;
        }
        return result;
    }

    private static class Node {
        private final Object value;  // final!!
        private Node prev;

        private Node(Object value) {
            this.value = value;
        }
    }
}
