package expression;

import expression.exceptions.CalculationException;

import java.util.EnumSet;
import java.util.List;

public class Multiply extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.MUL);

    public Multiply(CommonExpression first, CommonExpression second) {
    	super(first, second, Oper.MUL);
    }

    public int evaluate(int x, int y, int z) {
        return arg1.evaluate(x, y, z) * arg2.evaluate(x, y, z);
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}