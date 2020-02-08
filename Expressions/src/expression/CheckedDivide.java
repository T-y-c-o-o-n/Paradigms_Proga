package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

import java.util.EnumSet;

public class CheckedDivide extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.noneOf(Oper.class);

    public CheckedDivide(CommonExpression first, CommonExpression second) {
        super(first, second, Oper.DIV);
    }

    public int evaluate(int x, int y, int z) {
        int a = arg1.evaluate(x, y, z), b = arg2.evaluate(x, y, z);
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("");
        }
        if (b == 0) {
            throw new DivisionByZeroException("");
        }
        return a / b;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}
