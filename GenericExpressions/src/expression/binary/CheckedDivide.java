package expression.binary;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

import java.util.EnumSet;

public class CheckedDivide<T extends Number> extends AbstractBinarOper<T> {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.noneOf(Oper.class);

    public CheckedDivide(CommonExpression<T> first, CommonExpression<T> second) {
        super(first, second, Oper.DIV);
    }

    public T calculate(T a, T b) {
        if (b == 0) {
            throw new DivisionByZeroException("");
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            overflow(a, b);
        }
        return a / b;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}
