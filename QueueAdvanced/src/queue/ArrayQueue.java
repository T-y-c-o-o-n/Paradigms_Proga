package queue;

public class ArrayQueue extends AbstractQueue {
    private int head = 0, tail = 0;
    private Object[] elements = new Object[2];

    public void enqueue(Object e) {
        assert e != null;
        elements[tail] = e;
        tail = inc(tail);
        if (size() == elements.length) {
            increaseCapacity();
        }
    }

    public Object dequeue() {
        assert size() > 0;
        Object result = elements[head];
        elements[head] = null;
        head = inc(head);
        return result;
    }

    public Object element() {
        assert size() > 0;
        return elements[head];
    }

    public int size() {
        if (head == tail) {
            return elements[head] == null ? 0 : elements.length;
        }
        return head > tail ? elements.length - (head - tail) : tail - head;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        head = 0;
        tail = 0;
        elements = new Object[2];
    }

    private void increaseCapacity() {
        Object[] increased = new Object[elements.length * 2];
        System.arraycopy(elements, head, increased, 0, elements.length - head);
        System.arraycopy(elements, 0, increased, elements.length - head, tail);
        elements = increased;
        head = 0;
        tail = elements.length / 2;
    }

    private int inc(/*ArrayQueue this, */int a) {
        return (a + 1) % elements.length;
    }

    private int dec(/*ArrayQueue this, */int a) {
        return (elements.length + a - 1) % elements.length;
    }
}
