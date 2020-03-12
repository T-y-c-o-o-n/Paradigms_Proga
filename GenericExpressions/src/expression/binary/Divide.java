package expression.binary;

import expression.CommonExpression;
import expression.generic.Computer;
import expression.Oper;

import java.util.EnumSet;

public class Divide<T> extends AbstractBinaryOper<T> {
	private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
	private static final EnumSet<Oper> secondArgsToAllow = EnumSet.noneOf(Oper.class);

    public Divide(CommonExpression<T> first, CommonExpression<T> second, Computer<T> computer) {
    	super(Oper.DIV, first, second, computer);
    }

    public T calculate(T a, T b) {
    	return computer.div(a, b);
    }

    public String toMiniString() {
    	return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}