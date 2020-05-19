"use strict";

let parseOperation = {
    // "+": [Add, 2],
    // "-": [Subtract, 2],
    // "*": [Multiply, 2],
    // "/": [Divide, 2],
    // "negate": [Negate, 1],
    // "log": [Log, 2],
    // "pow": [Power, 2],
    // "exp": [Exp, 1],
    // "sumexp": [Sumexp, -1],
    // "softmax": [Softmax, -1]
};

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
        function() { return '(' + this.op + ' ' + this.args.map(arg => arg.prefix()).join(' ') + ')' },
        function() {
            return '(' + this.args.map(arg => arg.postfix()).join(' ') + ' ' + this.op + ')'
        }
    );

    const OperationFactory = function(evaluateImpl, op, diffImpl, arity) {
        function Operation(...args) { AbstractOperation.call(this, ...args); }
        Operation.prototype = Object.create(AbstractOperation.prototype);
        Operation.prototype.constructor = Operation;
        Operation.prototype.evaluateImpl = evaluateImpl;
        Operation.prototype.op = op;
        Operation.prototype.diffImpl = diffImpl;

        parseOperation[op] = [Operation, arity];
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
    (par, a, b) => new Add(a.diff(par), b.diff(par)), 2);
const Subtract = makeNewOperation((a, b) => a - b,"-",
    (par, a, b) => new Subtract(a.diff(par), b.diff(par)), 2
);
const Multiply = makeNewOperation((a, b) => a * b,"*",
    (par, a, b) => new Add(
        new Multiply(a.diff(par), b),
        new Multiply(a, b.diff(par))), 2
);
const Divide = makeNewOperation((a, b) => a / b,"/",
    (par, a, b) => new Divide(
        new Subtract(new Multiply(a.diff(par), b), new Multiply(a, b.diff(par))),
        new Multiply(b, b)
    ), 2
);
const Negate = makeNewOperation(a => -a,"negate",
    (par, a) => new Negate(a.diff(par)), 1
);
const Ln = makeNewOperation(a => Math.log(Math.abs(a)),"ln",
    (par, a) => new Divide(a.diff(par), a), 1
);
const Log = makeNewOperation((a, b) => Math.log(Math.abs(b)) / Math.log(Math.abs(a)),"log",
    (par, a, b) => new Divide(new Ln(b), new Ln(a)).diff(par), 2
);
const Power = makeNewOperation(Math.pow,"pow",
    function(par, a, b) { return new Multiply(this, new Add(
        new Multiply(b.diff(par), new Ln(a)),
        new Multiply(b, new Divide(a.diff(par), a))
    )) }, 2
);
const Exp = makeNewOperation(Math.exp, "exp",
    function(par, arg) { return new Multiply(arg.diff(par), this) }, 1
);
const Sumexp = makeNewOperation(
    (...args) => sum(...args.map(Math.exp)),
    "sumexp",
    (par, ...args) => args.length === 0 ? zero :
        args.map((arg) => new Multiply(new Exp(arg), arg.diff(par))).reduce((acc, tmp) => new Add(acc, tmp)),
    -1
);
const Softmax = makeNewOperation((...args) => Math.exp(args[0]) / sum(...args.map(Math.exp)), "softmax",
    (par, ...args) => new Divide(new Exp(args[0]), new Sumexp(...args)).diff(par),
    -1
);

