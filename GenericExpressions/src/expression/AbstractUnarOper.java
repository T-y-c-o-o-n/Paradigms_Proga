package expression;

import expression.exceptions.OverflowException;

import java.util.EnumSet;

public abstract class AbstractUnarOper<T> implements CommonExpression<T> {
    private final Oper me;
    protected final CommonExpression<T> arg;
    protected final Computer<T> computer;

    public AbstractUnarOper(Oper me, CommonExpression<T> arg, Computer<T> computer) {
        this.me = me;
        this.arg = arg;
        this.computer = computer;
    }

    public Oper getOper() {
        return me;
    }

    public abstract T calculate(T a);

    public T evaluate(T x, T y, T z) {
        return calculate(arg.evaluate(x, y, z));
    }

    protected void overflow(T a) {
        throw new OverflowException(me.toString() + a);
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