package expression.binary;

import java.util.EnumSet;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;

public class CheckedAdd extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);

    public CheckedAdd(CommonExpression first, CommonExpression second) {
        super(first, second, Oper.ADD);
    }

    public int calculate(int a, int b) {
        if ((a < 0) && (b < 0) && (Integer.MIN_VALUE - b > a) || (a > 0) && (b > 0) && (Integer.MAX_VALUE - b < a)) {
            overflow(a, b);
        }
        return a + b;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}
