package expression.binary;

import expression.CommonExpression;
import expression.Oper;

import java.util.EnumSet;

public class CheckedMultiply extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.MUL);

    public CheckedMultiply(CommonExpression first, CommonExpression second) {
        super(first, second, Oper.MUL);
    }

    public static boolean check(int a, int b) {
        if (a == 0 || b == 0) {
            return true;
        }
        if (a == Integer.MIN_VALUE) {
            if (b != 1) {
                return false;
            }
            return true;
        }
        if (b == Integer.MIN_VALUE) {
            if (a != 1) {
                return false;
            }
            return true;
        }
        if (a == 1 || a == -1 || b == 1 || b == -1) {
            return true;
        }
        if (a > 0 && b > 0 && Integer.MAX_VALUE / b < a || a < 0 && b < 0 && Integer.MAX_VALUE / b > a
                || a > 0 && b < 0 && Integer.MIN_VALUE / b < a || a < 0 && b > 0 && Integer.MIN_VALUE / b > a) {
            return false;
        }
        return true;
    }

    public int calculate(int a, int b) {
        if (!check(a, b)) {
            overflow(a, b);
        }
        return a * b;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}
