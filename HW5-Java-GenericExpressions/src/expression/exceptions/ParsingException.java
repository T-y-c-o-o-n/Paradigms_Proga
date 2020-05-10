package expression.exceptions;

public class ParsingException extends Exception {
    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, int pos) {
        super(message + " on position " + pos);
    }

    public ParsingException(String message, String pre, String post) {
        super(message + ":   \".....   " + pre + " HERE " + post + "   .....\"");
    }

    public ParsingException(String message, int pos, String pre, char ch, String post) {
        super(message + " on position " + pos + ":    " + pre + " error -------> " + ch + " <------- error " + post);
    }
}
