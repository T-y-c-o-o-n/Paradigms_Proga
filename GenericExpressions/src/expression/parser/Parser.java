package expression.parser;

import expression.CommonExpression;
import expression.generic.Computer;
import expression.exceptions.ParsingException;

public interface Parser<T> {
    CommonExpression<T> parse(String expression, Computer<T> example) throws ParsingException;
}