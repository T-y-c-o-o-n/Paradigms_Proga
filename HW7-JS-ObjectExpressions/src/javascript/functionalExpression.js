"use strict"

const cnst = (val) => (x, y, z) => val;
const pi = cnst(Math.PI);
const e = cnst(Math.E);

const variable = (view) => (x, y, z) => {
	switch (view) {
		case "x": return x;
		case "y": return y;
		case "z": return z;
	}
};

const sum = (...vals) => vals.reduce((a, b) => a + b);
const min = (...vals) => vals.reduce((a, b) => Math.min(a, b));
const max = (...vals) => vals.reduce((a, b) => Math.max(a, b));
const comparator = (a, b) => a > b ? 1 : a < b ? -1 : 0;

const abstract = (fun, args) => (...vars) => fun( ...args.map(arg => arg(...vars)) );
const add = (...args) => abstract((a, b) => a + b, args);
const subtract = (...args) => abstract((a, b) => a - b, args);
const multiply = (...args) =>  abstract((a, b) => a * b, args);
const divide = (...args) =>  abstract((a, b) => a / b, args);
const avg = (...args) => abstract((...vals) => sum(...vals) / vals.size, args);
const med = (...args) => abstract((...vals) => vals.sort(comparator)[vals.size / 2], args);
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
	"avg5": [avg, 5],
	"med3": [med, 3],
	"negate": [negate, 1]
};

const parse = function(source) {
	let stack = [];
	for (let ch of source.split(" ").filter(el => el.length > 0)) {
		if (ch in parseOperand) {
			let op = parseOperand[ch];
			stack.push(op[0](...stack.splice(-op[1])));
		} else if (ch in parseVariable) {
			stack.push(parseVariable[ch]);
		} else if (ch in parseConst) {
			stack.push(parseConst[ch]);
		} else {
			stack.push(cnst(parseInt(ch)));
		}
	}
	return stack.pop();
};

let res = parse("9 9  x y z avg5");

console.log(res(5, 1, 2));
