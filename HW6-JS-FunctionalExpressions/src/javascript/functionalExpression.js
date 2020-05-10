"use strict"

const cnst = (val) => (x, y, z) => val;
const pi = cnst(Math.PI);
const e = cnst(Math.E);

const variable = (view) => (x, y, z) => view === "x" ? x : view === "y" ? y : z;

const sum = (...vals) => vals.reduce((a, b) => a + b);
const min = (...vals) => vals.reduce(Math.min);
const max = (...vals) => vals.reduce(Math.max);
const comparator = (a, b) => a > b ? 1 : a < b ? -1 : 0;

const abstract = (fun, args) => (...vars) => fun( ...args.map(arg => arg(...vars)) );

const add = (...args) => abstract((a, b) => a + b, args);
const subtract = (...args) => abstract((a, b) => a - b, args);
const multiply = (...args) =>  abstract((a, b) => a * b, args);
const divide = (...args) =>  abstract((a, b) => a / b, args);
const avg5 = (...args) => abstract((...vals) => sum(...vals) / vals.length, args);
const med3 = (...args) => abstract((...vals) => vals.sort(comparator)[Math.floor(vals.length / 2)], args);
const negate = (arg) => (x, y, z) => -arg(x, y, z);

const parseConst = {
	"pi": pi,
	"e": e
};

const parseVariable = {
	"x": variable("x"),
	"y": variable("y"),
	"z": variable("z")
};

const parseOperand = {
	"+": [add, 2],
	"-": [subtract, 2],
	"*": [multiply, 2],
	"/": [divide, 2],
	"avg5": [avg5, 5],
	"med3": [med3, 3],
	"negate": [negate, 1]
};

const parse = function(source) {
	let stack = [];
	for (let token of source.split(" ").filter(word => word.length > 0)) {
		if (token in parseOperand) {
			let parsed = parseOperand[token];
			stack.push(parsed[0](...stack.splice(-parsed[1])));
		} else if (token in parseVariable) {
			stack.push(parseVariable[token]);
		} else if (token in parseConst) {
			stack.push(parseConst[token]);
		} else {
			stack.push(cnst(parseInt(token)));
		}
	}
	return stack.pop();
};
