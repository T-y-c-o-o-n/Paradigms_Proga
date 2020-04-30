"use strict";

/*
evaluate
toString
diff
simplify
 */

// Общий дед для всех
const makeNewExpressionType = function(evaluate, toString, diff) {
    function Expression(...args) {
        this.args = args;
    }

    Expression.prototype = Object.create(Object);
    Expression.prototype.constructor = Expression;
    Expression.prototype.evaluate = evaluate;
    Expression.prototype.toString = toString;
    Expression.prototype.diff = diff;

    return Expression;
};
// :NOTE: this is not required by JS. If it's removed code still be compiling but logic lost.
// User can forget to call this method
// fixed
const Const = makeNewExpressionType(function(x, y, z) { return this.args[0] },
    function() { return this.args[0].toString() },
    (par) => zero
);
// :NOTE: why it's not const?
// fixed
const zero = new Const(0);
const one = new Const(1);
const Variable = makeNewExpressionType(
    function(x, y, z) { return this.args[0] === "x" ? x : this.args[0] === "y" ? y : z },
    function() { return this.args[0] },
    function(par) { return this.args[0] === par ? one : zero }
);
const AbstractOperation = makeNewExpressionType(
    function(...vars) { return this.evaluateImpl( ...this.args.map(arg => arg.evaluate(...vars))) },
    function() { return this.args.join(" ") + " " + this.op },
    function(par) { return this.diffImpl(...this.args, par) }
);
// Специально для каждой операции
const makeNewOperation = function(evaluateImpl, op, diffImpl) {
    function Operation(...args) { AbstractOperation.call(this, ...args); }

    Operation.prototype = Object.create(AbstractOperation.prototype);
    Operation.prototype.constructor = Operation;
    Operation.prototype.evaluateImpl = evaluateImpl;
    Operation.prototype.op = op;
    Operation.prototype.diffImpl = diffImpl;

    return Operation
};

const Add = makeNewOperation((a, b) => a + b,"+",
    (a, b, par) => new Add(a.diff(par), b.diff(par)));
const Subtract = makeNewOperation((a, b) => a - b,"-",
    (a, b, par) => new Subtract(a.diff(par), b.diff(par))
);
const Multiply = makeNewOperation((a, b) => a * b,"*",
    (a, b, par) => new Add(
        new Multiply(a.diff(par), b),
        new Multiply(a, b.diff(par)))
);
const Divide = makeNewOperation((a, b) => a / b,"/",
    (a, b, par) => new Divide(
            new Subtract(new Multiply(a.diff(par), b), new Multiply(a, b.diff(par))),
            new Multiply(b, b)
        )
);
const Negate = makeNewOperation(a => -a,"negate",
    (a, par) => new Negate(a.diff(par))
);
const Ln = makeNewOperation(a => Math.log(Math.abs(a)),"ln",
    (a, par) => new Divide(a.diff(par), a)
);
const Log = makeNewOperation((a, b) => Math.log(Math.abs(b)) / Math.log(Math.abs(a)),"log",
    (a, b, par) => new Divide(new Ln(b), new Ln(a)).diff(par)
);
const Power = makeNewOperation(Math.pow,"pow",
    function(a, b, par) { return new Multiply(this, new Add(
        new Multiply(b.diff(par), new Ln(a)),
        new Multiply(b, new Divide(a.diff(par), a))
    )) }
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

let a = new Add(new Const(90), new Variable("y"));

console.log(a.toString());

// let test = new Add(new Variable("z"), new Const(5));
// let test = new Variable("z");

/*for (let part in test) {
    console.log(part);
}*/
/*
console.log(test);

console.log(test instanceof Add);
console.log(test instanceof AbstractOperation);
console.log(test instanceof makeNewOperation);
console.log(test instanceof initExpressionPrototype);

console.log(test.toString());
console.log(test.evaluate(1, 2, 3));
console.log(test.diff("z").toString());
*/

// :NOTE: homework not found :(