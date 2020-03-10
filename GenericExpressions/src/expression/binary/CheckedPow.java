package expression.binary;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;
import expression.binary.CheckedMultiply;
import expression.exceptions.PowArgumentsException;

import java.util.EnumSet;

public class CheckedPow<T extends Number> extends AbstractBinarOper<T> {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.noneOf(Oper.class);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.noneOf(Oper.class);

    public CheckedPow(CommonExpression<T> first, CommonExpression<T> second) {
        super(first, second, Oper.POW);
    }

    public T calculate(T a, T n) {
        if (n < 0) {
            throw new PowArgumentsException(a + Oper.POW.toString() + " degree <= 0");
        }
        if (a == 0 && n == 0) {
            throw new PowArgumentsException(a + Oper.POW.toString() + n);
        }
        if (a == 0 || a == 1) {
            return a;
        }
        if (a == -1) {
            return (n % 2 == 0) ? 1 : -1;
        }
        int res = 1;
        while (n > 0) {
            if (n % 2 == 1) {
                if (!CheckedMultiply.check(res, a)) {
                    overflow(a, n);
                }
                res *= a;  // overflow
                n--;
            } else {
                if (!CheckedMultiply.check(a, a)) {
                    overflow(a, n);
                }
                a *= a;  // overflow
                n /= 2;
            }
        }
        return res;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}