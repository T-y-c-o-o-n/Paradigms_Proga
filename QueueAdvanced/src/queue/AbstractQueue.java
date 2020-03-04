package queue;

public abstract class AbstractQueue implements Queue {
    private int size = 0;

    protected abstract void enqueueImpl(Object e);

    public void enqueue(Object e) {
        assert e != null;

        enqueueImpl(e);
        size++;
    }

    protected abstract void dequeueImpl();

    public Object dequeue() {
        assert size > 0;

        Object result = head();
        dequeueImpl();
        size--;
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

    public abstract Object[] toArray();
}
