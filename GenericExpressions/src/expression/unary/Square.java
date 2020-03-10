package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Oper;

public class Square<T extends Number> extends AbstractUnarOper<T> {
    public Square(CommonExpression<T> arg) {
        super(arg, Oper.SQR);
    }

    public int calculate(int a) {
        return a * a;
    }
}
