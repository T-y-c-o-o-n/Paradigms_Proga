package queue;

import java.util.Arrays;

public class ArrayQueueADT {
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
    private int cap = 2, size = 0;
    private int head = 0, tail = 0;
    private Object[] elements = new Object[cap];

    // Pre: true
    // Post: Q' = {e_1, e_2, ..., e_n, e} && |Q| > 0
    public static void enqueue(ArrayQueueADT q, Object e) {
        assert e != null;
        q.elements[q.tail] = e;
        q.tail = inc(q, q.tail);
        q.size++;
        if (q.size == q.cap) {
            increaseCapacity(q);
        }
    }

    // Pre: |Q| > 0
    // Post: R = e_1 && Q' = {e_2, ..., e_n} && |Q'| = |Q| - 1
    public static Object dequeue(ArrayQueueADT q) {
        assert q.size > 0;
        Object result = q.elements[q.head];
        q.elements[q.head] = null;
        q.head = inc(q, q.head);
        q.size--;
        if (q.size * 4 == q.cap) {
            decreaseCapacity(q);
        }
        return result;
    }

    // Pre: |Q| > 0
    // Post: R = e_1
    public static Object element(ArrayQueueADT q) {
        assert q.size > 0;
        return q.elements[q.head];
    }

    // Pre: true
    // Post: R = |Q|
    public static int size(ArrayQueueADT q) {
        return q.size;
    }

    // Pre: true
    // Post: R = (|Q| == 0)
    public static boolean isEmpty(ArrayQueueADT q) {
        return q.size == 0;
    }

    // Pre: true
    // Post: |Q| == 0
    public static void clear(ArrayQueueADT q) {
        q.cap = 2;
        q.size = 0;
        q.head = 0;
        q.tail = 0;
        q.elements = new Object[q.cap];
    }

    // Pre: true
    // Post: R = [e_1, e_2, ..., e_n-1, e_n]
    public static Object[] toArray(ArrayQueueADT q) {
        Object[] result = new Object[q.size];
        for (int i = 0; i < q.size; i++) {
            result[i] = q.elements[(q.head + i) % q.cap];
        }
        return result;
    }

    // Pre: true
    // Post: R = "[e_1, e_2, ..., e_n-1, e_n]"
    public static String toStr(ArrayQueueADT q) {
        StringBuilder sb = new StringBuilder("[");
        if (q.size > 0) {
            sb.append(q.elements[q.head]);
        }
        for (int i = 1; i < q.size; i++) {
            sb.append(", ");
            sb.append(q.elements[(q.head + i) % q.cap]);
        }
        sb.append(']');
        return sb.toString();
    }

    // Pre: true
    // Post: Q' = {e, e_1, e_2, ..., e_n-1, e_n} && |Q| > 0
    public static void push(ArrayQueueADT q, Object e) {
        assert e != null;
        q.head = dec(q, q.head);
        q.elements[q.head] = e;
        q.size++;
        if (q.size == q.cap) {
            increaseCapacity(q);
        }
    }

    // Pre: |Q| > 0
    // Post: R = e_n && Q' = {e_1, e_2, ..., e_n-1} && |Q'| = |Q| - 1
    public static Object remove(ArrayQueueADT q) {
        assert q.size > 0;
        q.tail = dec(q, q.tail);
        Object result = q.elements[q.tail];
        q.elements[q.tail] = null;
        q.size--;
        if (q.size * 4 == q.cap) {
            decreaseCapacity(q);
        }
        return result;
    }

    // Pre: |Q| > 0
    // Post: R = e_n
    public static Object peek(ArrayQueueADT q) {
        assert q.size > 0;
        return q.elements[dec(q, q.tail)];
    }

    private static void increaseCapacity(ArrayQueueADT q) {
        q.elements = Arrays.copyOf(toArray(q), q.cap * 2);
        q.cap *= 2;
        q.head = 0;
        q.tail = q.size;
    }

    private static void decreaseCapacity(ArrayQueueADT q) {
        q.elements = Arrays.copyOf(toArray(q), q.cap / 2);
        q.cap /= 2;
        q.head = 0;
        q.tail = q.size;
    }

    private static int inc(ArrayQueueADT q, int a) {
        return (a + 1) % q.cap;
    }

    private static int dec(ArrayQueueADT q, int a) {
        return (q.cap + a - 1) % q.cap;
    }
}
