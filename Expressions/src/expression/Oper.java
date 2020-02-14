package expression;

import java.util.Map;

import expression.exceptions.ParsingException;
import expression.unary.*;

public enum Oper {
    LSH(" << ", 0),
    RSH(" >> ", 0),
    ADD(" + ", 1),
    SUB(" - ", 1),
    MUL(" * ", 2),
    DIV(" / ", 2),
    POW(" ** ", 3),
    LOG(" // ", 3),
    POW2("pow2 ", 4),
    LOG2("log2 ", 4),
    NEG("- ", 4),
    ABS("abs ", 4),
    SQR("square ", 4),
    DIG("digits ", 4),
    REV("reverse ", 4),
    NAN(" NaN ", -1);

    public static Map<String, Oper> getUnarOper = Map.of(
            "abs", Oper.ABS,
            "square", Oper.SQR,
            "digits", Oper.DIG,
            "reverse", Oper.REV,
            "log2", Oper.LOG2,
            "pow2", Oper.POW2
    );
    public static CommonExpression getUnarExp(String token, CommonExpression arg) {
        switch (token) {
            case "abs":
                return new Abs(arg);
            case "square":
                return new Square(arg);
            case "digits":
                return new Digits(arg);
            case "reverse":
                return new Reverse(arg);
            case "log2":
                return new CheckedLog2(arg);
            case "pow2":
                return new CheckedPow2(arg);
        }
        throw new ParsingException("invalid unary operation");
    }

    private String view;
    private int priority;

    Oper(String view, int priority) {
        this.view = view;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return view;
    }
}