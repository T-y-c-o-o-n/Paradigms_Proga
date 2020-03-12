package expression;

public enum Oper {
    MIN(" min ", 0),
    MAX(" max ", 0),
    ADD(" + ", 1),
    SUB(" - ", 1),
    MUL(" * ", 2),
    DIV(" / ", 2),
    CNT("count ", 3),
    NEG("- ", 3),
    NAN(" NaN ", -1);

    private final String view;
    private final int priority;

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