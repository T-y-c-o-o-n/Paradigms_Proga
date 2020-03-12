package expression.unary;

import expression.CommonExpression;
import expression.generic.Computer;
import expression.Oper;

public class Count<T> extends AbstractUnaryOper<T> {
    public Count(CommonExpression<T> arg, Computer<T> computer) {
        super(Oper.CNT, arg, computer);
    }

    public T calculate(T a) {
        return computer.cnt(a);
    }
}
