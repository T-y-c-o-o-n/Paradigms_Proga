package expression;

import expression.binary.CheckedPow;
import expression.parser.*;

public class Main {
	public static void main(String[] args) {
		int z=-1538579178;
		System.out.println(Math.abs(-7));
		//System.out.println(new Digits(new Const(Integer.MIN_VALUE)).evaluate(1, 1, 1));
		System.out.println(Integer.MIN_VALUE);

		Parser parser = new ExpressionParser();
		System.out.println("PARSER:\n");
		System.out.println(parser.parse("log2 (22323)").toString());
		System.out.println(parser.parse("log2 4").toString());
		System.out.println(parser.parse("log2 8").toString());
		System.out.println(parser.parse("log2 16").toString());
		System.out.println(parser.parse("1 ** 10").toString());
		System.out.println(parser.parse("2 ** 10").toString());
		System.out.println(parser.parse("4 ** 3 ** 2").evaluate(0, 0, 0));
		System.out.println(parser.parse("64 ** 2").evaluate(0, 0, 0));
		System.out.println(parser.parse("1 ** 10").evaluate(0, 0, 0));
		System.out.println(parser.parse("2 ** 10").evaluate(0, 0, 0));
		System.out.println(parser.parse("5 ** 5") instanceof Const);
		System.out.println(parser.parse("1 ** 10") instanceof CheckedPow);
		System.out.println(parser.parse("10 // 2") instanceof Const);/*
		System.out.println(parser.parse("5 + y * -1").toString());
		System.out.println(parser.parse("5 / y - 1").toString());
		System.out.println(parser.parse("-5 / y - 1").toString());
		System.out.println(parser.parse("5 / -y - 1").toString());
		System.out.println(parser.parse("5 / y - -1").toString());
		System.out.println(parser.parse("x - y + z").toString());
		System.out.println(parser.parse("   x   *     z   ").toString());
		System.out.println(parser.parse("x / y / z").toString());
		System.out.println(parser.parse("x / y * z").toString());
		System.out.println(parser.parse("x * y / z").toString());
		System.out.println(parser.parse("x * y * z").toString());
		System.out.println(parser.parse("(2-y) + - y  <<  2").toMiniString());
		System.out.println(parser.parse("2-y + - (y<<2)").toMiniString());
		System.out.println(parser.parse("2-y + - (y<<2)").evaluate(0, 2, 0));
		System.out.println(parser.parse("  (3)*  z<<10  ").toMiniString());
		System.out.println(parser.parse("x/  -  2>>2 ").toMiniString());
		System.out.println(parser.parse("x>>y + z-1/10").toMiniString());
		System.out.println(parser.parse("---\t\t-5 + 16   *x*y + 1 * z - -11 ").toMiniString());
		// System.out.println(parser.parse("").toMiniString());
		// System.out.println(parser.parse("").toMiniString());
		System.out.println(parser.parse("x--y--z").toMiniString());
		System.out.println(parser.parse("2<<2-0/--2*555" ).toMiniString());
		System.out.println(parser.parse("(((x-1)-(x))+((y)-(y))+((z)-(z)))").toMiniString());
		System.out.println(parser.parse("   (   ((x)-(x))+((y)-(y))+((z)-(z))  )   ").evaluate(1, 2, 3));
		System.out.println(parser.parse("(".repeat(500) + "x + y + -10*-z" + ")".repeat(500)).toMiniString());
		System.out.println(parser.parse("x * y - z / x + abs sqr -z").toMiniString());
		System.out.println(parser.parse(" 23 + abs sqr --1234 * x ").toMiniString());
		System.out.println(parser.parse(" (   ((y+3)-(90<<4) +(1+2-3))  -  ((y+3)-(90<<4) +(1+2-3))  +   ((y+3)-(90<<4) +(1+2-3)) )").toMiniString());
		System.out.println(parser.parse("-2147483648").toMiniString());
		System.out.println(parser.parse("-(-(-\t\t-5 + 16   *x*y) + 1 * z) -(((-11)))").toMiniString());*/

	}
}