package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Oper;

public class Negate<T extends Number> extends AbstractUnarOper<T> {
    public Negate(CommonExpression<T> arg) {
        super(arg, Oper.NEG);
    }

    public int calculate(int a) {
        return -a;
    }
}
