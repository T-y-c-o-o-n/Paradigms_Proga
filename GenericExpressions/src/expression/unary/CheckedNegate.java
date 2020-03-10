package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.OverflowException;

public class CheckedNegate<T extends Number> extends AbstractUnarOper<T> {
    public CheckedNegate(CommonExpression<T> arg) {
        super(arg, Oper.NEG);
    }

    public T calculate(T a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("2147483648");
        }
        return -a;
    }
}
