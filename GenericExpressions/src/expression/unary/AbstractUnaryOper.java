package expression.unary;

import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.OverflowException;
import expression.generic.Computer;

import java.util.EnumSet;

public abstract class AbstractUnaryOper<T> implements CommonExpression<T> {
    private final Oper me;
    protected final CommonExpression<T> arg;
    protected final Computer<T> computer;

    public AbstractUnaryOper(Oper me, CommonExpression<T> arg, Computer<T> computer) {
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