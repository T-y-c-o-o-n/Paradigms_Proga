"use strict";

const expression = (() => {
    const ExpressionTypeFactory = function(evaluate, diff, toString, prefix, postfix) {
        function Expression(...args) {
            this.args = args;
        }
        Expression.prototype = Object.create(Object);
        Expression.prototype.constructor = Expression;
        Expression.prototype.evaluate = evaluate;
        Expression.prototype.diff = diff;
        Expression.prototype.toString = toString;
        Expression.prototype.prefix = prefix;
        Expression.prototype.postfix = postfix;
        return Expression;
    };

    const AbstractOperation = ExpressionTypeFactory(
        function(...vars) { return this.evaluateImpl( ...this.args.map(arg => arg.evaluate(...vars))) },
        function(par) { return this.diffImpl(par, ...this.args) },
        function() { return this.args.join(" ") + " " + this.op },
        function() { return '(' + this.op + this.args.reduce((acc, tmp) => acc + ' ' + tmp.prefix(), '') + ')' },
        function() { return '(' + this.args.reduce((acc, tmp) => acc + ' ' + tmp.prefix(), '') + this.op + ')' }
    );

    const OperationFactory = function(evaluateImpl, op, diffImpl) {
        function Operation(...args) { AbstractOperation.call(this, ...args); }
        Operation.prototype = Object.create(AbstractOperation.prototype);
        Operation.prototype.constructor = Operation;
        Operation.prototype.evaluateImpl = evaluateImpl;
        Operation.prototype.op = op;
        Operation.prototype.diffImpl = diffImpl;
        return Operation
    };

    return {
        makeNewExpressionType: ExpressionTypeFactory,
        makeNewOperation: OperationFactory
    }
})();

const makeNewExpressionType = expression.makeNewExpressionType;
const makeNewOperation = expression.makeNewOperation;

const Const = makeNewExpressionType(function(x, y, z) { return this.args[0] },
    (par) => zero,
    function() { return this.args[0].toString() },
    function() { return this.args[0].toString() },
    function() { return this.args[0].toString() }
);
const zero = new Const(0);
const one = new Const(1);
const Variable = makeNewExpressionType(
    function(x, y, z) { return this.args[0] === "x" ? x : this.args[0] === "y" ? y : z },
    function(par) { return this.args[0] === par ? one : zero },
    function() { return this.args[0] },
    function() { return this.args[0] },
function() { return this.args[0] }
);

const sum = (...arr) => arr.reduce((acc, tmp) => acc + tmp, 0);
const Add = makeNewOperation(sum,"+",
    (par, a, b) => new Add(a.diff(par), b.diff(par)));
const Subtract = makeNewOperation((a, b) => a - b,"-",
    (par, a, b) => new Subtract(a.diff(par), b.diff(par))
);
const Multiply = makeNewOperation((a, b) => a * b,"*",
    (par, a, b) => new Add(
        new Multiply(a.diff(par), b),
        new Multiply(a, b.diff(par)))
);
const Divide = makeNewOperation((a, b) => a / b,"/",
    (par, a, b) => new Divide(
        new Subtract(new Multiply(a.diff(par), b), new Multiply(a, b.diff(par))),
        new Multiply(b, b)
    )
);
const Negate = makeNewOperation(a => -a,"negate",
    (par, a) => new Negate(a.diff(par))
);
const Ln = makeNewOperation(a => Math.log(Math.abs(a)),"ln",
    (par, a) => new Divide(a.diff(par), a)
);
const Log = makeNewOperation((a, b) => Math.log(Math.abs(b)) / Math.log(Math.abs(a)),"log",
    (par, a, b) => new Divide(new Ln(b), new Ln(a)).diff(par)
);
const Power = makeNewOperation(Math.pow,"pow",
    function(par, a, b) { return new Multiply(this, new Add(
        new Multiply(b.diff(par), new Ln(a)),
        new Multiply(b, new Divide(a.diff(par), a))
    )) }
);
const Exp = makeNewOperation(Math.exp, "exp", function(par, arg) { return new Multiply(arg.diff(par), this) });
const SumExp = makeNewOperation(
    sum((...args) => args.map(Math.exp)),
    "sumexp",
    (par, ...args) => new Add(args.map((arg) => new Multiply(new Exp(arg), arg.diff(par))))
);
const SoftMax = makeNewOperation((...args) => Math.exp(args[0]) / sum(args.map(Math.exp)), "softmax",
    (par, ...args) => new Divide(new Exp(args[0]), new SumExp(...args)).diff(par));

