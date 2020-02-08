package expression;

public class Digits extends AbstractUnarOper {
    public Digits(CommonExpression arg) {
        super(arg, Oper.DIG);
    }

    public int evaluate(int x, int y, int z) {
        return sumDigits(arg.evaluate(x, y, z));
    }

    public int sumDigits(int val) {
        int sum = 0;
        while (val != 0) {
            sum += val % 10;
            val /= 10;
        }
        return sum < 0 ? -sum : sum;
    }
}
