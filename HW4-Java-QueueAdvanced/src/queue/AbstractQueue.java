package queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
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

    public abstract Iterator<Object> iterator();

    protected abstract Queue getQueue();

    public Queue filter(Predicate<Object> predicate) {
        assert predicate != null;

        Queue result = getQueue();
        for (Object o : this) {
            if (predicate.test(o)) {
                result.enqueue(o);
            }
        }
        return result;
    }

    public Queue map(Function<Object, Object> function) {
        assert function != null;

        Queue result = getQueue();
        for (Object o : this) {
            result.enqueue(function.apply(o));
        }
        return result;
    }

    protected abstract class AbstractQueueIterator implements Iterator<Object> {
        private int left = AbstractQueue.this.size;

        public boolean hasNext() {
            return left > 0;
        }

        public Object next() {
            if (left <= 0) {
                throw new NoSuchElementException();
            }
            left--;
            return nextImpl();
        }

        protected abstract Object nextImpl();
    }
}
