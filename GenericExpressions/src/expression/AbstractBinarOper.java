package expression;

import expression.exceptions.OverflowException;

import java.util.EnumSet;

public abstract class AbstractBinarOper<T extends Number> implements CommonExpression<T> {
	protected CommonExpression<T> arg1;
	protected CommonExpression<T> arg2;
	private final Oper me;

	public AbstractBinarOper(CommonExpression<T> arg1, CommonExpression<T> arg2, Oper me) {
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.me = me;
	}

	public CommonExpression<T> getArg1() {
		return arg1;
	}

	public CommonExpression<T> getArg2() {
		return arg2;
	}

	public Oper getOper() {
		return me;
	}

	public abstract T calculate(T a, T b);

	public T evaluate(int x, int y, int z) {
		return calculate(arg1.evaluate(x, y, z), arg2.evaluate(x, y, z));
	}

	protected void overflow(T a, T b) {
		throw new OverflowException(a + me.toString() + b);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		sb.append(arg1.toString());
		sb.append(me);
		sb.append(arg2.toString());
		sb.append(')');
		return sb.toString();
	}

	protected String toMiniString(EnumSet<Oper> firstArgsToAllow, EnumSet<Oper> secondArgsToAllow) {
		return arg1.checkString(firstArgsToAllow) + me +
				arg2.checkString(secondArgsToAllow);
	}

	public String checkString(EnumSet<Oper> allowed) {
		if (allowed.contains(me)) {
			return toMiniString();
		}
		return '(' + toMiniString() + ')';
	}

	public boolean equals(Object object) {
		if (object != null && object.getClass() == getClass()) {
			AbstractBinarOper<T> exp = (AbstractBinarOper)object;
			return me.equals(exp.getOper()) && arg1.equals(exp.getArg1()) && arg2.equals(exp.getArg2());
		} else {
			return false;
		}
	}

	public int hashCode() {
		return toString().hashCode();
	}
}