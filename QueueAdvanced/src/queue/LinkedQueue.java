package queue;

public class LinkedQueue<Type> extends AbstractQueue<Type> implements Queue<Type> {
    private Node first, last;

    public LinkedQueue() {
        super();
        first = new Node(null);
        last = new Node(null);
        first.prev = last;
        size = 0;
    }

    public void enqueue(Type element) {
        last.prev = new Node(element);
        last = last.prev;
        if (size == 0) {
            first = last;
        }
        size++;
    }

    public Type element() {
        assert size > 0;
        return first.value;
    }

    public Type dequeue() {
        assert size > 0;
        Type element = first.value;
        first = first.prev;
        size--;
        return element;
    }

    private class Node {
        private final Type value;
        private Node prev;

        private Node(Type value) {
            this.value = value;
        }
    }
}
