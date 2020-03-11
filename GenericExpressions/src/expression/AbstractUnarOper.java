package expression;

import expression.exceptions.OverflowException;

import java.util.EnumSet;

public abstract class AbstractUnarOper<T extends Number> implements CommonExpression<T> {
    protected CommonExpression<T> arg;
    private final Oper me;

    public AbstractUnarOper(CommonExpression<T> arg, Oper me) {
        this.arg = arg;
        this.me = me;
    }

    public void setArg(CommonExpression<T> newArg) {
        arg = newArg;
    }

    public CommonExpression<T> getArg() {
        return arg;
    }

    public Oper getOper() {
        return me;
    }

    public String toString() {
        return me +
                arg.toString();
    }

    public String toMiniString() {
        return me +
                arg.toMiniString();
    }

    public abstract T calculate(T a);

    public T evaluate(int x, int y, int z) {
        return calculate(arg.evaluate(x, y, z));
    }

    protected void overflow(T a) {
        throw new OverflowException(me.toString() + a);
    }

    public String checkString(EnumSet<Oper> allowed) {
        return toMiniString();
    }

    public boolean equals(Object object) {
        if (object != null && object.getClass() == getClass()) {
            AbstractUnarOper<T> exp = (AbstractUnarOper<T>)object;
            return me.equals(exp.getOper()) && arg.equals(exp.getArg());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return toString().hashCode();
    }
}