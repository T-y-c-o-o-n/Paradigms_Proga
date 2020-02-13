package expression.checked;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Const;
import expression.Oper;
import expression.checked.CheckedMultiply;
import expression.exceptions.PowArgumentsException;

import java.util.EnumSet;

public class CheckedPow extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.noneOf(Oper.class);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.noneOf(Oper.class);

    public CheckedPow(CommonExpression first, CommonExpression second) {
        super(first, second, Oper.POW);
    }

    public int evaluate(int x, int y, int z) {
        int a = arg1.evaluate(x, y, z), n = arg2.evaluate(x, y, z);
        if (a == 0 && n == 0 || n < 0) {
            throw new PowArgumentsException("");
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
                res = new CheckedMultiply(new Const(res), new Const(a)).evaluate(x, y, z);  // overflow
                n--;
            } else {
                a = new CheckedMultiply(new Const(a), new Const(a)).evaluate(x, y, z);  // overflow
                n /= 2;
            }
        }
        return res;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}