package queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    public LinkedQueue() {
        super();
        head = tail = new Node(null);
    }

    protected void enqueueImpl(Object element) {
        tail = tail.prev = new Node(element);
    }

    protected void dequeueImpl() {
        head = head.prev;
    }

    protected Object head() {
        return head.prev.value;
    }

    protected void clearImpl() {
        head = tail = new Node(null);
    }

    public Object[] toArray() {
        Object[] result = new Object[size()];
        int i = 0;
        Node pnt = head;
        while (pnt != tail) {
            pnt = pnt.prev;
            result[i++] = pnt.value;
        }
        return result;
    }

    public Iterator<Object> iterator() {
        return new LinkedQueueIterator();
    }

    protected Queue getQueue() {
        return new LinkedQueue();
    }

    private static class Node {
        private final Object value;  // final!!
        private Node prev;

        private Node(Object value) {
            this.value = value;
        }
    }

    private class LinkedQueueIterator extends AbstractQueueIterator {
        private Node pnt = head;

        @Override
        protected Object nextImpl() {
            pnt = pnt.prev;
            return pnt.value;
        }
    }
}
