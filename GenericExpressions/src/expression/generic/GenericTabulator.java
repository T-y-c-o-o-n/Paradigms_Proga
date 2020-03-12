package expression.generic;

import expression.*;
import expression.exceptions.CalculationException;
import expression.exceptions.ParsingException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;

import java.math.BigInteger;
import java.util.Map;
import java.util.function.Function;

public class GenericTabulator implements Tabulator {
    private static Map<String, Parser> parsers = Map.of(
            "i", new ExpressionParser<Integer>(),
            "bi", new ExpressionParser<BigInteger>(),
            "d", new ExpressionParser<Double>(),
            "l", new ExpressionParser<Long>(),
            "s", new ExpressionParser<Short>(),
            "u", new ExpressionParser<Integer>()
    );

    private static Map<String, Computer> computers = Map.of(
            "i", new IntComputer(),
            "bi", new BigIntComputer(),
            "d", new DoubleComputer(),
            "l", new LongComputer(),
            "s", new ShortComputer(),
            "u", new UintComputer()
    );

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
    throws Exception {
        return getTable(parsers.get(mode), computers.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    public <T extends Number> Object[][][] getTable(Parser<T> parser, Computer<T> computer, String expression,
                                                    int x1, int x2, int y1, int y2, int z1, int z2)
            throws Exception {
        CommonExpression<T> f = parser.parse(expression, computer);  // throws ParsingException;
        Object[][][] result = new Object[x2-x1+1][y2-y1+1][z2-z1+1];
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        result[x - x1][y - y1][z - z1] = f.evaluate(
                                computer.parseVal(Integer.toString(x)),
                                computer.parseVal(Integer.toString(y)),
                                computer.parseVal(Integer.toString(z)));
                    } catch (CalculationException e) {
                        result[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }
        return result;
    }
}
