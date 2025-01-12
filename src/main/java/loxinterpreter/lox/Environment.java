package loxinterpreter.lox;

import java.util.HashMap;
import java.util.Map;
class Environment implements Cloneable {
    // Linked list-esque setup for environment. Each local environment links to the environment greater in scope
    // This allows for variables outside of a scope to be updated and up to date.
    Environment enclosing;
    private final Map<String, Object> values = new HashMap<>();

    void define(String name, Object value) {
        values.put(name, value);
    }

    Environment ancestor(int distance) {
        Environment environment = this;
        for (int i = 0; i < distance; i++) {
            environment = environment.enclosing;
        }
        return environment;
    }
    Object getAt(int distance, String name) {
        return ancestor(distance).values.get(name);
    }

    // Test this
    @Override
    public Object clone() throws CloneNotSupportedException {
        Environment t = (Environment) super.clone();
        // Creating a deep copy for c
        if (t.enclosing != null) {
            t.enclosing = (Environment) t.enclosing.clone();
        }
        return t;
    }

    void assignAt(int distance, Token name, Object value) {
        ancestor(distance).values.put(name.lexeme, value);
    }
    Environment() {
        this.enclosing = null;
    }
    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    Object get(Token name) {
        if (values.containsKey(name.lexeme)) {
            return values.get(name.lexeme);
        }
        if (enclosing != null) return enclosing.get(name);
        throw new RuntimeError(name,
                "Undefined variable '" + name.lexeme + "'.");
    }

    void assign(Token name, Object value) {
        if (values.containsKey(name.lexeme)) {
            values.put(name.lexeme, value);
            return;
        }

        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }
        System.out.println(name);
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }
}
