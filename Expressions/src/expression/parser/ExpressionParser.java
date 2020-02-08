package expression.parser;

import expression.*;
import expression.exceptions.ParsingException;

public class ExpressionParser implements Parser {
    public CommonExpression parse(Source source) {
        return new ExpressionParser1(source).parse(0);
    }

    public CommonExpression parse(String string) {
        return parse(new StringSource(string));
    }

    private static class ExpressionParser1 extends BaseParser {
        private int balance = 0;
        private Oper oper;

        public ExpressionParser1(Source source) {
            super(source);
            nextChar();
            oper = Oper.NAN;
        }

        private CommonExpression parse(int priority) {
            oper = Oper.NAN;
            skipWhitespace();
            if (priority == 3) {
                return parseUnarOper();
            }
            CommonExpression argLeft;
            try {
                argLeft = parse(priority + 1);
            } catch (ParsingException e) {
                throw new ParsingException("no first argument");
            }
            while (true) {
                skipWhitespace();
                if (oper == Oper.NAN) {
                    if (test('\0')) {
                        return argLeft;
                    } else if (testCloseBracket()) {
                        if (balance == 0) {
                            throw new ParsingException("no opening bracket )");
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
                    } else if (test('>')) {
                        expect('>');
                        oper = Oper.RSH;
                    } else if (test('<')) {
                        expect('<');
                        oper = Oper.LSH;
                    } else {
                        throw new ParsingException("unexpected operation");
                    }
                }
                if (oper.getPriority() != priority) {
                    return argLeft;
                }
                Oper savedOper = oper;

                CommonExpression argRight;
                oper = Oper.NAN;
                try {
                    argRight = parse(priority + 1);
                } catch (ParsingException e) {
                    throw new ParsingException("no second argument");
                }
                argLeft = parseBinarOper(argLeft, savedOper, argRight);
            }
        }

        private CommonExpression parseBinarOper(CommonExpression argLeft, Oper tempOper, CommonExpression argRight) {
            switch (tempOper) {
                case ADD:
                    return new Add(argLeft, argRight);
                case SUB:
                    return new Subtract(argLeft, argRight);
                case MUL:
                    return new Multiply(argLeft, argRight);
                case DIV:
                    return new Divide(argLeft, argRight);
                case RSH:
                    return new RightShift(argLeft, argRight);
                case LSH:
                    return new LeftShift(argLeft, argRight);
                default:
                    throw new ParsingException("unsupported operation");
            }
        }

        private CommonExpression parseUnarOper() {
            if (testDigit()) {
                return parseConst(true);
            }
            if (testVariable()) {
                return parseVar();
            }
            if (test('-')) {
                skipWhitespace();
                if (testDigit()) {
                    return parseConst(false);
                } else {
                    return new Negate(parse(3));
                }
            }
            if (test('a')) {
                expect("bs");
                return new Abs(parse(3));
            } else if (test('s')) {
                    expect("quare");
                    return new Square(parse(3));
            } else if (test('r')) {
                    expect("everse");
                    return new Reverse(parse(3));
            } else if (test('d')) {
                    expect("igits");
                    return new Digits(parse(3));
            } else if (test('(')) {
                    balance++;
                    CommonExpression parsed = parse(0);
                    skipWhitespace();
                    expect(')');
                    balance--;
                    return parsed;
            } else {
                throw new ParsingException("no argument");
            }
        }

        private CommonExpression parseConst(boolean positive) {
            StringBuilder sb = new StringBuilder();
            if (!positive) {
                sb.append('-');
            }
            do {
                sb.append(getChar());
            } while (testDigit());
            int val = Integer.parseInt(sb.toString());
            return new Const(val);
        }

        private CommonExpression parseVar() {
            String var = String.valueOf(getChar());
            return new Variable(var);
        }
    }
}