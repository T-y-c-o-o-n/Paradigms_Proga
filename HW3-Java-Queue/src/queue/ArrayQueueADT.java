package queue;

public class ArrayQueueADT {
    // INV:
    // Q = {e_first, e_2, e_3, ..., e_n-1, e_last | e_i not null}
    // e_first - last element in queue
    // e_last - first element in queue
    // First in - Last out
    // в реализации:
    // head == tail <=> size == 0;
    // size > 0 <=> head != tail
    private int head = 0, tail = 0;
    private Object[] elements = new Object[2];

    // Pre: q not null && e not null
    // Post: Q' = {e_1, e_2, ..., e_n, e} && |Q| > 0
    public static void enqueue(ArrayQueueADT q, Object e) {
        assert q != null;
        assert e != null;

        if (size(q) + 1 == q.elements.length) {
            increaseCapacity(q);
        }
        q.elements[q.tail] = e;
        q.tail = inc(q, q.tail);
    }

    // Pre: |Q| > 0 && q not null
    // Post: R = e_1 && Q' = {e_2, ..., e_n} && |Q'| = |Q| - 1
    public static Object dequeue(ArrayQueueADT q) {
        assert q != null;
        assert size(q) > 0;

        Object result = q.elements[q.head];
        q.elements[q.head] = null;
        q.head = inc(q, q.head);
        return result;
    }

    // Pre: |Q| > 0 && q not null
    // Post: R = e_1
    public static Object element(ArrayQueueADT q) {
        assert q != null;
        assert size(q) > 0;

        return q.elements[q.head];
    }

    // Pre: q not null
    // Post: R = |Q|
    public static int size(ArrayQueueADT q) {
        assert q != null;

        return q.tail < q.head ? q.elements.length - (q.head - q.tail) : q.tail - q.head;
    }

    // Pre: q not null
    // Post: R = (|Q| == 0)
    public static boolean isEmpty(ArrayQueueADT q) {
        assert q != null;

        return size(q) == 0;
    }

    // Pre: q not null
    // Post: |Q| == 0
    public static void clear(ArrayQueueADT q) {
        assert q != null;

        q.head = 0;
        q.tail = 0;
        q.elements = new Object[2];
    }

    // Pre: q not null && e not null
    // Post: Q' = {e, e_1, e_2, ..., e_n-1, e_n} && |Q| > 0
    public static void push(ArrayQueueADT q, Object e) {
        assert q != null;
        assert e != null;

        if (size(q) + 1 == q.elements.length) {
            increaseCapacity(q);
        }
        q.head = dec(q, q.head);
        q.elements[q.head] = e;
    }

    // Pre: |Q| > 0 && q not null
    // Post: R = e_n && Q' = {e_1, e_2, ..., e_n-1} && |Q'| = |Q| - 1
    public static Object remove(ArrayQueueADT q) {
        assert q != null;
        assert size(q) > 0;

        q.tail = dec(q, q.tail);
        Object result = q.elements[q.tail];
        q.elements[q.tail] = null;
        return result;
    }

    // Pre: |Q| > 0 && q not null
    // Post: R = e_n
    public static Object peek(ArrayQueueADT q) {
        assert q != null;
        assert size(q) > 0;

        return q.elements[dec(q, q.tail)];
    }

    private static void increaseCapacity(ArrayQueueADT q) {
        int size = size(q);
        Object[] increased = new Object[q.elements.length * 2];
        System.arraycopy(q.elements, q.head, increased, 0, q.elements.length - q.head);
        System.arraycopy(q.elements, 0, increased, q.elements.length - q.head, q.tail);
        q.elements = increased;
        q.head = 0;
        q.tail = size;
    }

    private static int inc(ArrayQueueADT q, int a) {
        return (a + 1) % q.elements.length;
    }

    private static int dec(ArrayQueueADT q, int a) {
        return (q.elements.length + a - 1) % q.elements.length;
    }
}
