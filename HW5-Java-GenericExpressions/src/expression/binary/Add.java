package expression.binary;

import expression.Expression;
import expression.generic.Computer;
import expression.Oper;

import java.util.EnumSet;

public class Add<T extends Number> extends AbstractBinaryOper<T> {
	private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
	private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);

    public Add(Expression<T> first, Expression<T> second, Computer<T> computer) {
    	super(Oper.ADD, first, second, computer);
    }

    public T calculate(T a, T b) {
        return computer.add(a, b);
    }

    public String toMiniString() {
    	return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}