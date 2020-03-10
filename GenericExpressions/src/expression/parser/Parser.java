package expression.parser;

import expression.CommonExpression;
import expression.TripleExpression;
import expression.exceptions.ParsingException;

public interface Parser<T extends Number> {
    CommonExpression<T> parse(String expression) throws ParsingException;
    CommonExpression<T> parse(String expression, String mode) throws ParsingException;
}