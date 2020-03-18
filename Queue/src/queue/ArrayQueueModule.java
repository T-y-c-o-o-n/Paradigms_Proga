package queue;

public class ArrayQueueModule {
    // INV:
    // Q = {e_first, e_2, e_3, ..., e_n-1, e_last | e_i not null}
    // e_first - last element in queue
    // e_last - first element in queue
    // First in - Last out
    // в реализации:
    // head == tail <=> size == 0;
    // size > 0 <=> head != tail
    private static int head = 0, tail = 0;
    private static Object[] elements = new Object[2];

    // Pre: e not null
    // Post: Q' = {e_1, e_2, ..., e_n, e} && |Q| > 0
    public static void enqueue(Object e) {
        assert e != null;

        if (size() + 1 == elements.length) {
            increaseCapacity();
        }
        elements[tail] = e;
        tail = inc(tail);
    }

    // Pre: |Q| > 0
    // Post: R = e_first && Q' = {e_2, ..., e_n} && |Q'| = |Q| - 1
    public static Object dequeue() {
        assert size() > 0;

        Object result = elements[head];
        elements[head] = null;
        head = inc(head);
        return result;
    }

    // Pre: |Q| > 0
    // Post: R = e_first
    public static Object element() {
        assert size() > 0;

        return elements[head];
    }

    // Pre: true
    // Post: R = |Q|
    public static int size() {
        return tail < head ? elements.length - (head - tail) : tail - head;
    }

    // Pre: true
    // Post: R = (|Q| == 0)
    public static boolean isEmpty() {
        return size() == 0;
    }

    // Pre: true
    // Post: |Q| == 0
    public static void clear() {
        head = 0;
        tail = 0;
        elements = new Object[2];
    }

    // Pre: e not null
    // Post: Q' = {e, e_1, e_2, ..., e_n-1, e_n} && |Q| > 0
    public static void push(Object e) {
        assert e != null;

        if (size() + 1 == elements.length) {
            increaseCapacity();
        }
        head = dec(head);
        elements[head] = e;
    }

    // Pre: |Q| > 0
    // Post: R = e_n && Q' = {e_1, e_2, ..., e_n-1} && |Q'| = |Q| - 1
    public static Object remove() {
        assert size() > 0;

        tail = dec(tail);
        Object result = elements[tail];
        elements[tail] = null;
        return result;
    }

    // Pre: |Q| > 0
    // Post: R = e_n
    public static Object peek() {
        assert size() > 0;

        return elements[dec(tail)];
    }

    private static void increaseCapacity() {
        int size = size();
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
