package queue;

public class LinkedQueue<Type> extends AbstractQueue<Type> implements Queue<Type> {
    private Node first, last;

    public LinkedQueue() {
        super();
        first = new Node(null);
        last = new Node(null);
        first.prev = last;
        size = 0;
    }

    public void enqueue(Type element) {
        last.prev = new Node(element);
        last = last.prev;
        if (size == 0) {
            first = last;
        }
        size++;
    }

    public Type element() {
        assert size > 0;
        return first.value;
    }

    public Type dequeue() {
        assert size > 0;
        Type element = first.value;
        first = first.prev;
        size--;
        return element;
    }

    private class Node {
        private final Type value;  // final!!
        private final Node prev;  // final!!

        private Node(Type value) {
            this.value = value;
            LinkedQueue.this.size();  // если класс Node не static, то есть неявная ссылка на класс, который его создал
            // если Node static, то нет неявной ссылки на LinkedQueue, и нельзя
            LinkedQueue outer = queue.LinkedQueue.this;
            LinkedQueue outerr = LinkedQueue.this;  // внутренний класс может и короткое имя юзать
        }
    }
}