const parser = (() => {
    const parseVariable = {
        "x": new Variable("x"),
        "y": new Variable("y"),
        "z": new Variable("z")
    };

    const parser_errors = (() => {
        const ErrorFactory = function (name) {
            const myError = function(message, needPos) {
                this.message = message + (needPos ? (" in position " + pos) : '')
                    + " : \"" + getPre() + (needPos? "<HERE>" : '') + getPost() + "\"";
                this.name = name;
            };
            myError.prototype = new Error;
            return myError;
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
    const InvalidArgumentsError = makeNewError("InvalidArgumentsError");
    const EmptyInputError = makeNewError("EmptyInputError");

    const num = /\d/;
    let source;
    let pos;
    let ch;

    function getPre() {
        return source.substring(Math.max(pos - 17, 0), pos);
    }
    function getPost() {
        return source.substring(pos, Math.min(pos + 17, source.length));
    }

    function parsePoland(source) {
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
    }

    function parse(string, mode) {
        // console.log(string);
        source = string;
        pos = -1;
        nextChar();
        skipWhitespace();
        if (test('\0')) {
            throw new EmptyInputError("expected expression", false);
        }
        let expr = parseArg(mode);
        skipWhitespace();
        if (!test('\0')) {
            throw new DirtyEndOfInputError("expected end of input", true);
        }
        return expr;
    }

    function parseArg(mode) {
        skipWhitespace();
        if (testNum()) { return parseConst(false); }
        if (test('-')) {
            if (!testNum()) { throw new ConstantError("expected constant after minus", true); }
            return parseConst(true);
        }
        if (ch in parseVariable) { return parseVariable[getChar()]; }
        if (test('(')) {
            let expr = parseFun(mode);
            skipWhitespace();
            try {
                expect(')');
            } catch(e) {
                throw new NoCloseBracketError(e.message, true);
            }
            return expr;
        }
        throw new UnexpectedSymbolError("incorrect expression", true);
    }

    function parseFun(mode) {
        let parsed;
        let wasWS;
        let wasInBrackets = false;
        if (mode === "prefix") {
            skipWhitespace();
            parsed = parseOperand();
            if (parsed === undefined) { throw new UnexpectedOperandError(ch, true); }
        }
        let args = [];
        while (true) {
            wasWS = test(' ');
            skipWhitespace();
            if (mode === "postfix") {
                parsed = parseOperand();
                if (parsed !== undefined) {
                    if (!wasWS && !wasInBrackets) { throw new MissingSpaceError("between last argument and operand", false) }  // HERE
                    if (args.length !== parsed[1] && parsed[1] !== -1) {
                        throw new InvalidArgumentsError("invalid number of arguments for operation " + parsed[0].prototype.op, false);  // HERE
                    }
                    return new parsed[0](...args);
                }
            }
            if (testCloseBracket()) {
                if (parsed === undefined) { throw new UnexpectedOperandError("missing operand", true) }
                if (args.length !== parsed[1] && parsed[1] !== -1) {
                    throw new InvalidArgumentsError("invalid number of arguments for operation " + parsed[0].prototype.op, false);  // HERE
                }
                return new parsed[0](...args);
            }
            let res;
            try {
                res = parseArg(mode);
            } catch (e) {
                if (mode === "postfix") {
                    throw new UnexpectedOperandError("incorrect operation (or argument)", true);
                } else {
                    throw new InvalidArgumentsError("cannot read argument", true);
                }
            }
            args.push(res);
            const newInBrackets = ! (res instanceof Const || res instanceof Variable) ;
            if (args.length > 1 || mode === "prefix") {
                if (!wasWS && !wasInBrackets && !newInBrackets) { throw new MissingSpaceError("before argument", false) }  // HERE
            }
            wasInBrackets = newInBrackets;
        }
    }

    function parseOperand() {
        for (let variant in parseOperation) {
            if (variant === '-' && ch === '-') {
                let chWas = ch;
                let posWas = pos;
                nextChar();
                if (testNum()) {
                    ch = chWas;
                    pos = posWas;
                    return undefined;
                } else {
                    return parseOperation['-'];
                }
            }
            if (test(variant)) {
                return parseOperation[variant];
            }
        }
    }

    function parseConst(wasNegated) {
        let val = wasNegated ? "-" : "";
        while (testNum()) { val += getChar(); }
        return new Const(parseInt(val));
    }

    function nextChar() {
        if (pos + 1 === source.length) {
            ch = '\0';
        } else {
            pos++;
            ch = source[pos];
        }
        }

    function getChar() {
        const res = ch;
        nextChar();
        return res;
    }

    function test(tested) {
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
    }

    function testNum() {
        return num.test(ch);
    }

    function testCloseBracket() { return ch === ')' }

    function expect(expected) {
        for (let i = 0; i < expected.length; i++) {
            if (!test(expected[i])) {
                throw new UnexpectedSymbolError("expected \'" + expected[i] + "\' but found " + "\'" + ch + "\'", true);
            }
        }
    }

    function skipWhitespace() {
        while (test(' ')) {
            // skip
        }
    }

    return {
        parsePoland: parsePoland,
        parsePrefix: (string) => parse(string, "prefix"),
        parsePostfix: (string) => parse(string, "postfix"),
    }
})();
const parse = parser.parsePoland;
const parsePrefix = parser.parsePrefix;
const parsePostfix = parser.parsePostfix;


// let test = new Sumexp(new Variable('x')).diff('x');
try {
    let test = parsePostfix("(x y pow)");
    console.log(test.postfix());
    console.log(test);
    console.log(test.evaluate(4, 2, 3));
} catch(e) {
    console.log(e.name + ": " + e.message);
}

// :NOTE: homework not found :(