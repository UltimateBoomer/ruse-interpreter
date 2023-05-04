package io.github.ultimateboomer.ruseinterpreter.model.ruse;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;

public record Num(
    Integer n
) implements ArithExp {

    @Override
    public SExp toSExp() {
        return new Atom(n.toString());
    }

}
