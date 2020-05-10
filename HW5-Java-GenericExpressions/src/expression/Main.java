package expression;

import expression.generic.IntComputer;
import expression.parser.ExpressionParser;

public class Main {
	public static void main(String[] args) throws Exception {
		ExpressionParser<Integer> parser = new ExpressionParser<>();
		System.out.println(parser.parse("-z * 45 / x * (-y / z)", new IntComputer()).evaluate(13, 14, 15));
	}
}