package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Computer;
import expression.Oper;

public class Count<T> extends AbstractUnarOper<T> {
    public Count(CommonExpression<T> arg, Computer<T> computer) {
        super(Oper.CNT, arg, computer);
    }

    public T calculate(T a) {
        return computer.cnt(a);
    }
}
