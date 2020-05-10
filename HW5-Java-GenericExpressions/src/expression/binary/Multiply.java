package expression.binary;

import expression.Expression;
import expression.generic.Computer;
import expression.Oper;

import java.util.EnumSet;

public class Multiply<T extends Number> extends AbstractBinaryOper<T> {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.MUL);

    public Multiply(Expression<T> first, Expression<T> second, Computer<T> computer) {
    	super(Oper.MUL, first, second, computer);
    }

    public T calculate(T a, T b) { return computer.mul(a, b); }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}