package queue;

import java.util.function.Function;
import java.util.function.Predicate;

interface Queue extends Iterable<Object> {
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

    // Pre: predicate != null
    // Post: R = ( Q = {e_i1, e_i2, ..., e_ik  |  predicate(e) == true } )
    Queue filter(Predicate<Object> predicate);

    // Pre: fun != null
    // Post: R = ( Q = {fun(e_i1), fun(e_i2), ..., fun(e_ik)} )
    Queue map(Function<Object, Object> function);
}
