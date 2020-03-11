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
            "d", new ExpressionParser<Double>()
    );

    private static Map<String, Computer> computers = Map.of(
            "i", new IntComputer(),
            "bi", new BigIntComputer(),
            "d", new DoubleComputer()
    );

    private static Map<String, Function<String, ? extends Number>> value = Map.of(
            "i", Integer::parseInt,
            "bi", BigInteger::new,
            "d", Double::parseDouble
    );

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
            throws ParsingException {
        Parser parser = parsers.get(mode);
        Computer computer = computers.get(mode);
        CommonExpression f = parser.parse(expression, computer);  // throws ParsingException;
        Object[][][] result = new Object[x2-x1+1][y2-y1+1][z2-z1+1];
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        result[x - x1][y - y1][z - z1] = f.evaluate(
                                value.get(mode).apply(Integer.toString(x)),
                                value.get(mode).apply(Integer.toString(y)),
                                value.get(mode).apply(Integer.toString(z)));  // throws CalculationException
                    } catch (CalculationException e) {
                        result[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }
        return result;
    }
}
