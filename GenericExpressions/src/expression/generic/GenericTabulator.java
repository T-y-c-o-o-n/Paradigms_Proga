package expression.generic;

import expression.*;
import expression.exceptions.CalculationException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private static class Pair<T> {
        private final Parser<T> first;
        private final Computer<T> second;

        private Pair(Parser<T> parser, Computer<T> computer) {
            first = parser;
            second = computer;
        }
    }

    private static final Map<String, Pair<?>> parsersAndComputers = Map.of(
            "i", new Pair<>(new ExpressionParser<>(), new IntComputer()),
            "bi", new Pair<>(new ExpressionParser<>(), new BigIntComputer()),
            "d", new Pair<>(new ExpressionParser<>(), new DoubleComputer()),
            "l", new Pair<>(new ExpressionParser<>(), new LongComputer()),
            "s", new Pair<>(new ExpressionParser<>(), new ShortComputer()),
            "u", new Pair<>(new ExpressionParser<>(), new UintComputer())
    );

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
    throws Exception {
        Pair pair = parsersAndComputers.get(mode);
        Parser parser = pair.first;
        Computer computer = pair.second;
        return getTable(parser, computer,
                expression, x1, x2, y1, y2, z1, z2);
    }

    public <T> Object[][][] getTable(Parser<T> parser, Computer<T> computer,
                                     String expression, int x1, int x2, int y1, int y2, int z1, int z2)
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
