package expression.unary;

import expression.Expression;
import expression.Oper;
import expression.generic.Computer;

import java.util.EnumSet;

public abstract class AbstractUnaryOper<T extends Number> implements Expression<T> {
    private final Oper me;
    protected final Expression<T> arg;
    protected final Computer<T> computer;

    public AbstractUnaryOper(Oper me, Expression<T> arg, Computer<T> computer) {
        this.me = me;
        this.arg = arg;
        this.computer = computer;
    }

    public abstract T calculate(T a);

    public T evaluate(T x, T y, T z) {
        return calculate(arg.evaluate(x, y, z));
    }

    public String toString() {
        return me + arg.toString();
    }

    public String toMiniString() {
        return me + arg.toMiniString();
    }

    public String checkString(EnumSet<Oper> allowed) {
        return toMiniString();
    }
}