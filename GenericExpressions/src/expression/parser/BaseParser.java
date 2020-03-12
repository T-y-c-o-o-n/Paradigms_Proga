package expression.parser;

import expression.exceptions.ParsingException;

public abstract class BaseParser {
    private final Source source;
    private final CharQueue pre;
    private final CharQueue buffer;
    protected char ch;  // current char
    protected int cnt;  // count of checked symbols

    protected BaseParser(Source source) {
        this.source = source;
        pre = new CharQueue();
        buffer = new CharQueue();
        cnt = 0;
    }

    protected String getPre() {
        StringBuilder sb = new StringBuilder();
        while (pre.size() > 0) {
            sb.append(pre.pop());
        }
        return sb.toString();
    }

        protected String getPost() {
            StringBuilder sb = new StringBuilder();
            sb.append(ch);
            for (int i = 0; i < 19 && (ch != '\0'); ++i) {
                nextChar();
                sb.append(ch);
            }
            return sb.toString();
    }

    protected void nextChar() {
        pre.push(ch);
        if (pre.size() >= 20) {
            pre.pop();
        }
        if (buffer.size() > 0) {
            ch = buffer.pop();
        } else {
            ch = source.nextChar();
        }
        cnt++;
    }

    protected char getChar() {
        char ch = this.ch;
        nextChar();
        return ch;
    }

    protected boolean test(char tested) {
        if (ch == tested) {
            nextChar();
            return true;
        }
        return false;
    }

    protected boolean test(String tested) {
        if (ch != tested.charAt(0)) {
            return false;
        }
        for (int i = 1; i < tested.length(); ++i) {
            if (i - 1 == buffer.size()) {
                buffer.push(source.nextChar());
            }
            if (buffer.get(i - 1) != tested.charAt(i)) {
                return false;
            }
        }
        for (int i = 0; i < tested.length(); i++) {
            nextChar();
        }
        return true;
    }

    protected void expect(char expected) throws ParsingException {
        if (ch != expected) {
            throw new ParsingException("expected: '" + expected + "' , but found: " + ch);
        }
        nextChar();
    }

    protected void expect(String expected) throws ParsingException {
        for (char ch : expected.toCharArray()) {
            expect(ch);
        }
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
    }

    protected boolean isLetter() { return 'a' <= ch && ch <= 'z'; }

    protected boolean isDigit() {
        return '0' <= ch && ch <= '9';
    }

    protected boolean isVariable() {
        return 'x' <= ch && ch <= 'z';
    }

    protected boolean isCloseBracket() { return ch == ')'; }

    private static class CharQueue {
        private char[] arr = new char[10];
        private int head, tail = 0;

        private char get(int pos) {
            return arr[(head + pos) % arr.length];
        }

        private void push(char e) {
            if (size() + 1 == arr.length) {
                grow();
            }

            arr[tail] = e;
            tail = inc(tail);
        }

        private char pop() {
            assert size() > 0;

            char e = arr[head];
            head = inc(head);
            return e;
        }

        private int size() {
            return (arr.length + tail - head) % arr.length;
        }

        private void grow() {
            int size = size();
            char[] newArr = new char[arr.length * 2];
            if (tail < head) {
                System.arraycopy(arr, head, newArr, 0, arr.length - head);
                System.arraycopy(arr, 0, newArr, arr.length - head, tail);
            } else {
                System.arraycopy(arr, head, newArr, 0, tail - head);
            }
            head = 0;
            tail = size;
            arr = newArr;
        }

        private int inc(int a) {
            return (a + 1) % arr.length;
        }
    }
}