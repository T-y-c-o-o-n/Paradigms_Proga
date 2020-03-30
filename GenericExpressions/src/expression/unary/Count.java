package expression.unary;

import expression.Expression;
import expression.generic.Computer;
import expression.Oper;

public class Count<T extends Number> extends AbstractUnaryOper<T> {
    public Count(Expression<T> arg, Computer<T> computer) {
        super(Oper.CNT, arg, computer);
    }

    public T calculate(T a) {
        return computer.cnt(a);
    }
}
