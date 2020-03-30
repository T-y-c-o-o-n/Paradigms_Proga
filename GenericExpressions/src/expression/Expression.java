package expression;

import expression.exceptions.CalculationException;
import java.util.EnumSet;

public interface Expression<T extends Number> {
	String toString();
	String toMiniString();
	String checkString(EnumSet<Oper> allowed);
	T evaluate(T x, T y, T z) throws CalculationException;
}
