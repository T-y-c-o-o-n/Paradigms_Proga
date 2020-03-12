package expression.binary;

import expression.CommonExpression;
import expression.generic.Computer;
import expression.Oper;

import java.util.EnumSet;

public class Max<T> extends AbstractBinaryOper<T> {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);

    public Max(CommonExpression<T> arg1, CommonExpression<T> arg2, Computer<T> computer) {
        super(Oper.MIN, arg1, arg2, computer);
    }

    public T calculate(T a, T b) { return computer.max(a, b); }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}