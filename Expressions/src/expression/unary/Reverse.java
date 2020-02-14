package expression.unary;

import expression.AbstractUnarOper;
import expression.CommonExpression;
import expression.Oper;

public class Reverse extends AbstractUnarOper {
    public Reverse(CommonExpression arg) {
        super(arg, Oper.REV);
    }

    public int calculate(int val) {
        StringBuilder sb = new StringBuilder();
        if (Math.abs(val) > 1_000_000_000)  System.out.println(val);
        boolean isNeg = false;
        if (val < 0) {
            isNeg = true;
            val = -val;
        }
        sb.append(val).reverse();
        if (isNeg) {
            sb.insert(0, '-');
        }
        if (Math.abs(val) > 1_000_000_000)  System.out.println(sb);
        return Integer.parseInt(sb.toString());
    }
}
