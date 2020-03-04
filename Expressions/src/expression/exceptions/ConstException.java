package expression.exceptions;

public class ConstException extends ParsingException {
    public ConstException(String message) {
        super(message);
    }

    public ConstException(String message, int pos) {
        super(message, pos);
    }
}
