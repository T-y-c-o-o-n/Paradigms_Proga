package expression.binary;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.OverflowException;

import java.util.EnumSet;

public class CheckedSubtract<T extends Number> extends AbstractBinarOper<T> {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);

    public CheckedSubtract(CommonExpression<T> first, CommonExpression<T> second) {
        super(first, second, Oper.SUB);
    }

    public T calculate(T a, T b) {
        if ((a < 0) && (b > 0) && (Integer.MIN_VALUE + b > a) || (a > 0) && (b < 0) && (Integer.MAX_VALUE + b < a) ||
                (a == 0) && (b == Integer.MIN_VALUE)) {
            overflow(a, b);
        }
        return a - b;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}