const parser = (() => {
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
        "pow": [Power, 2],
        "exp": [Exp, 1],
        "sumexp": [SumExp, -1],
        "softmax": [SoftMax, -1]
    };

    const parser_errors = (() => {
        const ErrorFactory = function (name) {
            const Error = function(message) {
                this.message = message + " in position " + pos + " : \"" + getPre() + "<HERE>" + getPost() + "\"";
            };
            Error.prototype = Object.create(Error.prototype);
            Error.prototype.name = name;
            Error.prototype.constructor = Error;
            return Error;
        };
        return {
            makeNewError: ErrorFactory
        }
    })();
    const makeNewError = parser_errors.makeNewError;
    const DirtyEndOfInputError = makeNewError("DirtyEndOfInputError");
    const UnexpectedOperandError = makeNewError("UnexpectedOperandError");
    const NoCloseBracketError = makeNewError("NoCloseBracketError");
    const MissingSpaceError = makeNewError("MissingSpaceError");
    const ConstantError = makeNewError("ConstantError");
    const UnexpectedSymbolError = makeNewError("UnexpectedSymbolError");

    const num = /\d/;
    let source;
    let pos;
    let ch;

    function getPre() {
        return source.substring(Math.max(pos - 10, 0), pos);
    }
    function getPost() {
        return source.substring(pos, Math.min(pos + 10, source.length));
    }

    const parsePoland = function (source) {
        let stack = [];
        for (let token of source.split(" ").filter(word => word.length > 0)) {
            if (token in parseOperation) {
                let parsed = parseOperation[token];
                stack.push(new parsed[0](...stack.splice(-parsed[1])));
            } else if (token in parseVariable) {
                stack.push(parseVariable[token]);
            } else {
                stack.push(new Const(parseInt(token)));
            }
        }
        return stack.pop();
    };

    const parse = function(string, mode) {
        source = string;
        pos = -1;
        nextChar();
        let expr = parseArg(mode);
        skipWhitespace();
        if (!test('\0')) {
            throw new DirtyEndOfInputError("expected end of input");
        }
        return expr;
    };

    const parseArg = function(mode) {
        skipWhitespace();
        if (testNum()) { return parseConst(false); }
        if (test('-')) {
            if (!testNum()) { throw new ConstantError("expected constant after minus"); }
            return parseConst(true);
        }
        if (ch in parseVariable) { return parseVariable[getChar()]; }
        if (test('(')) {
            let expr = parseFun(mode);
            skipWhitespace();
            expect(')');
            return expr;
        }
        throw new UnexpectedSymbolError("expected expression");
    };

    const parseFun = function (mode) {
        let parsed;
        let wasWS;
        let wasInBrackets = false;
        if (mode === "prefix") {
            skipWhitespace();
            parsed = parseOperand();
            if (parsed === undefined) { throw new UnexpectedOperandError(ch); }
        }
        let args = [];
        while (true) {
            wasWS = test(' ');
            skipWhitespace();
            if (mode === "postfix") {
                parsed = parseOperand();
                if (parsed !== undefined) {
                    if (!wasWS && !wasInBrackets) { throw new MissingSpaceError("between last argument and operand") }
                    return new parsed[0](...args);
                }
            }
            if (mode === "prefix" && args.length === parsed[1] || testCloseBracket()) {
                return new parsed[0](...args);
            }
            const res = parseArg(mode);
            args.push(res);
            const newInBrackets = ! (res instanceof Const || res instanceof Variable) ;
            if (args.length > 0 || mode === "prefix") {
                if (!wasWS && !wasInBrackets && !newInBrackets) { throw new MissingSpaceError("before argument") }
            }
            wasInBrackets = newInBrackets;
        }
    };

    const parseOperand = function () {
        for (let variant in parseOperation) {
            if (test(variant)) {
                return parseOperation[variant];
            }
        }
    };

    const parseConst = function (wasNegated) {
        let val = wasNegated ? "-" : "";
        while (testNum()) { val += getChar(); }
        return new Const(parseInt(val));
    };

    const nextChar = function () {
        if (pos + 1 === source.length) {
            ch = '\0';
        } else {
            pos++;
            ch = source[pos];
        }
    };

    const getChar = function () {
        const res = ch;
        nextChar();
        return res;
    };

    const test = function (tested) {
        let posWas = pos;
        let charWas = ch;
        for (let i = 0; i < tested.length; i++) {
            if (ch === tested[i]) {
                nextChar();
            } else {
                pos = posWas;
                ch = charWas;
                return false;
            }
        }
        return true;
    };

    const testNum = function () {
        return num.test(ch);
    };

    function testCloseBracket() { return ch === ')' }

    const expect = function (expected) {
        for (let i = 0; i < expected.length; i++) {
            if (!test(expected[i])) {
                throw new UnexpectedSymbolError("expected \'" + expected[i] + "\' but found " + "\'" + ch + "\'");
            }
        }
    };

    const skipWhitespace = function() {
        while (test(' ')) {
            // skip
        }
    };

    return {
        parsePoland: parsePoland,
        parsePrefix: (string) => parse(string, "prefix"),
        parsePostfix: (string) => parse(string, "postfix")
    }
})();
const parse = parser.parsePoland;
const parsePrefix = parser.parsePrefix;
const parsePostfix = parser.parsePostfix;


// let test = new Add(new Variable('x'), new Const(2)).diff('x');
// let test = parsePostfix("( 1 2 3 ( 2 4 *) ( 1 3 +) 4 5 6 7 ( 9 0 ysoftmax) 9 -)");
// console.log(test.toString());
// console.log(test);
// console.log(test.evaluate(1, 2, 3));

// :NOTE: homework not found :(