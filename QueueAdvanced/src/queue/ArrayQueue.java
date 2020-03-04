package queue;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
    private int head, tail;
    private Object[] elements;

    public ArrayQueue() {
        head = tail = 0;
        elements = new Object[2];
    }

    public void enqueueImpl(Object e) {
        if (size() == elements.length) {
            increaseCapacity();
        }
        elements[tail] = e;
        tail = inc(tail);
    }

    public void dequeueImpl() {
        elements[head] = null;
        head = inc(head);
    }

    public Object head() {
        return elements[head];
    }

    public void clearImpl() {
        head = tail = 0;
        elements = new Object[2];
    }

    public Object[] toArray() {
        Object[] result = new Object[size()];
        if (head < tail || head == tail && elements[head] == null) {
            System.arraycopy(elements, head, result, 0, tail - head);
        } else {
            System.arraycopy(elements, head, result, 0, elements.length - head);
            System.arraycopy(elements, 0, result, elements.length - head, tail);
        }
        return result;
    }
/*
    public Iterator<Object> iterator() {
        return null;
    }*/

    protected Queue getQueue() {
        return new ArrayQueue();
    }

    public Queue filter(Predicate<Object> predicate) {
        return null;
    }

    public Queue map(Function<Object, Object> function) {
        return null;
    }

    private void increaseCapacity() {
        Object[] increased = new Object[elements.length * 2];
        System.arraycopy(elements, head, increased, 0, elements.length - head);
        System.arraycopy(elements, 0, increased, elements.length - head, tail);
        elements = increased;
        head = 0;
        tail = elements.length / 2;
    }

    private int inc(int a) {
        return (a + 1) % elements.length;
    }/*

    public class ArrayQueueIterator<Object> /*implements Iterator<Object> {
        private final Object[] elems;

        public ArrayQueueIterator(ArrayQueue arrQ) {
            elems = (Object[]) arrQ.toArray();
        }
    }*/
}
