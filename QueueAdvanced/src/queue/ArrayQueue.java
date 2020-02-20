package queue;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArrayQueue<Type> extends AbstractQueue<Type> implements Queue<Type> {
    // INV:
    //
    List<Object> list = new LinkedList<Object>();
    private Type[] elements;
    private int first, last;

    public ArrayQueue() {
        super();
        elements = new Type[];
        // elements = (Type[]) Array.newInstance(Type.class, 2);
        size = 0;
        first = 0;
        last = 0;
    }

    public void enqueue(Type elem) {
        if (last == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        elements[last++] = elem;
        size++;
    }

    public Type element() {
        assert size > 0;
        return elements[first];
    }

    public Type dequeue() {
        assert size > 0;
        Type element = elements[first];
        elements[first] = null;
        first++;
        size--;
        return element;
    }
}
