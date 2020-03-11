package expression;

public interface Computer<T> {
    T add(T a, T b);
    T sub(T a, T b);
    T mul(T a, T b);
    T div(T a, T b);
    T neg(T a);
    T parseVal(String str);
}
