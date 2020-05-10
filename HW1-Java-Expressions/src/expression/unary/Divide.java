package expression.unary;

import expression.binary.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;

import java.util.EnumSet;

public class Divide extends AbstractBinarOper {
	private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
	private static final EnumSet<Oper> secondArgsToAllow = EnumSet.noneOf(Oper.class);

    public Divide(CommonExpression first, CommonExpression second) {
    	super(first, second, Oper.DIV);
    }

    public int calculate(int a, int b) {
    	return a / b;
    }

    public String toMiniString() {
    	return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}