package expression.exceptions;

public class DivisionByZeroException extends CalculationException {
    public DivisionByZeroException(String message) {
        super(message);
    }
}
