package expression;

import expression.exceptions.OverflowException;

public class CheckedNegate extends AbstractUnarOper {
    public CheckedNegate(CommonExpression arg) {
        super(arg, Oper.NEG);
    }

    public int evaluate(int x, int y, int z) {
        int a = arg.evaluate(x, y, z);
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("");
        }
        return -a;
    }
}
