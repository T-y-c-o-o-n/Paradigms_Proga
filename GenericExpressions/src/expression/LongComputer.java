package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class LongComputer implements Computer<Long> {
    public Long min(Long a, Long b) {
        return Long.min(a, b);
    }

    public Long max(Long a, Long b) {
        return Long.max(a, b);
    }

    public Long add(Long a, Long b) {
        return a + b;
    }

    public Long sub(Long a, Long b) {
        return a - b;
    }

    public Long mul(Long a, Long b) {
        return a * b;
    }

    public Long div(Long a, Long b) {
        if (b == 0L) {
            throw new DivisionByZeroException(a.toString() + " / " + b.toString());
        }
        return a / b;
    }

    public Long neg(Long a) {
        return -a;
    }

    public Long cnt(Long a) {
        return (long) Long.bitCount(a);
    }

    public Long parseVal(String str) { return Long.parseLong(str); }
}
