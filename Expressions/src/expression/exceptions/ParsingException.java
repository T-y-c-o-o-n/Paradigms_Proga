package expression.exceptions;

public class ParsingException extends Exception {
    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, int pos) {
        super(message + " on position " + pos);
    }

    public ParsingException(String message, int pos, String pre, String post) {
        super(message + " on position " + pos + ":   \".....   " + pre + " HERE " + post + "   .....\"");
    }
}
