package loxinterpreter.lox;

class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;
    TokenType initType;
    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
        this.initType = null;
    }

    public void setInitType(TokenType initType) {
        this.initType = initType;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal; 
    }
    
}
