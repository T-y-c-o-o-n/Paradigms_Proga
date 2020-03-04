package expression.parser;

import expression.*;
import expression.binary.*;
import expression.exceptions.BracketException;
import expression.exceptions.ConstException;
import expression.exceptions.ParsingException;
import expression.unary.*;

import java.util.Map;
import java.util.function.Function;

public class ExpressionParser implements Parser {
    public static Map<String, Function<CommonExpression, CommonExpression>> getUnarOper = Map.of(
            "abs", Abs::new,
            "square", Square::new,
            "digits", Digits::new,
            "reverse", Reverse::new,
            "log2", CheckedLog2::new,
            "pow2", CheckedPow2::new
    );

    public CommonExpression parse(Source source) throws ParsingException {
        return new ExpressionParser1(source).parse(0);
    }

    public CommonExpression parse(String string) throws ParsingException {
        return parse(new StringSource(string));
    }

    private static class ExpressionParser1 extends BaseParser {
        private int balance = 0;
        public Oper oper;

        public ExpressionParser1(Source source) {
            super(source);
            nextChar();
            oper = Oper.NAN;
        }

        private CommonExpression parse(int priority) throws ParsingException {
            oper = Oper.NAN;
            skipWhitespace();
            if (priority == 4) {
                return parseUnarOper();
            }
            CommonExpression argLeft;
            //try {
                argLeft = parse(priority + 1);
            //} catch (ParsingException e) {
            //    throw new ParsingException("no first argument");
            //}
            while (true) {
                skipWhitespace();
                if (oper == Oper.NAN) {
                    if (test('\0')) {
                        return argLeft;
                    } else if (isCloseBracket()) {
                        if (balance == 0) {
                            throw new BracketException("no opening bracket", pos, getPre(), getPost());
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
                        throw new ParsingException("unexpected binary operation, but found: " + getChar(),
                                pos, getPre(), getPost());
                    }
                }
                if (oper.getPriority() != priority) {
                    return argLeft;
                }
                Oper savedOper = oper;
                CommonExpression argRight;
                oper = Oper.NAN;
                //try {
                    argRight = parse(priority + 1);
                //} catch (ParsingException e) {
                //    throw new ParsingException("no second argument");
                //}
                argLeft = parseBinarOper(argLeft, savedOper, argRight);
            }
        }

        private CommonExpression parseBinarOper(CommonExpression argLeft, Oper tempOper,
                                                CommonExpression argRight) throws ParsingException {
            switch (tempOper) {
                case RSH:
                    return new RightShift(argLeft, argRight);
                case LSH:
                    return new LeftShift(argLeft, argRight);
                case ADD:
                    return new CheckedAdd(argLeft, argRight);
                case SUB:
                    return new CheckedSubtract(argLeft, argRight);
                case MUL:
                    return new CheckedMultiply(argLeft, argRight);
                case DIV:
                    return new CheckedDivide(argLeft, argRight);
                case POW:
                    return new CheckedPow(argLeft, argRight);
                case LOG:
                    return new CheckedLog(argLeft, argRight);
                default:
                    throw new ParsingException("unsupported operation", pos, getPre(), getPost());
            }
        }

        private CommonExpression parseUnarOper() throws ParsingException {
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
                    return new CheckedNegate(parseUnarOper());
                }
            }
            if (test('(')) {
                balance++;
                CommonExpression parsed = parse(0);
                skipWhitespace();
                try {
                    expect(')');
                } catch (ParsingException e) {
                    throw new BracketException("no close bracket", pos, getPre(), getPost());
                }
                balance--;
                return parsed;
            }
            StringBuilder sb = new StringBuilder();
            while (isLetter() || isDigit()) {
                sb.append(getChar());
            }
            skipWhitespace();

            Function<CommonExpression, CommonExpression> constructor = getUnarOper.get(sb.toString());
            if (constructor != null) {
                return constructor.apply(parse(0));
            }
            throw new ParsingException("expected const, variable or unary operation", pos, getPre(), getPost());
        }

        private CommonExpression parseConst(boolean positive) throws ParsingException {
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
            int val;
            try {
                val = Integer.parseInt(sb.toString());
            } catch (NumberFormatException e) {
                throw new ConstException("overflow: " + sb.toString());
            }
            return new Const(val);
        }

        private CommonExpression parseVar() {
            String var = String.valueOf(getChar());
            return new Variable(var);
        }
    }
}