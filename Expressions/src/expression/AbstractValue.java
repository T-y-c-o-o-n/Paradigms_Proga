package expression;

import java.util.EnumSet;
import java.util.List;

public abstract class AbstractValue implements CommonExpression {
    private Oper me;

    public AbstractValue(Oper me) {
        this.me = me;
    }

    public int getPriority() {
        return me.getPriority();
    }
}