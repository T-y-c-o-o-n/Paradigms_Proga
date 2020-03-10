package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Oper;

public class Abs<T extends Number> extends AbstractUnarOper<T> {
    public Abs(CommonExpression<T> arg) {
        super(arg, Oper.ABS);
    }

    public T calculate(T a) {
        return a >= 0 ? a : -a;
    }
}
