package expression.unary;

import expression.Expression;
import expression.generic.Computer;
import expression.Oper;

public class Negate<T extends Number> extends AbstractUnaryOper<T> {
    public Negate(Expression<T> arg, Computer<T> computer) {
        super( Oper.NEG, arg, computer);
    }

    public T calculate(T a) {
        return computer.neg(a);
    }
}
