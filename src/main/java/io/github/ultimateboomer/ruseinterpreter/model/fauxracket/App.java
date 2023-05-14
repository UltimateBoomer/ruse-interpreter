package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record App(
    Exp fun,
    Exp arg
) implements ArithExp {

    @Override
    public SExp toSExp() {
        return SList.of(fun.toSExp(), arg.toSExp());
    }
    
}
