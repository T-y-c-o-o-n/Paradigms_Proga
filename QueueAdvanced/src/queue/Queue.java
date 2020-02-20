package queue;

public interface Queue<Type> {

    // Pre: queue[a_1, a_2, ..., a_n]; size > 0
    // Post: queue[a_1, a_2, ..., a_n, a_n+1]
    void enqueue(Type elem);

    // Pre: queue[a_1, a_2, ..., a_n]; size > 0
    // Post: R = a_1
    Type element();

    // Pre: queue[a_1, a_2, ..., a_n-1, a_n]; size > 0
    // Post: queue[a_2, ..., a_n]; size' = size - 1
    Type dequeue();

    // Pre: -
    // Post: R = size
    int size();

    // Pre: -
    // Post: R = (size == 0)
    boolean isEmpty();

    // Pre: -
    // Post: size == 0
    void clear();
}
