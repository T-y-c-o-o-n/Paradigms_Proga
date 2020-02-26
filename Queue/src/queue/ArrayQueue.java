package queue;

import java.util.Arrays;

public class ArrayQueue {
    // INV:
    // size == last - first
    // if size > 0:
    //      queue[first] - first element;
    //      queue[last - 1] - last element;
    private Object[] elements = new Object[2];
    private int size = 0, first = 0, last = 0;

    // Pre: queue[a_1, a_2, ..., a_n]; size > 0
    // Post: queue[a_1, a_2, ..., a_n, a_n+1]
    public void enqueue(/*ArrayQueue this,*/Object object) {
        if (/*this.*/last == /*this.*/elements.length) {
            /*this.*/elements = Arrays.copyOf(/*this.*/elements, /*this.*/elements.length * 2);
        }
        /*this.*/elements[/*this.*/last++] = object;
        /*this.*/size++;
    }

    // Pre: queue[a_1, a_2, ..., a_n]; size > 0
    // Post: R = a_1
    public Object element(/*ArrayQueue this*/) {
        assert /*this.*/size > 0;
        return /*this.*/elements[/*this.*/first];
    }

    // Pre: queue[a_1, a_2, ..., a_n-1, a_n]; size > 0
    // Post: queue[a_2, ..., a_n]; size' = size - 1
    public Object dequeue(/*ArrayQueue this*/) {
        assert /*this.*/size > 0;
        Object element = /*this.*/elements[/*this.*/first];
        /*this.*/elements[/*this.*/first] = null;
        /*this.*/first++;
        /*this.*/size--;
        return element;
    }

    // Pre: -
    // Post: R = size
    public int size(/*ArrayQueue this*/) {
        return /*this.*/size;
    }

    // Pre: -
    // Post: R = (size == 0)
    public boolean isEmpty(/*ArrayQueue this*/) {
        return /*this.*/size == 0;
    }

    // Pre: -
    // Post: size == 0
    public void clear(/*ArrayQueue this*/) {
        while (/*this.*/size > 0) {
            /*this.*/dequeue();
        }
    }

    public Object[] toArray() {
        return Arrays.copyOfRange(elements, first, last);
    }
}
