package BrainF;

import java.util.List;

abstract class Stmt {
    interface Visitor<R> {
        R visitLoopStmt(Loop stmt);
        R visitActionStmt(Action stmt);
    }
    static class Loop extends Stmt {

        final List<Stmt> statements;

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLoopStmt(this);
        }

        Loop(List<Stmt> statements) {
            this.statements = statements;
        }
    }
    static class Action extends Stmt {

        final Token action;

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitActionStmt(this);
        }

        Action(Token action) {
            this.action = action;
        }
    }


    abstract<R> R accept(Visitor<R> visitor);
}
