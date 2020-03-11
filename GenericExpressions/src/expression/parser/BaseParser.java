package expression.parser;

import expression.exceptions.ParsingException;
import java.util.Queue;
import java.util.LinkedList;

public abstract class BaseParser {
    private final Source source;
    protected char tempCh;
    protected int pos;
    private final Queue<Character> tempQueue;

    protected BaseParser(Source source) {
        this.source = source;
        pos = 0;
        tempQueue = new LinkedList<>();
    }

    protected String getPre() {
        StringBuilder sb = new StringBuilder();
        while (!tempQueue.isEmpty()) {
            sb.append(tempQueue.remove());
        }
        return sb.toString();
    }

    protected String getPost() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20 && (tempCh != '\0'); ++i) {
            nextChar();
            sb.append(tempCh);
        }
        return sb.toString();
    }

    protected void nextChar() {
        tempQueue.add(tempCh);
        if (tempQueue.size() >= 20) {
            tempQueue.remove();
        }
        tempCh = source.nextChar();
        pos++;
    }

    protected char getChar() {
        char ch = tempCh;
        nextChar();
        return ch;
    }

    protected boolean test(char expected) {
        if (tempCh == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected void expect(char ch) throws ParsingException {
        if (ch != tempCh) {
            throw new ParsingException("expected: '" + ch + "' , but found: " + tempCh);
        }
        nextChar();
    }

    protected void expect(String expected) throws ParsingException {
        for (char ch : expected.toCharArray()) {
            expect(ch);
        }
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(tempCh)) {
            nextChar();
        }
    }

    protected boolean isLetter() { return 'a' <= tempCh && tempCh <= 'z'; }

    protected boolean isDigit() {
        return '0' <= tempCh && tempCh <= '9';
    }

    protected boolean isVariable() {
        return 'x' <= tempCh && tempCh <= 'z';
    }

    protected boolean isCloseBracket() { return tempCh == ')'; }
}