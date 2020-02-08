package expression;

import java.util.EnumSet;
import expression.exceptions.OverflowException;

public class CheckedAdd extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);

    public CheckedAdd(CommonExpression first, CommonExpression second) {
        super(first, second, Oper.ADD);
    }

    public int evaluate(int x, int y, int z) {
        int a = arg1.evaluate(x, y, z), b = arg2.evaluate(x, y, z);
        int res = a + b;
        if (a < 0 && b < 0 && res >= 0 || a > 0 && b > 0 && res <= 0) {
            throw new OverflowException("");
        }
        return res;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}
