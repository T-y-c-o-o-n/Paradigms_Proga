package expression.binary;

import java.util.EnumSet;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;

public class CheckedAdd<T extends Number> extends AbstractBinarOper<T> {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);

    public CheckedAdd(CommonExpression<T> first, CommonExpression<T> second) {
        super(first, second, Oper.ADD);
    }

    public T calculate(T a, T b) {
        if ((a < 0) && (b < 0) && (Integer.MIN_VALUE - b > a) || (a > 0) && (b > 0) && (Integer.MAX_VALUE - b < a)) {
            overflow(a, b);
        }
        return a.add(b);
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}
