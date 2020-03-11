package expression.parser;

import expression.*;
import expression.binary.*;
import expression.exceptions.BracketException;
import expression.exceptions.ConstException;
import expression.exceptions.ParsingException;
import expression.unary.*;

import java.util.Map;
import java.util.function.BiFunction;

public class ExpressionParser<T extends Number> implements Parser<T> {
    public CommonExpression<T> parse(Source source) throws ParsingException {
        return new Parser<T>(source).parse(0);
    }

    public CommonExpression<T> parse(Source source, String mode) throws ParsingException {
        return new Parser<T>(source).parse(0);
    }

    public CommonExpression<T> parse(String string) throws ParsingException {
        return parse(new StringSource(string));
    }

    public CommonExpression<T> parse(String string, String mode) throws ParsingException {
        return parse(new StringSource(string), mode);
    }

    private static class Parser<T extends Number> extends BaseParser {
        private final Map<Oper, BiFunction<CommonExpression<T>,CommonExpression<T>, CommonExpression<T>>> getBinarOper
                = Map.of(
                Oper.ADD, CheckedAdd<T>::new,
                Oper.SUB, CheckedSubtract<T>::new,
                Oper.MUL, CheckedMultiply<T>::new,
                Oper.DIV, CheckedDivide<T>::new
        );

        private int balance = 0;
        private Oper oper;

        public Parser(Source source) {
            super(source);
            nextChar();
            oper = Oper.NAN;
        }

        private CommonExpression<T> parse(int priority) throws ParsingException {
            oper = Oper.NAN;
            skipWhitespace();
            if (priority == 4) {
                return parseUnarOper();
            }
            CommonExpression<T> argLeft;
            argLeft = parse(priority + 1);
            while (true) {
                skipWhitespace();
                if (oper == Oper.NAN) {
                    if (test('\0')) {
                        return argLeft;
                    } else if (isCloseBracket()) {
                        if (balance == 0) {
                            throw new BracketException("no opening bracket: ", getPre(), getPost());
                        }
                        return argLeft;
                    } else if (test('>')) {
                        expect('>');
                        oper = Oper.RSH;
                    } else if (test('<')) {
                        expect('<');
                        oper = Oper.LSH;
                    } else if (test('+')) {
                        oper = Oper.ADD;
                    } else if (test('-')) {
                        oper = Oper.SUB;
                    } else if (test('*')) {
                        if (test('*')) {
                            oper = Oper.POW;
                        } else {
                            oper = Oper.MUL;
                        }
                    } else if (test('/')) {
                        if (test('/')) {
                            oper = Oper.LOG;
                        } else {
                            oper = Oper.DIV;
                        }
                    } else {
                        throw new ParsingException("unexpected binary operation: ",
                                pos, getPre(), getChar(), getPost());
                    }
                }
                if (oper.getPriority() != priority) {
                    return argLeft;
                }
                Oper savedOper = oper;
                CommonExpression<T> argRight;
                oper = Oper.NAN;
                argRight = parse(priority + 1);
                argLeft = getBinarOper.get(savedOper).apply(argLeft, argRight);
            }
        }

        private CommonExpression<T> parseUnarOper() throws ParsingException {
            skipWhitespace();
            if (isDigit()) {
                return parseConst(true);
            }
            if (isVariable()) {
                return parseVar();
            }
            if (test('-')) {
                skipWhitespace();
                if (isDigit()) {
                    return parseConst(false);
                } else {
                    return new CheckedNegate<T>(parseUnarOper());
                }
            }
            if (test('(')) {
                balance++;
                CommonExpression<T> parsed = parse(0);
                skipWhitespace();
                try {
                    expect(')');
                } catch (ParsingException e) {
                    throw new BracketException("no close bracket: ", getPre(), getPost());
                }
                balance--;
                return parsed;
            }
            StringBuilder sb = new StringBuilder();
            throw new ParsingException("expected const, variable or unary operation, but found : '"
                        + getChar() + "'", pos, getPre(), getPost());
        }

        private CommonExpression<T> parseConst(boolean positive) throws ParsingException {
            StringBuilder sb = new StringBuilder();
            if (!positive) {
                sb.append('-');
            }
            do {
                sb.append(getChar());
            } while (isDigit());
            skipWhitespace();
            if (isDigit()) {
                throw new ConstException("Spaces in number: " + sb.toString() + " " + getChar());
            }
//            int val;
//            try {
//                val = Integer.parseInt(sb.toString());
//            } catch (NumberFormatException e) {
//                throw new ConstException("overflow: " + sb.toString());
//            }
            return new Const<T>(getNumber.get(type).apply(sb.toString()));
        }

        private CommonExpression<T> parseVar() {
            String var = String.valueOf(getChar());
            return new Variable<T>(var);
        }
    }
}