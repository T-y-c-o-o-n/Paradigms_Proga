package expression.parser;

public class StringSource implements Source {
    private final String source;
    private int pnt;
    public StringSource(String source) {
        this.source = source;
        pnt = 0;
    }
    public char nextChar() {
        return pnt < source.length() ? source.charAt(pnt++) : '\0';
    }
}
