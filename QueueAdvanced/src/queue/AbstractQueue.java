package queue;

import java.util.ArrayDeque;

public abstract class AbstractQueue<Type> implements Queue<Type> {
    protected int size;

    protected AbstractQueue() {}

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        while (size > 0) {
            dequeue();
        }
    }
}
