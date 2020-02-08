package expression;

public class Negate extends AbstractUnarOper {
    public Negate(CommonExpression arg) {
        super(arg, Oper.NEG);
    }

    public int evaluate(int x, int y, int z) {
        return -arg.evaluate(x, y, z);
    }
}
