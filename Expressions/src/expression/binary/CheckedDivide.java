package expression.binary;

import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.DivisionByZeroException;

import java.util.EnumSet;

public class CheckedDivide extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.noneOf(Oper.class);

    public CheckedDivide(CommonExpression first, CommonExpression second) {
        super(first, second, Oper.DIV);
    }

    public int calculate(int a, int b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            overflow(a, b);
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
