package expression.unary;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;

import java.util.EnumSet;
import java.util.List;

public class Divide<T extends Number> extends AbstractBinarOper<T> {
	private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
	private static final EnumSet<Oper> secondArgsToAllow = EnumSet.noneOf(Oper.class);

    public Divide(CommonExpression<T> first, CommonExpression<T> second) {
    	super(first, second, Oper.DIV);
    }

    public T calculate(T a, T b) {
    	return a / b;
    }

    public String toMiniString() {
    	return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}