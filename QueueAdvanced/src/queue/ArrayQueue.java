package queue;

public class ArrayQueue extends AbstractQueue {
    private int head, tail;
    private Object[] elements;

    public ArrayQueue() {
        head = tail = 0;
        elements = new Object[2];
    }

    public void push(Object e) {
        elements[tail] = e;
        tail = inc(tail);
        if (size() == elements.length) {
            increaseCapacity();
        }
    }

    public void pop() {
        elements[head] = null;
        head = inc(head);
    }

    public Object head() {
        return elements[head];
    }

    public void clearImpl() {
        head = tail = 0;
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

    private int inc(int a) {
        return (a + 1) % elements.length;
    }

    private int dec(int a) {
        return (elements.length + a - 1) % elements.length;
    }
}
