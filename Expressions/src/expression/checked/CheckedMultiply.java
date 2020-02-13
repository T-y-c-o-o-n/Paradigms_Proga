package expression.checked;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.OverflowException;

import java.util.EnumSet;

public class CheckedMultiply extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.MUL);

    public CheckedMultiply(CommonExpression first, CommonExpression second) {
        super(first, second, Oper.MUL);
    }

    public int evaluate(int x, int y, int z) {
        int a = arg1.evaluate(x, y, z), b = arg2.evaluate(x, y, z);
        int res = a * b;
        /*int absA = a >= 0 ? a : -a;
        int absB = b >= 0 ? b : -b;
        if (((absA >>> 15 != 0) || (absB >>> 15 != 0)) &&
                ((a == Integer.MIN_VALUE && b == -1) || (b != 0 && res / b != a))) {
            throw new OverflowException("");
        }*/
        if (a != 0 && b != 0 && (res / a != b || res / b != a)) {
            throw new OverflowException("");
        }
        return res;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}
