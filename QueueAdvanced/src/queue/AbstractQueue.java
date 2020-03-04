package queue;

public abstract class AbstractQueue implements Queue {
    private int size = 0;

    protected abstract void push(Object e);

    public void enqueue(Object e) {
        assert e != null;

        size++;
        push(e);
    }

    protected abstract void pop();

    public Object dequeue() {
        assert size > 0;

        Object result = head();
        size--;
        pop();
        return result;
    }

    protected abstract Object head();

    public Object element() {
        assert size > 0;

        return head();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    protected abstract void clearImpl();

    public void clear() {
        clearImpl();
        size = 0;
    }
}
