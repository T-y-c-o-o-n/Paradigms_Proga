package expression;

import expression.exceptions.CalculationException;

import java.util.EnumSet;
import java.util.List;

public class Subtract extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);

    public Subtract(CommonExpression first, CommonExpression second) {
    	super(first, second, Oper.SUB);
    }

    public int evaluate(int x, int y, int z) {
        return arg1.evaluate(x, y, z) - arg2.evaluate(x, y, z);
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}