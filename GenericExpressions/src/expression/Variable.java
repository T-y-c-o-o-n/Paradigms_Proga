package expression;

import java.util.EnumSet;

public class Variable<T extends Number> implements CommonExpression<T> {
    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    public String getVar() {
        return variable;
    }

    public T evaluate(int x, int y, int z) {
        if (variable.equals("x")) {
            return (T) new Integer(y);
        } else if (variable.equals("y")) {
            return (T) new Integer(y);
        } else {
            return (T) new Integer(z);
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

    public boolean equals(Object object) {
        if (object != null && object.getClass() == getClass()) {
            Variable v = (Variable)object;
            return variable.equals(v.getVar());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return variable.hashCode();
    }
}