package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Oper;

public class Negate extends AbstractUnarOper {
    public Negate(CommonExpression arg) {
        super(arg, Oper.NEG);
    }

    public int calculate(int a) {
        return -a;
    }
}
