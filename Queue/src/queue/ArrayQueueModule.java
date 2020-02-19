package queue;

import java.util.Arrays;

public class ArrayQueueModule {
    // INV:
    // size == last - first
    // if size > 0:
    //      queue[first] - first element;
    //      queue[last - 1] - last element;
    private static Object[] queue = new Object[2];
    private static int size = 0, first = 0, last = 0;

    // Pre: queue[a_1, a_2, ..., a_n]; size > 0
    // Post: queue[a_1, a_2, ..., a_n, a_n+1]
    public static void enqueue(Object object) {
        if (last == queue.length) {
            queue = Arrays.copyOf(queue, queue.length * 2);
        }
        queue[last++] = object;
        size++;
    }

    // Pre: queue[a_1, a_2, ..., a_n]; size > 0
    // Post: R = a_1
    public static Object element() {
        assert size > 0;
        return queue[first];
    }

    // Pre: queue[a_1, a_2, ..., a_n-1, a_n]; size > 0
    // Post: queue[a_2, ..., a_n]; size' = size - 1
    public static Object dequeue() {
        assert size > 0;
        Object element = queue[first];
        queue[first] = null;
        first++;
        size--;
        return element;
    }

    // Pre: -
    // Post: R = size
    public static int size() {
        return size;
    }

    // Pre: -
    // Post: R = (size == 0)
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pre: -
    // Post: size == 0
    public static void clear() {
        while (first < last) {
            queue[first] = null;
            first++;
            size--;
        }
    }
}
