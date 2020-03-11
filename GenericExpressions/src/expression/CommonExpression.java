package expression;

import expression.exceptions.CalculationException;

import java.util.EnumSet;

public interface CommonExpression<T> extends TripleExpression<T> {
	String toString();
	String toMiniString();
	String checkString(EnumSet<Oper> allowed);
	T evaluate(T x, T y, T z) throws CalculationException ;
}