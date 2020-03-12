package expression.unary;

import expression.CommonExpression;
import expression.generic.Computer;
import expression.Oper;

public class Negate<T> extends AbstractUnaryOper<T> {
    public Negate(CommonExpression<T> arg, Computer<T> computer) {
        super( Oper.NEG, arg, computer);
    }

    public T calculate(T a) {
        return computer.neg(a);
    }
}
