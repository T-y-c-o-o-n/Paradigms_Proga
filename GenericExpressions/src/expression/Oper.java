package expression;

import java.util.Map;
import java.util.function.Function;

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