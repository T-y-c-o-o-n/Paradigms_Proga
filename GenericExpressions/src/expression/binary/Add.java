package expression.binary;

import expression.AbstractBinarOper;
import expression.CommonExpression;
import expression.Oper;
import expression.exceptions.CalculationException;

import java.util.EnumSet;

public class Add<T extends Number> extends AbstractBinarOper<T> {
	private static final EnumSet<Oper> firstArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);
	private static final EnumSet<Oper> secondArgsToAllow = EnumSet.of(Oper.ADD, Oper.SUB, Oper.MUL, Oper.DIV);

    public Add(CommonExpression<T> first, CommonExpression<T> second) {
    	super(first, second, Oper.ADD);
    }

    public T calculate(T a, T b) {
        return a.add(b);
    }

    public String toMiniString() {
    	return super.toMiniString(firstArgsToAllow, secondArgsToAllow);
    }
}