package expression;

import expression.exceptions.CalculationException;

import java.util.EnumSet;
import java.util.List;

public interface CommonExpression extends TripleExpression {
	String toString();
	String toMiniString();
	String checkString(EnumSet<Oper> allowed);
	int evaluate(int x, int y, int z) throws CalculationException ;
	int getPriority();
}