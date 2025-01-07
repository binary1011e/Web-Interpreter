package loxinterpreter.lox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static loxinterpreter.lox.TokenType.*;

class Checker implements Expr.Visitor<TokenType>, Stmt.Visitor<Void> {
    private TokenType currentFunction = null;
    private enum FunctionType {
        NONE,
        FUNCTION
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        check(stmt.statements);
        return null;
    }

    @Override
    public Void visitFunctionStmt(Stmt.Function stmt) {

        checkFunction(stmt, stmt.name.type);
        return null;
    }

    @Override
    public Void visitIfStmt(Stmt.If stmt) {
        TokenType a = check(stmt.condition);
        if (a == STR) {
            Lox.error(-1, "Type Mismatch: if condition");
        }
        check(stmt.thenBranch);
        if(stmt.elseBranch != null) check(stmt.elseBranch);
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
        check(stmt.expression);
        return null;
    }

    @Override
    public Void visitReturnStmt(Stmt.Return stmt) {
        if (stmt.value != null) {
            TokenType a = check(stmt.value);
            if (currentFunction != null && a != currentFunction) {
                Lox.error(-1, "Return stmt mismatch");
            }
        }
        return null;
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        check(stmt.expression);
        return null;
    }

    @Override
    public TokenType visitVariableExpr(Expr.Variable expr) {
        return expr.name.type;
    }


    void check(List<Stmt> statements) {
        for (Stmt statement : statements) {
            check(statement);
        }
    }

    private void check(Stmt stmt) {
        stmt.accept(this);
    }

    private TokenType check(Expr expr) {
        return expr.accept(this);
    }


    private void checkFunction(Stmt.Function function, TokenType type) {
        TokenType enclosingFunction = currentFunction;
        currentFunction = type;
        check(function.body);
        currentFunction = enclosingFunction;
    }

    @Override
    public Void visitVarStmt(Stmt.Var stmt) {
        TokenType type = stmt.type;
        if (stmt.initializer != null) {
            if (type != VAR && type != check(stmt.initializer)) {
                Lox.error(-1, "Type mismatch");
            }
        }
        return null;
    }

    @Override
    public Void visitWhileStmt(Stmt.While stmt) {
        if (check(stmt.condition) == STR) {
            Lox.error(-1, "Type mismatch with conditional");
        }
        check(stmt.body);
        return null;
    }


    @Override
    public TokenType visitAssignExpr(Expr.Assign expr) {
        TokenType a = expr.name.type;
        TokenType b = check(expr);
        if (a != VAR && a != b) {
            Lox.error(expr.name.line, "Type mismatch");
        }
        return a;
    }

    @Override
    public TokenType visitBinaryExpr(Expr.Binary expr) {
        TokenType a = check(expr.left);
        TokenType b = check(expr.right);
        if (a != VAR && a != b) {
            return MISMATCH;
        }
        return a;
    }

    @Override
    public TokenType visitCallExpr(Expr.Call expr) {
        return check(expr.callee);
    }

    @Override
    public TokenType visitGroupingExpr(Expr.Grouping expr) {
        check(expr.expression);
        return null;
    }

    @Override
    public TokenType visitLiteralExpr(Expr.Literal expr) {
        Object val = expr.value;
        if (val instanceof String) {
            return STR;
        } else if (val instanceof Boolean) {
            return BOOL;
        }
        return NUM;
    }

    @Override
    public TokenType visitLogicalExpr(Expr.Logical expr) {
        TokenType a = check(expr.left);
        TokenType b = check(expr.right);
        if (a == STR || b == STR) {
            Lox.error(-1, "Type mismatch");
        }
        return BOOL;
    }

    @Override
    public TokenType visitUnaryExpr(Expr.Unary expr) {
        return check(expr.right);
    }







}

