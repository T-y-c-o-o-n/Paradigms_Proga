package queue;

import java.util.Arrays;

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
    private static int cap = 2, size = 0;
    private static int head = 0, tail = 0;
    private static Object[] elements = new Object[cap];

    // Pre: true
    // Post: Q' = {e_1, e_2, ..., e_n, e} && |Q| > 0
    public static void enqueue(Object e) {
        assert e != null;
        elements[tail] = e;
        tail = inc(tail);
        size++;
        if (size == cap) {
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
        if (size * 4 == cap) {
            decreaseCapacity();
        }
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
        cap = 2;
        size = 0;
        head = 0;
        tail = 0;
        elements = new Object[cap];
    }

    // Pre: true
    // Post: R = [e_1, e_2, ..., e_n-1, e_n]
    public static Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = elements[(head + i) % cap];
        }
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
            sb.append(elements[(head + i) % cap]);
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
        if (size == cap) {
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
        if (size * 4 == cap) {
            decreaseCapacity();
        }
        return result;
    }

    // Pre: |Q| > 0
    // Post: R = e_n
    public static Object peek() {
        assert size > 0;
        return elements[dec(tail)];
    }

    private static void increaseCapacity() {
        elements = Arrays.copyOf(toArray(), cap * 2);
        cap *= 2;
        head = 0;
        tail = size;
    }

    private static void decreaseCapacity() {
        elements = Arrays.copyOf(toArray(), cap / 2);
        cap /= 2;
        head = 0;
        tail = size;
    }

    private static int inc(int a) {
        return (a + 1) % cap;
    }

    private static int dec(int a) {
        return (cap + a - 1) % cap;
    }
}
