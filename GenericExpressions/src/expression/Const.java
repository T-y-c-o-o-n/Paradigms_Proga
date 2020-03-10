package expression;

import java.util.EnumSet;

public class Const<T extends Number> implements CommonExpression<T> {
    private final T val;

    public Const(T val) {
        this.val = val;
    }

    public Number getVal() {
        return val;
    }

    public T evaluate(int x, int y, int z) {
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

    public boolean equals(Object object) {
        if (object != null && object.getClass() == getClass()) {
            Const<T> c = (Const<T>)object;
            return val.equals(c.getVal());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return val.hashCode();
    }
}