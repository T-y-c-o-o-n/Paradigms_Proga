package expression.binary;

import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.LogArgumentsException;

import java.util.EnumSet;

public class CheckedLog extends AbstractBinarOper {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.noneOf(Oper.class);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.noneOf(Oper.class);

    public CheckedLog(CommonExpression first, CommonExpression second) {
        super(first, second, Oper.LOG);
    }

    public int calculate(int b, int a) {
        if (a <= 1) {
            throw new LogArgumentsException(a + Oper.LOG.toString() + b + " base <= 0 or = 1");
        }
        if (b <= 0) {
            throw new LogArgumentsException(a + Oper.LOG.toString() + b + " argument <= 0");
        }
        int res = 0;
        while(b > 1) {
            if (b < a) {
                break;
            }
            res++;
            b /= a;
        }
        return res;
    }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}