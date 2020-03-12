package expression.parser;

import expression.*;
import expression.binary.*;
import expression.exceptions.BracketException;
import expression.exceptions.ConstException;
import expression.exceptions.ParsingException;
import expression.generic.Computer;
import expression.generic.IntComputer;
import expression.unary.*;

public class ExpressionParser<T> implements Parser<T> {
    public CommonExpression<T> parse(Source source, Computer<T> example) throws ParsingException {
        return new Parser<>(source, example).parse(0);
    }

    public CommonExpression<T> parse(Source source) throws ParsingException {
        return parse(source, (Computer<T>) new IntComputer());
    }

    public CommonExpression<T> parse(String string) throws ParsingException {
        return parse(new StringSource(string));
    }

    public CommonExpression<T> parse(String string, Computer<T> example) throws ParsingException {
        return parse(new StringSource(string), example);
    }

    private static class Parser<T> extends BaseParser {
        private final static int MAX_PRIORITY = 3;
        private int balance;
        private Oper oper;
        private final Computer<T> example;

        public Parser(Source source, Computer<T> example) {
            super(source);
            balance = 0;
            oper = Oper.NAN;
            this.example = example;
            nextChar();
        }

        private CommonExpression<T> parse(int priority) throws ParsingException {
            oper = Oper.NAN;
            skipWhitespace();
            if (priority == MAX_PRIORITY) {
                return parseUnaryOper();
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
                    } else if (test('+')) {
                        oper = Oper.ADD;
                    } else if (test('-')) {
                        oper = Oper.SUB;
                    } else if (test('*')) {
                        oper = Oper.MUL;
                    } else if (test('/')) {
                        oper = Oper.DIV;
                    } else if (test('m')) {
                        if (test('i')) {
                            expect('n');
                            oper = Oper.MIN;
                        } else {
                            expect("ax");
                            oper = Oper.MAX;
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
                argLeft = makeBinaryOper(savedOper, argLeft, argRight);
            }
        }

        private CommonExpression<T> makeBinaryOper(Oper me, CommonExpression<T> arg1, CommonExpression<T> arg2) {
            switch (me) {
                case MIN: return new Min<>(arg1, arg2, example);
                case MAX: return new Max<>(arg1, arg2, example);
                case ADD: return new Add<>(arg1, arg2, example);
                case SUB: return new Subtract<>(arg1, arg2, example);
                case MUL: return new Multiply<>(arg1, arg2, example);
                case DIV: return new Divide<>(arg1, arg2, example);
                default: return null;
            }
        }

        private CommonExpression<T> parseUnaryOper() throws ParsingException {
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
            return new Const<>(example.parseVal(sb.toString()));
        }

        private CommonExpression<T> parseVar() {
            String var = String.valueOf(getChar());
            return new Variable<>(var);
        }
    }
}