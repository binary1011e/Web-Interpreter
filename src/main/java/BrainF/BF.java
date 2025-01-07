package BrainF;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
/**
 * BF main class for interpreter
 */
@Service
public class BF {
    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;
    private String source;
    private String input;
    private static String err = "";
    public BF(String source, String input) {
        this.source = source;
        this.input = String.join("", input.split(" "));
        hadError = false;
        hadRuntimeError = false;
    }
    // Actual run function
    public String run() {
        System.out.println(source);
        Scanner scanner = new Scanner(source, input);
        if (hadError) return err;
        // Splits up the line into tokens
        List<Token> tokens = scanner.scanTokens();
        // Parses the tokens into an AST (Abstract Syntax Tree)
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();
        if (hadError) return err;
        // Interpreter interprets the expression
        return interpreter.interpret(statements, input);
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
            report(token.line, " at " + token.current, message);
        }
    }
    static void error() {
        err = "Array out of bounds exception within loop";
        hadError = true;
    }

    static void inputError(int commacnt, int len) {
        err = "Bad input. You need " + commacnt + " characters and you have " + len;
        hadError = true;
    }

    static void badCharacter(int line) {
        err = "Bad input. There is a character in line " + line + " not part of BF";
        hadError = true;
    }

}