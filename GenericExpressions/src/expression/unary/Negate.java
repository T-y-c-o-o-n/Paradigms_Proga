package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Computer;
import expression.Oper;

public class Negate<T> extends AbstractUnarOper<T> {
    public Negate(CommonExpression<T> arg, Computer<T> computer) {
        super( Oper.NEG, arg, computer);
    }

    public T calculate(T a) {
        return computer.neg(a);
    }
}
