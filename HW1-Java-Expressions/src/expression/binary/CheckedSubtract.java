package expression.binary;

import expression.CommonExpression;
import expression.Oper;

import java.util.EnumSet;

public class CheckedSubtract extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);

    public CheckedSubtract(CommonExpression first, CommonExpression second) {
        super(first, second, Oper.SUB);
    }

    public int calculate(int a, int b) {
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
