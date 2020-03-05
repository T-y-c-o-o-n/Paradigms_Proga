package expression.exceptions;

public class BracketException extends ParsingException {
    public BracketException(String message) {
        super(message);
    }

    public BracketException(String message, int pos) {
        super(message, pos);
    }

    public BracketException(String message, int pos, String pre, String post) {
        super(message, pos, pre, post);
    }
}
