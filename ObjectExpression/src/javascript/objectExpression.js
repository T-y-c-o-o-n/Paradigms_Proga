"use strict";

/*
evaluate
toString
diff
simplify
 */
let zero = new Const(0);
let one = new Const(1);

//  Флекс со значениями
const initValueProt = function(Val, fun, toStr, funDiff) {
    Val.prototype.evaluate = fun;
    Val.prototype.toString = toStr;
    Val.prototype.diff = funDiff;
};

function Const(val) {
    this.val = val;
}
function Variable(view) {
    this.view = view;
}

initValueProt(Const,
    function() { return this.val; },
function() { return this.val.toString(); },
function(par) {return zero}
);
initValueProt(Variable,
function(x, y, z) { return this.view === "x" ? x : this.view === "y" ? y : z; },
function() { return this.view; },
function(par) { return par === this.view ? one : zero; }
);

//  Теперь флекс с операторами
function Operation(...args) {
    this.args = args;
}
Operation.prototype.evaluate = function(x, y, z) {
    return this.calc(...this.args.map(arg => arg.evaluate(x, y, z)));
};
Operation.prototype.toString = function() {
    return this.args.reduce((a, b) => a.toString() + " " + b.toString()) + this.op;
};
const initOperationPrototype = function(Oper, view, fun, funDiff) {
    Oper.prototype = Object.create(Operation.prototype);
    Oper.prototype.op = view;
    Oper.prototype.calc = fun;
    Oper.prototype.diff = funDiff;
};

function Add(arg1, arg2) {
    Operation.call(this, arg1, arg2);
}
function Subtract(arg1, arg2) {
    Operation.call(this, arg1, arg2);
}
function Multiply(arg1, arg2) {
    Operation.call(this, arg1, arg2);
}
function Divide(arg1, arg2) {
    Operation.call(this, arg1, arg2);
}
function Negate(arg) {
    Operation.call(this, arg);
}
function Ln(arg) {
    Operation.call(this, arg);
}
function Log(arg1, arg2) {
    Operation.call(this, arg1, arg2);
}
function Power(arg1, arg2) {
    Operation.call(this, arg1, arg2);
}

initOperationPrototype(Add, " +", (a, b) => a + b,
    function(par) { return new Add(this.args[0].diff(par), this.args[1].diff(par)); }
);
initOperationPrototype(Subtract," -", (a, b) => a - b,
    function(par) {
    return new Subtract(this.args[0].diff(par), this.args[1].diff(par));
}
);
initOperationPrototype(Multiply," *",(a, b) => a * b,
    function(par) {
    return new Add(
        new Multiply(this.args[0].diff(par), this.args[1]),
        new Multiply(this.args[0], this.args[1].diff(par)));
}
);
initOperationPrototype(Divide," /",(a, b) => a / b,
    function(par) {
    return new Divide(
        new Subtract(new Multiply(this.args[0].diff(par), this.args[1]), new Multiply(this.args[0], this.args[1].diff(par))),
        new Multiply(this.args[1], this.args[1])
    );
}
);
initOperationPrototype(Negate," negate", a => -a,
    function(par) {
    return new Negate(this.args[0].diff(par));
}
);
initOperationPrototype(Ln," ln", a => Math.log(Math.abs(a)),
    function(par) {
    return new Divide(this.args[0].diff(par), this.args[0]);
}
);
initOperationPrototype(Log," log",(a, b) => Math.log(Math.abs(b)) / Math.log(Math.abs(a)),
    function(par) {
    return new Divide(new Ln(this.args[1]), new Ln(this.args[0])).diff(par);
}
);
initOperationPrototype(Power," pow", Math.pow,
    function(par) {
    return new Multiply(this, new Add(
        new Multiply(this.args[1].diff(par), new Ln(this.args[0])),
        new Multiply(this.args[1], new Divide(this.args[0].diff(par), this.args[0]))
    ));
}
);

//  а теперь парсер
const parseVariable = {
    "x": new Variable("x"),
    "y": new Variable("y"),
    "z": new Variable("z")
};

const parseOperation = {
    "+": [Add, 2],
    "-": [Subtract, 2],
    "*": [Multiply, 2],
    "/": [Divide, 2],
    "negate": [Negate, 1],
    "log": [Log, 2],
    "pow": [Power, 2]
};

const parse = function(source) {
    let stack = [];
    for (let token of source.split(" ").filter(word => word.length > 0)) {
        if (token in parseOperation) {
            let parsed = parseOperation[token];
            stack.push(new parsed[0] (...stack.splice(-parsed[1])));
        } else if (token in parseVariable) {
            stack.push(parseVariable[token]);
        } else {
            stack.push(new Const(parseInt(token)));
        }
    }
    return stack.pop();
};
