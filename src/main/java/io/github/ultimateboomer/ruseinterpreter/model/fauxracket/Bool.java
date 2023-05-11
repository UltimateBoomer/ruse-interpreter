package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;

public record Bool(
    boolean value
) implements BoolExp {

    @Override
    public SExp toSExp() {
        return new Atom(Boolean.toString(value));
    }
    
}
