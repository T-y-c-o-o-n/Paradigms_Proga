package expression;

import java.util.EnumSet;

public class Variable extends AbstractValue implements CommonExpression {
    private final String variable;

    public Variable(String variable) {
        super(Oper.VAR);
        this.variable = variable;
    }

    public String getVar() {
        return variable;
    }

    public int evaluate(int x, int y, int z) {
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