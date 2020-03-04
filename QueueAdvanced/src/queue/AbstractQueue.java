package queue;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

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

    // public abstract Iterator<Object> iterator();

    protected abstract Queue getQueue();

    public Queue filter(Predicate<Object> predicate) {
        assert predicate != null;

        Object[] elements = toArray();
        Queue result = getQueue();
        for (Object e : elements) {
            if (predicate.test(e)) {
                result.enqueue(e);
            }
        }

        return result;
    }

    public Queue map(Function<Object, Object> function) {
        assert function != null;

        Object[] elements = toArray();
        Queue result = getQueue();
        for (Object e : elements) {
            result.enqueue(function.apply(e));
        }

        return result;
    }
}
