package expression;

import expression.exceptions.CalculationException;
import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;

public class BigIntComputer implements Computer<BigInteger> {
    public BigInteger min(BigInteger a, BigInteger b) { return a.min(b); }

    public BigInteger max(BigInteger a, BigInteger b) { return a.max(b); }

    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    public BigInteger sub(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    public BigInteger mul(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    public BigInteger div(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException(a + " / " + b);
        }
        return a.divide(b);
    }

    public BigInteger neg(BigInteger a) {
        return a.negate();
    }

    public BigInteger cnt(BigInteger a) {
        return new BigInteger(Integer.toString(a.bitCount()));
    }

    public BigInteger parseVal(String str) {
        return new BigInteger(str);
    }
}
