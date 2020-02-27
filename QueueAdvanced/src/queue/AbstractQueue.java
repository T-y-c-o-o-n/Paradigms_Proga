package queue;

import java.util.ArrayDeque;

public abstract class AbstractQueue implements Queue {
    protected int size;

    protected AbstractQueue() { size = 0; }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
