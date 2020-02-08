package expression;

import java.util.EnumSet;

public class Const extends AbstractValue implements CommonExpression {
    private final Number val;

    public Const(Number val) {
        super(Oper.CONST);
        this.val = val;
    }

    public Number getVal() {
        return val;
    }

    public int evaluate(int x, int y, int z) {
        return val.intValue();
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
            Const c = (Const)object;
            return val.equals(c.getVal());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return val.hashCode();
    }
}