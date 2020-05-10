package expression;

import java.util.EnumSet;

public class Const<T extends Number> implements Expression<T> {
    private final T val;

    public Const(T val) {
        this.val = val;
    }

    public T evaluate(T x, T y, T z) {
        return val;
    }

	public String toString() {
		return val.toString();
	}

    public String toMiniString() {
        return toString();
    }

    public String checkString(EnumSet<Oper> allowed) {
        return toString();
    }
}