package expression;

public class DoubleComputer implements Computer<Double> {
    public Double add(Double a, Double b) { return a + b; }

    public Double sub(Double a, Double b) { return a - b; }

    public Double mul(Double a, Double b) { return a * b; }

    public Double div(Double a, Double b) { return a / b; }

    public Double neg(Double a) { return -a; }

    public Double parseVal(String str) { return Double.parseDouble(str); }
}
