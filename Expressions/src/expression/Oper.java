package expression;

import java.util.Map;

public enum Oper {
    LSH {
        public String toString() {
            return " << ";
        }
        public int getPriority() {
            return 0;
        }
    },
    RSH {
        public String toString() {
            return " >> ";
        }
        public int getPriority() {
            return 0;
        }
    },
    ADD {
        public String toString() {
            return " + ";
        }
        public int getPriority() {
            return 1;
        }
    },
    SUB {
        public String toString() {
            return " - ";
        }
        public int getPriority() {
            return 1;
        }
    },
    MUL {
        public String toString() {
            return " * ";
        }
        public int getPriority() {
            return 2;
        }
    },
    DIV {
        public String toString() {
            return " / ";
        }
        public int getPriority() {
            return 2;
        }
    },
    NEG {
        public String toString() {
            return " -";
        }
        public int getPriority() {
            return 3;
        }
    },
    ABS {
        public String toString() {
            return "abs ";
        }
        public int getPriority() {
            return 3;
        }
    },
    SQR {
        public String toString() {
            return "square ";
        }
        public int getPriority() {
            return 3;
        }
    },
    DIG {
        public String toString() {
            return "digits ";
        }
        public int getPriority() {
            return 3;
        }
    },
    REV {
        public String toString() {
            return "reverse ";
        }
        public int getPriority() {
            return 3;
        }
    },
    VAR {
        public String toString() {
            return " var ";
        }
        public int getPriority() {
            return 3;
        }
    },
    CONST {
        public String toString() {
            return " const ";
        }
        public int getPriority() {
            return 3;
        }
    },
    NAN {
        public String toString() {
            return " NaN ";
        }
        public int getPriority() {
            return -1;
        }
    };

    
    public abstract int getPriority();
}