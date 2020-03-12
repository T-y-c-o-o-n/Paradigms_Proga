package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class ShortComputer implements Computer<Short> {
    public Short min(Short a, Short b) {
        return (short) Math.min(a, b);
    }

    public Short max(Short a, Short b) {
        return (short) Math.max(a, b);
    }

    public Short add(Short a, Short b) {
        return (short) (a + b);
    }

    public Short sub(Short a, Short b) {
        return (short) (a - b);
    }

    public Short mul(Short a, Short b) {
        return (short) (a * b);
    }

    public Short div(Short a, Short b) {
        if (b == 0L) {
            throw new DivisionByZeroException(a.toString() + " / " + b.toString());
        }
        return (short) (a / b);
    }

    public Short neg(Short a) {
        return (short) -a;
    }

    public Short cnt(Short a) {
        return (short) Integer.bitCount (0xFFFF & a);
    }

    public Short parseVal(String str) { return (short) Integer.parseInt(str); }
}
