package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.OverflowException;

public class CheckedNegate extends AbstractUnarOper {
    public CheckedNegate(CommonExpression arg) {
        super(arg, Oper.NEG);
    }

    public int calculate(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("2147483648");
        }
        return -a;
    }
}
