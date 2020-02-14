package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Oper;

public class Digits extends AbstractUnarOper {
    public Digits(CommonExpression arg) {
        super(arg, Oper.DIG);
    }

    public int calculate(int val) {
        int sum = 0;
        while (val != 0) {
            sum += val % 10;
            val /= 10;
        }
        return sum < 0 ? -sum : sum;
    }
}
