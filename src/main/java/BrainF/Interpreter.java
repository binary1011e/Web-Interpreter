package BrainF;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class Interpreter implements Stmt.Visitor<Void> {
    private final List<Integer> arr = new ArrayList<>(Collections.nCopies(30000, 0));
    private int cur = 0;
    private String output = "";
    private int id = 0;
    private String input;
    String interpret(List<Stmt> statements, String input) {
        id = 0;
        cur = 0;
        Collections.fill(arr, 0);
        output = "";
        this.input = input;
        try {
            for (Stmt statement : statements) {
                execute(statement);
            }
        } catch (RuntimeException error) {
            output = "error in Runtime";
            return output;
        }
        return output;
    }

    private void execute(Stmt stmt) {
        stmt.accept(this);
    }

    @Override
    public Void visitLoopStmt(Stmt.Loop stmt) {
        executeBlock(stmt.statements);
        return null;
    }

    private Void executeBlock(List<Stmt> statements) {
        while(get() != 0) {
            for(Stmt statement : statements) {
                execute(statement);
            }
        }
        return null;
    }

    @Override
    public Void visitActionStmt(Stmt.Action stmt) {
        switch(stmt.action.type) {
            case INCREMENT:
                set(stmt, get(stmt) + 1);
                break;
            case DECREMENT:
                set(stmt, get(stmt) - 1);
                break;
            case IN:


                set(stmt, (int)input.charAt(id++));

                break;
            case OUT:
                output += (char) (arr.get(cur).intValue());
                break;
            case RIGHT:
                cur++;
                break;
            case LEFT:
                cur--;
                break;

        }
        return null;
    }
    private void set(Stmt.Action stmt, int val) {
        try {
            arr.set(cur, val);
        } catch(ArrayIndexOutOfBoundsException e) {
            BF.error(stmt.action, "Array Index out of bounds");
            throw new RuntimeException();
        }
    }

    private int get(Stmt.Action stmt) {
        int a = 0;
        try {
            a = arr.get(cur);

        } catch(ArrayIndexOutOfBoundsException e) {
            BF.error(stmt.action, "Array Index out of bounds");
            throw new RuntimeException();
        }
        return a;
    }
    private int get() {
        int a = 0;
        try {
            a = arr.get(cur);

        } catch(ArrayIndexOutOfBoundsException e) {
            BF.error();
            throw new RuntimeException();
        }
        return a;
    }

}
