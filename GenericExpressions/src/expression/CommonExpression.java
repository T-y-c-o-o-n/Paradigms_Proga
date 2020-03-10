package expression;

import expression.exceptions.CalculationException;
import expression.exceptions.OverflowException;

import java.util.EnumSet;
import java.util.List;

public interface CommonExpression<T extends Number> {
	String toString();
	String toMiniString();
	String checkString(EnumSet<Oper> allowed);
	T evaluate(int x, int y, int z) throws CalculationException ;
}