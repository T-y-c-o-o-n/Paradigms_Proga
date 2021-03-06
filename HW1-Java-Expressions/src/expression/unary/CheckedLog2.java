package expression.unary;

import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.LogArgumentsException;

public class CheckedLog2 extends AbstractUnarOper {
    public CheckedLog2(CommonExpression arg) {
        super(arg, Oper.LOG2);
    }

    public int calculate(int b) {
        int a = 2;
        if (b <= 0) {
            throw new LogArgumentsException(Oper.LOG2.toString() + b + " argument <= 0");
        }
        int res = 0;
        while(b > 1) {
            res++;
            b /= a;
        }
        return res;
    }
}