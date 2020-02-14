package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Oper;

public class Abs extends AbstractUnarOper {
    public Abs(CommonExpression arg) {
        super(arg, Oper.ABS);
    }

    public int calculate(int a) {
        return a >= 0 ? a : -a;
    }
}
