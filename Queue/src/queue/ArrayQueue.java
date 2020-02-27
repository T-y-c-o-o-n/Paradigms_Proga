package queue;

import java.util.Arrays;

public class ArrayQueue {
    // INV:
    // First in - Last out
    /*
    INVs реализации:
    capacity > 0
    0 <= head, tail < capacity
    size == (head - tail) % capacity (% математический!!)
    if size > 0:
        elements[head] - first element;
        elements[(tail - 1) % capacity] - last element; (% математический!!)
    */
    private int size = 0;
    private int head = 0, tail = 0;
    private Object[] elements = new Object[2];

    // Pre: true
    // Post: Q' = {e_1, e_2, ..., e_n, e} && |Q| > 0
    public void enqueue(/*ArrayQueue this, */Object e) {
        assert e != null;
        elements[tail] = e;
        tail = inc(tail);
        size++;
        if (size == elements.length) {
            increaseCapacity();
        }
    }

    // Pre: |Q| > 0
    // Post: R = e_1 && Q' = {e_2, ..., e_n} && |Q'| = |Q| - 1
    public Object dequeue(/*ArrayQueue this*/) {
        assert size > 0;
        Object result = elements[head];
        elements[head] = null;
        head = inc(head);
        size--;
        return result;
    }

    // Pre: |Q| > 0
    // Post: R = e_1
    public Object element(/*ArrayQueue this*/) {
        assert size > 0;
        return elements[head];
    }

    // Pre: true
    // Post: R = |Q|
    public int size(/*ArrayQueue this*/) {
        return size;
    }

    // Pre: true
    // Post: R = (|Q| == 0)
    public boolean isEmpty(/*ArrayQueue this*/) {
        return size == 0;
    }

    // Pre: true
    // Post: |Q| == 0
    public void clear(/*ArrayQueue this*/) {
        size = 0;
        head = 0;
        tail = 0;
        elements = new Object[2];
    }

    // Pre: true
    // Post: R = [e_1, e_2, ..., e_n-1, e_n]
    public Object[] toArray(/*ArrayQueue this*/) {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = elements[(head + i) % elements.length];
        }
        return result;
    }

    // Pre: true
    // Post: R = "[e_1, e_2, ..., e_n-1, e_n]"
    public String toStr(/*ArrayQueue this*/) {
        StringBuilder sb = new StringBuilder("[");
        if (size > 0) {
            sb.append(elements[head]);
        }
        for (int i = 1; i < size; i++) {
            sb.append(", ");
            sb.append(elements[(head + i) % elements.length]);
        }
        sb.append(']');
        return sb.toString();
    }

    // Pre: true
    // Post: Q' = {e, e_1, e_2, ..., e_n-1, e_n} && |Q| > 0
    public void push(/*ArrayQueue this, */Object e) {
        assert e != null;
        head = dec(head);
        elements[head] = e;
        size++;
        if (size == elements.length) {
            increaseCapacity();
        }
    }

    // Pre: |Q| > 0
    // Post: R = e_n && Q' = {e_1, e_2, ..., e_n-1} && |Q'| = |Q| - 1
    public Object remove(/*ArrayQueueADT this*/) {
        assert size > 0;
        tail = dec(tail);
        Object result = elements[tail];
        elements[tail] = null;
        size--;
        return result;
    }

    // Pre: |Q| > 0
    // Post: R = e_n
    public Object peek(/*ArrayQueue this*/) {
        assert size > 0;
        return elements[dec(tail)];
    }

    private void increaseCapacity(/*ArrayQueue this*/) {
        Object[] increased = new Object[elements.length * 2];
        System.arraycopy(elements, head, increased, 0, elements.length - head);
        System.arraycopy(elements, 0, increased, elements.length - head, tail);
        elements = increased;
        head = 0;
        tail = size;
    }

    private int inc(/*ArrayQueue this, */int a) {
        return (a + 1) % elements.length;
    }

    private int dec(/*ArrayQueue this, */int a) {
        return (elements.length + a - 1) % elements.length;
    }
}
