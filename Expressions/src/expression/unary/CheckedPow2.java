package expression.unary;

import expression.*;
import expression.binary.CheckedMultiply;
import expression.exceptions.PowArgumentsException;

public class CheckedPow2 extends AbstractUnarOper {
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
                if (!CheckedMultiply.check(res, a)) {
                    overflow(n);
                }
                res *= a;  // overflow
                n--;
            } else {
                if (!CheckedMultiply.check(a, a)) {
                    overflow(n);
                }
                a *= a;  // overflow
                n /= 2;
            }
        }
        return res;
    }
}