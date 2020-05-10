package expression.unary;

import expression.CommonExpression;
import expression.Oper;

public class Square extends AbstractUnarOper {
    public Square(CommonExpression arg) {
        super(arg, Oper.SQR);
    }

    public int calculate(int a) {
        return a * a;
    }
}
