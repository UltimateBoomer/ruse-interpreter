package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;

public record Var(
    String name
) implements ArithExp {

    @Override
    public SExp toSExp() {
        return new Atom(name);
    }
    
}
