package expression;

import expression.exceptions.CalculationException;
import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class UintComputer implements Computer<Integer> {
    public Integer min(Integer a, Integer b) {
        return Integer.min(a, b);
    }

    public Integer max(Integer a, Integer b) {
        return Integer.max(a, b);
    }

    public Integer add(Integer a, Integer b) {
        /*long longRes = (long) a + (long) b;
        if (longRes != (long)(a + b)) {
            throw new OverflowException(a + " + " + b);
        }*/
        return a + b;
    }

    public Integer sub(Integer a, Integer b) {
        /*long longRes = (long) a - (long) b;
        if (longRes != (long)(a - b)) {
            throw new OverflowException(a + " - " + b);
        }*/
        return a - b;
    }

    public Integer mul(Integer a, Integer b) {
        /*long longRes = (long) a * (long) b;
        if (longRes != (long)(a * b)) {
            throw new OverflowException(a + " * " + b);
        }*/
        return a * b;
    }

    public Integer div(Integer a, Integer b) {
        if (b == 0) {
            throw new DivisionByZeroException(a.toString() + " / " + b.toString());
        }/*
        long longRes = (long) a / (long) b;
        if (longRes != (long)(a / b)) {
            throw new OverflowException(a + " / " + b);
        }*/
        return a / b;
    }

    public Integer neg(Integer a) {
        /*if (a == Integer.MIN_VALUE) {
            throw new OverflowException("- " + a.toString());
        }*/
        return -a;
    }

    public Integer cnt(Integer a) {
        return Integer.bitCount(a);
    }

    public Integer parseVal(String str) { return Integer.parseInt(str); }
}

