package BrainF;

import java.util.ArrayList;
import java.util.List;

import static BrainF.TokenType.*;

class Parser {
    // Handles errors
    private static class ParseError extends RuntimeException {

    }
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    //Tries parsing the tokens
    List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while(!isAtEnd())
        {
            try {
                statements.add(statement());
            } catch (ParseError e) {
                return null;
            }

        }
        return statements;
    }

    private Stmt statement() {
        if (match(LEFT_BRACE)) return new Stmt.Loop(loop());
        if (check(RIGHT_BRACE)) return null;
        return actionStatement();
    }
    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private List<Stmt> loop() {
        List<Stmt> statements = new ArrayList<>();

        while(!check(RIGHT_BRACE) && !isAtEnd()) {
            statements.add(statement());
        }
        if (!statements.isEmpty() && statements.getLast() == null) statements.removeLast();
        consume(RIGHT_BRACE, "Expect ']' after block.");
        return statements;
    }

    private Stmt actionStatement() {
        advance();
        return new Stmt.Action(previous());
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw error(peek(), message);
    }


    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private ParseError error(Token token, String message) {
        BF.error(token, message);
        return new ParseError();
    }
}
