package expression.unary;

import expression.*;
import expression.binary.CheckedMultiply;
import expression.exceptions.PowArgumentsException;

import java.util.EnumSet;

public class CheckedPow2 extends AbstractUnarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.noneOf(Oper.class);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.noneOf(Oper.class);

    public CheckedPow2(CommonExpression arg) {
        super(arg, Oper.POW2);
    }

    public int calculate(int n) {
        int a = 2;
        if (n < 0) {
            throw new PowArgumentsException("");
        }
        int res = 1;
        while (n > 0) {
            if (n % 2 == 1) {
                res = new CheckedMultiply(null, null).calculate(res, a);  // overflow
                n--;
            } else {
                a = new CheckedMultiply(null, null).calculate(a, a);  // overflow
                n /= 2;
            }
        }
        return res;
    }
}