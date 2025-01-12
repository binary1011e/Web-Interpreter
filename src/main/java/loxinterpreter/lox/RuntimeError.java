package loxinterpreter.lox;

class RuntimeError extends RuntimeException{
    Token token = null;
    RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }
    RuntimeError(String message) {
        super(message);
    }
}
