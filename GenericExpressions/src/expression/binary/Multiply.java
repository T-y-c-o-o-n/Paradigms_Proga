package expression.binary;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.CalculationException;

import java.util.EnumSet;
import java.util.List;

public class Multiply<T extends Number> extends AbstractBinarOper<T> {
    private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.MUL, Oper.DIV);
    private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.MUL);

    public Multiply(CommonExpression<T> first, CommonExpression<T> second) {
    	super(first, second, Oper.MUL);
    }

    public T calculate(T a, T b) { return a * b; }

    public String toMiniString() {
        return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}