package BrainF;

class Token {
    final TokenType type;
    final String lexeme;
    final int line;
    final int current;

    Token(TokenType type, String lexeme, int line, int current) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.current = current;
    }

    public String toString() {
        return type + " " + lexeme + " ";
    }

}
