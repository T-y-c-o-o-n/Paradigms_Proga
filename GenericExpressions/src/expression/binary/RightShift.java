package expression.binary;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;

import java.util.EnumSet;
import java.util.List;

public class RightShift<T extends Number> extends AbstractBinarOper<T> {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);

    public RightShift(CommonExpression<T> first, CommonExpression<T> second) {
        super(first, second, Oper.RSH);
    }

    public T calculate(T a, T b) {
        return a >> b;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}