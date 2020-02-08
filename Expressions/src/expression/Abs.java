package expression;

public class Abs extends AbstractUnarOper {
    public Abs(CommonExpression arg) {
        super(arg, Oper.ABS);
    }

    public int evaluate(int x, int y, int z) {
        int res = Math.abs(arg.evaluate(x, y, z));
        return res >= 0 ? res : -res;
    }
}
