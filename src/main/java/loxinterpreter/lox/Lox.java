package loxinterpreter.lox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Lox main class for interpreter
 */
public class Lox {
    private static Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;
    private static String err = "";
    // Actual run function
    public String run(String source) {
        interpreter = new Interpreter();
        hadError = false;
        hadRuntimeError = false;
        err = "";
        Scanner scanner = new Scanner(source);
        // Splits up the line into tokens
        List<Token> tokens = scanner.scanTokens();
        // Parses the tokens into an AST (Abstract Syntax Tree)
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        if (hadError) return err;

        // Resolves scoping issues
        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);

        if (hadError) return err;

        // Simple type checker for the variables with a type + checks for type mismatches
        Checker checker = new Checker();
        checker.check(statements);
        if (hadError) return err;
        // Interpreter interprets the expression
        return interpreter.interpret(statements);
    }

    // Error reporting
    static void error(int line, String message) {
        report(line, "", message);
    }

    // Error printing
    private static void report(int line, String where, String message) {
        err = "[line " + line + "] Error" + where + ": " + message;
        hadError = true;
    }

    // Error with a specific token
    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    //runTime error
    static void runtimeError(RuntimeError error) {
        err = error.getMessage() + "\n[line " + error.token.line + "]";
        hadRuntimeError = true;
    }
}