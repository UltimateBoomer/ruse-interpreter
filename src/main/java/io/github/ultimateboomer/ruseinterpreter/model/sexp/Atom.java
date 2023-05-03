package io.github.ultimateboomer.ruseinterpreter.model.sexp;

public record Atom(
    String value
) implements SExp {
    
    @Override
    public String toString() {
        return value;
    }

}
