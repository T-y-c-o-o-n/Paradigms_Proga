package expression.exceptions;

import java.lang.Exception;

public class CalculationException extends RuntimeException {
    public CalculationException(String message) {
        super(message);
    }
}
