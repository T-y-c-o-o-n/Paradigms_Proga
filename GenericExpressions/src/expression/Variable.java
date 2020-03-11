package expression;

import java.util.EnumSet;

public class Variable<T> implements CommonExpression<T> {
    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    public String getVar() {
        return variable;
    }

    public T evaluate(T x, T y, T z) {
        if (variable.equals("x")) {
            return x;
        } else if (variable.equals("y")) {
            return y;
        } else {
            return z;
        }
    }

	public String toString() {
		return variable;
	}

    public String toMiniString() {
        return toString();
    }

    public String checkString(EnumSet<Oper> allowed) {
        return toString();
    }
}