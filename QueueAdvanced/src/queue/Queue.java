package queue;

public interface Queue<Type> {

    // Pre: queue[a_1, a_2, ..., a_n]; size > 0
    // Post: queue[a_1, a_2, ..., a_n, a_n+1]
    void enqueue(Type elem);  // 0

    // Pre: queue[a_1, a_2, ..., a_n]; size > 0
    // Post: R = a_1
    Type element();  // 1

    // Pre: queue[a_1, a_2, ..., a_n-1, a_n]; size > 0
    // Post: queue[a_2, ..., a_n]; size' = size - 1
    Type dequeue();  // 2

    // Pre: -
    // Post: R = size
    int size();  // 3

    // Pre: -
    // Post: R = (size == 0)
    boolean isEmpty();  // 4

    // Pre: -
    // Post: size == 0
    void clear();  // 5
}
