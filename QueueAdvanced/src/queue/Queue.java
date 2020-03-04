package queue;

interface Queue {
    // INV:
    // Q = {e_first, e_2, e_3, ..., e_n-1, e_last}
    // e_i != null
    // First in - First out
    
    
    // Pre: e not null
    // Post: Q' = {e_1, e_2, ..., e_n, e} && |Q| > 0
    void enqueue(Object e);

    // Pre: |Q| > 0
    // Post: R = e_1 && Q' = {e_2, ..., e_n} && |Q'| = |Q| - 1
    Object dequeue();

    // Pre: |Q| > 0
    // Post: R = e_1
    Object element();

    // Pre: true
    // Post: R = |Q|
    int size();

    // Pre: true
    // Post: R = (|Q| == 0)
    boolean isEmpty();

    // Pre: true
    // Post: |Q| == 0
    void clear();

    // Pre: true
    // Post: R = [e_first, e_2, ..., e_n-1, e_last]
    Object[] toArray();
}
