package queue;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.util.Arrays;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ArrayQueueModule {
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
    private static int size = 0;
    private static int head = 0, tail = 0;
    private static Object[] elements = new Object[2];

    // Pre: true
    // Post: Q' = {e_1, e_2, ..., e_n, e} && |Q| > 0
    public static void enqueue(Object e) {
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
    public static Object dequeue() {
        assert size > 0;
        Object result = elements[head];
        elements[head] = null;
        head = inc(head);
        size--;
        return result;
    }

    // Pre: |Q| > 0
    // Post: R = e_1
    public static Object element() {
        assert size > 0;
        return elements[head];
    }

    // Pre: true
    // Post: R = |Q|
    public static int size() {
        return size;
    }

    // Pre: true
    // Post: R = (|Q| == 0)
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pre: true
    // Post: |Q| == 0
    public static void clear() {
        size = 0;
        head = 0;
        tail = 0;
        elements = new Object[2];
    }

    // Pre: true
    // Post: R = [e_1, e_2, ..., e_n-1, e_n]
    public static Object[] toArray() {
        if (head <= tail) {
            return Arrays.copyOfRange(elements, head, tail);
        }
        Object[] result = new Object[size];
        System.arraycopy(elements, head, result, 0, elements.length - head);
        System.arraycopy(elements, 0, result, elements.length - head, tail);
        return result;
    }

    // Pre: true
    // Post: R = "[e_1, e_2, ..., e_n-1, e_n]"
    public static String toStr() {
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
    public static void push(Object e) {
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
    public static Object remove() {
        assert size > 0;
        tail = dec(tail);
        Object result = elements[tail];
        elements[tail] = null;
        size--;
        return result;
    }

    // Pre: |Q| > 0
    // Post: R = e_n
    public static Object peek() {
        assert size > 0;
        return elements[dec(tail)];
    }

    private static void increaseCapacity() {
        Object[] increased = new Object[elements.length * 2];
        System.arraycopy(elements, head, increased, 0, elements.length - head);
        System.arraycopy(elements, 0, increased, elements.length - head, tail);
        elements = increased;
        head = 0;
        tail = size;
    }

    private static int inc(int a) {
        return (a + 1) % elements.length;
    }

    private static int dec(int a) {
        return (elements.length + a - 1) % elements.length;
    }
}
