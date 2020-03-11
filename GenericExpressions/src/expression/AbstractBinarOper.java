package expression;

import expression.exceptions.OverflowException;

import java.util.EnumSet;

public abstract class AbstractBinarOper<T> implements CommonExpression<T> {
	private final Oper me;
	protected final CommonExpression<T> arg1;
	protected final CommonExpression<T> arg2;
	protected final Computer<T> computer;

	public AbstractBinarOper(Oper me, CommonExpression<T> arg1, CommonExpression<T> arg2, Computer<T> computer) {
		this.me = me;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.computer = computer;
	}

	public Oper getOper() {
		return me;
	}

	public abstract T calculate(T a, T b);

	public T evaluate(T x, T y, T z) {
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
}