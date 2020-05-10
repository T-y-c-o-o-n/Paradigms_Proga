package expression.parser;

import expression.*;
import expression.binary.*;
import expression.exceptions.BracketException;
import expression.exceptions.ConstException;
import expression.exceptions.ParsingException;
import expression.generic.Computer;
import expression.unary.*;

import java.util.Map;

public class ExpressionParser<T extends Number> {
    public Expression<T> parse(Source source, Computer<T> example) throws ParsingException {
        return new InnerParser<>(source, example).parse(0);
    }

    public Expression<T> parse(String string, Computer<T> example) throws ParsingException {
        return parse(new StringSource(string), example);
    }

    private static class InnerParser<T extends Number> extends BaseParser {
        private final static int MAX_PRIORITY = 3;
        private int balance;
        private Oper oper;
        private final Computer<T> example;

        private static Map<Character, Oper> charToOper = Map.of(
                '+', Oper.ADD,
                '-', Oper.SUB,
                '*', Oper.MUL,
                '/', Oper.DIV
        );

        private static Map<String, Oper> stringToOper = Map.of(
                "min", Oper.MIN,
                "max", Oper.MAX
        );

        public InnerParser(Source source, Computer<T> example) {
            super(source);
            balance = 0;
            oper = Oper.NAN;
            this.example = example;
            nextChar();
        }

        private Expression<T> parse(int priority) throws ParsingException {
            oper = Oper.NAN;
            skipWhitespace();
            if (priority == MAX_PRIORITY) {
                return parseUnaryOper();
            }
            Expression<T> argLeft;
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
                    } else {
                        boolean defined = false;
                        for (Map.Entry<Character, Oper> abc : charToOper.entrySet()) {
                            if (test(abc.getKey())) {
                                oper = abc.getValue();
                                defined = true;
                                break;
                            }
                        }
                        if (!defined) {
                            for (Map.Entry<String, Oper> abc : stringToOper.entrySet()) {
                                if (test(abc.getKey())) {
                                    oper = abc.getValue();
                                    defined = true;
                                    break;
                                }
                            }
                        }
                        if (!defined) {
                            throw new ParsingException("unexpected binary operation: ",
                                    pos, getPre(), getChar(), getPost());
                        }
                    }
                }

                if (oper.getPriority() != priority) {
                    return argLeft;
                }
                Oper savedOper = oper;
                Expression<T> argRight;
                oper = Oper.NAN;
                argRight = parse(priority + 1);
                argLeft = makeBinaryOper(savedOper, argLeft, argRight);
            }
        }

        private Expression<T> makeBinaryOper(Oper me, Expression<T> arg1, Expression<T> arg2) {
            switch (me) {
                case MIN:
                    return new Min<>(arg1, arg2, example);
                case MAX:
                    return new Max<>(arg1, arg2, example);
                case ADD:
                    return new Add<>(arg1, arg2, example);
                case SUB:
                    return new Subtract<>(arg1, arg2, example);
                case MUL:
                    return new Multiply<>(arg1, arg2, example);
                case DIV:
                    return new Divide<>(arg1, arg2, example);
                default:
                    return null;
            }
        }

        private Expression<T> parseUnaryOper() throws ParsingException {
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
                    return new Negate<>(parseUnaryOper(), example);
                }
            }
            if (test('c')) {
                expect("ount");
                return new Count<>(parseUnaryOper(), example);
            }
            if (test('(')) {
                balance++;
                Expression<T> parsed = parse(0);
                skipWhitespace();
                try {
                    expect(')');
                } catch (ParsingException e) {
                    throw new BracketException("no close bracket: ", getPre(), getPost());
                }
                balance--;
                return parsed;
            }
            String pre = getPre();
            char ch = getChar();
            throw new ParsingException("expected const, variable or unary operation, but found : ", pos, pre, ch, getPost());
        }

        private Expression<T> parseConst(boolean positive) throws ParsingException {
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
            return new Const<>(example.parseVal(sb.toString()));
        }

        private Expression<T> parseVar() {
            String var = String.valueOf(getChar());
            return new Variable<>(var);
        }
    }
}