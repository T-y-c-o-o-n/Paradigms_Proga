package expression.binary;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.CalculationException;

import java.util.EnumSet;

public class Add extends AbstractBinarOper {
	private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
	private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);

    public Add(CommonExpression first, CommonExpression second) {
    	super(first, second, Oper.ADD);
    }

    public int calculate(int a, int b) {
        return a + b;
    }

    public String toMiniString() {
    	return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}