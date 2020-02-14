package expression.unary;

import expression.AbstractBinarOper;
import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.CalculationException;
import expression.exceptions.LogArgumentsException;

import java.util.EnumSet;
import java.util.List;

public class CheckedLog2 extends AbstractUnarOper {
    private static final EnumSet<Oper> allowed = EnumSet.noneOf(Oper.class);

    public CheckedLog2(CommonExpression arg) {
        super(arg, Oper.LOG2);
    }

    public int calculate(int b) {
        int a = 2;
        if (b <= 0) {
            throw new LogArgumentsException("");
        }
        int res = 0;
        while(b > 1) {
            if (b < a) {
                break;
            }
            res++;
            b /= a;
        }
        return res
                ;
    }
}