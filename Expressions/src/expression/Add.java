package expression;

import expression.exceptions.CalculationException;

import java.util.EnumSet;

public class Add extends AbstractBinarOper {
	private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
	private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);

    public Add(CommonExpression first, CommonExpression second) {
    	super(first, second, Oper.ADD);
    }

    public int evaluate(int x, int y, int z) throws CalculationException {
        return arg1.evaluate(x, y, z) + arg2.evaluate(x, y, z);
    }

    public String toMiniString() {
    	return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}