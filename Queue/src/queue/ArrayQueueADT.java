package queue;

import java.util.Arrays;

public class ArrayQueueADT {
    // INV:
    // size == last - first
    // if size > 0:
    //      queue[first] - first element;
    //      queue[last - 1] - last element;
    private Object[] elements = new Object[2];
    private int size = 0, first = 0, last = 0;

    // Pre: queue[a_1, a_2, ..., a_n]; size > 0
    // Post: queue[a_1, a_2, ..., a_n, a_n+1]
    public static void enqueue(ArrayQueueADT q, Object object) {
        if (q.last == q.elements.length) {
            q.elements = Arrays.copyOf(q.elements, q.elements.length * 2);
        }
        q.elements[q.last++] = object;
        q.size++;
    }

    // Pre: queue[a_1, a_2, ..., a_n]; size > 0
    // Post: R = a_1
    public static Object element(ArrayQueueADT q) {
        assert q.size > 0;
        return q.elements[q.first];
    }

    // Pre: queue[a_1, a_2, ..., a_n-1, a_n]; size > 0
    // Post: queue[a_2, ..., a_n]; size' = size - 1
    public static Object dequeue(ArrayQueueADT q) {
        assert q.size > 0;
        Object element = q.elements[q.first];
        q.elements[q.first] = null;
        q.first++;
        q.size--;
        return element;
    }

    // Pre: -
    // Post: R = size
    public static int size(ArrayQueueADT q) {
        return q.size;
    }

    // Pre: -
    // Post: R = (size == 0)
    public static boolean isEmpty(ArrayQueueADT q) {
        return q.size == 0;
    }

    // Pre: -
    // Post: size == 0
    public static void clear(ArrayQueueADT q) {
        while (q.first < q.last) {
            q.elements[q.first] = null;
            q.first++;
            q.size--;
        }
    }
}
