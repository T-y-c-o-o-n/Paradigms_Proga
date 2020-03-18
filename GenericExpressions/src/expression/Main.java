package expression;

import expression.generic.IntComputer;
import expression.parser.ExpressionParser;
import expression.parser.Parser;

public class Main {
	public static void main(String[] args) throws Exception {
		Parser<Integer> parser = new ExpressionParser<>();
		System.out.println(parser.parse("-z ***** 45 / x * (-y //// z)", new IntComputer()));
	}
}